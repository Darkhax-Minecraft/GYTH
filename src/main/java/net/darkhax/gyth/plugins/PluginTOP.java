package net.darkhax.gyth.plugins;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Function;

import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ITheOneProbe;
import mcjty.theoneprobe.api.ProbeMode;
import net.darkhax.gyth.api.GythApi;
import net.darkhax.gyth.libs.Constants;
import net.darkhax.gyth.tileentity.TileEntityModularTank;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PluginTOP implements IProbeInfoProvider {

    @Override
    public String getID () {

        return Constants.MOD_NAME + ":tank";
    }

    @Override
    public void addProbeInfo (ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {

        final TileEntity tile = world.getTileEntity(data.getPos());

        if (tile instanceof TileEntityModularTank) {

            final TileEntityModularTank tileTank = (TileEntityModularTank) tile;
            final List<String> info = new ArrayList<>();
            GythApi.createTierTooltip(tileTank.getTier(), tileTank.tank.getFluid(), info, false);

            for (final String text : info) {
                probeInfo.text(text);
            }
        }
    }

    public static class GetTheOneProbe implements Function<ITheOneProbe, Void> {

        public static ITheOneProbe theOneProbe;

        @Nullable
        @Override
        public Void apply (ITheOneProbe input) {

            theOneProbe = input;
            theOneProbe.registerProvider(new PluginTOP());
            return null;
        }
    }
}