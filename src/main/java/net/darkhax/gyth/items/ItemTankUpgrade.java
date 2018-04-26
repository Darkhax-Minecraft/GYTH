package net.darkhax.gyth.items;

import net.darkhax.gyth.api.GythApi;
import net.darkhax.gyth.api.TankTier;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ItemTankUpgrade extends Item {

    public ItemTankUpgrade () {

        this.setMaxStackSize(16);
        this.hasSubtypes = true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag advanced) {

        TankTier tier = null;

        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("TierID")) {
            tier = GythApi.getTier(stack.getTagCompound().getString("TierID"));
        }

        GythApi.createTierTooltip(tier, null, tooltip);
    }

    @Override
    public void getSubItems (@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> items) {

        if (this.isInCreativeTab(tab)) {
            for (final TankTier tier : GythApi.REGISTRY.values()) {
                items.add(GythApi.createTierUpgrade(tier));
            }
        }
    }
}