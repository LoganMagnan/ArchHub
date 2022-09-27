package club.archdev.archhub.commands;

import club.archdev.archhub.ArchHub;
import club.archdev.archhub.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class BaseCommand implements CommandExecutor {

    private ArchHub main = ArchHub.getInstance();

    private CommandInfo commandInfo;

    public BaseCommand() {
        this.commandInfo = this.getClass().getDeclaredAnnotation(CommandInfo.class);
    }

    public abstract void executeAs(Player player, String[] args);

    public abstract void executeAs(CommandSender sender, String[] args);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!this.commandInfo.permission().equals("")) {
            if (!sender.hasPermission(this.commandInfo.permission())) {
                sender.sendMessage(Utils.translate("&cNo permission"));

                return true;
            }
        }

        if (this.commandInfo.player()) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.translate("&cYou have to be a player to do this"));

                return true;
            }

            Player player = (Player) sender;

            this.executeAs(player, args);

            return true;
        }

        this.executeAs(sender, args);

        return true;
    }
}