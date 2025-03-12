package com.startraveler.verdant.data;

import com.startraveler.verdant.Constants;
import com.startraveler.verdant.registry.*;
import com.startraveler.verdant.util.VerdantTags;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.apache.commons.lang3.text.WordUtils;

import java.util.HashSet;
import java.util.Set;

public class VerdantEnglishUSLanguageProvider extends LanguageProvider {

    private final Set<Block> excludedBlocks;
    private final Set<Item> excludedItems;
    private final Set<TagKey<?>> excludedTags;
    private final Set<MobEffect> excludedEffects;
    private final Set<EntityType<?>> excludedEntityTypes;
    private final Set<Potion> excludedPotions;

    public VerdantEnglishUSLanguageProvider(PackOutput output) {
        super(output, Constants.MOD_ID, "en_us");
        excludedBlocks = new HashSet<>();
        excludedItems = new HashSet<>();
        excludedTags = new HashSet<>();
        excludedEffects = new HashSet<>();
        excludedEntityTypes = new HashSet<>();
        excludedPotions = new HashSet<>();
    }


    @Override
    protected void addTranslations() {


        // Delete all superfluous entries and manual override as need be.
        this.exclude(BlockRegistry.CANDLE_UBE_CAKE.get(), "Ube Cake with Candle");
        this.exclude(BlockRegistry.WHITE_CANDLE_UBE_CAKE.get(), "Ube Cake with White Candle");
        this.exclude(BlockRegistry.LIGHT_GRAY_CANDLE_UBE_CAKE.get(), "Ube Cake with Light Gray Candle");
        this.exclude(BlockRegistry.GRAY_CANDLE_UBE_CAKE.get(), "Ube Cake with Gray Candle");
        this.exclude(BlockRegistry.BLACK_CANDLE_UBE_CAKE.get(), "Ube Cake with Black Candle");
        this.exclude(BlockRegistry.BROWN_CANDLE_UBE_CAKE.get(), "Ube Cake with Brown Candle");
        this.exclude(BlockRegistry.RED_CANDLE_UBE_CAKE.get(), "Ube Cake with Red Candle");
        this.exclude(BlockRegistry.ORANGE_CANDLE_UBE_CAKE.get(), "Ube Cake with Orange Candle");
        this.exclude(BlockRegistry.YELLOW_CANDLE_UBE_CAKE.get(), "Ube Cake with Yellow Candle");
        this.exclude(BlockRegistry.LIME_CANDLE_UBE_CAKE.get(), "Ube Cake with Lime Candle");
        this.exclude(BlockRegistry.GREEN_CANDLE_UBE_CAKE.get(), "Ube Cake with Green Candle");
        this.exclude(BlockRegistry.CYAN_CANDLE_UBE_CAKE.get(), "Ube Cake with Cyan Candle");
        this.exclude(BlockRegistry.LIGHT_BLUE_CANDLE_UBE_CAKE.get(), "Ube Cake with Light Blue Candle");
        this.exclude(BlockRegistry.BLUE_CANDLE_UBE_CAKE.get(), "Ube Cake with Blue Candle");
        this.exclude(BlockRegistry.PURPLE_CANDLE_UBE_CAKE.get(), "Ube Cake with Purple Candle");
        this.exclude(BlockRegistry.MAGENTA_CANDLE_UBE_CAKE.get(), "Ube Cake with Magenta Candle");
        this.exclude(BlockRegistry.PINK_CANDLE_UBE_CAKE.get(), "Ube Cake with Pink Candle");
        this.add("block.verdant.fish_trap.gui.no_water", "No Water");
        this.add("block.verdant.fish_trap.gui.no_bait", "No Bait");
        this.add("block.verdant.fish_trap.gui.bait", "%s%%");
        this.exclude(BlockRegistry.VERDANT_GRASS_DIRT.get(), "Verdant Grass Block");
        this.exclude(BlockRegistry.VERDANT_ROOTED_DIRT.get(), "Verdant Rooted Dirt");
        this.exclude(BlockRegistry.VERDANT_GRASS_MUD.get(), "Verdant Mud Grass Block");
        this.exclude(BlockRegistry.VERDANT_ROOTED_MUD.get(), "Verdant Rooted Mud");
        this.exclude(BlockRegistry.VERDANT_GRASS_CLAY.get(), "Verdant Clay Grass Block");
        this.exclude(BlockRegistry.VERDANT_ROOTED_CLAY.get(), "Verdant Rooted Clay");
        this.add("item.verdant.rope_coil.length", "Length: %s");
        this.add("item.verdant.rope_coil.hook", "Has Hook");
        this.add("item.verdant.rope_coil.glow", "Glow: %s");
        this.add("item.verdant.rope_coil.none", "None");
        this.add("item.verdant.rope_coil.bell", "Bell");
        this.add("item.verdant.rope_coil.lantern", "Lantern");
        this.add("item.verdant.rope_coil.soul_lantern", "Soul Lantern");
        this.exclude(ItemRegistry.BUCKET_OF_TOXIC_ASH.get(), "Toxic Ash Bucket");
        this.exclude(ItemRegistry.BUCKET_OF_TOXIC_SOLUTION.get(), "Toxic Solution Bucket");
        this.exclude(BlockRegistry.POISON_STRANGLER_LEAVES.get(), "Poison Ivy Covered Strangler Leaves");
        this.exclude(BlockRegistry.BLEEDING_HEART.get(), "Bleeding Heart Flower");
        this.exclude(BlockRegistry.BUSH.get(), "Tangled Bush");
        this.exclude(BlockRegistry.POTTED_BUSH.get(), "Potted Tangled Bush");
        this.exclude(BlockRegistry.THORN_BUSH.get(), "Thorny Tangled Bush");
        this.exclude(BlockRegistry.DEAD_GRASS.get(), "Wilted Grass");
        this.add("block.minecraft.bed.caffeine", "You may not rest now; you are caffeinated");
        this.add("item.minecraft.smithing_template.imbuement_upgrade.applies_to", "Heartwood Equipment");
        this.add("item.minecraft.smithing_template.imbuement_upgrade.ingredients", "Heart Fragment");
        this.add("item.minecraft.smithing_template.imbuement_upgrade.additions_slot_description", "Add Heart Fragment");
        this.add(
                "item.minecraft.smithing_template.imbuement_upgrade.base_slot_description",
                "Add heartwood armor, weapon, or tool"
        );
        this.excludedPotions.add(PotionRegistry.CAFFEINE.get());
        this.excludedPotions.add(PotionRegistry.LONG_CAFFEINE.get());
        this.excludedPotions.add(PotionRegistry.STRONG_CAFFEINE.get());
        this.excludedPotions.add(PotionRegistry.LONG_STRONG_CAFFEINE.get());
        this.add("item.minecraft.potion.effect.caffeine", "Coffee");
        this.add("item.minecraft.splash_potion.effect.caffeine", "Splash Potion of Caffeine");
        this.add("item.minecraft.lingering_potion.effect.caffeine", "Lingering Potion of Caffeine");
        this.add("item.minecraft.potion.effect.strong_caffeine", "Sweet Coffee");
        this.add("item.minecraft.splash_potion.effect.strong_caffeine", "Splash Potion of Caffeine");
        this.add("item.minecraft.lingering_potion.effect.strong_caffeine", "Lingering Potion of Caffeine");
        this.add("item.minecraft.potion.effect.long_caffeine", "Strong Coffee");
        this.add("item.minecraft.splash_potion.effect.long_caffeine", "Splash Potion of Caffeine");
        this.add("item.minecraft.lingering_potion.effect.long_caffeine", "Lingering Potion of Caffeine");
        this.add("item.minecraft.potion.effect.long_strong_caffeine", "Sweetened Strong Coffee");
        this.add("item.minecraft.splash_potion.effect.long_strong_caffeine", "Splash Potion of Caffeine");
        this.add("item.minecraft.lingering_potion.effect.long_strong_caffeine", "Lingering Potion of Caffeine");
        this.add("item.minecraft.tipped_arrow.effect.caffeine", "Arrow of Caffeine");
        this.add("item.minecraft.tipped_arrow.effect.long_caffeine", "Arrow of Caffeine");
        this.add("item.minecraft.tipped_arrow.effect.strong_caffeine", "Arrow of Caffeine");
        this.add("item.minecraft.tipped_arrow.effect.long_strong_caffeine", "Arrow of Caffeine");
        this.add("item.verdant.tipped_dart.effect.caffeine", "Dart of Caffeine");
        this.add("item.verdant.tipped_dart.effect.long_caffeine", "Dart of Caffeine");
        this.add("item.verdant.tipped_dart.effect.strong_caffeine", "Dart of Caffeine");
        this.add("item.verdant.tipped_dart.effect.long_strong_caffeine", "Dart of Caffeine");

        this.exclude(MobEffectRegistry.RED_GREEN.get(), "Red-Green Colorblind");
        this.exclude(MobEffectRegistry.SEPIA.get(), "Sepia");
        this.add("death.attack.verdant.briar.message", "%s sat on a thorn.");
        this.add("death.attack.verdant.item.message", "%s was thrown into the briar patch by %s using %s.");
        this.add("death.attack.verdant.player.message", "%s was thrown into the briar patch by %s.");
        this.add("death.attack.photosensitive", "%s got a sunburn.");
        this.add("death.attack.photosensitive.item", "%s got a sunburn thanks to %s.");
        this.add("death.attack.photosensitive.player", "%s got a sunburn thanks to %s.");
        this.add("death.attack.toxic_ash", "%s suffered chemical burns.");
        this.add("death.attack.toxic_ash.item", "%s suffered chemical burns thanks to %s.");
        this.add("death.attack.toxic_ash.player", "%s suffered chemical burns thanks to %s.");
        this.add("advancements.verdant.root.title", "The Verdant Growth");
        this.add("advancements.verdant.root.description", "A spreading plague of greenery is sealed in this world.");
        this.add("advancements.verdant.petrichor.title", "Petrichor");
        this.add("advancements.verdant.petrichor.description", "Discover a pyramid that smells of potential life.");
        this.add("advancements.verdant.overgrowth.title", "Overgrowth");
        this.add(
                "advancements.verdant.overgrowth.description",
                "Unleash the Verdant Growth upon an unsuspecting world. There is no going back."
        );
        this.add("advancements.verdant.stand_on_verdant_ground.title", "What's All That Green Stuff?");
        this.add("advancements.verdant.stand_on_verdant_ground.description", "Set foot on a strange new type of grass");
        this.add("advancements.verdant.stinking_blossom.title", "It Makes Silage Smell Like Roses");
        this.add(
                "advancements.verdant.stinking_blossom.description",
                "Stroll through a large reddish blossom, and regret it."
        );
        this.add("advancements.verdant.thorn_bush.title", "Thorny Problem");
        this.add("advancements.verdant.thorn_bush.description", "Try to walk through a thorn bush.");
        this.add("advancements.verdant.trap_plant.title", "In Soviet Russia...");
        this.add("advancements.verdant.trap_plant.description", "Whoops.");
        this.add("advancements.verdant.poison_ivy.title", "Leaves of Three");
        this.add("advancements.verdant.poison_ivy.description", "Discover why those vines are poison green.");
        this.add("advancements.verdant.inside_tree.title", "A Spirit Too Delicate");
        this.add("advancements.verdant.inside_tree.description", "Get stuck inside a fast-growing tree.");
        this.add("advancements.verdant.strong_trees.title", "The Trees Are Strong");
        this.add("advancements.verdant.strong_trees.description", "Get to the heart of a new tree species.");
        this.add("advancements.verdant.heartworm.title", "Heartworm");
        this.add("advancements.verdant.heartworm.description", "Defeat a Timbermite.");
        this.add("advancements.verdant.deep_roots.title", "Their Roots Go Deep");
        this.add("advancements.verdant.deep_roots.description", "Discover just how deep the Verdant has spread.");
        this.add("advancements.verdant.rip_them_all_down.title", "Rip Them All Down");
        this.add(
                "advancements.verdant.rip_them_all_down.description",
                "Wage a war against the forest, and hold a very large amount of heartwood"
        );
        this.add("creativetab.verdant.items", "Verdant Items");
        this.add("creativetab.verdant.foods", "Verdant Foods");
        this.add("creativetab.verdant.blocks", "Verdant Blocks");
        this.add("creativetab.verdant.tools", "Verdant Tools & Utilities");
        this.add("creativetab.verdant.combat", "Verdant Combat");

        // Now, do all the rest automagically.
        this.addBlockTranslations();
        this.addTagTranslations();
        this.addItemTranslations();
        this.addEffectTranslations();
        this.addEntityTranslations();
        this.addPotionTranslations();
        this.addWoodSetTranslations();
        this.addDartTranslations();

    }

