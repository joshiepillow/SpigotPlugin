package me.joshiepillow.starwars.classes;

import org.bukkit.inventory.Inventory;


public class InventoryWrapper {
    private Inventory inv;
    public InventoryWrapper(Inventory inv) {this.inv = inv;}
    public InventoryWrapper() {this.inv = null;}
    public Inventory get() {return inv;}
    public void set(Inventory inv) {this.inv = inv;}
}

