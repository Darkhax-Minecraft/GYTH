package net.darkhax.gyth.tabs;

import net.darkhax.gyth.api.GythApi;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class CreativeTabGyth extends CreativeTabs {

    public CreativeTabGyth () {

        super("gyth");
        this.setBackgroundImageName("item_search.png");
    }

    @Override
    @Nonnull
    public ItemStack createIcon () {

        return new ItemStack(Items.BUCKET);
    }

    @Override
    @Nonnull
    public ItemStack getIcon () {

        return GythApi.createTieredTank(GythApi.WOOD_OAK);
    }

    @Override
    public boolean hasSearchBar () {

        return true;
    }
}
