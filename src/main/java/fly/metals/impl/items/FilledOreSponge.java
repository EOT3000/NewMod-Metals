package fly.metals.impl.items;

import fly.metals.MetalsPlugin;
import fly.metals.impl.MetalTextures;
import fly.metals.impl.items.meta.FilledOreSpongeMeta;
import fly.metals.setup.MetalsAddonSetup;
import fly.newmod.NewMod;
import fly.newmod.api.block.BlockManager;
import fly.newmod.api.item.ItemManager;
import fly.newmod.api.item.ModItemStack;
import fly.newmod.api.item.texture.DefaultMetaFlags;
import fly.newmod.api.item.texture.MetaModifier;
import fly.newmod.api.item.type.ModItemType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Dropper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.inventory.*;

import java.util.HashMap;
import java.util.Map;

import static org.bukkit.Material.AIR;
import static org.bukkit.Material.PLAYER_HEAD;

public class FilledOreSponge extends ModItemType {
    private final static Map<Material, String> VARIANTS = new HashMap<>();

    public FilledOreSponge() {
        super(PLAYER_HEAD, new NamespacedKey(MetalsPlugin.get(), "filled_ore_sponge"), FilledOreSpongeMeta.class);

        name("Filled Ore Sponge", 0x7d5e36);
        addModifier(new MetaModifier<>(MetalTextures.FILLED_ORE_SPONGE.getRawTexture(), DefaultMetaFlags.SKULL_MODIFIER));

        NewMod.get().getItemManager().registerItem(this);

        addVariant(AIR, "errored");
    }

    public void addVariant(Material ore, String name) {
        VARIANTS.put(ore, name);

        ModItemStack modStack = new ModItemStack(this);

        FilledOreSpongeMeta meta = (FilledOreSpongeMeta) modStack.getMeta();

        meta.setMaterial(ore);

        modStack.setMeta(meta);

        ItemStack stack = modStack.create();

        stack.setAmount(8);

        stack.getItemMeta();

        @SuppressWarnings("deprecation")
        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(getId().getNamespace(), getId().getKey() + "_" + ore.name().toLowerCase()), stack);

        shapedRecipe.shape("AAA", "ABA", "AAA");

        shapedRecipe.setIngredient('A', new ModItemStack(MetalsAddonSetup.ORE_SPONGE).create());
        shapedRecipe.setIngredient('B', new ItemStack(ore));

        Bukkit.addRecipe(shapedRecipe);

        @SuppressWarnings("deprecation")
        NamespacedKey nk = new NamespacedKey(getId().getNamespace(), getId().getKey() + "_" + ore.name().toLowerCase() + "_furnace");

        System.out.println(nk);

        BlastingRecipe furnaceRecipe = new BlastingRecipe(nk, new ModItemStack(MetalsAddonSetup.HARD_CARBON_CHUNK).create(), new RecipeChoice.ExactChoice(modStack.create()), 2.0f, 600);

        Bukkit.addRecipe(furnaceRecipe);
    }

    public static String getVariantName(Material ore) {
        return VARIANTS.get(ore);
    }

    public static class SpongeListener implements Listener {
        @EventHandler
        public void onFurnaceSmelt(FurnaceSmeltEvent event) {
            Location l = event.getBlock().getLocation().add(0, -1, 0);
            BlockManager bs = NewMod.get().getBlockManager();
            ItemManager is = NewMod.get().getItemManager();

            if (is.getType(event.getResult()) == MetalsAddonSetup.HARD_CARBON_CHUNK && bs.getType(l.getBlock()) == MetalsAddonSetup.COLLECTOR.getBlock()) {
                ModItemStack stack = new ModItemStack(((RecipeChoice.ExactChoice) event.getRecipe().getInputChoice()).getItemStack());
                FilledOreSpongeMeta sponge = (FilledOreSpongeMeta) stack.getMeta();

                ItemStack item = MetalsAddonSetup.refine(sponge.getMaterial());

                Dropper dropper = (Dropper) l.getBlock().getState();

                dropper.getInventory().addItem(item);
            }
        }
    }
}
