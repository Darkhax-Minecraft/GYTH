package net.darkhax.gyth.common.handler;

import net.darkhax.gyth.utils.TankData;
import net.darkhax.gyth.utils.TankTier;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import cpw.mods.fml.common.registry.GameRegistry;

public class CraftingHandler {
    
    public CraftingHandler() {
    
        for (TankTier tier : TankData.tiers.values()) {
            
            // Creates tier 1 block crafting
            if (tier.getTier() == 1)
                GameRegistry.addShapedRecipe(tier.getTankItemStack(), new Object[] { "xyx", "yzy", "xyx", Character.valueOf('x'), tier.getCraftingStack(), Character.valueOf('y'), Blocks.glass_pane, Character.valueOf('z'), Items.bucket });
            
            // Creates tier upgrade items
            GameRegistry.addShapedRecipe(tier.getUpgradeItemStack(), new Object[] { "xyx", "yxy", "xyx", Character.valueOf('x'), tier.getCraftingStack(), Character.valueOf('y'), Blocks.glass_pane });
        }
    }
}