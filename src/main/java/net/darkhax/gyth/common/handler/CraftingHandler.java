package net.darkhax.gyth.common.handler;

import net.darkhax.gyth.common.items.ItemTankUpgrade;
import net.darkhax.gyth.utils.EnumTankData;
import net.darkhax.gyth.utils.Utilities;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import cpw.mods.fml.common.registry.GameRegistry;

public class CraftingHandler {
    
    public CraftingHandler() {
    
        addTankRecipes();
        addUpgradeRecipes();
        addAbstractUpgradeRecipes();
    }
    
    public void addTankRecipes () {
    
        for (EnumTankData data : EnumTankData.values()) {
            
            if (data.tier == 1)
                GameRegistry.addShapedRecipe(Utilities.getTankStackFromData(data), new Object[] { "xyx", "yzy", "xyx", Character.valueOf('x'), data.craftingStack, Character.valueOf('y'), Blocks.glass_pane, Character.valueOf('z'), Items.bucket });
        }
    }
    
    public void addUpgradeRecipes () {
    
        for (EnumTankData data : EnumTankData.values())
            GameRegistry.addShapedRecipe(ItemTankUpgrade.getUpgradeStackFromEnum(data), new Object[] { "xyx", "yxy", "xyx", Character.valueOf('x'), data.craftingStack, Character.valueOf('y'), Blocks.glass_pane });
    }
    
    @SubscribeEvent
    public void addAbstractUpgradeRecipes () {
    
    }
    
    public void addCamoUpgrade (ItemCraftedEvent event) {
    
    }
}