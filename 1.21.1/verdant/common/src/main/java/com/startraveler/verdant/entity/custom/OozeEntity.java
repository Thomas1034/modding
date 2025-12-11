package com.startraveler.verdant.entity.custom;

import com.google.common.base.Suppliers;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.startraveler.verdant.registry.ItemRegistry;
import com.startraveler.verdant.util.VerdantTags;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.SuspiciousEffectHolder;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class OozeEntity extends Slime implements Bucketable {
    public static final int EFFECT_SCALE = 4;
    public static final Supplier<ParticleOptions> PARTICLE_OPTIONS = Suppliers.memoize(() -> new ItemParticleOption(
            ParticleTypes.ITEM,
            new ItemStack(ItemRegistry.SAP_GLOB.get())
    ));
    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(
            OozeEntity.class,
            EntityDataSerializers.BOOLEAN
    );


    public OozeEntity(EntityType<? extends OozeEntity> type, Level level) {
        super(type, level);
    }

    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(FROM_BUCKET, false);
    }

    @Override
    public void setSize(int size, boolean resetHealth) {
        super.setSize(size, resetHealth);
        int clampedSize = Mth.clamp(size, 1, 127);
        Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_DAMAGE))
                .setBaseValue(Mth.clamp(clampedSize / 2.0, 1, 127) + 1);
        Objects.requireNonNull(this.getAttribute(Attributes.ARMOR)).setBaseValue(clampedSize * 2);
    }

    @Override
    protected void addAdditionalSaveData(@NotNull ValueOutput valueOutput) {
        super.addAdditionalSaveData(valueOutput);
        valueOutput.putBoolean("FromBucket", this.fromBucket());
    }

    @Override
    protected void readAdditionalSaveData(@NotNull ValueInput valueInput) {
        super.readAdditionalSaveData(valueInput);
        this.setFromBucket(valueInput.getBooleanOr("FromBucket", false));
    }

    @Override
    protected @NotNull ParticleOptions getParticleType() {
        return PARTICLE_OPTIONS.get();
    }

    @Override
    protected boolean isDealsDamage() {
        return this.isEffectiveAi();
    }

    @Override
    protected float getAttackDamage() {
        return this.fromBucket() ? Math.max(0, (super.getAttackDamage() / 4) - 1) : (super.getAttackDamage() + 1.0F);
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor serverLevelAccessor, @NotNull DifficultyInstance difficultyInstance, @NotNull EntitySpawnReason entitySpawnReason, @Nullable SpawnGroupData spawnGroupData) {
        SpawnGroupData result = super.finalizeSpawn(
                serverLevelAccessor,
                difficultyInstance,
                entitySpawnReason,
                spawnGroupData
        );

        RandomSource random = serverLevelAccessor.getRandom();
        boolean shouldIncreaseSize = random.nextInt(128) == 0;
        boolean shouldHoldFlower = random.nextInt(4) != 0;
        boolean shouldBeOozing = random.nextInt(16) == 0;

        if (shouldIncreaseSize) {
            this.setSize(2 * this.getSize(), true);
        }

        if (shouldHoldFlower) {
            if (this.getMainHandItem().isEmpty()) {
                ArrayList<Holder<Item>> allFlowers = Lists.newArrayList(BuiltInRegistries.ITEM.getTagOrEmpty(VerdantTags.Items.VERDANT_SMALL_FLOWERS));

                Holder<Item> randomFlower = allFlowers.get(random.nextInt(allFlowers.size()));

                this.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(randomFlower));
                if (!this.getMainHandItem().isEmpty()) {
                    this.setDropChance(EquipmentSlot.MAINHAND, 1.0f);
                }
            }
        }


        if (shouldBeOozing) {
            this.addEffect(new MobEffectInstance(
                    MobEffects.OOZING,
                    MobEffectInstance.INFINITE_DURATION,
                    MobEffectInstance.MIN_AMPLIFIER,
                    true,
                    false
            ));
        }

        this.setCanPickUpLoot(true);
        return result;
    }

    @Override
    @SuppressWarnings("deprecation")
    public float getLightLevelDependentMagicValue() {
        return 1.0F;
    }

    @Override
    public boolean wantsToPickUp(@NotNull ServerLevel level, ItemStack stack) {
        return stack.is(ItemTags.SMALL_FLOWERS);
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return !this.fromBucket() && !this.hasCustomName() && super.removeWhenFarAway(distanceToClosestPlayer);
    }

    @Override
    public boolean canPickUpLoot() {
        return super.canPickUpLoot();
    }

    @Override
    protected @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        return this.getSize() == 1
                ? Bucketable.bucketMobPickup(player, hand, this).orElse(super.mobInteract(player, hand))
                : super.mobInteract(player, hand);
    }

    public void applyHitPotionEffect(@NotNull Entity entity) {
        ItemStack stack = this.getMainHandItem();
        if (stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof SuspiciousEffectHolder seh) {

            SuspiciousStewEffects effects = seh.getSuspiciousEffects();

            if (entity instanceof LivingEntity livingEntity) {
                for (SuspiciousStewEffects.Entry effect : effects.effects()) {
                    livingEntity.addEffect(effect.createEffectInstance().withScaledDuration(EFFECT_SCALE), this);
                }
            }

        }
    }

    @Override
    public boolean fromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }

    @Override
    public void setFromBucket(boolean b) {
        this.entityData.set(FROM_BUCKET, b);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void saveToBucketTag(@NotNull ItemStack bucket) {
        Bucketable.saveDefaultDataToBucketTag(this, bucket);
        CustomData.update(
                DataComponents.BUCKET_ENTITY_DATA, bucket, (tag) -> {
                    tag.putInt("Size", this.getSize() - 1);
                    CompoundTag inventoryTag = new CompoundTag();
                    for (EquipmentSlot slot : EquipmentSlot.values()) {
                        ItemStack inSlot = this.getItemBySlot(slot);
                        if (!inSlot.isEmpty()) {
                            inventoryTag.put(
                                    slot.getSerializedName(),
                                    ItemStack.CODEC.encodeStart(NbtOps.INSTANCE, inSlot).getOrThrow()
                            );
                        }
                    }
                    tag.put("EncodedInventory", inventoryTag);
                }
        );
    }


    @Override
    @SuppressWarnings("deprecation")
    public void loadFromBucketTag(@NotNull CompoundTag compoundTag) {
        Bucketable.loadDefaultDataFromBucketTag(this, compoundTag);

        Optional<CompoundTag> inventoryTag = compoundTag.getCompound("EncodedInventory");
        inventoryTag.ifPresent(tag -> {
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                Tag slotTag = tag.get(slot.getSerializedName());
                if (slotTag != null) {
                    ItemStack inSlot = ItemStack.CODEC.decode(NbtOps.INSTANCE, slotTag).map(Pair::getFirst).mapOrElse(
                            Function.identity(), (error) -> ItemStack.EMPTY);
                    if (inSlot != null && !inSlot.isEmpty()) {
                        this.setItemSlot(slot, inSlot);
                    }
                }
            }
        });

        this.setSize(compoundTag.getIntOr("Size", 0) + 1, false);
    }


    @Override
    public @NotNull ItemStack getBucketItemStack() {
        return new ItemStack(ItemRegistry.OOZE_BUCKET.get());
    }

    @Override
    public @NotNull SoundEvent getPickupSound() {
        return SoundEvents.SLIME_JUMP_SMALL;
    }
}
