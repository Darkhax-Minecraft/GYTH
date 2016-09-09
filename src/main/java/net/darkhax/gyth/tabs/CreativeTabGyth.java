package net.darkhax.gyth.tabs;

import net.darkhax.gyth.utils.TankData;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CreativeTabGyth extends CreativeTabs {
    
    public CreativeTabGyth() {
        
        super("tabGyth");
    }
    
    @Override
    public Item getTabIconItem () {
        
        return Items.BUCKET;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getIconItemStack () {
        
        return TankData.tiers.get("spruce").getTankItemStack();
    }
}
