package net.darkhax.gyth.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.darkhax.gyth.common.handler.ConfigurationHandler;
import net.darkhax.gyth.utils.Constants;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

public class GuiConfigGyth extends GuiConfig {
    
    static Configuration cfg = ConfigurationHandler.config;
    static ConfigurationHandler cfgh;
    
    public GuiConfigGyth(GuiScreen parent) {
        
        super(parent, generateConfigList(), Constants.MODID, false, false, GuiConfig.getAbridgedConfigPath(ConfigurationHandler.config.toString()));
    }
    
    public static List<IConfigElement> generateConfigList () {
        
        final ArrayList<IConfigElement> elements = new ArrayList<IConfigElement>();
        elements.add(new ConfigElement(cfg.getCategory("capacity")));
        elements.add(new ConfigElement(cfg.getCategory("tiers")));
        elements.add(new ConfigElement(cfg.getCategory("general")));
        return elements;
    }
}