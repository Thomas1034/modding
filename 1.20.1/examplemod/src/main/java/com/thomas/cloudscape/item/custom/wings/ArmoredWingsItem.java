package com.thomas.cloudscape.item.custom.wings;

import com.thomas.cloudscape.util.WingsItem;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.phys.Vec3;

public class ArmoredWingsItem extends ArmorItem implements WingsItem {

	public ArmoredWingsItem(ArmorMaterial material, Type type, Properties properties) {
		super(material, type, properties);
	}

	public static boolean isFlyEnabled(ItemStack p_41141_) {
		return p_41141_.getDamageValue() < p_41141_.getMaxDamage() - 1;
	}

	@Override
	public boolean canElytraFly(ItemStack stack, net.minecraft.world.entity.LivingEntity entity) {
		return ArmoredWingsItem.isFlyEnabled(stack);
	}

	// From ElytraItem
	@SuppressWarnings("resource")
	@Override
	public boolean elytraFlightTick(ItemStack stack, net.minecraft.world.entity.LivingEntity entity, int flightTicks) {
		if (!entity.level().isClientSide) {
			int nextFlightTick = flightTicks + 1;
			if (nextFlightTick % 10 == 0) {
				if (nextFlightTick % 20 == 0) {
					// Roll unbreaking.
					if (0 == entity.getRandom().nextIntBetweenInclusive(0,
							stack.getEnchantmentLevel(Enchantments.UNBREAKING))) {
						stack.hurtAndBreak(1, entity,
								e -> e.broadcastBreakEvent(net.minecraft.world.entity.EquipmentSlot.CHEST));
					}
				}
				entity.gameEvent(net.minecraft.world.level.gameevent.GameEvent.ELYTRA_GLIDE);
			}

		} else {
			// Also decrease lift.
			int defense = this.material.getDefenseForType(this.type);
			Vec3 weight = new Vec3(0, -defense / 200.0, 0);
			entity.addDeltaMovement(weight);
		}
		return true;
	}

}
