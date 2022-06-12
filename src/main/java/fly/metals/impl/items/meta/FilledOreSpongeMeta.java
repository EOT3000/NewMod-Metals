package fly.metals.impl.items.meta;

import fly.metals.MetalsPlugin;
import fly.metals.impl.items.FilledOreSponge;
import fly.newmod.NewMod;
import fly.newmod.api.item.ItemManager;
import fly.newmod.api.item.meta.ModItemMeta;
import fly.newmod.api.item.meta.ModItemMetaSerializer;
import fly.newmod.api.item.type.ModItemType;
import fly.newmod.utils.PersistentDataUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;

public class FilledOreSpongeMeta extends ModItemMeta.AbstractModItemMeta {
    public static final NamespacedKey ORE = new NamespacedKey(MetalsPlugin.get(), "ore");

    static {
        new FilledOreSpongeMetaSerializer();
    }

    private Material material;

    protected FilledOreSpongeMeta(ModItemType type, Material material) {
        super(type);

        this.material = material;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    @Override
    public FilledOreSpongeMeta cloneItem() {
        return new FilledOreSpongeMeta(getType(), material);
    }

    public static class FilledOreSpongeMetaSerializer extends ModItemMetaSerializer<FilledOreSpongeMeta> {
        public FilledOreSpongeMetaSerializer() {
            super(FilledOreSpongeMeta.class);
        }

        @Override
        public FilledOreSpongeMeta getItemMeta(PersistentDataContainer container) {
            ItemManager manager = NewMod.get().getItemManager();

            return new FilledOreSpongeMeta(manager.getType(container), Material.valueOf(container.get(ORE, PersistentDataType.STRING)));
        }

        @Override
        public FilledOreSpongeMeta defaultMeta(ModItemType type) {
            return new FilledOreSpongeMeta(type, Material.AIR);
        }

        @Override
        public boolean applyMeta(ItemStack itemStack, FilledOreSpongeMeta fmeta) {
            if(fmeta == null) {
                return false;
            } else {
                ItemMeta meta = itemStack.getItemMeta();

                meta.lore(Arrays.asList(Component.text(""), Component.text(FilledOreSponge.getVariantName(fmeta.material))));
                meta.getPersistentDataContainer().set(ORE, PersistentDataType.STRING, fmeta.material.name());

                itemStack.setItemMeta(meta);

                return true;
            }
        }
    }
}
