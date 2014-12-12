package net.darkhax.gyth.client;

import net.darkhax.gyth.client.renderer.RenderModularTank;
import net.darkhax.gyth.common.ProxyCommon;
import net.darkhax.gyth.common.blocks.BlockModularTank;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ProxyClient extends ProxyCommon {

    @Override
    public void registerBlockRenderers() {

        int id = RenderingRegistry.getNextAvailableRenderId();
        BlockModularTank.renderID = id;
        RenderingRegistry.registerBlockHandler(id, new RenderModularTank(id));
    }
}
