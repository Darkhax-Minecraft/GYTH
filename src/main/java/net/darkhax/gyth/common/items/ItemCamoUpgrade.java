package net.darkhax.gyth.common.items;

import net.darkhax.gyth.common.blocks.BlockModularTank;
import net.darkhax.gyth.common.tileentity.TileEntityModularTank;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemCamoUpgrade extends Item {

    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10) {

        if (world.getBlock(x, y, z) instanceof BlockModularTank) {

            TileEntityModularTank tank = (TileEntityModularTank) world.getTileEntity(x, y, z);

            if (tank != null && stack.hasTagCompound())
                world.func_147479_m(x, y, z);
        }

        return false;
    }
}