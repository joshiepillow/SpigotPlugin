package me.joshiepillow.starwars.classes;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

public class Recipes {
    public static void setItems(Plugin plugin) {
        ItemStack output;
        ShapedRecipe lightsaber;

        //green
        output = CustomItem.GREEN_LIGHTSABER;
        lightsaber = new ShapedRecipe(new NamespacedKey(plugin, "greenLightsaber"), output);
        // * b g
        // i k *
        // * r *
        lightsaber.shape(" bg","ik "," r ");
        lightsaber.setIngredient('b', Material.BROWN_MUSHROOM);
        lightsaber.setIngredient('g', Material.GOLD_NUGGET);
        lightsaber.setIngredient('i', Material.IRON_NUGGET);
        lightsaber.setIngredient('r', Material.RED_MUSHROOM);

        lightsaber.setIngredient('k', Material.EMERALD);

        plugin.getServer().addRecipe(lightsaber);

        //blue
        output = CustomItem.BLUE_LIGHTSABER;
        lightsaber = new ShapedRecipe(new NamespacedKey(plugin, "blueLightsaber"), output);
        // * b g
        // i k *
        // * r *
        lightsaber.shape("*bg","ik*","*r*");
        lightsaber.setIngredient('b', Material.BROWN_MUSHROOM);
        lightsaber.setIngredient('g', Material.GOLD_NUGGET);
        lightsaber.setIngredient('i', Material.IRON_NUGGET);
        lightsaber.setIngredient('r', Material.RED_MUSHROOM);

        lightsaber.setIngredient('k', Material.LAPIS_LAZULI);

        plugin.getServer().addRecipe(lightsaber);

        //red
        output = CustomItem.RED_LIGHTSABER;
        lightsaber = new ShapedRecipe(new NamespacedKey(plugin, "redLightsaber"), output);
        // * b g
        // i k *
        // * r *
        lightsaber.shape(" bg","ik "," r ");
        lightsaber.setIngredient('b', Material.BROWN_MUSHROOM);
        lightsaber.setIngredient('g', Material.GOLD_NUGGET);
        lightsaber.setIngredient('i', Material.IRON_NUGGET);
        lightsaber.setIngredient('r', Material.RED_MUSHROOM);

        lightsaber.setIngredient('k', Material.REDSTONE);

        plugin.getServer().addRecipe(lightsaber);
    }
}
