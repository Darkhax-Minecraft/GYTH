package net.darkhax.gyth;

import net.darkhax.gyth.blocks.BlockModularTank;
import net.darkhax.gyth.common.ProxyCommon;
import net.darkhax.gyth.handler.ConfigurationHandler;
import net.darkhax.gyth.handler.CraftingHandler;
import net.darkhax.gyth.items.ItemBlockModularTank;
import net.darkhax.gyth.items.ItemTankUpgrade;
import net.darkhax.gyth.plugins.PluginManager;
import net.darkhax.gyth.tabs.CreativeTabGyth;
import net.darkhax.gyth.tileentity.TileEntityModularTank;
import net.darkhax.gyth.utils.Constants;
import net.darkhax.gyth.utils.TankData;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Constants.MODID, name = Constants.MOD_NAME, version = Constants.VERSION, guiFactory = Constants.FACTORY)
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
        
        new TankData();
        new PluginManager();
        new ConfigurationHandler(event.getSuggestedConfigurationFile());
        
        tabGyth = new CreativeTabGyth();
        blockModularTanks = new BlockModularTank();
        itemBlockModularTank = new ItemBlockModularTank(blockModularTanks);
        GameRegistry.register(blockModularTanks);
        GameRegistry.register(itemBlockModularTank);
        GameRegistry.registerTileEntity(TileEntityModularTank.class, "modularTank");
        
        itemTankUpgrade = new ItemTankUpgrade();
        GameRegistry.register(itemTankUpgrade);
        
        proxy.registerBlockRenderers();
    }
    
    @Mod.EventHandler
    public void init (FMLInitializationEvent event) {
        
        new CraftingHandler();
        
        proxy.registerBlockRenderers();
    }
    
    @Mod.EventHandler
    public void messageRecieved (FMLInterModComms.IMCEvent event) {
    
    }
}