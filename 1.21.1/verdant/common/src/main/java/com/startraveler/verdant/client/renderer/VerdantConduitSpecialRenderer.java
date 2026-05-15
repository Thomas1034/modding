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
package com.startraveler.verdant.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.ConduitRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.special.ConduitSpecialRenderer;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import org.jetbrains.annotations.NotNull;

// TODO
public class VerdantConduitSpecialRenderer extends ConduitSpecialRenderer {

    public VerdantConduitSpecialRenderer(SpriteGetter sprites, ModelPart model) {
        super(sprites, model);
    }

    public void submit(@NotNull PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, int overlayCoords, boolean hasFoil, int outlineColor) {
        submitNodeCollector.submitModelPart(
                this.model,
                poseStack,
                ConduitRenderer.SHELL_TEXTURE.renderType(RenderTypes::entitySolid),
                lightCoords,
                overlayCoords,
                this.sprites.get(VerdantConduitRenderer.SHELL_TEXTURE),
                false,
                false,
                -1,
                null,
                outlineColor
        );
    }

    public record Unbaked() implements NoDataSpecialModelRenderer.Unbaked {
        public static final MapCodec<VerdantConduitSpecialRenderer.Unbaked> MAP_CODEC = MapCodec.unit(new VerdantConduitSpecialRenderer.Unbaked());

        @Override
        public @NotNull MapCodec<VerdantConduitSpecialRenderer.Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public VerdantConduitSpecialRenderer bake(SpecialModelRenderer.BakingContext context) {
            return new VerdantConduitSpecialRenderer(
                    context.sprites(),
                    context.entityModelSet().bakeLayer(ModelLayers.CONDUIT_SHELL)
            );
        }
    }
}
