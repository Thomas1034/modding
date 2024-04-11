package com.thomas.cloudscape.item.custom.wings;

import com.thomas.cloudscape.util.WingsItem;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class AncientFeatherWingsItem extends FeatherWingsItem {

	public AncientFeatherWingsItem(Properties properties) {
		super(properties);
	}
	
	@Override
	public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks) {
		super.elytraFlightTick(stack, entity, flightTicks);
	
		// Check if entity is too high, if so damage the wings.
		// If is server side
		if (!entity.level().isClientSide()) {
			// If is too high
			if (entity.position().y > 256) {
				WingsItem.decreaseDurabilityPublic(stack, entity, 1);
			}
		}
		
		return true;
	}
	
	@Override
	public String getTextureName() {
		return "ancient_feather";
	}
	
	@Override
	public Item breaksInto() {
		return Items.AIR;
	}

}
