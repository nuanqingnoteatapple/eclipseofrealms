package com.oblivioussp.spartanweaponry.init;

import com.oblivioussp.spartanweaponry.ModSpartanWeaponry;
import com.oblivioussp.spartanweaponry.entity.projectile.ArrowBaseEntity;
import com.oblivioussp.spartanweaponry.entity.projectile.BoltEntity;
import com.oblivioussp.spartanweaponry.entity.projectile.BoltSpectralEntity;
import com.oblivioussp.spartanweaponry.entity.projectile.ThrowingWeaponEntity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities 
{
	public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ModSpartanWeaponry.ID);
	
	public static final RegistryObject<EntityType<ArrowBaseEntity>> ARROW_SW = REGISTRY.register("arrow", () -> EntityType.Builder.<ArrowBaseEntity>of(ArrowBaseEntity::new, MobCategory.MISC).
			clientTrackingRange(4).
			updateInterval(20).
			setShouldReceiveVelocityUpdates(true).
			sized(0.5f, 0.5f).
			setCustomClientFactory(ArrowBaseEntity::new).
			build("arrow_sw"));
	public static final RegistryObject<EntityType<BoltEntity>> BOLT = REGISTRY.register("bolt", () -> EntityType.Builder.<BoltEntity>of(BoltEntity::new, MobCategory.MISC).
			clientTrackingRange(4).
			updateInterval(20).
			setShouldReceiveVelocityUpdates(true).
			sized(0.5f, 0.5f).
			setCustomClientFactory(BoltEntity::new).
			build("bolt"));
	public static final RegistryObject<EntityType<BoltSpectralEntity>> BOLT_SPECTRAL = REGISTRY.register("spectral_bolt", () -> EntityType.Builder.<BoltSpectralEntity>of(BoltSpectralEntity::new, MobCategory.MISC).
			clientTrackingRange(4).
			updateInterval(20).
			setShouldReceiveVelocityUpdates(true).
			sized(0.5f, 0.5f).
			setCustomClientFactory(BoltSpectralEntity::new).
			build("bolt_spectral"));
	public static final RegistryObject<EntityType<ThrowingWeaponEntity>> THROWING_WEAPON = REGISTRY.register("throwing_weapon", () -> EntityType.Builder.<ThrowingWeaponEntity>of(ThrowingWeaponEntity::new, MobCategory.MISC).
			clientTrackingRange(4).
			updateInterval(20).
			setShouldReceiveVelocityUpdates(true).
			sized(0.5f, 0.5f).
			setCustomClientFactory(ThrowingWeaponEntity::new).
			build("throwing_weapon"));
}
