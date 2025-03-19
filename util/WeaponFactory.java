package com.oblivioussp.spartanweaponry.util;

import com.oblivioussp.spartanweaponry.ModSpartanWeaponry;
import com.oblivioussp.spartanweaponry.api.WeaponMaterial;
import com.oblivioussp.spartanweaponry.item.HeavyCrossbowItem;
import com.oblivioussp.spartanweaponry.item.LongbowItem;
import com.oblivioussp.spartanweaponry.item.SwordBaseItem;
import com.oblivioussp.spartanweaponry.item.ThrowingWeaponItem;

import net.minecraft.world.item.Item;

public class WeaponFactory 
{
	/**
	 * A special functional interface to create Spartan Weaponry weapons with using the same parameters for each one
	 * @author ObliviousSpartan
	 *
	 * @param <T> An item type to be created using this function
	 */
	@FunctionalInterface
	public interface WeaponFunction<T extends Item>
	{
		public abstract T create(WeaponMaterial material, Item.Properties property);
	}
	
	public static final WeaponFunction<SwordBaseItem> DAGGER = (material, prop) -> {
		return new SwordBaseItem(prop, material, WeaponArchetype.DAGGER, Defaults.DamageBaseDagger, Defaults.DamageMultiplierDagger, Defaults.SpeedDagger, "item." + ModSpartanWeaponry.ID + ".custom_dagger");
	};

	public static final WeaponFunction<SwordBaseItem> PARRYING_DAGGER = (material, prop) -> {
		return new SwordBaseItem(prop, material, WeaponArchetype.PARRYING_DAGGER, Defaults.DamageBaseParryingDagger, Defaults.DamageMultiplierParryingDagger, Defaults.SpeedParryingDagger, "item." + ModSpartanWeaponry.ID + ".custom_parrying_dagger");
	};
		

	public static final WeaponFunction<SwordBaseItem> GREATSWORD = (material, prop) -> {
		return new SwordBaseItem(prop, material, WeaponArchetype.GREATSWORD, Defaults.DamageBaseGreatsword, Defaults.DamageMultiplierGreatsword, Defaults.SpeedGreatsword, "item." + ModSpartanWeaponry.ID + ".custom_greatsword");
	};
		

	public static final WeaponFunction<SwordBaseItem> SPEAR = (material, prop) -> {
		return new SwordBaseItem(prop, material, WeaponArchetype.SPEAR, Defaults.DamageBaseSpear, Defaults.DamageMultiplierSpear, Defaults.SpeedSpear, "item." + ModSpartanWeaponry.ID + ".custom_spear");
	};

	public static final WeaponFunction<SwordBaseItem> LANCE = (material, prop) -> {
		return new SwordBaseItem(prop, material, WeaponArchetype.LANCE, Defaults.DamageBaseLance, Defaults.DamageMultiplierLance, Defaults.SpeedLance, "item." + ModSpartanWeaponry.ID + ".custom_lance");
	};
		
	public static final WeaponFunction<LongbowItem> LONGBOW = (material, prop) -> {
		return new LongbowItem(prop, material, "item." + ModSpartanWeaponry.ID + ".custom_longbow");
	};
		
	public static final WeaponFunction<HeavyCrossbowItem> HEAVY_CROSSBOW = (material, prop) -> {
		return new HeavyCrossbowItem(prop, material, "item." + ModSpartanWeaponry.ID + ".custom_heavy_crossbow");
	};

	public static final WeaponFunction<SwordBaseItem> GLAIVE = (material, prop) -> {
		return new SwordBaseItem(prop, material, WeaponArchetype.GLAIVE, Defaults.DamageBaseGlaive, Defaults.DamageMultiplierGlaive, Defaults.SpeedGlaive, "item." + ModSpartanWeaponry.ID + ".custom_glaive");
	};
		
	public static final WeaponFunction<SwordBaseItem> QUARTERSTAFF = (material, prop) -> {
		return new SwordBaseItem(prop, material, WeaponArchetype.QUARTERSTAFF, Defaults.DamageBaseQuarterstaff, Defaults.DamageMultiplierQuarterstaff, Defaults.SpeedQuarterstaff, "item." + ModSpartanWeaponry.ID + ".custom_quarterstaff");
	};
		
	public static final WeaponFunction<SwordBaseItem> SCYTHE = (material, prop) -> {
		return new SwordBaseItem(prop, material, WeaponArchetype.SCYTHE, Defaults.DamageBaseScythe, Defaults.DamageMultiplierScythe, Defaults.SpeedScythe, "item." + ModSpartanWeaponry.ID + ".custom_scythe");
	};
}
