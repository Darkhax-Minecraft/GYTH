package net.darkhax.gyth.common.tabs;

import java.util.List;

import net.darkhax.gyth.utils.EnumTankData;
import net.darkhax.gyth.utils.Utilities;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CreativeTabGyth extends CreativeTabs {

    public CreativeTabGyth() {

        super("tabGyth");
    }

    @Override
    public Item getTabIconItem() {

        return Items.bucket;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void displayAllReleventItems(List itemList) {

        super.displayAllReleventItems(itemList);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getIconItemStack() {

        return Utilities.getTankStackFromData(EnumTankData.SPRUCE);
    }
}
