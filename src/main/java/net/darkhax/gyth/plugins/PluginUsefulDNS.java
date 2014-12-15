package net.darkhax.gyth.plugins;

import net.darkhax.gyth.utils.EnumTankData;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PluginUsefulDNS {

    // ID == usefulDNS
    public PluginUsefulDNS(boolean loaded) {
        
        if (loaded) {
            
            EnumTankData.addEnumTankData("DNSSTEEL", "dns_steel", 4, 64, new ItemStack((Item) Item.itemRegistry.getObject("usefulDNS:SteelIngot")));
            EnumTankData.addEnumTankData("DNS", "dns", 5, 128, new ItemStack((Item) Item.itemRegistry.getObject("usefulDNS:DecaNySodiumGem")));
            EnumTankData.addEnumTankData("DNSUNOBTAINIUM", "dns_unobtainium", 10, 4096, new ItemStack((Item) Item.itemRegistry.getObject("usefulDNS:UnobtainIngot")));
        }
    }
}
