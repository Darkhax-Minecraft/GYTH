package net.darkhax.gyth.common.items;

import java.util.List;

import net.darkhax.gyth.utils.TankData;
import net.darkhax.gyth.utils.TankTier;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBlockModularTank extends ItemBlock {
    
    public ItemBlockModularTank(Block block) {
    
        super(block);
        this.setMaxStackSize(1);
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List tooltip, boolean isAdvanced) {
    
        if (stack.hasTagCompound()) {
            
            NBTTagCompound tag = stack.stackTagCompound;
            
            FluidStack fluid = null;
            
            if (tag.hasKey("Fluid")) {
                
                fluid = FluidStack.loadFluidStackFromNBT((NBTTagCompound) tag.getTag("Fluid"));
                tooltip.add(StatCollector.translateToLocal("tooltip.gyth.fluidName") + ": " + fluid.getLocalizedName());
            }
            
            int amount = (fluid != null) ? fluid.amount : 0;
            
            tooltip.add(StatCollector.translateToLocal("tooltip.gyth.fluidAmount") + ": " + amount + "/" + (tag.getInteger("TankCapacity") * FluidContainerRegistry.BUCKET_VOLUME) + " mB");
            tooltip.add(StatCollector.translateToLocal("tooltip.gyth.tankTier") + ": " + tag.getString("TierName") + " (" + tag.getInteger("Tier") + ")");
        }
        
        else
            tooltip.add(StatCollector.translateToLocal("tooltip.gyth.itemError"));
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubItems (Item item, CreativeTabs tab, List itemList) {
    
        for (TankTier tier : TankData.tiers.values())
            itemList.add(tier.getTankItemStack());
    }
}