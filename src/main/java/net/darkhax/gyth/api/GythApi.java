package net.darkhax.gyth.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.darkhax.bookshelf.lib.util.ItemStackUtils;
import net.darkhax.bookshelf.lib.util.ModUtils;
import net.darkhax.bookshelf.lib.util.OreDictUtils;
import net.darkhax.gyth.Gyth;
import net.darkhax.gyth.libs.Constants;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public class GythApi {
    
    /**
     * Registry of all tiers that have been registered.
     */
    public static final Map<ResourceLocation, TankTier> REGISTRY = new HashMap<ResourceLocation, TankTier>();
    
    // Tier 1
    public static final TankTier WOOD_OAK = createTier("oak", Blocks.PLANKS, 0, new ItemStack(Blocks.PLANKS, 1, 0), 1);
    public static final TankTier WOOD_SPRUCE = createTier("spruce", Blocks.PLANKS, 1, new ItemStack(Blocks.PLANKS, 1, 1), 1);
    public static final TankTier WOOD_BIRCH = createTier("birch", Blocks.PLANKS, 2, new ItemStack(Blocks.PLANKS, 1, 2), 1);
    public static final TankTier WOOD_JUNGLE = createTier("jungle", Blocks.PLANKS, 3, new ItemStack(Blocks.PLANKS, 1, 3), 1);
    public static final TankTier WOOD_ACACIA = createTier("acacia", Blocks.PLANKS, 4, new ItemStack(Blocks.PLANKS, 1, 4), 1);
    public static final TankTier WOOD_DARK_OAK = createTier("dark_oak", Blocks.PLANKS, 5, new ItemStack(Blocks.PLANKS, 1, 5), 1);
    
    // Tier 2
    public static final TankTier STONE_COBBLE = createTier("stone_cobble", Blocks.COBBLESTONE, 0, OreDictUtils.COBBLESTONE, 1);
    public static final TankTier STONE_SMOOTH = createTier("stone_smooth", Blocks.STONE, 0, new ItemStack(Blocks.STONE, 1, 0), 2);
    public static final TankTier STONE_GRANITE = createTier("stone_granite", Blocks.STONE, 1, new ItemStack(Blocks.STONE, 1, 1), 2);
    public static final TankTier STONE_GRANITE_SMOOTH = createTier("stone_granite_smooth", Blocks.STONE, 2, new ItemStack(Blocks.STONE, 1, 2), 2);
    public static final TankTier STONE_DIORITE = createTier("stone_diorite", Blocks.STONE, 3, new ItemStack(Blocks.STONE, 1, 3), 2);
    public static final TankTier STONE_DIORITE_SMOOTH = createTier("stone_diorite_smooth", Blocks.STONE, 4, new ItemStack(Blocks.STONE, 1, 4), 2);
    public static final TankTier STONE_ANDESITE = createTier("stone_andesite", Blocks.STONE, 5, new ItemStack(Blocks.STONE, 1, 5), 2);
    public static final TankTier STONE_ANDESITE_SMOOTH = createTier("stone_andesite_smooth", Blocks.STONE, 6, new ItemStack(Blocks.STONE, 1, 6), 2);
    public static final TankTier SANDSTONE_BRICK = createTier("sandstone_brick", Blocks.SANDSTONE, 0, Blocks.SANDSTONE, 2);
    public static final TankTier SANDSTONE_BRICK_RED = createTier("sandstone_brick_red", Blocks.RED_SANDSTONE, 0, Blocks.RED_SANDSTONE, 2);
    public static final TankTier BRICK = createTier("brick", Blocks.BRICK_BLOCK, 0, OreDictUtils.INGOT_BRICK, 2);
    public static final TankTier BRICK_NETHER = createTier("brick_nether", Blocks.NETHER_BRICK, 0, OreDictUtils.INGOT_BRICK_NETHER, 2);
    public static final TankTier BRICK_STONE = createTier("brick_stone", Blocks.STONEBRICK, 0, Blocks.STONEBRICK, 2);
    public static final TankTier BRICK_END = createTier("brick_end", Blocks.END_BRICKS, 0, Blocks.END_BRICKS, 2);
    public static final TankTier BRICK_PURPUR = createTier("brick_purpur", Blocks.PURPUR_BLOCK, 0, Blocks.PURPUR_BLOCK, 2);
    
    // Tier 3
    public static final TankTier IRON = createTier("iron", Blocks.IRON_BLOCK, 0, OreDictUtils.INGOT_IRON, 3);
    public static final TankTier GOLD = createTier("gold", Blocks.GOLD_BLOCK, 0, OreDictUtils.INGOT_GOLD, 3);
    public static final TankTier OBSIDIAN = createTier("obsidian", Blocks.OBSIDIAN, 0, OreDictUtils.OBSIDIAN, 3);
    
    // Tier 4
    public static final TankTier LAPIS = createTier("lapis", Blocks.LAPIS_BLOCK, 0, OreDictUtils.GEM_LAPIS, 4);
    public static final TankTier REDSTONE = createTier("redstone", Blocks.REDSTONE_BLOCK, 0, OreDictUtils.DUST_REDSTONE, 4);
    public static final TankTier QUARTZ = createTier("quartz", Blocks.QUARTZ_BLOCK, 0, OreDictUtils.GEM_QUARTZ, 4);
    
    // Tier 5
    public static final TankTier DIAMOND = createTier("diamond", Blocks.DIAMOND_BLOCK, 0, OreDictUtils.GEM_DIAMOND, 5);
    public static final TankTier EMERALD = createTier("emerald", Blocks.EMERALD_BLOCK, 0, OreDictUtils.GEM_EMERALD, 5);
    
    /**
     * Registers a tier with the tier registry.
     * 
     * @param identifier The identifier to use.
     * @param tier The tier object to register.
     * @return The tier object being registered, for ease of use.
     */
    public static TankTier registerTier (ResourceLocation identifier, TankTier tier) {
        
        REGISTRY.put(identifier, tier);
        return tier;
    }
    
    /**
     * Retrieves a TankTier based on it's identifier.
     * 
     * @param identifier The identifier to look up.
     * @return The TankTier that was found, or the default tier.
     */
    public static TankTier getTier (String identifier) {
        
        return REGISTRY.get(new ResourceLocation(identifier));
    }
    
    /**
     * Creates a new ItemStack which represents the upgrade item for the tier passed.
     * 
     * @param tier The tier to create an upgrade stack for.
     * @return An ItemStack which represents the upgrade item for the tier.
     */
    public static ItemStack createTierUpgrade (TankTier tier) {
        
        final ItemStack stack = new ItemStack(Gyth.itemTankUpgrade);
        final NBTTagCompound tag = new NBTTagCompound();
        tag.setString("TierID", tier.identifier.toString());
        stack.setTagCompound(tag);
        return stack;
    }
    
    /**
     * Creates a new ItemStack which represents the tank for the tier passed.
     * 
     * @param tier The tier to create an upgrade stack for.
     * @return An ItemStack which represents the tank for the tier.
     */
    public static ItemStack createTieredTank (TankTier tier) {
        
        final ItemStack stack = new ItemStack(Gyth.blockModularTanks);
        ItemStackUtils.prepareDataTag(stack);
        final NBTTagCompound tag = new NBTTagCompound();
        tag.setString("TierID", tier.identifier.toString());
        stack.getTagCompound().setTag("TileData", tag);
        return stack;
    }
    
    /**
     * Reads a TankTier from an ItemStack.
     * 
     * @param stack The stack to read the tier from.
     * @return The TankTier that was found.
     */
    public static TankTier getTierFromStack (ItemStack stack) {
        
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("TierID"))
            return getTier(stack.getTagCompound().getString("TierID"));
            
        return null;
    }
    
    /**
     * Generates the tooltip for a TankTier.
     * 
     * @param tier The tier to represent.
     * @param tooltip The list to add to.
     */
    public static void createTierTooltip (TankTier tier, FluidStack stack, List<String> tooltip) {
        
        if (tier != null) {
            
            if (stack != null) {
                
                tooltip.add(I18n.format("tooltip.gyth.capacity", stack.amount, tier.getCapacity()));
                tooltip.add(I18n.format("tooltip.gyth.contents") + ": " + stack.getLocalizedName());
            }
            
            else
                tooltip.add(I18n.format("tooltip.gyth.capacity", 0, tier.getCapacity()));
                
            tooltip.add(I18n.format("tooltip.gyth.block") + ": " + ItemStackUtils.getStackFromState(tier.renderState, 1).getDisplayName());
            tooltip.add(I18n.format("tooltip.gyth.tier") + ": " + tier.tier);
            tooltip.add(I18n.format("tooltip.gyth.owner", ChatFormatting.BLUE, ModUtils.getModName(tier.identifier.getResourceDomain())));
            return;
        }
        
        tooltip.add(ChatFormatting.RED + "[WARNING] " + ChatFormatting.GRAY + I18n.format("tooltip.gyth.missing"));
    }
    
    public static void removeTier (String modId, ResourceLocation tier) {
        
        if (REGISTRY.remove(tier) != null) {
            
            Constants.LOG.info("The tier " + tier.toString() + " was removed by " + modId);
        }
    }
    
    public static TankTier createTier (String modId, String name, Block block, int meta, Object recipe, int tier) {
        
        final ResourceLocation identifier = new ResourceLocation(modId, name);
        final TankTier tankTier = new TankTier(identifier, block.getStateFromMeta(meta), recipe, tier);
        REGISTRY.put(identifier, tankTier);
        
        return tankTier;
    }
    
    private static TankTier createTier (String name, Block block, int meta, Object recipe, int tier) {
        
        return createTier(Constants.MODID, name, block, meta, recipe, tier);
    }
}