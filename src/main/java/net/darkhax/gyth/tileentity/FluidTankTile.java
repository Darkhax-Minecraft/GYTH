package net.darkhax.gyth.tileentity;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class FluidTankTile extends FluidTank {
    
    public FluidTankTile(int capacity) {
        
        super(capacity);
    }
    
    public FluidTankTile(FluidStack fluidStack, int capacity) {
        
        super(fluidStack, capacity);
    }
    
    public FluidTankTile(Fluid fluid, int amount, int capacity) {
        
        super(fluid, amount, capacity);
    }
    
    @Override
    public void onContentsChanged () {
        
        if (this.tile != null) {
            
            this.tile.getWorld().notifyNeighborsOfStateChange(this.tile.getPos(), this.tile.getBlockType());
            this.tile.markDirty();
        }
    }
}
