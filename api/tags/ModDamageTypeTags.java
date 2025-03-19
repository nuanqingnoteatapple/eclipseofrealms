package com.oblivioussp.spartanweaponry.api.tags;

import com.oblivioussp.spartanweaponry.api.SpartanWeaponryAPI;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;

public class ModDamageTypeTags
{
	public static final TagKey<DamageType> IS_ARMOR_PIERCING = create("is_armor_piercing");
	
			
	public static TagKey<DamageType> create(ResourceLocation loc)
	{
		return TagKey.create(Registries.DAMAGE_TYPE, loc);
	}
	
	public static TagKey<DamageType> create(String namespace, String path)
	{
		return create(new ResourceLocation(namespace, path));
	}
	
	public static TagKey<DamageType> create(String path)
	{
		return create(new ResourceLocation(SpartanWeaponryAPI.MOD_ID, path));
	}
}
