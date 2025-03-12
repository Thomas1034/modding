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
package com.startraveler.verdant.item.custom;

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
import java.util.Collections;
import java.util.List;

public record DiminishEffectConsumeEffect(MobEffectCategory category) implements ConsumeEffect {

    public static final MapCodec<MobEffectCategory> CATEGORY_MAP_CODEC = RecordCodecBuilder.mapCodec((ops) -> ops.group(
            Codec.STRING.fieldOf("name").forGetter(MobEffectCategory::name)).apply(ops, MobEffectCategory::valueOf));
    public static final MapCodec<DiminishEffectConsumeEffect> CODEC = RecordCodecBuilder.mapCodec((ops) -> ops.group(
                    CATEGORY_MAP_CODEC.fieldOf("category").forGetter(DiminishEffectConsumeEffect::category))
            .apply(ops, DiminishEffectConsumeEffect::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, MobEffectCategory> CATEGORY_MAP_STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            MobEffectCategory::name,
            MobEffectCategory::valueOf
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, DiminishEffectConsumeEffect> STREAM_CODEC = StreamCodec.composite(
            CATEGORY_MAP_STREAM_CODEC,
            DiminishEffectConsumeEffect::category,
            DiminishEffectConsumeEffect::new
    );

    @Override
    public Type<? extends ConsumeEffect> getType() {
        return ConsumeEffectRegistry.DIMINISH_EFFECT.get();
    }

    @Override
    public boolean apply(Level level, ItemStack itemStack, LivingEntity livingEntity) {

        if (!level.isClientSide) {
            // Find the mob effect instance that the eater has.
            List<MobEffectInstance> effects = new ArrayList<>(livingEntity.getActiveEffects());
            Collections.shuffle(effects);

            for (MobEffectInstance instance : effects) {
                Holder<MobEffect> effect = instance.getEffect();
                if (this.category == null || this.category == effect.value().getCategory()) {
                    livingEntity.removeEffect(effect);

                    break;
                }
            }
        }
        return false;
    }
}
