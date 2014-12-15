package net.darkhax.gyth.plugins;

import net.darkhax.gyth.utils.EnumTankData;

public class PluginThaumcraft {

    // ID == Thaumcraft
    public PluginThaumcraft(boolean enabled) {

        EnumTankData.addEnumTankData("GREATWOOD", "greatwood", 1, 4);
        EnumTankData.addEnumTankData("SILVERWOOD", "silverwood", 1, 4);
        EnumTankData.addEnumTankData("ARCANESTONE", "arcanestone", 2, 8);
        EnumTankData.addEnumTankData("AMBER", "amber", 3, 32);
        EnumTankData.addEnumTankData("THAUMIUM", "thaumium", 5, 128);
    }
}