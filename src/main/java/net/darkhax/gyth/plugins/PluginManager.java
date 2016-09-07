package net.darkhax.gyth.plugins;

public class PluginManager {

    public PluginManager() {

//        FMLInterModComms.sendMessage("Waila", "register", "net.darkhax.gyth.plugins.PluginWaila.callbackRegister");

        new PluginThaumcraft();
        new PluginTinkersConstruct();
        new PluginDendrology();
    }
}
