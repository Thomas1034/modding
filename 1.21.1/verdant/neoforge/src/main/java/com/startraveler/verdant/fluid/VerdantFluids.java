package com.startraveler.verdant.fluid;

import com.startraveler.verdant.Constants;
import com.startraveler.verdant.Verdant;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class VerdantFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(Registries.FLUID, Constants.MOD_ID);

    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }

    public static final DeferredHolder<Fluid, FlowingFluid> SOURCE_SAP = FLUIDS.register(
            "sap_fluid",
            () -> new BaseFlowingFluid.Source(VerdantFluids.SAP_FLUID_PROPERTIES)
    );
    public static final DeferredHolder<Fluid, FlowingFluid> FLOWING_SAP = FLUIDS.register(
            "flowing_sap",
            () -> new BaseFlowingFluid.Flowing(VerdantFluids.SAP_FLUID_PROPERTIES)
    );

    public static final BaseFlowingFluid.Properties SAP_FLUID_PROPERTIES = new BaseFlowingFluid.Properties(
            VerdantFluidTypes.SAP_FLUID_TYPE, SOURCE_SAP, FLOWING_SAP)
            .slopeFindDistance(2).levelDecreasePerBlock(3).block(Verdant.InnerRegistration.SAP)
            .bucket(Verdant.InnerRegistration.SAP_BUCKET);


}
