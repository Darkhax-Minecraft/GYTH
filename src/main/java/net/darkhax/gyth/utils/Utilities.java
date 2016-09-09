package net.darkhax.gyth.utils;

import net.darkhax.gyth.Gyth;
import net.darkhax.gyth.tileentity.TileEntityModularTank;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

public class Utilities {
    
    /**
     * Safely consumes an item, without losing any container data. Useful for things like
     * buckets or bottles.
     *
     * @param stack: The ItemStack being used.
     * @return ItemStack: The returned ItemStack.
     */
    public static ItemStack useItemSafely (ItemStack stack) {
        
        if (stack.stackSize == 1) {
            
            if (stack.getItem().hasContainerItem(stack))
                return stack.getItem().getContainerItem(stack);
            else
                return null;
        }
        else {
            
            stack.splitStack(1);
            return stack;
        }
    }
    
    /**
     * Safely drops an ItemStack into the world as an EntityItem.
     *
     * @param world Instance of the world.
     * @param pos The position for the item to spawn.
     * @param stack The ItemStack being dropped into the world.
     */
    public static void dropStackInWorld (World world, BlockPos pos, ItemStack stack) {
        
        if (!world.isRemote && world.getGameRules().getBoolean("doTileDrops")) {
            
            final float f = 0.7F;
            final double d0 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
            final double d1 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
            final double d2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
            final EntityItem entityitem = new EntityItem(world, pos.getX() + d0, pos.getY() + d1, pos.getZ() + d2, stack);
            entityitem.setPickupDelay(10);
            world.spawnEntityInWorld(entityitem);
        }
    }
    
    public static ItemStack getTankStackFromTile (TileEntityModularTank tank, boolean keepFluid) {
        
        final ItemStack stack = new ItemStack(Gyth.blockModularTanks);
        stack.setTagCompound(new NBTTagCompound());
        final FluidStack fluid = tank.tank.getFluid();
        
        if (fluid != null && keepFluid) {
            
            final NBTTagCompound tagFluid = new NBTTagCompound();
            fluid.writeToNBT(tagFluid);
            stack.getTagCompound().setTag("Fluid", tagFluid);
        }
        
        stack.getTagCompound().setInteger("Tier", tank.tier);
        stack.getTagCompound().setString("TierName", tank.tierName);
        stack.getTagCompound().setInteger("TankCapacity", tank.tank.getCapacity() / FluidContainerRegistry.BUCKET_VOLUME);
        
        return stack;
    }
}
