package com.thomas.cloudscape.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class FlamingArrowItem extends ArrowItem {

	public FlamingArrowItem(Item.Properties p_40512_) {
		super(p_40512_);
	}

	public AbstractArrow createArrow(Level level, ItemStack p_40514_, LivingEntity p_40515_) {
		// Overrides the hit block effect in the arrow.
		Arrow arrow = new Arrow(level, p_40515_) {
			protected void onHitBlock(BlockHitResult res) {
				super.onHitBlock(res);
				// Gets the direction the arrow hit at.
				Direction moving = res.getDirection();
				// Gets the position of the hit.
				BlockPos pos = res.getBlockPos().relative(moving);
				// Get the block state at the position of the arrow.
				BlockState at = this.level().getBlockState(pos);
				// Set the block on fire if it's air
				if (at.isAir())
					this.level().setBlockAndUpdate(pos, BaseFireBlock.getState(level, pos));
				// Sets the block on fire if it's replacable.
				else if (at.canBeReplaced() && this.level().getFluidState(pos).getFluidType().isAir())
					this.level().setBlockAndUpdate(pos, BaseFireBlock.getState(level, pos));
				// Update the neighbors.
				this.level().updateNeighborsAt(res.getBlockPos(), this.level().getBlockState(res.getBlockPos()).getBlock());
			}
		};

		arrow.setEffectsFromItem(p_40514_);
		arrow.setSecondsOnFire(10);
		return arrow;
	}

}

//summon minecraft:blaze -446.32 74.00 -2000.29 {Brain: {memories: {}}, HurtByTimestamp: 882, Attributes: [{Base: 0.0d, Name: "forge:step_height_addition"}, {Base: 0.08d, Name: "forge:entity_gravity"}, {Base: 48.0d, Modifiers: [{Amount: 0.011179259093619803d, Operation: 1, UUID: [I; 537781801, -258650371, -1088753671, -1738751210], Name: "Random spawn bonus"}], Name: "minecraft:generic.follow_range"}, {Base: 0.23000000417232513d, Name: "minecraft:generic.movement_speed"}], Invulnerable: 0b, FallFlying: 0b, PortalCooldown: 0, AbsorptionAmount: 0.0f, FallDistance: 0.0f, CanUpdate: 1b, DeathTime: 0s, HandDropChances: [0.085f, 0.085f], PersistenceRequired: 0b, Motion: [0.0d, -0.0784000015258789d, 0.0d], Health: 11.0f, LeftHanded: 0b, Air: 300s, OnGround: 1b, Rotation: [310.89856f, -21.737316f], HandItems: [{}, {}], ArmorDropChances: [0.085f, 0.085f, 0.085f, 0.085f], Fire: -1s, ArmorItems: [{}, {}, {}, {}], CanPickUpLoot: 0b, HurtTime: 0s, "forge:spawn_type": "COMMAND"}