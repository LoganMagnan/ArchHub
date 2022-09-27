package club.archdev.archhub;

import aether.Aether;
import club.archdev.archhub.listeners.*;
import club.archdev.archhub.managers.ChunkManager;
import club.archdev.archhub.managers.CommandManager;
import club.archdev.archhub.managers.PlayerDataManager;
import club.archdev.archhub.managers.SpawnManager;
import club.archdev.archhub.managers.items.HotBarManager;
import club.archdev.archhub.normalmenusystem.PlayerMenuUtil;
import club.archdev.archhub.scoreboard.ScoreboardProvider;
import club.archdev.archhub.utils.BungeeUtils;
import club.archdev.archhub.utils.Utils;
import club.archdev.archhub.utils.config.FileConfig;
import club.archdev.archhub.utils.config.file.Config;
import lombok.Getter;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Arrays;
import java.util.HashMap;

@Getter
public class ArchHub extends JavaPlugin {

    @Getter private static ArchHub instance;

    private Config settingsConfig;
    private FileConfig messagesConfig;
    private FileConfig hotBarConfig;
    private FileConfig menusConfig;
    private FileConfig scoreboardConfig;

    private PlayerDataManager playerDataManager;
    private HotBarManager hotBarManager;
    private SpawnManager spawnManager;
    private ChunkManager chunkManager;
    private CommandManager commandManager;

    private Chat chat;
    private Permission perms;

    private HashMap<Player, PlayerMenuUtil> playerMenuUtilMap = new HashMap<Player, PlayerMenuUtil>();

    public void onEnable() {
        instance = this;

        this.saveDefaultConfig();
        this.settingsConfig = new Config("config", this);
        this.messagesConfig = new FileConfig(this, "messages.yml");
        this.hotBarConfig = new FileConfig(this, "hotbar.yml");
        this.menusConfig = new FileConfig(this, "menus.yml");
        this.scoreboardConfig = new FileConfig(this, "scoreboard.yml");

        Bukkit.getConsoleSender().sendMessage("------------------------------------------------");
        Bukkit.getConsoleSender().sendMessage(Utils.translate("&dArchHub &8- &av" + this.getDescription().getVersion()));
        Bukkit.getConsoleSender().sendMessage(Utils.translate("&7Made by &eTrixkz"));
        Bukkit.getConsoleSender().sendMessage("------------------------------------------------");

        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        Bukkit.getMessenger().registerIncomingPluginChannel(this, "Return", new BungeeUtils());

        this.loadManagers();
        this.loadListeners();
        this.loadRunnables();
        this.loadTasks();

        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity.getType() != EntityType.PLAYER && entity.getType() != EntityType.ITEM_FRAME) {
                    entity.remove();
                }
            }

            world.setGameRuleValue("doDaylightCycle", "false");
            world.setTime(0L);
            world.setStorm(false);
        }

        this.setupChat();
        this.setupPermissions();
    }

    public void onDisable() {
        instance = null;

        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity.getType() == EntityType.DROPPED_ITEM) {
                    entity.remove();
                }
            }

            for (Chunk chunk : world.getLoadedChunks()) {
                chunk.unload(true);
            }
        }
    }

    private void loadManagers() {
        this.playerDataManager = new PlayerDataManager();
        this.hotBarManager = new HotBarManager();
        this.spawnManager = new SpawnManager();
        this.chunkManager = new ChunkManager();
        this.commandManager = new CommandManager();
    }

    private void loadListeners() {
        Arrays.asList(
                new PlayerDataListener(),
                new HubListeners(),
                new InteractListener(),
                new MenuListener(),
                new ButtonListener(),
                new MovementListener(),
                new CosmeticListener()
        ).forEach(listener -> this.getServer().getPluginManager().registerEvents(listener, this));
    }

    private void loadRunnables() {
        new Aether(this, new ScoreboardProvider());
    }

    private void loadTasks() {

    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(Chat.class);
        if (chatProvider != null) {
            chat = chatProvider.getProvider();
        }
        return (chat != null);
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(Permission.class);
        if (permissionProvider != null) {
            perms = permissionProvider.getProvider();
        }
        return (perms != null);
    }

    public PlayerMenuUtil getPlayerMenuUtil(Player player) {
        PlayerMenuUtil playerMenuUtil;

        if (playerMenuUtilMap.containsKey(player)) {
            return playerMenuUtilMap.get(player);
        } else {
            playerMenuUtil = new PlayerMenuUtil(player);

            playerMenuUtilMap.put(player, playerMenuUtil);

            return playerMenuUtil;
        }
    }
}
