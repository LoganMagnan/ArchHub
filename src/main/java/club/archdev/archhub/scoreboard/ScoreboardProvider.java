package club.archdev.archhub.scoreboard;

import aether.scoreboard.Board;
import aether.scoreboard.BoardAdapter;
import aether.scoreboard.cooldown.BoardCooldown;
import club.archdev.archhub.ArchHub;
import club.archdev.archhub.playerdata.PlayerData;
import club.archdev.archhub.utils.BungeeUtils;
import club.archdev.archhub.utils.Utils;
import me.signatured.ezqueuespigot.EzQueueAPI;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ScoreboardProvider implements BoardAdapter {

    private ArchHub main = ArchHub.getInstance();

    @Override
    public String getTitle(Player player) {
        return Utils.translate(this.main.getScoreboardConfig().getConfig().getString("SCOREBOARD.TITLE"));
    }

    @Override
    public List<String> getScoreboard(Player player, Board board, Set<BoardCooldown> cooldowns) {
        PlayerData playerData = this.main.getPlayerDataManager().getPlayerData(player.getUniqueId());

        if (playerData == null) {
            return null;
        }

        if (!playerData.getPlayerSettings().isScoreboardEnabled()) {
            return null;
        }

        if (EzQueueAPI.getQueue(player) != null) {
            List<String> lines = new ArrayList<>();

            String rank = this.main.getChat().getGroupPrefix(player.getWorld(), this.main.getChat().getPrimaryGroup(player));

            Object entityPlayer;

            int ping = 0;

            try {
                entityPlayer = player.getClass().getMethod("getHandle").invoke(player);

                ping = (int) entityPlayer.getClass().getField("ping").get(entityPlayer);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException exception) {
                exception.printStackTrace();
            }

            DecimalFormat decimalFormat = new DecimalFormat("##.##");

            for (String string : this.main.getScoreboardConfig().getConfig().getStringList("SCOREBOARD.LINES.QUEUE")) {
                lines.add(
                        string.replace("%online%", String.valueOf(BungeeUtils.getPlayerCount(null)))
                                .replace("%rank%", rank)
                                .replace("%ping%", String.valueOf(ping))
                                .replace("%queue-name%", EzQueueAPI.getQueue(player))
                                .replace("%queue-position%", String.valueOf(EzQueueAPI.getPosition(player)))
                                .replace("%queue-size%", String.valueOf(EzQueueAPI.getQueueSize(EzQueueAPI.getQueue(player))))
                );
            }

            return Utils.translate(lines);
        }

        switch (playerData.getPlayerState()) {
            case SPAWN:
                return this.getSpawnScoreboard(player, playerData);
        }

        return null;
    }

    @Override
    public void onScoreboardCreate(Player player, Scoreboard scoreboard) {

    }

    private List<String> getSpawnScoreboard(Player player, PlayerData playerData) {
        List<String> lines = new ArrayList<>();

        String rank = this.main.getChat().getGroupPrefix(player.getWorld(), this.main.getChat().getPrimaryGroup(player));

        Object entityPlayer;

        int ping = 0;

        try {
            entityPlayer = player.getClass().getMethod("getHandle").invoke(player);

            ping = (int) entityPlayer.getClass().getField("ping").get(entityPlayer);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException exception) {
            exception.printStackTrace();
        }

        DecimalFormat decimalFormat = new DecimalFormat("##.##");

        for (String string : this.main.getScoreboardConfig().getConfig().getStringList("SCOREBOARD.LINES.DEFAULT")) {
            lines.add(
                    string.replace("%online%", String.valueOf(BungeeUtils.getPlayerCount(null)))
                            .replace("%rank%", rank)
                            .replace("%ping%", String.valueOf(ping))
            );
        }

        return Utils.translate(lines);
    }
}
