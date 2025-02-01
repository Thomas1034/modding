package com.startraveler.verdant.platform;

import com.startraveler.verdant.platform.services.*;

import java.util.ServiceLoader;

// Service loaders are a built-in Java feature that allow us to locate implementations of an interface that vary from one
// environment to another. In the context of MultiLoader we use this feature to access a mock API in the common code that
// is swapped out for the platform specific implementation at runtime.
public class Services {

    // In this example we provide a platform helper which provides information about what platform the mod is running on.
    // For example this can be used to check if the code is running on Forge vs Fabric, or to ask the modloader if another
    // mod is loaded.
    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);

    // This provides a menu type for the fish trap menu, with per-platform extended functionality to sync extra data.
    public static final IFishTrapMenuCreator FISH_TRAP_MENU_CREATOR = load(IFishTrapMenuCreator.class);

    // This actually opens the fish trap menu, since that varies per-platform.
    public static final IFishTrapMenuOpener FISH_TRAP_MENU_OPENER = load(IFishTrapMenuOpener.class);

    // Handles the growth-event wrapped code for the cassava crop.
    public static final ICropEventHelper CROP_EVENT_HELPER = load(ICropEventHelper.class);

    // Handles getting the growth speed of crops. Blame NeoForge changing the signature
    // unnecessarily.
    public static final ICropGrowthSpeedChecker CROP_GROWTH_SPEED = load(ICropGrowthSpeedChecker.class);

    // This code is used to load a service for the current environment. Your implementation of the service must be defined
    // manually by including a text file in META-INF/services named with the fully qualified class name of the service.
    // Inside the file you should write the fully qualified class name of the implementation to load for the platform. For
    // example our file on Forge points to ForgePlatformHelper while Fabric points to FabricPlatformHelper.
    public static <T> T load(Class<T> clazz) {

        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        // Constants.LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}