package net.darkhax.gyth;

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
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
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
    
    @Mod.EventHandler
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
    
    @Mod.EventHandler
    public void init (FMLInitializationEvent event) {
        
        proxy.registerBlockRenderers();
        
        for (final TankTier tier : GythApi.REGISTRY) {
            
            if (tier.tier == 1)
                GameRegistry.addRecipe(new ShapedOreRecipe(GythApi.createTieredTank(tier), new Object[] { "xyx", "yzy", "xyx", 'x', tier.recipe, 'y', OreDictUtils.PANE_GLASS, 'z', Items.BUCKET }));
                
            GameRegistry.addRecipe(new ShapedOreRecipe(GythApi.createTierUpgrade(tier), new Object[] { "xyx", "yxy", "xyx", 'x', tier.recipe, 'y', OreDictUtils.PANE_GLASS }));
        }
    }
}