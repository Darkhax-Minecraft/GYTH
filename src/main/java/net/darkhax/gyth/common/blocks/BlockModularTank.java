package net.darkhax.gyth.common.blocks;

import net.darkhax.gyth.common.tileentity.TileEntityModularTank;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockModularTank extends BlockContainer {

	public BlockModularTank() {

		super(Material.glass);
		//setCreativeTab();
		this.setBlockName("gyth.modularTank");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {

		return new TileEntityModularTank();
	}

	@Override
	public boolean isOpaqueCube() {

		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {

		return false;
	}
}