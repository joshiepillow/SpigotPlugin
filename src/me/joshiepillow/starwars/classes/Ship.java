/*package me.joshiepillow.starwars.classes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Ship {
    private static List<Player> onShips = new ArrayList<>();
    private boolean isSummoned;
    public boolean summon() {
        return false;
    }
    public static boolean isOnShip(Player player) {
        return onShips.contains(player);
    }
    public static NamespacedKey getKey() {return null;}
    public static void onInventoryClick(InventoryClickEvent event) {
        if (event.getClick() == ClickType.RIGHT && event.getClick() == ClickType.SHIFT_RIGHT) {
            Player player = (Player) event.getWhoClicked(); // The player that clicked the item
            Inventory inv = Bukkit.createInventory(null, 9, "Ships");
            int max = (int) (Math.random()*9);
            for (int i = 0; i <= max; i++) {
                ItemStack item = new ItemStack(Material.BLACK_BED, 64);
                inv.setItem(i, item);
            }
            player.openInventory(inv);
        }
    }
    public static void onMovement(PlayerMoveEvent event) {
        double x = 0;
        double y = 0;
        double z = 0;

        //if (Ship.isOnShip(event.getPlayer())) {
        if (true) {
            if (event.getPlayer().isSneaking()) {
                event.getPlayer().setVelocity(event.getPlayer().getLocation().getDirection().multiply(0.3));
            } else {
                event.getPlayer().setVelocity(event.getPlayer().getLocation().getDirection());
            }
        }
    }
}
*/