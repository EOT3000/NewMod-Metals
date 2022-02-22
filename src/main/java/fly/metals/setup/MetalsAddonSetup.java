package fly.metals.setup;

import fly.metals.impl.blocks.Collector;
import fly.metals.impl.items.FilledOreSponge;
import fly.newmod.NewMod;
import fly.newmod.bases.ModItem;
import fly.metals.impl.items.MetalNugget;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;

import static org.bukkit.Material.*;

public class MetalsAddonSetup {
    private static int bn;
    public static Map<Material, Map<ItemStack, Double>> oreMap = new LinkedHashMap<>();
    private static Map<ItemStack, Double> otherPercentages = new HashMap<>();

    //private static Map<Material, ModItem> dusts = new HashMap<>();

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
                if(metalKey.equals("ITEM")) {
                    BASE_FILLED_ORE_SPONGE.addVariant(Material.valueOf(oreKey), oreData.getString("NAME"));
                    continue;
                }

                if(metalKey.equals("NAME")) {
                    continue;
                }

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

    public static ItemStack refine(Material material) {
        Random random = new Random();

        double r = random.nextDouble();

        for (ItemStack stack : oreMap.get(material).keySet()) {
            double probability = oreMap.get(material).get(stack);

            if (r < probability) {
                return Objects.requireNonNullElse(stack, SILICON_NUGGET);
            } else {
                r -= probability;
            }
        }

        return SILICON_NUGGET;
    }


    public static final ModItem ZINC_INGOT = new ModItem(IRON_INGOT, "Zinc Ingot", 0x6F857E, "zinc_ingot");
    public static final ModItem ALUMINUM_INGOT = new ModItem(IRON_INGOT, "Aluminum Ingot", 0x8495B8, "aluminum_ingot");
    public static final ModItem TITANIUM_INGOT = new ModItem(IRON_INGOT, "Titanium Ingot", 0xFAFAFA, "titanium_ingot");
    public static final ModItem NEODYMIUM_INGOT = new ModItem(IRON_INGOT, "Neodymium Ingot", 0xC2CBFF, "neodymium_ingot");
    public static final ModItem SODIUM_INGOT = new ModItem(IRON_INGOT, "Sodium Ingot", 0xFFFDEB, "sodium_ingot");
    public static final ModItem ZIRCONIUM_INGOT = new ModItem(IRON_INGOT, "Zirconium Ingot", 0xF4EDFF, "zirconium_ingot");
    public static final ModItem POTASSIUM_INGOT  = new ModItem(IRON_INGOT, "Potassium Ingot", 0xFFF5ED, "potassium_ingot");
    public static final ModItem LITHIUM_INGOT  = new ModItem(IRON_INGOT, "Lithium Ingot", 0xE8F2E6, "lithium_ingot");
    public static final ModItem LEAD_INGOT  = new ModItem(IRON_INGOT, "Lead Ingot", 0x282D3B, "lead_ingot");
    public static final ModItem MAGNESIUM_INGOT  = new ModItem(IRON_INGOT, "Magnesium Ingot", 0xE8E289, "magnesium_ingot");

    //public static final ModItem REDSTONE_INGOT = new ModItem(NETHER_BRICK, "Redstone Ingot", "redstone_ingot");
    public static final ModItem SILICON_INGOT = new ModItem(IRON_INGOT, "Silicon Ingot", 0xEBFFFE, "silicon_ingot");

    public static final ModItem CARBON_CHUNK  = new ModItem(COAL, "Carbon Chunk", 0x202020, "carbon_chunk");

    public static final MetalNugget ZINC_NUGGET = new MetalNugget(IRON_NUGGET, "Zinc Nugget", 0x6F857E, "zinc_nugget", ZINC_INGOT);
    public static final MetalNugget ALUMINUM_NUGGET = new MetalNugget(IRON_NUGGET, "Aluminum Nugget", 0x8495B8, "aluminum_nugget", ALUMINUM_INGOT);
    public static final MetalNugget TITANIUM_NUGGET = new MetalNugget(IRON_NUGGET, "Titanium Nugget", 0xFAFAFA, "titanium_nugget", TITANIUM_INGOT);
    public static final MetalNugget NEODYMIUM_NUGGET = new MetalNugget(IRON_NUGGET, "Neodymium Nugget", 0xC2CBFF, "neodymium_nugget", NEODYMIUM_INGOT);
    public static final MetalNugget SODIUM_NUGGET = new MetalNugget(IRON_NUGGET, "Sodium Nugget", 0xFFFDEB, "sodium_nugget", SODIUM_INGOT);
    public static final MetalNugget ZIRCONIUM_NUGGET = new MetalNugget(IRON_NUGGET, "Zirconium Nugget", 0xF4EDFF, "zirconium_nugget", ZIRCONIUM_INGOT);
    public static final MetalNugget POTASSIUM_NUGGET = new MetalNugget(IRON_NUGGET, "Potassium Nugget", 0xFFF5ED, "potassium_nugget", POTASSIUM_INGOT);
    public static final MetalNugget LITHIUM_NUGGET = new MetalNugget(IRON_NUGGET, "Lithium Nugget", 0xE8F2E6, "lithium_nugget", LITHIUM_INGOT);
    public static final MetalNugget LEAD_NUGGET = new MetalNugget(IRON_NUGGET, "Lead Nugget", 0x282D3B, "lead_nugget", LEAD_INGOT);
    public static final MetalNugget MAGNESIUM_NUGGET = new MetalNugget(IRON_NUGGET, "Magnesium Nugget", 0xE8E289, "magnesium_nugget", MAGNESIUM_INGOT);

    public static final MetalNugget SILICON_NUGGET = new MetalNugget(IRON_NUGGET, "Silicon Nugget", 0xEBFFFE, "silicon_nugget", SILICON_INGOT);

    public static final MetalNugget COPPER_NUGGET = new MetalNugget(GOLD_NUGGET, "Copper Nugget", 0xE8BF82, "copper_nugget", new ItemStack(COPPER_INGOT));

    public static final ModItem SULFUR_NUGGET = new ModItem(GOLD_NUGGET, "Sulfur Nugget", 0xFFF200, "sulfur_nugget");
    public static final MetalNugget CARBON_PIECE = new MetalNugget(FLINT, "Carbon Piece", 0x202020, "carbon_piece", CARBON_CHUNK);

    public static final ModItem IMPURE_URANIUM_DUST = new ModItem(SUGAR, "Impure Uranium Dust", 0xFFFFFF, "impure_uranium_dust");
    public static final ModItem PURE_URANIUM_NUGGET = new ModItem(SCUTE, "Pure Uranium Nugget", 0x00FF00, "pure_uranium_nugget");

    public static final ModItem MAGNET = new ModItem(STONE_BUTTON, "Magnet", 0x00EAFF, "magnet");

    public static final ModItem HARD_CARBON_CHUNK = new ModItem(FLINT, "Hard Carbon Chunk", 0x202020, "hard_carbon_chunk");

    public static final ModItem ORE_SPONGE = new ModItem(GLASS, "Ore Sponge", 0xFFFEBF, "ore_sponge", 24, new ItemStack(COAL_BLOCK), new ItemStack(CLAY), new ItemStack(GLASS));
    public static final FilledOreSponge BASE_FILLED_ORE_SPONGE = new FilledOreSponge();

    public static final Collector COLLECTOR = new Collector();
}
