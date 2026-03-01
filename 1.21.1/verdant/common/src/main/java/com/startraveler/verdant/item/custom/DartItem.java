package com.startraveler.verdant.item.custom;

import com.startraveler.verdant.entity.custom.DartEntity;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class DartItem extends ArrowItem {
    public DartItem(Properties properties) {
        super(properties);
    }



    @Override
    public @NotNull AbstractArrow createArrow(@NotNull Level level, ItemStack ammo, @NotNull LivingEntity shooter, ItemStack weapon) {
        DartEntity dart = new DartEntity(level, shooter, ammo.copyWithCount(1), weapon);
        dart.setBaseDamage(1.0f);
        return dart;
    }

    @Override
    public @NotNull Projectile asProjectile(@NotNull Level level, Position position, ItemStack stack, @NotNull Direction direction) {
        DartEntity dart = new DartEntity(level, position.x(), position.y(), position.z(), stack.copyWithCount(1), null);
        dart.pickup = AbstractArrow.Pickup.ALLOWED;
        return dart;
    }
}
