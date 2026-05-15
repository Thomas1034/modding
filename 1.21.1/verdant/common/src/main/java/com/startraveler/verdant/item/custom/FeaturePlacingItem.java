package com.startraveler.verdant.item.custom;

import com.startraveler.rootbound.featureset.FeatureSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.component.UseRemainder;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public class FeaturePlacingItem extends Item {
    protected final Identifier featureSet;

    public FeaturePlacingItem(Properties properties, Identifier featureSet) {
        super(properties);
        this.featureSet = featureSet;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockPos blockPos = pos.relative(context.getClickedFace().getOpposite());
        ItemStack stack = context.getItemInHand();
        InteractionHand hand = context.getHand();
        Player player = context.getPlayer();

        if (stack.getMaxDamage() == 0 || !stack.nextDamageWillBreak() || stack.has(DataComponents.USE_REMAINDER)) {
            if (level instanceof ServerLevel serverLevel) {
                if (this.placeFeature(serverLevel, pos) || this.placeFeature(serverLevel, blockPos)) {
                    if (player != null) {
                        player.gameEvent(GameEvent.ITEM_INTERACT_FINISH);
                        ItemStack result = this.getEmptySuccessItem(stack, player);
                        if (stack.getMaxDamage() > 0) {
                            stack.hurtAndBreak(
                                    1,
                                    player,
                                    hand.asEquipmentSlot()
                            );
                            if (stack.isEmpty()) {
                                player.setItemInHand(hand, result);
                            }
                        } else {
                            if (stack.getMaxStackSize() != 1) {
                                stack.shrink(1);
                                player.addItem(result);
                            } else {
                                player.setItemInHand(hand, result);
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
        return set.place(serverLevel, pos);
    }

    public ItemStack getEmptySuccessItem(ItemStack stack, Player player) {
        return (player != null && !player.hasInfiniteMaterials()) ? (stack.get(
                DataComponents.USE_REMAINDER) instanceof UseRemainder(ItemStackTemplate convertInto)) ? convertInto.create() : ItemStack.EMPTY : stack;
    }
}
