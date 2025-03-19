package com.oblivioussp.spartanweaponry.api.tags;

import com.oblivioussp.spartanweaponry.api.SpartanWeaponryAPI;
import com.oblivioussp.spartanweaponry.api.WeaponTraits;
import com.oblivioussp.spartanweaponry.api.trait.WeaponTrait;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

/**
 * This class contains all the different Weapon Trait tags used by Spartan Weaponry.
 * These are used to construct the Weapon Trait values of each weapon type when loading the world
 *  so certain traits can be disabled or changed via datapacks if desired<br>
 * @author ObliviousSpartan
 */
public class ModWeaponTraitTags 
{
	public static final TagKey<WeaponTrait> DAGGER = create("dagger");
	public static final TagKey<WeaponTrait> PARRYING_DAGGER = create("parrying_dagger");
	public static final TagKey<WeaponTrait> GREATSWORD = create("greatsword");
	public static final TagKey<WeaponTrait> SPEAR = create("spear");
	public static final TagKey<WeaponTrait> LANCE = create("lance");
	public static final TagKey<WeaponTrait> GLAIVE = create("glaive");
	public static final TagKey<WeaponTrait> QUARTERSTAFF = create("quarterstaff");
	public static final TagKey<WeaponTrait> SCYTHE = create("scythe");
	
	public static final TagKey<WeaponTrait> WOOD = create("materials/wood");
	public static final TagKey<WeaponTrait> STONE = create("materials/stone");
	public static final TagKey<WeaponTrait> LEATHER = create("materials/leather");
	public static final TagKey<WeaponTrait> IRON = create("materials/iron");
	public static final TagKey<WeaponTrait> GOLD = create("materials/gold");
	public static final TagKey<WeaponTrait> DIAMOND = create("materials/diamond");
	public static final TagKey<WeaponTrait> NETHERITE = create("materials/netherite");
	
	public static TagKey<WeaponTrait> create(ResourceLocation loc)
	{
		return WeaponTraits.REGISTRY.createTagKey(loc);
	}
	
	public static TagKey<WeaponTrait> create(String namespace, String path)
	{
		return create(new ResourceLocation(namespace, path));
	}
	
	public static TagKey<WeaponTrait> create(String path)
	{
		return create(new ResourceLocation(SpartanWeaponryAPI.MOD_ID, path));
	}
}
