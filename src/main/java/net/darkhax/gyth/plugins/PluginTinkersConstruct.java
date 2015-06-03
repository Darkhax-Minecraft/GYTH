package net.darkhax.gyth.plugins;

import net.darkhax.gyth.utils.EnumTankData;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PluginTinkersConstruct {
    
    // ID == TConstruct
    public PluginTinkersConstruct(boolean loaded) {
    
        if (loaded) {
            
            EnumTankData.addEnumTankData("TINKERSBROWNSTONE", "tinkers_brownstone", 2, 16, new ItemStack((Block) Block.blockRegistry.getObject("TConstruct:SpeedBlock"), 1, 2));
            EnumTankData.addEnumTankData("TINKERSBROWNSTONEROUGH", "tinkers_brownstone_rough", 2, 16, new ItemStack((Block) Block.blockRegistry.getObject("TConstruct:SpeedBlock"), 1, 0));
            EnumTankData.addEnumTankData("TINKERSBROWNSTONEBRICK", "tinkers_brownstone_brick", 2, 16, new ItemStack((Block) Block.blockRegistry.getObject("TConstruct:SpeedBlock"), 1, 3));
            EnumTankData.addEnumTankData("TINKERSSEAREDBRICK", "tinkers_searedbrick", 2, 16, new ItemStack((Block) Block.blockRegistry.getObject("TConstruct:Smeltery"), 1, 2));
            EnumTankData.addEnumTankData("TINKERSSEAREDBRICKNETHER", "tinkers_seared_nether", 2, 16, new ItemStack((Block) Block.blockRegistry.getObject("TConstruct:SmelteryNether"), 1, 2));
            EnumTankData.addEnumTankData("TINKERSNETHERBRICK", "tinkers_netherrack", 2, 16, new ItemStack((Block) Block.blockRegistry.getObject("TConstruct:decoration.multibrick"), 1, 2));
            EnumTankData.addEnumTankData("TINKERSTIN", "tinkers_tin", 3, 32, new ItemStack((Item) Item.itemRegistry.getObject("TConstruct:materials"), 1, 10));
            EnumTankData.addEnumTankData("TINKERSCOPPER", "tinkers_copper", 3, 32, new ItemStack((Item) Item.itemRegistry.getObject("TConstruct:materials"), 1, 9));
            EnumTankData.addEnumTankData("TINKERSBRONZE", "tinkers_bronze", 3, 32, new ItemStack((Item) Item.itemRegistry.getObject("TConstruct:materials"), 1, 13));
            EnumTankData.addEnumTankData("TINKERSALUMINUM", "tinkers_aluminum", 3, 32, new ItemStack((Item) Item.itemRegistry.getObject("TConstruct:materials"), 1, 11));
            EnumTankData.addEnumTankData("TINKERSOBSIDIAN", "tinkers_obsidian", 3, 32, new ItemStack((Block) Block.blockRegistry.getObject("TConstruct:decoration.multibrick"), 1, 0));
            EnumTankData.addEnumTankData("TINKERSBRASS", "tinkers_brass", 4, 64, new ItemStack((Item) Item.itemRegistry.getObject("TConstruct:materials"), 1, 14));
            EnumTankData.addEnumTankData("TINKERSCOBALT", "tinkers_cobalt", 5, 128, new ItemStack((Item) Item.itemRegistry.getObject("TConstruct:materials"), 1, 3));
            EnumTankData.addEnumTankData("TINKERSARDITE", "tinkers_ardite", 5, 128, new ItemStack((Item) Item.itemRegistry.getObject("TConstruct:materials"), 1, 4));
            EnumTankData.addEnumTankData("TINKERSALUMITE", "tinkers_alumite", 5, 128, new ItemStack((Item) Item.itemRegistry.getObject("TConstruct:materials"), 1, 15));
            EnumTankData.addEnumTankData("TINKERSSTEEL", "tinkers_steel", 6, 256, new ItemStack((Item) Item.itemRegistry.getObject("TConstruct:materials"), 1, 16));
            EnumTankData.addEnumTankData("TINKERSMANYULLYN", "tinkers_manyullyn", 6, 256, new ItemStack((Item) Item.itemRegistry.getObject("TConstruct:materials"), 1, 5));
            EnumTankData.addEnumTankData("TINKERSENDER", "tinkers_ender", 6, 256, new ItemStack((Block) Block.blockRegistry.getObject("TConstruct:MetalBlock"), 1, 10));
        }
    }
}
