package net.darkhax.gyth.client;

import net.darkhax.bookshelf.client.model.ModelRetexturable;
import net.darkhax.bookshelf.lib.util.RenderUtils;
import net.darkhax.gyth.Gyth;
import net.darkhax.gyth.client.renderer.ItemBlockRenderer;
import net.darkhax.gyth.client.renderer.RendererTank;
import net.darkhax.gyth.client.renderer.TankItemOverride;
import net.darkhax.gyth.common.ProxyCommon;
import net.darkhax.gyth.tileentity.TileEntityModularTank;
import net.darkhax.gyth.utils.Constants;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
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

    public static final ModelResourceLocation MODEL = new ModelResourceLocation(new ResourceLocation("gyth", "modular_tank"), null);
    
    @Override
    public void registerBlockRenderers() {

        MinecraftForge.EVENT_BUS.register(this);

        ModelLoader.setCustomModelResourceLocation(Gyth.itemBlockModularTank, 0, MODEL);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityModularTank.class, new RendererTank());

        MinecraftForge.EVENT_BUS.register(new ItemBlockRenderer());
    }

    @SubscribeEvent
    public void onModelBake (ModelBakeEvent event) {

        try {

            final IModel currentModel = ModelLoaderRegistry.getModel(new ResourceLocation("gyth", "block/modular_tank"));

            if (currentModel instanceof IRetexturableModel) {

                final IRetexturableModel model = (IRetexturableModel) currentModel;
                final IBakedModel baseModel = event.getModelRegistry().getObject(MODEL);

                if (baseModel instanceof IPerspectiveAwareModel)
                    event.getModelRegistry().putObject(MODEL, new ModelRetexturable(model, "case", Blocks.GLASS.getDefaultState(), RenderUtils.getBasicTransforms((IPerspectiveAwareModel) baseModel), new TankItemOverride()));
            }
        }

        catch (final Exception exception) {

            Constants.LOG.warn(exception);
        }
    }
}
