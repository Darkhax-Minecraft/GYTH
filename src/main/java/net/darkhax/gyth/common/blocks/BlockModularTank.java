package net.darkhax.gyth.common.blocks;

import net.darkhax.gyth.client.renderer.RenderModularTank;
import net.darkhax.gyth.common.tileentity.TileEntityModularTank;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockModularTank extends BlockContainer {
            
    public static int renderID;
    
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
    public int getRenderBlockPass () {
        
        return 1;
    }
    
    @Override
    public boolean canRenderInPass (int pass) {
        
        RenderModularTank.renderPass = pass;
        return true;
    }
}