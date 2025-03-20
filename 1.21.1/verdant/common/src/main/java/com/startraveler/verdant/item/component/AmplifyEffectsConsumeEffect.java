/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * If you modify this file, please include a notice stating the changes:
 * Example: "Modified by [Your Name] on [Date] - [Short Description of Changes]"
 */
package com.startraveler.verdant.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.startraveler.verdant.registry.ConsumeEffectRegistry;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record AmplifyEffectsConsumeEffect(Optional<MobEffectCategory> category) implements ConsumeEffect {

    public static final MapCodec<MobEffectCategory> CATEGORY_MAP_CODEC = RecordCodecBuilder.mapCodec((ops) -> ops.group(
            Codec.STRING.fieldOf("name").forGetter(MobEffectCategory::name)).apply(ops, MobEffectCategory::valueOf));
    public static final MapCodec<AmplifyEffectsConsumeEffect> CODEC = RecordCodecBuilder.mapCodec((ops) -> ops.group(
                    CATEGORY_MAP_CODEC.codec().optionalFieldOf("category").forGetter(AmplifyEffectsConsumeEffect::category))
            .apply(ops, AmplifyEffectsConsumeEffect::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, Optional<MobEffectCategory>> OPTIONAL_CATEGORY_MAP_STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            optional -> optional.map(Enum::name).orElse(""),
            string -> string.isEmpty() ? Optional.empty() : Optional.of(MobEffectCategory.valueOf(string))
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, AmplifyEffectsConsumeEffect> STREAM_CODEC = StreamCodec.composite(
            OPTIONAL_CATEGORY_MAP_STREAM_CODEC,
            AmplifyEffectsConsumeEffect::category,
            AmplifyEffectsConsumeEffect::new
    );

    @Override
    public Type<? extends ConsumeEffect> getType() {
        return ConsumeEffectRegistry.AMPLIFY_EFFECTS.get();
    }

    @Override
    public boolean apply(Level level, ItemStack itemStack, LivingEntity livingEntity) {

        if (!level.isClientSide) {
            // Find the mob effect instance that the eater has.
            List<MobEffectInstance> effects = new ArrayList<>(livingEntity.getActiveEffects());

            for (MobEffectInstance instance : effects) {
                Holder<MobEffect> effect = instance.getEffect();
                if (this.category.isEmpty() || this.category.get() == effect.value().getCategory()) {
                    livingEntity.removeEffect(effect);
                    livingEntity.addEffect(
                            new MobEffectInstance(
                                    effect,
                                    (2 * instance.getDuration()) / 3 + 60,
                                    instance.getAmplifier() + 1,
                                    instance.isAmbient(),
                                    instance.isVisible(),
                                    instance.showIcon()
                            ), livingEntity
                    );
                }
            }
        }
        return false;
    }
}
