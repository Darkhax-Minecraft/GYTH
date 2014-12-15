package net.darkhax.gyth.common.handler;

import net.darkhax.gyth.common.items.ItemBlockModularTank;
import net.darkhax.gyth.common.items.ItemTankUpgrade;
import net.darkhax.gyth.utils.EnumTankData;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class CraftingHandler {

    public CraftingHandler() {

        addTankRecipes();
        addUpgradeRecipes();
        addAbstractUpgradeRecipes();
    }

    public void addTankRecipes() {

        for (EnumTankData data : EnumTankData.values()) {

            if (data.tier == 1)
                GameRegistry.addShapedRecipe(ItemBlockModularTank.getTankStackFromData(data), new Object[] { "xyx", "yzy", "xyx", Character.valueOf('x'), data.craftingStack, Character.valueOf('y'), Blocks.glass_pane, Character.valueOf('z'), Items.bucket });
        }
    }

    public void addUpgradeRecipes() {

        for (EnumTankData data : EnumTankData.values())
            GameRegistry.addShapedRecipe(ItemTankUpgrade.getUpgradeStackFromEnum(data), new Object[] { "xyx", "yxy", "xyx", Character.valueOf('x'), data.craftingStack, Character.valueOf('y'), Blocks.glass_pane });
    }

    public void addAbstractUpgradeRecipes() {

    }
}
