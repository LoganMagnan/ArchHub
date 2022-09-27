package club.archdev.archhub.normalmenusystem;

import club.archdev.archhub.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public abstract class Menu implements InventoryHolder {

    protected PlayerMenuUtil playerMenuUtil;
    protected Inventory inventory;
    protected ItemStack FILLER_GLASS = new ItemBuilder(Material.STAINED_GLASS_PANE).durability(7).name("").build();

    public Menu(PlayerMenuUtil playerMenuUtil) {
        this.playerMenuUtil = playerMenuUtil;
    }

    public abstract String getMenuName();

    public abstract int getSlots();

    public abstract void handleMenu(InventoryClickEvent event);

    public abstract void setMenuItems(Player player);

    public void open(Player player) {
        inventory = Bukkit.createInventory(this, getSlots(), getMenuName());

        this.setMenuItems(player);

        playerMenuUtil.getOwner().openInventory(inventory);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public void setFillerGlass(){
        for (int i = 0; i < this.getSlots(); i++) {
            if (this.inventory.getItem(i) == null){
                this.inventory.setItem(i, this.FILLER_GLASS);
            }
        }
    }

    public ItemStack makeItemStack(Material material, String displayName, String[] lore) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);

        itemMeta.setLore(Arrays.asList(lore));
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
}
