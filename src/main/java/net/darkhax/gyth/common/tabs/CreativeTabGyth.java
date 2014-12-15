package net.darkhax.gyth.common.tabs;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
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

    @SideOnly(Side.CLIENT)
    public void displayAllReleventItems(List itemList) {

        super.displayAllReleventItems(itemList);
    }
}
