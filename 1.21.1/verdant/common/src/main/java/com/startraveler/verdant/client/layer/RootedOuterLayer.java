/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * If you modify this file, please include a notice stating the changes:
 * Example: "Modified by [Your Name] on [Date] - [Short Description of Changes]"
 */
package com.startraveler.verdant.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.startraveler.verdant.Constants;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.resources.ResourceLocation;

public class RootedOuterLayer extends RenderLayer<ZombieRenderState, ZombieModel<ZombieRenderState>> {
    private static final ResourceLocation ROOTED_OUTER_LAYER_LOCATION = ResourceLocation.fromNamespaceAndPath(
            Constants.MOD_ID,
            "textures/entity/zombie/rooted_outer_layer.png"
    );
    private final ZombieModel<ZombieRenderState> model;
    private final ZombieModel<ZombieRenderState> babyModel;

    public RootedOuterLayer(RenderLayerParent<ZombieRenderState, ZombieModel<ZombieRenderState>> renderer, EntityModelSet modelSet) {


        super(renderer);
        this.model = new ZombieModel<>(modelSet.bakeLayer(ModelLayers.DROWNED_OUTER_LAYER));
        this.babyModel = new ZombieModel<>(modelSet.bakeLayer(ModelLayers.DROWNED_BABY_OUTER_LAYER));
    }

    public void render(PoseStack stack, MultiBufferSource source, int packedLight, ZombieRenderState renderState, float f, float g) {
        ZombieModel<ZombieRenderState> drownedModel = renderState.isBaby ? this.babyModel : this.model;
        coloredCutoutModelCopyLayerRender(
                drownedModel,
                ROOTED_OUTER_LAYER_LOCATION,
                stack,
                source,
                packedLight,
                renderState,
                -1
        );
    }
}

