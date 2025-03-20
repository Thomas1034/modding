package com.startraveler.verdant.registry;

import com.startraveler.verdant.item.component.BlowdartTippingIngredient;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class BlowdartTippingIngredientRegistry {

    public static void addIngredients(BiConsumer<Item, Consumer<BiConsumer<DataComponentType<BlowdartTippingIngredient>, BlowdartTippingIngredient>>> event) {
        event.accept(
                Items.SPIDER_EYE, builder -> builder.accept(
                        DataComponentRegistry.BLOWDART_TIPPING_INGREDIENT.get(),
                        new BlowdartTippingIngredient(List.of(new MobEffectInstance(MobEffects.POISON, 140, 0)))
                )
        );

        event.accept(
                BlockRegistry.POISON_IVY.get().asItem(),
                builder -> builder.accept(
                        DataComponentRegistry.BLOWDART_TIPPING_INGREDIENT.get(),
                        new BlowdartTippingIngredient(List.of(new MobEffectInstance(MobEffects.POISON, 70, 2)))
                )
        );

        event.accept(
                Items.PUFFERFISH, (builder -> builder.accept(
                        DataComponentRegistry.BLOWDART_TIPPING_INGREDIENT.get(), new BlowdartTippingIngredient(List.of(
                                new MobEffectInstance(MobEffects.CONFUSION, 140, 0),
                                new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 20, 0)
                        ))
                ))
        );

        event.accept(
                Items.GLOW_INK_SAC, (builder -> builder.accept(
                        DataComponentRegistry.BLOWDART_TIPPING_INGREDIENT.get(),
                        new BlowdartTippingIngredient(List.of(new MobEffectInstance(MobEffects.GLOWING, 70, 0)))
                ))
        );

        event.accept(
                Items.BLAZE_POWDER, (builder -> builder.accept(
                        DataComponentRegistry.BLOWDART_TIPPING_INGREDIENT.get(),
                        new BlowdartTippingIngredient(List.of(new MobEffectInstance(
                                MobEffectRegistry.INNER_FIRE.asHolder(),
                                20,
                                0
                        )))
                ))
        );

        event.accept(
                Items.MAGMA_CREAM, (builder -> builder.accept(
                        DataComponentRegistry.BLOWDART_TIPPING_INGREDIENT.get(),
                        new BlowdartTippingIngredient(List.of(new MobEffectInstance(
                                MobEffectRegistry.INNER_FIRE.asHolder(),
                                100,
                                0
                        )))
                ))
        );

        event.accept(
                Items.WARPED_ROOTS, (builder -> builder.accept(
                        DataComponentRegistry.BLOWDART_TIPPING_INGREDIENT.get(),
                        new BlowdartTippingIngredient(List.of(new MobEffectInstance(
                                MobEffectRegistry.FLICKERING.asHolder(),
                                70,
                                0
                        )))
                ))
        );

        event.accept(
                Items.CRIMSON_ROOTS, (builder -> builder.accept(
                        DataComponentRegistry.BLOWDART_TIPPING_INGREDIENT.get(),
                        new BlowdartTippingIngredient(List.of(new MobEffectInstance(
                                MobEffects.MOVEMENT_SLOWDOWN,
                                70,
                                0
                        )))
                ))
        );

        event.accept(
                Items.WARPED_FUNGUS, (builder -> builder.accept(
                        DataComponentRegistry.BLOWDART_TIPPING_INGREDIENT.get(),
                        new BlowdartTippingIngredient(List.of(new MobEffectInstance(
                                MobEffectRegistry.FLICKERING.asHolder(),
                                140,
                                0
                        )))
                ))
        );

        event.accept(
                Items.CRIMSON_FUNGUS, (builder -> builder.accept(
                        DataComponentRegistry.BLOWDART_TIPPING_INGREDIENT.get(),
                        new BlowdartTippingIngredient(List.of(new MobEffectInstance(
                                MobEffectRegistry.RED_GREEN.asHolder(),
                                200,
                                0
                        )))
                ))
        );
    }

}
