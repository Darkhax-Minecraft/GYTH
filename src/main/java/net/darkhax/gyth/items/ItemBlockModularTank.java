package net.darkhax.gyth.items;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.darkhax.gyth.api.GythApi;
import net.darkhax.gyth.api.TankTier;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
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
    public void addInformation (ItemStack stack, EntityPlayer player, List<String> info, boolean advanced) {
        
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("TileData")) {
            
            final NBTTagCompound tileData = stack.getTagCompound().getCompoundTag("TileData");
            final TankTier tier = GythApi.getTier(tileData.getString("TierID"));
            
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
            itemList.add(GythApi.createTieredTank(tier));
    }
}