package net.darkhax.gyth.common.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.*;

import javax.annotation.Nullable;

public class TileEntityModularTank extends TileEntity implements IFluidHandler {

    public FluidTank tank;
    public int tier = 1;
    public String tierName = "oak";

    public TileEntityModularTank() {

        tank = new FluidTank(FluidContainerRegistry.BUCKET_VOLUME * 2);
    }

    public void setTankTier(int tankTier) {

        this.tier = tankTier;
    }

    public int getTankTier() {

        return this.tier;
    }

    public void upgradeTank(ItemStack upgradeItem) {

        if (upgradeItem.getItem() != null && upgradeItem.hasTagCompound() && getTankTier() + 1 == upgradeItem.getTagCompound().getInteger("TankTier")) {

            setTankTier(upgradeItem.getTagCompound().getInteger("TankTier"));
            setTankCapacity(getTankTier() * 16);
        }
    }

    /**
     * Sets the capacity of the tank based on buckets.
     *
     * @param tankCapacityModifier: The amount of buckets the tank can hold.
     */
    public void setTankCapacity(int tankCapacityModifier) {

        tank.setCapacity(tankCapacityModifier);
    }

    /**
     * Decreases the capacity of the tank based on buckets. If the decrease amount is more than
     * the tank capacity, the capacity will be set to 0.
     *
     * @param tankCapacityModifier: The amount of buckets to decrease the tanks capacity by.
     */
    public void decreaseTankCapacity(int tankCapacityModifier) {

        if (tank.getCapacity() > FluidContainerRegistry.BUCKET_VOLUME * tankCapacityModifier)
            tank.setCapacity(tank.getCapacity() - (FluidContainerRegistry.BUCKET_VOLUME * tankCapacityModifier));

        else
            tank.setCapacity(0);
    }

    /**
     * Increases the capacity of the tank based on buckets.
     *
     * @param tankCapacityModifier: The amount of additional buckets this tank can hold.
     */
    public void increaseTankCapacity(int tankCapacityModifier) {

        tank.setCapacity(tank.getCapacity() + (FluidContainerRegistry.BUCKET_VOLUME * tankCapacityModifier));
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.getNbtCompound());
//        this.worldObj.func_147479_m(xCoord, yCoord, zCoord);
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        return new SPacketUpdateTileEntity(pos, getBlockMetadata(), nbt);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {

        super.readFromNBT(nbt);

        tier = nbt.getInteger("tier");
        tierName = nbt.getString("tierName");
        tank.setCapacity(nbt.getInteger("TankCapacity") * FluidContainerRegistry.BUCKET_VOLUME);
        if (nbt.getBoolean("hasFluid"))
            tank.setFluid(FluidRegistry.getFluidStack(nbt.getString("fluidName"), nbt.getInteger("fluidAmount")));
        else
            tank.setFluid(null);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {

        super.writeToNBT(nbt);

        nbt.setBoolean("hasFluid", tank.getFluid() != null);

        if (tank.getFluid() != null) {

            nbt.setString("fluidName", tank.getFluid().getFluid().getName());
            nbt.setInteger("fluidAmount", tank.getFluidAmount());
        }

        nbt.setInteger("tier", tier);
        nbt.setInteger("TankCapacity", tank.getCapacity() / FluidContainerRegistry.BUCKET_VOLUME);
        nbt.setString("tierName", tierName);
        return nbt;
    }


    @Override
    public int fill(EnumFacing from, FluidStack resource, boolean doFill) {
        int fluid = tank.fill(resource, doFill);

        if (fluid > 0 && doFill) {

//            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
//            worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, this.getBlockType());
        }

        return fluid;
    }

    @Override
    public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain) {

        if (tank.getFluidAmount() > 0 && tank.getFluid().getFluid() == resource.getFluid())
            return this.drain(from, resource.amount, doDrain);

        return null;
    }

    @Override
    public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain) {
        FluidStack fluidStack = tank.drain(maxDrain, doDrain);

        if (fluidStack != null && doDrain) {

//            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
//            worldObj.notifyBlockOfNeighborChange(xCoord, yCoord, zCoord, this.getBlockType());
        }

        return fluidStack;
    }

    @Override
    public boolean canFill(EnumFacing from, Fluid fluid) {
        return tank.getFluidAmount() == 0 || (tank.getFluid().getFluid() == fluid && tank.getFluidAmount() < tank.getCapacity());
    }

    @Override
    public boolean canDrain(EnumFacing from, Fluid fluid) {
        return tank.getFluidAmount() > 0;
    }

    @Override
    public FluidTankInfo[] getTankInfo(EnumFacing from) {
        FluidStack fluid = (tank.getFluid() != null) ? tank.getFluid().copy() : null;
        return new FluidTankInfo[]{new FluidTankInfo(fluid, tank.getCapacity())};
    }
}