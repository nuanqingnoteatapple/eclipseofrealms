package com.oblivioussp.spartanweaponry.util;

import java.util.Arrays;
import java.util.List;

public class Defaults
{
	// Default config options
	
	// Dagger
	public static final float SpeedDagger = 2.5f;
	public static final float DamageBaseDagger = 2.5f;
	public static final float DamageMultiplierDagger = 1.0f;
	// Parrying Dagger
	public static final float SpeedParryingDagger = 2.5f;
	public static final float DamageBaseParryingDagger = 2.5f;
	public static final float DamageMultiplierParryingDagger = 1.0f;

	// Greatsword
	public static final float SpeedGreatsword = 1.4f;
	public static final float DamageBaseGreatsword = 4.0f;
	public static final float DamageMultiplierGreatsword = 1.5f;

	// Spear
	public static final float SpeedSpear = 1.4f;
	public static final float DamageBaseSpear = 5.5f;
	public static final float DamageMultiplierSpear = 0.5f;
	
	// Lance
	public static final float SpeedLance = 1.0f;
	public static final float DamageBaseLance = 4.0f;
	public static final float DamageMultiplierLance = 1.0f;
	
	// Longbow
	public static final float MultiplierLongbow = 1.25f;
	
	// Crossbow
	public static final float CrossbowInaccuracyMax = 10.0f;		// Formerly 20
	public static final int CrossbowInaccuracyTicks = 10;			// Used to take 20 ticks for minimalise inaccuracy
	public static final int CrossbowTicksToLoad = 25;
	//public static final int CrossbowTicksCooldown = 8;
	public static final float BaseDamageBolt = 4.0f;
	public static final float RangeMultiplierBolt = 1.0f;
	public static final float ArmorPiercingFactorBolt = 0.25f;
	public static final float BaseDamageBoltDiamond = 5.0f;
	public static final float RangeMultiplierBoltDiamond = 1.0f;
	public static final float ArmorPiercingFactorBoltDiamond = 0.5f;
	public static final float BaseDamageBoltNetherite = 5.5f;
	public static final float RangeMultiplierBoltNetherite = 1.0f;
	public static final float ArmorPiercingFactorBoltNetherite = 0.5f;

	// Glaives
	public static final float SpeedGlaive = 1.0f;
	public static final float DamageBaseGlaive = 4.0f;
	public static final float DamageMultiplierGlaive = 1.5f;
	
	// Quarterstaves
	public static final float SpeedQuarterstaff = 1.4f;
	public static final float DamageBaseQuarterstaff = 3.0f;
	public static final float DamageMultiplierQuarterstaff = 1.5f;
	
	// Scythes
	public static final float SpeedScythe = 1.0f;
	public static final float DamageBaseScythe = 5.0f;
	public static final float DamageMultiplierScythe = 1.0f;
	
	// Arrows & Quivers
	public static final List<String> QuiverArrowBlacklist = Arrays.asList("botania:crystal_bow", "iceandfire:dragonbone_bow");
//	public static final String[] QuiverArrowBlacklist = {"botania:crystal_bow", "iceandfire:dragonbone_bow"};
	public static final float BaseDamageArrowWood = 0.2f;
	public static final float RangeMultiplierArrowWood = 1.5f;
	public static final float BaseDamageArrowIron = 3.0f;
	public static final float RangeMultiplierArrowIron = 0.75f;
	public static final float BaseDamageArrowDiamond = 3.5f;
	public static final float RangeMultiplierArrowDiamond = 1.25f;
	public static final float BaseDamageArrowNetherite = 4.0f;
	public static final float RangeMultiplierArrowNetherite = 1.25f;

	// Weapon Trait related
	public static final float DamageBonusChestMultiplier = 2.0f;
	public static final float DamageBonusHeadMultiplier = 1.5f;
	public static final float DamageBonusRidingMultiplier = 2.0f;
	public static final float DamageBonusThrowMultiplier = 2.0f;
	public static final float DamageBonusThrowJavelinMultiplier = 3.0f;
	public static final float DamageBonusUnarmoredMultiplier = 3.0f;
	public static final float DamageBonusUndeadMultiplier = 1.5f;
	public static final float DamageBonusBackstabMultiplier = 3.0f;
	public static final float DamageBonusMaxArmorValue = 0.0f;
	public static final float DamageAbsorptionFactor = 0.25f;
	public static final float Reach1Value = 6.0f;
	public static final float Reach2Value = 7.0f;
	public static final float Sweep2Percentage = 0.5f;
	public static final float Sweep3Percentage = 1.0f;
	public static final float ArmorPiercePercentage = 50.0f;
	public static final float DecapitateSkullDropPercentage = 25.0f;

	// Loot Options
	public static final float zombieWithMeleeSpawnChanceNormal = 0.05f;
	public static final float zombieWithMeleeSpawnChanceHard = 0.25f;
	public static final float skeletonWithLongbowSpawnChanceNormal = 0.05f;
	public static final float skeletonWithLongbowSpawnChanceHard = 0.25f;

	
	// Client Options
	public static final int DefaultQuiverHudOffsetX = -138;
	public static final int DefaultQuiverHudOffsetY = 65;
	public static final int DefaultOilUsesHudOffsetX = 0;
	public static final int DefaultOilUsesHudOffsetY = 0;
}
