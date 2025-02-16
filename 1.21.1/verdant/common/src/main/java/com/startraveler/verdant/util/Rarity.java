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
package com.startraveler.verdant.util;

public class Rarity {
    public static final int ALWAYS = Integer.MAX_VALUE >> 1;
    public static final int OVERWHELMINGLY_COMMON = 400;
    public static final int EXTREMELY_COMMON = 200;
    public static final int VERY_COMMON = 150;
    public static final int COMMON = 100;
    public static final int UNCOMMON = 50;
    public static final int VERY_UNCOMMON = 25;
    public static final int EXTREMELY_UNCOMMON = 10;
    public static final int RARE = 5;
    public static final int VERY_RARE = 2;
    public static final int EXTREMELY_RARE = 1;
    public static final int NEVER = 0;

    public static final int SPECIAL_CASE_COMMON = 3000;
    public static final int SPECIAL_CASE_UNCOMMON = 1000;
}
