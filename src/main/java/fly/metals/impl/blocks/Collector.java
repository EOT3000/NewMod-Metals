package fly.metals.impl.blocks;

import fly.metals.MetalsPlugin;
import fly.newmod.bases.ModItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class Collector extends ModItem {
    public Collector() {
        super(Material.DROPPER, Component.text("Collector").color(TextColor.color(0x808080)), "collector");

        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(MetalsPlugin.getPlugin(MetalsPlugin.class), getId()), this);

        recipe.shape("ICI", "CHC", "ICI");

        recipe.setIngredient('I', new ItemStack(Material.IRON_INGOT));
        recipe.setIngredient('H', new ItemStack(Material.HOPPER));
        recipe.setIngredient('C', new ItemStack(Material.COPPER_INGOT));

        addRecipe(recipe);
    }
}
