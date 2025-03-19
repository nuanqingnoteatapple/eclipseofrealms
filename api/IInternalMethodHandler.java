package com.oblivioussp.spartanweaponry.api;

import net.minecraft.world.item.Item;

/**
 * Basic Internal method handler interface. Do NOT create your own version of this. It is required for the API to work!
 * @author ObliviousSpartan
 *
 */
public interface IInternalMethodHandler 
{
	//---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ----
	// Weapon Creation functions
	//---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ----
	
	/**
	 * Creates a Dagger item while adding additional Weapon Properties derived from the weapon's material. Does *NOT* register the item. The addon author will have to do that.
	 * @param material The weapon material
	 * @return The newly created Dagger item
	 */
	public abstract Item addDagger(WeaponMaterial material);
	
	
	/**
	 * Creates a Parrying Dagger item while adding additional Weapon Properties derived from the weapon's material. Does *NOT* register the item. The addon author will have to do that.
	 * @param material The weapon material
	 * @return The newly created Dagger item
	 */
	public abstract Item addParryingDagger(WeaponMaterial material);

	public abstract Item addGreatsword(WeaponMaterial material);

	public abstract Item addSpear(WeaponMaterial material);


	public abstract Item addLance(WeaponMaterial material);
	
	/**
	 * Creates a Longbow item while adding additional Weapon Properties derived from the weapon's material. Does *NOT* register the item. The addon author will have to do that.
	 * @param material The weapon material
	 * @return The newly created Longbow item
	 */
	public abstract Item addLongbow(WeaponMaterial material);
	
	/**
	 * Creates a Heavy Crossbow item while adding additional Weapon Properties derived from the weapon's material. Does *NOT* register the item. The addon author will have to do that.
	 * @param material The weapon material
	 * @return The newly created Heavy Crossbow item
	 */
	public abstract Item addHeavyCrossbow(WeaponMaterial material);


	public abstract Item addGlaive(WeaponMaterial material);

	/**
	 * Creates a Quarterstaff item while adding additional Weapon Properties derived from the weapon's material. Does *NOT* register the item. The addon author will have to do that.
	 * @param material The weapon material
	 * @return The newly created Mace item
	 */
	public abstract Item addQuarterstaff(WeaponMaterial material);

	/**
	 * Creates a Scythe item while adding additional Weapon Properties derived from the weapon's material. Does *NOT* register the item. The addon author will have to do that.
	 * @param material The weapon material
	 * @return The newly created Mace item
	 */
	public abstract Item addScythe(WeaponMaterial material);
}
