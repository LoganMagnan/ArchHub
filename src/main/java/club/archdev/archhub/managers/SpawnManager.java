package club.archdev.archhub.managers;

import club.archdev.archhub.ArchHub;
import club.archdev.archhub.utils.CustomLocation;
import club.archdev.archhub.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

@Getter
@Setter
public class SpawnManager {

    private ArchHub main = ArchHub.getInstance();

    private FileConfiguration config = this.main.getSettingsConfig().getConfig();

    private CustomLocation spawnLocation;
    private CustomLocation spawnMin;
    private CustomLocation spawnMax;

    public SpawnManager() {
        this.loadConfig();
    }

    private void loadConfig() {
        if (this.config.contains("spawn.location")) {
            try {
                this.spawnLocation = CustomLocation.stringToLocation(this.config.getString("spawn.location"));
                this.spawnMin = CustomLocation.stringToLocation(this.config.getString("spawn.min"));
                this.spawnMax = CustomLocation.stringToLocation(this.config.getString("spawn.max"));
            } catch (NullPointerException exception) {
                Bukkit.getConsoleSender().sendMessage(Utils.translate("&cSpawn min & max locations not found!"));
            }
        }
    }

    public void saveConfig() {
        this.config.set("spawn.location", CustomLocation.locationToString(this.spawnLocation));
        this.config.set("spawn.min", CustomLocation.locationToString(this.spawnMin));
        this.config.set("spawn.max", CustomLocation.locationToString(this.spawnMax));

        this.main.getSettingsConfig().save();
    }
}
