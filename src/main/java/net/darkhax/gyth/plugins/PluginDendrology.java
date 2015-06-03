package net.darkhax.gyth.plugins;

import net.darkhax.gyth.utils.TankData;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.Loader;

public class PluginDendrology {
    
    public PluginDendrology() {
    
        if (Loader.isModLoaded("dendrology")) {
            
            TankData.addTankTier("ancienttree_acemus", 1, 4, new ItemStack(Block.getBlockFromName("dendrology:wood0"), 1, 0));
            TankData.addTankTier("ancienttree_cedrum", 1, 4, new ItemStack(Block.getBlockFromName("dendrology:wood0"), 1, 1));
            TankData.addTankTier("ancienttree_cerasu", 1, 4, new ItemStack(Block.getBlockFromName("dendrology:wood0"), 1, 2));
            TankData.addTankTier("ancienttree_delnas", 1, 4, new ItemStack(Block.getBlockFromName("dendrology:wood0"), 1, 3));
            TankData.addTankTier("ancienttree_ewcaly", 1, 4, new ItemStack(Block.getBlockFromName("dendrology:wood0"), 1, 4));
            TankData.addTankTier("ancienttree_hekur", 1, 4, new ItemStack(Block.getBlockFromName("dendrology:wood0"), 1, 5));
            TankData.addTankTier("ancienttree_kulist", 1, 4, new ItemStack(Block.getBlockFromName("dendrology:wood0"), 1, 7));
            TankData.addTankTier("ancienttree_kiparis", 1, 4, new ItemStack(Block.getBlockFromName("dendrology:wood0"), 1, 6));
            TankData.addTankTier("ancienttree_lata", 1, 4, new ItemStack(Block.getBlockFromName("dendrology:wood0"), 1, 8));
            TankData.addTankTier("ancienttree_nucis", 1, 4, new ItemStack(Block.getBlockFromName("dendrology:wood0"), 1, 9));
            TankData.addTankTier("ancienttree_porffor", 1, 4, new ItemStack(Block.getBlockFromName("dendrology:wood0"), 1, 10));
            TankData.addTankTier("ancienttree_salyx", 1, 4, new ItemStack(Block.getBlockFromName("dendrology:wood0"), 1, 11));
            TankData.addTankTier("ancienttree_tuopa", 1, 4, new ItemStack(Block.getBlockFromName("dendrology:wood0"), 1, 12));
        }
    }
}
