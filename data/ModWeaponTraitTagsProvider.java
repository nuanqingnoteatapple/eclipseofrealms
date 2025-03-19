package com.oblivioussp.spartanweaponry.data;

import java.util.concurrent.CompletableFuture;

import com.oblivioussp.spartanweaponry.ModSpartanWeaponry;
import com.oblivioussp.spartanweaponry.api.WeaponTraits;
import com.oblivioussp.spartanweaponry.api.tags.ModWeaponTraitTags;
import com.oblivioussp.spartanweaponry.api.trait.WeaponTrait;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryManager;

public class ModWeaponTraitTagsProvider extends IntrinsicHolderTagsProvider<WeaponTrait> 
{

	public ModWeaponTraitTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registry, ExistingFileHelper existingFileHelper)
	{
		super(output, WeaponTraits.REGISTRY_KEY, registry, 
				(weaponTrait) -> RegistryManager.ACTIVE.getRegistry(WeaponTraits.REGISTRY_KEY).getResourceKey(weaponTrait).orElseThrow(), 
				ModSpartanWeaponry.ID, existingFileHelper);
	}

	@Override
	public String getName() 
	{
		return ModSpartanWeaponry.NAME + " Weapon Trait Tags";
	}

	@Override
	protected void addTags(HolderLookup.Provider registry) 
	{
		tag(ModWeaponTraitTags.DAGGER).add( WeaponTraits.DAMAGE_BONUS_BACKSTAB.get());
		tag(ModWeaponTraitTags.PARRYING_DAGGER).add(WeaponTraits.BLOCK_MELEE.get());
		tag(ModWeaponTraitTags.GREATSWORD).add(WeaponTraits.TWO_HANDED_2.get(), WeaponTraits.REACH_1.get(), WeaponTraits.SWEEP_3.get());
		tag(ModWeaponTraitTags.SPEAR).add(WeaponTraits.REACH_1.get());
		tag(ModWeaponTraitTags.LANCE).add(WeaponTraits.REACH_1.get(), WeaponTraits.DAMAGE_BONUS_RIDING.get(), WeaponTraits.SWEEP_1.get());
		tag(ModWeaponTraitTags.GLAIVE).add(WeaponTraits.TWO_HANDED_1.get(), WeaponTraits.REACH_1.get(), WeaponTraits.SWEEP_2.get());
		tag(ModWeaponTraitTags.QUARTERSTAFF).add(WeaponTraits.TWO_HANDED_1.get(), WeaponTraits.SWEEP_2.get());
		tag(ModWeaponTraitTags.SCYTHE).add( WeaponTraits.DECAPITATE.get());
		
		tag(ModWeaponTraitTags.WOOD);
		tag(ModWeaponTraitTags.STONE);
		tag(ModWeaponTraitTags.LEATHER);
		tag(ModWeaponTraitTags.IRON);
		tag(ModWeaponTraitTags.GOLD);
		tag(ModWeaponTraitTags.DIAMOND);
		tag(ModWeaponTraitTags.NETHERITE).add(WeaponTraits.FIREPROOF.get());

	}

}
