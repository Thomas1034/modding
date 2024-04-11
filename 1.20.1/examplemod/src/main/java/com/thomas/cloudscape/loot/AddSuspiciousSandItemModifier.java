package com.thomas.cloudscape.loot;

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

// From Kaupenjoe
public class AddSuspiciousSandItemModifier extends LootModifier {
	public static final Supplier<Codec<AddSuspiciousSandItemModifier>> CODEC = Suppliers
			.memoize(() -> RecordCodecBuilder.create(inst -> codecStart(inst)
					.and(ForgeRegistries.ITEMS.getCodec().fieldOf("item").forGetter(m -> m.item))
					.apply(inst, AddSuspiciousSandItemModifier::new)));
	private final Item item;
	private static final float CHANCE = 0.5f; // 50% WAY TOO HIGH!

	public AddSuspiciousSandItemModifier(LootItemCondition[] conditionsIn, Item item) {
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

		if (context.getRandom().nextFloat() < CHANCE) {
			generatedLoot.clear();
			generatedLoot.add(new ItemStack(this.item));
		}

		return generatedLoot;
	}

	@Override
	public Codec<? extends IGlobalLootModifier> codec() {
		return CODEC.get();
	}
}
