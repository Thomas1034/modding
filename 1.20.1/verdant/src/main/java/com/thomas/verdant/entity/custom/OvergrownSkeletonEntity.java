package com.thomas.verdant.entity.custom;

import com.thomas.verdant.entity.ModEntityTypes;
import com.thomas.verdant.item.ModItems;
import com.thomas.verdant.overgrowth.EntityOvergrowthEffects;

import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class OvergrownSkeletonEntity extends Skeleton {

	public OvergrownSkeletonEntity(EntityType<? extends Skeleton> type, Level level) {
		super(type, level);
	}

	public OvergrownSkeletonEntity(Level level) {
		super(ModEntityTypes.OVERGROWN_SKELETON.get(), level);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Blaze.class, 6.0F, 1.0D, 1.2D));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2,
				new NearestAttackableTargetGoal<>(this, Player.class, true, EntityOvergrowthEffects::isEnemy));
		this.targetSelector.addGoal(3,
				new NearestAttackableTargetGoal<>(this, IronGolem.class, true, EntityOvergrowthEffects::isEnemy));
	}

	// Attributes
	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.ARMOR, 2.0D);
	}

	// Noninflammable.
	@Override
	protected boolean isSunBurnTick() {
		return false;
	}

	// Poison arrows.
	@Override
	protected AbstractArrow getArrow(ItemStack stack, float f) {
		ArrowItem arrowitem = (ArrowItem) (stack.getItem() instanceof ArrowItem ? stack.getItem()
				: ModItems.POISON_ARROW.get());
		// Replace arrow.
		if (arrowitem == Items.ARROW) {
			arrowitem = (ArrowItem) ModItems.POISON_ARROW.get();
		}
		// Now create entity.
		AbstractArrow abstractarrow = arrowitem.createArrow(this.level(), stack, this);
		abstractarrow.setEnchantmentEffectsFromEntity(this, f);
		if (stack.is(Items.TIPPED_ARROW) && abstractarrow instanceof Arrow) {
			((Arrow) abstractarrow).setEffectsFromItem(stack);
		}

		return abstractarrow;
	}

}
