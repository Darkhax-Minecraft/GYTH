package net.darkhax.gyth.common.items;

import java.util.HashMap;
import java.util.List;

import net.darkhax.gyth.common.blocks.BlockModularTank;
import net.darkhax.gyth.common.tileentity.TileEntityModularTank;
import net.darkhax.gyth.utils.TankData;
import net.darkhax.gyth.utils.TankTier;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemTankUpgrade extends Item {
    
    public static HashMap<String, IIcon> iconArray = new HashMap<String, IIcon>();
    
    public ItemTankUpgrade() {
    
        this.setMaxStackSize(16);
    }
    
    public boolean onItemUse (ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10) {
    
        if (world.getBlock(x, y, z) instanceof BlockModularTank) {
            
            TileEntityModularTank tank = (TileEntityModularTank) world.getTileEntity(x, y, z);
            
            if (tank != null && stack.hasTagCompound()) {
                
                NBTTagCompound tag = stack.getTagCompound();
                
                if (tag.hasKey("Tier") && tag.hasKey("TierName")) {
                    
                    if (tank.tier + 1 == tag.getInteger("Tier")) {
                        
                        tank.tier = tag.getInteger("Tier");
                        tank.tierName = tag.getString("TierName");
                        tank.setTankCapacity(tag.getInteger("TankCapacity") * FluidContainerRegistry.BUCKET_VOLUME);
                        world.func_147479_m(x, y, z);
                        stack.stackSize--;
                        return true;
                    }
                    
                    else if (tank.tier == tag.getInteger("Tier") && !tank.tierName.equalsIgnoreCase(tag.getString("TierName"))) {
                        
                        tank.tierName = tag.getString("TierName");
                        world.func_147479_m(x, y, z);
                        stack.stackSize--;
                        return true;
                    }
                }
            }
        }
        
        return false;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public String getUnlocalizedName (ItemStack stack) {
    
        return "item.gyth.upgrade.name";
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons (IIconRegister ir) {
    
        for (TankTier tier : TankData.tiers.values())
            iconArray.put(tier.getName(), ir.registerIcon("gyth:tank_" + tier.getName() + "_side"));
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List info, boolean advanced) {
    
        if (stack.hasTagCompound()) {
            
            NBTTagCompound tag = stack.getTagCompound();
            info.add(StatCollector.translateToLocal("tooltip.gyth.tankTier") + ": " + tag.getString("TierName") + " (" + tag.getInteger("Tier") + ")");
            info.add(tag.getInteger("TankCapacity") + " " + StatCollector.translateToLocal("tooltip.gyth.capacity"));
        }
        
        else
            info.add(EnumChatFormatting.RED + "[WARNING]" + EnumChatFormatting.WHITE + "This upgrade item is missing its data, don't trust it!");
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconIndex (ItemStack stack) {
    
        if (stack.hasTagCompound()) {
            
            IIcon icon = iconArray.get(stack.stackTagCompound.getString("TierName"));
            
            if (icon != null)
                return icon;
        }
        
        this.itemIcon = Blocks.fire.getIcon(0, 0);
        return Blocks.fire.getIcon(0, 0);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems (Item item, CreativeTabs tab, List list) {
    
        for (TankTier tier : TankData.tiers.values())
            list.add(tier.getUpgradeItemStack());
    }
}