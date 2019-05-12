package net.darkhax.gyth.plugins;

import net.darkhax.gyth.Gyth;
import net.darkhax.gyth.api.GythApi;
import net.darkhax.gyth.api.TankTier;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class AddonTwilightForest {
    
    public static void initialize() {
        
        createTier("tower_wood", "tower_wood", 1, 0);
        createTier("tower_wood_encased", "tower_wood", 1, 1);
        createTier("tower_wood_cracked", "tower_wood", 1, 2);
        createTier("tower_wood_mossy", "tower_wood", 1, 3);
        createTier("tower_wood_infested", "tower_wood", 1, 4);
        
        createTier("twilight_oak_planks", 1);
        createTier("canopy_planks", 1);
        createTier("mangrove_planks", 1);
        createTier("dark_planks", 1);
        createTier("time_planks", 1);
        createTier("trans_planks", 1);
        createTier("mine_planks", 1);
        createTier("sort_planks", 1);
        
        createTier("maze_stone", "maze_stone", 2, 0);
        createTier("maze_stone_brick", "maze_stone", 2, 1);
        createTier("maze_stone_chiseled", "maze_stone", 2, 2);
        createTier("maze_stone_decorative", "maze_stone", 2, 3);
        createTier("maze_stone_cracked_brick", "maze_stone", 2, 4);
        createTier("maze_stone_mossy_brick", "maze_stone", 2, 5);
        createTier("maze_stone_mosaic", "maze_stone", 2, 6);
        createTier("maze_stone_border", "maze_stone", 2, 7);
        
        createTier("deadrock_weathered", "deadrock", 2, 0);
        createTier("deadrock_cracked", "deadrock", 2, 1);
        createTier("deadrock", "deadrock", 2, 2);
        
        createTier("castle_brick", "castle_brick", 2, 0);
        createTier("castle_brick_worn", "castle_brick", 2, 1);
        createTier("castle_brick_cracked", "castle_brick", 2, 2);
        createTier("castle_brick_mossy", "castle_brick", 2, 4);
        createTier("castle_brick_thick", "castle_brick", 2, 5);
        
        createTier("underbrick", "underbrick", 2, 0);
        createTier("underbrick_mossy", "underbrick", 2, 1);
        createTier("underbrick_cracked", "underbrick", 2, 2);
        
        createTier("ironwood", "block_storage", 3, 0);
        createTier("firemetal", "block_storage", 3, 1);
        createTier("nagascale", "block_storage", 3, 2);
    }
    
    private static TankTier createTier(String name, String blockName, int tier, int meta) {
        
        ResourceLocation id = new ResourceLocation("twilightforest", blockName);
        Block block = ForgeRegistries.BLOCKS.getValue(id);
        Item itemBlock = ForgeRegistries.ITEMS.getValue(id);        
        
        if (block != null && itemBlock != null) {
            
            IBlockState state = block.getStateFromMeta(meta);
            
            if (state != null) {
                
                TankTier tankTier = new TankTier(new ResourceLocation("twilightforest", name), state, new ItemStack(itemBlock, 1, meta), tier);
                GythApi.REGISTRY.put(new ResourceLocation("twilightforest", name), tankTier);
                return tankTier;
            }
        }
        
        Gyth.LOG.error("Could not register tier {} no block/item found for twilightforest:{}.", name, blockName);
        return null;
    }
    
    private static TankTier createTier(String name, int tier) {
        
        return createTier(name, name, tier, 0);
    }
}
