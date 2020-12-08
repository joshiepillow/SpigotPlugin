package me.joshiepillow.starwars.classes;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.nio.charset.StandardCharsets;

public class Force {
    private static NamespacedKey key;
    public static void init(Plugin plugin) {
        key = new NamespacedKey(plugin, "force-key");
    }
    public static NamespacedKey getKey() {
        return key;
    }
    public static void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked(); // The player that clicked the item
        PersistentDataContainer container = event.getCurrentItem().getItemMeta().getPersistentDataContainer();
        byte[] bytes = container.get(key, PersistentDataType.BYTE_ARRAY);
        assert bytes != null;
        String string = new String(bytes, StandardCharsets.UTF_8);
        if (string.equals("")) {
            Inventory inv = Bukkit.createInventory(null, 9, "Force");
            player.openInventory(inv);
        }
    }
}
