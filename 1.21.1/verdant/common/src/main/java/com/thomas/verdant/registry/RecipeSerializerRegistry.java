package com.thomas.verdant.registry;

import com.thomas.verdant.Constants;
import com.thomas.verdant.recipe.RopeCoilUpgradeRecipe;
import com.thomas.verdant.registration.RegistrationProvider;
import com.thomas.verdant.registration.RegistryObject;
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
