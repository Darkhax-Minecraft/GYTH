package net.darkhax.gyth.utils;

import net.minecraftforge.common.util.EnumHelper;

public enum EnumTankData {

    ACACIA("acacia", 1, 4),
    BIRCH("birch", 1, 4),
    DARKOAK("darkoak", 1, 4),
    JUNGLE("jungle", 1, 4),
    OAK("oak", 1, 4),
    SPRUCE("spruce", 1, 4),
    STONE("stone", 2, 16),
    IRON("iron", 3, 32),
    LAPIS("lapis", 4, 64),
    REDSTONE("redstone", 4, 64),
    GOLD("gold", 5, 128),
    QUARTZ("quartz", 5, 128),
    DIAMOND("diamond", 6, 256),
    OBSIDIAN("obsidian", 7, 512),
    EMERALD("emerald", 8, 1024);

    public String upgradeName;
    public int tier;
    public int capacity;

    private EnumTankData(String upgradeName, int tier, int capacity) {

        this.upgradeName = upgradeName;
        this.tier = tier;
        this.capacity = capacity;
    }

    public static EnumTankData addEnumTankData(String enumName, String upgradeName, int tier, int capacity) {

        Class<?>[] paramTypes = { String.class, int.class, int.class };
        Object[] paramValues = { upgradeName, tier, capacity };
        return EnumHelper.addEnum(EnumTankData.class, enumName, paramTypes, paramValues);
    }

    public static int getPosInEnum(String value) {

        for (int i = 0; i < EnumTankData.values().length; i++)
            if (EnumTankData.values()[i].upgradeName.equalsIgnoreCase(value))
                return i;

        return 0;
    }
}