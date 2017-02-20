package net.darkhax.gyth.tileentity;

import net.darkhax.bookshelf.tileentity.TileEntityBasic;
import net.darkhax.gyth.api.GythApi;
import net.darkhax.gyth.api.TankTier;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class TileEntityModularTank extends TileEntityBasic {

    /**
     * The tier of the tank. Cached here for efficiency. Use {@link #getTier()} to access this
     * field!
     */
    private TankTier tier;

    /**
     * The string based ID of the tier. This is cached to help prevent issues when a tank is
     * loaded but the tier does not yet exist.
     */
    private String tierId;

    /**
     * The fluid tank, handles all the fluid logic. This is basically the same as the default
     * forge one but allows client side packet updates for the visuals of the tank.
     */
    public FluidTankTile tank;

    /**
     * A counter used by {@link #getTier()} to represent the amount of times the tier was
     * looked up for the tank, and the tier was null. A maximum of 5 attempts will be made.
     */
    private int tierLookupAttempt = 0;

    /**
     * The initial NBT tag that the tile reads. This is cached in the event that a tile later
     * loads with a null tile tier. Used by {@link #writeNBT(NBTTagCompound)} to minimize loss
     * of data.
     */
    private NBTTagCompound tagCache;

    public TileEntityModularTank () {

        this.tank = new FluidTankTile(0);
        this.tank.setTileEntity(this);
    }

    public void upgradeTank (TankTier upgradeTier, IBlockState state) {

        this.tier = upgradeTier;
        this.tank.setCapacity(upgradeTier.getCapacity());
        this.getWorld().notifyBlockUpdate(this.pos, state, state, 8);
        this.markDirty();
    }

    public TankTier getTier () {

        if (this.tier == null && this.tierLookupAttempt < 5) {

            this.tier = GythApi.getTier(this.tierId);
            this.tierLookupAttempt++;
        }

        return this.tier;
    }

    @Override
    public void writeNBT (NBTTagCompound dataTag) {

        final TankTier tankTier = this.getTier();

        if (tankTier != null && this.tank != null) {

            dataTag.setString("TierID", tankTier.identifier.toString());

            if (this.tank != null && this.tank.getFluid() != null) {

                final NBTTagCompound tankTag = new NBTTagCompound();
                this.tank.getFluid().writeToNBT(tankTag);
                dataTag.setTag("FluidData", tankTag);
            }
        }

        else {

            this.tagCache.merge(dataTag);
        }
    }

    @Override
    public void readNBT (NBTTagCompound dataTag) {

        this.tagCache = dataTag;
        this.tierId = dataTag.getString("TierID");
        this.tier = GythApi.getTier(this.tierId);

        if (this.tier != null) {

            if (dataTag.hasKey("FluidData")) {
                this.tank = new FluidTankTile(FluidStack.loadFluidStackFromNBT(dataTag.getCompoundTag("FluidData")), this.tier.getCapacity());
            }
            else {
                this.tank = new FluidTankTile(this.tier.getCapacity());
            }

            if (this.tank != null) {
                this.tank.setTileEntity(this);
            }
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