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
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public record RemoveMobEffectsConsumeEffect(MobEffectCategory category, int amountToRemove) implements ConsumeEffect {

    public static final int REMOVE_ALL_IN_CATEGORY = -1;
    public static final MapCodec<MobEffectCategory> CATEGORY_MAP_CODEC = RecordCodecBuilder.mapCodec((ops) -> ops.group(
            Codec.STRING.fieldOf("name").forGetter(MobEffectCategory::name)).apply(ops, MobEffectCategory::valueOf));
    public static final MapCodec<RemoveMobEffectsConsumeEffect> CODEC = RecordCodecBuilder.mapCodec((ops) -> ops.group(
            CATEGORY_MAP_CODEC.fieldOf("category").forGetter(RemoveMobEffectsConsumeEffect::category),
            Codec.INT.fieldOf("amount_to_remove").forGetter(RemoveMobEffectsConsumeEffect::amountToRemove)
    ).apply(ops, RemoveMobEffectsConsumeEffect::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, MobEffectCategory> CATEGORY_MAP_STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            MobEffectCategory::name,
            MobEffectCategory::valueOf
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, RemoveMobEffectsConsumeEffect> STREAM_CODEC = StreamCodec.composite(
            CATEGORY_MAP_STREAM_CODEC,
            RemoveMobEffectsConsumeEffect::category,
            ByteBufCodecs.INT,
            RemoveMobEffectsConsumeEffect::amountToRemove,
            RemoveMobEffectsConsumeEffect::new
    );

    public RemoveMobEffectsConsumeEffect(MobEffectCategory category) {
        this(category, 1);
    }

    @Override
    public @NotNull Type<? extends ConsumeEffect> getType() {
        return ConsumeEffectRegistry.REMOVE_EFFECTS.get();
    }

    @Override
    public boolean apply(Level level, @NotNull ItemStack itemStack, @NotNull LivingEntity livingEntity) {

        if (!level.isClientSide()) {
            // Find the mob effect instance that the eater has.
            List<MobEffectInstance> effects = new ArrayList<>(livingEntity.getActiveEffects());
            Collections.shuffle(effects);

            Stream<MobEffectInstance> filteredEffects = effects.stream()
                    .filter(effect -> (this.category == null) || (effect.getEffect()
                            .value()
                            .getCategory() == this.category));
            if (this.amountToRemove != REMOVE_ALL_IN_CATEGORY) {
                filteredEffects = filteredEffects.limit(Math.max(this.amountToRemove, 0));
            }
            filteredEffects.map(MobEffectInstance::getEffect).forEachOrdered(livingEntity::removeEffect);
            return !effects.isEmpty() && this.amountToRemove > 0;
        }
        return false;
    }
}
