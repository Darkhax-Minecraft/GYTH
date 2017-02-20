package net.darkhax.gyth.client.renderer;

import com.google.common.collect.ImmutableList;

import net.darkhax.bookshelf.client.model.ModelRetexturable;
import net.darkhax.bookshelf.lib.util.RenderUtils;
import net.darkhax.gyth.api.GythApi;
import net.darkhax.gyth.api.TankTier;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class TankItemOverride extends ItemOverrideList {

    public TankItemOverride () {

        super(ImmutableList.of());
    }

    @Override
    public IBakedModel handleItemState (IBakedModel originalModel, ItemStack stack, World world, EntityLivingBase entity) {

        if (stack != null && stack.hasTagCompound() && stack.getTagCompound().hasKey("TileData")) {

            final TankTier tier = GythApi.getTier(stack.getTagCompound().getCompoundTag("TileData").getString("TierID"));

            if (tier != null) {

                final IBlockState state = tier.renderState;

                if (state != null)
                    return ((ModelRetexturable) originalModel).getRetexturedModel(RenderUtils.getSprite(state).getIconName());
            }
        }

        else
            return ((ModelRetexturable) originalModel).getRetexturedModel(RenderUtils.getSprite(Blocks.FIRE.getDefaultState()).getIconName());

        return originalModel;
    }
}