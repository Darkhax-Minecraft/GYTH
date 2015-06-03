package net.darkhax.gyth.common.blocks;

import java.util.HashMap;
import java.util.Random;

import net.darkhax.gyth.Gyth;
import net.darkhax.gyth.client.renderer.RenderModularTank;
import net.darkhax.gyth.common.tileentity.TileEntityModularTank;
import net.darkhax.gyth.utils.EnumTankData;
import net.darkhax.gyth.utils.Utilities;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockModularTank extends BlockContainer {
    
    public static int renderID;
    public static HashMap<String, IIcon[]> iconArray = new HashMap<String, IIcon[]>();
    
    public BlockModularTank() {
    
        super(Material.glass);
        this.setBlockName("gyth.modularTank");
        this.setCreativeTab(Gyth.tabGyth);
        this.setHardness(0.3F);
        this.setStepSound(soundTypeGlass);
    }
    
    @Override
    public boolean hasComparatorInputOverride () {
    
        return true;
    }
    
    @Override
    public TileEntity createNewTileEntity (World world, int meta) {
    
        return new TileEntityModularTank();
    }
    
    @Override
    public boolean isOpaqueCube () {
    
        return false;
    }
    
    @Override
    public int getRenderType () {
    
        return renderID;
    }
    
    @Override
    public boolean renderAsNormalBlock () {
    
        return false;
    }
    
    @Override
    public int getRenderBlockPass () {
    
        return 1;
    }
    
    @Override
    public ItemStack getPickBlock (MovingObjectPosition target, World world, int x, int y, int z) {
    
        return Utilities.getTankStackFromTile((TileEntityModularTank) world.getTileEntity(x, y, z), true);
    }
    
    @Override
    public int quantityDropped (Random rnd) {
    
        return 0;
    }
    
    @Override
    public boolean canRenderInPass (int pass) {
    
        RenderModularTank.renderPass = pass;
        return true;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon (IBlockAccess block, int x, int y, int z, int side) {
    
        TileEntityModularTank tank = (TileEntityModularTank) block.getTileEntity(x, y, z);
        
        if (tank != null && iconArray != null) {
            
            String tier = tank.tierName;
            
            IIcon[] icons = iconArray.get(tier);
            
            if (icons == null) {
                
                icons = new IIcon[2];
                icons[0] = Blocks.glass.getIcon(0, 0);
                icons[1] = Blocks.fire.getIcon(0, 0);
            }
            
            return (side > 1) ? Utilities.getValidIIcon(icons[1]) : Utilities.getValidIIcon(icons[0]);
        }
        
        return Blocks.glass.getIcon(0, 0);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon (int side, int meta) {
    
        return Blocks.glass.getIcon(side, meta);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons (IIconRegister ir) {
    
        for (EnumTankData data : EnumTankData.values()) {
            
            IIcon[] tieredIcons = new IIcon[2];
            
            tieredIcons[0] = ir.registerIcon("gyth:tank_" + data.upgradeName + "_cap");
            tieredIcons[1] = ir.registerIcon("gyth:tank_" + data.upgradeName + "_side");
            
            iconArray.put(data.upgradeName, tieredIcons);
        }
    }
    
    @Override
    public int getComparatorInputOverride (World world, int x, int y, int z, int opSide) {
    
        return 0;
    }
    
    @Override
    public boolean onBlockActivated (World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
    
        ItemStack stack = player.inventory.getCurrentItem();
        if (stack != null) {
            
            FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(stack);
            TileEntityModularTank tank = (TileEntityModularTank) world.getTileEntity(x, y, z);
            
            if (liquid != null) {
                
                int amount = tank.fill(ForgeDirection.UNKNOWN, liquid, false);
                
                if (amount == liquid.amount) {
                    
                    tank.fill(ForgeDirection.UNKNOWN, liquid, true);
                    if (!player.capabilities.isCreativeMode)
                        player.inventory.setInventorySlotContents(player.inventory.currentItem, Utilities.useItemSafely(stack));
                    
                    return true;
                }
                
                else
                    return true;
            }
            
            else if (FluidContainerRegistry.isBucket(stack)) {
                
                FluidTankInfo[] tanks = tank.getTankInfo(ForgeDirection.UNKNOWN);
                
                if (tanks[0] != null) {
                    
                    FluidStack fillFluid = tanks[0].fluid;
                    ItemStack fillStack = FluidContainerRegistry.fillFluidContainer(fillFluid, stack);
                    
                    if (fillStack != null) {
                        
                        tank.drain(ForgeDirection.UNKNOWN, FluidContainerRegistry.getFluidForFilledItem(fillStack).amount, true);
                        
                        if (!player.capabilities.isCreativeMode) {
                            
                            if (stack.stackSize == 1)
                                player.inventory.setInventorySlotContents(player.inventory.currentItem, fillStack);
                            
                            else {
                                player.inventory.setInventorySlotContents(player.inventory.currentItem, Utilities.useItemSafely(stack));
                                
                                if (!player.inventory.addItemStackToInventory(fillStack))
                                    player.dropPlayerItemWithRandomChoice(fillStack, false);
                            }
                        }
                        return true;
                    }
                    
                    else
                        return true;
                }
            }
        }
        
        return false;
    }
    
    @Override
    public boolean removedByPlayer (World world, EntityPlayer player, int x, int y, int z, boolean willHarvest) {
    
        if (!player.capabilities.isCreativeMode) {
            
            TileEntityModularTank tank = (TileEntityModularTank) world.getTileEntity(x, y, z);
            Utilities.dropStackInWorld(world, x, y, z, Utilities.getTankStackFromTile(tank, !player.isSneaking()));
        }
        
        return world.setBlockToAir(x, y, z);
    }
    
    @Override
    public void onBlockPlacedBy (World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
    
        if (stack.hasTagCompound()) {
            
            TileEntityModularTank tank = (TileEntityModularTank) world.getTileEntity(x, y, z);
            
            if (tank != null) {
                
                NBTTagCompound tagFluid = stack.getTagCompound().getCompoundTag("Fluid");
                
                if (tagFluid != null) {
                    
                    FluidStack liquid = FluidStack.loadFluidStackFromNBT(tagFluid);
                    tank.tank.setFluid(liquid);
                }
                
                if (stack.getTagCompound().hasKey("CamoBlock"))
                    tank.camoStack = ItemStack.loadItemStackFromNBT(stack.stackTagCompound.getCompoundTag("CamoBlock"));
                
                tank.tier = stack.getTagCompound().getInteger("Tier");
                tank.tierName = stack.getTagCompound().getString("TierName");
                tank.setTankCapacity(stack.getTagCompound().getInteger("TankCapacity") * FluidContainerRegistry.BUCKET_VOLUME);
            }
        }
    }
    
    @Override
    public void onBlockExploded (World world, int x, int y, int z, Explosion explosion) {
    
        Utilities.dropStackInWorld(world, x, y, z, Utilities.getTankStackFromTile((TileEntityModularTank) world.getTileEntity(x, y, z), true));
        world.setBlockToAir(x, y, z);
        onBlockDestroyedByExplosion(world, x, y, z, explosion);
    }
}