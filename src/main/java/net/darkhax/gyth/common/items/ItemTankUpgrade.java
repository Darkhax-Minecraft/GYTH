package net.darkhax.gyth.common.items;

import java.util.List;

import net.darkhax.gyth.Gyth;
import net.darkhax.gyth.common.blocks.BlockModularTank;
import net.darkhax.gyth.common.tileentity.TileEntityModularTank;
import net.darkhax.gyth.utils.EnumTankData;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemTankUpgrade extends Item {

    public static IIcon[] iconArray;

    public ItemTankUpgrade() {

        this.setMaxStackSize(16);
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10) {

        if (world.getBlock(x, y, z) instanceof BlockModularTank) {

            TileEntityModularTank tank = (TileEntityModularTank) world.getTileEntity(x, y, z);

            if (tank != null && stack.hasTagCompound()) {

                NBTTagCompound tag = stack.getTagCompound();

                if (tag.hasKey("Tier") && tag.hasKey("TierName")) {

                    if (tank.tier + 1 == tag.getInteger("Tier")) {

                        tank.tier = tag.getInteger("Tier");
                        tank.tierName = tag.getString("TierName");
                        tank.setTankCapacity(tag.getInteger("TankCapacity") * FluidContainerRegistry.BUCKET_VOLUME);
                        world.func_147479_m(x, y, z);
                        stack.stackSize--;
                        return true;
                    }

                    else if (tank.tier == tag.getInteger("Tier") && !tank.tierName.equalsIgnoreCase(tag.getString("TierName"))) {

                        tank.tierName = tag.getString("TierName");
                        world.func_147479_m(x, y, z);
                        stack.stackSize--;
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getUnlocalizedName(ItemStack stack) {

        return "item.gyth.upgrade.name";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir) {

        iconArray = new IIcon[EnumTankData.values().length];

        for (int i = 0; i < iconArray.length; i++)
            iconArray[i] = ir.registerIcon("gyth:tank_" + EnumTankData.values()[i].upgradeName + "_side");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean advanced) {

        if (stack.hasTagCompound()) {

            NBTTagCompound tag = stack.getTagCompound();
            info.add(StatCollector.translateToLocal("tooltip.gyth.tankTier") + ": " + tag.getString("TierName") + " (" + tag.getInteger("Tier") + ")");
            info.add(tag.getInteger("TankCapacity") + " " + StatCollector.translateToLocal("tooltip.gyth.capacity"));
        }

        else
            info.add(EnumChatFormatting.RED + "[WARNING]" + EnumChatFormatting.WHITE + "This upgrade item is missing its data, don't trust it!");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconIndex(ItemStack stack) {

        if (stack.hasTagCompound()) {

            int pos = EnumTankData.getPosInEnum(stack.stackTagCompound.getString("TierName"));
            this.itemIcon = iconArray[pos];
            return iconArray[pos];
        }

        this.itemIcon = iconArray[0];
        return iconArray[0];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list) {

        for (EnumTankData data : EnumTankData.values())
            list.add(getUpgradeStackFromEnum(data));
    }

    public static ItemStack getUpgradeStackFromEnum(EnumTankData data) {

        ItemStack stack = new ItemStack(Gyth.tankUpgrade);
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("Tier", data.tier);
        tag.setInteger("TankCapacity", data.capacity);
        tag.setString("TierName", data.upgradeName);
        stack.setTagCompound(tag);

        return stack;
    }
}