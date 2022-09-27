package club.archdev.archhub.managers;

import club.archdev.archhub.ArchHub;
import club.archdev.archhub.utils.CustomLocation;
import org.bukkit.Chunk;
import org.bukkit.scheduler.BukkitRunnable;

public class ChunkManager {

    private ArchHub main = ArchHub.getInstance();

    public ChunkManager() {
        new BukkitRunnable() {
            @Override
            public void run() {
                loadChunks();
            }
        }.runTaskLater(this.main, 1);
    }

    private void loadChunks() {
        CustomLocation spawnMin = this.main.getSpawnManager().getSpawnMin();
        CustomLocation spawnMax = this.main.getSpawnManager().getSpawnMax();

        if (spawnMin != null && spawnMax != null) {
            int spawnMinX = spawnMin.toBukkitLocation().getBlockX() >> 4;
            int spawnMinZ = spawnMin.toBukkitLocation().getBlockZ() >> 4;
            int spawnMaxX = spawnMax.toBukkitLocation().getBlockX() >> 4;
            int spawnMaxZ = spawnMax.toBukkitLocation().getBlockZ() >> 4;

            if (spawnMinX > spawnMaxX) {
                int lastSpawnMinX = spawnMinX;

                spawnMinX = spawnMaxX;
                spawnMaxX = lastSpawnMinX;
            }

            if (spawnMinZ > spawnMaxZ) {
                int lastSpawnMinZ = spawnMinZ;

                spawnMinZ = spawnMaxZ;
                spawnMaxZ = lastSpawnMinZ;
            }

            for (int x = spawnMinX; x <= spawnMaxX; x++) {
                for (int z = spawnMinZ; z <= spawnMaxZ; z++) {
                    Chunk chunk = spawnMin.toBukkitWorld().getChunkAt(x, z);

                    if (!chunk.isLoaded()) {
                        chunk.load();
                    }
                }
            }
        }
    }
}
