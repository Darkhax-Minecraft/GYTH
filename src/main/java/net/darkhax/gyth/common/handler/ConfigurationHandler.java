package net.darkhax.gyth.common.handler;

import java.io.File;

import net.darkhax.gyth.utils.Constants;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ConfigurationHandler {
    
    public static Configuration config;
    
    public ConfigurationHandler(File configFile) {
    
        config = new Configuration(configFile);
        FMLCommonHandler.instance().bus().register(this);
        syncConfigData();
    }
    
    @SubscribeEvent
    public void onConfigChange (ConfigChangedEvent.OnConfigChangedEvent event) {
    
        if (event.modID.equals(Constants.MODID))
            syncConfigData();
    }
    
    private void syncConfigData () {
    
        config.save();
    }
    
    public static String tierNameAcacia;
    public static int tierAcacia;
    public static int storageAcacia;
}