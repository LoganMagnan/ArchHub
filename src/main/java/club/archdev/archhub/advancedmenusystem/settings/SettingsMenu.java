package club.archdev.archhub.advancedmenusystem.settings;

import club.archdev.archhub.ArchHub;
import club.archdev.archhub.advancedmenusystem.Button;
import club.archdev.archhub.advancedmenusystem.Menu;
import club.archdev.archhub.utils.Utils;
import club.archdev.archhub.utils.config.ConfigCursor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Map;

public class SettingsMenu extends Menu {

    private ArchHub main = ArchHub.getInstance();

    private ConfigCursor configCursor = new ConfigCursor(this.main.getMenusConfig(), "settings");

    @Override
    public String getTitle(Player player) {
        return Utils.translate(this.configCursor.getString("title"));
    }

    @Override
    public int getSize() {
        return this.configCursor.getInt("size") * 9;
    }

    @Override
    public boolean isUpdateAfterClick() {
        return true;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<Integer, Button>();
        buttons.put(
                this.main.getMenusConfig().getConfig().getInt("settings.settings.toggle-scoreboard.slot"),
                new SettingsButton(
                        this.main.getMenusConfig().getConfig().getString("settings.settings.toggle-scoreboard.name"),
                        this.main.getMenusConfig().getConfig().getStringList("settings.settings.toggle-scoreboard.lore"),
                        Material.valueOf(this.main.getMenusConfig().getConfig().getString("settings.settings.toggle-scoreboard.material")),
                        0,
                        "toggle-scoreboard",
                        "/togglescoreboard"
                )
        );

//        buttons.put(
//                this.main.getMenusConfig().getConfig().getInt("settings.settings.toggle-tab-list.slot"),
//                new SettingsButton(
//                        this.main.getMenusConfig().getConfig().getString("settings.settings.toggle-tab-list.name"),
//                        this.main.getMenusConfig().getConfig().getStringList("settings.settings.toggle-tab-list.lore"),
//                        Material.valueOf(this.main.getMenusConfig().getConfig().getString("settings.settings.toggle-tab-list.material")),
//                        0,
//                        "toggle-tab-list",
//                        "/toggletablist"
//                )
//        );

//        toggle-tab-list:
//        name: "&bToggle Tab List"
//        lore:
//        - "&8&m----------------------"
//                - "&7If enabled, you will"
//                - "&7be able to see the players on the tab list"
//        slot: 1
//        material: QUARTZ

        buttons.put(
                this.main.getMenusConfig().getConfig().getInt("settings.settings.player-visibility.slot"),
                new SettingsButton(
                        this.main.getMenusConfig().getConfig().getString("settings.settings.player-visibility.name"),
                        this.main.getMenusConfig().getConfig().getStringList("settings.settings.player-visibility.lore"),
                        Material.valueOf(this.main.getMenusConfig().getConfig().getString("settings.settings.player-visibility.material")),
                        0,
                        "player-visibility",
                        "/playervisibility"
                )
        );

        return buttons;
    }
}
