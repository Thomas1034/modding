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
import com.startraveler.verdant.client.layer.RootedOuterLayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.resources.ResourceLocation;

public class RootedRenderer extends ZombieRenderer {

    private static final ResourceLocation ZOMBIE_LOCATION = ResourceLocation.fromNamespaceAndPath(
            Constants.MOD_ID,
            "textures/entity/zombie/rooted.png"
    );

    public RootedRenderer(Context context) {
        super(context);
        this.addLayer(new RootedOuterLayer(this, context.getModelSet()));

    }

    public ResourceLocation getTextureLocation(ZombieRenderState state) {
        return ZOMBIE_LOCATION;
    }

}

