package net.darkhax.gyth.utils;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;

public enum EnumTankData {

    ACACIA("acacia", 1, 4, new ItemStack(Blocks.planks, 1, 4)),
    BIRCH("birch", 1, 4, new ItemStack(Blocks.planks, 1, 2)),
    DARKOAK("darkoak", 1, 4, new ItemStack(Blocks.planks, 1, 5)),
    JUNGLE("jungle", 1, 4, new ItemStack(Blocks.planks, 1, 3)),
    OAK("oak", 1, 4, new ItemStack(Blocks.planks, 1, 0)),
    SPRUCE("spruce", 1, 4, new ItemStack(Blocks.planks, 1, 1)),
    STONE("stone", 2, 16, new ItemStack(Blocks.stone)),
    IRON("iron", 3, 32, new ItemStack(Items.iron_ingot)),
    LAPIS("lapis", 4, 64, new ItemStack(Blocks.lapis_block)),
    REDSTONE("redstone", 4, 64, new ItemStack(Blocks.redstone_block)),
    GOLD("gold", 5, 128, new ItemStack(Items.gold_ingot)),
    QUARTZ("quartz", 5, 128, new ItemStack(Blocks.quartz_block)),
    DIAMOND("diamond", 6, 256, new ItemStack(Items.diamond)),
    OBSIDIAN("obsidian", 7, 512, new ItemStack(Blocks.obsidian)),
    EMERALD("emerald", 8, 1024, new ItemStack(Blocks.emerald_block));
    //2048
    //4096

    public String upgradeName;
    public int tier;
    public int capacity;
    public ItemStack craftingStack;

    private EnumTankData(String upgradeName, int tier, int capacity, ItemStack stack) {

        this.upgradeName = upgradeName;
        this.tier = tier;
        this.capacity = capacity;
        craftingStack = stack;
    }

    public static EnumTankData addEnumTankData(String enumName, String upgradeName, int tier, int capacity, ItemStack stack) {

        Class<?>[] paramTypes = { String.class, int.class, int.class, ItemStack.class };
        Object[] paramValues = { upgradeName, tier, capacity, stack };
        return EnumHelper.addEnum(EnumTankData.class, enumName, paramTypes, paramValues);
    }

    public static int getPosInEnum(String value) {

        for (int i = 0; i < EnumTankData.values().length; i++)
            if (EnumTankData.values()[i].upgradeName.equalsIgnoreCase(value))
                return i;

        return 0;
    }
}