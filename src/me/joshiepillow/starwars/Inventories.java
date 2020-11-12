package me.joshiepillow.starwars;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

public class Inventories {
    static Inventory myInventory = Bukkit.createInventory(null, 54, "My custom Inventory!");
    private static Plugin plugin;
    private static NamespacedKey key;
    // The first parameter, is the inventory owner. I make it null to let everyone use it.
    //The second parameter, is the slots in a inventory. Must be a multiple of 9. Can be up to 54.
    //The third parameter, is the inventory name. This will accept chat colors

    static void init(Plugin plugin) {
        Inventories.plugin = plugin;
        key = new NamespacedKey(plugin, "my-key");
        myInventory.setItem(40, create(Material.ENDER_PEARL, "Ender Pearl", "INFORMATION"));
        myInventory.setItem(43, create(Material.GREEN_STAINED_GLASS, "Confirm", ""));
        myInventory.setItem(37, create(Material.RED_STAINED_GLASS, "Cancel", ""));
        fillAll(myInventory);
    }

    static void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked(); // The player that clicked the item
        ItemStack clicked = event.getCurrentItem(); // The item that was clicked
        Inventory inventory = event.getInventory(); // The inventory that was clicked in
        if (inventory.equals(myInventory)) { // The inventory is our custom Inventory
            if (clicked != null && clicked.getItemMeta() != null) {
                PersistentDataContainer container = clicked.getItemMeta().getPersistentDataContainer();
                if (container.has(key, PersistentDataType.INTEGER)) {
                    if (clicked.getType() == Material.RED_STAINED_GLASS) {
                        player.closeInventory();
                    } else if (clicked.getType() == Material.GREEN_STAINED_GLASS) {
                        player.closeInventory();
                        player.getInventory().addItem(new ItemStack(Material.ENDER_PEARL));
                    }
                    event.setCancelled(true);
                }
            }
        }
    }

    private static ItemStack create(Material material, String name, String lore, int amount) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(name);
        if (!lore.isEmpty()) {
            ArrayList<String> Lore = new ArrayList<>();
            Lore.add(lore);
            meta.setLore(Lore);
        }
        meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, 1);
        item.setItemMeta(meta);
        return item;
    }
    private static ItemStack create(Material material, String name, String lore) {
        return create(material, name, lore, 1);
    }
    private static ItemStack create(Material material, int amount) {
        return create(material, material.name(), "", amount);
    }
    private static ItemStack create(Material material) {
        return create(material, material.name(), "", 1);
    }
    private static void fillAll(Inventory inventory) {
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, create(Material.LIGHT_GRAY_STAINED_GLASS_PANE, " ", ""));
            }
        }
    }

}

