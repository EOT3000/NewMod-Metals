package fly.metals.impl.items;

import fly.metals.MetalsPlugin;
import fly.metals.impl.MetalTextures;
import fly.metals.setup.MetalsAddonSetup;
import fly.newmod.NewMod;
import fly.newmod.bases.ModItem;
import fly.newmod.bases.textures.Texture;
import fly.newmod.bases.textures.TexturedModItem;
import fly.newmod.setup.BlockStorage;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Dropper;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.inventory.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.bukkit.Material.PLAYER_HEAD;

public class FilledOreSponge extends ModItem implements TexturedModItem {
    private final static Map<Material, FilledOreSponge> VARIANTS = new HashMap<>();
    private final Material ore;

    public FilledOreSponge() {
        super(PLAYER_HEAD, "Filled Ore Sponge", 0x7d5e36, "filled_ore_sponge");

        this.ore = null;

        Bukkit.getPluginManager().registerEvents(new SpongeListener(), NewMod.get());
    }

    private FilledOreSponge(Material ore, String name) {
        super(PLAYER_HEAD, "Filled Ore Sponge", 0x7d5e36, "filled_ore_sponge_" + ore.name().toLowerCase());

        lore(Collections.singletonList(Component.text(name).color(TextColor.color(0x808080))));

        VARIANTS.put(ore, this);

        ItemStack stack = new ItemStack(this);

        stack.setAmount(8);

        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(MetalsPlugin.getPlugin(MetalsPlugin.class), getId()), stack);

        shapedRecipe.shape("AAA", "ABA", "AAA");

        shapedRecipe.setIngredient('A', MetalsAddonSetup.ORE_SPONGE);
        shapedRecipe.setIngredient('B', new ItemStack(ore));

        addRecipe(shapedRecipe);

        NamespacedKey nk = new NamespacedKey(MetalsPlugin.getPlugin(MetalsPlugin.class), getId() + "_furnace");

        BlastingRecipe furnaceRecipe = new BlastingRecipe(nk, MetalsAddonSetup.HARD_CARBON_CHUNK, new RecipeChoice.ExactChoice(this), 2.0f, 600);

        addRecipe(furnaceRecipe);

        this.ore = ore;
    }

    @Override
    public Texture getTexture() {
        return MetalTextures.FILLED_ORE_SPONGE;
    }

    public FilledOreSponge addVariant(Material ore, String name) {
        return new FilledOreSponge(ore, name);
    }

    public static FilledOreSponge getVariant(Material ore) {
        return VARIANTS.get(ore);
    }

    private static class SpongeListener implements Listener {
        @EventHandler
        public void onFurnaceSmelt(FurnaceSmeltEvent event) {
            Location l = event.getBlock().getLocation().add(0, -1, 0);
            BlockStorage bs = NewMod.get().getBlockStorage();

            if (event.getResult().equals(MetalsAddonSetup.HARD_CARBON_CHUNK) && bs.getData(l, "id").equals("collector")) {
                String id = BlockStorage.getID(((RecipeChoice.ExactChoice) event.getRecipe().getInputChoice()).getItemStack());
                FilledOreSponge sponge = (FilledOreSponge) bs.getItems().get(id);

                ItemStack item = MetalsAddonSetup.refine(sponge.ore);

                Dropper dropper = (Dropper) l.getBlock().getState();

                dropper.getInventory().addItem(item);
            }
        }
    }
}