    private void exclude(EntityType<?> key, String name) {
        this.excludedEntityTypes.add(key);
        super.add(key, name);
    }

    public void exclude(Block key, String name) {
        this.excludedBlocks.add(key);
        super.add(key, name);
    }

    public void exclude(Item key, String name) {
        this.excludedItems.add(key);
        super.add(key, name);
    }

    public void exclude(MobEffect key, String name) {
        this.excludedEffects.add(key);
        super.add(key, name);
    }

    public void exclude(TagKey<?> tagKey, String name) {
        this.excludedTags.add(tagKey);
        super.add(tagKey, name);
    }

    protected void addTagTranslations() {
        VerdantTags.TAGS.forEach(tag -> {
            if (!excludedTags.contains(tag)) {
                this.addTag(
                        () -> tag,
                        WordUtils.capitalize(tag.location().getPath().replace('/', ' ').replace('_', ' '))
                );
            }
        });
    }

    protected void addBlockTranslations() {
        BlockRegistry.BLOCKS.getEntries().forEach(holder -> {
            if (!excludedBlocks.contains(holder.get())) {
                this.addBlock(
                        holder,
                        WordUtils.capitalize(holder.getId().getPath().replace('/', ' ').replace('_', ' '))
                );
            }
        });
    }

    protected void addEffectTranslations() {
        MobEffectRegistry.MOB_EFFECTS.getEntries().forEach(holder -> {
            if (!excludedEffects.contains(holder.get())) {
                this.addEffect(
                        holder,
                        WordUtils.capitalize(holder.getId().getPath().replace('/', ' ').replace('_', ' '))
                );
            }
        });
    }

