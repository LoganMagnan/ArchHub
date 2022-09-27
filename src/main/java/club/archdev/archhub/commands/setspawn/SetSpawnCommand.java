package club.archdev.archhub.commands.setspawn;

import club.archdev.archhub.ArchHub;
import club.archdev.archhub.commands.BaseCommand;
import club.archdev.archhub.commands.CommandInfo;
import club.archdev.archhub.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandInfo(name = "setspawn", permission = "archhub.admin", player = true)
public class SetSpawnCommand extends BaseCommand {

    private ArchHub main = ArchHub.getInstance();

    @Override
    public void executeAs(Player player, String[] args) {
        if (args.length == 0) {
            player.sendMessage(Utils.translate("&cUsage:"));
            player.sendMessage(Utils.translate("  &c/setspawn spawn"));
            player.sendMessage(Utils.translate("  &c/setspawn min"));
            player.sendMessage(Utils.translate("  &c/setspawn max"));
            player.sendMessage(Utils.translate("  &c/setspawn holograms"));

            return;
        }

        switch (args[0].toLowerCase()) {
            case "spawn":
                new SpawnCommand().executeAs(player, args);

                break;
            case "min":
                new MinCommand().executeAs(player, args);

                break;
            case "max":
                new MaxCommand().executeAs(player, args);

                break;
            case "holograms":
                break;
        }
    }

    @Override
    public void executeAs(CommandSender sender, String[] args) {

    }
}
