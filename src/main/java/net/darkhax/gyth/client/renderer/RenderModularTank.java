package net.darkhax.gyth.client.renderer;

import org.lwjgl.opengl.GL11;

import net.darkhax.gyth.common.blocks.BlockModularTank;
import net.darkhax.gyth.common.tileentity.TileEntityModularTank;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
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

            if (renderPass == 0 && tank.tank.getFluid() != null) {

                Block fluid = tank.tank.getFluid().getFluid().getBlock();
                renderFluid(fluid, world.getBlockMetadata(x, y, z), renderer, (double) tank.tank.getFluidAmount() / (double) tank.tank.getCapacity(), x, y, z);
            }

            else if (true) {

                renderer.setRenderBounds(0, 0, 0, 1, 1, 1);
                
                IIcon[] iconArray = BlockModularTank.iconArray.get(tank.tierName);
                
                if (iconArray != null)
                    renderBlock(renderer, block, x, y, z, iconArray);
                
                else
                    renderer.renderStandardBlock(Blocks.glass, x, y, z);
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
    
    private void renderBlock(RenderBlocks renderer, Block block, int x, int y, int z, IIcon[] iconArray) {
        
        GL11.glPushMatrix();
        //bottom
        renderer.renderFaceYNeg(block, x, y, z, iconArray[0]);
        
        //top
        renderer.renderFaceYPos(block, x, y, z, iconArray[1]);
        
        //north
        renderer.renderFaceZNeg(block, x, y, z, iconArray[2]);
        
        //South
        renderer.renderFaceZPos(block, x, y, z, iconArray[3]);
        
        //East
        renderer.renderFaceXPos(block, x, y, z, iconArray[4]);
        
        //west
        renderer.renderFaceXNeg(block, x, y, z, iconArray[5]);
        GL11.glPopMatrix();
    }

    private void renderFluid(Block block, int meta, RenderBlocks renderer, double filled, int x, int y, int z) {

        renderer.setRenderBounds(0.01, 0.01, 0.01, 0.99, filled * 0.99, 0.99);
        renderer.setOverrideBlockTexture(block.func_149735_b(3, meta));
        renderer.renderStandardBlock(block, x, y, z);
        renderer.clearOverrideBlockTexture();
    }
}