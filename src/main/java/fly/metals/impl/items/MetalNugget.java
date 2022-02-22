package fly.metals.impl.items;

import fly.newmod.NewMod;
import fly.newmod.bases.ModItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;

public class MetalNugget extends ModItem {
    private ItemStack metal;

    public MetalNugget(Material material, String name, int color, String id, ItemStack metal) {
        super(material, name, color, id);

        this.metal = metal;

        ItemStack result = new ItemStack(this);

        result.setAmount(9);

        ShapelessRecipe recipe = new ShapelessRecipe(new NamespacedKey(NewMod.get(), id), result);

        recipe.addIngredient(metal);

        addRecipe(recipe);


        ShapelessRecipe recipeReverse = new ShapelessRecipe(new NamespacedKey(NewMod.get(), id + "_reverse"), metal);

        recipeReverse.addIngredient(9, this);

        addRecipe(recipeReverse);
    }

    public ItemStack getMetal() {
        return metal;
    }
}
