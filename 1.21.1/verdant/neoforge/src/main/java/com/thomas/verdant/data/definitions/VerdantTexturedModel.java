package com.thomas.verdant.data.definitions;

import com.thomas.verdant.Constants;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import org.apache.commons.lang3.function.TriFunction;

import java.util.function.BiFunction;

public class VerdantTexturedModel {


    public static final TexturedModel.Provider FISH_TRAP = TexturedModel.createDefault(
            VerdantTextureMapping::fishTrap,
            VerdantModelTemplates.FISH_TRAP
    );

    public static final BiFunction<String, Block, TexturedModel.Provider> OVERLAID_CUBE = (overlay, base) -> TexturedModel.createDefault(
            (block) -> VerdantTextureMapping.overlaidCubeBlock(
                    block,
                    base,
                    ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, overlay).withPrefix("block/")
            ),
            VerdantModelTemplates.OVERLAID_CUBE
    );

    public static final TriFunction<String, String, Block, TexturedModel.Provider> TOP_OVERLAID_CUBE = (overlay, topOverlay, base) -> TexturedModel.createDefault(
            (block) -> VerdantTextureMapping.topOverlaidCubeBlock(
                    block,
                    base,
                    ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, overlay).withPrefix("block/"),
                    ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, topOverlay).withPrefix("block/")
            ),
            VerdantModelTemplates.TOP_OVERLAID_CUBE
    );

}
