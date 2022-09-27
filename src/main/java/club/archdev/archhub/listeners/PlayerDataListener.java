package club.archdev.archhub.listeners;

import club.archdev.archhub.ArchHub;
import club.archdev.archhub.playerdata.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

public class PlayerDataListener implements Listener {

	private ArchHub main = ArchHub.getInstance();

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
		Player player = Bukkit.getPlayer(event.getUniqueId());

		if (player != null) {
			if (player.isOnline()) {
				event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
				event.setKickMessage("§cYou tried to login too quickly after disconnecting.\n§cTry again in a few seconds.");

				this.main.getServer().getScheduler().runTask(this.main, () -> player.kickPlayer("§cDuplicate Login"));

				return;
			}

			PlayerData playerData = this.main.getPlayerDataManager().getPlayerData(player.getUniqueId());
		}
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPlayerLogin(PlayerLoginEvent event) {
		PlayerData playerData = this.main.getPlayerDataManager().getOrCreate(event.getPlayer().getUniqueId());

		if (playerData == null) {
			event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
			event.setKickMessage("§cAn error has occurred while loading your profile. Please reconnect.");

			return;
		}

		if (!playerData.isLoaded()) {
			event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
			event.setKickMessage("§cAn error has occurred while loading your profile. Please reconnect.");
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		event.setQuitMessage(null);

		Player player = event.getPlayer();

		PlayerData playerData = this.main.getPlayerDataManager().getPlayerData(player.getUniqueId());

		this.handleLeave(player);
		this.handleDataSave(playerData);
	}

	@EventHandler
	public void onPlayerKick(PlayerKickEvent event) {
		event.setLeaveMessage(null);

		Player player = event.getPlayer();

		PlayerData playerData = this.main.getPlayerDataManager().getPlayerData(player.getUniqueId());

		this.handleLeave(player);
		this.handleDataSave(playerData);
	}

	private void handleLeave(Player player) {

	}

	private void handleDataSave(PlayerData playerData) {
		if (playerData != null) {
			this.main.getPlayerDataManager().deletePlayer(playerData.getUniqueId());
		}
	}
}
