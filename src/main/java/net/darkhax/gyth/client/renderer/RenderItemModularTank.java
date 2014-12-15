package net.darkhax.gyth.client.renderer;

import net.darkhax.gyth.common.blocks.BlockModularTank;
import net.darkhax.gyth.utils.Utilities;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

public class RenderItemModularTank implements IItemRenderer {

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {

        return type == ItemRenderType.INVENTORY || type == ItemRenderType.ENTITY || type == ItemRenderType.EQUIPPED_FIRST_PERSON || type == ItemRenderType.EQUIPPED;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {

        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {

        RenderBlocks renderblocks = (RenderBlocks) data[0];
        Block block = Block.getBlockFromItem(item.getItem());

        if (item.hasTagCompound()) {

            NBTTagCompound tag = item.stackTagCompound;

            if (tag.hasKey("Fluid") && tag.hasKey("TankCapacity")) {

                FluidStack liquid = FluidStack.loadFluidStackFromNBT(item.getTagCompound().getCompoundTag("Fluid"));
                if (liquid != null && liquid.getFluid().getBlock() != null) {

                    GL11.glEnable(GL11.GL_BLEND);
                    float height = ((float) liquid.amount / (float) (tag.getInteger("TankCapacity") * FluidContainerRegistry.BUCKET_VOLUME) * (float) 0.99);
                    renderblocks.setRenderBounds(0.01, 0.01, 0.01, 0.99, height, 0.99);
                    Utilities.renderInventoryBlock(renderblocks, liquid.getFluid().getBlock(), 0);
                    GL11.glDisable(GL11.GL_BLEND);
                }
            }

            if (tag.hasKey("TierName")) {

                GL11.glEnable(GL11.GL_ALPHA_TEST);
                renderblocks.setRenderBounds(0, 0, 0, 1, 1, 1);
                IIcon[] iconArray = BlockModularTank.iconArray.get(tag.getString("TierName"));

                if (iconArray == null) {

                    iconArray = new IIcon[2];
                    iconArray[0] = Blocks.glass.getIcon(0, 0);
                    iconArray[1] = Blocks.fire.getIcon(0, 0);
                }

                Utilities.renderInventoryBlock(renderblocks, block, iconArray);
                GL11.glDisable(GL11.GL_ALPHA_TEST);
            }
        }
    }
}