    protected void addPotionTranslations() {
        PotionRegistry.POTIONS.getEntries().forEach(holder -> {
            if (!excludedPotions.contains(holder.get())) {
                String rawId = holder.getId().getPath();
                String id = rawId.replace("long_", "").replace("strong_", "").replace("_", " ");
                String name = WordUtils.capitalize(id);
                this.add("item.minecraft.potion.effect." + rawId, "Potion of " + name);
                this.add("item.minecraft.splash_potion.effect." + rawId, "Splash Potion of " + name);
                this.add("item.minecraft.lingering_potion.effect." + rawId, "Lingering Potion of " + name);
                this.add("item.minecraft.tipped_arrow.effect." + rawId, "Arrow of " + name);
            }
        });
    }

    protected void addDartTranslations() {
        this.add("item.verdant.tipped_dart.effect.empty", "Tipped Dart");
        BuiltInRegistries.POTION.entrySet().forEach(entry -> {
            if (!excludedPotions.contains(entry.getValue())) {
                String rawId = entry.getKey().location().getPath();
                String id = rawId.replace("long_", "").replace("strong_", "").replace("_", " ");
                String name = WordUtils.capitalize(id);
                this.add("item.verdant.tipped_dart.effect." + rawId, "Dart of " + name);
            }
        });
    }

