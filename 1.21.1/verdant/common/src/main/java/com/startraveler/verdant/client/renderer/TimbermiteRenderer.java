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

import com.startraveler.verdant.Constants;
import com.startraveler.verdant.entity.custom.TimbermiteEntity;
import net.minecraft.client.model.EndermiteModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;

public class TimbermiteRenderer extends MobRenderer<TimbermiteEntity, LivingEntityRenderState, EndermiteModel> {
    private static final ResourceLocation TIMBERMITE_LOCATION = ResourceLocation.fromNamespaceAndPath(
            Constants.MOD_ID, "textures/entity/timbermite.png");

    public TimbermiteRenderer(EntityRendererProvider.Context context) {
        super(context, new EndermiteModel(context.bakeLayer(ModelLayers.ENDERMITE)), 0.3F);
    }

    public ResourceLocation getTextureLocation(LivingEntityRenderState p_363663_) {
        return TIMBERMITE_LOCATION;
    }

    protected float getFlipDegrees() {
        return 180.0F;
    }

    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }
}
