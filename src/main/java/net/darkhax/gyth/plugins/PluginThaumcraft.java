package net.darkhax.gyth.plugins;

import net.darkhax.gyth.utils.TankData;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;

public class PluginThaumcraft {
    
    // ID == Thaumcraft
    public PluginThaumcraft() {
    
        if (Loader.isModLoaded("Thaumcraft")) {
            
            TankData.addTankTier("thaumcraft_greatwood", 1, 4, new ItemStack(Block.getBlockFromName("Thaumcraft:blockWoodenDevice"), 1, 6));
            TankData.addTankTier("thaumcraft_silverwood", 1, 4, new ItemStack(Block.getBlockFromName("Thaumcraft:blockWoodenDevice"), 1, 7));
            TankData.addTankTier("thaumcraft_arcanestone", 2, 16, new ItemStack(Block.getBlockFromName("Thaumcraft:blockCosmeticSolid"), 1, 6));
            TankData.addTankTier("thaumcraft_thaumium", 5, 128, new ItemStack(Item.getByNameOrId("Thaumcraft:ItemResource"), 1, 2));
        }
    }
}