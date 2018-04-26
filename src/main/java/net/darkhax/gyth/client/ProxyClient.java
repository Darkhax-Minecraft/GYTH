package net.darkhax.gyth.client;

import net.darkhax.gyth.Gyth;
import net.darkhax.gyth.client.renderer.ModelTank;
import net.darkhax.gyth.client.renderer.RendererTank;
import net.darkhax.gyth.common.ProxyCommon;
import net.darkhax.gyth.tileentity.TileEntityModularTank;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT)
public class ProxyClient extends ProxyCommon {

    @Override
    public void registerBlockRenderers () {

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityModularTank.class, new RendererTank());
    }

    @SubscribeEvent
    public static void onModelBake (ModelBakeEvent event) {

        try {

            //Tank
            final IModel rawTank = ModelLoaderRegistry.getModel(new ResourceLocation("gyth", "block/modular_tank"));

            final ModelResourceLocation tankBlockLocation = new ModelResourceLocation(new ResourceLocation("gyth", "modular_tank"), "normal");
            final IBakedModel bakedTank = event.getModelRegistry().getObject(tankBlockLocation);
            event.getModelRegistry().putObject(tankBlockLocation, new ModelTank(bakedTank, rawTank));

            final ModelResourceLocation tankItemLocation = new ModelResourceLocation(new ResourceLocation("gyth", "modular_tank"), "inventory");
            final IBakedModel bakedTankItem = event.getModelRegistry().getObject(tankItemLocation);
            event.getModelRegistry().putObject(tankItemLocation, new ModelTank(bakedTankItem, rawTank));

            //Tank Upgrade
            final IModel rawTankUpgrade = ModelLoaderRegistry.getModel(new ResourceLocation("gyth", "item/tank_upgrade"));

            final ModelResourceLocation tankUpgradeLocation = new ModelResourceLocation(new ResourceLocation("gyth", "tank_upgrade"), "inventory");
            final IBakedModel bakedTankUpgrade = event.getModelRegistry().getObject(tankUpgradeLocation);
            event.getModelRegistry().putObject(tankUpgradeLocation, new ModelTank(bakedTankUpgrade, rawTankUpgrade));
        }

        catch (final Exception e) {

            Gyth.LOG.warn(e);
            Gyth.LOG.catching(e);
        }
    }

    
    //TODO Liam pls fix it's been like a year

    /*
     * @SubscribeEvent public void renderItem (RenderItemEvent.Allow event) { if
     * (event.getItemStack().getItem() == Gyth.itemBlockModularTank) { event.setCanceled(true);
     * } }
     * @SubscribeEvent public void renderItem (RenderItemEvent.Pre event) {
     * GlStateManager.pushMatrix(); TankTier tier = null; FluidStack fluid = null; if
     * (event.getItemStack().hasTagCompound() &&
     * event.getItemStack().getTagCompound().hasKey("TileData")) { tier =
     * GythApi.getTier(event.getItemStack().getTagCompound().getCompoundTag("TileData").
     * getString("TierID")); fluid =
     * FluidStack.loadFluidStackFromNBT(event.getItemStack().getTagCompound().getCompoundTag(
     * "TileData").getCompoundTag("FluidData")); } if (tier != null && fluid != null) {
     * GlStateManager.enableBlend(); RenderUtils.renderFluid(fluid, new BlockPos(0, 0, 0),
     * 0.06d, 0.12d, 0.06d, 0.0d, 0.0d, 0.0d, 0.88d, (double) fluid.amount / (double)
     * tier.getCapacity() * 0.8d, 0.88d); GlStateManager.disableBlend(); }
     * GlStateManager.popMatrix(); }
     */
}
