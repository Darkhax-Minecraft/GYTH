package net.darkhax.gyth.tileentity;

import net.darkhax.bookshelf.tileentity.TileEntityBasic;
import net.darkhax.gyth.api.GythApi;
import net.darkhax.gyth.api.TankTier;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class TileEntityModularTank extends TileEntityBasic {
    
    public TankTier tier;
    public FluidTank tank;
    
    public TileEntityModularTank() {
        
        this.tank = new FluidTank(0);
    }
    
    public void upgradeTank (TankTier upgradeTier, IBlockState state) {
        
        this.tier = upgradeTier;
        this.tank.setCapacity(upgradeTier.getCapacity());
        this.getWorld().notifyBlockUpdate(this.pos, state, state, 8);
        this.markDirty();
    }
    
    @Override
    public void writeNBT (NBTTagCompound dataTag) {
        
        if (this.tier != null && this.tank != null) {
            
            dataTag.setString("TierID", this.tier.identifier.toString());
            
            if (this.tank != null && this.tank.getFluid() != null) {
                
                final NBTTagCompound tankTag = new NBTTagCompound();
                this.tank.getFluid().writeToNBT(tankTag);
                dataTag.setTag("FluidData", tankTag);
            }
        }
    }
    
    @Override
    public void readNBT (NBTTagCompound dataTag) {
        
        this.tier = GythApi.getTier(dataTag.getString("TierID"));
        
        if (this.tier != null) {
            
            if (dataTag.hasKey("FluidData"))
                this.tank = new FluidTank(FluidStack.loadFluidStackFromNBT(dataTag.getCompoundTag("FluidData")), this.tier.getCapacity());
                
            else
                this.tank = new FluidTank(this.tier.getCapacity());
        }
    }
    
    @Override
    public boolean hasCapability (Capability<?> capability, EnumFacing facing) {
        
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return true;
            
        return super.hasCapability(capability, facing);
    }
    
    @Override
    public <T> T getCapability (Capability<T> capability, EnumFacing facing) {
        
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return (T) this.tank;
            
        return super.getCapability(capability, facing);
    }
}