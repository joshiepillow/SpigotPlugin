package me.joshiepillow.starwars.classes;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;

public class Recipes {
    private static JavaPlugin plugin;
    public static void setItems(JavaPlugin plugin) {
        Recipes.plugin = plugin;

        setLightsaberRecipes();
    }
    private static void setLightsaberRecipesHelper(String key, ItemStack output, ItemStack crystal) {
        ShapedRecipe lightsaber = new ShapedRecipe(new NamespacedKey(plugin, key), output);
        // * b g
        // i k *
        // * r *
        lightsaber.shape(" bg","ik "," r ");
        lightsaber.setIngredient('b', new RecipeChoice.ExactChoice(CustomItem.TOP_HILT));
        lightsaber.setIngredient('g', new RecipeChoice.ExactChoice(CustomItem.BLADE_EMITTER));
        lightsaber.setIngredient('i', new RecipeChoice.ExactChoice(CustomItem.ACTIVATION_STUD));
        lightsaber.setIngredient('r', new RecipeChoice.ExactChoice(CustomItem.BOTTOM_HILT));

        lightsaber.setIngredient('k', new RecipeChoice.ExactChoice(crystal));

        plugin.getServer().addRecipe(lightsaber);
    }
    private static void setLightsaberRecipes() {

        //green
        setLightsaberRecipesHelper("greenLightsaber", CustomItem.GREEN_LIGHTSABER, new ItemStack(Material.EMERALD));

        //blue
        setLightsaberRecipesHelper("blueLightsaber", CustomItem.BLUE_LIGHTSABER, new ItemStack(Material.LAPIS_LAZULI));

        //red
        setLightsaberRecipesHelper("redLightsaber", CustomItem.RED_LIGHTSABER, new ItemStack(Material.REDSTONE));

        //yellow
        setLightsaberRecipesHelper("yellowLightsaber", CustomItem.YELLOW_LIGHTSABER, new ItemStack(Material.GLOWSTONE_DUST));

        //purple
        setLightsaberRecipesHelper("purpleLightsaber", CustomItem.PURPLE_LIGHTSABER, new ItemStack(Material.OBSIDIAN));
    }
}
