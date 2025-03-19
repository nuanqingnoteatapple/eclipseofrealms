package com.oblivioussp.spartanweaponry.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.ImmutableSet;
import com.oblivioussp.spartanweaponry.api.IReloadable;
import com.oblivioussp.spartanweaponry.api.ReloadableHandler;
import com.oblivioussp.spartanweaponry.api.SpartanWeaponryAPI;
import com.oblivioussp.spartanweaponry.api.WeaponTraits;
import com.oblivioussp.spartanweaponry.api.tags.ModWeaponTraitTags;
import com.oblivioussp.spartanweaponry.api.trait.WeaponTrait;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.RegistryManager;
import net.minecraftforge.registries.tags.ITag;
import net.minecraftforge.registries.tags.ITagManager;

/**
 * This class contains all the data that are constant for every weapon of a certain type (e.g. Dagger, Longsword, etc.)
 * This should reduce redundant values on each weapon item.
 * Currently only filters and pre-caches traits to be used for Weapon items to improve performance
 * @author ObliviousSpartan
 */
public class WeaponArchetype implements IReloadable
{
	public static final WeaponArchetype DAGGER = new WeaponArchetype("Dagger", true, ModWeaponTraitTags.DAGGER, WeaponType.MELEE);
	public static final WeaponArchetype PARRYING_DAGGER = new WeaponArchetype("Parrying Dagger", true, ModWeaponTraitTags.PARRYING_DAGGER, WeaponType.MELEE);
	public static final WeaponArchetype GREATSWORD = new WeaponArchetype("Greatsword", true, ModWeaponTraitTags.GREATSWORD, WeaponType.MELEE, ToolActions.SWORD_DIG);
	public static final WeaponArchetype SPEAR = new WeaponArchetype("Spear", false, ModWeaponTraitTags.SPEAR, WeaponType.MELEE);
	public static final WeaponArchetype LANCE = new WeaponArchetype("Lance", false, ModWeaponTraitTags.LANCE, WeaponType.MELEE);
	public static final WeaponArchetype GLAIVE = new WeaponArchetype("Glaive", true, ModWeaponTraitTags.GLAIVE, WeaponType.MELEE);
	public static final WeaponArchetype QUARTERSTAFF = new WeaponArchetype("Quarterstaff", false, ModWeaponTraitTags.QUARTERSTAFF, WeaponType.MELEE);
	public static final WeaponArchetype SCYTHE = new WeaponArchetype("Scythe", false, ModWeaponTraitTags.SCYTHE, WeaponType.MELEE);
	
	protected final String name;
	protected final TagKey<WeaponTrait> traitsTag;
	protected boolean isValidTag = true;
	protected List<WeaponTrait> traits;
	protected Optional<WeaponTrait> actionTrait = Optional.empty();
	protected Optional<List<Pair<WeaponTrait, WeaponTrait.InvalidReason>>> invalidTraits = Optional.empty();
//	protected final Predicate<WeaponTrait> traitFilter;
	protected final WeaponType type;
	protected final boolean isBladed;						// Used to determine if the weapon has a blade can cut through things such as Cobwebs
	protected final Set<ToolAction> toolActions;

	public WeaponArchetype(String nameIn, boolean isBladedIn, TagKey<WeaponTrait> traitsTagIn, WeaponType typeIn, Set<ToolAction> toolActionsIn)
	{
		name = nameIn;
		traitsTag = traitsTagIn;
		type = typeIn;
		isBladed = isBladedIn;
		toolActions = toolActionsIn;
		
		ReloadableHandler.addToReloadList(this);
	}
	
	public WeaponArchetype(String nameIn, boolean isBladedIn, TagKey<WeaponTrait> traitsTagIn, WeaponType typeIn, ToolAction... toolActionsIn)
	{
		this(nameIn, isBladedIn, traitsTagIn, typeIn, ImmutableSet.copyOf(toolActionsIn));
	}
	
	/*
	@Deprecated(since = "3.1.1", forRemoval = true)
	public WeaponArchetype(String nameIn, boolean isBladedIn, TagKey<WeaponTrait> traitsTagIn, Predicate<WeaponTrait> traitFilterIn, Set<ToolAction> toolActionsIn)
	{
		name = nameIn;
		traitsTag = traitsTagIn;
//		traitFilter = traitFilterIn;
		isBladed = isBladedIn;
		toolActions = toolActionsIn;
		
		ReloadableHandler.addToReloadList(this);
	}
	
	@Deprecated(since = "3.1.1", forRemoval = true)
	public WeaponArchetype(String nameIn, boolean isBladedIn, TagKey<WeaponTrait> traitsTagIn, Predicate<WeaponTrait> traitFilterIn, ToolAction... toolActionsIn)
	{
		this(nameIn, isBladedIn, traitsTagIn, traitFilterIn, ImmutableSet.copyOf(toolActionsIn));
	}*/
	

