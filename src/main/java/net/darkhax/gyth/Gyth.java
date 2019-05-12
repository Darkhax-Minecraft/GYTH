package net.darkhax.gyth;

import net.darkhax.bookshelf.registry.RegistryHelper;
import net.darkhax.bookshelf.util.OreDictUtils;
import net.darkhax.bookshelf.util.StackUtils;
import net.darkhax.gyth.api.GythApi;
import net.darkhax.gyth.api.TankTier;
import net.darkhax.gyth.blocks.BlockTank;
import net.darkhax.gyth.common.ProxyCommon;
import net.darkhax.gyth.items.ItemBlockTank;
import net.darkhax.gyth.items.ItemTankUpgrade;
import net.darkhax.gyth.libs.ConfigurationHandler;
import net.darkhax.gyth.plugins.AddonAtum2;
import net.darkhax.gyth.plugins.AddonDarkUtilities;
import net.darkhax.gyth.plugins.AddonTwilightForest;
import net.darkhax.gyth.plugins.PluginMineTweaker;
import net.darkhax.gyth.tabs.CreativeTabGyth;
import net.darkhax.gyth.tileentity.TileEntityModularTank;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLInterModComms.IMCEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms.IMCMessage;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@Mod(modid = Gyth.MOD_ID, name = Gyth.MOD_NAME, version = "@VERSION@", dependencies = Gyth.DEPENDENCIES, certificateFingerprint = "@FINGERPRINT@")
@EventBusSubscriber
public class Gyth {

    public static final String MOD_ID = "gyth";

    public static final String MOD_NAME = "Get Ya' Tanks Here";

    public static final String DEPENDENCIES = "required-after:bookshelf@[2.3,)";

    public static final Logger LOG = LogManager.getLogger(MOD_NAME);

    public static final CreativeTabs TAB = new CreativeTabGyth();

    public static final RegistryHelper REGISTRY = new RegistryHelper(MOD_ID).setTab(TAB).enableAutoRegistration();

    @SidedProxy(serverSide = "net.darkhax.gyth.common.ProxyCommon", clientSide = "net.darkhax.gyth.client.ProxyClient")
    public static ProxyCommon proxy;

    @Instance(Gyth.MOD_ID)
    public static Gyth instance;

    public static Block blockModularTanks;

    public static Item itemTankUpgrade;

    public static ItemBlock itemBlockModularTank;

    @EventHandler
    public void preInit (FMLPreInitializationEvent event) {

        ConfigurationHandler.initConfig(event.getSuggestedConfigurationFile());
        blockModularTanks = new BlockTank();
        itemBlockModularTank = new ItemBlockTank(blockModularTanks);
        itemBlockModularTank.setHasSubtypes(true);
        REGISTRY.registerBlock(blockModularTanks, itemBlockModularTank, "modular_tank");
        GameRegistry.registerTileEntity(TileEntityModularTank.class, new ResourceLocation(MOD_ID, "modularTank"));

        itemTankUpgrade = new ItemTankUpgrade();
        itemTankUpgrade.setHasSubtypes(true);
        REGISTRY.registerItem(itemTankUpgrade, "tank_upgrade");

        proxy.registerBlockRenderers();
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void registerRecipes(Register<IRecipe> event) {
        
        if (Loader.isModLoaded("atum")) {
            
            LOG.info("Loading Atum2 support. These tanks belong in a museum!");
            AddonAtum2.initialize();
        }
        
        if (Loader.isModLoaded("twilightforest")) {
            
            LOG.info("Loading Twilight Forest support. You've just crossed over into the Twilight Zone.");
            AddonTwilightForest.initialize();
        }
        
        if (Loader.isModLoaded("darkutils")) {
            
            LOG.info("Loading Dark Utils support. This seems oddly familiar.");
            AddonDarkUtilities.initialize();
        }
        
        for (final TankTier tier : GythApi.REGISTRY.values()) {

            if (tier.tier == 1) {

                REGISTRY.addShapedRecipe("modular_tank_" + tier.identifier.getPath(), GythApi.createTieredTank(tier), "xyx", "yzy", "xyx", 'x', tier.recipe, 'y', OreDictUtils.PANE_GLASS, 'z', Items.BUCKET);
            }

            REGISTRY.addShapedRecipe("tank_upgrade_" + tier.identifier.getPath(), GythApi.createTierUpgrade(tier), "xyx", "yxy", "xyx", 'x', tier.recipe, 'y', OreDictUtils.PANE_GLASS);

        }
    }
    
    @EventHandler
    public void init (FMLInitializationEvent event) {

        GythApi.REGISTRY = GythApi.REGISTRY.entrySet().stream().sorted(Entry.comparingByValue()).collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        if (Loader.isModLoaded("waila")) {
            FMLInterModComms.sendMessage("waila", "register", "net.darkhax.gyth.plugins.PluginWaila.registerAddon");
        }

        if (Loader.isModLoaded("theoneprobe")) {
            FMLInterModComms.sendFunctionMessage("theoneprobe", "getTheOneProbe", "net.darkhax.gyth.plugins.PluginTOP$GetTheOneProbe");
        }
    }
    
    @EventHandler
    public void handleIMC (IMCEvent event) {

        for (final IMCMessage msg : event.getMessages())
            if (msg.key.equalsIgnoreCase("addTier") && msg.isNBTMessage()) {

                final NBTTagCompound tag = msg.getNBTValue();
                final String name = tag.getString("tierName");
                final Block block = Block.getBlockFromName(tag.getString("blockId"));
                final int meta = tag.getInteger("meta");
                final int tier = tag.getInteger("tier");
                final Object recipe = this.getRecipeFromStackString(tag.getString("recipe"));

                if (!name.isEmpty() && block != null && meta >= 0 && tier >= 0 && recipe != null) {
                    GythApi.createTier(msg.getSender(), name, block, meta, recipe, Math.min(10, tier));
                }
                else {
                    LOG.info(msg.getSender() + " tried to register a tier, but it failed.");
                }
            }

            else if (msg.key.equalsIgnoreCase("removeTier") && msg.isResourceLocationMessage()) {
                GythApi.removeTier(msg.getSender(), msg.getResourceLocationValue());
            }
    }

    private Object getRecipeFromStackString (String string) {

        final ItemStack stack = StackUtils.createStackFromString(string);
        return !stack.isEmpty() ? stack : string;
    }
}