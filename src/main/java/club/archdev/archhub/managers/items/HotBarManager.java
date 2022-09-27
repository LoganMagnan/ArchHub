package club.archdev.archhub.managers.items;

import club.archdev.archhub.ArchHub;
import club.archdev.archhub.utils.ItemUtil;
import club.archdev.archhub.utils.Utils;
import club.archdev.archhub.utils.config.ConfigCursor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class HotBarManager {

    private ArchHub main = ArchHub.getInstance();

    private ConfigCursor configCursor;

    private List<HotBarItem> hubItems = new ArrayList<HotBarItem>();

    public HotBarManager() {
        try {
            this.configCursor = new ConfigCursor(this.main.getHotBarConfig(), "hub");

            for (String hubItem : this.main.getHotBarConfig().getConfig().getConfigurationSection("hub").getKeys(false)) {
                this.hubItems.add(new HotBarItem(ItemUtil.createUnbreakableItem(
                        Material.valueOf(this.configCursor.getString(hubItem + ".material")),
                        Utils.translate(this.configCursor.getString(hubItem + ".name")),
                        this.configCursor.getInt(hubItem + ".amount"),
                        (short) this.configCursor.getInt(hubItem + ".data")),
                        this.configCursor.getInt(hubItem + ".slot"),
                        this.configCursor.getBoolean(hubItem + ".enabled"),
                        this.configCursor.getString(hubItem + ".action-type"))
                );
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
