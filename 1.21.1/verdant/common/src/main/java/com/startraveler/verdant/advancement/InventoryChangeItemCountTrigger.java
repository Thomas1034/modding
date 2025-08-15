package com.startraveler.verdant.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.startraveler.verdant.registry.TriggerRegistry;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderSet;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class InventoryChangeItemCountTrigger extends SimpleCriterionTrigger<InventoryChangeItemCountTrigger.TriggerInstance> {
    public InventoryChangeItemCountTrigger() {
    }

    public @NotNull Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, Inventory inventory, ItemStack stack) {
        this.trigger(player, (triggerInstance) -> triggerInstance.matches(inventory, stack));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player,
            List<ItemPredicate> items,
            List<Integer> counts) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                ItemPredicate.CODEC.listOf().optionalFieldOf("items", List.of()).forGetter(TriggerInstance::items),
                Codec.INT.listOf().optionalFieldOf("counts", List.of()).forGetter(TriggerInstance::counts)
        ).apply(instance, TriggerInstance::new));

        public TriggerInstance {
            if (counts.isEmpty()) {
                items.replaceAll(item -> new ItemPredicate(item.items(), MinMaxBounds.Ints.ANY, item.components()));
                items.stream().mapToInt(item -> item.count().min().orElse(1)).forEachOrdered(counts::add);
            }
            if (items.size() != counts.size()) {
                throw new IllegalArgumentException("Item predicate and count lists must not have mismatched lengths.");
            }
        }

        public static Criterion<TriggerInstance> hasItems(ItemPredicate.Builder... items) {
            return hasItems(Stream.of(items).map(ItemPredicate.Builder::build).toArray(ItemPredicate[]::new));
        }

        public static Criterion<TriggerInstance> hasItems(ItemPredicate... items) {
            return TriggerRegistry.INVENTORY_CHANGE_ITEM_COUNT_TRIGGER.get().createCriterion(new TriggerInstance(
                    Optional.empty(),
                    Arrays.stream(items)
                            .map(item -> new ItemPredicate(item.items(), MinMaxBounds.Ints.ANY, item.components()))
                            .toList(),
                    Arrays.stream(items).mapToInt(item -> item.count().min().orElse(1)).boxed().toList()
            ));
        }

        public static Criterion<TriggerInstance> hasItems(ItemLike... items) {
            ItemPredicate[] itemPredicates = new ItemPredicate[items.length];

            for (int i = 0; i < items.length; ++i) {
                itemPredicates[i] = new ItemPredicate(
                        Optional.of(HolderSet.direct(items[i].asItem().builtInRegistryHolder())),
                        MinMaxBounds.Ints.ANY,
                        DataComponentMatchers.ANY
                );
            }

            return hasItems(itemPredicates);
        }

        public boolean matches(Inventory inventory, ItemStack stack) {
            if (this.items.isEmpty()) {
                return true;
            } else {

                Object2IntArrayMap<ItemPredicate> conditions = new Object2IntArrayMap<>();
                this.items.forEach(item -> conditions.put(item, 0));

                int containerSize = inventory.getContainerSize();

                for (int j = 0; j < containerSize; ++j) {
                    ItemStack itemStack = inventory.getItem(j);
                    if (!itemStack.isEmpty()) {
                        for (ItemPredicate predicate : conditions.keySet()) {
                            if (predicate.test(itemStack)) {
                                conditions.put(predicate, conditions.getInt(predicate) + itemStack.getCount());
                            }
                        }
                    }
                }

                for (int i = 0, size = this.items.size(); i < size; i++) {
                    ItemPredicate predicate = this.items.get(i);
                    int threshold = this.counts.get(i);
                    int value = conditions.getInt(predicate);
                    if (value < threshold) {
                        return false;
                    }
                }
                return true; // all passed
            }


        }
    }
}