package club.archdev.archhub.normalmenusystem.menu;

import club.archdev.archhub.ArchHub;
import club.archdev.archhub.normalmenusystem.Menu;
import club.archdev.archhub.normalmenusystem.PlayerMenuUtil;
import club.archdev.archhub.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ServerSelectorMenu extends Menu {

    private ArchHub main = ArchHub.getInstance();

    public ServerSelectorMenu(PlayerMenuUtil playerMenuUtil) {
        super(playerMenuUtil);
    }

    @Override
    public String getMenuName() {
        return Utils.translate(this.main.getHotBarConfig().getConfig().getString("hub.server-selector.name"));
    }

    @Override
    public int getSlots() {
        return this.main.getHotBarConfig().getConfig().getInt("hub.server-selector.inventory-size");
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getView().getTitle().equalsIgnoreCase(Utils.translate(this.main.getHotBarConfig().getConfig().getString("hub.server-selector.name")))) {
            ItemStack itemStack = new ItemStack(event.getCurrentItem().getType());
            assert false;
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(event.getCurrentItem().getItemMeta().getDisplayName());
            itemMeta.setLore(event.getCurrentItem().getItemMeta().getLore());
            itemStack.setItemMeta(itemMeta);
            if (event.getCurrentItem().equals(itemStack)) {
                for (int i = 0; i < this.main.getHotBarConfig().getConfig().getConfigurationSection("hub.server-selector.items").getKeys(false).size(); i++) {
                    ItemStack itemStack1 = new ItemStack(Objects.requireNonNull(Material.getMaterial(Objects.requireNonNull(this.main.getHotBarConfig().getConfig().getString("hub.server-selector.items." + i + ".material")))));
                    assert false;
                    ItemMeta itemMeta1 = itemStack1.getItemMeta();
                    itemMeta1.setDisplayName(Utils.translate(this.main.getHotBarConfig().getConfig().getString(
                            "hub.server-selector.items." + i + ".title")));
                    List<String> lore = new ArrayList<>();
                    for (String string : this.main.getHotBarConfig().getConfig().getStringList("hub.server-selector.items." + i + ".lore")) {
                        lore.add(Utils.translate(string));
                    }
                    itemMeta1.setLore(lore);
                    itemStack1.setItemMeta(itemMeta1);
                    if (itemStack.equals(itemStack1)) {
                        player.chat(this.main.getHotBarConfig().getConfig().getString("hub.server-selector.items." + i + ".command"));
                        return;
                    }
                }
            }
        }
    }

    @Override
    public void setMenuItems(Player player) {
        for (int i = 0; i < this.main.getHotBarConfig().getConfig().getConfigurationSection("hub.server-selector.items").getKeys(false).size(); i++) {
            ItemStack itemStack = new ItemStack(Objects.requireNonNull(Material.getMaterial(Objects.requireNonNull(this.main.getHotBarConfig().getConfig().getString("hub.server-selector.items." + i + ".material")))));
            assert false;
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(Utils.translate(this.main.getHotBarConfig().getConfig().getString("hub.server-selector.items." + i + ".title")));
            List<String> lore = new ArrayList<>();
            for (String string : this.main.getHotBarConfig().getConfig().getStringList("hub.server-selector.items." + i + ".lore")) {
                lore.add(Utils.translate(string));
            }
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
            inventory.setItem(this.main.getHotBarConfig().getConfig().getInt("hub.server-selector.items." + i + ".slot"), itemStack);
        }
    }
}
