package com.startraveler.verdant.fluid;

import com.startraveler.verdant.Constants;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Function;

public class VerdantFluidTypes {
    public static final ResourceLocation WATER_STILL_RL = ResourceLocation.withDefaultNamespace("block/water_still");
    public static final ResourceLocation WATER_FLOWING_RL = ResourceLocation.withDefaultNamespace("block/water_flow");
    public static final ResourceLocation SAP_OVERLAY_RL = ResourceLocation.fromNamespaceAndPath(
            Constants.MOD_ID,
            "misc/in_sap"
    );

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, Constants.MOD_ID);
    public static final int SAP_TINT = 0xFF33CC55;
    public static final DeferredHolder<FluidType, FluidType> SAP_FLUID_TYPE = register(
            "sap_fluid", (properties) -> new FluidType(properties) {
                public double motionScale(Entity entity) {
                    return 0.0023333333333333335;
                }

                public boolean move(FluidState state, LivingEntity entity, Vec3 movementVector, double gravity) {
                    return true;
                }

                public void setItemMovement(ItemEntity entity) {
                    Vec3 vec3 = entity.getDeltaMovement();
                    entity.setDeltaMovement(
                            vec3.x * (double) 0.95F,
                            vec3.y + (double) (vec3.y < (double) 0.06F ? 5.0E-4F : 0.0F),
                            vec3.z * (double) 0.95F
                    );
                }

                public boolean canConvertToSource(FluidState state, LevelReader reader, BlockPos pos) {
                    return false;
                }
            },
            FluidType.Properties.create()
                    .canSwim(false)
                    .canDrown(true)
                    .pathType(PathType.DANGER_OTHER)
                    .adjacentPathType(null)
                    .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
                    .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
                    .lightLevel(3)
                    .density(2000)
                    .viscosity(6000)
    );

    private static DeferredHolder<FluidType, FluidType> register(String name, Function<FluidType.Properties, FluidType> getter, FluidType.Properties properties) {
        return FLUID_TYPES.register(
                name, () -> getter.apply(properties)
        );
    }

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
    }
}
