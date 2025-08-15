package com.startraveler.verdant.block.custom.entity;

import com.startraveler.verdant.VerdantIFF;
import com.startraveler.verdant.block.custom.OozeFissureBlock;
import com.startraveler.verdant.registry.BlockEntityTypeRegistry;
import com.startraveler.verdant.util.VerdantTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class OozeFissureBlockEntity extends BlockEntity {

    private static final double ACTIVATION_DISTANCE = 32;
    private static final int MAX_NEARBY_MONSTERS = 16;

    public OozeFissureBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityTypeRegistry.OOZE_FISSURE_BLOCK_ENTITY.get(), pos, blockState);
    }


    public static void clientTick(Level level, BlockPos pos, BlockState state, OozeFissureBlockEntity blockEntity) {

    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, OozeFissureBlockEntity blockEntity) {
        boolean willPerformComplexTickLogic = level.random.nextFloat() < ((1.0 / 20.0) * (1.0 / 10.0));
        if (!willPerformComplexTickLogic) {
            return;
        }

        int countOfBlocksAround = blockEntity.getCountOfOozingBlocksAroundPos(level, pos, state);

        if (countOfBlocksAround < 1) {
            return;
        }

        int playerCount = 0;
        List<? extends Player> players = level.players();
        int numberOfPlayers = players.size();
        Vec3 center = pos.getCenter();
        for (int i = 0; i < numberOfPlayers; i++) {
            Player player = players.get(i);
            if (player.distanceToSqr(center) < ACTIVATION_DISTANCE && VerdantIFF.isEnemy(player)) {
                playerCount++;
            }
        }

        if (playerCount < 1) {
            return;
        }

        int monstersAround = level.getEntitiesOfClass(
                Monster.class,
                AABB.ofSize(pos.getCenter(), ACTIVATION_DISTANCE, ACTIVATION_DISTANCE, ACTIVATION_DISTANCE)
        ).size();

        if (monstersAround < MAX_NEARBY_MONSTERS) {
            Slime slime = new Slime(EntityType.SLIME, level);
            slime.setSize(4, true);
            slime.setPos(pos.getCenter().add(state.getValue(OozeFissureBlock.FACING).getUnitVec3().scale(0.5)));
            level.addFreshEntity(slime);
        }
    }

    protected int getCountOfOozingBlocksAroundPos(Level level, BlockPos pos, BlockState state) {
        if (!state.getValue(OozeFissureBlock.ACTIVE)) {
            return 0;
        }
        Direction facing = state.getValue(OozeFissureBlock.FACING);
        Direction[] perps = getPerpendicularDirections(facing);
        Direction ortho = perps[1];
        Direction cross = perps[0];
        int count = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    BlockPos relativePos = pos.relative(facing, -k).relative(ortho, i).relative(cross, j);
                    BlockState relativeState = level.getBlockState(relativePos);
                    if (relativeState.is(VerdantTags.Blocks.SUSTAINS_OOZE_FISSURE)) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    public Direction[] getPerpendicularDirections(Direction dir) {
        return switch (dir) {
            case Direction.EAST -> new Direction[]{Direction.UP, Direction.SOUTH};
            case Direction.WEST -> new Direction[]{Direction.SOUTH, Direction.UP};
            case Direction.UP -> new Direction[]{Direction.SOUTH, Direction.EAST};
            case Direction.DOWN -> new Direction[]{Direction.EAST, Direction.SOUTH};
            case Direction.SOUTH -> new Direction[]{Direction.EAST, Direction.UP};
            case Direction.NORTH -> new Direction[]{Direction.UP, Direction.EAST};
        };
    }
}
