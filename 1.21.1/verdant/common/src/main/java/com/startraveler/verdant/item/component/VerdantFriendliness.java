package com.startraveler.verdant.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

// Data Component for storing how much a worn/held item contributes to verdant friendliness.
public record VerdantFriendliness(float sway) {
    public static final VerdantFriendliness HEARTWOOD_ARMOR = new VerdantFriendliness(0.1f);
    public static final VerdantFriendliness HEARTWOOD_HORSE_ARMOR = new VerdantFriendliness(0.7f);
    public static final VerdantFriendliness IMBUED_HEARTWOOD_ARMOR = new VerdantFriendliness(0.3f);
    public static final VerdantFriendliness IMBUED_HEARTWOOD_HORSE_ARMOR = new VerdantFriendliness(1.0f);


    public static final Codec<VerdantFriendliness> CODEC = RecordCodecBuilder.create(instance -> instance.group(Codec.FLOAT.fieldOf(
            "sway").forGetter(VerdantFriendliness::sway)).apply(instance, VerdantFriendliness::new));
}
