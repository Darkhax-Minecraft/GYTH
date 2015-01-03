package net.darkhax.gyth.plugins;

import net.darkhax.gyth.utils.EnumTankData;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class PluginDendrology {

    // ID == dendrology
    public PluginDendrology(boolean enabled) {

        if (enabled) {

            EnumTankData.addEnumTankData("ANCIENTTREEACEMUS", "ancienttree_acemus", 1, 4, new ItemStack(Block.getBlockFromName("dendrology:wood0"), 1, 0));
            EnumTankData.addEnumTankData("ANCIENTTREECEDRUM", "ancienttree_cedrum", 1, 4, new ItemStack(Block.getBlockFromName("dendrology:wood0"), 1, 1));
            EnumTankData.addEnumTankData("ANCIENTTREECERASU", "ancienttree_cerasu", 1, 4, new ItemStack(Block.getBlockFromName("dendrology:wood0"), 1, 2));
            EnumTankData.addEnumTankData("ANCIENTTREEDELNAS", "ancienttree_delnas", 1, 4, new ItemStack(Block.getBlockFromName("dendrology:wood0"), 1, 3));
            EnumTankData.addEnumTankData("ANCIENTTREEEWCALY", "ancienttree_ewcaly", 1, 4, new ItemStack(Block.getBlockFromName("dendrology:wood0"), 1, 4));
            EnumTankData.addEnumTankData("ANCIENTTREEHEKUR", "ancienttree_hekur", 1, 4, new ItemStack(Block.getBlockFromName("dendrology:wood0"), 1, 5));
            EnumTankData.addEnumTankData("ANCIENTTREEKULIST", "ancienttree_kulist", 1, 4, new ItemStack(Block.getBlockFromName("dendrology:wood0"), 1, 7));
            EnumTankData.addEnumTankData("ANCIENTTREEKIPARIS", "ancienttree_kiparis", 1, 4, new ItemStack(Block.getBlockFromName("dendrology:wood0"), 1, 6));
            EnumTankData.addEnumTankData("ANCIENTTREELATA", "ancienttree_lata", 1, 4, new ItemStack(Block.getBlockFromName("dendrology:wood0"), 1, 8));
            EnumTankData.addEnumTankData("ANCIENTTREENUCIS", "ancienttree_nucis", 1, 4, new ItemStack(Block.getBlockFromName("dendrology:wood0"), 1, 9));
            EnumTankData.addEnumTankData("ANCIENTTREEPORFFOR", "ancienttree_porffor", 1, 4, new ItemStack(Block.getBlockFromName("dendrology:wood0"), 1, 10));
            EnumTankData.addEnumTankData("ANCIENTTREESALYX", "ancienttree_salyx", 1, 4, new ItemStack(Block.getBlockFromName("dendrology:wood0"), 1, 11));
            EnumTankData.addEnumTankData("ANCIENTTREETUOPA", "ancienttree_tuopa", 1, 4, new ItemStack(Block.getBlockFromName("dendrology:wood0"), 1, 12));
        }
    }
}
