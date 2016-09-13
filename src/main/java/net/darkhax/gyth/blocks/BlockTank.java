package net.darkhax.gyth.blocks;

import java.util.Random;

import net.darkhax.bookshelf.lib.BlockStates;
import net.darkhax.bookshelf.lib.util.ItemStackUtils;
import net.darkhax.gyth.Gyth;
import net.darkhax.gyth.api.GythApi;
import net.darkhax.gyth.api.TankTier;
import net.darkhax.gyth.items.ItemTankUpgrade;
import net.darkhax.gyth.tileentity.TileEntityModularTank;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class BlockTank extends BlockContainer {
    
    public BlockTank() {
        
        super(Material.GLASS);
        this.setUnlocalizedName("gyth.tank");
        this.setRegistryName(new ResourceLocation("gyth", "modular_tank"));
        this.setCreativeTab(Gyth.tabGyth);
        this.setHardness(0.3F);
        this.setSoundType(SoundType.GLASS);
        this.setDefaultState(((IExtendedBlockState) this.blockState.getBaseState()).withProperty(BlockStates.HELD_STATE, null).withProperty(BlockStates.BLOCK_ACCESS, null).withProperty(BlockStates.BLOCKPOS, null));
    }
    
    @Override
    public boolean onBlockActivated (World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        
        final TileEntityModularTank tank = (TileEntityModularTank) worldIn.getTileEntity(pos);
        
        // Handle bad tank
        if (tank == null || !tank.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side))
            return false;
            
        // Handle upgrade
        if (tank != null && heldItem != null && heldItem.getItem() instanceof ItemTankUpgrade) {
            
            final TankTier upgradeTier = GythApi.getTierFromStack(heldItem);
            
            if (tank != null && !tank.isInvalid() && upgradeTier != null && tank.tier != null && tank.tier.canApplyUpgrage(upgradeTier)) {
                
                tank.upgradeTank(upgradeTier, state);
                ItemStackUtils.consumeStack(heldItem);
                return true;
            }
        }
        
        // Handle input
        final IFluidHandler fluidHandler = tank.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side);
        FluidUtil.interactWithFluidHandler(heldItem, fluidHandler, playerIn);
        return heldItem != null && !(heldItem.getItem() instanceof ItemBlock);
    }
    
    @Override
    public BlockStateContainer createBlockState () {
        
        return new ExtendedBlockState(this, new IProperty[] {}, new IUnlistedProperty[] { BlockStates.HELD_STATE, BlockStates.BLOCK_ACCESS, BlockStates.BLOCKPOS });
    }
    
    @Override
    public IBlockState getExtendedState (IBlockState state, IBlockAccess world, BlockPos pos) {
        
        state = ((IExtendedBlockState) state).withProperty(BlockStates.BLOCK_ACCESS, world).withProperty(BlockStates.BLOCKPOS, pos);
        
        if (world.getTileEntity(pos) instanceof TileEntityModularTank) {
            
            final TileEntityModularTank tile = (TileEntityModularTank) world.getTileEntity(pos);
            
            if (tile != null && tile.tier != null)
                return ((IExtendedBlockState) state).withProperty(BlockStates.HELD_STATE, tile.tier.renderState);
        }
        return state;
    }
    
    @Override
    public boolean hasComparatorInputOverride (IBlockState state) {
        
        return true;
    }
    
    @Override
    public TileEntity createNewTileEntity (World world, int meta) {
        
        return new TileEntityModularTank();
    }
    
    @Override
    public boolean isOpaqueCube (IBlockState state) {
        
        return false;
    }
    
    @Override
    public BlockRenderLayer getBlockLayer () {
        
        return BlockRenderLayer.CUTOUT;
    }
    
    @Override
    public EnumBlockRenderType getRenderType (IBlockState state) {
        
        return EnumBlockRenderType.MODEL;
    }
    
    @Override
    public ItemStack getPickBlock (IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        
        return ItemStackUtils.createStackFromTileEntity(world.getTileEntity(pos));
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
    public boolean removedByPlayer (IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        
        if (!player.capabilities.isCreativeMode) {
            
            final TileEntityModularTank tank = (TileEntityModularTank) world.getTileEntity(pos);
            ItemStackUtils.dropStackInWorld(world, pos, ItemStackUtils.createStackFromTileEntity(tank));
        }
        
        return world.setBlockToAir(pos);
    }
    
    @Override
    public void onBlockPlacedBy (World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        
        if (stack.hasTagCompound()) {
            
            final TileEntityModularTank tank = (TileEntityModularTank) worldIn.getTileEntity(pos);
            
            System.out.println("placed");
            if (tank != null)
                tank.readNBT(stack.getTagCompound().getCompoundTag("TileData"));
        }
    }
    
    @Override
    public void onBlockExploded (World world, BlockPos pos, Explosion explosion) {
        
        ItemStackUtils.dropStackInWorld(world, pos, ItemStackUtils.createStackFromTileEntity(world.getTileEntity(pos)));
        world.setBlockToAir(pos);
        this.onBlockDestroyedByExplosion(world, pos, explosion);
    }
}