    protected void addEntityTranslations() {
        EntityTypeRegistry.ENTITY_TYPES.getEntries().forEach(holder -> {
            if (!excludedEntityTypes.contains(holder.get())) {
                this.addEntityType(
                        holder,
                        WordUtils.capitalize(holder.getId().getPath().replace('/', ' ').replace('_', ' '))
                );
            }
        });
    }

    protected void addItemTranslations() {
        ItemRegistry.ITEMS.getEntries().forEach(holder -> {

            if (!excludedItems.contains(holder.get()) && !(holder.get().getDescriptionId().startsWith("block"))) {
                this.addItem(
                        holder,
                        WordUtils.capitalize(holder.getId().getPath().replace('/', ' ').replace('_', ' '))
                );
            }
        });
    }

    protected void addWoodSetTranslations() {
        WoodSets.WOOD_SETS.forEach(set -> {
            String name = WordUtils.capitalize(set.getName());
            this.add(set.getLog().get(), name + " Log");
            this.add(set.getWood().get(), name + " Wood");
            this.add(set.getStrippedLog().get(), "Stripped " + name + " Log");
            this.add(set.getStrippedWood().get(), "Stripped " + name + " Wood");
            this.add(set.getPlanks().get(), name + " Planks");
            this.add(set.getSlab().get(), name + " Slab");
            this.add(set.getStairs().get(), name + " Stairs");
            this.add(set.getFence().get(), name + " Fence");
            this.add(set.getFenceGate().get(), name + " Fence Gate");
            this.add(set.getDoor().get(), name + " Door");
            this.add(set.getTrapdoor().get(), name + " Trapdoor");
            this.add(set.getPressurePlate().get(), name + " Pressure Plate");
            this.add(set.getButton().get(), name + " Button");
            this.add(set.getWallHangingSign().get(), name + " Wall Hanging Sign");
            this.add(set.getWallSign().get(), name + " Wall Sign");
            this.add(set.getHangingSign().get(), name + " Hanging Sign");
            this.add(set.getSign().get(), name + " Sign");
            this.add(set.getHangingSignItem().get(), name + " Hanging Sign");
            this.add(set.getSignItem().get(), name + " Sign");
            this.add(set.getBoat().get(), name + " Boat");
            this.add(set.getChestBoat().get(), name + " Boat with Chest");
            this.add(set.getBoatItem().get(), name + " Boat");
            this.add(set.getChestBoatItem().get(), name + " Boat with Chest");
            this.add("creativetab." + set.getModid() + ".wood_set." + set.getName(), name + " Wood Set");
            this.add(set.getLogs(), name + " Logs");


        });


    }
}