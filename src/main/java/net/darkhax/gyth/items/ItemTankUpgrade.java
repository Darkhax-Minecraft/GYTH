package net.darkhax.gyth.items;

import java.util.List;

import net.darkhax.gyth.Gyth;
import net.darkhax.gyth.api.GythApi;
import net.darkhax.gyth.api.TankTier;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTankUpgrade extends Item {
    
    public ItemTankUpgrade() {
        
        this.setMaxStackSize(16);
        this.setRegistryName(new ResourceLocation("gyth", "tank_upgrade"));
        this.setCreativeTab(Gyth.tabGyth);
        this.setUnlocalizedName("gyth.upgrade");
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List<String> info, boolean advanced) {
        
        TankTier tier = null;
        
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("TierID"))
            tier = GythApi.getTier(stack.getTagCompound().getString("TierID"));
            
        GythApi.createTierTooltip(tier, null, info);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems (Item item, CreativeTabs tab, List<ItemStack> itemList) {
        
        for (final TankTier tier : GythApi.REGISTRY)
            itemList.add(GythApi.createTierUpgrade(tier));
    }
}