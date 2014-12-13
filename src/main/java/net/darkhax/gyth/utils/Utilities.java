package net.darkhax.gyth.utils;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

public class Utilities {

    /**
     * Safely consumes an item, without losing any container data. Useful for things like buckets or
     * bottles.
     * 
     * @param stack: The ItemStack being used.
     * @return ItemStack: The returned ItemStack.
     */
    public static ItemStack useItemSafely(ItemStack stack) {

        if (stack.stackSize == 1) {

            if (stack.getItem().hasContainerItem(stack))
                return stack.getItem().getContainerItem(stack);
            else
                return null;
        }

        else {

            stack.splitStack(1);
            return stack;
        }
    }

    /**
     * Safely drops an ItemStack into the world as an EntityItem.
     * 
     * @param world: Instance of the world.
     * @param x: The x position for the item to spawn.
     * @param y: The y position for the item to spawn.
     * @param z: The z position for the item to spawn.
     * 
     * @param stack: The ItemStack being dropped into the world.
     */
    public static void dropStackInWorld(World world, int x, int y, int z, ItemStack stack) {

        if (!world.isRemote && world.getGameRules().getGameRuleBooleanValue("doTileDrops")) {

            float f = 0.7F;
            double d0 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
            double d1 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
            double d2 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
            EntityItem entityitem = new EntityItem(world, (double) x + d0, (double) y + d1, (double) z + d2, stack);
            entityitem.delayBeforeCanPickup = 10;
            world.spawnEntityInWorld(entityitem);
        }
    }

    public static void renderStandardInvBlock(RenderBlocks renderblocks, Block block, int meta) {

        Tessellator tessellator = Tessellator.instance;
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1F, 0.0F);
        renderblocks.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, getValidIIcon(block.getIcon(0, meta)));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        renderblocks.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, getValidIIcon(block.getIcon(1, meta)));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1F);
        renderblocks.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, getValidIIcon(block.getIcon(2, meta)));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderblocks.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, getValidIIcon(block.getIcon(3, meta)));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1F, 0.0F, 0.0F);
        renderblocks.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, getValidIIcon(block.getIcon(4, meta)));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderblocks.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, getValidIIcon(block.getIcon(5, meta)));
        tessellator.draw();
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
    }

    public static void renderStandardInvBlock(RenderBlocks renderblocks, Block block, IIcon[] iconArray) {

        Tessellator tessellator = Tessellator.instance;

        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1F, 0.0F);
        renderblocks.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, getValidIIcon(iconArray[0]));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        renderblocks.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, getValidIIcon(iconArray[0]));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1F);
        renderblocks.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, getValidIIcon(iconArray[1]));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderblocks.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, getValidIIcon(iconArray[1]));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1F, 0.0F, 0.0F);
        renderblocks.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, getValidIIcon(iconArray[1]));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderblocks.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, getValidIIcon(iconArray[1]));
        tessellator.draw();
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
    }

    /**
     * Validates that the provided IIcon is not null, if it then the glass texture will be provided.
     * 
     * @param icon: The icon being provided.
     * @return IIcon: A safe IIcon.
     */
    public static IIcon getValidIIcon(IIcon icon) {

        if (icon != null)
            return icon;

        return Blocks.fire.getIcon(0, 0);
    }

    public static void renderFluid(Block block, int meta, RenderBlocks renderer, double filled, int x, int y, int z) {

        renderer.setRenderBounds(0.01, 0.01, 0.01, 0.99, filled * 0.99, 0.99);
        renderer.setOverrideBlockTexture(block.func_149735_b(3, meta));
        renderer.renderStandardBlock(block, x, y, z);
        renderer.clearOverrideBlockTexture();
    }
}
