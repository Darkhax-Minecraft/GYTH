package net.darkhax.gyth.client;

import net.darkhax.bookshelf.client.event.RenderItemEvent;
import net.darkhax.bookshelf.client.model.ModelRetexturable;
import net.darkhax.bookshelf.lib.util.RenderUtils;
import net.darkhax.gyth.Gyth;
import net.darkhax.gyth.api.GythApi;
import net.darkhax.gyth.api.TankTier;
import net.darkhax.gyth.client.renderer.RendererTank;
import net.darkhax.gyth.client.renderer.TankItemOverride;
import net.darkhax.gyth.client.renderer.UpgradeItemOverride;
import net.darkhax.gyth.common.ProxyCommon;
import net.darkhax.gyth.libs.Constants;
import net.darkhax.gyth.tileentity.TileEntityModularTank;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.client.model.IRetexturableModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ProxyClient extends ProxyCommon {
    
    public static final ModelResourceLocation MODEL = new ModelResourceLocation(new ResourceLocation("gyth", "modular_tank"), null);
    public static final ModelResourceLocation MODEL_UPGRADE = new ModelResourceLocation(new ResourceLocation("gyth", "tank_upgrade"), null);
    
    @Override
    public void registerBlockRenderers () {
        
        MinecraftForge.EVENT_BUS.register(this);
        
        ModelLoader.setCustomModelResourceLocation(Gyth.itemBlockModularTank, 0, MODEL);
        ModelLoader.setCustomModelResourceLocation(Gyth.itemTankUpgrade, 0, MODEL_UPGRADE);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityModularTank.class, new RendererTank());
    }
    
    @SubscribeEvent
    public void onModelBake (ModelBakeEvent event) {
        
        try {
            
            IModel currentModel = ModelLoaderRegistry.getModel(new ResourceLocation("gyth", "block/modular_tank"));
            
            if (currentModel instanceof IRetexturableModel) {
                
                final IRetexturableModel model = (IRetexturableModel) currentModel;
                final IBakedModel baseModel = event.getModelRegistry().getObject(MODEL);
                
                if (baseModel instanceof IPerspectiveAwareModel)
                    event.getModelRegistry().putObject(MODEL, new ModelRetexturable(model, "case", Blocks.GLASS.getDefaultState(), RenderUtils.getBasicTransforms((IPerspectiveAwareModel) baseModel), new TankItemOverride(), null));
            }
            
            currentModel = ModelLoaderRegistry.getModel(new ResourceLocation("gyth", "item/tank_upgrade"));
            
            if (currentModel instanceof IRetexturableModel) {
                
                final IRetexturableModel model = (IRetexturableModel) currentModel;
                final IBakedModel baseModel = event.getModelRegistry().getObject(MODEL_UPGRADE);
                
                if (baseModel instanceof IPerspectiveAwareModel)
                    event.getModelRegistry().putObject(MODEL_UPGRADE, new ModelRetexturable(model, "case", Blocks.GLASS.getDefaultState(), RenderUtils.getBasicTransforms((IPerspectiveAwareModel) baseModel), new UpgradeItemOverride(), null));
            }
        }
        
        catch (final Exception exception) {
            
            Constants.LOG.warn(exception);
            exception.printStackTrace();
        }
    }
    
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
