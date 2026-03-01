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
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.monster.zombie.ZombieModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public class RootedOuterLayer extends RenderLayer<@NotNull ZombieRenderState, @NotNull ZombieModel<@NotNull ZombieRenderState>> {
    private static final Identifier ROOTED_OUTER_LAYER_LOCATION = Identifier.fromNamespaceAndPath(
            Constants.MOD_ID,
            "textures/entity/zombie/rooted_outer_layer.png"
    );
    private final ZombieModel<@NotNull ZombieRenderState> model;
    private final ZombieModel<@NotNull ZombieRenderState> babyModel;

    public RootedOuterLayer(RenderLayerParent<@NotNull ZombieRenderState, @NotNull ZombieModel<@NotNull ZombieRenderState>> renderer, EntityModelSet modelSet) {


        super(renderer);
        this.model = new ZombieModel<>(modelSet.bakeLayer(ModelLayers.DROWNED_OUTER_LAYER));
        this.babyModel = new ZombieModel<>(modelSet.bakeLayer(ModelLayers.DROWNED_BABY_OUTER_LAYER));
    }

    @Override
    public void submit(@NotNull PoseStack poseStack, @NotNull SubmitNodeCollector submitNodeCollector, int i, ZombieRenderState renderState, float v, float v1) {
        ZombieModel<@NotNull ZombieRenderState> rootedModel = renderState.isBaby ? this.babyModel : this.model;
        coloredCutoutModelCopyLayerRender(
                rootedModel,
                ROOTED_OUTER_LAYER_LOCATION,
                poseStack,
                submitNodeCollector,
                i,
                renderState,
                -1,
                1
        );

    }
}

