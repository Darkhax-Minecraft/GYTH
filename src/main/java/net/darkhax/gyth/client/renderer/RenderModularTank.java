package net.darkhax.gyth.client.renderer;

import net.darkhax.gyth.common.tileentity.TileEntityModularTank;
import net.darkhax.gyth.utils.Utilities;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderModularTank implements ISimpleBlockRenderingHandler {

    public static int renderID;
    public static int renderPass = 0;

    public RenderModularTank(int renderingID) {

        renderID = renderingID;
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        
        if (modelId == renderID) {

            TileEntityModularTank tank = (TileEntityModularTank) world.getTileEntity(x, y, z);

            
            if (renderPass == 0) {

                if (tank.tank.getFluid() != null) {

                    Block fluid = tank.tank.getFluid().getFluid().getBlock();
                    Utilities.renderBlockByCompleteness(fluid, world.getBlockMetadata(x, y, z), renderer, (double) tank.tank.getFluidAmount() / (double) tank.tank.getCapacity(), x, y, z);
                }
            }

            else if (renderPass == 1) {

                renderer.setRenderBounds(0, 0, 0, 1, 1, 1);
                
                if (tank.camoStack != null)
                    block = Block.getBlockFromItem(tank.camoStack.getItem());
                
                renderer.renderStandardBlock(block, x, y, z);
                
                renderer.setRenderFromInside(true);
                renderer.setRenderBounds(0, 0, 0, 1, 1, 1);
                renderer.renderStandardBlock(block, x, y, z);
                renderer.setRenderFromInside(false);
            }
        }
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {

        return true;
    }

    @Override
    public int getRenderId() {

        return renderID;
    }

}