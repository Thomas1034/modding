package com.thomas.verdant.registry;

import com.thomas.verdant.Constants;
import com.thomas.verdant.util.VerdantTags;
import net.minecraft.Util;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

import java.util.EnumMap;

public interface ArmorMaterialRegistry {

    ResourceKey<EquipmentAsset> HEARTWOOD_ASSET = ResourceKey.create(
            EquipmentAssets.ROOT_ID,
            ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "heartwood")
    );
    ResourceKey<EquipmentAsset> IMBUED_HEARTWOOD_ASSET = ResourceKey.create(
            EquipmentAssets.ROOT_ID,
            ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "imbued_heartwood")
    );

    ArmorMaterial HEARTWOOD = new ArmorMaterial(
            7, Util.make(
            new EnumMap<>(ArmorType.class), (builder) -> {
                builder.put(ArmorType.BOOTS, 1);
                builder.put(ArmorType.LEGGINGS, 4);
                builder.put(ArmorType.CHESTPLATE, 5);
                builder.put(ArmorType.HELMET, 2);
                builder.put(ArmorType.BODY, 5);
            }
    ), 8, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, VerdantTags.Items.REPAIRS_HEARTWOOD_ARMOR, HEARTWOOD_ASSET
    );

    ArmorMaterial IMBUED_HEARTWOOD = new ArmorMaterial(
            7,
            Util.make(
                    new EnumMap<>(ArmorType.class), (builder) -> {
                        builder.put(ArmorType.BOOTS, 3);
                        builder.put(ArmorType.LEGGINGS, 5);
                        builder.put(ArmorType.CHESTPLATE, 7);
                        builder.put(ArmorType.HELMET, 3);
                        builder.put(ArmorType.BODY, 7);
                    }
            ),
            8,
            SoundEvents.ARMOR_EQUIP_LEATHER,
            0.0F,
            0.1F,
            VerdantTags.Items.REPAIRS_IMBUED_HEARTWOOD_ARMOR,
            IMBUED_HEARTWOOD_ASSET
    );
}
