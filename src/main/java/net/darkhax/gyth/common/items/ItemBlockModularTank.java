package net.darkhax.gyth.common.items;

import java.util.List;

import net.darkhax.gyth.utils.TankData;
import net.darkhax.gyth.utils.TankTier;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBlockModularTank extends ItemBlock {
    
    public ItemBlockModularTank(Block block) {
        
        super(block);
        this.setMaxStackSize(1);
        this.setRegistryName(new ResourceLocation("gyth", "modularTank"));
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List tooltip, boolean isAdvanced) {
        
        if (stack.hasTagCompound()) {
            
            final NBTTagCompound tag = stack.getTagCompound();
            
            FluidStack fluid = null;
            
            if (tag.hasKey("Fluid")) {
                
                fluid = FluidStack.loadFluidStackFromNBT((NBTTagCompound) tag.getTag("Fluid"));
                tooltip.add(I18n.format("tooltip.gyth.fluidName") + ": " + fluid.getLocalizedName());
            }
            
            final int amount = fluid != null ? fluid.amount : 0;
            
            tooltip.add(I18n.format("tooltip.gyth.fluidAmount") + ": " + amount + "/" + tag.getInteger("TankCapacity") * FluidContainerRegistry.BUCKET_VOLUME + " mB");
            tooltip.add(I18n.format("tooltip.gyth.tankTier") + ": " + tag.getString("TierName") + " (" + tag.getInteger("Tier") + ")");
        }
        
        else
            tooltip.add(I18n.format("tooltip.gyth.itemError"));
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems (Item item, CreativeTabs tab, List itemList) {
        
        for (final TankTier tier : TankData.tiers.values())
            itemList.add(tier.getTankItemStack());
    }
}