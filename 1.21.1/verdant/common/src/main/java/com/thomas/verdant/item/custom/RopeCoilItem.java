package com.thomas.verdant.item.custom;

import com.thomas.verdant.entity.custom.ThrownRopeEntity;
import com.thomas.verdant.item.component.ThrownRopeComponent;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;

public class RopeCoilItem extends Item implements ProjectileItem {

    public static final ThrownRopeComponent DEFAULT_DATA_COMPONENT = new ThrownRopeComponent(8, false);

    public RopeCoilItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        level.playSound(
                null,
                player.getX(),
                player.getY(),
                player.getZ(),
                SoundEvents.ITEM_FRAME_ADD_ITEM,
                SoundSource.NEUTRAL,
                0.5F,
                0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F)
        );
        if (level instanceof ServerLevel serverlevel) {
            Projectile.spawnProjectileFromRotation(
                    ThrownRopeEntity::new,
                    serverlevel,
                    itemstack,
                    player,
                    0.0F,
                    1.5F,
                    0.1F
            );
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        itemstack.consume(1, player);
        return InteractionResult.SUCCESS;
    }

    @Override
    public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
        return new ThrownRopeEntity(level, pos.x(), pos.y(), pos.z(), stack);
    }
}
