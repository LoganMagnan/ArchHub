package club.archdev.archhub.commands;

import club.archdev.archhub.ArchHub;
import club.archdev.archhub.utils.CustomLocation;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandInfo(name = "spawn", permission = "", player = true)
public class SpawnCommand extends BaseCommand {

    private ArchHub main = ArchHub.getInstance();

    @Override
    public void executeAs(Player player, String[] args) {
        player.teleport(CustomLocation.stringToLocation(this.main.getSettingsConfig().getConfig().getString("spawn.location")).toBukkitLocation());
    }

    @Override
    public void executeAs(CommandSender sender, String[] args) {

    }
}
