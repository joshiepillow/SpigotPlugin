package me.joshiepillow.starwars;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Inventories {
    public static Inventory myInventory = Bukkit.createInventory(null, 54, "My custom Inventory!");
    // The first parameter, is the inventory owner. I make it null to let everyone use it.
    //The second parameter, is the slots in a inventory. Must be a multiple of 9. Can be up to 54.
    //The third parameter, is the inventory name. This will accept chat colors
    static {
        myInventory.setItem(0, new ItemStack(Material.DIRT, 1));
        myInventory.setItem(8, new ItemStack(Material.GOLD_BLOCK, 1));
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked(); // The player that clicked the item
        ItemStack clicked = event.getCurrentItem(); // The item that was clicked
        Inventory inventory = event.getInventory(); // The inventory that was clicked in
        if (inventory.equals(myInventory)) { // The inventory is our custom Inventory
            if (clicked.getType() == Material.DIRT) { // The item that the player clicked it dirt
                event.setCancelled(true); // Make it so the dirt is back in its original spot
                player.closeInventory(); // Closes there inventory
                player.getInventory().addItem(new ItemStack(Material.DIRT, 1)); // Adds dirt
            }
            player.sendMessage("RIGHT");
        } else {
            player.sendMessage("NOT RIGHT");
        }
    }
}
