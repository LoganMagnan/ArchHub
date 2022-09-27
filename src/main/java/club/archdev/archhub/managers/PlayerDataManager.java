package club.archdev.archhub.managers;

import club.archdev.archhub.ArchHub;
import club.archdev.archhub.managers.items.HotBarItem;
import club.archdev.archhub.playerdata.PlayerData;
import club.archdev.archhub.utils.CustomLocation;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDataManager {

	private ArchHub main = ArchHub.getInstance();

	@Getter private final Map<UUID, PlayerData> players = new HashMap<>();

	public PlayerData getOrCreate(UUID uniqueId) {
		return this.players.computeIfAbsent(uniqueId, PlayerData::new);
	}

	public PlayerData getPlayerData(UUID uniqueId) {
		return this.players.getOrDefault(uniqueId, new PlayerData(uniqueId));
	}

	public Collection<PlayerData> getAllPlayers() {
		return this.players.values();
	}

	public void deletePlayer(UUID uniqueId) {
		this.getPlayers().remove(uniqueId);
	}

	public void sendToSpawnAndResetPlayer(Player player) {
		player.getInventory().clear();

		this.giveHubItemsToPlayer(player);

		if (!player.isOnline()) {
			return;
		}

		this.resetPlayerView();

		player.teleport(CustomLocation.stringToLocation(this.main.getSettingsConfig().getConfig().getString("spawn.location")).toBukkitLocation());
	}

	public void giveHubItemsToPlayer(Player player) {
		this.main.getHotBarManager().getHubItems().stream().filter(HotBarItem::isEnabled).forEach(item -> player.getInventory().setItem(item.getSlot(), item.getItemStack()));

		player.updateInventory();
	}

	public void resetPlayerView() {
		(new BukkitRunnable() {
			public void run() {
				PlayerDataManager.this.main.getServer().getOnlinePlayers().forEach(player -> {
					PlayerData playerData = PlayerDataManager.this.main.getPlayerDataManager().getPlayerData(player.getUniqueId());

					if (playerData.getPlayerSettings().isPlayerVisibilityEnabled()) {
						PlayerDataManager.this.main.getServer().getOnlinePlayers().forEach(playerOnline -> {
							player.showPlayer(playerOnline);
						});
					} else {
						PlayerDataManager.this.main.getServer().getOnlinePlayers().forEach(playerOnline -> {
							player.hidePlayer(playerOnline);
						});
					}
				});
			}
		}).runTaskAsynchronously(this.main);
	}
}
