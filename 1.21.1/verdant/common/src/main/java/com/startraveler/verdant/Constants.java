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
package com.startraveler.verdant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {

    public static final String MOD_ID = "verdant";
    public static final String MOD_NAME = "Verdant";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public static final float DEFAULT_EXPLOSION_DAMAGE_MULTIPLIER = 1f;
    // Uses a ThreadLocal to modify the damage multiplier of explosions.
    // ALWAYS use try-finally to reset to 1 when you're done!
    public static final ThreadLocal<Float> EXPLOSION_DAMAGE_MULTIPLIER = ThreadLocal.withInitial(() -> DEFAULT_EXPLOSION_DAMAGE_MULTIPLIER);
}
