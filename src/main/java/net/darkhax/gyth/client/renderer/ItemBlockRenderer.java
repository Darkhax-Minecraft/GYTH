package net.darkhax.gyth.client.renderer;

import net.darkhax.bookshelf.client.event.RenderItemEvent;
import net.darkhax.bookshelf.lib.util.RenderUtils;
import net.darkhax.gyth.Gyth;
import net.darkhax.gyth.api.GythApi;
import net.darkhax.gyth.api.TankTier;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemBlockRenderer {
    
    @SubscribeEvent
    public void renderItem (RenderItemEvent.Allow event) {
        
        if (event.getItemStack().getItem() == Gyth.itemBlockModularTank)
            event.setCanceled(true);
    }
    
    @SubscribeEvent
    public void renderItem (RenderItemEvent.Pre event) {
        
        GlStateManager.pushMatrix();
        TankTier tier = null;
        FluidStack fluid = null;
        
        if (event.getItemStack().hasTagCompound() && event.getItemStack().getTagCompound().hasKey("TileData")) {
            
            tier = GythApi.getTier(event.getItemStack().getTagCompound().getCompoundTag("TileData").getString("TierID"));
            fluid = FluidStack.loadFluidStackFromNBT(event.getItemStack().getTagCompound().getCompoundTag("TileData").getCompoundTag("FluidData"));
        }
        
        if (tier != null && fluid != null) {
            
            GlStateManager.enableBlend();
            RenderUtils.renderFluid(fluid, new BlockPos(0, 0, 0), 0.06d, 0.12d, 0.06d, 0.0d, 0.0d, 0.0d, 0.88d, (double) fluid.amount / (double) tier.getCapacity() * 0.8d, 0.88d);
            GlStateManager.disableBlend();
        }
        
        GlStateManager.popMatrix();
    }
}
