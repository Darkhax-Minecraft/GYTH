package net.darkhax.gyth.api;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

public class TankTier {
    
    /**
     * The identifier for the tier type. Follows same format as the rest of the game.
     */
    public final ResourceLocation identifier;
    
    /**
     * The crafting ingredient for the tier. Can be ItemStack, Item, Block, or oredict name.
     */
    public final Object recipe;
    
    /**
     * The tier of the tank. Used to establish the upgrade hierarchy.
     */
    public final int tier;
    
    /**
     * The block state to use when rendering this tier.
     */
    public final IBlockState renderState;
    
    /**
     * Constructs the tank tier. Does not register it.
     * 
     * @param identifier The identifier for the tier. Follows standard mc format.
     * @param renderState The block to use in tank rendering.
     * @param recipe The crafting ingredient for the tier.
     * @param tier The tier of the tank, used to establish the upgrade hierarchy.
     */
    public TankTier(ResourceLocation identifier, IBlockState renderState, Object recipe, int tier) {
        
        this.identifier = identifier;
        this.renderState = renderState;
        this.recipe = recipe;
        this.tier = tier;
    }
    
    /**
     * Gets the capacity for the tier. By default, a tier capcity is equal to 4^tier buckets.
     * 
     * @return The capacity held by this tier.
     */
    public int getCapacity () {
        
        return (int) Math.pow(4d, this.tier) * 1000;
    }
    
    /**
     * Checks if a tier can be upgraded into another one.
     * 
     * @param upgradeTier The new tier being applied.
     * @return Whether or not the new tier is allowed.
     */
    public boolean canApplyUpgrage (TankTier upgradeTier) {
        
        return upgradeTier.tier > this.tier;
    }
}
