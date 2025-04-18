package com.startraveler.verdant.item.custom;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;

import java.util.List;
import java.util.Objects;

public class TippedDartItem extends DartItem {
    public TippedDartItem(Properties properties) {
        super(properties);
    }

    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> components, TooltipFlag flag) {
        PotionContents potionContents = stack.get(DataComponents.POTION_CONTENTS);
        if (potionContents != null) {
            Objects.requireNonNull(components);
            potionContents.addPotionTooltip(components::add, 1.0F, context.tickRate());
        }

    }

    public Component getName(ItemStack stack) {
        PotionContents potioncontents = stack.get(DataComponents.POTION_CONTENTS);
        return potioncontents != null ? potioncontents.getName(this.descriptionId + ".effect.") : super.getName(
                stack);
    }

    public ItemStack getDefaultInstance() {
        ItemStack itemstack = super.getDefaultInstance();
        itemstack.set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.POISON));
        return itemstack;
    }

}
