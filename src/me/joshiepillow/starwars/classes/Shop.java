package me.joshiepillow.starwars.classes;

import me.joshiepillow.CustomItems.CustomItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Shop {

    //Now the definitions
    public static InventoryWrapper MAIN;
    public static InventoryWrapper GUNS;
    public static InventoryWrapper WEAPONS;
    public static InventoryWrapper LIGHTSABERS;
    public static InventoryWrapper ARMOR;

    private static ItemStack openInventoryBuilder(Material m, InventoryWrapper i) {
        CustomItems c = new CustomItems();
        c.onInventoryClickEvent = (event) -> {
            try {
                event.getWhoClicked().openInventory(i.get());
                event.setCancelled(true);
                ((Player) event.getWhoClicked()).updateInventory();
            } catch (Exception e){}
        };
        return c.setPersistentData(new ItemStack(m));
    }
    private static ItemStack openInventoryBuilder(Material m, InventoryWrapper i, String name) {
        ItemStack item = openInventoryBuilder(m, i);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack shopCloseBuilder(Material m) {
        CustomItems c = new CustomItems();
        c.onInventoryClickEvent = (event) -> {
            event.getWhoClicked().closeInventory();
            event.setCancelled(true);
            ((Player)event.getWhoClicked()).updateInventory();
        };
        return c.setPersistentData(new ItemStack(m));
    }
    private static ItemStack shopCloseBuilder(Material m, String name) {
        ItemStack item = shopCloseBuilder(m);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack immovableBuilder(Material m) {
        CustomItems c = new CustomItems();
        c.onInventoryClickEvent = (event) -> {
            event.setCancelled(true);
            ((Player)event.getWhoClicked()).updateInventory();
        };
        return c.setPersistentData(new ItemStack(m));
    }
    private static ItemStack immovableBuilder(Material m, String name) {
        ItemStack item = immovableBuilder(m);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack clickBuyBuilder(Product p) {
        CustomItems c = new CustomItems();
        c.onInventoryClickEvent = (event) -> {
            event.getWhoClicked().closeInventory();
            BountyHunter h = BountyHunter.getByUUID(event.getWhoClicked().getUniqueId());
            assert h != null;
            if (h.changeBal(-p.getCost())) {
                event.getWhoClicked().sendMessage("Success, you have bought a "+p.getName()+" for "+p.getCost()+"C.");
                event.getWhoClicked().getInventory().addItem(p.getItem());
            } else {
                event.getWhoClicked().sendMessage("You cannot afford that.");
            }
            event.setCancelled(true);
            ((Player)event.getWhoClicked()).updateInventory();
        };

        Material m = p.getItem().getType();
        ItemMeta pmeta = p.getItem().getItemMeta();

        ItemStack i = new ItemStack(m);
        ItemMeta meta = i.getItemMeta();
        meta.setDisplayName(pmeta.getDisplayName());
        meta.setLore(new ArrayList<String>(){{
            add("§6Cost: "+p.getCost());
            List<String> s = pmeta.getLore();
            if (s != null) addAll(s);
        }});
        i.setItemMeta(meta);
        return c.setPersistentData(i);
    }

    private static void fillAll(Inventory inventory) {
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, immovableBuilder(Material.LIGHT_GRAY_STAINED_GLASS_PANE, " "));
            }
        }
    }

    public static void setItems() {
        MAIN = new InventoryWrapper();
        GUNS = new InventoryWrapper();
        WEAPONS = new InventoryWrapper();
        LIGHTSABERS = new InventoryWrapper();
        ARMOR = new InventoryWrapper();


        Inventory inv;

        inv = Bukkit.createInventory(null, 54, "HorizonShop");
        inv.setItem(4, openInventoryBuilder(Material.AZURE_BLUET, GUNS, "§7Guns"));
        inv.setItem(10, openInventoryBuilder(Material.NETHERITE_AXE, WEAPONS, "§4Weapons"));
        inv.setItem(16, openInventoryBuilder(Material.IRON_AXE, LIGHTSABERS, "§1Lightsabers"));
        inv.setItem(22, openInventoryBuilder(Material.LEATHER_HELMET, ARMOR, "§6Armor"));
        inv.setItem(40, shopCloseBuilder(Material.RED_STAINED_GLASS, "Close"));
        fillAll(inv);
        MAIN.set(inv);

        inv = Bukkit.createInventory(null, 54, "HorizonShop");
        /*inv.setItem(11, clickBuyBuilder(Products.A180, Material.BLUE_ORCHID));
        inv.setItem(12, Inventories.create("shop.buy %name A280", Material.ALLIUM, "§7A280", "§6Cost: "+Products.A280.getCost()));
        inv.setItem(13, Inventories.create("shop.buy %name A280C", Material.AZURE_BLUET, "§7A280C", "§6Cost: "+Products.A280C.getCost()));
        inv.setItem(14, Inventories.create("shop.buy %name DL18", Material.RED_TULIP, "§7DL18", "§6Cost: "+Products.DL18.getCost()));
        inv.setItem(15, Inventories.create("shop.buy %name K16_Bryar", Material.ORANGE_TULIP, "§7K16 Bryar", "§6Cost: "+Products.K16_Bryar.getCost()));
        inv.setItem(20, Inventories.create("shop.buy %name SE14C", Material.POPPY, "§7SE14C", "§6Cost: "+Products.SE14C.getCost()));
        inv.setItem(21, Inventories.create("shop.buy %name A295", Material.DANDELION, "§7A295", "§6Cost: "+Products.A295.getCost()));
        inv.setItem(22, Inventories.create("shop.buy %name TL50", Material.OXEYE_DAISY, "§7TL50", "§6Cost: "+Products.TL50.getCost()));
        inv.setItem(23, Inventories.create("shop.buy %name Cycler_Rifle", Material.PINK_TULIP, "§7Cycler Rifle", "§6Cost: "+Products.Cycler_Rifle.getCost()));
        inv.setItem(24, Inventories.create("shop.buy %name RelbyV10", Material.WHITE_TULIP, "§7RelbyV10", "§6Cost: "+Products.RelbyV10.getCost()));
         */
        inv.setItem(40, openInventoryBuilder(Material.RED_STAINED_GLASS, MAIN, "Back"));
        fillAll(inv);
        GUNS.set(inv);

        inv = Bukkit.createInventory(null, 54, "HorizonShop");
        /*
        inv.setItem(10, Inventories.create("shop.buy %name Gaderffii", Material.WOODEN_SWORD, "§4Gaderffii", "§6Cost: "+Products.Gaderffii.getCost()));
        inv.setItem(11, Inventories.create("shop.buy %name Gammorean_Axe", Material.WOODEN_AXE, "§4Gammorean Axe", "§6Cost: "+Products.Gammorean_Axe.getCost()));
        inv.setItem(13, Inventories.create("shop.buy %name Mythosaur_Axe", Material.STONE_SWORD, "§4Mythosaur Axe", "§6Cost: "+Products.Mythosaur_Axe.getCost()));
        inv.setItem(15, Inventories.create("shop.buy %name Kyuso_Petar", Material.STONE_AXE, "§4Kyuso Petar", "§6Cost: "+Products.Kyuso_Petar.getCost()));
        inv.setItem(19, Inventories.create("shop.buy %name Yellow_Electro_Staff", Material.GOLDEN_AXE, "§4Yellow Electro Staff", "§6Cost: "+Products.Yellow_Electro_Staff.getCost()));
        inv.setItem(20, Inventories.create("shop.buy %name Purple_Electro_Staff", Material.GOLDEN_SWORD, "§4Purple Electro Staff", "§6Cost: "+Products.Purple_Electro_Staff.getCost()));
        inv.setItem(22, Inventories.create("shop.buy %name Vibro_Blade_1", Material.NETHERITE_SWORD, "§4Vibro Blade #1", "§6Cost: "+Products.Vibro_Blade_1.getCost()));
        inv.setItem(23, Inventories.create("shop.buy %name Vibro_Blade_2", Material.DIAMOND_AXE, "§4Vibro Blade #2", "§6Cost: "+Products.Vibro_Blade_2.getCost()));
        inv.setItem(24, Inventories.create("shop.buy %name Vibro_Blade_3", Material.NETHERITE_AXE, "§4Vibro Blade #3", "§6Cost: "+Products.Vibro_Blade_3.getCost()));
        */
        inv.setItem(40, openInventoryBuilder(Material.RED_STAINED_GLASS, MAIN, "Back"));
        fillAll(inv);
        WEAPONS.set(inv);

        inv = Bukkit.createInventory(null, 54, "HorizonShop");
        inv.setItem(13, clickBuyBuilder(Products.Top_Hilt));
        inv.setItem(14, clickBuyBuilder(Products.Activation_Stud));
        inv.setItem(21, clickBuyBuilder(Products.Blade_Emitter));
        inv.setItem(31, clickBuyBuilder(Products.Bottom_Hilt));
        inv.setItem(40, openInventoryBuilder(Material.RED_STAINED_GLASS, MAIN, "Back"));
        fillAll(inv);
        LIGHTSABERS.set(inv);

        inv = Bukkit.createInventory(null, 54, "HorizonShop");
        /*
        inv.setItem(2, Inventories.create("shop.buy %name Jedi_Cap", Material.LEATHER_HELMET, "§6Jedi Cap", "§6Cost: "+Products.Jedi_Cap.getCost()));
        inv.setItem(11, Inventories.create("shop.buy %name Jedi_Tunic", Material.LEATHER_CHESTPLATE, "§6Jedi Tunic", "§6Cost: "+Products.Jedi_Tunic.getCost()));
        inv.setItem(20, Inventories.create("shop.buy %name Jedi_Pants", Material.LEATHER_LEGGINGS, "§6Jedi Pants", "§6Cost: "+Products.Jedi_Pants.getCost()));
        inv.setItem(29, Inventories.create("shop.buy %name Jedi_Boots", Material.LEATHER_BOOTS, "§6Jedi Boots", "§6Cost: "+Products.Jedi_Boots.getCost()));
        inv.setItem(3, Inventories.create("shop.buy %name CG_Helmet", Material.CHAINMAIL_HELMET, "§6CG Helmet", "§6Cost: "+Products.CG_Helmet.getCost()));
        inv.setItem(12, Inventories.create("shop.buy %name CG_Chestplate", Material.CHAINMAIL_CHESTPLATE, "§6CG Chestplate", "§6Cost: "+Products.CG_Chestplate.getCost()));
        inv.setItem(21, Inventories.create("shop.buy %name CG_Leggings", Material.CHAINMAIL_LEGGINGS, "§6CG Leggings", "§6Cost: "+Products.CG_Leggings.getCost()));
        inv.setItem(30, Inventories.create("shop.buy %name CG_Boots", Material.CHAINMAIL_BOOTS, "§6CG Boots", "§6Cost: "+Products.CG_Boots.getCost()));
        inv.setItem(4, Inventories.create("shop.buy %name 501th_Helmet", Material.IRON_HELMET, "§6501th Helmet", "§6Cost: "+Products._501th_Helmet.getCost()));
        inv.setItem(13, Inventories.create("shop.buy %name 501th_Chestplate", Material.IRON_CHESTPLATE, "§6501th Chestplate", "§6Cost: "+Products._501th_Chestplate.getCost()));
        inv.setItem(22, Inventories.create("shop.buy %name 501th_Leggings", Material.IRON_LEGGINGS, "§6501th Leggings", "§6Cost: "+Products._501th_Leggings.getCost()));
        inv.setItem(31, Inventories.create("shop.buy %name 501th_Boots", Material.IRON_BOOTS, "§6501th Boots", "§6Cost: "+Products._501th_Boots.getCost()));
        inv.setItem(5, Inventories.create("shop.buy %name Durasteel_Helmet", Material.DIAMOND_HELMET, "§6Durasteel Helmet", "§6Cost: "+Products.Durasteel_Helmet.getCost()));
        inv.setItem(14, Inventories.create("shop.buy %name Durasteel_Chestplate", Material.DIAMOND_CHESTPLATE, "§6Durasteel Chestplate", "§6Cost: "+Products.Durasteel_Chestplate.getCost()));
        inv.setItem(23, Inventories.create("shop.buy %name Durasteel_Leggings", Material.DIAMOND_LEGGINGS, "§6Durasteel Leggings", "§6Cost: "+Products.Durasteel_Leggings.getCost()));
        inv.setItem(32, Inventories.create("shop.buy %name Durasteel_Boots", Material.DIAMOND_BOOTS, "§6Durasteel Boots", "§6Cost: "+Products.Durasteel_Boots.getCost()));
        inv.setItem(6, Inventories.create("shop.buy %name Beskar_Helmet", Material.NETHERITE_HELMET, "§6Beskar Helmet", "§6Cost: "+Products.Beskar_Helmet.getCost()));
        inv.setItem(15, Inventories.create("shop.buy %name Beskar_Chestplate", Material.NETHERITE_CHESTPLATE, "§6Beskar Chestplate", "§6Cost: "+Products.Beskar_Chestplate.getCost()));
        inv.setItem(24, Inventories.create("shop.buy %name Beskar_Leggings", Material.NETHERITE_LEGGINGS, "§6Beskar Leggings", "§6Cost: "+Products.Beskar_Leggings.getCost()));
        inv.setItem(33, Inventories.create("shop.buy %name Beskar_Boots", Material.NETHERITE_BOOTS, "§6Beskar Boots", "§6Cost: "+Products.Beskar_Boots.getCost()));
        */
        inv.setItem(40, openInventoryBuilder(Material.RED_STAINED_GLASS, MAIN, "Back"));
        fillAll(inv);
        ARMOR.set(inv);

    }

    /*public static ItemStack Test(Product product) {
        CustomItems c = new CustomItems();
        c.onInventoryEvent = event -> {
            BountyHunter.getByUsername(event.getWhoClicked().getName())
        }
    }*/
}
