package net.darkhax.gyth.items;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.darkhax.gyth.Gyth;
import net.darkhax.gyth.blocks.BlockModularTank;
import net.darkhax.gyth.tileentity.TileEntityModularTank;
import net.darkhax.gyth.utils.TankData;
import net.darkhax.gyth.utils.TankTier;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
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
            
            if (tank != null && stack.hasTagCompound()) {
                
                final NBTTagCompound tag = stack.getTagCompound();
                
                if (tag.hasKey("Tier") && tag.hasKey("TierName"))
                    if (tank.tier + 1 == tag.getInteger("Tier") || tank.tier == tag.getInteger("Tier") && !tank.tierName.equalsIgnoreCase(tag.getString("TierName"))) {
                        
                        tank.tier = tag.getInteger("Tier");
                        tank.tierName = tag.getString("TierName");
                        tank.setTankCapacity(tag.getInteger("TankCapacity") * FluidContainerRegistry.BUCKET_VOLUME);
                        // worldIn.func_147479_m(x, y, z);
                        stack.stackSize--;
                        return EnumActionResult.SUCCESS;
                    }
            }
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
    public void addInformation (ItemStack stack, EntityPlayer player, List info, boolean advanced) {
        
        if (stack.hasTagCompound()) {
            
            final NBTTagCompound tag = stack.getTagCompound();
            info.add(I18n.format("tooltip.gyth.tankTier") + ": " + tag.getString("TierName") + " (" + tag.getInteger("Tier") + ")");
            info.add(tag.getInteger("TankCapacity") + " " + I18n.format("tooltip.gyth.capacity"));
        }
        else
            info.add(ChatFormatting.RED + "[WARNING]" + ChatFormatting.WHITE + "This upgrade item is missing its data, don't trust it!");
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems (Item item, CreativeTabs tab, List list) {
        
        for (final TankTier tier : TankData.tiers.values())
            list.add(tier.getUpgradeItemStack());
    }
}