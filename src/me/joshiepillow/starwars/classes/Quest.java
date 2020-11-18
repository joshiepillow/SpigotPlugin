package me.joshiepillow.starwars.classes;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

class Quest {
    private String type;
    private String item_name;
    private int count;
    private String complete_command;

    static Quest create(String type, String item_name, int count, String complete_command) {
        if (!type.equals("mob")) return null;
        else return new Quest(type, item_name, count, complete_command);
    }
    private Quest(String type, String item_name, int count, String complete_command) {
        this.type = type;
        this.item_name = item_name + " Head";
        this.count = count;
        this.complete_command = complete_command;
    }
    String submit(Inventory inv) {
        for(int i = 0; i < inv.getSize(); i++) {
            ItemStack a = inv.getItem(i);
            if (a != null && a.getType().equals(Material.PLAYER_HEAD)) {
                if (a.getItemMeta() != null && a.getItemMeta().getDisplayName().equalsIgnoreCase(item_name)) {
                    if (a.getAmount() >= count) {
                        a.setAmount(a.getAmount()-count);
                        inv.setItem(i, a);
                        return complete_command;
                    }
                }
            }

        }
        return null;
    }

    @Override
    public String toString() {
        return "("+type+")"+item_name+" -- "+count+" "+complete_command;
    }


}
