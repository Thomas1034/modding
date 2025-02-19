package com.startraveler.verdant.mixin;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(AbstractArrow.class)
public interface AbstractArrowAccessors {
    @Invoker("setPierceLevel")
    void verdant$setPierceLevel(byte level);

    @Accessor("pickupItemStack")
    void verdant$setPickupItemStack(ItemStack stack);

    @Accessor("piercingIgnoreEntityIds")
    IntOpenHashSet verdant$getPiercingIgnoreEntityIds();

    @Accessor("piercingIgnoreEntityIds")
    void verdant$setPiercingIgnoreEntityIds(IntOpenHashSet ids);

    @Accessor("piercedAndKilledEntities")
    List<Entity> verdant$getPiercedAndKilledEntities();

    @Accessor("piercedAndKilledEntities")
    void verdant$setPiercedAndKilledEntities(List<Entity> entities);

    @Accessor("firedFromWeapon")
    ItemStack verdant$getFiredFromWeapon();

    @Accessor("firedFromWeapon")
    void verdant$setFiredFromWeapon(ItemStack stack);
}
