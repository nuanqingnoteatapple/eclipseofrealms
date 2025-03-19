package com.oblivioussp.spartanweaponry.api.tags;

import com.oblivioussp.spartanweaponry.ModSpartanWeaponry;
import com.oblivioussp.spartanweaponry.api.SpartanWeaponryAPI;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

/**
 * This class contains all the different item tags used by Spartan Weaponry. 
 * Addon authors should add their weapons to these tags as necessary to allow Quivers to work with addon weapons and Advancements to trigger
 * @author ObliviousSpartan
 *
 */
public class ModItemTags 
{
	// Handles and Poles
	public static final TagKey<Item> HANDLES = ItemTags.create(new ResourceLocation(SpartanWeaponryAPI.MOD_ID + ":handles"));
	public static final TagKey<Item> POLES = ItemTags.create(new ResourceLocation(SpartanWeaponryAPI.MOD_ID + ":poles"));
	
	// Tags for all weapons of a specified type
	public static final TagKey<Item> DAGGERS = ItemTags.create(new ResourceLocation(SpartanWeaponryAPI.MOD_ID + ":daggers"));
	public static final TagKey<Item> PARRYING_DAGGERS = ItemTags.create(new ResourceLocation(SpartanWeaponryAPI.MOD_ID + ":parrying_daggers"));
	public static final TagKey<Item> GREATSWORDS = ItemTags.create(new ResourceLocation(SpartanWeaponryAPI.MOD_ID + ":greatswords"));
	public static final TagKey<Item> SPEARS = ItemTags.create(new ResourceLocation(SpartanWeaponryAPI.MOD_ID + ":spears"));
	public static final TagKey<Item> LANCES = ItemTags.create(new ResourceLocation(SpartanWeaponryAPI.MOD_ID + ":lances"));
	public static final TagKey<Item> LONGBOWS = ItemTags.create(new ResourceLocation(SpartanWeaponryAPI.MOD_ID + ":longbows"));
	public static final TagKey<Item> HEAVY_CROSSBOWS = ItemTags.create(new ResourceLocation(SpartanWeaponryAPI.MOD_ID + ":heavy_crossbows"));
	public static final TagKey<Item> GLAIVES = ItemTags.create(new ResourceLocation(SpartanWeaponryAPI.MOD_ID + ":glaives"));
	public static final TagKey<Item> QUARTERSTAVES = ItemTags.create(new ResourceLocation(SpartanWeaponryAPI.MOD_ID + ":quarterstaves"));
	public static final TagKey<Item> SCYTHES = ItemTags.create(new ResourceLocation(SpartanWeaponryAPI.MOD_ID + ":scythes"));
	
	// Tags for all weapons made from a specific material
	public static final TagKey<Item> WOODEN_WEAPONS = ItemTags.create(new ResourceLocation(SpartanWeaponryAPI.MOD_ID + ":wooden_weapons"));
	public static final TagKey<Item> STONE_WEAPONS = ItemTags.create(new ResourceLocation(SpartanWeaponryAPI.MOD_ID + ":stone_weapons"));
	public static final TagKey<Item> LEATHER_WEAPONS = ItemTags.create(new ResourceLocation(SpartanWeaponryAPI.MOD_ID + ":leather_weapons"));
	public static final TagKey<Item> IRON_WEAPONS = ItemTags.create(new ResourceLocation(SpartanWeaponryAPI.MOD_ID + ":iron_weapons"));
	public static final TagKey<Item> GOLDEN_WEAPONS = ItemTags.create(new ResourceLocation(SpartanWeaponryAPI.MOD_ID + ":golden_weapons"));
	public static final TagKey<Item> DIAMOND_WEAPONS = ItemTags.create(new ResourceLocation(SpartanWeaponryAPI.MOD_ID + ":diamond_weapons"));
	public static final TagKey<Item> NETHERITE_WEAPONS = ItemTags.create(new ResourceLocation(SpartanWeaponryAPI.MOD_ID + ":netherite_weapons"));

	// Arrows and Bolts
	public static final TagKey<Item> ARROWS = ItemTags.create(new ResourceLocation(SpartanWeaponryAPI.MOD_ID + ":arrows"));
	public static final TagKey<Item> DIAMOND_PROJECTILES = ItemTags.create(new ResourceLocation(SpartanWeaponryAPI.MOD_ID + ":diamond_projectiles"));
	public static final TagKey<Item> NETHERITE_PROJECTILES = ItemTags.create(new ResourceLocation(SpartanWeaponryAPI.MOD_ID + ":netherite_projectiles"));
	public static final TagKey<Item> BOLTS = ItemTags.create(new ResourceLocation(SpartanWeaponryAPI.MOD_ID + ":bolts"));
	public static final TagKey<Item> THROWING_WEAPONS = ItemTags.create(new ResourceLocation(SpartanWeaponryAPI.MOD_ID + ":throwing_weapons"));	
	public static final TagKey<Item> HAS_CUSTOM_CROSSHAIR = ItemTags.create(new ResourceLocation(SpartanWeaponryAPI.MOD_ID + ":has_custom_crosshair"));
	
	// Materials for repairing weapons
	public static final TagKey<Item> COBBLESTONE = ItemTags.create(new ResourceLocation("forge:cobblestone"));
	public static final TagKey<Item> LEATHER = ItemTags.create(new ResourceLocation("forge:leather"));
	public static final TagKey<Item> IRON_INGOT = ItemTags.create(new ResourceLocation("forge:ingots/iron"));
	public static final TagKey<Item> GOLD_INGOT = ItemTags.create(new ResourceLocation("forge:ingots/gold"));
	public static final TagKey<Item> DIAMOND = ItemTags.create(new ResourceLocation("forge:gems/diamond"));
	public static final TagKey<Item> NETHERITE_INGOT = ItemTags.create(new ResourceLocation("forge:ingots/netherite"));
	public static final TagKey<Item> GRASS = ItemTags.create(new ResourceLocation("forge:grass"));
	public static final TagKey<Item> RAW_MEAT = ItemTags.create(new ResourceLocation("forge:foods/meat/raw"));
	
	public static final TagKey<Item> ZOMBIE_SPAWN_WEAPONS = ItemTags.create(new ResourceLocation(SpartanWeaponryAPI.MOD_ID + ":zombie_spawn_weapons"));
	public static final TagKey<Item> SKELETON_SPAWN_LONGBOWS = ItemTags.create(new ResourceLocation(SpartanWeaponryAPI.MOD_ID + ":skeleton_spawn_longbows"));
}
