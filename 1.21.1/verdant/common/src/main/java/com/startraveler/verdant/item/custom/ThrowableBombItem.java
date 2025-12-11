package com.startraveler.verdant.item.custom;

import com.startraveler.verdant.entity.custom.BlockIgnoringPrimedTnt;
import com.startraveler.verdant.mixin.PrimedTntAccessors;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class ThrowableBombItem extends Item {
    public static final int DEFAULT_PROJECTILE_FUSE = 80;
    public static final float DEFAULT_BLAST_DAMAGE_MULTIPLIER = 1.0f;
    public static float DEFAULT_PROJECTILE_SHOOT_POWER = 0.5F;
    public static float DEFAULT_PROJECTILE_BLAST_POWER = 2.0F;

    public final Supplier<BlockState> state;
    protected final float shootPower;
    protected final int fuse;
    protected final float blastDamageMultiplier;
    protected final float blastPower;

    public ThrowableBombItem(Properties properties, Supplier<BlockState> state) {
        this(
                properties,
                state,
                DEFAULT_PROJECTILE_SHOOT_POWER,
                DEFAULT_PROJECTILE_FUSE,
                DEFAULT_BLAST_DAMAGE_MULTIPLIER,
                DEFAULT_PROJECTILE_BLAST_POWER
        );
    }

    public ThrowableBombItem(Properties properties, Supplier<BlockState> state, float shootPower, int fuse, float blastDamageMultiplier, float blastPower) {
        super(properties);
        this.state = state;
        this.shootPower = shootPower;
        this.fuse = fuse;
        this.blastDamageMultiplier = blastDamageMultiplier;
        this.blastPower = blastPower;
    }

    @Override
    public @NotNull InteractionResult use(Level level, Player player, @NotNull InteractionHand hand) {
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
        if (level instanceof ServerLevel) {
            this.createAndLaunchBomb(
                    level,
                    player.getX(),
                    player.getY(0.5),
                    player.getZ(),
                    player,
                    player.getLookAngle()
            );
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        itemstack.consume(1, player);
        return InteractionResult.SUCCESS;
    }

    public void createAndLaunchBomb(Level level, double x, double y, double z, @Nullable LivingEntity owner, Vec3 shootAngle) {
        BlockIgnoringPrimedTnt bomb = new BlockIgnoringPrimedTnt(level, x, y, z, owner);
        bomb.setBlockState(this.state.get());
        ((PrimedTntAccessors) bomb).setExplosionPower(this.blastPower);
        bomb.setDamageMultiplier(this.blastDamageMultiplier);
        bomb.setDeltaMovement(shootAngle.scale(this.shootPower).add(0, this.shootPower / 5, 0));
        bomb.setFuse(this.fuse);

        bomb.setBoundingBox(bomb.getBoundingBox().deflate(0.25, 0.25, 0.25));
        bomb.refreshDimensions();

        level.addFreshEntity(bomb);
    }
}
