package fly.metals.setup;

import fly.newmod.NewMod;
import fly.newmod.bases.ModItem;
import fly.metals.impl.MetalNugget;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.bukkit.Material.*;

public class MetalsAddonSetup {
    private static int bn;
    public static Map<Material, Map<ItemStack, Double>> oreMap = new LinkedHashMap<>();
    private static Map<ItemStack, Double> otherPercentages = new HashMap<>();

    public static void init() {
        File file = new File("plugins\\NewMod\\config.yml");

        try {
            if (!file.exists()) {
                file.createNewFile();

                FileOutputStream outputStream = new FileOutputStream(file);

                InputStream inputStream = MetalsAddonSetup.class.getClassLoader().getResourceAsStream("config.yml");

                byte[] bytes = new byte[inputStream.available()];

                inputStream.read(bytes);

                outputStream.write(bytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        bn = configuration.getInt("block-nuggets");

        ConfigurationSection ores = configuration.getConfigurationSection("ores");

        for(String oreKey : ores.getKeys(false)) {
            ConfigurationSection oreData = ores.getConfigurationSection(oreKey);

            oreMap.put(Material.valueOf(oreKey), new HashMap<>());

            double d = 0;

            for(String metalKey : oreData.getKeys(false)) {
                double metalData = oreData.getDouble(metalKey);

                System.out.println(oreKey + ", " + metalKey + ", " + metalData);

                d += metalData;

                oreMap.get(Material.valueOf(oreKey)).put(NewMod.get().getBlockStorage().getTypeE(metalKey), metalData);
            }

            if(d != 1) {
                System.err.println("Error on init of " + oreKey + ", values do not equal 1, equals " + d);
            }
        }
    }

    public static final ModItem ZINC_INGOT = new ModItem(IRON_INGOT, "&7Zinc Ingot", "zinc_ingot");
    public static final ModItem ALUMINUM_INGOT = new ModItem(IRON_INGOT, "&8Aluminum Ingot", "aluminum_ingot");
    public static final ModItem TIN_INGOT = new ModItem(IRON_INGOT, "&7Tin Ingot", "tin_ingot");
    public static final ModItem TITANIUM_INGOT = new ModItem(IRON_INGOT, "&8Titanium Ingot", "titanium_ingot");
    public static final ModItem NEODYMIUM_INGOT = new ModItem(IRON_INGOT, "&7Neodymium Ingot", "neodymium_ingot");
    public static final ModItem MAGNESIUM_INGOT = new ModItem(IRON_INGOT, "&7Magnesium Ingot", "magnesium_ingot");
    public static final ModItem CALCIUM_INGOT = new ModItem(IRON_INGOT, "&fCalcium Ingot", "calcium_ingot");
    public static final ModItem SODIUM_INGOT = new ModItem(IRON_INGOT, "&fSodium Ingot", "sodium_ingot");
    public static final ModItem ZIRCONIUM_INGOT = new ModItem(IRON_INGOT, "&7Zirconium Ingot", "zirconium_ingot");
    public static final ModItem POTASSIUM_INGOT  = new ModItem(IRON_INGOT, "&7Potassium Ingot", "potassium_ingot");

    public static final ModItem REDSTONE_INGOT = new ModItem(NETHER_BRICK, "&4Redstone Ingot", "redstone_ingot");
    public static final ModItem SILICON_INGOT = new ModItem(IRON_INGOT, "&fSilicon Ingot", "silicon_ingot");

    public static final ModItem CARBON_CHUNK  = new ModItem(COAL, "&8Carbon Chunk", "carbon_chunk");

    public static final ModItem ZINC_NUGGET = new MetalNugget(IRON_NUGGET, "&7Zinc Nugget", "zinc_nugget", ZINC_INGOT);
    public static final ModItem ALUMINUM_NUGGET = new MetalNugget(IRON_NUGGET, "&8Aluminum Nugget", "aluminum_nugget", ALUMINUM_INGOT);
    public static final ModItem TIN_NUGGET = new MetalNugget(IRON_NUGGET, "&7Tin Nugget", "tin_nugget", TIN_INGOT);
    public static final ModItem TITANIUM_NUGGET = new MetalNugget(IRON_NUGGET, "&8Titanium Nugget", "titanium_nugget", TITANIUM_INGOT);
    public static final ModItem NEODYMIUM_NUGGET = new MetalNugget(IRON_NUGGET, "&7Neodymium Nugget", "neodymium_nugget", NEODYMIUM_INGOT);
    public static final ModItem MAGNESIUM_NUGGET = new MetalNugget(IRON_NUGGET, "&7Magnesium Nugget", "magnesium_nugget", MAGNESIUM_INGOT);
    public static final ModItem CALCIUM_NUGGET = new MetalNugget(IRON_NUGGET, "&fCalcium Nugget", "calcium_nugget", CALCIUM_INGOT);
    public static final ModItem SODIUM_NUGGET = new MetalNugget(IRON_NUGGET, "&fSodium Nugget", "sodium_nugget", SODIUM_INGOT);
    public static final ModItem ZIRCONIUM_NUGGET = new MetalNugget(IRON_NUGGET, "&7Zirconium Nugget", "zirconium_nugget", ZIRCONIUM_INGOT);
    public static final ModItem POTASSIUM_NUGGET = new MetalNugget(IRON_NUGGET, "&7Potassium Nugget", "potassium_nugget", POTASSIUM_INGOT);
    public static final ModItem SILICON_NUGGET = new MetalNugget(IRON_NUGGET, "&7Silicon Nugget", "silicon_nugget", SILICON_INGOT);

    public static final ModItem COPPER_NUGGET  = new MetalNugget(GOLD_NUGGET, "&6Copper Nugget", "copper_nugget", new ItemStack(COPPER_INGOT));

    public static final ModItem SULFUR_NUGGET  = new ModItem(GOLD_NUGGET, "&6Sulfur Nugget", "sulfur_nugget");
    public static final ModItem CARBON_PIECE  = new MetalNugget(FLINT, "&0Carbon Piece", "carbon_piece", CARBON_CHUNK);

    public static final ModItem IMPURE_URANIUM_DUST = new ModItem(SUGAR, "&7Impure Uranium Dust", "impure_uranium_dust");
    public static final ModItem PURE_URANIUM_NUGGET = new ModItem(SCUTE, "&2Pure Uranium Nugget", "pure_uranium_nugget");

    public static final ModItem MAGNET = new ModItem(BRICK, "&4Magnet", "magnet");
}
