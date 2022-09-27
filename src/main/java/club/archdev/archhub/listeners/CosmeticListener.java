package club.archdev.archhub.listeners;

import club.archdev.archhub.ArchHub;
import club.archdev.archhub.cosmetics.Cosmetic;
import club.archdev.archhub.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class CosmeticListener implements Listener {

    private ArchHub main = ArchHub.getInstance();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getCurrentItem() == null) {
            return;
        }

        if (event.getClickedInventory() == null) {
            return;
        }

        if (!event.getClickedInventory().getName().equalsIgnoreCase(Utils.translate(""))) {
            return;
        }

        event.setCancelled(true);

        Cosmetic cosmetic = Cosmetic.getByName(event.getCurrentItem().getType().name());

        if (cosmetic == null) {
            return;
        }

        cosmetic.onItemStackClick(player, event.getCurrentItem());
    }
}
