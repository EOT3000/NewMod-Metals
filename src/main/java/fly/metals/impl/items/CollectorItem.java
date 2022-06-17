package fly.metals.impl.items;

import fly.metals.MetalsPlugin;
import fly.newmod.NewMod;
import fly.newmod.api.block.BlockManager;
import fly.newmod.api.block.type.ModBlockType;
import fly.newmod.api.item.ItemManager;
import fly.newmod.api.item.ModItemStack;
import fly.newmod.api.item.type.ModItemType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class CollectorItem extends ModItemType {
    public CollectorItem() {
        super(Material.DROPPER, new NamespacedKey(MetalsPlugin.get(), "collector"));
        ItemManager manager = NewMod.get().getItemManager();
        BlockManager bmanager = NewMod.get().getBlockManager();

        name("Collector", 0x808080);

        ShapedRecipe recipe = new ShapedRecipe(getId(), new ModItemStack(this).create());

        recipe.shape("ICI", "CHC", "ICI");

        recipe.setIngredient('I', new ItemStack(Material.IRON_INGOT));
        recipe.setIngredient('H', new ItemStack(Material.HOPPER));
        recipe.setIngredient('C', new ItemStack(Material.COPPER_INGOT));

        Bukkit.addRecipe(recipe);

        System.out.println("a");

        setBlock(new ModBlockType(Material.DROPPER, getId()));

        System.out.println("b");

        manager.registerItem(this);
        bmanager.registerBlock(getBlock());
    }
}
