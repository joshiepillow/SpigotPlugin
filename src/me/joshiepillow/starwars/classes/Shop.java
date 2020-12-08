package me.joshiepillow.starwars.classes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import me.joshiepillow.starwars.classes.Inventories.*;

public class Shop {

    //Now the definitions
    public static Inventories MAIN;
    public static Inventories GUNS;
    public static Inventories WEAPONS;
    public static Inventories LIGHTSABERS;
    public static Inventories ARMOR;


    public static void setItems() {
        Inventory inv;

        inv = Bukkit.createInventory(null, 54, "HorizonShop");
        inv.setItem(4, Inventories.create("shop.open %name guns", Material.AZURE_BLUET, "§7Guns", ""));
        inv.setItem(10, Inventories.create("shop.open %name weapons", Material.NETHERITE_AXE, "§4Weapons", ""));
        inv.setItem(16, Inventories.create("shop.open %name lightsabers", Material.IRON_AXE, "§1Lightsabers", ""));
        inv.setItem(22, Inventories.create("shop.open %name armor", Material.LEATHER_HELMET, "§6Armor", ""));
        inv.setItem(40, Inventories.create("shop.close %name", Material.RED_STAINED_GLASS, "Close", ""));
        Inventories.fillAll(inv);
        MAIN = Inventories.create("main", inv);

        inv = Bukkit.createInventory(null, 54, "HorizonShop");
        inv.setItem(11, Inventories.create("shop.buy %name A180", Material.BLUE_ORCHID, "§7A180", "§6Cost: "+Products.A180.getCost()));
        inv.setItem(12, Inventories.create("shop.buy %name A280", Material.ALLIUM, "§7A280", "§6Cost: "+Products.A280.getCost()));
        inv.setItem(13, Inventories.create("shop.buy %name A280C", Material.AZURE_BLUET, "§7A280C", "§6Cost: "+Products.A280C.getCost()));
        inv.setItem(14, Inventories.create("shop.buy %name DL18", Material.RED_TULIP, "§7DL18", "§6Cost: "+Products.DL18.getCost()));
        inv.setItem(15, Inventories.create("shop.buy %name K16_Bryar", Material.ORANGE_TULIP, "§7K16 Bryar", "§6Cost: "+Products.K16_Bryar.getCost()));
        inv.setItem(20, Inventories.create("shop.buy %name SE14C", Material.POPPY, "§7SE14C", "§6Cost: "+Products.SE14C.getCost()));
        inv.setItem(21, Inventories.create("shop.buy %name A295", Material.DANDELION, "§7A295", "§6Cost: "+Products.A295.getCost()));
        inv.setItem(22, Inventories.create("shop.buy %name TL50", Material.OXEYE_DAISY, "§7TL50", "§6Cost: "+Products.TL50.getCost()));
        inv.setItem(23, Inventories.create("shop.buy %name Cycler_Rifle", Material.PINK_TULIP, "§7Cycler Rifle", "§6Cost: "+Products.Cycler_Rifle.getCost()));
        inv.setItem(24, Inventories.create("shop.buy %name RelbyV10", Material.WHITE_TULIP, "§7RelbyV10", "§6Cost: "+Products.RelbyV10.getCost()));
        inv.setItem(40, Inventories.create("shop.open %name main", Material.RED_STAINED_GLASS, "Back", "", 1));
        Inventories.fillAll(inv);
        GUNS = Inventories.create("guns", inv);

        inv = Bukkit.createInventory(null, 54, "HorizonShop");
        inv.setItem(10, Inventories.create("shop.buy %name Gaderffii", Material.WOODEN_SWORD, "§4Gaderffii", "§6Cost: "+Products.Gaderffii.getCost()));
        inv.setItem(11, Inventories.create("shop.buy %name Gammorean_Axe", Material.WOODEN_AXE, "§4Gammorean Axe", "§6Cost: "+Products.Gammorean_Axe.getCost()));
        inv.setItem(13, Inventories.create("shop.buy %name Mythosaur_Axe", Material.STONE_SWORD, "§4Mythosaur Axe", "§6Cost: "+Products.Mythosaur_Axe.getCost()));
        inv.setItem(15, Inventories.create("shop.buy %name Kyuso_Petar", Material.STONE_AXE, "§4Kyuso Petar", "§6Cost: "+Products.Kyuso_Petar.getCost()));
        inv.setItem(19, Inventories.create("shop.buy %name Yellow_Electro_Staff", Material.GOLDEN_AXE, "§4Yellow Electro Staff", "§6Cost: "+Products.Yellow_Electro_Staff.getCost()));
        inv.setItem(20, Inventories.create("shop.buy %name Purple_Electro_Staff", Material.GOLDEN_SWORD, "§4Purple Electro Staff", "§6Cost: "+Products.Purple_Electro_Staff.getCost()));
        inv.setItem(22, Inventories.create("shop.buy %name Vibro_Blade_1", Material.NETHERITE_SWORD, "§4Vibro Blade #1", "§6Cost: "+Products.Vibro_Blade_1.getCost()));
        inv.setItem(23, Inventories.create("shop.buy %name Vibro_Blade_2", Material.DIAMOND_AXE, "§4Vibro Blade #2", "§6Cost: "+Products.Vibro_Blade_2.getCost()));
        inv.setItem(24, Inventories.create("shop.buy %name Vibro_Blade_3", Material.NETHERITE_AXE, "§4Vibro Blade #3", "§6Cost: "+Products.Vibro_Blade_3.getCost()));
        inv.setItem(40, Inventories.create("shop.open %name main", Material.RED_STAINED_GLASS, "Back", "", 1));
        Inventories.fillAll(inv);
        WEAPONS = Inventories.create("weapons", inv);

        inv = Bukkit.createInventory(null, 54, "HorizonShop");
        inv.setItem(13, Inventories.create("shop.buy %name Top_Hilt", Material.BROWN_MUSHROOM, "§1Top Hilt", "§6Cost: "+Products.Top_Hilt.getCost()));
        inv.setItem(14, Inventories.create("shop.buy %name Activation_Stud", Material.GOLD_NUGGET, "§1Activation Stud", "§6Cost: "+Products.Activation_Stud.getCost()));
        inv.setItem(21, Inventories.create("shop.buy %name Blade_Emitter", Material.IRON_NUGGET, "§1Blade Emitter", "§6Cost: "+Products.Blade_Emitter.getCost()));
        inv.setItem(31, Inventories.create("shop.buy %name Bottom_Hilt", Material.RED_MUSHROOM, "§1Bottom Hilt", "§6Cost: "+Products.Bottom_Hilt.getCost()));
        inv.setItem(40, Inventories.create("shop.open %name main", Material.RED_STAINED_GLASS, "Back", ""));
        Inventories.fillAll(inv);
        LIGHTSABERS = Inventories.create("lightsabers", inv);

        inv = Bukkit.createInventory(null, 54, "HorizonShop");
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
        inv.setItem(40, Inventories.create("shop.open %name main", Material.RED_STAINED_GLASS, "Back", ""));
        Inventories.fillAll(inv);
        ARMOR = Inventories.create("armor", inv);

    }
}
