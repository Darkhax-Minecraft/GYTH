package net.darkhax.gyth.plugins;

import net.darkhax.gyth.utils.EnumTankData;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PluginThaumcraft {

    // ID == Thaumcraft
    public PluginThaumcraft(boolean enabled) {

        if (enabled) {

            EnumTankData.addEnumTankData("THAUMCRAFTGREATWOOD", "thaumcraft_greatwood", 1, 4, new ItemStack(Block.getBlockFromName("Thaumcraft:blockWoodenDevice"), 1, 6));
            EnumTankData.addEnumTankData("THAUMCRAFTSILVERWOOD", "thaumcraft_silverwood", 1, 4, new ItemStack(Block.getBlockFromName("Thaumcraft:blockWoodenDevice"), 1, 7));
            EnumTankData.addEnumTankData("THAUMCRAFTARCANESTONE", "thaumcraft_arcanestone", 2, 16, new ItemStack(Block.getBlockFromName("Thaumcraft:blockCosmeticSolid"), 1, 6));
            EnumTankData.addEnumTankData("THAUMCRAFTTHAUMIUM", "thaumcraft_thaumium", 5, 128, new ItemStack((Item) Item.itemRegistry.getObject("Thaumcraft:ItemResource"), 1, 2));
        }
    }
}