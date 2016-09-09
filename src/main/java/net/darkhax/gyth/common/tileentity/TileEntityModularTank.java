package net.darkhax.gyth.common.tileentity;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityModularTank extends TileEntity implements IFluidHandler {
    
    public FluidTank tank;
    public int tier = 1;
    public String tierName = "oak";
    
    public TileEntityModularTank() {
        
        this.tank = new FluidTank(FluidContainerRegistry.BUCKET_VOLUME * 2);
    }
    
    public void setTankTier (int tankTier) {
        
        this.tier = tankTier;
    }
    
    public int getTankTier () {
        
        return this.tier;
    }
    
    public void upgradeTank (ItemStack upgradeItem) {
        
        if (upgradeItem.getItem() != null && upgradeItem.hasTagCompound() && this.getTankTier() + 1 == upgradeItem.getTagCompound().getInteger("TankTier")) {
            
            this.setTankTier(upgradeItem.getTagCompound().getInteger("TankTier"));
            this.setTankCapacity(this.getTankTier() * 16);
        }
    }
    
    /**
     * Sets the capacity of the tank based on buckets.
     *
     * @param tankCapacityModifier: The amount of buckets the tank can hold.
     */
    public void setTankCapacity (int tankCapacityModifier) {
        
        this.tank.setCapacity(tankCapacityModifier);
    }
    
    /**
     * Decreases the capacity of the tank based on buckets. If the decrease amount is more than
     * the tank capacity, the capacity will be set to 0.
     *
     * @param tankCapacityModifier: The amount of buckets to decrease the tanks capacity by.
     */
    public void decreaseTankCapacity (int tankCapacityModifier) {
        
        if (this.tank.getCapacity() > FluidContainerRegistry.BUCKET_VOLUME * tankCapacityModifier)
            this.tank.setCapacity(this.tank.getCapacity() - FluidContainerRegistry.BUCKET_VOLUME * tankCapacityModifier);
            
        else
            this.tank.setCapacity(0);
    }
    
    /**
     * Increases the capacity of the tank based on buckets.
     *
     * @param tankCapacityModifier: The amount of additional buckets this tank can hold.
     */
    public void increaseTankCapacity (int tankCapacityModifier) {
        
        this.tank.setCapacity(this.tank.getCapacity() + FluidContainerRegistry.BUCKET_VOLUME * tankCapacityModifier);
    }
    
    @Override
    public void onDataPacket (NetworkManager net, SPacketUpdateTileEntity pkt) {
        
        this.readFromNBT(pkt.getNbtCompound());
        // this.worldObj.func_147479_m(xCoord, yCoord, zCoord);
    }
    
    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket () {
        
        final NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        return new SPacketUpdateTileEntity(this.pos, this.getBlockMetadata(), nbt);
    }
    
    @Override
    public void readFromNBT (NBTTagCompound nbt) {
        
        super.readFromNBT(nbt);
        
        this.tier = nbt.getInteger("tier");
        this.tierName = nbt.getString("tierName");
        this.tank.setCapacity(nbt.getInteger("TankCapacity") * FluidContainerRegistry.BUCKET_VOLUME);
        if (nbt.getBoolean("hasFluid"))
            this.tank.setFluid(FluidRegistry.getFluidStack(nbt.getString("fluidName"), nbt.getInteger("fluidAmount")));
        else
            this.tank.setFluid(null);
    }
    
    @Override
    public NBTTagCompound writeToNBT (NBTTagCompound nbt) {
        
        super.writeToNBT(nbt);
        
        nbt.setBoolean("hasFluid", this.tank.getFluid() != null);
        
        if (this.tank.getFluid() != null) {
            
            nbt.setString("fluidName", this.tank.getFluid().getFluid().getName());
            nbt.setInteger("fluidAmount", this.tank.getFluidAmount());
        }
        
        nbt.setInteger("tier", this.tier);
        nbt.setInteger("TankCapacity", this.tank.getCapacity() / FluidContainerRegistry.BUCKET_VOLUME);
        nbt.setString("tierName", this.tierName);
        return nbt;
    }
    
    @Override
    public int fill (EnumFacing from, FluidStack resource, boolean doFill) {
        
        final int fluid = this.tank.fill(resource, doFill);
        
        if (fluid > 0 && doFill) {
            
            // worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            // worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord,
            // this.getBlockType());
        }
        
        return fluid;
    }
    
    @Override
    public FluidStack drain (EnumFacing from, FluidStack resource, boolean doDrain) {
        
        if (this.tank.getFluidAmount() > 0 && this.tank.getFluid().getFluid() == resource.getFluid())
            return this.drain(from, resource.amount, doDrain);
            
        return null;
    }
    
    @Override
    public FluidStack drain (EnumFacing from, int maxDrain, boolean doDrain) {
        
        final FluidStack fluidStack = this.tank.drain(maxDrain, doDrain);
        
        if (fluidStack != null && doDrain) {
            
            // worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            // worldObj.notifyBlockOfNeighborChange(xCoord, yCoord, zCoord,
            // this.getBlockType());
        }
        
        return fluidStack;
    }
    
    @Override
    public boolean canFill (EnumFacing from, Fluid fluid) {
        
        return this.tank.getFluidAmount() == 0 || this.tank.getFluid().getFluid() == fluid && this.tank.getFluidAmount() < this.tank.getCapacity();
    }
    
    @Override
    public boolean canDrain (EnumFacing from, Fluid fluid) {
        
        return this.tank.getFluidAmount() > 0;
    }
    
    @Override
    public FluidTankInfo[] getTankInfo (EnumFacing from) {
        
        final FluidStack fluid = this.tank.getFluid() != null ? this.tank.getFluid().copy() : null;
        return new FluidTankInfo[] { new FluidTankInfo(fluid, this.tank.getCapacity()) };
    }
}