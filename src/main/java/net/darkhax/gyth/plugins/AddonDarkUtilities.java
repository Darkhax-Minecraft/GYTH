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

public class AddonDarkUtilities {
    
    public static void initialize() {
        
        createTier("pearl_block", "pearl_block", 2, 0);
        createTier("pearl_block_brick", "pearl_block", 2, 1);
        createTier("pearl_block_carved", "pearl_block", 2, 2);
        createTier("pearl_block_chiseled", "pearl_block", 2, 3);
        
        createTier("wither_block", "wither_block", 2, 0);
        createTier("wither_block_brick", "wither_block", 2, 1);
        createTier("wither_block_carved", "wither_block", 2, 2);
        createTier("wither_block_chiseled", "wither_block", 2, 3);
        createTier("wither_block_corrupt", "wither_block", 2, 4);
        createTier("wither_block_checkered", "wither_block", 2, 5);
    }
    
    private static TankTier createTier(String name, String blockName, int tier, int meta) {
        
        ResourceLocation id = new ResourceLocation("darkutils", blockName);
        Block block = ForgeRegistries.BLOCKS.getValue(id);
        Item itemBlock = ForgeRegistries.ITEMS.getValue(id);        
        
        if (block != null && itemBlock != null) {
            
            IBlockState state = block.getStateFromMeta(meta);
            
            if (state != null) {
                
                TankTier tankTier = new TankTier(new ResourceLocation("darkutils", name), state, new ItemStack(itemBlock, 1, meta), tier);
                GythApi.REGISTRY.put(new ResourceLocation("darkutils", name), tankTier);
                return tankTier;
            }
        }
        
        Gyth.LOG.error("Could not register tier {} no block/item found for darkutils:{}.", name, blockName);
        return null;
    }
    
    private static TankTier createTier(String name, int tier) {
        
        return createTier(name, name, tier, 0);
    }
}
