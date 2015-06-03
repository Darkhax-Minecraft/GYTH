package net.darkhax.gyth.plugins;

import cpw.mods.fml.common.event.FMLInterModComms;

public class PluginManager {
    
    public PluginManager() {
    
        FMLInterModComms.sendMessage("Waila", "register", "net.darkhax.gyth.plugins.PluginWaila.callbackRegister");
        
        new PluginThaumcraft();
        new PluginTinkersConstruct();
        new PluginDendrology();
    }
}
