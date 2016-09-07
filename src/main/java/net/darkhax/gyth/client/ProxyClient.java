package net.darkhax.gyth.client;

import net.darkhax.gyth.Gyth;
import net.darkhax.gyth.client.renderer.BakedModularTank;
import net.darkhax.gyth.client.renderer.RendererModularTank;
import net.darkhax.gyth.common.ProxyCommon;
import net.darkhax.gyth.common.tileentity.TileEntityModularTank;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ProxyClient extends ProxyCommon {

    @Override
    public void registerBlockRenderers() {
        MinecraftForge.EVENT_BUS.register(this);

//        ModelLoader.setCustomModelResourceLocation(Gyth.itemTankUpgrade, 0, BakedModularTank.MODEL);
        ModelLoader.setCustomModelResourceLocation(Gyth.itemBlockModularTank, 0, BakedModularTank.MODEL);

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityModularTank.class, new RendererModularTank());
        ForgeHooksClient.registerTESRItemStack(Gyth.itemBlockModularTank, 0, TileEntityModularTank.class);
        ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(Gyth.blockModularTanks), 0, TileEntityModularTank.class);
    }

    @SubscribeEvent
    public void onModelBake(ModelBakeEvent event) {
        try {
            IModel m = ModelLoaderRegistry.getModel(new ResourceLocation("gyth", "block/modularTank"));

            if (m instanceof IRetexturableModel) {
                IRetexturableModel model = (IRetexturableModel) m;
                IBakedModel standard = event.getModelRegistry().getObject(BakedModularTank.MODEL);
                if (standard instanceof IPerspectiveAwareModel) {
                    event.getModelRegistry().putObject(BakedModularTank.MODEL, new BakedModularTank((IPerspectiveAwareModel) standard, model));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
