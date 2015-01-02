package net.darkhax.gyth.common.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
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
    public ItemStack camoStack = null;

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
     * Decreases the capacity of the tank based on buckets. If the decrease amount is more than the tank
     * capacity, the capacity will be set to 0.
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
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {

        this.readFromNBT(pkt.func_148857_g());
        this.worldObj.func_147479_m(xCoord, yCoord, zCoord);
    }

    @Override
    public Packet getDescriptionPacket() {

        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbt);
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
    public void writeToNBT(NBTTagCompound nbt) {

        super.writeToNBT(nbt);

        nbt.setBoolean("hasFluid", tank.getFluid() != null);

        if (tank.getFluid() != null) {

            nbt.setString("fluidName", tank.getFluid().getFluid().getName());
            nbt.setInteger("fluidAmount", tank.getFluidAmount());
        }

        nbt.setInteger("tier", tier);
        nbt.setInteger("TankCapacity", tank.getCapacity() / FluidContainerRegistry.BUCKET_VOLUME);
        nbt.setString("tierName", tierName);
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {

        int amount = tank.fill(resource, doFill);

        if (amount > 0 && doFill)
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);

        return amount;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {

        if (tank.getFluidAmount() > 0 && tank.getFluid().getFluid() != resource.getFluid())
            return this.drain(from, resource.amount, doDrain);

        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {

        FluidStack fluidStack = tank.drain(maxDrain, doDrain);

        if (fluidStack != null && doDrain)
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);

        return fluidStack;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {

        return tank.getFluid().getFluid() == fluid && tank.getFluidAmount() < tank.getCapacity();
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {

        return tank.getFluidAmount() > 0;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {

        FluidStack fluidStack = tank.getFluid();

        if (fluidStack != null)
            return new FluidTankInfo[] { new FluidTankInfo(fluidStack.copy(), tank.getCapacity()) };

        return new FluidTankInfo[] { null };
    }
}