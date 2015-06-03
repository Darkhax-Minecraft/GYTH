package net.darkhax.gyth.client;

import net.darkhax.gyth.Gyth;
import net.darkhax.gyth.client.renderer.RenderItemModularTank;
import net.darkhax.gyth.client.renderer.RenderModularTank;
import net.darkhax.gyth.common.ProxyCommon;
import net.darkhax.gyth.common.blocks.BlockModularTank;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ProxyClient extends ProxyCommon {
    
    @Override
    public void registerBlockRenderers () {
    
        int id = RenderingRegistry.getNextAvailableRenderId();
        BlockModularTank.renderID = id;
        RenderingRegistry.registerBlockHandler(id, new RenderModularTank(id));
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Gyth.modularTank), new RenderItemModularTank());
    }
}
