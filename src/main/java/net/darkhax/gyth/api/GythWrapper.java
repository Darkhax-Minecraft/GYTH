package net.darkhax.gyth.api;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

/**
 * Wrapper class for the GYTH IMC API. This allows for API support without the need to build
 * from GYTH, or reference any of its classes directly. If you want to take this class, or the
 * methods and include them in your own mod, that is completely fine. No rights reserved on
 * this. More info on the API can be seen on the GitHub README.md file as well.
 * https://github.com/Darkhax-Minecraft/GYTH/blob/master/README.md
 */
public class GythWrapper {
    
    /**
     * Wrapper for the GYTH IMC API. This will create a new tier which can be applied to the
     * tanks.
     * 
     * @param tierName The name of the tier to register. This should be unique to the mod which
     *        added it. Your modId will be added to this on Gyth's end, similarly to how forge
     *        adds it to item/block ids.
     * @param block The block to use for the case of this tank tier.
     * @param meta The meta value for the case block. Must be in range of 0 to 15.
     * @param tier The position of this tier on the hierarchy. Must be in range of 1 to 10.
     * @param recipe The item to use in the tier crafting recipe. Accepts an ItemStack, or an
     *        oredict name!
     */
    public static void addTier (String tierName, Block block, int meta, int tier, Object recipe) {
        
        final NBTTagCompound tag = new NBTTagCompound();
        tag.setString("tierName", tierName);
        tag.setString("blockId", block.getRegistryName().toString());
        tag.setInteger("meta", meta);
        tag.setInteger("tier", tier);
        
        if (recipe instanceof ItemStack) {
            
            final ItemStack stack = (ItemStack) recipe;
            tag.setString("recipe", stack.getItem().getRegistryName().toString() + "#" + stack.getMetadata());
        }
        
        else if (recipe instanceof IForgeRegistryEntry.Impl)
            tag.setString("recipe", ((IForgeRegistryEntry.Impl) recipe).getRegistryName().toString() + "#" + 0);
            
        else
            tag.setString("recipe", (String) recipe);
            
        FMLInterModComms.sendMessage("gyth", "addTier", tag);
    }
    
    /**
     * Wrapper for the GYTH IMC API. This will remove a tier from the mod, preventing it from
     * being craftable. Can be used on any tier, including those added by other mods.
     * 
     * @param tierName The id of the tier to remove.
     */
    public static void removeTier (ResourceLocation tierName) {
        
        FMLInterModComms.sendMessage("gyth", "removeTier", tierName);
    }
}
