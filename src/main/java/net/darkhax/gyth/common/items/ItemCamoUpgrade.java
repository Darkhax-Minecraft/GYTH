package net.darkhax.gyth.common.items;

import java.util.List;

import net.darkhax.gyth.Gyth;
import net.darkhax.gyth.common.blocks.BlockModularTank;
import net.darkhax.gyth.common.tileentity.TileEntityModularTank;
import net.darkhax.gyth.utils.EnumTankData;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCamoUpgrade extends Item {

    public static IIcon[] iconArray;

    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10) {

        if (world.getBlock(x, y, z) instanceof BlockModularTank) {

            TileEntityModularTank tank = (TileEntityModularTank) world.getTileEntity(x, y, z);

            tank.camoStack = new ItemStack(Blocks.bookshelf);
            world.func_147479_m(x, y, z);
            if (tank != null && stack.hasTagCompound()) {

                NBTTagCompound tag = stack.getTagCompound();

                if (tag.hasKey("CamoBlock")) {
                    
                    tank.camoStack = new ItemStack(Blocks.bookshelf);
                }
            }
        }

        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean advanced) {

        if (stack.hasTagCompound()) {

        }

        else {

            info.add("This upgrade item is missing its data!");
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconIndex(ItemStack stack) {

        if (stack.hasTagCompound() && stack.stackTagCompound.hasKey("CamoBlock"))
            return ItemStack.loadItemStackFromNBT(stack.stackTagCompound.getCompoundTag("CamoBlock")).getItem().getIcon(stack, 0);
        
        else 
            return Blocks.gold_block.getIcon(0, 0);
    }
}