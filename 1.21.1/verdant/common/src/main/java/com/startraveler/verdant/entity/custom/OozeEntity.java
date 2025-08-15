package com.startraveler.verdant.entity.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SuspiciousEffectHolder;
import org.jetbrains.annotations.NotNull;

public class OozeEntity extends Slime {
    public OozeEntity(EntityType<? extends OozeEntity> type, Level level) {
        super(type, level);
        this.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Blocks.BLUE_ORCHID));
    }

    @Override
    public boolean doHurtTarget(@NotNull ServerLevel level, @NotNull Entity entity) {
        boolean attackResult = super.doHurtTarget(level, entity);

        if (attackResult) {
            ItemStack stack = this.getMainHandItem();

            if (stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof SuspiciousEffectHolder seh) {

                SuspiciousStewEffects effects = seh.getSuspiciousEffects();

                if (entity instanceof LivingEntity livingEntity) {
                    for (SuspiciousStewEffects.Entry effect : effects.effects()) {
                        livingEntity.addEffect(effect.createEffectInstance().withScaledDuration(8), this);
                    }
                }

            }
        }

        return attackResult;
    }
}
