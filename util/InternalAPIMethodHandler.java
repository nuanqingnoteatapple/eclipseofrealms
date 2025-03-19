package com.oblivioussp.spartanweaponry.util;

import com.oblivioussp.spartanweaponry.api.IInternalMethodHandler;
import com.oblivioussp.spartanweaponry.api.WeaponMaterial;

import net.minecraft.world.item.Item;

public class InternalAPIMethodHandler implements IInternalMethodHandler
{
	//---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ----
	// Weapon Creation functions
	//---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- ----

	@Override
	public Item addDagger(WeaponMaterial material)
	{
		return WeaponFactory.DAGGER.create(material, new Item.Properties());
	}
	
	@Override
	public Item addParryingDagger(WeaponMaterial material) 
	{
		return WeaponFactory.PARRYING_DAGGER.create(material, new Item.Properties());
	}

	@Override
	public Item addGreatsword(WeaponMaterial material) 
	{
		return WeaponFactory.GREATSWORD.create(material, new Item.Properties());
	}

	@Override
	public Item addSpear(WeaponMaterial material)
	{
		return WeaponFactory.SPEAR.create(material, new Item.Properties());
	}


	@Override
	public Item addLance(WeaponMaterial material)
	{
		return WeaponFactory.LANCE.create(material, new Item.Properties());
	}

	@Override
	public Item addLongbow(WeaponMaterial material)
	{
		return WeaponFactory.LONGBOW.create(material, new Item.Properties());
	}

	@Override
	public Item addHeavyCrossbow(WeaponMaterial material) 
	{
		return WeaponFactory.HEAVY_CROSSBOW.create(material, new Item.Properties());
	}

	@Override
	public Item addGlaive(WeaponMaterial material)
	{
		return WeaponFactory.GLAIVE.create(material, new Item.Properties());
	}

	@Override
	public Item addQuarterstaff(WeaponMaterial material)
	{
		return WeaponFactory.QUARTERSTAFF.create(material, new Item.Properties());
	}

	@Override
	public Item addScythe(WeaponMaterial material)
	{
		return WeaponFactory.SCYTHE.create(material, new Item.Properties());
	}

}
