package club.archdev.archhub.normalmenusystem;

import club.archdev.archhub.utils.HeadUtils;
import club.archdev.archhub.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class PaginatedMenu extends Menu {

    protected int page = 0;
    protected int maxItemsPerPage = 28;
    protected int index = 0;

    public PaginatedMenu(PlayerMenuUtil playerMenuUtil) {
        super(playerMenuUtil);
    }

    public void addMenuBorder() {
        ItemStack previousPageItemStack = HeadUtils.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ==");

        ItemMeta previousPageItemMeta = previousPageItemStack.getItemMeta();
        previousPageItemMeta.setDisplayName(Utils.translate("&ePrevious Page"));

        previousPageItemStack.setItemMeta(previousPageItemMeta);

        this.inventory.setItem(48, previousPageItemStack);
        this.inventory.setItem(49, makeItemStack(Material.BARRIER, ChatColor.DARK_RED + "Close", new String[]{""}));

        ItemStack nextPageItemStack = HeadUtils.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTliZjMyOTJlMTI2YTEwNWI1NGViYTcxM2FhMWIxNTJkNTQxYTFkODkzODgyOWM1NjM2NGQxNzhlZDIyYmYifX19");

        ItemMeta nextPageItemMeta = nextPageItemStack.getItemMeta();
        nextPageItemMeta.setDisplayName(Utils.translate("&eNext Page"));

        nextPageItemStack.setItemMeta(nextPageItemMeta);

        this.inventory.setItem(50, nextPageItemStack);

        for (int i = 0; i < 10; i++) {
            if (this.inventory.getItem(i) == null) {
                this.inventory.setItem(i, this.FILLER_GLASS);
            }
        }

        this.inventory.setItem(17, this.FILLER_GLASS);
        this.inventory.setItem(18, this.FILLER_GLASS);
        this.inventory.setItem(26, this.FILLER_GLASS);
        this.inventory.setItem(27, this.FILLER_GLASS);
        this.inventory.setItem(35, this.FILLER_GLASS);
        this.inventory.setItem(36, this.FILLER_GLASS);

        for (int i = 44; i < 54; i++) {
            if (this.inventory.getItem(i) == null) {
                this.inventory.setItem(i, this.FILLER_GLASS);
            }
        }
    }

    public int getMaxItemsPerPage() {
        return maxItemsPerPage;
    }
}