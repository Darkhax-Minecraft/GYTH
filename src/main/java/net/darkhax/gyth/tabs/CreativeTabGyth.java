package net.darkhax.gyth.tabs;

import net.darkhax.gyth.api.GythApi;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CreativeTabGyth extends CreativeTabs {
    
    public CreativeTabGyth() {
        
        super("gyth");
    }
    
    @Override
    public Item getTabIconItem () {
        
        return Items.BUCKET;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getIconItemStack () {
        
        return GythApi.createTieredTank(GythApi.WOOD_OAK);
    }
}
