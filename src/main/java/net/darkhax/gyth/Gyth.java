package net.darkhax.gyth;

import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import net.darkhax.bookshelf.lib.util.ItemStackUtils;
import net.darkhax.bookshelf.lib.util.OreDictUtils;
import net.darkhax.gyth.api.GythApi;
import net.darkhax.gyth.api.TankTier;
import net.darkhax.gyth.blocks.BlockTank;
import net.darkhax.gyth.common.ProxyCommon;
import net.darkhax.gyth.items.ItemBlockTank;
import net.darkhax.gyth.items.ItemTankUpgrade;
import net.darkhax.gyth.libs.Constants;
import net.darkhax.gyth.tabs.CreativeTabGyth;
import net.darkhax.gyth.tileentity.TileEntityModularTank;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms.IMCEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms.IMCMessage;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

@Mod(modid = Constants.MODID, name = Constants.MOD_NAME, version = Constants.VERSION, dependencies = "required-after:bookshelf@[1.4.0.318,)")
public class Gyth {
    
    @SidedProxy(serverSide = Constants.SERVER, clientSide = Constants.CLIENT)
    public static ProxyCommon proxy;
    
    @Instance(Constants.MODID)
    public static Gyth instance;
    
    public static Block blockModularTanks;
    public static Item itemTankUpgrade;
    public static Item itemBlockModularTank;
    
    public static CreativeTabs tabGyth;
    
    @EventHandler
    public void preInit (FMLPreInitializationEvent event) {
        
        tabGyth = new CreativeTabGyth();
        blockModularTanks = new BlockTank();
        itemBlockModularTank = new ItemBlockTank(blockModularTanks);
        GameRegistry.register(blockModularTanks);
        GameRegistry.register(itemBlockModularTank);
        GameRegistry.registerTileEntity(TileEntityModularTank.class, "modularTank");
        
        itemTankUpgrade = new ItemTankUpgrade();
        GameRegistry.register(itemTankUpgrade);
        
        proxy.registerBlockRenderers();
    }
    
    @EventHandler
    public void init (FMLInitializationEvent event) {
        
        GythApi.REGISTRY = GythApi.REGISTRY.entrySet().stream().sorted(Entry.comparingByValue()).collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        
        proxy.registerBlockRenderers();
        
        for (final TankTier tier : GythApi.REGISTRY.values()) {
            
            if (tier.tier == 1)
                GameRegistry.addRecipe(new ShapedOreRecipe(GythApi.createTieredTank(tier), new Object[] { "xyx", "yzy", "xyx", 'x', tier.recipe, 'y', OreDictUtils.PANE_GLASS, 'z', Items.BUCKET }));
                
            GameRegistry.addRecipe(new ShapedOreRecipe(GythApi.createTierUpgrade(tier), new Object[] { "xyx", "yxy", "xyx", 'x', tier.recipe, 'y', OreDictUtils.PANE_GLASS }));
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
                
                if (!name.isEmpty() && block != null && meta >= 0 && tier >= 0 && recipe != null)
                    GythApi.createTier(msg.getSender(), name, block, meta, recipe, Math.min(10, tier));
                    
                else
                    Constants.LOG.info(msg.getSender() + " tried to register a tier, but it failed.");
            }
            
            else if (msg.key.equalsIgnoreCase("removeTier") && msg.isResourceLocationMessage())
                GythApi.removeTier(msg.getSender(), msg.getResourceLocationValue());
    }
    
    private Object getRecipeFromStackString (String string) {
        
        final ItemStack stack = ItemStackUtils.createStackFromString(string);
        return ItemStackUtils.isValidStack(stack) ? stack : string;
    }
}