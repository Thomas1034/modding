package com.startraveler.verdant.item.custom;

import com.startraveler.rootbound.featureset.FeatureSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class FeaturePlacingItem extends Item {
    protected final ResourceLocation featureSet;

    public FeaturePlacingItem(Properties properties, ResourceLocation featureSet) {
        super(properties);
        this.featureSet = featureSet;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockPos blockPos = pos.relative(context.getClickedFace().getOpposite());
        ItemStack stack = context.getItemInHand();
        Player player = context.getPlayer();

        if (stack.getMaxDamage() == 0 || !stack.nextDamageWillBreak() || stack.has(DataComponents.USE_REMAINDER)) {
            if (level instanceof ServerLevel serverLevel) {
                if (this.placeFeature(serverLevel, pos) || this.placeFeature(serverLevel, blockPos)) {
                    if (player != null) {
                        player.gameEvent(GameEvent.ITEM_INTERACT_FINISH);
                        ItemStack result = this.getEmptySuccessItem(stack, player);
                        if (stack.getMaxDamage() > 0) {
                            stack.hurtAndConvertOnBreak(
                                    1,
                                    result.getItem(),
                                    player,
                                    LivingEntity.getSlotForHand(context.getHand())
                            );
                        } else {
                            if (stack.getMaxStackSize() != 1) {
                                stack.shrink(1);
                                player.addItem(result);
                            } else {
                                player.setItemInHand(context.getHand(), result);
                            }
                        }
                        if (result.isEmpty() || stack.getDamageValue() < stack.getMaxDamage()) {
                            return InteractionResult.SUCCESS_SERVER;
                        } else {
                            return InteractionResult.SUCCESS_SERVER.heldItemTransformedTo(result);
                        }
                    } else {
                        return InteractionResult.SUCCESS_SERVER;
                    }
                } else {
                    return InteractionResult.PASS;
                }
            } else {
                return InteractionResult.SUCCESS;
            }
        } else {
            return InteractionResult.PASS;
        }
    }

    private boolean placeFeature(ServerLevel serverLevel, BlockPos pos) {

        Registry<FeatureSet> features = serverLevel.registryAccess().lookupOrThrow(FeatureSet.KEY);
        FeatureSet set = features.get(this.featureSet).orElseThrow().value();
        boolean placed = set.place(serverLevel, pos);
        if (placed) {
            // Do nothing.
        }
        return placed;
    }

    public ItemStack getEmptySuccessItem(ItemStack stack, Player player) {
        return (player != null && !player.hasInfiniteMaterials()) ? stack.has(DataComponents.USE_REMAINDER) ? stack.get(
                DataComponents.USE_REMAINDER).convertInto() : ItemStack.EMPTY : stack;
    }
}
