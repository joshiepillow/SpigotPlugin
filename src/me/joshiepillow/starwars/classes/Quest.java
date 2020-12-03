package me.joshiepillow.starwars.classes;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

class Quest implements Serializable {
    private String type;
    private String item_name;
    private int count;
    private String complete_command;

    static Quest create(String type, String item_name, int count, String complete_command) {
        if (!type.equals("mob") && !type.equals("player")) return null;
        else return new Quest(type, item_name, count, complete_command);
    }
    private Quest(String type, String item_name, int count, String complete_command) {
        this.type = type;
        this.item_name = item_name;
        this.count = count;
        this.complete_command = complete_command;
    }
    String getType() {
        return type;
    }
    String getItem_name() {
        return item_name;
    }
    int getCount() {
        return count;
    }

    private int getNum(Inventory inv) {
        int count = 0;
        for(int i = 0; i < inv.getSize(); i++) {
            ItemStack a = inv.getItem(i);
            if (a != null && a.getType().equals(Material.PLAYER_HEAD)) {
                if (a.getItemMeta() != null && a.getItemMeta().getDisplayName().equals(item_name)) {
                    count += a.getAmount();
                }
            }
        }
        return count;
    }
    private void remove(Inventory inv) {
        int count = this.count;
        for(int i = 0; i < inv.getSize(); i++) {
            ItemStack a = inv.getItem(i);
            if (a != null && a.getType().equals(Material.PLAYER_HEAD)) {
                if (a.getItemMeta() != null && a.getItemMeta().getDisplayName().equals(item_name)) {
                    int temp = Math.min(a.getAmount()-count, 0);
                    count = Math.max(0, count-a.getAmount());
                    a.setAmount(temp);
                }
            }
        }
    }
    String submit(Inventory inv) {
        int count = getNum(inv);
        if (count>=this.count) {
            remove(inv);
            return complete_command;
        } else return null;
    }

    @Override
    public String toString() {
        return "("+type+")"+item_name+" -- "+count+" "+complete_command;
    }

}
