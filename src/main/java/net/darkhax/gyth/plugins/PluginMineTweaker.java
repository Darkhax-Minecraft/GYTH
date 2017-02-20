package net.darkhax.gyth.plugins;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.mc1102.MineTweakerMod;
import net.darkhax.gyth.api.GythApi;
import net.darkhax.gyth.api.TankTier;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.gyth.Tiers")
public class PluginMineTweaker {

    /**
     * This method exists because Jared was too lazy to implement auto class loading using the
     * ZenClass annotation which he already has. So now I have to make this pointless method.
     * >_<
     */
    public static void registerSelf () {

        MineTweakerAPI.registerClass(PluginMineTweaker.class);
    }

    @ZenMethod
    public static void addTier (String name, IIngredient displayBlock, IIngredient recipe, int tier) {

        Block block = null;
        int meta = 0;
        
        final Object recipeObj = recipe.getInternal();
        
        if (displayBlock.getInternal() instanceof ItemStack) {

            ItemStack stack = (ItemStack) displayBlock.getInternal();
            block = Block.getBlockFromItem(stack.getItem());
            meta = stack.getMetadata();
        }
            
        if (!(recipeObj instanceof String || recipeObj instanceof ItemStack || recipeObj instanceof Item || recipeObj instanceof Block)) {

            MineTweakerAPI.logInfo("The GYTH tier " + name + " could not be created. The recipe object was invalid! " + recipeObj.toString());
        }
        
        if (block == null) {
            
            MineTweakerAPI.logInfo("The GYTH tier " + name + " could not be created. The display block was null!" );
        }
        
        if (meta < 0 || meta > 15) {
            
            MineTweakerAPI.logInfo("The GYTH tier " + name + " could not be created. The meta value was out of range!");
        }

        MineTweakerAPI.apply(new ActionAddGythTier(name, block, meta, recipeObj, tier));
    }

    @ZenMethod
    public static void removeTier (String tierName) {

        final TankTier tier = GythApi.getTier(tierName);

        if (tier == null) {

            MineTweakerAPI.logInfo("The GYTH tier could not be removed. No tier found with name " + tierName);
        }

        MineTweakerAPI.apply(new ActionRemoveGythTier(tier));
    }

    public static class ActionAddGythTier implements IUndoableAction {

        private final String name;

        private final Block block;

        private final int meta;

        private final Object recipe;

        private final int tier;

        public ActionAddGythTier (String name, Block block, int meta, Object recipe, int tier) {

            this.name = name;
            this.block = block;
            this.meta = meta;
            this.recipe = recipe;
            this.tier = tier;
        }

        @Override
        public void apply () {

            GythApi.createTier(MineTweakerMod.MODID, this.name, this.block, this.meta, this.recipe, this.tier);
        }

        @Override
        public void undo () {

        }

        @Override
        public String describe () {

            return "Creating a GYTH tier called crafttweaker: " + this.name;
        }

        @Override
        public String describeUndo () {

            return "Unable to remove GYTH tier! THIS IS NOT AN ERROR";
        }

        @Override
        public boolean canUndo () {

            return true;
        }

        @Override
        public Object getOverrideKey () {

            return null;
        }
    }

    public static class ActionRemoveGythTier implements IUndoableAction {

        private final TankTier tier;

        public ActionRemoveGythTier (TankTier tier) {

            this.tier = tier;
        }

        @Override
        public void apply () {

            GythApi.removeTier(MineTweakerMod.MODID, this.tier.identifier);
        }

        @Override
        public void undo () {

        }

        @Override
        public String describe () {

            return "Removing a GYTH tier called " + this.tier.identifier.toString();
        }

        @Override
        public String describeUndo () {

            return "Unable to re-add GYTH tier! THIS IS NOT AN ERROR";
        }

        @Override
        public boolean canUndo () {

            return true;
        }

        @Override
        public Object getOverrideKey () {

            return null;
        }
    }
}