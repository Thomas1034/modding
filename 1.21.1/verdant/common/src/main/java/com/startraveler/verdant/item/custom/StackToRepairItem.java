package com.startraveler.verdant.item.custom;

import net.minecraft.world.item.Item;

public class StackToRepairItem extends Item {
    public StackToRepairItem(Properties properties) {
        super(properties);
    }
//
//
//    public boolean overrideStackedOnOther(ItemStack p_150733_, Slot p_150734_, ClickAction p_150735_, Player p_150736_) {
//        BundleContents bundlecontents = p_150733_.get(DataComponents.BUNDLE_CONTENTS);
//        if (bundlecontents == null) {
//            return false;
//        } else {
//            ItemStack itemstack = p_150734_.getItem();
//            BundleContents.Mutable bundlecontents$mutable = new BundleContents.Mutable(bundlecontents);
//            if (p_150735_ == ClickAction.PRIMARY && !itemstack.isEmpty()) {
//                if (bundlecontents$mutable.tryTransfer(p_150734_, p_150736_) > 0) {
//                    playInsertSound(p_150736_);
//                } else {
//                    playInsertFailSound(p_150736_);
//                }
//
//                p_150733_.set(DataComponents.BUNDLE_CONTENTS, bundlecontents$mutable.toImmutable());
//                this.broadcastChangesOnContainerMenu(p_150736_);
//                return true;
//            } else if (p_150735_ == ClickAction.SECONDARY && itemstack.isEmpty()) {
//                ItemStack itemstack1 = bundlecontents$mutable.removeOne();
//                if (itemstack1 != null) {
//                    ItemStack itemstack2 = p_150734_.safeInsert(itemstack1);
//                    if (itemstack2.getCount() > 0) {
//                        bundlecontents$mutable.tryInsert(itemstack2);
//                    } else {
//                        playRemoveOneSound(p_150736_);
//                    }
//                }
//
//                p_150733_.set(DataComponents.BUNDLE_CONTENTS, bundlecontents$mutable.toImmutable());
//                this.broadcastChangesOnContainerMenu(p_150736_);
//                return true;
//            } else {
//                return false;
//            }
//        }
//    }
//
//    public boolean overrideOtherStackedOnMe(ItemStack p_150742_, ItemStack p_150743_, Slot p_150744_, ClickAction p_150745_, Player p_150746_, SlotAccess p_150747_) {
//        if (p_150745_ == ClickAction.PRIMARY && p_150743_.isEmpty()) {
//            toggleSelectedItem(p_150742_, -1);
//            return false;
//        } else {
//            BundleContents bundlecontents = (BundleContents)p_150742_.get(DataComponents.BUNDLE_CONTENTS);
//            if (bundlecontents == null) {
//                return false;
//            } else {
//                BundleContents.Mutable bundlecontents$mutable = new BundleContents.Mutable(bundlecontents);
//                if (p_150745_ == ClickAction.PRIMARY && !p_150743_.isEmpty()) {
//                    if (p_150744_.allowModification(p_150746_) && bundlecontents$mutable.tryInsert(p_150743_) > 0) {
//                        playInsertSound(p_150746_);
//                    } else {
//                        playInsertFailSound(p_150746_);
//                    }
//
//                    p_150742_.set(DataComponents.BUNDLE_CONTENTS, bundlecontents$mutable.toImmutable());
//                    this.broadcastChangesOnContainerMenu(p_150746_);
//                    return true;
//                } else if (p_150745_ == ClickAction.SECONDARY && p_150743_.isEmpty()) {
//                    if (p_150744_.allowModification(p_150746_)) {
//                        ItemStack itemstack = bundlecontents$mutable.removeOne();
//                        if (itemstack != null) {
//                            playRemoveOneSound(p_150746_);
//                            p_150747_.set(itemstack);
//                        }
//                    }
//
//                    p_150742_.set(DataComponents.BUNDLE_CONTENTS, bundlecontents$mutable.toImmutable());
//                    this.broadcastChangesOnContainerMenu(p_150746_);
//                    return true;
//                } else {
//                    toggleSelectedItem(p_150742_, -1);
//                    return false;
//                }
//            }
//        }
//    }
}
