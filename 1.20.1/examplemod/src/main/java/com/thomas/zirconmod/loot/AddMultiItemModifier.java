package com.thomas.zirconmod.loot;

import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;

// Extend to implement custom ranges.
public class AddMultiItemModifier extends LootModifier {
	public static final Supplier<Codec<AddMultiItemModifier>> CODEC = Suppliers.memoize(() -> RecordCodecBuilder.create(
			inst -> codecStart(inst).and(ForgeRegistries.ITEMS.getCodec().fieldOf("item").forGetter(m -> m.item))
					.apply(inst, (conditionArray, item) -> new AddMultiItemModifier(conditionArray, item) )));
	private final Item item;

	public AddMultiItemModifier(LootItemCondition[] conditionsIn, Item item) {
		super(conditionsIn);
		this.item = item;
	}

	@Override
	protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot,
			LootContext context) {
		for (LootItemCondition condition : this.conditions) {
			if (!condition.test(context)) {
				return generatedLoot;
			}
		}

		generatedLoot.add(new ItemStack(this.item, this.getCount(context)));

		return generatedLoot;
	}

	@Override
	public Codec<? extends IGlobalLootModifier> codec() {
		return CODEC.get();
	}
	
	// Returns the count of items to return.
	public int getCount(LootContext context) {
		return 1;
	}
}
