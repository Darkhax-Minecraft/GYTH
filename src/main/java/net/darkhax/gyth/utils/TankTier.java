package net.darkhax.gyth.utils;

import net.darkhax.gyth.Gyth;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TankTier {
    
    private String name;
    private int tier;
    private int capacity;
    private ItemStack craftingStack;
    private final IBlockState renderBlock;
    
    /**
     * Creates a new instance of a TankTier. This is an object which represents all the
     * information related to a tier.
     * 
     * @param tierName : A string which represents a tier. This should be unique, and is used
     *        for textures.
     * @param tierLevel : An integer which represents what level this tier is on. This is used
     *        for enforcing the tier based upgrade system.
     * @param tierCapacity : The capacity of this tank tier. This represents the total capacity
     *        of this tier, not how much capacity will be added. This value is represented
     *        using buckets (1000 mb).
     * @param crafting : An ItemStack which represents the item needed for crafting this tank
     * @param renderBlock
     */
    public TankTier(String tierName, int tierLevel, int tierCapacity, ItemStack crafting, Block renderBlock) {
        
        this.name = tierName;
        this.tier = tierLevel;
        this.capacity = tierCapacity;
        this.craftingStack = crafting;
        this.renderBlock = renderBlock.getDefaultState();
    }
    
    /**
     * A basic setter for the tier name. The tier name is a unique identifier used to represent
     * this tier. It is also used for textures.
     *
     * @param newName: The new name to set for this TankTier.
     */
    public void setName (String newName) {
        
        this.name = newName;
    }
    
    /**
     * Retrieves the tank tier name. The tier name is a unique identifier used to represent a
     * tank tier. It is also used for textures.
     *
     * @return String: The tier name for this TankTier, as a string.
     */
    public String getName () {
        
        return this.name;
    }
    
    /**
     * A basic setter for the tank tier value. The tier value is used to represent what stage a
     * tier is at in the tier tree.
     *
     * @param newTier: The new tier value for this TankTier.
     */
    public void setTier (int newTier) {
        
        this.tier = newTier;
    }
    
    /**
     * Retrieves the tank tier value. The tier value is used to represent what stage a tier is
     * at in the tier tree.
     *
     * @return int: The tank tier value for this TankTier.
     */
    public int getTier () {
        
        return this.tier;
    }
    
    /**
     * A basic setter for the tier capacity. Capacity represents the amount of buckets a tank
     * with this tier can hold. One bucket represents 1000 mb.
     *
     * @param newCapacity: The new capacity value for this TankTier.
     */
    public void setCapacity (int newCapacity) {
        
        this.capacity = newCapacity;
    }
    
    /**
     * Retrieves the capacity for this TankTier. Capacity represents the amount of buckets a
     * tank with this tier can hold. One bucket represents 1000 mb.
     *
     * @return int: The amount of buckets a tank with this tier can store.
     */
    public int getCapacity () {
        
        return this.capacity;
    }
    
    /**
     * A basic setter for the crafting stack of this TankTier. The crafting stack is the
     * primary crafting ingredient for a tank tier.
     *
     * @param newStack: The new ItemStack to use for crafting.
     */
    public void setCraftingStack (ItemStack newStack) {
        
        this.craftingStack = newStack;
    }
    
    /**
     * Retrieves the crafting stack for this TankTier. The crafting stack is the primary
     * crafting ingredient for a tank tier.
     *
     * @return ItemStack: The primary crafting stack for this TankTier.
     */
    public ItemStack getCraftingStack () {
        
        return this.craftingStack;
    }
    
    /**
     * Creates an ItemStack representation of this TankTier as an actual tank Item.
     *
     * @return ItemStack: A new ItemStack which represents this TankTier as an actual item.
     */
    public ItemStack getTankItemStack () {
        
        final ItemStack stack = new ItemStack(Gyth.blockModularTanks);
        final NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("Tier", this.getTier());
        tag.setString("TierName", this.getName());
        tag.setInteger("TankCapacity", this.getCapacity());
        stack.setTagCompound(tag);
        return stack;
    }
    
    public ItemStack getUpgradeItemStack () {
        
        final ItemStack stack = new ItemStack(Gyth.itemTankUpgrade);
        final NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("Tier", this.getTier());
        tag.setInteger("TankCapacity", this.getCapacity());
        tag.setString("TierName", this.getName());
        stack.setTagCompound(tag);
        return stack;
    }
    
    public IBlockState getRenderBlock () {
        
        return this.renderBlock;
    }
}