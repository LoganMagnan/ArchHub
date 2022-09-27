package club.archdev.archhub.advancedmenusystem.buttons;

import club.archdev.archhub.advancedmenusystem.Button;
import club.archdev.archhub.advancedmenusystem.Menu;
import club.archdev.archhub.utils.ItemBuilder;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

@AllArgsConstructor
public class BackButton extends Button {

	private Menu back;

	@Override
	public ItemStack getButtonItem(Player player) {
		return new ItemBuilder(Material.BED)
				.name("&cGo Back")
				.lore(Arrays.asList(" ", "&9Click to go back."))
				.hideFlags()
				.build();
	}

	@Override
	public void clicked(Player player, int i, ClickType clickType, int hb) {
		this.back.openMenu(player);
		playNeutral(player);
	}
}
