package club.archdev.archhub.utils;

import club.archdev.archhub.ArchHub;
import club.archdev.archhub.managers.items.HotBarItem;
import club.archdev.archhub.utils.config.ConfigCursor;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    private static ArchHub main = ArchHub.getInstance();

    public static String scoreboardBar = ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "----------------------";
    public static String chatBar = ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "--------------------------------------------";

    public static ConfigCursor settingsMenu = new ConfigCursor(main.getMenusConfig(), "settings.settings");

    public static String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> translate(List<String> lines) {
        List<String> strings = new ArrayList<>();
        for (String line : lines) {
            strings.add(ChatColor.translateAlternateColorCodes('&', line));
        }
        return strings;
    }

    public static List<String> translate(String[] lines) {
        List<String> strings = new ArrayList<>();
        for (String line : lines) {
            if (line != null) {
                strings.add(ChatColor.translateAlternateColorCodes('&', line));
            }
        }
        return strings;
    }

    public static List<Block> getBlocks(CustomLocation loc1, CustomLocation loc2, World w) {

        //First of all, we create the list:
        List<Block> blocks = new ArrayList<Block>();

        //Next we will name each coordinate
        int x1 = loc1.toBukkitLocation().getBlockX();
        int y1 = loc1.toBukkitLocation().getBlockY();
        int z1 = loc1.toBukkitLocation().getBlockZ();

        int x2 = loc2.toBukkitLocation().getBlockX();
        int y2 = loc2.toBukkitLocation().getBlockY();
        int z2 = loc2.toBukkitLocation().getBlockZ();

        //Then we create the following integers
        int xMin, yMin, zMin;
        int xMax, yMax, zMax;
        int x, y, z;

        //Now we need to make sure xMin is always lower then xMax
        if(x1 > x2){ //If x1 is a higher number then x2
            xMin = x2;
            xMax = x1;
        }else{
            xMin = x1;
            xMax = x2;
        }

        //Same with Y
        if(y1 > y2){
            yMin = y2;
            yMax = y1;
        }else{
            yMin = y1;
            yMax = y2;
        }

        //And Z
        if(z1 > z2){
            zMin = z2;
            zMax = z1;
        }else{
            zMin = z1;
            zMax = z2;
        }

        //Now it's time for the loop
        for(x = xMin; x <= xMax; x ++){
            for(y = yMin; y <= yMax; y ++){
                for(z = zMin; z <= zMax; z ++){
                    Block b = new Location(w, x, y, z).getBlock();
                    blocks.add(b);
                }
            }
        }

        //And last but not least, we return with the list
        return blocks;
    }

    public static boolean getPlayer(Player p, CustomLocation loc1, CustomLocation loc2) {
        double x1 = loc1.toBukkitLocation().getX();
        double y1 = loc1.toBukkitLocation().getY();
        double z1 = loc1.toBukkitLocation().getZ();

        double x2 = loc2.toBukkitLocation().getX();
        double y2 = loc2.toBukkitLocation().getY();
        double z2 = loc2.toBukkitLocation().getZ();

        return (p.getLocation().getX() > x1) && (p.getLocation().getY() > y1) && (p.getLocation().getZ() > z1) && (p.getLocation().getX() < x2) && (p.getLocation().getY() < y2) && (p.getLocation().getZ() < z2);
    }

    public static String getSettingName(String name) {
        return translate(settingsMenu.getString(name + ".name"));
    }

    public static List<String> getSettingLore(String name) {
        List<String> lore = new ArrayList<String>();

        for (String string : settingsMenu.getStringList(name + ".lore")) {
            lore.add(translate(string));
        }

        return lore;
    }

    public static Material getSettingMaterial(String name) {
        return Material.valueOf(settingsMenu.getString(name + ".material"));
    }

    public static int getSettingSlot(String name) {
        return settingsMenu.getInt(name + ".slot");
    }

    public static HotBarItem getHotBarItemByItemStack(ItemStack itemStack) {
        return main.getHotBarManager().getHubItems().stream().filter(hotBarItem -> hotBarItem.getItemStack().isSimilar(itemStack)).findFirst().orElse(null);
    }

    public static boolean isNumeric(String string) {
        return regexNumeric(string).length() == 0;
    }

    public static String regexNumeric(String string) {
        return string.replaceAll("[0-9]", "").replaceFirst("\\.", "");
    }
}
