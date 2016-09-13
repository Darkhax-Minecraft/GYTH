package net.darkhax.gyth.tileentity;

import net.darkhax.bookshelf.tileentity.TileEntityBasic;
import net.darkhax.gyth.api.GythApi;
import net.darkhax.gyth.api.TankTier;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class TileEntityModularTank extends TileEntityBasic {
    
    public TankTier tier;
    public FluidTank tank;
    
    public TileEntityModularTank() {
        
        this.tank = new FluidTank(0);
    }
    
    public void upgradeTank (TankTier upgradeTier) {
        
        if (this.tier.canApplyUpgrage(upgradeTier)) {
            
            this.tier = upgradeTier;
            this.tank.setCapacity(upgradeTier.getCapacity());
        }
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
        
        if (dataTag.hasKey("FluidData"))
            this.tank = new FluidTank(FluidStack.loadFluidStackFromNBT(dataTag.getCompoundTag("FluidData")), this.tier.getCapacity());
    }
}