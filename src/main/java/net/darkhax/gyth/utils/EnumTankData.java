package net.darkhax.gyth.utils;

import net.minecraftforge.common.util.EnumHelper;

public enum EnumTankData {

    ACACIA("acacia", 1),
    BIRCH("birch", 1),
    DARKOAK("darkoak", 1),
    JUNGLE("jungle", 1),
    OAK("oak", 1),
    SPRUCE("spruce", 1),
    STONE("stone", 2),
    IRON("iron", 3),
    LAPIS("lapis", 4),
    REDSTONE("redstone", 4),
    GOLD("gold", 5),
    QUARTZ("quartz", 5),
    DIAMOND("diamond", 6),
    OBSIDIAN("obsidian", 7),
    EMERALD("emerald", 8),
    NETHERSTAR("neterstar", 9);

    public String upgradeName;
    public int tier;

    private EnumTankData(String upgradeName, int tier) {

        this.upgradeName = upgradeName;
        this.tier = tier;
    }

    public static EnumTankData addEnumTankSizeData(String enumName, String upgradeName, int tier) {

        Class<?>[] paramTypes = { String.class, int.class };
        Object[] paramValues = { upgradeName, tier };
        return EnumHelper.addEnum(EnumTankData.class, enumName, paramTypes, paramValues);
    }

    public static int getPosInEnum(String value) {

        for (int i = 0; i < EnumTankData.values().length; i++)
            if (EnumTankData.values()[i].upgradeName.equalsIgnoreCase(value))
                return i;

        return 0;
    }
}