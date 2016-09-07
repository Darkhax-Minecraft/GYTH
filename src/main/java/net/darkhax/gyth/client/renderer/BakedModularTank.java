package net.darkhax.gyth.client.renderer;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.darkhax.bookshelf.lib.BlockStates;
import net.darkhax.gyth.common.blocks.BlockModularTank;
import net.darkhax.gyth.utils.TankData;
import net.darkhax.gyth.utils.TankTier;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.client.model.IRetexturableModel;
import net.minecraftforge.client.model.SimpleModelState;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.common.property.IExtendedBlockState;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.vecmath.Matrix4f;
import java.util.List;
import java.util.Map;

public class BakedModularTank implements IPerspectiveAwareModel {

    public static final ModelResourceLocation MODEL = new ModelResourceLocation(new ResourceLocation("gyth", "modularTank"), null);
    private final IPerspectiveAwareModel standard;
    private final IRetexturableModel model;

    private final Map<String, IBakedModel> cache = Maps.newHashMap();
    private final Function<ResourceLocation, TextureAtlasSprite> textureGetter;
    private final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms;

    public BakedModularTank(IPerspectiveAwareModel standard, IRetexturableModel model) {
        this.standard = standard;
        this.model = model;

        this.textureGetter = location -> Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());
        this.transforms = getTransforms(standard);
    }

    protected IBakedModel getActualModel(String texture) {
        IBakedModel bakedModel = this;

        if (cache.containsKey(texture)) {
            bakedModel = cache.get(texture);
        } else if (model != null) {
            ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
            builder.put("case", texture);
            IModel retexturedModel = model.retexture(builder.build());
            IModelState modelState = new SimpleModelState(transforms);
            bakedModel = retexturedModel.bake(modelState, DefaultVertexFormats.BLOCK, textureGetter);
            cache.put(texture, bakedModel);
        }

        return bakedModel;
    }

    @Nonnull
    @Override
    public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
        final Minecraft mc = Minecraft.getMinecraft();

        if (!(state.getBlock() instanceof BlockModularTank))
            return mc.getBlockRendererDispatcher().getBlockModelShapes().getModelManager().getMissingModel().getQuads(state, side, rand);

        final IBlockState heldState = ((IExtendedBlockState) state).getValue(BlockStates.HELD_STATE);
        String texture = getTextureFromBlock(Blocks.LOG.getDefaultState()).getIconName();
        if (heldState != null)
            texture = getTextureFromBlock(heldState).getIconName();

        return getActualModel(texture).getQuads(state, side, rand);
    }

    @Override
    public boolean isAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean isGui3d() {
        return true;
    }

    @Override
    public boolean isBuiltInRenderer() {
        return true;
    }

    @Nonnull
    @Override
    public TextureAtlasSprite getParticleTexture() {
        return standard.getParticleTexture();
    }

    @Nonnull
    @Override
    public ItemCameraTransforms getItemCameraTransforms() {
        return ItemCameraTransforms.DEFAULT;
    }

    @Nonnull
    @Override
    public ItemOverrideList getOverrides() {
        return TableItemOverrideList.INSTANCE;
    }

    @Override
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
        return IPerspectiveAwareModel.MapWrapper.handlePerspective(this, transforms, cameraTransformType);
    }

    private static class TableItemOverrideList extends ItemOverrideList {

        static TableItemOverrideList INSTANCE = new TableItemOverrideList();

        private TableItemOverrideList() {
            super(ImmutableList.of());
        }

        @Nonnull
        @Override
        public IBakedModel handleItemState(@Nonnull IBakedModel originalModel, ItemStack stack, @Nonnull World world, @Nonnull EntityLivingBase entity) {
            if (originalModel instanceof BakedModularTank) {
                TankTier tier = TankData.tiers.get(stack.getTagCompound().getString("TierName"));
                if (tier != null) {
                    IBlockState blockStack = tier.getRenderBlock();
                    if (blockStack != null) {
                        TextureAtlasSprite t = getTextureFromBlock(blockStack);
                        String texture = t.getIconName();
                        return ((BakedModularTank) originalModel).getActualModel(texture);
                    }
                }
            }

            return originalModel;
        }
    }

    private static TextureAtlasSprite getTextureFromBlock(IBlockState blockStack) {
        return Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getTexture(blockStack);
    }

    public static ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> getTransforms(IPerspectiveAwareModel model) {
        ImmutableMap.Builder<ItemCameraTransforms.TransformType, TRSRTransformation> builder = ImmutableMap.builder();
        for (ItemCameraTransforms.TransformType type : ItemCameraTransforms.TransformType.values()) {
            TRSRTransformation transformation = new TRSRTransformation(model.handlePerspective(type).getRight());
            if (!transformation.equals(TRSRTransformation.identity())) {
                builder.put(type, TRSRTransformation.blockCenterToCorner(transformation));
            }
        }
        return builder.build();
    }
}