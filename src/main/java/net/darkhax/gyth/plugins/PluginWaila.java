package net.darkhax.gyth.plugins;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.darkhax.gyth.api.GythApi;
import net.darkhax.gyth.blocks.BlockTank;
import net.darkhax.gyth.tileentity.TileEntityModularTank;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;

import javax.annotation.Nonnull;
import java.util.List;

@Optional.Interface(iface = "IWailaDataProvider", modid = "waila")
public class PluginWaila implements IWailaDataProvider {

    @Override
    @Nonnull
    public ItemStack getWailaStack (IWailaDataAccessor data, IWailaConfigHandler cfg) {

        return data.getStack();
    }

    @Override
    @Nonnull
    public List<String> getWailaHead (ItemStack stack, List<String> tip, IWailaDataAccessor data, IWailaConfigHandler cfg) {

        return tip;
    }

    @Override
    @Nonnull
    public List<String> getWailaBody (ItemStack stack, List<String> tip, IWailaDataAccessor data, IWailaConfigHandler cfg) {

        if (data.getTileEntity() instanceof TileEntityModularTank) {

            final TileEntityModularTank tank = (TileEntityModularTank) data.getTileEntity();
            GythApi.createTierTooltip(tank.getTier(), tank.tank.getFluid(), tip, false);
        }

        return tip;
    }

    @Override
    @Nonnull
    public List<String> getWailaTail (ItemStack stack, List<String> tip, IWailaDataAccessor data, IWailaConfigHandler cfg) {

        return tip;
    }

    @Override
    @Nonnull
    public NBTTagCompound getNBTData (EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos) {

        if (te != null && !te.isInvalid()) {
            te.writeToNBT(tag);
        }

        return tag;
    }

    public static void registerAddon (IWailaRegistrar register) {

        final PluginWaila dataProvider = new PluginWaila();
        register.registerBodyProvider(dataProvider, BlockTank.class);
    }
}