package net.darkhax.gyth.plugins;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import net.darkhax.gyth.api.GythApi;
import net.darkhax.gyth.api.TankTier;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.gyth.Tiers")
public class PluginMineTweaker {

    @ZenMethod
    public static void addTier (String name, IIngredient displayBlock, IIngredient recipe, int tier) {

        Block block = null;
        int meta = 0;

        final Object recipeObj = recipe.getInternal();

        if (displayBlock.getInternal() instanceof ItemStack) {

            final ItemStack stack = (ItemStack) displayBlock.getInternal();
            block = Block.getBlockFromItem(stack.getItem());
            meta = stack.getMetadata();
        }

        if (!(recipeObj instanceof String || recipeObj instanceof ItemStack || recipeObj instanceof Item || recipeObj instanceof Block)) {

            CraftTweakerAPI.logInfo("The GYTH tier " + name + " could not be created. The recipe object was invalid! " + recipeObj.toString());
        }

        if (block == null) {

            CraftTweakerAPI.logInfo("The GYTH tier " + name + " could not be created. The display block was null!");
        }

        if (meta < 0 || meta > 15) {

            CraftTweakerAPI.logInfo("The GYTH tier " + name + " could not be created. The meta value was out of range!");
        }

        CraftTweakerAPI.apply(new ActionAddGythTier(name, block, meta, recipeObj, tier));
    }

    @ZenMethod
    public static void removeTier (String tierName) {

        final TankTier tier = GythApi.getTier(tierName);

        if (tier == null) {

            CraftTweakerAPI.logInfo("The GYTH tier could not be removed. No tier found with name " + tierName);
        }

        CraftTweakerAPI.apply(new ActionRemoveGythTier(tier));
    }

    public static class ActionAddGythTier implements IAction {

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

            GythApi.createTier("crafttweaker", this.name, this.block, this.meta, this.recipe, this.tier);
        }

        @Override
        public String describe () {

            return "Creating a GYTH tier called crafttweaker: " + this.name;
        }
    }

    public static class ActionRemoveGythTier implements IAction {

        private final TankTier tier;

        public ActionRemoveGythTier (TankTier tier) {

            this.tier = tier;
        }

        @Override
        public void apply () {

            GythApi.removeTier("crafttweaker", this.tier.identifier);
        }

        @Override
        public String describe () {

            return "Removing a GYTH tier called " + this.tier.identifier.toString();
        }
    }
}