package net.darkhax.gyth.utils;

import java.util.HashMap;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class TankData {
    
    public static HashMap<String, TankTier> tiers = new HashMap();
    
    public TankData() {
    
        // Tier 1
        addTankTier("oak", 1, 4, new ItemStack(Blocks.planks, 1, 0));
        addTankTier("spruce", 1, 4, new ItemStack(Blocks.planks, 1, 1));
        addTankTier("birch", 1, 4, new ItemStack(Blocks.planks, 1, 2));
        addTankTier("jungle", 1, 4, new ItemStack(Blocks.planks, 1, 3));
        addTankTier("acacia", 1, 4, new ItemStack(Blocks.planks, 1, 4));
        addTankTier("darkoak", 1, 4, new ItemStack(Blocks.planks, 1, 5));
        
        // Tier 2
        addTankTier("stone", 2, 16, new ItemStack(Blocks.stone));
        addTankTier("stonebrick", 2, 16, new ItemStack(Blocks.stonebrick));
        addTankTier("sandstone", 2, 16, new ItemStack(Blocks.sandstone));
        addTankTier("cobble", 2, 16, new ItemStack(Blocks.cobblestone));
        addTankTier("netherbrick", 2, 16, new ItemStack(Blocks.nether_brick));
        addTankTier("brick", 2, 16, new ItemStack(Blocks.brick_block));
        
        // Tier 3
        addTankTier("iron", 3, 32, new ItemStack(Items.iron_ingot));
        
        // Tier 4
        addTankTier("lapis", 4, 64, new ItemStack(Blocks.lapis_block));
        addTankTier("redstone", 4, 64, new ItemStack(Blocks.redstone_block));
        
        // Tier 5
        addTankTier("gold", 5, 128, new ItemStack(Items.gold_ingot));
        addTankTier("quartz", 5, 128, new ItemStack(Blocks.quartz_block));
        
        // Tier 6
        addTankTier("diamond", 6, 256, new ItemStack(Items.diamond));
        
        // Tier 7
        addTankTier("obsidian", 7, 512, new ItemStack(Blocks.obsidian));
        
        // Tier 8
        addTankTier("emerald", 8, 1024, new ItemStack(Blocks.emerald_block));
    }
    
    public static void addTankTier (String name, int tier, int capacity, ItemStack craftingStack) {
    
        tiers.put(name, new TankTier(name, tier, capacity, craftingStack));
    }
}
