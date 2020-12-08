package me.joshiepillow.starwars.classes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Inventories extends SingleNameObject {
    private static NamespacedKey key;
    private Inventory inv;

    //Functions and stuff
    static Inventories create(String name, org.bukkit.inventory.Inventory inv) {
        if (isTaken(name, Inventories.class)) return null;
        else return new Inventories(name, inv);
    }
    private Inventories(String name, org.bukkit.inventory.Inventory inv) {
        super(name);
        this.inv = inv;
    }


    public static void init(Plugin plugin) {
        key = new NamespacedKey(plugin, "shop-key");
    }

    public static NamespacedKey getKey() {return key;}

    public static Inventories getByName(String name) {
        return (Inventories) getByName(name, Inventories.class);
    }

    public static void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked(); // The player that clicked the item
        ItemStack clicked = event.getCurrentItem(); // The item that was clicked
        //Inventory inventory = event.getInventory(); // The inventory that was clicked in
        if (clicked != null && clicked.getItemMeta() != null ) {
            PersistentDataContainer container = clicked.getItemMeta().getPersistentDataContainer();
            if (container.has(key, PersistentDataType.BYTE_ARRAY)) {
                //player.sendMessage("Got to checkpoint A");
                String string = new String(container.get(key, PersistentDataType.BYTE_ARRAY), StandardCharsets.UTF_8)
                        .replace("%name", player.getName());
                for (String datum : string.split("/"))
                    if (!datum.isEmpty())
                        Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), datum);
                event.setCancelled(true);
            }
        }
        PersistentDataContainer container = event.getCurrentItem().getItemMeta().getPersistentDataContainer();
        byte[] bytes = container.get(key, PersistentDataType.BYTE_ARRAY);
        assert bytes != null;
        String string = new String(bytes, StandardCharsets.UTF_8)
                .replace("%name", player.getName());
        for (String datum : string.split("/"))
            if (!datum.isEmpty())
                Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), datum);
            event.setCancelled(true);
    }

    public Inventory getInventory() {
        return inv;
    }



    //A bunch of overloaded helpful ItemStack constructors
    static ItemStack create(String onClick, Material material, String name, String lore, int amount) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(name);
        if (!lore.isEmpty()) {
            ArrayList<String> Lore = new ArrayList<>();
            Lore.add(lore);
            meta.setLore(Lore);
        }
        meta.getPersistentDataContainer().set(key, PersistentDataType.BYTE_ARRAY, onClick.getBytes());
        item.setItemMeta(meta);
        return item;
    }
    static ItemStack create(String onClick, Material material, String name, String lore) {
        return create(onClick, material, name, lore, 1);
    }
    static ItemStack create(String onClick, Material material, int amount) {
        return create(onClick, material, material.name(), "", amount);
    }
    static ItemStack create(String onClick, Material material) {
        return create(onClick, material, material.name(), "", 1);
    }
    static ItemStack create(Material material, int amount) {
        return create("", material, material.name(), "", amount);
    }
    static ItemStack create(Material material) {
        return create("", material, material.name(), "", 1);
    }
    static void fillAll(Inventory inventory) {
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, create("", Material.LIGHT_GRAY_STAINED_GLASS_PANE, " ", ""));
            }
        }
    }
}