	@Override
	public void reload() 
	{
		ForgeRegistry<WeaponTrait> registry = RegistryManager.ACTIVE.getRegistry(WeaponTraits.REGISTRY_KEY);
		ITagManager<WeaponTrait> tagManager = registry.tags();

		if(!(isValidTag = tagManager.isKnownTagName(traitsTag)))
		{
			Log.error("Weapon Trait tag \"" + traitsTag.location() +  "\" couldn't be found for weapon archetype \"" + name + "\"!");
			return;
		}
		
		ITag<WeaponTrait> tag = tagManager.getTag(traitsTag);

		invalidTraits = Optional.empty();
		List<Pair<WeaponTrait, WeaponTrait.InvalidReason>> invalidTraitList = new ArrayList<>();
		List<String> invalidTraitValues = new ArrayList<>();
		AtomicReference<WeaponTrait> actionTraitRef = new AtomicReference<WeaponTrait>(null);
		
		traits = tag.stream().filter((trait) ->
		{
			boolean isValid = type.getTraitFilter().test(trait);
			if(isValid && trait.isActionTrait())
			{
				if(actionTraitRef.get() == null)
					actionTraitRef.set(trait);
				else
				{
					invalidTraitList.add(Pair.of(trait, WeaponTrait.InvalidReason.MULTIPLE_ACTION_TRAITS));
					invalidTraitValues.add(registry.getKey(trait).toString());
					return false;
				}
			}
			else if(!isValid)
			{
				WeaponTrait.InvalidReason reason = trait.isMeleeTrait() ? WeaponTrait.InvalidReason.WEAPON_NOT_MELEE :
												trait.isRangedTrait() ? WeaponTrait.InvalidReason.WEAPON_NOT_RANGED :
												trait.isThrowingTrait() ? WeaponTrait.InvalidReason.WEAPON_NOT_THROWING :
													WeaponTrait.InvalidReason.WEAPON_NOT_SUPPORTED;
				
				invalidTraitList.add(Pair.of(trait, reason));
				invalidTraitValues.add(registry.getKey(trait).toString());
			}
			return isValid;
		}).collect(Collectors.toUnmodifiableList());
		
		WeaponTrait trait = actionTraitRef.get();
			actionTrait = trait != null ? Optional.of(actionTraitRef.get()) : Optional.empty();
		
		if(!invalidTraitList.isEmpty())
		{
			Log.warn("Found invalid Weapon Traits for weapon archetype \"" + name + "\" which have not been added: " + String.join(", ", invalidTraitValues));
			invalidTraits = Optional.of(invalidTraitList);
		}
	}
	
	public boolean isBladed() 
	{
		return isBladed;
	}
	
	public boolean canPerformToolAction(ToolAction toolAction)
	{
		return toolActions.contains(toolAction);
	}
	
	public List<WeaponTrait> getTraits()
	{
		return traits;
	}
	
	public Optional<List<Pair<WeaponTrait, WeaponTrait.InvalidReason>>> getInvalidTraits() {
		return invalidTraits;
	}
	
	public Optional<WeaponTrait> getActionTrait()
	{
		return actionTrait;
	}
	
	public WeaponType getType() 
	{
		return type;
	}
	
	public void addTagErrorTooltip(ItemStack stack, List<Component> tooltip)
	{
		if(!isValidTag)
			tooltip.add(Component.translatable(String.format("tooltip.%s.trait.invalid.archetype_tag", SpartanWeaponryAPI.MOD_ID), name, traitsTag.location()).withStyle(ChatFormatting.DARK_RED));
	}
	
	public void addTraitsToTooltip(ItemStack stack, List<Component> tooltip, boolean isShiftPressed)
	{
		getTraits().forEach((trait) -> trait.addTooltip(stack, tooltip, isShiftPressed, WeaponTrait.InvalidReason.NONE));
		if(invalidTraits.isPresent())
			invalidTraits.get().forEach((traitPair) -> traitPair.getLeft().addTooltip(stack, tooltip, isShiftPressed, traitPair.getRight()));
	}
}
