package club.archdev.archhub.listeners;

import club.archdev.archhub.ArchHub;
import club.archdev.archhub.advancedmenusystem.Button;
import club.archdev.archhub.advancedmenusystem.Menu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class ButtonListener implements Listener {

    private ArchHub main = ArchHub.getInstance();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onButtonPress(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        Menu menu = Menu.currentlyOpenedMenus.get(player.getName());

        if (menu != null) {
            if (event.getSlot() != event.getRawSlot()) {
                if ((event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT)) {
                    event.setCancelled(true);
                }

                return;
            }

            if (menu.getButtons().containsKey(event.getSlot())) {
                Button button = menu.getButtons().get(event.getSlot());

                boolean cancel = button.shouldCancel(player, event.getSlot(), event.getClick());

                if (!cancel && (event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT)) {
                    event.setCancelled(true);

                    if (event.getCurrentItem() != null) {
                        player.getInventory().addItem(event.getCurrentItem());
                    }
                } else {
                    event.setCancelled(cancel);
                }

                button.clicked(player, event.getSlot(), event.getClick(), event.getHotbarButton());

                if (Menu.currentlyOpenedMenus.containsKey(player.getName())) {
                    Menu newMenu = Menu.currentlyOpenedMenus.get(player.getName());

                    if (newMenu == menu && menu.isUpdateAfterClick()) {
                        menu.setClosedByMenu(true);

                        newMenu.openMenu(player);
                    }
                } else if (button.shouldUpdate(player, event.getSlot(), event.getClick())) {
                    menu.setClosedByMenu(true);
                    menu.openMenu(player);
                }

                if (event.isCancelled()) {
                    Bukkit.getScheduler().runTaskLater(this.main, player::updateInventory, 1L);
                }
            } else {
                if ((event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT || event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY) || event.getAction().equals(InventoryAction.HOTBAR_MOVE_AND_READD) || event.getAction().equals(InventoryAction.HOTBAR_SWAP))) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        Menu menu = Menu.currentlyOpenedMenus.get(player.getName());

        if (menu != null) {
            menu.onClose(player);

            Menu.currentlyOpenedMenus.remove(player.getName());
        }
    }
}
