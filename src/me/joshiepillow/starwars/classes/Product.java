package me.joshiepillow.starwars.classes;

import org.bukkit.inventory.ItemStack;

public class Product {
    private int cost;
    private ItemStack item;
    Product(int cost, ItemStack item) {
        this.cost = cost;
        this.item = item;
    }
    ItemStack getItem() {
        return item;
    }
    String getName() {
        return item.getItemMeta().getDisplayName();
    }
    int getCost() {
        return cost;
    }
}
