package net.darkhax.gyth.client.renderer;

import com.google.common.collect.ImmutableMap;
import net.darkhax.bookshelf.client.model.block.CachedDynamicBakedModel;
import net.darkhax.bookshelf.data.Blockstates;
import net.darkhax.bookshelf.util.RenderUtils;
import net.darkhax.gyth.api.GythApi;
import net.darkhax.gyth.api.TankTier;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.SimpleModelState;
import net.minecraftforge.common.model.IModelPart;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.function.Function;

@SideOnly(Side.CLIENT)
public class ModelTank extends CachedDynamicBakedModel {

    private final Function<ResourceLocation, TextureAtlasSprite> textureGetter = location -> {

        assert location != null;
        return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());
    };

    private final ImmutableMap<? extends IModelPart, TRSRTransformation> transforms;

    public ModelTank(IBakedModel baked, IModel raw) {

        super(baked, raw);
        this.transforms = RenderUtils.copyTransforms(baked);
    }

    @Override
    public String getCacheKey(IBlockState state, EnumFacing side) {

    	final IBlockState heldState = ((IExtendedBlockState) state).getValue(Blockstates.HELD_STATE);
    	
        ItemStack stack = heldState != null ? new ItemStack(heldState.getBlock()) : new ItemStack(Blocks.FIRE);

        if (stack.isEmpty()) {

            stack = new ItemStack(Blocks.LOG);
        }

        return RenderUtils.getParticleTexture(stack).getIconName();
    }

    @Override
    public String getCacheKey(ItemStack stack, World world, EntityLivingBase entity) {

        final TankTier tierUpgrade = GythApi.getTierFromStack(stack);

        //Tank
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("TileData")) {

            final TankTier tier = GythApi.getTier(stack.getTagCompound().getCompoundTag("TileData").getString("TierID"));

            if (tier != null) {

                final IBlockState renderState = tier.renderState;

                if (renderState != null)
                    return RenderUtils.getSprite(renderState).getIconName();
            }
        }

        //Tank Upgrade
        else if (tierUpgrade != null) {

            final IBlockState state = tierUpgrade.renderState;

            if (state != null)
                return RenderUtils.getSprite(state).getIconName();
        }

        else
            return RenderUtils.getSprite(Blocks.FIRE.getDefaultState()).getIconName();

        return RenderUtils.getParticleTexture(stack).getIconName();
    }

    @Override
    public IBakedModel generateBlockModel(String key) {

        final ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
        final String texture = key == null ? "minecraft:blocks/stone" : key;
        builder.put("case", texture);
        builder.put("particle", texture);
        return this.getRaw().retexture(builder.build()).bake(new SimpleModelState(this.transforms), DefaultVertexFormats.BLOCK, this.textureGetter);
    }
}