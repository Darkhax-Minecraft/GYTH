package net.darkhax.gyth.common.blocks;

import java.util.HashMap;

import net.darkhax.gyth.client.renderer.RenderModularTank;
import net.darkhax.gyth.common.tileentity.TileEntityModularTank;
import net.darkhax.gyth.utils.EnumTankData;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockModularTank extends BlockContainer {

    public static int renderID;
    public static HashMap<String, IIcon[]> iconArray = new HashMap<String, IIcon[]>();

    public BlockModularTank() {

        super(Material.glass);
        this.setCreativeTab(CreativeTabs.tabDecorations);
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
    public int getRenderType() {

        return renderID;
    }

    @Override
    public boolean renderAsNormalBlock() {

        return false;
    }

    @Override
    public int getRenderBlockPass() {

        return 1;
    }

    @Override
    public boolean canRenderInPass(int pass) {

        RenderModularTank.renderPass = pass;
        return true;
    }

    @Override
    public void registerBlockIcons(IIconRegister ir) {

        for (EnumTankData data : EnumTankData.values()) {

            IIcon[] tieredIcons = new IIcon[6];
            tieredIcons[0] = ir.registerIcon("gyth:" + data.upgradeName + "_bottom");
            tieredIcons[1] = ir.registerIcon("gyth:" + data.upgradeName + "_top");
            tieredIcons[2] = ir.registerIcon("gyth:" + data.upgradeName + "_north");
            tieredIcons[3] = ir.registerIcon("gyth:" + data.upgradeName + "_south");
            tieredIcons[4] = ir.registerIcon("gyth:" + data.upgradeName + "_east");
            tieredIcons[5] = ir.registerIcon("gyth:" + data.upgradeName + "_west");
            iconArray.put(data.upgradeName, tieredIcons);
        }
    }
}