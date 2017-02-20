package net.darkhax.gyth.plugins;

import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import net.darkhax.gyth.Gyth;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

@JEIPlugin
public class GythJEIPlugin extends BlankModPlugin {

    @Override
    public void register (IModRegistry registry) {

        registry.addDescription(new ItemStack(Gyth.itemBlockModularTank, 1, OreDictionary.WILDCARD_VALUE), "jei.gyth.tank.desc");
        registry.addDescription(new ItemStack(Gyth.itemTankUpgrade, 1, OreDictionary.WILDCARD_VALUE), "jei.gyth.upgrade.desc");
    }
}