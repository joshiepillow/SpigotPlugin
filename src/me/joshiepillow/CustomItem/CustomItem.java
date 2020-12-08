package me.joshiepillow.CustomItem;

import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Function;

public class CustomItem extends ItemStack {
    Function<InventoryEvent, Void> onInventoryEvent;
}
