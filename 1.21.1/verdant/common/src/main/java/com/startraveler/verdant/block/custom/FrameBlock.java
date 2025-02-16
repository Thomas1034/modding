/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * If you modify this file, please include a notice stating the changes:
 * Example: "Modified by [Your Name] on [Date] - [Short Description of Changes]"
 */
package com.startraveler.verdant.block.custom;


import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FrameBlock extends SimpleFrameBlock implements SimpleWaterloggedBlock {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final double ENTITY_AREA_CUTOFF = 0.25 * 0.75 * 0.75;
    private static final double LENIENT_ENTITY_AREA_CUTOFF = 1.5 * 1.5 * ENTITY_AREA_CUTOFF;
    private static final double SHORT_ENTITY_HEIGHT_CUTOFF = Math.sqrt(2);

    public FrameBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(WATERLOGGED, false));
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        boolean isSmallEntity = context instanceof EntityCollisionContext entityContext && entityContext.getEntity() instanceof Entity entity && entity.getBoundingBox() instanceof AABB box && ((box.getXsize() * box.getZsize() < ((entity instanceof LivingEntity livingEntity && livingEntity.isBaby()) || (entity instanceof Projectile) ? LENIENT_ENTITY_AREA_CUTOFF : ENTITY_AREA_CUTOFF)) || (box.getYsize() < SHORT_ENTITY_HEIGHT_CUTOFF));
        return this.hasCollision && !isSmallEntity ? state.getShape(level, pos) : Shapes.empty();
    }

}
