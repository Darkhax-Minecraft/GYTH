package net.darkhax.gyth.client.renderer;

import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.vecmath.Matrix4f;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import net.darkhax.bookshelf.lib.BlockStates;
import net.darkhax.bookshelf.lib.util.RenderUtils;
import net.darkhax.gyth.api.GythApi;
import net.darkhax.gyth.api.TankTier;
import net.darkhax.gyth.blocks.BlockTank;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.client.model.IRetexturableModel;
import net.minecraftforge.client.model.SimpleModelState;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.common.property.IExtendedBlockState;

public class ModelTank implements IPerspectiveAwareModel {
    
    public static final ModelResourceLocation MODEL = new ModelResourceLocation(new ResourceLocation("gyth", "modular_tank"), null);
    private final IRetexturableModel model;
    
    private final Map<String, IBakedModel> cache = Maps.newHashMap();
    private final Function<ResourceLocation, TextureAtlasSprite> textureGetter;
    private final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms;
    
    public ModelTank(IPerspectiveAwareModel standard, IRetexturableModel model) {
        
        this.model = model;
        this.textureGetter = location -> Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());
        this.transforms = getTransforms(standard);
    }
    
    protected IBakedModel getActualModel (String texture) {
        
        IBakedModel bakedModel = this;
        
        if (this.cache.containsKey(texture))
            bakedModel = this.cache.get(texture);
            
        else if (this.model != null) {
            
            final ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
            builder.put("case", texture);
            final IModel retexturedModel = this.model.retexture(builder.build());
            final IModelState modelState = new SimpleModelState(this.transforms);
            bakedModel = retexturedModel.bake(modelState, DefaultVertexFormats.BLOCK, this.textureGetter);
            this.cache.put(texture, bakedModel);
        }
        
        return bakedModel;
    }
    
    @Override
    public List<BakedQuad> getQuads (IBlockState state, EnumFacing side, long rand) {
        
        final Minecraft mc = Minecraft.getMinecraft();
        
        if (!(state.getBlock() instanceof BlockTank))
            return mc.getBlockRendererDispatcher().getBlockModelShapes().getModelManager().getMissingModel().getQuads(state, side, rand);
            
        final IBlockState heldState = ((IExtendedBlockState) state).getValue(BlockStates.HELD_STATE);
        String texture = getTextureFromBlock(Blocks.LOG.getDefaultState()).getIconName();
        
        if (heldState != null)
            texture = getTextureFromBlock(heldState).getIconName();
            
        return this.getActualModel(texture).getQuads(state, side, rand);
    }
    
    @Override
    public boolean isAmbientOcclusion () {
        
        return true;
    }
    
    @Override
    public boolean isGui3d () {
        
        return true;
    }
    
    @Override
    public boolean isBuiltInRenderer () {
        
        return true;
    }
    
    @Override
    public TextureAtlasSprite getParticleTexture () {
        
        return RenderUtils.getSprite(new ItemStack(Blocks.GLASS));
    }
    
    @Override
    public ItemCameraTransforms getItemCameraTransforms () {
        
        return ItemCameraTransforms.DEFAULT;
    }
    
    @Override
    public ItemOverrideList getOverrides () {
        
        return TableItemOverrideList.INSTANCE;
    }
    
    @Override
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective (ItemCameraTransforms.TransformType cameraTransformType) {
        
        return IPerspectiveAwareModel.MapWrapper.handlePerspective(this, this.transforms, cameraTransformType);
    }
    
    private static class TableItemOverrideList extends ItemOverrideList {
        
        static TableItemOverrideList INSTANCE = new TableItemOverrideList();
        
        private TableItemOverrideList() {
            
            super(ImmutableList.of());
        }
        
        @Override
        public IBakedModel handleItemState (@Nonnull IBakedModel originalModel, ItemStack stack, @Nonnull World world, @Nonnull EntityLivingBase entity) {
            
            if (stack != null && stack.hasTagCompound() && stack.getTagCompound().hasKey("TileData")) {
                
                final TankTier tier = GythApi.getTier(stack.getTagCompound().getCompoundTag("TileData").getString("TierID"));
                
                if (tier != null) {
                    
                    final IBlockState blockStack = tier.renderState;
                    
                    if (blockStack != null)
                        return ((ModelTank) originalModel).getActualModel(getTextureFromBlock(blockStack).getIconName());
                }
            }
            
            return originalModel;
        }
    }
    
    private static TextureAtlasSprite getTextureFromBlock (IBlockState blockStack) {
        
        return Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getTexture(blockStack);
    }
    
    public static ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> getTransforms (IPerspectiveAwareModel model) {
        
        final ImmutableMap.Builder<ItemCameraTransforms.TransformType, TRSRTransformation> builder = ImmutableMap.builder();
        for (final ItemCameraTransforms.TransformType type : ItemCameraTransforms.TransformType.values()) {
            final TRSRTransformation transformation = new TRSRTransformation(model.handlePerspective(type).getRight());
            if (!transformation.equals(TRSRTransformation.identity()))
                builder.put(type, TRSRTransformation.blockCenterToCorner(transformation));
        }
        return builder.build();
    }
}