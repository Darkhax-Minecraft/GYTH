package net.darkhax.gyth.blocks;

import net.darkhax.bookshelf.data.Blockstates;
import net.darkhax.bookshelf.util.StackUtils;
import net.darkhax.gyth.api.GythApi;
import net.darkhax.gyth.api.TankTier;
import net.darkhax.gyth.items.ItemTankUpgrade;
import net.darkhax.gyth.libs.ConfigurationHandler;
import net.darkhax.gyth.tileentity.TileEntityModularTank;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nonnull;
import java.util.Random;

public class BlockTank extends BlockContainer {

    public BlockTank () {

        super(Material.GLASS);
        this.setHardness(0.3F);
        this.setSoundType(SoundType.GLASS);
        this.setDefaultState(((IExtendedBlockState) this.blockState.getBaseState()).withProperty(Blockstates.HELD_STATE, null).withProperty(Blockstates.BLOCK_ACCESS, null).withProperty(Blockstates.BLOCKPOS, null));
    }

    @Override
    public boolean onBlockActivated (World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack heldItem = playerIn.getHeldItem(hand);
        final TileEntityModularTank tank = (TileEntityModularTank) worldIn.getTileEntity(pos);
        final FluidStack fluid = FluidUtil.getFluidContained(heldItem);

        // Handle bad tank
        if (tank == null || tank.getTier() == null || ConfigurationHandler.handleTemperature && tank.getTier().isFlammable(worldIn, pos, facing) && fluid != null && fluid.getFluid().getTemperature(fluid) > ConfigurationHandler.maxFluidHeat || !tank.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing))
            return !(heldItem.getItem() instanceof ItemBlock);

        // Handle upgrade
        if (tank != null && heldItem.getItem() instanceof ItemTankUpgrade) {

            final TankTier upgradeTier = GythApi.getTierFromStack(heldItem);

            if (tank != null && !tank.isInvalid() && upgradeTier != null && tank.getTier() != null && tank.getTier().canApplyUpgrage(upgradeTier)) {

                tank.upgradeTank(upgradeTier, state);
                heldItem.shrink(1);
                return true;
            }
        }

        // Handle input
        final IFluidHandler fluidHandler = tank.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing);
        FluidUtil.interactWithFluidHandler(playerIn, hand, fluidHandler);
        return !(heldItem.getItem() instanceof ItemBlock);
    }

    @Override
    @Nonnull
    public BlockStateContainer createBlockState () {

        return new ExtendedBlockState(this, new IProperty[] {}, new IUnlistedProperty[] { Blockstates.HELD_STATE, Blockstates.BLOCK_ACCESS, Blockstates.BLOCKPOS });
    }

    @Override
    @Nonnull
    public IBlockState getExtendedState (@Nonnull    IBlockState state, IBlockAccess world, BlockPos pos) {

        state = ((IExtendedBlockState) state).withProperty(Blockstates.BLOCK_ACCESS, world).withProperty(Blockstates.BLOCKPOS, pos);

        if (world.getTileEntity(pos) instanceof TileEntityModularTank) {

            final TileEntityModularTank tile = (TileEntityModularTank) world.getTileEntity(pos);

            if (tile != null && tile.getTier() != null)
                return ((IExtendedBlockState) state).withProperty(Blockstates.HELD_STATE, tile.getTier().renderState);
        }
        return state;
    }

    @Override
    public boolean hasComparatorInputOverride (IBlockState state) {

        return true;
    }

    @Override
    public TileEntity createNewTileEntity (@Nonnull World world, int meta) {

        return new TileEntityModularTank();
    }

    @Override
    public boolean isOpaqueCube (IBlockState state) {

        return false;
    }

    @Override
    @Nonnull
    public BlockRenderLayer getRenderLayer () {

        return BlockRenderLayer.CUTOUT;
    }

    @Override
    @Nonnull
    public EnumBlockRenderType getRenderType (IBlockState state) {

        return EnumBlockRenderType.MODEL;
    }

    @Override
    @Nonnull
    public ItemStack getPickBlock (@Nonnull IBlockState state, RayTraceResult target, @Nonnull World world, @Nonnull BlockPos pos, EntityPlayer player) {

        return StackUtils.createStackFromTileEntity(world.getTileEntity(pos));
    }

    @Override
    public int quantityDropped (Random rnd) {

        return 0;
    }

    @Override
    public int getComparatorInputOverride (IBlockState blockState, World worldIn, BlockPos pos) {

        return 0;
    }

    @Override
    public boolean removedByPlayer (@Nonnull IBlockState state, World world, @Nonnull BlockPos pos, @Nonnull EntityPlayer player, boolean willHarvest) {

        if (!player.capabilities.isCreativeMode) {

            final TileEntityModularTank tank = (TileEntityModularTank) world.getTileEntity(pos);
            StackUtils.dropStackInWorld(world, pos, StackUtils.createStackFromTileEntity(tank));
        }

        return world.setBlockToAir(pos);
    }

    @Override
    public void onBlockPlacedBy (World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {

        if (stack.hasTagCompound()) {

            final TileEntityModularTank tank = (TileEntityModularTank) worldIn.getTileEntity(pos);

            if (tank != null) {
                tank.readNBT(stack.getTagCompound().getCompoundTag("TileData"));
            }
        }
    }

    @Override
    public void onBlockExploded (World world, @Nonnull BlockPos pos, @Nonnull Explosion explosion) {

        StackUtils.dropStackInWorld(world, pos, StackUtils.createStackFromTileEntity(world.getTileEntity(pos)));
        world.setBlockToAir(pos);
        this.onExplosionDestroy(world, pos, explosion);
    }

    @Override
    public void getSubBlocks (CreativeTabs itemIn, NonNullList<ItemStack> items) {

        for (final TankTier tier : GythApi.REGISTRY.values()) {
            items.add(GythApi.createTieredTank(tier));
        }
    }

    public static ItemStack getVariantFromTag (NBTTagCompound tag) {

        ItemStack stack = new ItemStack(Blocks.LOG);

        if (tag != null && tag.hasKey("TierID")) {

            final ItemStack tagStack = new ItemStack(tag.getCompoundTag("TierID"));

            if (!tagStack.isEmpty()) {

                stack = tagStack;
            }
        }

        return stack;
    }
}