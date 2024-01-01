package com.thomas.zirconmod.item.custom;

import java.util.List;
import java.util.function.Predicate;

import com.thomas.zirconmod.entity.custom.ModBoatEntity;
import com.thomas.zirconmod.entity.custom.ModChestBoatEntity;

import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

// Essentially copied from the Kaupenjoe course.
// Which was copied from the BoatItem class.
public class ModBoatItem extends Item {
	private static final Predicate<Entity> ENTITY_PREDICATE = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);
	private final ModBoatEntity.Type type;
	private final boolean hasChest;

	public ModBoatItem(boolean hasChest, ModBoatEntity.Type type, Item.Properties properties) {
		super(properties);
		this.hasChest = hasChest;
		this.type = type;
	}

	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand useHand) {
		ItemStack itemstack = player.getItemInHand(useHand);
		HitResult hitresult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.ANY);
		if (hitresult.getType() == HitResult.Type.MISS) {
			return InteractionResultHolder.pass(itemstack);
		} else {
			Vec3 vec3 = player.getViewVector(1.0F);
			List<Entity> list = level.getEntities(player,
					player.getBoundingBox().expandTowards(vec3.scale(5.0D)).inflate(1.0D), ENTITY_PREDICATE);
			if (!list.isEmpty()) {
				Vec3 vec31 = player.getEyePosition();

				for (Entity entity : list) {
					AABB aabb = entity.getBoundingBox().inflate((double) entity.getPickRadius());
					if (aabb.contains(vec31)) {
						return InteractionResultHolder.pass(itemstack);
					}
				}
			}
			if (hitresult.getType() == HitResult.Type.BLOCK) {
				Boat boat = this.getBoat(level, hitresult);
				if (boat instanceof ModChestBoatEntity chestBoat) {
					chestBoat.setVariant(this.type);
				} else if (boat instanceof ModBoatEntity) {
					((ModBoatEntity) boat).setVariant(this.type);
				}
				boat.setYRot(player.getYRot());
				if (!level.noCollision(boat, boat.getBoundingBox())) {
					return InteractionResultHolder.fail(itemstack);
				} else {
					if (!level.isClientSide) {
						level.addFreshEntity(boat);
						level.gameEvent(player, GameEvent.ENTITY_PLACE, hitresult.getLocation());
						if (!player.getAbilities().instabuild) {
							itemstack.shrink(1);
						}
					}

					player.awardStat(Stats.ITEM_USED.get(this));
					return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
				}
			} else {
				return InteractionResultHolder.pass(itemstack);
			}
		}
	}

	private Boat getBoat(Level level, HitResult result) {
		return (Boat) (this.hasChest
				? new ModChestBoatEntity(level, result.getLocation().x, result.getLocation().y, result.getLocation().z)
				: new ModBoatEntity(level, result.getLocation().x, result.getLocation().y, result.getLocation().z));
	}
}
