package com.oblivioussp.spartanweaponry.init;

import com.google.common.collect.ImmutableList;
import com.oblivioussp.spartanweaponry.ModSpartanWeaponry;
import com.oblivioussp.spartanweaponry.api.WeaponMaterial;
import com.oblivioussp.spartanweaponry.item.ArrowBaseItem;
import com.oblivioussp.spartanweaponry.item.ArrowBaseTippedItem;
import com.oblivioussp.spartanweaponry.item.BasicItem;
import com.oblivioussp.spartanweaponry.item.BoltItem;
import com.oblivioussp.spartanweaponry.item.BoltSpectralItem;
import com.oblivioussp.spartanweaponry.item.BoltTippedItem;
import com.oblivioussp.spartanweaponry.item.SwordBaseItem;
import com.oblivioussp.spartanweaponry.item.ThrowingWeaponItem;
import com.oblivioussp.spartanweaponry.util.Defaults;
import com.oblivioussp.spartanweaponry.util.WeaponFactory;
import com.oblivioussp.spartanweaponry.util.WeaponFactory.WeaponFunction;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems
{
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.Keys.ITEMS, ModSpartanWeaponry.ID);
	
	public static class WeaponItemsMelee
	{
		public final RegistryObject<SwordBaseItem> wood, stone, iron, gold, diamond, netherite;
		
		public WeaponItemsMelee(DeferredRegister<Item> register, String weaponName, WeaponFunction<SwordBaseItem> factory)
		{
			Item.Properties propVanilla = new Item.Properties();
			Item.Properties propModded = new Item.Properties();
			
			wood = register.register("wooden_" + weaponName, () -> factory.create(WeaponMaterial.WOOD, propVanilla));
			stone = register.register("stone_" + weaponName, () -> factory.create(WeaponMaterial.STONE, propVanilla));
			iron = register.register("iron_" + weaponName, () -> factory.create(WeaponMaterial.IRON, propVanilla));
			gold = register.register("golden_" + weaponName, () -> factory.create(WeaponMaterial.GOLD, propVanilla));
			diamond = register.register("diamond_" + weaponName, () -> factory.create(WeaponMaterial.DIAMOND, propVanilla));
			netherite = register.register("netherite_" + weaponName, () -> factory.create(WeaponMaterial.NETHERITE, new Item.Properties().fireResistant()));

		}
		
		public void updateSettingsFromConfig(float baseDamage, float damageMultiplier, double speed)
		{
			getAsList().forEach((weapon) -> weapon.setAttackDamageAndSpeed(baseDamage, damageMultiplier, speed));
		}
		
		public ImmutableList<ItemStack> getVanillaItemStacks()
		{
			return ImmutableList.of(new ItemStack(wood.get()), new ItemStack(stone.get()),  new ItemStack(iron.get()),
					new ItemStack(gold.get()), new ItemStack(diamond.get()), new ItemStack(netherite.get()));
		}

		
		public ImmutableList<SwordBaseItem> getAsList()
		{
			return ImmutableList.of(wood.get(), stone.get(), iron.get(), gold.get(), diamond.get(), netherite.get());
		}
	}
	
	public static class WeaponItemsRanged
	{
		public final RegistryObject<Item> wood, leather,  iron, gold, diamond, netherite;
		
		public WeaponItemsRanged(DeferredRegister<Item> register, String weaponName, WeaponFunction<? extends Item> factory)
		{
			Item.Properties propVanilla = new Item.Properties();
			Item.Properties propModded = new Item.Properties();
			
			wood = register.register("wooden_" + weaponName, () -> factory.create(WeaponMaterial.WOOD, propVanilla));
			leather = register.register("leather_" + weaponName, () -> factory.create(WeaponMaterial.LEATHER, propVanilla));
			iron = register.register("iron_" + weaponName, () -> factory.create(WeaponMaterial.IRON, propVanilla));
			gold = register.register("golden_" + weaponName, () -> factory.create(WeaponMaterial.GOLD, propVanilla));
			diamond = register.register("diamond_" + weaponName, () -> factory.create(WeaponMaterial.DIAMOND, propVanilla));
			netherite = register.register("netherite_" + weaponName, () -> factory.create(WeaponMaterial.NETHERITE, new Item.Properties().fireResistant()));
		}
		
		public ImmutableList<ItemStack> getVanillaItemStacks()
		{
			return ImmutableList.of(new ItemStack(wood.get()),
					new ItemStack(leather.get()),
					new ItemStack(iron.get()),
					new ItemStack(gold.get()),
					new ItemStack(diamond.get()),
					new ItemStack(netherite.get()));
		}

		
		public ImmutableList<Item> getAsList()
		{
			return ImmutableList.of(wood.get(),
					leather.get(),
					iron.get(),
					gold.get(),
					diamond.get(),
					netherite.get());
		}
	}
	
	public static class WeaponItemsThrowing
	{
		public RegistryObject<ThrowingWeaponItem> wood, stone, iron, gold, diamond, netherite;
		
		public WeaponItemsThrowing(DeferredRegister<Item> register, String weaponName, WeaponFunction<ThrowingWeaponItem> factory)
		{
			Item.Properties propVanilla = new Item.Properties();
			Item.Properties propModded = new Item.Properties();
			wood = register.register("wooden_" + weaponName, () -> factory.create(WeaponMaterial.WOOD, propVanilla));

			stone = register.register("stone_" + weaponName, () -> factory.create(WeaponMaterial.STONE, propVanilla));

			iron = register.register("iron_" + weaponName, () -> factory.create(WeaponMaterial.IRON, propVanilla));

			gold = register.register("golden_" + weaponName, () -> factory.create(WeaponMaterial.GOLD, propVanilla));

			diamond = register.register("diamond_" + weaponName, () -> factory.create(WeaponMaterial.DIAMOND, propVanilla));

			netherite = register.register("netherite_" + weaponName, () -> factory.create(WeaponMaterial.NETHERITE, new Item.Properties().fireResistant()));

		}
		
		public void updateSettingsFromConfig(float baseDamage, float damageMultiplier, double speed, int chargeTicks)
		{
			getAsList().forEach((weapon) -> weapon.updateFromConfig(baseDamage, damageMultiplier, speed, chargeTicks));
		}
		
		public ImmutableList<ItemStack> getVanillaItemStacks()
		{
			return ImmutableList.of(wood.get().makeTabStack(), stone.get().makeTabStack(), iron.get().makeTabStack(),
					gold.get().makeTabStack(), diamond.get().makeTabStack(), netherite.get().makeTabStack());
		}

		
		public ImmutableList<ThrowingWeaponItem> getAsList()
		{
			return ImmutableList.of(wood.get(), stone.get(), iron.get(), gold.get(), diamond.get(), netherite.get());
		}
	}
	
	// Basic Items

	public static final RegistryObject<Item> SIMPLE_HANDLE = REGISTRY.register("simple_handle", () -> new BasicItem(new Item.Properties()));

	public static final RegistryObject<Item> HANDLE = REGISTRY.register("handle", () -> new BasicItem(new Item.Properties()));

	public static final RegistryObject<Item> SIMPLE_POLE = REGISTRY.register("simple_pole", () -> new BasicItem(new Item.Properties()));

	public static final RegistryObject<Item> POLE = REGISTRY.register("pole", () -> new BasicItem(new Item.Properties()));

	public static final RegistryObject<Item> GREASE_BALL = REGISTRY.register("grease_ball", () -> new BasicItem(new Item.Properties()));
	
	// Weapons
	public static final WeaponItemsMelee DAGGERS = new WeaponItemsMelee(REGISTRY, "dagger", WeaponFactory.DAGGER);

	public static final WeaponItemsMelee PARRYING_DAGGERS = new WeaponItemsMelee(REGISTRY, "parrying_dagger", WeaponFactory.PARRYING_DAGGER);

	public static final WeaponItemsMelee GREATSWORDS = new WeaponItemsMelee(REGISTRY, "greatsword", WeaponFactory.GREATSWORD);

	public static final WeaponItemsMelee SPEARS = new WeaponItemsMelee(REGISTRY, "spear", WeaponFactory.SPEAR);

	public static final WeaponItemsMelee LANCES = new WeaponItemsMelee(REGISTRY, "lance", WeaponFactory.LANCE);

	public static final WeaponItemsRanged LONGBOWS = new WeaponItemsRanged(REGISTRY, "longbow", WeaponFactory.LONGBOW);

	public static final WeaponItemsRanged HEAVY_CROSSBOWS = new WeaponItemsRanged(REGISTRY, "heavy_crossbow", WeaponFactory.HEAVY_CROSSBOW);

	public static final WeaponItemsMelee GLAIVES = new WeaponItemsMelee(REGISTRY, "glaive", WeaponFactory.GLAIVE);

	public static final WeaponItemsMelee QUARTERSTAVES = new WeaponItemsMelee(REGISTRY, "quarterstaff", WeaponFactory.QUARTERSTAFF);

	public static final WeaponItemsMelee SCYTHES = new WeaponItemsMelee(REGISTRY, "scythe", WeaponFactory.SCYTHE);
	
	// Arrows

	public static final RegistryObject<ArrowBaseItem> WOODEN_ARROW = REGISTRY.register("wooden_arrow", () -> new ArrowBaseItem(Defaults.BaseDamageArrowWood, Defaults.RangeMultiplierArrowWood));

	public static final RegistryObject<ArrowBaseItem> TIPPED_WOODEN_ARROW = REGISTRY.register("tipped_wooden_arrow", () -> new ArrowBaseTippedItem("wooden_arrow", Defaults.BaseDamageArrowWood, Defaults.RangeMultiplierArrowWood));

	public static final RegistryObject<ArrowBaseItem> IRON_ARROW = REGISTRY.register("iron_arrow", () -> new ArrowBaseItem(Defaults.BaseDamageArrowIron, Defaults.RangeMultiplierArrowIron));

	public static final RegistryObject<ArrowBaseItem> TIPPED_IRON_ARROW = REGISTRY.register("tipped_iron_arrow", () -> new ArrowBaseTippedItem("iron_arrow", Defaults.BaseDamageArrowIron, Defaults.RangeMultiplierArrowIron));

	public static final RegistryObject<ArrowBaseItem> DIAMOND_ARROW = REGISTRY.register("diamond_arrow", () -> new ArrowBaseItem(Defaults.BaseDamageArrowDiamond, Defaults.RangeMultiplierArrowDiamond));

	public static final RegistryObject<ArrowBaseItem> TIPPED_DIAMOND_ARROW = REGISTRY.register("tipped_diamond_arrow", () -> new ArrowBaseTippedItem("diamond_arrow", Defaults.BaseDamageArrowDiamond, Defaults.RangeMultiplierArrowDiamond));

	public static final RegistryObject<ArrowBaseItem> NETHERITE_ARROW = REGISTRY.register("netherite_arrow", () -> new ArrowBaseItem(Defaults.BaseDamageArrowNetherite, Defaults.RangeMultiplierArrowNetherite));

	public static final RegistryObject<ArrowBaseItem> TIPPED_NETHERITE_ARROW = REGISTRY.register("tipped_netherite_arrow", () -> new ArrowBaseTippedItem("netherite_arrow", Defaults.BaseDamageArrowNetherite, Defaults.RangeMultiplierArrowNetherite));

	public static final RegistryObject<BoltItem> BOLT = REGISTRY.register("bolt", () -> new BoltItem(Defaults.BaseDamageBolt, Defaults.RangeMultiplierBolt, Defaults.ArmorPiercingFactorBolt));

	public static final RegistryObject<BoltItem> TIPPED_BOLT = REGISTRY.register("tipped_bolt", () -> new BoltTippedItem("bolt", Defaults.BaseDamageBolt, Defaults.RangeMultiplierBolt, Defaults.ArmorPiercingFactorBolt));

	public static final RegistryObject<BoltItem> SPECTRAL_BOLT = REGISTRY.register("spectral_bolt", () -> new BoltSpectralItem(Defaults.BaseDamageBolt, Defaults.RangeMultiplierBolt, Defaults.ArmorPiercingFactorBolt));

	public static final RegistryObject<BoltItem> DIAMOND_BOLT = REGISTRY.register("diamond_bolt", () -> new BoltItem(Defaults.BaseDamageBoltDiamond, Defaults.RangeMultiplierBoltDiamond, Defaults.ArmorPiercingFactorBoltDiamond));

	public static final RegistryObject<BoltItem> TIPPED_DIAMOND_BOLT = REGISTRY.register("tipped_diamond_bolt", () -> new BoltTippedItem("diamond_bolt", Defaults.BaseDamageBoltDiamond, Defaults.RangeMultiplierBoltDiamond, Defaults.ArmorPiercingFactorBoltDiamond));

	public static final RegistryObject<BoltItem> NETHERITE_BOLT = REGISTRY.register("netherite_bolt", () -> new BoltItem(Defaults.BaseDamageBoltNetherite, Defaults.RangeMultiplierBoltNetherite, Defaults.ArmorPiercingFactorBoltNetherite));

	public static final RegistryObject<BoltItem> TIPPED_NETHERITE_BOLT = REGISTRY.register("tipped_netherite_bolt", () -> new BoltTippedItem("netherite_bolt", Defaults.BaseDamageBoltNetherite, Defaults.RangeMultiplierBoltNetherite, Defaults.ArmorPiercingFactorBoltNetherite));

	
}
