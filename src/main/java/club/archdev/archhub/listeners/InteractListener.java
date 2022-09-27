package club.archdev.archhub.listeners;

import club.archdev.archhub.ArchHub;
import club.archdev.archhub.advancedmenusystem.settings.SettingsMenu;
import club.archdev.archhub.managers.items.HotBarItem;
import club.archdev.archhub.normalmenusystem.menu.HubSelectorMenu;
import club.archdev.archhub.normalmenusystem.menu.ServerSelectorMenu;
import club.archdev.archhub.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractListener implements Listener {

    private ArchHub main = ArchHub.getInstance();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if (!event.hasItem()) {
            return;
        }

        HotBarItem hotBarItem = Utils.getHotBarItemByItemStack(player.getItemInHand());

        if (hotBarItem == null) {
            return;
        }

        if (hotBarItem.getActionType() == null) {
            return;
        }

        switch (hotBarItem.getActionType()) {
            case OPEN_SERVER_SELECTOR:
                new ServerSelectorMenu(this.main.getPlayerMenuUtil(player)).open(player);

                break;
            case OPEN_SETTINGS_SELECTOR:
                new SettingsMenu().openMenu(player);

                break;
            case OPEN_HUB_SELECTOR:
                new HubSelectorMenu(this.main.getPlayerMenuUtil(player)).open(player);

                break;
        }
    }
}
