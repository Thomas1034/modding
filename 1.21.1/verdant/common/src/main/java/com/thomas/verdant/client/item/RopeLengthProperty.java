package com.thomas.verdant.client.item;

import com.mojang.serialization.MapCodec;
import com.thomas.verdant.item.component.RopeCoilData;
import com.thomas.verdant.item.custom.RopeCoilItem;
import com.thomas.verdant.registry.DataComponentRegistry;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.BundleFullness;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

/*

{
  "model": {
    "type": "minecraft:composite",
    "models": [
      {
        "type": "minecraft:range_dispatch",
        "property": "minecraft:bundle/fullness",
        "scale": 32,
        "entries": [
          {
            "threshold": 4,
            "model": {
              "type": "minecraft:model",
              "model": "verdant:item/rope_coil/base/length4"
            }
          },
          {
            "threshold": 8,
            "model": {
              "type": "minecraft:model",
              "model": "verdant:item/rope_coil/base/length8"
            }
          },
          {
            "threshold": 16,
            "model": {
              "type": "minecraft:model",
              "model": "verdant:item/rope_coil/base/length16"
            }
          },
          {
            "threshold": 32,
            "model": {
              "type": "minecraft:model",
              "model": "verdant:item/rope_coil/base/length32"
            }
          }
        ]
      },
      {
        "type": "minecraft:select",
        "property": "minecraft:trim_material",
        "cases": [
          {
            "when": "minecraft:bell",
            "model": {
              "type": "minecraft:model",
              "model": "verdant:item/rope_coil/hanging/bell"
            }
          },
          {
            "when": "minecraft:lantern",
            "model": {
              "type": "minecraft:model",
              "model": "verdant:item/rope_coil/hanging/lantern"
            }
          },
          {
            "when": "minecraft:soul_lantern",
            "model": {
              "type": "minecraft:model",
              "model": "verdant:item/rope_coil/hanging/soul_lantern"
            }
          }
        ]
      },
      {
        "type": "minecraft:condition",
        "property": "minecraft:broken",
        "on_true": {
          "type": "minecraft:model",
          "model": "verdant:item/rope_coil/hook/hook"
        },
        "on_false": {
          "type": "minecraft:empty"
        }
      }
    ]
  }
}




 */
public record RopeLengthProperty() implements RangeSelectItemModelProperty {
    public static final MapCodec<BundleFullness> MAP_CODEC = MapCodec.unit(new BundleFullness());

    @Override
    public float get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity, int i) {
        return Math.min(
                ((float) itemStack.getOrDefault(
                                DataComponentRegistry.ROPE_COIL.get(),
                                RopeCoilItem.DEFAULT_DATA_COMPONENT
                        )
                        .length()) / RopeCoilData.MAX_LENGTH_FROM_CRAFTING, 1.0f
        );
    }

    @Override
    public MapCodec<? extends RangeSelectItemModelProperty> type() {
        return MAP_CODEC;
    }
}
