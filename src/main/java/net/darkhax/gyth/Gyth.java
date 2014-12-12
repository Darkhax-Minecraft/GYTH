package net.darkhax.gyth;

import java.util.Arrays;

import net.darkhax.gyth.common.ProxyCommon;
import net.darkhax.gyth.common.blocks.BlockModularTank;
import net.darkhax.gyth.common.tileentity.TileEntityModularTank;
import net.darkhax.gyth.plugins.PluginManager;
import net.darkhax.gyth.utils.Constants;
import net.minecraft.block.Block;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = Constants.MODID, name = Constants.MOD_NAME, version = Constants.VERSION, dependencies = "required-after:Waila")
public class Gyth {

    @SidedProxy(serverSide = Constants.SERVER, clientSide = Constants.CLIENT)
    public static ProxyCommon proxy;

    @Mod.Instance(Constants.MODID)
    public static Gyth instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        setModMeta(event.getModMetadata());
        proxy.registerBlockRenderers();
        Block modularTank = new BlockModularTank();
        GameRegistry.registerBlock(modularTank, "modularTank");
        GameRegistry.registerTileEntity(TileEntityModularTank.class, "modularTank");
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

        new PluginManager();
    }

    @EventHandler
    public void messageRecieved(FMLInterModComms.IMCEvent event) {

    }

    void setModMeta(ModMetadata meta) {

        meta.authorList = Arrays.asList("Darkhax");
        meta.credits = "Coded by Darkhax";
        meta.description = "This mod adds a modular tank system.";
        meta.autogenerated = false;
    }
}