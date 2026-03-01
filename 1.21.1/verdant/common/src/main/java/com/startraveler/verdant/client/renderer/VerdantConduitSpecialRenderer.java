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
import com.startraveler.verdant.Constants;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.resources.model.MaterialSet;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemDisplayContext;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3fc;

import java.util.function.Consumer;

// TODO
public class VerdantConduitSpecialRenderer implements NoDataSpecialModelRenderer {
    private final MaterialSet materials;
    private final ModelPart model;

    public VerdantConduitSpecialRenderer(MaterialSet materials, ModelPart model) {
        this.materials = materials;
        this.model = model;
    }

    public void submit(@NotNull ItemDisplayContext itemDisplayContext, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int i, int i1, boolean b, int i2) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 0.5F, 0.5F);
        submitNodeCollector.submitModelPart(
                this.model,
                poseStack,
                VerdantConduitRenderer.SHELL_TEXTURE.renderType(RenderTypes::entitySolid),
                i,
                i1,
                this.materials.get(VerdantConduitRenderer.SHELL_TEXTURE),
                false,
                false,
                -1,
                null,
                i2
        );
        poseStack.popPose();
    }

    public void getExtents(@NotNull Consumer<Vector3fc> consumer) {
        PoseStack posestack = new PoseStack();
        posestack.translate(0.5F, 0.5F, 0.5F);
        this.model.getExtentsForGui(posestack, consumer);
    }

    public record Unbaked() implements SpecialModelRenderer.Unbaked {
        public static final MapCodec<VerdantConduitSpecialRenderer.Unbaked> MAP_CODEC = MapCodec.unit(new VerdantConduitSpecialRenderer.Unbaked());
        public static final Identifier LOCATION = Constants.id("item/verdant_conduit"
        );

        public SpecialModelRenderer<?> bake(SpecialModelRenderer.BakingContext context) {
            return new VerdantConduitSpecialRenderer(
                    context.materials(),
                    context.entityModelSet().bakeLayer(ModelLayers.CONDUIT_SHELL)
            );
        }

        public @NotNull MapCodec<VerdantConduitSpecialRenderer.Unbaked> type() {
            return MAP_CODEC;
        }
    }
}
