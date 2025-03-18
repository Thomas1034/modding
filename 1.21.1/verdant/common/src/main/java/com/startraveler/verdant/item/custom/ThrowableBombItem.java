package com.startraveler.verdant.item.custom;

import com.startraveler.verdant.entity.custom.BlockIgnoringPrimedTnt;
import com.startraveler.verdant.mixin.PrimedTntAccessors;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class ThrowableBombItem extends Item {
    public static float PROJECTILE_SHOOT_POWER = 0.5F;

    public final Supplier<BlockState> state;

    public ThrowableBombItem(Properties properties, Supplier<BlockState> state) {
        super(properties);
        this.state = state;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        level.playSound(
                null,
                player.getX(),
                player.getY(),
                player.getZ(),
                SoundEvents.FISHING_BOBBER_THROW,
                SoundSource.NEUTRAL,
                0.5F,
                0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F)
        );
        if (level instanceof ServerLevel serverlevel) {
            PrimedTnt bomb = new BlockIgnoringPrimedTnt(level, player.getX(), player.getY(0.5), player.getZ(), player);
            bomb.setBlockState(this.state.get());
            ((PrimedTntAccessors) bomb).setExplosionPower(2.0f);
            bomb.setDeltaMovement(player.getLookAngle()
                    .scale(PROJECTILE_SHOOT_POWER)
                    .add(0, PROJECTILE_SHOOT_POWER / 5, 0));
            bomb.setFuse(40);
            bomb.setBoundingBox(bomb.getBoundingBox().deflate(0.25, 0.25, 0.25));
            bomb.refreshDimensions();
            serverlevel.addFreshEntity(bomb);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        itemstack.consume(1, player);
        return InteractionResult.SUCCESS;
    }
}
