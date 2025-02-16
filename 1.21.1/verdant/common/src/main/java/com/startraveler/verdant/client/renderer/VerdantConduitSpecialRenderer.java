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
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.serialization.MapCodec;
import com.startraveler.verdant.Constants;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;

public class VerdantConduitSpecialRenderer implements NoDataSpecialModelRenderer {
    private final ModelPart model;

    public VerdantConduitSpecialRenderer(ModelPart model) {
        this.model = model;
    }

    public void render(ItemDisplayContext p_387714_, PoseStack p_386873_, MultiBufferSource p_388451_, int p_387407_, int p_387355_, boolean p_386645_) {
        VertexConsumer vertexconsumer = VerdantConduitRenderer.SHELL_TEXTURE.buffer(p_388451_, RenderType::entitySolid);
        p_386873_.pushPose();
        p_386873_.translate(0.5F, 0.5F, 0.5F);
        this.model.render(p_386873_, vertexconsumer, p_387407_, p_387355_);
        p_386873_.popPose();
    }

    public record Unbaked() implements SpecialModelRenderer.Unbaked {
        public static final ResourceLocation LOCATION = ResourceLocation.fromNamespaceAndPath(
                Constants.MOD_ID,
                "item/verdant_conduit"
        );
        public static final MapCodec<VerdantConduitSpecialRenderer.Unbaked> MAP_CODEC = MapCodec.unit(new VerdantConduitSpecialRenderer.Unbaked());

        public SpecialModelRenderer<?> bake(EntityModelSet p_387917_) {
            return new VerdantConduitSpecialRenderer(p_387917_.bakeLayer(ModelLayers.CONDUIT_SHELL));
        }

        public MapCodec<VerdantConduitSpecialRenderer.Unbaked> type() {
            return MAP_CODEC;
        }
    }
}

