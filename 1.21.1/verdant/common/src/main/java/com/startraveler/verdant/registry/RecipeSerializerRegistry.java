package com.startraveler.verdant.registry;

import com.startraveler.verdant.Constants;
import com.startraveler.verdant.recipe.RopeCoilUpgradeRecipe;
import com.startraveler.verdant.registration.RegistrationProvider;
import com.startraveler.verdant.registration.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class RecipeSerializerRegistry {

    public static final RegistrationProvider<RecipeSerializer<?>> SERIALIZERS = RegistrationProvider.get(
            Registries.RECIPE_SERIALIZER,
            Constants.MOD_ID
    );

    public static final RegistryObject<RecipeSerializer<?>, CustomRecipe.Serializer<RopeCoilUpgradeRecipe>> ROPE_COIL_SERIALIZER = SERIALIZERS.register("rope_coil_upgrade",
            () -> new CustomRecipe.Serializer<>(RopeCoilUpgradeRecipe::new)
    );

    public static void init() {

    }

}
