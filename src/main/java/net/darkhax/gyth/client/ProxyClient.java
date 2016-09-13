package net.darkhax.gyth.client;

import net.darkhax.gyth.Gyth;
import net.darkhax.gyth.client.renderer.ModelTank;
import net.darkhax.gyth.client.renderer.RendererTank;
import net.darkhax.gyth.common.ProxyCommon;
import net.darkhax.gyth.tileentity.TileEntityModularTank;
import net.darkhax.gyth.utils.Constants;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.client.model.IRetexturableModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ProxyClient extends ProxyCommon {
    
    @Override
    public void registerBlockRenderers () {
        
        MinecraftForge.EVENT_BUS.register(this);
        
        ModelLoader.setCustomModelResourceLocation(Gyth.itemBlockModularTank, 0, ModelTank.MODEL);
        ModelLoader.setCustomModelResourceLocation(Gyth.itemTankUpgrade, 0, new ModelResourceLocation(new ResourceLocation("gyth", "tank_upgrade"), null));
        
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityModularTank.class, new RendererTank());
    }
    
    @SubscribeEvent
    public void onModelBake (ModelBakeEvent event) {
        
        try {
            
            final IModel currentModel = ModelLoaderRegistry.getModel(new ResourceLocation("gyth", "block/modular_tank"));
            
            if (currentModel instanceof IRetexturableModel) {
                
                final IRetexturableModel model = (IRetexturableModel) currentModel;
                final IBakedModel baseModel = event.getModelRegistry().getObject(ModelTank.MODEL);
                
                if (baseModel instanceof IPerspectiveAwareModel)
                    event.getModelRegistry().putObject(ModelTank.MODEL, new ModelTank((IPerspectiveAwareModel) baseModel, model));
            }
        }
        
        catch (final Exception exception) {
            
            Constants.LOG.warn(exception);
        }
    }
}
