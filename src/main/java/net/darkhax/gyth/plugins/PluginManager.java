package net.darkhax.gyth.plugins;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInterModComms;

public class PluginManager {

    public PluginManager() {

        FMLInterModComms.sendMessage("Waila", "register", "net.darkhax.gyth.plugins.PluginWaila.callbackRegister");
        new PluginThaumcraft(Loader.isModLoaded("Thaumcraft"));
        new PluginUsefulDNS(Loader.isModLoaded("usefulDNS"));
        new PluginTinkersConstruct(Loader.isModLoaded("TConstruct"));
    }
}
