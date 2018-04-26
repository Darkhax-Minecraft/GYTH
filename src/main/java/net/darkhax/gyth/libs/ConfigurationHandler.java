package net.darkhax.gyth.libs;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigurationHandler {

    private static Configuration config = null;

    public static int maxFluidHeat = 450;

    public static boolean handleTemperature = true;

    public static void initConfig (File file) {

        config = new Configuration(file);
        syncConfig();
    }

    public static void syncConfig () {

        maxFluidHeat = config.getInt("maxFluidHeat", Configuration.CATEGORY_GENERAL, 450, 0, Integer.MAX_VALUE, "The maximum heat for a fluid in a flammable tier. 450 kelvin is the burning point for wood. Lava is 1300 kelvin.");
        handleTemperature = config.getBoolean("handleTemperature", Configuration.CATEGORY_GENERAL, true, "Should flammability be taken into consideration for tanks? If so, flammable tanks will not be able to hold fluids hotter than the maxFluidHeat setting.");

        if (config.hasChanged()) {
            config.save();
        }
    }
}