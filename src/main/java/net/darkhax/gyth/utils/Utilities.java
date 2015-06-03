package net.darkhax.gyth.utils;

import net.darkhax.gyth.Gyth;
import net.darkhax.gyth.common.tileentity.TileEntityModularTank;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

public class Utilities {
    
    /**
     * Safely consumes an item, without losing any container data. Useful for things like
     * buckets or bottles.
     * 
     * @param stack: The ItemStack being used.
     * @return ItemStack: The returned ItemStack.
     */
    public static ItemStack useItemSafely (ItemStack stack) {
    
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
    public static void dropStackInWorld (World world, int x, int y, int z, ItemStack stack) {
    
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
    
    /**
     * Renders a standard block in the inventory. This is used to render the fluid blocks.
     * 
     * @param renderblocks: Instance of RenderBlocks.
     * @param block: The block being rendered.
     * @param meta: The meta value of the block.
     */
    public static void renderInventoryBlock (RenderBlocks renderblocks, Block block, int meta) {
    
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
    
    /**
     * Renders a standard block using an array of IIcons. Note that pos 0 is the top and bottom
     * while pos 1 represents the four sides. This is used by Gyth to render the tank in the
     * players hand, as getIcon only provides the valid texture for tanks if a TileEntity is
     * present.
     * 
     * @param renderblocks: Instance of RenderBlocks.
     * @param block: The block being rendered on.
     * @param iconArray: An array of IIcons where 0 is the top and bottom, while 1 is the
     *            sides.
     */
    public static void renderInventoryBlock (RenderBlocks renderblocks, Block block, IIcon[] iconArray) {
    
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
     * Validates that the provided IIcon is not null, if it then the glass texture will be
     * provided.
     * 
     * @param icon: The icon being provided.
     * @return IIcon: A safe IIcon.
     */
    public static IIcon getValidIIcon (IIcon icon) {
    
        if (icon != null)
            return icon;
        
        return Blocks.fire.getIcon(0, 0);
    }
    
    /**
     * Renders a block based on how complete it is. This can be used to render a block like a
     * hologram, or a fluid in a tank. Note that this block is not rendered in a 1x1x1 block
     * space, there is 0.01 space between the bounds of this render and the sides and bottom of
     * the block space it is rendered in.
     * 
     * @param block: The block that you wish to render.
     * @param meta: The metadata of the block being rendered. This is so things like colored
     *            wool can be supported.
     * @param renderer: An instance of RenderBlocks. This can be obtained from your
     *            ISimpleBlockRenderingHandler.
     * @param complete: A double which represents how complete this block is. 1 is 100% and 0
     *            is 0% while 0.50 is 50%.
     * @param x: The X position for this block to be rendered at.
     * @param y: The Y position for this block to be rendered at.
     * @param z: The Z position for this block to be rendered at.
     */
    public static void renderBlockByCompleteness (Block block, int meta, RenderBlocks renderer, double complete, int x, int y, int z) {
    
        if (block != null && renderer != null) {
            
            renderer.setRenderBounds(0.01, 0.01, 0.01, 0.99, complete * 0.99, 0.99);
            renderer.setOverrideBlockTexture(block.func_149735_b(3, meta));
            renderer.renderStandardBlock(block, x, y, z);
            renderer.clearOverrideBlockTexture();
        }
    }
    
    /**
     * Creates an ItemStack which represents an EnumTankData entry.
     * 
     * @param data: The tank data you wish to represent in item form.
     */
    public static ItemStack getTankStackFromData (EnumTankData data) {
    
        ItemStack stack = new ItemStack(Item.getItemFromBlock(Gyth.modularTank));
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("Tier", data.tier);
        tag.setString("TierName", data.upgradeName);
        tag.setInteger("TankCapacity", data.capacity);
        stack.setTagCompound(tag);
        return stack;
    }
    
    public static ItemStack getTankStackFromTile (TileEntityModularTank tank, boolean keepFluid) {
    
        ItemStack stack = new ItemStack(Gyth.modularTank);
        stack.setTagCompound(new NBTTagCompound());
        FluidStack fluid = tank.tank.getFluid();
        
        if (fluid != null && keepFluid) {
            
            NBTTagCompound tagFluid = new NBTTagCompound();
            fluid.writeToNBT(tagFluid);
            stack.getTagCompound().setTag("Fluid", tagFluid);
        }
        
        stack.getTagCompound().setInteger("Tier", tank.tier);
        stack.getTagCompound().setString("TierName", tank.tierName);
        stack.getTagCompound().setInteger("TankCapacity", tank.tank.getCapacity() / FluidContainerRegistry.BUCKET_VOLUME);
        
        return stack;
    }
}
