package net.darkhax.gyth.utils;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class TankData {

    public static Map<String, TankTier> tiers = new HashMap<>();

    public TankData() {

        // Tier 1
        addTankTier("oak", 1, 4, new ItemStack(Blocks.PLANKS, 1, 0));
        addTankTier("spruce", 1, 4, new ItemStack(Blocks.PLANKS, 1, 1));
        addTankTier("birch", 1, 4, new ItemStack(Blocks.PLANKS, 1, 2));
        addTankTier("jungle", 1, 4, new ItemStack(Blocks.PLANKS, 1, 3));
        addTankTier("acacia", 1, 4, new ItemStack(Blocks.PLANKS, 1, 4));
        addTankTier("darkoak", 1, 4, new ItemStack(Blocks.PLANKS, 1, 5));

        // Tier 2
        addTankTier("stone", 2, 16, new ItemStack(Blocks.STONE));
        addTankTier("stonebrick", 2, 16, new ItemStack(Blocks.STONEBRICK));
        addTankTier("sandstone", 2, 16, new ItemStack(Blocks.SANDSTONE));
        addTankTier("cobble", 2, 16, new ItemStack(Blocks.COBBLESTONE));
        addTankTier("netherbrick", 2, 16, new ItemStack(Blocks.NETHER_BRICK));
        addTankTier("brick", 2, 16, new ItemStack(Blocks.BRICK_BLOCK));

        // Tier 3
        addTankTier("iron", 3, 32, new ItemStack(Items.IRON_INGOT), Blocks.IRON_BLOCK);

        // Tier 4
        addTankTier("lapis", 4, 64, new ItemStack(Blocks.LAPIS_BLOCK));
        addTankTier("redstone", 4, 64, new ItemStack(Blocks.REDSTONE_BLOCK));

        // Tier 5
        addTankTier("gold", 5, 128, new ItemStack(Items.GOLD_INGOT), Blocks.GOLD_BLOCK);
        addTankTier("quartz", 5, 128, new ItemStack(Blocks.QUARTZ_BLOCK));

        // Tier 6
        addTankTier("diamond", 6, 256, new ItemStack(Items.DIAMOND), Blocks.DIAMOND_BLOCK);

        // Tier 7
        addTankTier("obsidian", 7, 512, new ItemStack(Blocks.OBSIDIAN));

        // Tier 8
        addTankTier("emerald", 8, 1024, new ItemStack(Blocks.EMERALD_BLOCK));
    }

    public static void addTankTier(String name, int tier, int capacity, ItemStack craftingStack) {

        addTankTier(name, tier, capacity, craftingStack, Block.getBlockFromItem(craftingStack.getItem()));
    }

    public static void addTankTier(String name, int tier, int capacity, ItemStack craftingStack, Block renderBlock) {

        tiers.put(name, new TankTier(name, tier, capacity, craftingStack, renderBlock));
    }
}
