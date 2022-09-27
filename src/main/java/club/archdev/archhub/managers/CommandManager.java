package club.archdev.archhub.managers;

import club.archdev.archhub.ArchHub;
import club.archdev.archhub.commands.SpawnCommand;
import club.archdev.archhub.commands.setspawn.SetSpawnCommand;

public class CommandManager {

    private ArchHub main = ArchHub.getInstance();

    public CommandManager() {
        this.registerCommands();
    }

    private void registerCommands() {
        this.main.getCommand("spawn").setExecutor(new SpawnCommand());
        this.main.getCommand("setspawn").setExecutor(new SetSpawnCommand());
    }
}
