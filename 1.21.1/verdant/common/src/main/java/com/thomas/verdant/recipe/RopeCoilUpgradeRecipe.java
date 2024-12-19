package com.thomas.verdant.recipe;

import com.thomas.verdant.item.component.RopeCoilData;
import com.thomas.verdant.registry.DataComponentRegistry;
import com.thomas.verdant.registry.ItemRegistry;
import com.thomas.verdant.registry.RecipeSerializerRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class RopeCoilUpgradeRecipe extends CustomRecipe {

    public RopeCoilUpgradeRecipe(CraftingBookCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        return assemble(input, level.registryAccess()) != ItemStack.EMPTY;
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        // There must be at least two items to perform the recipe.
        if (input.ingredientCount() < 2) {
            return ItemStack.EMPTY;
        }
        // First, check to see if there is a rope coil somewhere here.
        // This is done by checking for the component.
        boolean ropeCoilIsPresent = false;
        int ropeIndex = -1;
        for (int i = 0; i < input.size(); i++) {
            if (input.getItem(i).has(DataComponentRegistry.ROPE_COIL.get())) {
                ropeCoilIsPresent = true;
                ropeIndex = i;
            }
        }
        // If no rope coil was found, return false.
        if (!ropeCoilIsPresent) {
            return ItemStack.EMPTY;
        }
        // Otherwise, check for illegal items.
        // This varies based on the component, so get the component!
        RopeCoilData component = input.getItem(ropeIndex).get(DataComponentRegistry.ROPE_COIL.get());
        if (component == null) {
            return ItemStack.EMPTY;
        }
        // The maximum allowed length that can be added to the coil.
        int remainingAllowedLength = RopeCoilData.MAX_LENGTH_FROM_CRAFTING - component.length();
        int resultLength = component.length();
        // Whether adding a hook is allowed.
        boolean canAddHook = !component.hasHook();
        boolean resultHasHook = component.hasHook();
        for (int i = 0; i < input.size(); i++) {
            // Skip the rope coil, it's allowed.
            if (i == ropeIndex) {
                continue;
            }
            ItemStack stack = input.getItem(i);
            Item item = stack.getItem();
            if (item == ItemRegistry.ROPE.get()) {
                // If it is rope, decrement the amount of rope that can be added further.
                if (remainingAllowedLength > 0) {
                    remainingAllowedLength--;
                    resultLength++;
                } else {
                    // If no more rope can be added, the recipe fails.
                    return ItemStack.EMPTY;
                }
            } else if (item == Items.TRIPWIRE_HOOK) {
                // If it is a hook, check if a hook can be added.
                // If not, fail. If so, disallow further hooks.
                if (canAddHook) {
                    canAddHook = false;
                    resultHasHook = true;
                } else {
                    return ItemStack.EMPTY;
                }
            } else if (stack.has(DataComponentRegistry.ROPE_COIL.get())) {
                // Allow combining rope coils, if they're not too long and don't both have hooks.
                RopeCoilData data = stack.get(DataComponentRegistry.ROPE_COIL.get());
                if (data == null) {
                    // This shouldn't happen... if it does, we have a problem.
                    return ItemStack.EMPTY;
                }
                if (data.length() <= remainingAllowedLength) {
                    remainingAllowedLength -= data.length();
                    resultLength += data.length();
                } else {
                    // It's too long to be combined.
                    return ItemStack.EMPTY;
                }
                if (data.hasHook()) {
                    if (canAddHook) {
                        // It has a hook and a hook can be added.
                        // No further hooks can be added.
                        canAddHook = false;
                        resultHasHook = true;
                    } else {
                        // It has a hook and a hook cannot be added.
                        // Fail.
                        return ItemStack.EMPTY;
                    }
                }
            }
        }
        ItemStack result = new ItemStack(ItemRegistry.ROPE_COIL.get());
        result.set(DataComponentRegistry.ROPE_COIL.get(), new RopeCoilData(resultLength, resultHasHook));
        return result;
    }

    @Override
    public RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return RecipeSerializerRegistry.ROPE_COIL_SERIALIZER.get();
    }
}
