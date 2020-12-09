package me.joshiepillow.starwars.classes;

import me.joshiepillow.CustomItems.CustomItems;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class CustomItem {
    //fill this with the items later
    //much better than commands

    //Lightsabers
    public static ItemStack GREEN_LIGHTSABER;
    public static ItemStack BLUE_LIGHTSABER;
    public static ItemStack RED_LIGHTSABER;

    public static void setItems() {




        ItemMeta m;

        GREEN_LIGHTSABER = new ItemStack(Material.IRON_SWORD);
        m = GREEN_LIGHTSABER.getItemMeta();
        assert m != null;
        m.setDisplayName("Green Lightsaber");
        m.setLore(new ArrayList<String>(){{
            add("Lightsaber");
            add("Damage: 1");
        }});
        m.addEnchant(Enchantment.CHANNELING, 1, false);
        m.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE,
                new AttributeModifier(UUID.randomUUID(), "damage", 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        m.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        GREEN_LIGHTSABER.setItemMeta(m);

        BLUE_LIGHTSABER = new ItemStack(Material.IRON_AXE);
        m = BLUE_LIGHTSABER.getItemMeta();
        assert m != null;
        m.setDisplayName("Blue Lightsaber");
        m.setLore(new ArrayList<String>(){{
            add("Lightsaber");
            add("Damage: 1");
        }});
        m.addEnchant(Enchantment.CHANNELING, 1, false);
        m.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE,
                new AttributeModifier(UUID.randomUUID(), "damage", 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        m.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        BLUE_LIGHTSABER.setItemMeta(m);

        RED_LIGHTSABER = new ItemStack(Material.DIAMOND_SWORD);
        m = RED_LIGHTSABER.getItemMeta();
        assert m != null;
        m.setLore(new ArrayList<String>(){{
            add("Lightsaber");
            add("Damage: 1");
        }});
        m.addEnchant(Enchantment.CHANNELING, 1, false);
        m.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE,
                new AttributeModifier(UUID.randomUUID(), "damage", 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        m.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        m.setDisplayName("Red Lightsaber");
        RED_LIGHTSABER.setItemMeta(m);

    }
}
