package net.darkhax.gyth.items;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.darkhax.gyth.Gyth;
import net.darkhax.gyth.api.GythApi;
import net.darkhax.gyth.api.TankTier;
import net.darkhax.gyth.blocks.BlockModularTank;
import net.darkhax.gyth.tileentity.TileEntityModularTank;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTankUpgrade extends Item {
    
    public ItemTankUpgrade() {
        
        this.setMaxStackSize(16);
        this.setRegistryName(new ResourceLocation("gyth", "tankUpgrade"));
        this.setCreativeTab(Gyth.tabGyth);
        this.setUnlocalizedName("modularTankUpgrade");
    }
    
    @Override
    public EnumActionResult onItemUse (ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        
        if (worldIn.getBlockState(pos).getBlock() instanceof BlockModularTank) {
            
            final TileEntityModularTank tank = (TileEntityModularTank) worldIn.getTileEntity(pos);
            final TankTier upgradeTier = GythApi.getTierFromStack(stack);
            
            if (tank != null && !tank.isInvalid() && upgradeTier != null && tank.tier != null && tank.tier.canApplyUpgrage(upgradeTier))
                tank.upgradeTank(upgradeTier);
        }
        return EnumActionResult.FAIL;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public String getUnlocalizedName (ItemStack stack) {
        
        return "item.gyth.upgrade.name";
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List<String> info, boolean advanced) {
        
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("TierID")) {
            
            final TankTier tier = GythApi.getTier(stack.getTagCompound().getString("TierID"));
            
            if (tier != null) {
                
                info.add(I18n.format("tooltip.gyth.tier") + ": " + tier.tier);
                info.add(I18n.format("tooltip.gyth.owner") + ": " + tier.identifier.getResourceDomain());
                return;
            }
        }
        
        info.add(ChatFormatting.RED + "[WARNING]" + ChatFormatting.GRAY + I18n.format("tooltip.gyth.missing"));
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems (Item item, CreativeTabs tab, List<ItemStack> itemList) {
        
        for (final TankTier tier : GythApi.REGISTRY)
            itemList.add(GythApi.createTierUpgrade(tier));
    }
}