package net.darkhax.gyth.common.handler;

import java.io.File;

import net.darkhax.gyth.utils.Constants;
import net.darkhax.gyth.utils.TankData;
import net.darkhax.gyth.utils.TankTier;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ConfigurationHandler {
    
    public static Configuration config;
    
    public static boolean useFancyRender = false;
    
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
    
        for (TankTier tier : TankData.tiers.values()) {
            
            tier.setCapacity(config.getInt("capacity_" + tier.getName(), "capacity", tier.getCapacity(), 0, 2147483, "How many buckets (1000mb) should the " + tier.getName() + " tank tier hold?"));
            tier.setTier(config.getInt("tier_" + tier.getName(), "tiers", tier.getTier(), 1, 1024, "How high should the " + tier.getName() + " tier be on the tier tree?"));
        }
        
        useFancyRender = config.getBoolean("useFancyRender", "general", false, "If the fancy renerer is used, tanks will render on both the inside and outside.");
        
        if (config.hasChanged())
            config.save();
    }
}