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
package com.startraveler.verdant.mixin;

import com.startraveler.verdant.entity.custom.RootedEntity;
import com.startraveler.verdant.registry.EntityTypeRegistry;
import com.startraveler.verdant.util.VerdantTags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zombie;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Zombie.class)
public abstract class ZombieConvertToRootedMixin {

    @Unique
    protected boolean verdant$isConverting;
    @Unique
    private int verdant$rootedConversionTime;
    @Unique
    private int verdant$onVerdantTime;

    @Shadow
    protected void convertToZombieType(EntityType<? extends Zombie> entityType) {
        throw new AssertionError();
    }

    @Inject(method = "readAdditionalSaveData", at = @At(value = "TAIL"))
    private void readAdditionalSaveData(CompoundTag compound, CallbackInfo ci) {
        this.verdant$onVerdantTime = compound.getInt("OnVerdantTime");
        this.verdant$isConverting = compound.getBoolean("IsVerdantConverting");
        if (compound.contains("RootedConversionTime", 99) && compound.getInt("RootedConversionTime") > -1) {
            this.verdant$startOnVerdantConversion(compound.getInt("RootedConversionTime"));
        }
    }

    @Inject(method = "addAdditionalSaveData", at = @At(value = "TAIL"))
    private void addAdditionalSaveData(CompoundTag compound, CallbackInfo ci) {
        compound.putInt("OnVerdantTime", this.verdant$isOnVerdantConverting() ? this.verdant$onVerdantTime : -1);
        compound.putBoolean("IsVerdantConverting", this.verdant$isConverting);
        compound.putInt(
                "RootedConversionTime",
                this.verdant$isOnVerdantConverting() ? this.verdant$rootedConversionTime : -1
        );
    }

    @Inject(method = "tick", at = @At(value = "TAIL"))
    private void tick(CallbackInfo ci) {
        if (!((Zombie) (Object) this).level().isClientSide && ((Zombie) (Object) this).isAlive() && !((Zombie) (Object) this).isNoAi()) {
            if (this.verdant$isOnVerdantConverting()) {
                --this.verdant$rootedConversionTime;
                if (this.verdant$rootedConversionTime < 0) {
                    this.verdant$doOnRootedConversion();
                }
            } else if (this.verdant$convertsOnVerdant()) {
                if (((Zombie) (Object) this).getBlockStateOn().is(VerdantTags.Blocks.VERDANT_GROUND)) {
                    ++this.verdant$onVerdantTime;
                    if (this.verdant$onVerdantTime >= 60) {
                        this.verdant$startOnVerdantConversion(30);
                    }
                } else {
                    this.verdant$onVerdantTime = -1;
                }
            }
        }
    }

    @Unique
    private boolean verdant$convertsOnVerdant() {
        return !(((Zombie) (Object) this) instanceof RootedEntity);
    }

    @Unique
    private void verdant$doOnRootedConversion() {
        this.convertToZombieType(EntityTypeRegistry.ROOTED.get());
        if (!((Zombie) (Object) this).isSilent()) {
            ((Zombie) (Object) this).level().levelEvent(null, 1040, ((Zombie) (Object) this).blockPosition(), 0);
        }
    }


    @Unique
    public boolean verdant$isOnVerdantConverting() {
        return this.verdant$isConverting;
    }


    @Unique
    private void verdant$startOnVerdantConversion(int conversionTime) {
        this.verdant$rootedConversionTime = conversionTime;
        this.verdant$isConverting = true;
    }


}

