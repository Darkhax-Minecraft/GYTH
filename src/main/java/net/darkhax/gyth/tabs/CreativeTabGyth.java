package net.darkhax.gyth.tabs;

import net.darkhax.gyth.api.GythApi;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CreativeTabGyth extends CreativeTabs {

    public CreativeTabGyth () {

        super("gyth");
        this.setBackgroundImageName("item_search.png");
    }

    @Override
    public Item getTabIconItem () {

        return Items.BUCKET;
    }

    @Override
    public ItemStack getIconItemStack () {

        return GythApi.createTieredTank(GythApi.WOOD_OAK);
    }

    @Override
    public boolean hasSearchBar () {

        return true;
    }
}
