package com.startraveler.verdant.item.custom;

import com.startraveler.verdant.entity.custom.PoisonArrowEntity;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PoisonArrowItem extends ArrowItem {
    public PoisonArrowItem(Properties properties) {
        super(properties);
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack ammo, LivingEntity shooter, ItemStack weapon) {
        return new PoisonArrowEntity(level, shooter, ammo.copyWithCount(1), weapon);
    }

    @Override
    public Projectile asProjectile(Level level, Position position, ItemStack stack, Direction direction) {
        AbstractArrow arrow = new PoisonArrowEntity(
                level,
                position.x(),
                position.y(),
                position.z(),
                stack.copyWithCount(1),
                null
        );
        arrow.pickup = AbstractArrow.Pickup.ALLOWED;
        return arrow;
    }
}
