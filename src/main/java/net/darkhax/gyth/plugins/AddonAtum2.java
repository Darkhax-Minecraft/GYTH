package net.darkhax.gyth.plugins;

import net.darkhax.gyth.Gyth;
import net.darkhax.gyth.api.GythApi;
import net.darkhax.gyth.api.TankTier;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class AddonAtum2 {
    
    public static void initialize() {
        
        createTier("deadwood_planks", 1);
        createTier("palm_planks", 1);
        
        createTier("limestone", 2);
        createTier("limestone_cracked", 2);
        createTier("limestone_brick_small", 2);
        createTier("limestone_brick_large", 2);
        createTier("limestone_brick_cracked_brick", 2);
        createTier("limestone_brick_chiseled", 2);
        createTier("limestone_brick_carved", 2);
        
        createTier("alabaster", 2);
        createTier("alabaster_brick_smooth", 2);
        createTier("alabaster_brick_polished", 2);
        createTier("alabaster_brick_carved", 2);
        createTier("alabaster_brick_tiled", 2);
        createTier("alabaster_brick_pillar", 2);

        createTier("porphyry", 2);
        createTier("porphyry_brick_smooth", 2);
        createTier("porphyry_brick_polished", 2);
        createTier("porphyry_brick_carved", 2);
        createTier("porphyry_brick_tiled", 2);
        createTier("porphyry_brick_pillar", 2);
        
        createTier("ceramic_white", 2);
        createTier("ceramic_orange", 2);
        createTier("ceramic_magenta", 2);
        createTier("ceramic_light_blue", 2);
        createTier("ceramic_yellow", 2);
        createTier("ceramic_lime", 2);
        createTier("ceramic_pink", 2);
        createTier("ceramic_gray", 2);
        createTier("ceramic_silver", 2);
        createTier("ceramic_cyan", 2);
        createTier("ceramic_purple", 2);
        createTier("ceramic_blue", 2);
        createTier("ceramic_brown", 2);
        createTier("ceramic_green", 2);
        createTier("ceramic_red", 2);
        createTier("ceramic_black", 2);
    }
    
    private static TankTier createTier(String name, int tier) {
        
        ResourceLocation id = new ResourceLocation("atum", name);
        Block block = ForgeRegistries.BLOCKS.getValue(id);
        Item itemBlock = ForgeRegistries.ITEMS.getValue(id);
        
        if (block != null && itemBlock != null) {
            
            TankTier tankTier = new TankTier(id, block.getDefaultState(), itemBlock, tier);
            GythApi.REGISTRY.put(id, tankTier);
            return tankTier;
        }
        
        Gyth.LOG.error("Could not register tier {} no block/item found for atum:{}.", name, name);
        return null;
    }
}
