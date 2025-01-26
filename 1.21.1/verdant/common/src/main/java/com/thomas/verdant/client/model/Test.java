package com.thomas.verdant.client.model;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.color.item.ItemTintSources;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ResolvableModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Test implements ItemModel {
    private final BakedModel model;
    private final List<ItemTintSource> tints;

    Test(BakedModel model, List<ItemTintSource> tints) {
        this.model = model;
        this.tints = tints;
    }

    private static boolean hasSpecialAnimatedTexture(ItemStack stack) {
        return stack.is(ItemTags.COMPASSES) || stack.is(Items.CLOCK);
    }

    public void update(ItemStackRenderState renderState, ItemStack stack, ItemModelResolver itemModelResolver, ItemDisplayContext displayContext, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {

        ItemStackRenderState.LayerRenderState layerRenderState = renderState.newLayer();
        if (stack.hasFoil()) {
            layerRenderState.setFoilType(hasSpecialAnimatedTexture(stack) ? ItemStackRenderState.FoilType.SPECIAL : ItemStackRenderState.FoilType.STANDARD);
        }

        int numberOfTints = this.tints.size();
        int[] tints = layerRenderState.prepareTintLayers(numberOfTints);

        for (int j = 0; j < numberOfTints; ++j) {
            tints[j] = this.tints.get(j).calculate(stack, level, entity);
        }
        
        RenderType renderType = ItemBlockRenderTypes.getRenderType(stack);
        layerRenderState.setupBlockModel(this.model, renderType);
    }

    public record Unbaked(ResourceLocation model, List<ItemTintSource> tints) implements ItemModel.Unbaked {
        public static final MapCodec<Test.Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                ResourceLocation.CODEC.fieldOf("model").forGetter(Test.Unbaked::model),
                ItemTintSources.CODEC.listOf().optionalFieldOf("tints", List.of()).forGetter(Test.Unbaked::tints)
        ).apply(instance, Test.Unbaked::new));

        public Unbaked(ResourceLocation model, List<ItemTintSource> tints) {
            this.model = model;
            this.tints = tints;
        }

        public void resolveDependencies(ResolvableModel.Resolver resolver) {
            resolver.resolve(this.model);
        }

        public MapCodec<Test.Unbaked> type() {
            return MAP_CODEC;
        }

        public ItemModel bake(ItemModel.BakingContext context) {
            BakedModel bakedModel = context.bake(this.model);
            return new Test(bakedModel, this.tints);
        }

        public ResourceLocation model() {
            return this.model;
        }

        public List<ItemTintSource> tints() {
            return this.tints;
        }
    }
}