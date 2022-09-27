package club.archdev.archhub.listeners;

import club.archdev.archhub.ArchHub;
import club.archdev.archhub.playerdata.PlayerData;
import club.archdev.archhub.utils.Utils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HubListeners implements Listener {

    private ArchHub main = ArchHub.getInstance();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        PlayerData playerData = this.main.getPlayerDataManager().getPlayerData(player.getUniqueId());

        player.getInventory().clear();
        player.updateInventory();

        this.main.getPlayerDataManager().sendToSpawnAndResetPlayer(player);

        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 4));

        event.setJoinMessage(null);

        for (String string : main.getMessagesConfig().getConfig().getStringList("JOIN-MESSAGE")) {
            player.sendMessage(
                    Utils.translate(
                            string.replace("%player%", player.getName()))
            );
        }
    }

    @EventHandler
    public void onBlockPlaceOne(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        if (player.hasPermission("archhub.admin")) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlaceTwo(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        Block block = event.getBlock();

        ItemStack itemStack = new ItemStack(block.getType());

        if (!block.getType().equals(Material.getMaterial(this.main.getHotBarConfig().getConfig().getString("hub.blocks.material")))) {
            return;
        }

        Bukkit.getScheduler().scheduleSyncDelayedTask(this.main, new Runnable() {
            @Override
            public void run() {
                block.setType(Material.AIR);

                itemStack.setAmount(64);

                player.getInventory().setItem(main.getHotBarConfig().getConfig().getInt("hub.blocks.slot"), itemStack);
            }
        }, 60L);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (player.hasPermission("archhub.admin")) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        String message = event.getMessage();

        String[] arguments = message.split(" ");

        switch (arguments[0].toLowerCase()) {
            case "/me":
            case "/minecraft:me":
                event.setCancelled(true);

                break;
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        Location location = player.getLocation();

        if (location.getY() <= 0) {
            player.chat("/spawn");
        }

        if (player.getGameMode() != GameMode.CREATIVE && player.getLocation().subtract(0.0, 1.0, 0.0).getBlock().getType() != Material.AIR && !player.isFlying()) {
            player.setAllowFlight(true);
        }

        if (event.getTo().getY() < 2.0) {
            main.getServer().getScheduler().scheduleSyncDelayedTask((Plugin) main, (Runnable) new Runnable() {
                @Override
                public void run() {
                    double y = player.getLocation().getY() - 2.0;
                    Location l = new Location(player.getLocation().getWorld(), player.getLocation().getX(), y, player.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());
                    player.getWorld().playEffect(l, Effect.ENDER_SIGNAL, 50, 30);
                }
            }, 10L);
        }
    }

    @EventHandler
    public void onPlayerToggleFlight(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();

        if (player.getGameMode() == GameMode.CREATIVE) {
            return;
        }

        event.setCancelled(true);
        player.setAllowFlight(false);
        player.setFlying(false);
        player.setVelocity(player.getLocation().getDirection().multiply(1.5).setY(1));
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.hasItem() && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && event.getItem().getType().equals(Material.ENDER_PEARL)) {
            player.setVelocity(event.getPlayer().getLocation().getDirection().multiply(1.5f));
            event.setCancelled(true);
            player.getInventory().setItem(this.main.getHotBarConfig().getConfig().getInt("hub.ender-pearl.slot"), new ItemStack(Material.ENDER_PEARL, 1));
            player.updateInventory();
        }
    }

    @EventHandler
    public void onPlayerDropItemEvent(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onMobSpawn(CreatureSpawnEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent event) {
        Player p = (Player) event.getEntity();
        p.setFoodLevel(20);
        p.setSaturation(20.0f);
        p.setHealth(20.0);

        event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        event.setCancelled(true);

        if ((!(event.getWhoClicked() instanceof Player)) || (event.getCurrentItem() == null)) {
            return;
        }

        if (event.getInventory().getType().equals(InventoryType.PLAYER)) {
            event.setCancelled(false);
        }
    }
}
