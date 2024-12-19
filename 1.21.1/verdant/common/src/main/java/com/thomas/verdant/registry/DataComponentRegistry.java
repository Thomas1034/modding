package com.thomas.verdant.registry;

import com.thomas.verdant.Constants;
import com.thomas.verdant.item.component.RopeCoilData;
import com.thomas.verdant.registration.RegistrationProvider;
import com.thomas.verdant.registration.RegistryObject;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;

public class DataComponentRegistry {

    public static final RegistrationProvider<DataComponentType<?>> COMPONENTS = RegistrationProvider.get(
            Registries.DATA_COMPONENT_TYPE,
            Constants.MOD_ID
    );

    public static final RegistryObject<DataComponentType<?>, DataComponentType<RopeCoilData>> ROPE_COIL = COMPONENTS.register(
            "rope_coil",
            () -> DataComponentType.<RopeCoilData>builder().persistent(RopeCoilData.CODEC).build()
    );

    public static void init() {
    }
}
