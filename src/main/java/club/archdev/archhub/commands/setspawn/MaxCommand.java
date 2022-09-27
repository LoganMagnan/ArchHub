package club.archdev.archhub.commands.setspawn;

import club.archdev.archhub.ArchHub;
import club.archdev.archhub.commands.BaseCommand;
import club.archdev.archhub.commands.CommandInfo;
import club.archdev.archhub.utils.CustomLocation;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

@CommandInfo(name = "setspawn", permission = "archhub.admin", player = true)
public class MaxCommand extends BaseCommand {

    private ArchHub main = ArchHub.getInstance();

    @Override
    public void executeAs(Player player, String[] args) {
        this.main.getSpawnManager().setSpawnLocation(CustomLocation.fromBukkitLocation(player.getLocation()));

        this.saveLocation(player, "spawn.max");

        player.sendMessage(ChatColor.GREEN + "Successfully set the max location.");
    }

    @Override
    public void executeAs(CommandSender sender, String[] args) {

    }

    private void saveLocation(Player player, String location) {
        FileConfiguration config = this.main.getSettingsConfig().getConfig();
        config.set(location, CustomLocation.locationToString(CustomLocation.fromBukkitLocation(player.getLocation())));

        this.main.getSettingsConfig().save();
    }
}
