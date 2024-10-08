package com.thomas.verdant.datagen.lootmodifiers;

import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;

public class AddTableModifier extends LootModifier {
	public static final Supplier<Codec<AddTableModifier>> CODEC = Suppliers.memoize(() -> RecordCodecBuilder
			.create(inst -> codecStart(inst).and(ResourceLocation.CODEC.fieldOf("table").forGetter(m -> m.table))
					.apply(inst, AddTableModifier::new)));
	private final ResourceLocation table;

	public AddTableModifier(LootItemCondition[] conditionsIn, ResourceLocation table) {
		super(conditionsIn);
		this.table = table;
	}

	@Override
	protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot,
			LootContext context) {

		for (LootItemCondition condition : this.conditions) {
			if (!condition.test(context)) {
				return generatedLoot;
			}
		}

		// Add the table now

		LootContext newContext = new LootContext.Builder(context).withQueriedLootTableId(this.table).create(this.table);

		LootTable addedTable = newContext.getLevel().getServer().getLootData().getLootTable(this.table);
		addedTable.getRandomItems(newContext, generatedLoot::add);

		return generatedLoot;
	}

	@Override
	public Codec<? extends IGlobalLootModifier> codec() {
		return CODEC.get();
	}
}