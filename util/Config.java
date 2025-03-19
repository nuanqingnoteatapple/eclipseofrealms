package com.oblivioussp.spartanweaponry.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.ImmutableList;
import com.oblivioussp.spartanweaponry.ModSpartanWeaponry;
import com.oblivioussp.spartanweaponry.api.APIConfigValues;
import com.oblivioussp.spartanweaponry.api.WeaponMaterial;
import com.oblivioussp.spartanweaponry.api.WeaponTraits;
import com.oblivioussp.spartanweaponry.init.ModItems;
import com.oblivioussp.spartanweaponry.merchant.villager.WeaponsmithTrades;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.RegistryManager;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
	public static final Config INSTANCE;
	public static final ForgeConfigSpec CONFIG_SPEC;
	
	protected final Predicate<Object> IS_VALID_RESOURCE_LOCATION = (entry) -> ResourceLocation.isValidResourceLocation(entry.toString());
	
	static
	{
		 final Pair<Config, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Config::new);
		 INSTANCE = specPair.getLeft();
		 CONFIG_SPEC = specPair.getRight();
	}
	
	// Weapon categories
	public WeaponCategory daggers, parryingDaggers, greatswords;
	public WeaponCategory spears,lances;
	public RangedWeaponCategory longbows, heavyCrossbows;
	public WeaponCategory glaives, quarterstaves;
	public WeaponCategory scythes;

	
	// Projectile settings
	public BooleanValue disableNewArrowRecipes, disableDiamondAmmoRecipes, disableNetheriteAmmoRecipes, disableQuiverRecipes;
	public ProjectileCategory arrowWood, arrowIron, arrowDiamond, arrowNetherite;
	public BoltCategory bolt, boltDiamond, boltNetherite;
	public ConfigValue<List<? extends String>> quiverBowBlacklist;
	
	// Loot settings
	public BooleanValue addIronWeaponsToVillageWeaponsmith, addBowAndCrossbowLootToVillageFletcher, addDiamondWeaponsToEndCity,
					disableSpawningZombieWithWeapon, disableSpawningSkeletonWithLongbow;
	public DoubleValue zombieWithMeleeSpawnChanceNormal, zombieWithMeleeSpawnChanceHard,
					skeletonWithLongbowSpawnChanceNormal, skeletonWithLongbowSpawnChanceHard;
	public BooleanValue disableNewHeadDrops;
	
	// Trading settings
	public BooleanValue disableVillagerTrading;
	
	// Trait settings
	public DoubleValue damageBonusChestMultiplier, damageBonusHeadMultiplier, damageBonusRidingMultiplier, //damageBonusRidingVelocityForMaxBonus,
						damageBonusThrowMultiplier, damageBonusThrowJavelinMultiplier,
						damageBonusUnarmoredMultiplier;
	public BooleanValue damageBonusCheckArmorValue;
	public DoubleValue damageBonusMaxArmorValue, 
						damageBonusUndeadMultiplier, damageBonusBackstabMultiplier,
						damageAbsorptionFactor, reach1Value, reach2Value,
						sweep2Percentage, sweep3Percentage, armorPiercePercentage,
						decapitateSkullDropPercentage;

	// JEI settings
	public BooleanValue forceShowDisabledItems;
	
	private Config(ForgeConfigSpec.Builder builder)
	{
		daggers = new WeaponCategory(builder, "dagger", "Daggers", Defaults.SpeedDagger, Defaults.DamageBaseDagger, Defaults.DamageMultiplierDagger);

		parryingDaggers = new WeaponCategory(builder, "parrying_dagger", "Parrying Daggers", Defaults.SpeedParryingDagger, Defaults.DamageBaseParryingDagger, Defaults.DamageMultiplierParryingDagger);

		greatswords = new WeaponCategory(builder, "greatsword", "Greatswords", Defaults.SpeedGreatsword, Defaults.DamageBaseGreatsword, Defaults.DamageMultiplierGreatsword);

		spears = new WeaponCategory(builder, "spear", "Spears", Defaults.SpeedSpear, Defaults.DamageBaseSpear, Defaults.DamageMultiplierSpear);

		lances = new WeaponCategory(builder, "lance", "Lances", Defaults.SpeedLance, Defaults.DamageBaseLance, Defaults.DamageMultiplierLance);

		longbows = new RangedWeaponCategory(builder, "longbow", "Longbows");

		heavyCrossbows = new RangedWeaponCategory(builder, "heavy_crossbow", "Heavy Crossbows");


		glaives = new WeaponCategory(builder, "glaive", "Glaives", Defaults.SpeedGlaive, Defaults.DamageBaseGlaive, Defaults.DamageMultiplierGlaive);

		quarterstaves = new WeaponCategory(builder, "quarterstaff", "Quarterstaves", Defaults.SpeedQuarterstaff, Defaults.DamageBaseQuarterstaff, Defaults.DamageMultiplierQuarterstaff);

		scythes = new WeaponCategory(builder, "scythes", "Scythes", Defaults.SpeedScythe, Defaults.DamageBaseScythe, Defaults.DamageMultiplierScythe);

		

		
		builder.push("projectiles");
			disableNewArrowRecipes = builder.comment("Disables Recipes for all new Arrows.")
								.translation("config." + ModSpartanWeaponry.ID + ".projectile.disable_new_arrow_recipes")
								.worldRestart()
								.define("disable_new_arrow_recipes", false);
			disableDiamondAmmoRecipes = builder.comment("Disables Recipes for both Diamond Arrows and Diamond Bolts.")
								.translation("config." + ModSpartanWeaponry.ID + ".projectile.disable_diamond_ammo_recipes")
								.worldRestart()
								.define("disable_diamond_ammo_recipes", false);
			disableNetheriteAmmoRecipes = builder.comment("Disables Recipes for both Netherite Arrows and Netherite Bolts.")
					.translation("config." + ModSpartanWeaponry.ID + ".projectile.disable_netherite_ammo_recipes")
					.worldRestart()
					.define("disable_netherite_ammo_recipes", false);
			disableQuiverRecipes = builder.comment("Disables all variants of the Arrow Quiver and the Bolt Quiver in this mod")
								.translation("config." + ModSpartanWeaponry.ID + ".projectile.disable_quiver_recipes")
								.worldRestart()
								.define("disable_quiver_recipes", false);
		quiverBowBlacklist = builder.comment("List of bow item IDs that are blacklisted from being used with quivers")
				.translation("config." + ModSpartanWeaponry.ID + ".projectile.quiver_bow_blacklist")
				.worldRestart()
				.<String>defineListAllowEmpty(
						ImmutableList.of("quiver_bow_blacklist"),
						() -> new ArrayList<>(),
						IS_VALID_RESOURCE_LOCATION
				);

		arrowWood = new ProjectileCategory(builder, "wood", "arrow", Defaults.BaseDamageArrowWood, Defaults.RangeMultiplierArrowWood);
			
			arrowWood = new ProjectileCategory(builder, "wood", "arrow", Defaults.BaseDamageArrowWood, Defaults.RangeMultiplierArrowWood);
			arrowIron = new ProjectileCategory(builder, "iron", "arrow", Defaults.BaseDamageArrowIron, Defaults.RangeMultiplierArrowIron);
			arrowDiamond = new ProjectileCategory(builder, "diamond", "arrow", Defaults.BaseDamageArrowDiamond, Defaults.RangeMultiplierArrowDiamond);
			arrowNetherite = new ProjectileCategory(builder, "netherite", "arrow", Defaults.BaseDamageArrowNetherite, Defaults.RangeMultiplierArrowNetherite);
			bolt = new BoltCategory(builder, "", "bolt", Defaults.BaseDamageBolt, Defaults.RangeMultiplierBolt, Defaults.ArmorPiercingFactorBolt);
			boltDiamond = new BoltCategory(builder, "diamond", "bolt", Defaults.BaseDamageBoltDiamond, Defaults.RangeMultiplierBoltDiamond, Defaults.ArmorPiercingFactorBoltDiamond);
			boltNetherite = new BoltCategory(builder, "netherite", "bolt", Defaults.BaseDamageBoltNetherite, Defaults.RangeMultiplierBoltNetherite, Defaults.ArmorPiercingFactorBoltNetherite);

		builder.pop();
		
		builder.push("loot");
			addIronWeaponsToVillageWeaponsmith = builder.comment("Set to false to disable spawning Iron Weapons in Village Weaponsmith chests via loot table injection")
								.translation("config." + ModSpartanWeaponry.ID + ".loot.add_iron_weapons_to_village_blacksmith")
								.worldRestart()
								.define("add_iron_weapons_to_village_blacksmith", true);
			addBowAndCrossbowLootToVillageFletcher = builder.comment("Set to false to disable spawning Longbow and Heavy Crossbow-related loot in Village Fletcher chests via loot table injection")
					.translation("config." + ModSpartanWeaponry.ID + ".loot.add_bow_and_crossbow_loot_to_village_fletcher")
					.worldRestart()
					.define("add_bow_and_crossbow_loot_to_village_fletcher", true);
			addDiamondWeaponsToEndCity = builder.comment("Set to false to disable spawning Diamond Weapons in End City chests via loot table injection")
								.translation("config." + ModSpartanWeaponry.ID + ".loot.add_diamond_weapons_to_end_city")
								.worldRestart()
								.define("add_diamond_weapons_to_end_city", true);
			zombieWithMeleeSpawnChanceNormal = builder.comment("Chance for Zombies to spawn with Iron Melee Weapons on all difficulties apart from Hard and Hardcore")
								.translation("config." + ModSpartanWeaponry.ID + ".loot.zombie_with_melee_spawn_chance_normal")
								.defineInRange("zombie_with_melee_spawn_chance_normal", Defaults.zombieWithMeleeSpawnChanceNormal, 0.0, 1.0);
			zombieWithMeleeSpawnChanceHard = builder.comment("Chance for Zombies to spawn with Iron Melee Weapons on Hard or Hardcore difficulty")
								.translation("config." + ModSpartanWeaponry.ID + ".loot.zombie_with_melee_spawn_chance_hard")
								.defineInRange("zombie_with_melee_spawn_chance_hard", Defaults.zombieWithMeleeSpawnChanceHard, 0.0, 1.0);
			disableSpawningZombieWithWeapon = builder.comment("Set to true to disable spawning a Zombie with any weapons from this mod")
								.translation("config." + ModSpartanWeaponry.ID + ".loot.disable_spawning_zombie_with_weapon")
								.define("disable_spawning_zombie_with_weapon", false);
			skeletonWithLongbowSpawnChanceNormal = builder.comment("Chance for Skeletons to spawn with various Longbows on all difficulties apart from Hard and Hardcore")
								.translation("config." + ModSpartanWeaponry.ID + ".loot.skeleton_with_longbow_spawn_chance_normal")
								.defineInRange("skeleton_with_longbow_spawn_chance_normal", Defaults.skeletonWithLongbowSpawnChanceNormal, 0.0, 1.0);
			skeletonWithLongbowSpawnChanceHard = builder.comment("Chance for Skeletons to spawn with various Longbows on Hard or Hardcore difficulty")
								.translation("config." + ModSpartanWeaponry.ID + ".loot.skeleton_with_longbow_spawn_chance_hard")
								.defineInRange("skeleton_with_longbow_spawn_chance_hard", Defaults.skeletonWithLongbowSpawnChanceHard, 0.0, 1.0);
			disableSpawningSkeletonWithLongbow = builder.comment("Set to true to disable spawning a Skeleton with any Longbow from this mod")
					.translation("config." + ModSpartanWeaponry.ID + ".loot.disable_spawning_skeleton_with_longbow")
					.define("disable_spawning_skeleton_with_longbow", false);
			disableNewHeadDrops = builder.comment("Set to true to disable the new mob heads from being dropped from mobs using the Decapitate Weapon Trait from this mod.")
								.translation("config." + ModSpartanWeaponry.ID + ".loot.disable_new_head_drops")
								.define("disable_new_head_drops", false);
		builder.pop();
		
		builder.push("trading");
			disableVillagerTrading = builder.comment("Set to true to disable Villagers (Weaponsmiths and Fletchers) from trading weapons from this mod")
								.translation("config." + ModSpartanWeaponry.ID + ".trading.disabled")
								.define("disable", false);
		builder.pop();
		
		builder.push("traits");
			builder.push("damage_bonus");
				damageBonusChestMultiplier = builder.comment("Changes the \"Chest Damage Bonus\" Weapon Trait multiplier value")
									.translation("config." + ModSpartanWeaponry.ID + ".traits.damage_bonus.chest_multiplier")
									.defineInRange("chest_multiplier", Defaults.DamageBonusChestMultiplier, 1.0, 50.0);
				damageBonusHeadMultiplier = builder.comment("Changes the \"Head Damage Bonus\" Weapon Trait multiplier value")
						.translation("config." + ModSpartanWeaponry.ID + ".traits.damage_bonus.head_multiplier")
						.defineInRange("head_multiplier", Defaults.DamageBonusHeadMultiplier, 1.0, 50.0);
				damageBonusRidingMultiplier = builder.comment("Changes the \"Riding Damage Bonus\" Weapon Trait multiplier value")
						.translation("config." + ModSpartanWeaponry.ID + ".traits.damage_bonus.riding_multiplier")
						.defineInRange("riding_multiplier", Defaults.DamageBonusRidingMultiplier, 1.0, 50.0);
/*				damageBonusRidingVelocityForMaxBonus = builder.comment("Velocity required for the \"Riding Damage Bonus\" Weapon Trait to award the max bonus")
						.translation("config." + ModSpartanWeaponry.ID + ".traits.damage_bonus.riding_velocity_for_max_bonus")
						.defineInRange("riding_velocity_for_max_bonus", Defaults.DamageBonusRidingVelocityMax, 0.0, 10.0);*/
				damageBonusThrowMultiplier = builder.comment("Changes the \"Throwing Damage Bonus\" Weapon Trait multiplier value")
						.translation("config." + ModSpartanWeaponry.ID + ".traits.damage_bonus.throw_multiplier")
						.defineInRange("throw_multiplier", Defaults.DamageBonusThrowMultiplier, 1.0, 50.0);
				damageBonusThrowJavelinMultiplier = builder.comment("Changes the \"Chest Damage Bonus\" Weapon Trait multiplier value")
						.translation("config." + ModSpartanWeaponry.ID + ".traits.damage_bonus.throw_javelin_multiplier")
						.defineInRange("throw_javelin_multiplier", Defaults.DamageBonusThrowJavelinMultiplier, 1.0, 50.0);
				damageBonusUnarmoredMultiplier = builder.comment("Changes the \"Unarmored Damage Bonus\" Weapon Trait multiplier value")
						.translation("config." + ModSpartanWeaponry.ID + ".traits.damage_bonus.unarmored_multiplier")
						.defineInRange("unarmored_multiplier", Defaults.DamageBonusUnarmoredMultiplier, 1.0, 50.0);
				damageBonusCheckArmorValue = builder.comment("If set to true, any damage bonus that checks for armor will only apply if the hit mob has less than the total armor value threshold, while still checking for armor")
						.translation("config." + ModSpartanWeaponry.ID + ".traits.damage_bonus.check_armor_value")
						.define("check_armor_value", false);
				damageBonusMaxArmorValue = builder.comment("Max armor value allowed for any damage bonus that checks for armor to apply, without any armor equipped")
						.translation("config." + ModSpartanWeaponry.ID + ".traits.damage_bonus.max_armor_value")
						.defineInRange("max_armor_value", Defaults.DamageBonusMaxArmorValue, 1.0, 50.0);
				damageBonusUndeadMultiplier = builder.comment("Changes the \"Undead Damage Bonus\" Weapon Trait multiplier value")
						.translation("config." + ModSpartanWeaponry.ID + ".traits.damage_bonus.undead_multiplier")
						.defineInRange("undead_multiplier", Defaults.DamageBonusUndeadMultiplier, 1.0, 50.0);
				damageBonusBackstabMultiplier = builder.comment("Changes the \"Backstab Damage Bonus\" Weapon Trait multiplier value")
						.translation("config." + ModSpartanWeaponry.ID + ".traits.damage_bonus.backstab_multiplier")
						.defineInRange("backstab_multiplier", Defaults.DamageBonusBackstabMultiplier, 1.0, 50.0);
			builder.pop();
			builder.push("damage_absorption");
				damageAbsorptionFactor = builder.comment("Changes the percentage of damage absorbed by the \"Damage Absorption\" Weapon Trait")
						.translation("config." + ModSpartanWeaponry.ID + ".traits.damage_absorption_factor")
						.defineInRange("damage_absorption_factor", Defaults.DamageAbsorptionFactor, 0.0, 1.0);
			builder.pop();
			builder.push("reach");
				reach1Value = builder.comment("Changes the reach of any weapons with the \"Reach I\" Weapon Trait")
						.translation("config." + ModSpartanWeaponry.ID + ".traits.reach1.value")
						.defineInRange("reach1_value", Defaults.Reach1Value, 5.0, 15.0);
				reach2Value = builder.comment("Changes the reach of any weapons with the \"Reach II\" Weapon Trait")
						.translation("config." + ModSpartanWeaponry.ID + ".traits.reach2.value")
						.defineInRange("reach2_value", Defaults.Reach2Value, 5.0, 15.0);
			builder.pop();
			builder.push("sweep");
				sweep2Percentage = builder.comment("Changes the factor of damage inflicted to enemies when sweep attacked on weapons with the \"Sweep II\" Weapon Trait")
						.translation("config." + ModSpartanWeaponry.ID + ".traits.sweep2.percentage")
						.defineInRange("sweep2_percentage", Defaults.Sweep2Percentage, 0.0, 1.0);
				sweep3Percentage = builder.comment("Changes the factor of damage inflicted to enemies when sweep attacked on weapons with the \"Sweep III\" Weapon Trait")
						.translation("config." + ModSpartanWeaponry.ID + ".traits.sweep3.percentage")
						.defineInRange("sweep3_percentage", Defaults.Sweep3Percentage, 0.0, 1.0);
			builder.pop();
			builder.push("armor_pierce");
				armorPiercePercentage = builder.comment("Changes the percentage of damage that ignores armor on weapons with the \"Armor Piercing\" Weapon Trait")
						.translation("config." + ModSpartanWeaponry.ID + ".traits.armor_pierce.percentage")
						.defineInRange("percentage", Defaults.ArmorPiercePercentage, 0.0, 100.0);

			builder.push("decapitate");
				decapitateSkullDropPercentage = builder.comment("Tweaks the percentage of Skull drops from weapons with the \"Decapitate\" Weapon Trait")
						.translation("config." + ModSpartanWeaponry.ID + ".traits.decapitate.skull_drop_percentage")
						.defineInRange("skull_drop_percentage", Defaults.DecapitateSkullDropPercentage, 0.0, 100.0);
			builder.pop();
		builder.pop();
		
		builder.push("jei");
			forceShowDisabledItems = builder.comment("Set to true to forcibly show disabled items in JEI, even if they cannot be crafted. Should be useful for modpack makers defining their own recipes.")
					.translation("config." + ModSpartanWeaponry.ID + ".jei.force_show_disabled_items")
					.worldRestart()
					.define("force_show_disabled_items", false);
		builder.pop();
	}
	
	@SubscribeEvent
	public static void onConfigLoad(ModConfigEvent ev)
	{
		if(ev.getConfig().getSpec() != CONFIG_SPEC)
			return;
		
		ModItems.DAGGERS.updateSettingsFromConfig(INSTANCE.daggers.baseDamage.get().floatValue(), INSTANCE.daggers.damageMultipler.get().floatValue(), INSTANCE.daggers.speed.get().doubleValue());

		ModItems.PARRYING_DAGGERS.updateSettingsFromConfig(INSTANCE.parryingDaggers.baseDamage.get().floatValue(), INSTANCE.parryingDaggers.damageMultipler.get().floatValue(), INSTANCE.parryingDaggers.speed.get().doubleValue());

		ModItems.GREATSWORDS.updateSettingsFromConfig(INSTANCE.greatswords.baseDamage.get().floatValue(), INSTANCE.greatswords.damageMultipler.get().floatValue(), INSTANCE.greatswords.speed.get().doubleValue());

		ModItems.SPEARS.updateSettingsFromConfig(INSTANCE.spears.baseDamage.get().floatValue(), INSTANCE.spears.damageMultipler.get().floatValue(), INSTANCE.spears.speed.get().doubleValue());

		ModItems.LANCES.updateSettingsFromConfig(INSTANCE.lances.baseDamage.get().floatValue(), INSTANCE.lances.damageMultipler.get().floatValue(), INSTANCE.lances.speed.get().doubleValue());
		
		ModItems.GLAIVES.updateSettingsFromConfig(INSTANCE.glaives.baseDamage.get().floatValue(), INSTANCE.glaives.damageMultipler.get().floatValue(), INSTANCE.glaives.speed.get().doubleValue());

		ModItems.QUARTERSTAVES.updateSettingsFromConfig(INSTANCE.quarterstaves.baseDamage.get().floatValue(), INSTANCE.quarterstaves.damageMultipler.get().floatValue(), INSTANCE.quarterstaves.speed.get().doubleValue());
		
		ModItems.SCYTHES.updateSettingsFromConfig(INSTANCE.scythes.baseDamage.get().floatValue(), INSTANCE.scythes.damageMultipler.get().floatValue(), INSTANCE.scythes.speed.get().doubleValue());

		ModItems.WOODEN_ARROW.get().updateFromConfig(INSTANCE.arrowWood.baseDamage.get().floatValue(), INSTANCE.arrowWood.rangeMultiplier.get().floatValue());

		ModItems.TIPPED_WOODEN_ARROW.get().updateFromConfig(INSTANCE.arrowWood.baseDamage.get().floatValue(), INSTANCE.arrowWood.rangeMultiplier.get().floatValue());

		//arrow
		ModItems.IRON_ARROW.get().updateFromConfig(INSTANCE.arrowIron.baseDamage.get().floatValue(), INSTANCE.arrowIron.rangeMultiplier.get().floatValue());

		ModItems.TIPPED_IRON_ARROW.get().updateFromConfig(INSTANCE.arrowIron.baseDamage.get().floatValue(), INSTANCE.arrowIron.rangeMultiplier.get().floatValue());

		ModItems.DIAMOND_ARROW.get().updateFromConfig(INSTANCE.arrowDiamond.baseDamage.get().floatValue(), INSTANCE.arrowDiamond.rangeMultiplier.get().floatValue());

		ModItems.TIPPED_DIAMOND_ARROW.get().updateFromConfig(INSTANCE.arrowDiamond.baseDamage.get().floatValue(), INSTANCE.arrowDiamond.rangeMultiplier.get().floatValue());

		ModItems.NETHERITE_ARROW.get().updateFromConfig(INSTANCE.arrowNetherite.baseDamage.get().floatValue(), INSTANCE.arrowNetherite.rangeMultiplier.get().floatValue());

		ModItems.TIPPED_NETHERITE_ARROW.get().updateFromConfig(INSTANCE.arrowNetherite.baseDamage.get().floatValue(), INSTANCE.arrowNetherite.rangeMultiplier.get().floatValue());

		//bolt
		ModItems.BOLT.get().updateFromConfig(INSTANCE.bolt.baseDamage.get().floatValue(), INSTANCE.bolt.rangeMultiplier.get().floatValue(), INSTANCE.bolt.armorPiercingFactor.get().floatValue());

		ModItems.TIPPED_BOLT.get().updateFromConfig(INSTANCE.bolt.baseDamage.get().floatValue(), INSTANCE.bolt.rangeMultiplier.get().floatValue(), INSTANCE.bolt.armorPiercingFactor.get().floatValue());

		ModItems.SPECTRAL_BOLT.get().updateFromConfig(INSTANCE.bolt.baseDamage.get().floatValue(), INSTANCE.bolt.rangeMultiplier.get().floatValue(), INSTANCE.bolt.armorPiercingFactor.get().floatValue());

		ModItems.DIAMOND_BOLT.get().updateFromConfig(INSTANCE.boltDiamond.baseDamage.get().floatValue(), INSTANCE.boltDiamond.rangeMultiplier.get().floatValue(), INSTANCE.boltDiamond.armorPiercingFactor.get().floatValue());

		ModItems.TIPPED_DIAMOND_BOLT.get().updateFromConfig(INSTANCE.boltDiamond.baseDamage.get().floatValue(), INSTANCE.boltDiamond.rangeMultiplier.get().floatValue(), INSTANCE.boltDiamond.armorPiercingFactor.get().floatValue());

		ModItems.NETHERITE_BOLT.get().updateFromConfig(INSTANCE.boltNetherite.baseDamage.get().floatValue(), INSTANCE.boltNetherite.rangeMultiplier.get().floatValue(), INSTANCE.boltNetherite.armorPiercingFactor.get().floatValue());

		ModItems.TIPPED_NETHERITE_BOLT.get().updateFromConfig(INSTANCE.boltNetherite.baseDamage.get().floatValue(), INSTANCE.boltNetherite.rangeMultiplier.get().floatValue(), INSTANCE.boltNetherite.armorPiercingFactor.get().floatValue());

		
		// Update Weapon Traits
		WeaponTraits.DAMAGE_BONUS_CHEST.get().setMagnitude(INSTANCE.damageBonusChestMultiplier.get().floatValue());
		WeaponTraits.DAMAGE_BONUS_RIDING.get().setMagnitude(INSTANCE.damageBonusRidingMultiplier.get().floatValue());
		WeaponTraits.DAMAGE_BONUS_THROWN_1.get().setMagnitude(INSTANCE.damageBonusThrowMultiplier.get().floatValue());
		WeaponTraits.DAMAGE_BONUS_THROWN_2.get().setMagnitude(INSTANCE.damageBonusThrowJavelinMultiplier.get().floatValue());
		WeaponTraits.DAMAGE_BONUS_UNARMOURED.get().setMagnitude(INSTANCE.damageBonusUnarmoredMultiplier.get().floatValue());
		WeaponTraits.DAMAGE_BONUS_UNDEAD.get().setMagnitude(INSTANCE.damageBonusUndeadMultiplier.get().floatValue());
		WeaponTraits.DAMAGE_BONUS_BACKSTAB.get().setMagnitude(INSTANCE.damageBonusBackstabMultiplier.get().floatValue());
		WeaponTraits.DAMAGE_ABSORB.get().setMagnitude(INSTANCE.damageAbsorptionFactor.get().floatValue());
		WeaponTraits.REACH_1.get().setMagnitude(INSTANCE.reach1Value.get().floatValue());
		WeaponTraits.REACH_2.get().setMagnitude(INSTANCE.reach2Value.get().floatValue());
		WeaponTraits.SWEEP_2.get().setMagnitude(INSTANCE.sweep2Percentage.get().floatValue());
		WeaponTraits.SWEEP_3.get().setMagnitude(INSTANCE.sweep3Percentage.get().floatValue());
		WeaponTraits.ARMOUR_PIERCING.get().setMagnitude(INSTANCE.armorPiercePercentage.get().floatValue());
		WeaponTraits.DECAPITATE.get().setMagnitude(INSTANCE.decapitateSkullDropPercentage.get().floatValue());

		
		// Update values required API-side
		APIConfigValues.damageBonusCheckArmorValue = INSTANCE.damageBonusCheckArmorValue.get();
		APIConfigValues.damageBonusMaxArmorValue = INSTANCE.damageBonusMaxArmorValue.get().floatValue();
		
		WeaponsmithTrades.initTradeLists();

	}
	
	private static void updateMaterialValues(WeaponMaterial material, float baseDamage, int durability)
	{
		material.setAttackDamage(baseDamage);
		material.setDurability(durability);
	}
	

	
	public class WeaponCategory
	{
		public BooleanValue disableRecipes;
		public DoubleValue speed;
		public DoubleValue baseDamage;
		public DoubleValue damageMultipler;
		
		protected WeaponCategory(ForgeConfigSpec.Builder builder, String weaponClass, String weaponPlural, float defaultSpeed, float defaultBaseDamage, float defaultDamageMuliplier)
		{
			builder.push(weaponClass);
			disableRecipes = builder.comment("Disables all recipes for all " + weaponPlural + ".")
					.translation("config." + ModSpartanWeaponry.ID + ".weapon.disable")
					.worldRestart()
					.define("disable", false);
			speed = builder.comment("Attack speed of " + weaponPlural + ".")
					.translation("config." + ModSpartanWeaponry.ID + ".weapon.speed")
					.defineInRange("speed", defaultSpeed, 0.0d, 4.0d);
			baseDamage = builder.comment("Base Damage of " + weaponPlural + ".")
					.translation("config." + ModSpartanWeaponry.ID + ".weapon.base_damage")
					.defineInRange("base_damage", defaultBaseDamage, 0.1d, 100.0d);
			damageMultipler = builder.comment("Damage Multiplier for " + weaponPlural + ".")
					.translation("config." + ModSpartanWeaponry.ID + ".weapon.damage_multiplier")
					.defineInRange("damage_multiplier", defaultDamageMuliplier, 0.1d, 10.0d);
			builder.pop();
		}

	}
	
	public class RangedWeaponCategory
	{
		public BooleanValue disableRecipes;
		private String typeDisabledName;
		
		protected RangedWeaponCategory(ForgeConfigSpec.Builder builder, String weaponClass, String weaponPlural)
		{
			builder.push(weaponClass);
			disableRecipes = builder.comment("Disables all recipes for all " + weaponPlural + ".")
					.translation("config." + ModSpartanWeaponry.ID + ".weapon.disable")
					.worldRestart()
					.define("disable", false);
			builder.pop();
		}

	}
	
	public class ThrowingWeaponCategory
	{
		public BooleanValue disableRecipes;
		public DoubleValue speed;
		public DoubleValue baseDamage;
		public DoubleValue damageMultipler;
		public IntValue chargeTicks;
		private String typeDisabledName;
		
		protected ThrowingWeaponCategory(ForgeConfigSpec.Builder builder, String weaponClass, String weaponPlural, float defaultSpeed, float defaultBaseDamage, float defaultDamageMuliplier, int defaultChargeTicks, String typeDisabledNameIn)
		{
			builder.push(weaponClass);
			typeDisabledName = typeDisabledNameIn;
			disableRecipes = builder.comment("Disables all recipes for all " + weaponPlural + ".")
					.translation("config." + ModSpartanWeaponry.ID + ".weapon.disable")
					.worldRestart()
					.define("disable", false);
			speed = builder.comment("Attack speed of " + weaponPlural + ".")
					.translation("config." + ModSpartanWeaponry.ID + ".weapon.speed")
					.defineInRange("speed", defaultSpeed, 0.0d, 4.0d);
			baseDamage = builder.comment("Base Damage of " + weaponPlural + ".")
					.translation("config." + ModSpartanWeaponry.ID + ".weapon.base_damage")
					.defineInRange("base_damage", defaultBaseDamage, 0.1d, 100.0d);
			damageMultipler = builder.comment("Damage Multiplier for " + weaponPlural + ".")
					.translation("config." + ModSpartanWeaponry.ID + ".weapon.damage_multiplier")
					.defineInRange("damage_multiplier", defaultDamageMuliplier, 0.1d, 10.0d);
			chargeTicks = builder.comment("Charge time in ticks for " + weaponPlural + ".")
					.translation("config." + ModSpartanWeaponry.ID + ".weapon.charge_ticks")
					.defineInRange("charge_ticks", defaultChargeTicks, 1, 1000);
			builder.pop();
		}
	}
	
	public class MaterialCategory
	{
		public DoubleValue damage;
		public IntValue durability;
		public BooleanValue disableRecipes;
		private String materialName;
		private String typeDisabledName;
		
		private MaterialCategory(ForgeConfigSpec.Builder builder, String materialName, float damage, int durability, String typeDisabledName)
		{
			builder.push(materialName);
			this.materialName = materialName;
			this.typeDisabledName = typeDisabledName;
			this.damage = builder.comment("Base Damage for " + this.materialName + " weapons")
						.translation("config." + ModSpartanWeaponry.ID + ".material.base_damage")
						.defineInRange("base_damage", damage, 0.1d, 100.0d);
			this.durability = builder.comment("Durability for " + this.materialName + " weapons")
					.translation("config." + ModSpartanWeaponry.ID + ".material.durability")
					.defineInRange("durability", durability, 1, 100000);
			this.disableRecipes = builder.comment("Set to true to disable " + this.materialName + " weapons")
					.translation("config." + ModSpartanWeaponry.ID + ".material.disable")
					.worldRestart()
					.define("disable", false);
			builder.pop();
		}
	}
	
	public class ProjectileCategory
	{
		public DoubleValue baseDamage;
		public DoubleValue rangeMultiplier;
		
		private ProjectileCategory(ForgeConfigSpec.Builder builder, String materialName, String projectileName, float baseDamage, float rangeMultiplier)
		{
			String projName = materialName == null || materialName == "" ? projectileName : materialName + " " + projectileName;
			String category = materialName == null || materialName == "" ? projectileName : materialName + "_" + projectileName;
			builder.push(category);
			this.baseDamage = builder.comment("Base damage for " + projName + "s")
					.translation("config." + ModSpartanWeaponry.ID + ".arrow.base_damage")
					.defineInRange("base_damage", baseDamage, 0.1d, 100.0d);
			this.rangeMultiplier = builder.comment("Range muliplier for " + projName + "s")
					.translation("config." + ModSpartanWeaponry.ID + ".arrow.range_multiplier")
					.defineInRange("range_multiplier", rangeMultiplier, 0.1d, 100.0d);
			builder.pop();
		}
	}
	
	public class BoltCategory
	{
		public DoubleValue baseDamage;
		public DoubleValue rangeMultiplier;
		public DoubleValue armorPiercingFactor;
		
		protected BoltCategory(ForgeConfigSpec.Builder builder, String materialName, String projectileName, float baseDamage, float rangeMultiplier, float armorPiercingFactor)
		{
			String projName = materialName == null || materialName == "" ? projectileName : materialName + " " + projectileName;
			String category = materialName == null || materialName == "" ? projectileName : materialName + "_" + projectileName;
			builder.push(category);
			this.baseDamage = builder.comment("Base damage for " + projName + "s")
					.translation("config." + ModSpartanWeaponry.ID + ".arrow.base_damage")
					.defineInRange("base_damage", baseDamage, 0.1d, 100.0d);
			this.rangeMultiplier = builder.comment("Range muliplier for " + projName + "s")
				.translation("config." + ModSpartanWeaponry.ID + ".arrow.range_multiplier")
				.defineInRange("range_multiplier", rangeMultiplier, 0.1d, 100.0d);
			this.armorPiercingFactor = builder.comment("Armor Piercing factor for " + projName + "s")
					.translation("config." + ModSpartanWeaponry.ID + ".bolt.armor_piercing_factor")
					.defineInRange("armor_piercing_factor", armorPiercingFactor, 0.0d, 1.0d);
			builder.pop();
		}
	}
}
