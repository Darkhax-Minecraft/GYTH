package net.darkhax.gyth.items;

import java.util.List;

import net.darkhax.gyth.api.GythApi;
import net.darkhax.gyth.api.TankTier;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBlockTank extends ItemBlock {

    public ItemBlockTank (Block block) {

        super(block);
        this.setMaxStackSize(1);
        this.setRegistryName(new ResourceLocation("gyth", "modular_tank"));
        this.hasSubtypes = true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List<String> info, boolean advanced) {

        TankTier tier = null;
        FluidStack fluid = null;

        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("TileData")) {

            tier = GythApi.getTier(stack.getTagCompound().getCompoundTag("TileData").getString("TierID"));
            fluid = FluidStack.loadFluidStackFromNBT(stack.getTagCompound().getCompoundTag("TileData").getCompoundTag("FluidData"));
        }

        GythApi.createTierTooltip(tier, fluid, info);
    }
}