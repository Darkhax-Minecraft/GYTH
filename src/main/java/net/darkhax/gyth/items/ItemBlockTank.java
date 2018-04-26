package net.darkhax.gyth.items;

import net.darkhax.gyth.api.GythApi;
import net.darkhax.gyth.api.TankTier;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ItemBlockTank extends ItemBlock {

    public ItemBlockTank (Block block) {

        super(block);
        this.setMaxStackSize(1);
        this.hasSubtypes = true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (@Nonnull ItemStack stack, @Nullable World world, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flag) {

        TankTier tier = null;
        FluidStack fluid = null;

        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("TileData")) {

            tier = GythApi.getTier(stack.getTagCompound().getCompoundTag("TileData").getString("TierID"));
            fluid = FluidStack.loadFluidStackFromNBT(stack.getTagCompound().getCompoundTag("TileData").getCompoundTag("FluidData"));
        }

        GythApi.createTierTooltip(tier, fluid, tooltip);
    }
}