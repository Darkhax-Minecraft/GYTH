package net.darkhax.gyth.plugins;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import net.darkhax.gyth.Gyth;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

@JEIPlugin
public class GythJEIPlugin implements IModPlugin {

    @Override
    public void register (IModRegistry registry) {

        registry.addIngredientInfo(new ItemStack(Gyth.itemBlockModularTank, 1, OreDictionary.WILDCARD_VALUE), ItemStack.class, "jei.gyth.tank.desc");
        registry.addIngredientInfo(new ItemStack(Gyth.itemTankUpgrade, 1, OreDictionary.WILDCARD_VALUE), ItemStack.class, "jei.gyth.upgrade.desc");
    }
}