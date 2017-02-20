package net.darkhax.gyth.client.renderer;

import net.darkhax.bookshelf.lib.util.RenderUtils;
import net.darkhax.gyth.tileentity.TileEntityModularTank;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fluids.FluidTank;

public class RendererTank extends TileEntitySpecialRenderer<TileEntityModularTank> {

    @Override
    public void renderTileEntityAt (TileEntityModularTank te, double x, double y, double z, float partialTicks, int destroyStage) {

        if (te != null) {

            final FluidTank fluid = te.tank;

            if (fluid != null && fluid.getFluid() != null && fluid.getFluidAmount() > 0) {

                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();

                RenderUtils.translateAgainstPlayer(te.getPos(), false);
                RenderUtils.renderFluid(fluid.getFluid(), te.getPos(), 0.06d, 0.12d, 0.06d, 0.0d, 0.0d, 0.0d, 0.88d, (double) fluid.getFluidAmount() / (double) fluid.getCapacity() * 0.8d, 0.88d);

                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }
        }
    }
}
