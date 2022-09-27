package club.archdev.archhub.advancedmenusystem.settings;

import club.archdev.archhub.ArchHub;
import club.archdev.archhub.advancedmenusystem.Button;
import club.archdev.archhub.playerdata.PlayerData;
import club.archdev.archhub.playerdata.PlayerSettings;
import club.archdev.archhub.utils.ItemBuilder;
import club.archdev.archhub.utils.Utils;
import club.archdev.archhub.utils.config.ConfigCursor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class SettingsButton extends Button {

    private String name;
    private List<String> lore;
    private Material material;
    private int durability;
    private String type;
    private String command;

    @Override
    public ItemStack getButtonItem(Player player) {
        List<String> lore = new ArrayList<String>();

        ConfigCursor configCursor = new ConfigCursor(ArchHub.getInstance().getMenusConfig(), "settings");

        PlayerData playerData = ArchHub.getInstance().getPlayerDataManager().getPlayerData(player.getUniqueId());

        PlayerSettings playerSettings = playerData.getPlayerSettings();

        String enabled = Utils.translate(configCursor.getString("enabled"));
        String disabled = Utils.translate(configCursor.getString("disabled"));
        String unselected = Utils.translate(configCursor.getString("unselected"));

        switch (this.type) {
            case "toggle-scoreboard":
                lore.add((playerSettings.isScoreboardEnabled() ? enabled : unselected) + "&7Show the scoreboard");
                lore.add((!playerSettings.isScoreboardEnabled() ? disabled : unselected) + "&7Hide the scoreboard");

                break;
            case "toggle-tab-list":
                lore.add((playerSettings.isTabListEnabled() ? enabled : unselected) + "&7Show the tab list");
                lore.add((!playerSettings.isTabListEnabled() ? disabled : unselected) + "&7Hide the tab list");

                break;
            case "player-visibility":
                lore.add((playerSettings.isPlayerVisibilityEnabled() ? enabled : unselected) + "&7Show the players in the hub");
                lore.add((!playerSettings.isPlayerVisibilityEnabled() ? disabled : unselected) + "&7Hide the players in the hub");

                break;
        }

        lore.add(Utils.scoreboardBar);

        ItemStack itemStack = new ItemBuilder(this.material)
                .name(Utils.translate(configCursor.getString("name").replace("%setting-name%", this.name)))
                .lore(Utils.translate(lore))
                .amount(1)
                .durability(this.durability)
                .build();

        return itemStack;
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        PlayerData playerData = ArchHub.getInstance().getPlayerDataManager().getPlayerData(player.getUniqueId());

        switch (this.type) {
            case "toggle-scoreboard":
                playerData.getPlayerSettings().setScoreboardEnabled(!playerData.getPlayerSettings().isScoreboardEnabled());

                break;
            case "toggle-tab-list":
                playerData.getPlayerSettings().setTabListEnabled(!playerData.getPlayerSettings().isTabListEnabled());

                break;
            case "player-visibility":
                playerData.getPlayerSettings().setPlayerVisibilityEnabled(!playerData.getPlayerSettings().isPlayerVisibilityEnabled());

                ArchHub.getInstance().getPlayerDataManager().resetPlayerView();

                break;
        }

        player.updateInventory();
    }
}
