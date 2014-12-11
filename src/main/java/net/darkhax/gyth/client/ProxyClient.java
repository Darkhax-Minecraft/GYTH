package net.darkhax.gyth.client;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.darkhax.gyth.client.renderer.RenderModularTank;
import net.darkhax.gyth.common.ProxyCommon;
import net.darkhax.gyth.common.blocks.BlockModularTank;

public class ProxyClient extends ProxyCommon {

    @Override
    public void registerBlockRenderers() {
        
        int id = RenderingRegistry.getNextAvailableRenderId();
        BlockModularTank.renderID = id;
        RenderingRegistry.registerBlockHandler(id, new RenderModularTank(id));
    }
}
