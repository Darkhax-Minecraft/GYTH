package net.darkhax.gyth.tileentity;

import net.darkhax.gyth.libs.ConfigurationHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
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
    public boolean canFillFluidType(FluidStack fluid) {
        
        if (ConfigurationHandler.handleTemperature && fluid != null && this.tile instanceof TileEntityModularTank && ((TileEntityModularTank) this.tile).tier.isFlammable(this.tile.getWorld(), this.tile.getPos(), EnumFacing.UP) && fluid.getFluid().getTemperature() > ConfigurationHandler.maxFluidHeat)
            return false;
        
        return super.canFillFluidType(fluid);
    }
    
    @Override
    public void onContentsChanged () {
        
        if (this.tile != null) {
            
            final IBlockState state = this.tile.getWorld().getBlockState(this.tile.getPos());
            this.tile.getWorld().notifyBlockUpdate(this.tile.getPos(), state, state, 8);
            this.tile.markDirty();
        }
    }
}
