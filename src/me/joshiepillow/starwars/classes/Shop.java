package me.joshiepillow.starwars.classes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import me.joshiepillow.starwars.classes.Inventories.*;

public class Shop {

    //Now the definitions
    public static Inventories main;
    public static Inventories weapons;
    public static Inventories guns;

    static void setItems() {
        Inventory inv;

        inv = Bukkit.createInventory(null, 54, "HorizonShop");
        inv.setItem(10, Inventories.create("/shop.open %name weapons", Material.STONE_SWORD, "Weapons", "", 1));
        inv.setItem(11, Inventories.create("/shop.open %name guns", Material.STONE_HOE, "Guns", "", 1));
        //inv.setItem(12, Inventories.create("/shop.open %name guns", Material.STONE_HOE, "Guns", "", 1));
        //inv.setItem(13, Inventories.create("/shop.open %name guns", Material.STONE_HOE, "Guns", "", 1));
        //inv.setItem(14, Inventories.create("/shop.open %name guns", Material.STONE_HOE, "Guns", "", 1));
        //inv.setItem(15, Inventories.create("/shop.open %name guns", Material.STONE_HOE, "Guns", "", 1));
        //inv.setItem(16, Inventories.create("/shop.open %name guns", Material.STONE_HOE, "Guns", "", 1));
        inv.setItem(38, Inventories.create("/shop.close %name", Material.RED_STAINED_GLASS, "Close", "", 1));

        Inventories.fillAll(inv);
        main = Inventories.create("main", inv);

        inv = Bukkit.createInventory(null, 54, "HorizonShop");
        inv.setItem(13, Inventories.create(Material.BEEF));
        inv.setItem(38, Inventories.create("/shop.open %name main", Material.RED_STAINED_GLASS, "Back", "", 1));
        inv.setItem(42, Inventories.create("/shop.buy %name Food", Material.GREEN_STAINED_GLASS, "Confirm", "", 1));
        Inventories.fillAll(inv);
        weapons = Inventories.create("weapons", inv);

        inv = Bukkit.createInventory(null, 54, "HorizonShop");
        Inventories.fillAll(inv);
        guns = Inventories.create("guns", inv);

    }
}
