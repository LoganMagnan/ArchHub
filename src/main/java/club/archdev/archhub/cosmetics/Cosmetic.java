package club.archdev.archhub.cosmetics;

import club.archdev.archhub.ArchHub;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public abstract class Cosmetic {

    private ArchHub main = ArchHub.getInstance();

    private String name;

    private static Map<String, Cosmetic> names = new HashMap<String, Cosmetic>();

    public Cosmetic(String name) {
        this.name = name;

        names.put(this.name, this);
    }

    public abstract ItemStack getItemStack();

    public abstract void onItemStackClick(Player player, ItemStack itemStack);

    public static Cosmetic getByName(String name) {
        return names.get(name);
    }
}
