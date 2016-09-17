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
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class UpgradeItemOverride extends ItemOverrideList {
    
    public UpgradeItemOverride() {
        
        super(ImmutableList.of());
    }
    
    @Override
    public IBakedModel handleItemState (IBakedModel originalModel, ItemStack stack, World world, EntityLivingBase entity) {
        
        TankTier tier = GythApi.getTierFromStack(stack);
        
        if (tier != null) {
            
            final IBlockState state = tier.renderState;
            
            if (state != null)
                return ((ModelRetexturable) originalModel).getRetexturedModel(RenderUtils.getSprite(state).getIconName());
        }
        
        return originalModel;
    }
}