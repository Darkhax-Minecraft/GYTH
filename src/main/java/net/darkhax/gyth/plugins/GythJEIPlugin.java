package net.darkhax.gyth.plugins;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.ISubtypeRegistry.ISubtypeInterpreter;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.VanillaTypes;
import net.darkhax.gyth.Gyth;
import net.darkhax.gyth.api.GythApi;
import net.darkhax.gyth.api.TankTier;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

@JEIPlugin
public class GythJEIPlugin implements IModPlugin {
    
    @Override
    public void register (IModRegistry registry) {
        
        registry.addIngredientInfo(new ItemStack(Gyth.itemBlockModularTank, 1, OreDictionary.WILDCARD_VALUE), VanillaTypes.ITEM, "jei.gyth.tank.desc");
        registry.addIngredientInfo(new ItemStack(Gyth.itemTankUpgrade, 1, OreDictionary.WILDCARD_VALUE), VanillaTypes.ITEM, "jei.gyth.upgrade.desc");
    }
    
    @Override
    public void registerItemSubtypes (ISubtypeRegistry registry) {
        
        ISubtypeInterpreter interpreter = stack -> {            

            final TankTier tier = GythApi.getTierFromStack(stack);
            return tier != null ? tier.identifier.toString() : "";
        };
        
        registry.registerSubtypeInterpreter(Gyth.itemTankUpgrade, interpreter);
        registry.registerSubtypeInterpreter(Gyth.itemBlockModularTank, interpreter);
    }
}