package club.archdev.archhub.managers.items;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class HotBarItem {

    private ItemStack itemStack;
    private int slot;
    private boolean enabled;
    private ActionType actionType;

    private List<HotBarItem> hubItems = new ArrayList<HotBarItem>();

    public HotBarItem(ItemStack itemStack, int slot, boolean enabled, String actionType) {
        this.itemStack = itemStack;
        this.slot = slot;
        this.enabled = enabled;
        this.actionType = ActionType.valueOf(actionType);
        this.hubItems.add(this);
    }

    public HotBarItem getItemByItemStack(ItemStack itemStack) {
        return this.hubItems.stream().filter(item -> item.getItemStack().isSimilar(itemStack)).findFirst().orElse(null);
    }
}
