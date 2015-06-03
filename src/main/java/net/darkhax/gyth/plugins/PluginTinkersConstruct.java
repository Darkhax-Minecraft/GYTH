package net.darkhax.gyth.plugins;

import net.darkhax.gyth.utils.TankData;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.Loader;

public class PluginTinkersConstruct {
    
    // ID == TConstruct
    public PluginTinkersConstruct() {
    
        if (Loader.isModLoaded("TConstruct")) {
            
            TankData.addTankTier("tinkers_brownstone", 2, 16, new ItemStack((Block) Block.blockRegistry.getObject("TConstruct:SpeedBlock"), 1, 2));
            TankData.addTankTier("tinkers_brownstone_rough", 2, 16, new ItemStack((Block) Block.blockRegistry.getObject("TConstruct:SpeedBlock"), 1, 0));
            TankData.addTankTier("tinkers_brownstone_brick", 2, 16, new ItemStack((Block) Block.blockRegistry.getObject("TConstruct:SpeedBlock"), 1, 3));
            TankData.addTankTier("tinkers_searedbrick", 2, 16, new ItemStack((Block) Block.blockRegistry.getObject("TConstruct:Smeltery"), 1, 2));
            TankData.addTankTier("tinkers_seared_nether", 2, 16, new ItemStack((Block) Block.blockRegistry.getObject("TConstruct:SmelteryNether"), 1, 2));
            TankData.addTankTier("tinkers_netherrack", 2, 16, new ItemStack((Block) Block.blockRegistry.getObject("TConstruct:decoration.multibrick"), 1, 2));
            TankData.addTankTier("tinkers_tin", 3, 32, new ItemStack((Item) Item.itemRegistry.getObject("TConstruct:materials"), 1, 10));
            TankData.addTankTier("tinkers_copper", 3, 32, new ItemStack((Item) Item.itemRegistry.getObject("TConstruct:materials"), 1, 9));
            TankData.addTankTier("tinkers_bronze", 3, 32, new ItemStack((Item) Item.itemRegistry.getObject("TConstruct:materials"), 1, 13));
            TankData.addTankTier("tinkers_aluminum", 3, 32, new ItemStack((Item) Item.itemRegistry.getObject("TConstruct:materials"), 1, 11));
            TankData.addTankTier("tinkers_obsidian", 3, 32, new ItemStack((Block) Block.blockRegistry.getObject("TConstruct:decoration.multibrick"), 1, 0));
            TankData.addTankTier("tinkers_brass", 4, 64, new ItemStack((Item) Item.itemRegistry.getObject("TConstruct:materials"), 1, 14));
            TankData.addTankTier("tinkers_cobalt", 5, 128, new ItemStack((Item) Item.itemRegistry.getObject("TConstruct:materials"), 1, 3));
            TankData.addTankTier("tinkers_ardite", 5, 128, new ItemStack((Item) Item.itemRegistry.getObject("TConstruct:materials"), 1, 4));
            TankData.addTankTier("tinkers_alumite", 5, 128, new ItemStack((Item) Item.itemRegistry.getObject("TConstruct:materials"), 1, 15));
            TankData.addTankTier("tinkers_steel", 6, 256, new ItemStack((Item) Item.itemRegistry.getObject("TConstruct:materials"), 1, 16));
            TankData.addTankTier("tinkers_manyullyn", 6, 256, new ItemStack((Item) Item.itemRegistry.getObject("TConstruct:materials"), 1, 5));
            TankData.addTankTier("tinkers_ender", 6, 256, new ItemStack((Block) Block.blockRegistry.getObject("TConstruct:MetalBlock"), 1, 10));
        }
    }
}
