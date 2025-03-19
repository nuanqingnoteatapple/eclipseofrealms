package com.oblivioussp.spartanweaponry.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.ImmutableList;
import com.oblivioussp.spartanweaponry.ModSpartanWeaponry;
import com.oblivioussp.spartanweaponry.api.tags.ModItemTags;
import com.oblivioussp.spartanweaponry.api.tags.ModWeaponTraitTags;
import com.oblivioussp.spartanweaponry.api.trait.WeaponTrait;
import com.oblivioussp.spartanweaponry.util.Log;
import com.oblivioussp.spartanweaponry.util.WeaponType;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryManager;
import net.minecraftforge.registries.tags.ITag;
import net.minecraftforge.registries.tags.ITagManager;

@SuppressWarnings("deprecation")
public class WeaponMaterial implements Tier, IReloadable
{
	public static final WeaponMaterial WOOD = new WeaponMaterial("wood", SpartanWeaponryAPI.MOD_ID, Tiers.WOOD, ItemTags.PLANKS, ModWeaponTraitTags.WOOD);
	public static final WeaponMaterial STONE = new WeaponMaterial("stone", SpartanWeaponryAPI.MOD_ID, Tiers.STONE, ModItemTags.COBBLESTONE, ModWeaponTraitTags.STONE);
	public static final WeaponMaterial LEATHER = new WeaponMaterial("leather", SpartanWeaponryAPI.MOD_ID, 128, 2.0f, 0.0f, 5, ModItemTags.LEATHER, ModWeaponTraitTags.LEATHER);
	public static final WeaponMaterial IRON = new WeaponMaterial("iron", SpartanWeaponryAPI.MOD_ID, Tiers.IRON, ModItemTags.IRON_INGOT, ModWeaponTraitTags.IRON);
	public static final WeaponMaterial GOLD = new WeaponMaterial("gold", SpartanWeaponryAPI.MOD_ID, Tiers.GOLD, ModItemTags.GOLD_INGOT, ModWeaponTraitTags.GOLD);
	public static final WeaponMaterial DIAMOND = new WeaponMaterial("diamond", SpartanWeaponryAPI.MOD_ID, Tiers.DIAMOND, ModItemTags.DIAMOND, ModWeaponTraitTags.DIAMOND);
	public static final WeaponMaterial NETHERITE = new WeaponMaterial("netherite", SpartanWeaponryAPI.MOD_ID, Tiers.NETHERITE, ModItemTags.NETHERITE_INGOT, ModWeaponTraitTags.NETHERITE);

	
	private int durability;
	private final float speed;
	private float baseDamage;
	private final int enchantability;
	private final LazyLoadedValue<Ingredient> repairMaterial;
	private final TagKey<Item> repairTag;
	
	private final String name;
	private final String modId;
	private int colourPrimary = 0x7F7F7F,
				colourSecondary = 0xFFFFFF;
	
	private boolean useCustomDisplayName = false;
	private Function<String, String> translationFunc = null;

	protected List<WeaponTrait> traits;				// *ALL* traits		 TODO: Does this still need to be cached?
	protected List<WeaponTrait> meleeTraits;		// Melee-only traits
	protected List<WeaponTrait> rangedTraits;		// Ranged-only traits
	protected List<WeaponTrait> throwingTraits;		// Throwing-only traits
	protected final TagKey<WeaponTrait> traitsTag;
	protected boolean isValidTag;
	protected Optional<List<Pair<WeaponTrait, WeaponTrait.InvalidReason>>> invalidTraits = Optional.empty();
	
	public WeaponMaterial(String nameIn, String modIdIn, int colourPrimaryIn, int colourSecondaryIn, int durabilityIn, 
			float speedIn, float baseDamageIn, int enchantabilityIn, TagKey<Item> repairTagIn, TagKey<WeaponTrait> traitsTagIn)
	{
		name = nameIn;
		modId = modIdIn;
		colourPrimary = colourPrimaryIn;
		colourSecondary = colourSecondaryIn;
		
		durability = durabilityIn;
		speed = speedIn;
		baseDamage = baseDamageIn;
		enchantability = enchantabilityIn;
		repairTag = repairTagIn;
		repairMaterial = new LazyLoadedValue<Ingredient>(() -> Ingredient.of(repairTagIn));
		traitsTag = traitsTagIn;
		
		ReloadableHandler.addToReloadList(this);
	}
	
	public WeaponMaterial(String unlocName, String modIdIn, int maxUses, float efficiency, float baseDamage, int enchantability, TagKey<Item> tag, TagKey<WeaponTrait> traitsTagIn)
	{
		this(unlocName, modIdIn, 0x7F7F7F, 0xFFFFFF, maxUses, efficiency, baseDamage, enchantability, tag, traitsTagIn);
	}
	
	public WeaponMaterial(String nameIn, String modIdIn, Tier itemTierIn, TagKey<Item> tagIn, TagKey<WeaponTrait> traitsTagIn)
	{
		this(nameIn, modIdIn, 0x7F7F7F, 0xFFFFFF, itemTierIn.getUses(), itemTierIn.getSpeed(), 
				itemTierIn.getAttackDamageBonus(), itemTierIn.getEnchantmentValue(), tagIn, traitsTagIn);
	}
	
	@Override
	public void reload()
	{
		IForgeRegistry<WeaponTrait> registry = RegistryManager.ACTIVE.getRegistry(WeaponTraits.REGISTRY_KEY);
		ITagManager<WeaponTrait> tagManager = registry.tags();
		// Verify the tag and Initialize Weapon Traits
		ImmutableList.Builder<WeaponTrait> builder = ImmutableList.builder();
		
		if(!(isValidTag = tagManager.isKnownTagName(traitsTag)))
		{
			Log.error("Weapon Trait tag \"" + traitsTag.location() +  "\" couldn't be found for weapon material \"" + name + "\"!");
			return;
		}
		else
		{
			ITag<WeaponTrait> tag = tagManager.getTag(traitsTag);
			invalidTraits = Optional.empty();
			List<Pair<WeaponTrait, WeaponTrait.InvalidReason>> invalidTraitList = new ArrayList<>();
			List<String> invalidTraitValues = new ArrayList<>();
			builder.addAll(tag.stream().filter((trait) -> 
			{
				boolean isActionTrait = trait.isActionTrait();
				if(isActionTrait)
				{
					invalidTraitList.add(Pair.of(trait, WeaponTrait.InvalidReason.MATERIAL_ACTION_TRAIT));
					invalidTraitValues.add(registry.getKey(trait).toString());
				}
				return !isActionTrait;
			}).collect(Collectors.toUnmodifiableList()));
			
			if(!invalidTraitValues.isEmpty())
			{
				Log.warn("Found non-material Weapon Traits for weapon material \"" + name + "\" which have not been added: " + String.join(", ", invalidTraitValues));
				invalidTraits = Optional.of(invalidTraitList);
			}
		}
		traits = builder.build();
		
		meleeTraits = traits.stream().filter(WeaponType.MELEE.getTraitFilter()).collect(Collectors.toUnmodifiableList());
		rangedTraits = traits.stream().filter(WeaponType.RANGED.getTraitFilter()).collect(Collectors.toUnmodifiableList());
		throwingTraits = traits.stream().filter(WeaponType.THROWING.getTraitFilter()).collect(Collectors.toUnmodifiableList());
	}
	
	public WeaponMaterial setUseCustomDisplayName()
	{
		this.useCustomDisplayName = true;
		return this;
	}
	
	public WeaponMaterial setUseCustomDisplayName(Function<String, String> translationFunc)
	{
		this.translationFunc = translationFunc;
		return setUseCustomDisplayName();
	}
	
	public boolean useCustomDisplayName()
	{
		return this.useCustomDisplayName;
	}
	
	public Component translateName()
	{
		if(translationFunc == null)
			return Component.translatable("material." + this.getModId() + "." + this.getMaterialName());
		return Component.literal(translationFunc.apply(name));
	}
	
	public String getMaterialName()
	{
		return name;
	}
	
	public int getPrimaryColour()
	{
		return colourPrimary;
	}
	
	public int getSecondaryColour()
	{
		return colourSecondary;
	}
	
	public String getModId()
	{
		return modId;
	}

	@Override
	public int getUses() 
	{
		return this.durability;
	}
	
	public void setDurability(int maxUses) 
	{
		this.durability = maxUses;
	}

	@Override
	public float getSpeed() 
	{
		return this.speed;
	}

	@Override
	public float getAttackDamageBonus()
	{
		return this.baseDamage;
	}
	
	public void setAttackDamage(float baseDamage) 
	{
		this.baseDamage = baseDamage;
	}


	@Override
	public int getLevel() 
	{
//		return this.harvestLevel;
		return 0;
	}

	@Override
	public int getEnchantmentValue() 
	{
		return this.enchantability;
	}

	@Override
	public Ingredient getRepairIngredient()
	{
		return repairMaterial.get();
	}
	
	public TagKey<Item> getRepairTag()
	{
		return repairTag;
	}
	
	public String getRepairTagName()
	{
		return repairTag.location().toString();
	}
	
	public TagKey<WeaponTrait> getTraitsTag() 
	{
		return traitsTag;
	}
	
	/**
	 * Queries if the material has any Weapon Traits
	 * @return true if any Weapon Trait bonus exists on this material; false otherwise.
	 */
	@Deprecated(since = "3.1.1", forRemoval = true)
	public boolean hasAnyBonusTraits()
	{
		return traits != null && (!traits.isEmpty() || invalidTraits.isPresent());
	}
	
	public boolean hasAnyBonusTraits(WeaponType type)
	{
		List<WeaponTrait> weaponTraits;
		
		switch(type)
		{
		case MELEE:
			weaponTraits = meleeTraits;
			break;
		case RANGED:
			weaponTraits = rangedTraits;
			break;
		case THROWING:
			weaponTraits = throwingTraits;
			break;
		default:
			return false;
		}
		
		return weaponTraits != null && (!weaponTraits.isEmpty() || invalidTraits.isPresent());
		
	}
	
	@Deprecated(since = "3.1.1", forRemoval = true)
	public List<WeaponTrait> getBonusTraits()
	{
		return traits;
	}
	
	public List<WeaponTrait> getBonusTraits(WeaponType type) 
	{
		switch(type)
		{
			case MELEE:
				return meleeTraits;
			case RANGED:
				return rangedTraits;
			case THROWING:
				return throwingTraits;
			default:
				return List.of();
		}
	}

	public void addTagErrorTooltip(ItemStack stack, List<Component> tooltip)
	{
		if(!isValidTag)
			tooltip.add(Component.translatable(String.format("tooltip.%s.trait.invalid.material_tag", SpartanWeaponryAPI.MOD_ID), Component.translatable(String.format("tooltip.%s.material.%s", SpartanWeaponryAPI.MOD_ID, name)), traitsTag.location()).withStyle(ChatFormatting.DARK_RED));
	}
	
	public void addTraitsToTooltip(ItemStack stack, WeaponType type, List<Component> tooltip, boolean isShiftPressed)
	{
		if(hasAnyBonusTraits(type))
		{
    		tooltip.add(Component.translatable(String.format("tooltip.%s.trait.material_bonus", ModSpartanWeaponry.ID)).withStyle(ChatFormatting.AQUA));
			traits.forEach((trait) -> trait.addTooltip(stack, tooltip, isShiftPressed, WeaponTrait.InvalidReason.NONE));
		}
		if(invalidTraits.isPresent())
			invalidTraits.get().forEach((traitPair) -> traitPair.getLeft().addTooltip(stack, tooltip, isShiftPressed, traitPair.getRight()));
	}

	@Deprecated(since = "3.1.1", forRemoval = true)
	public void addTraitsToTooltip(ItemStack stack, List<Component> tooltip, boolean isShiftPressed)
	{
		if(hasAnyBonusTraits())
		{
			traits.forEach((trait) -> trait.addTooltip(stack, tooltip, isShiftPressed, WeaponTrait.InvalidReason.NONE));
		}
		if(invalidTraits.isPresent())
			invalidTraits.get().forEach((traitPair) -> traitPair.getLeft().addTooltip(stack, tooltip, isShiftPressed, traitPair.getRight()));
	}
	
	/**
	 * Converts RGB color to the integer format expected for material colors
	 * @param r Red value
	 * @param g Green value
	 * @param b Blue value
	 * @return The combined integer color format
	 */
	public static int colorRGB(byte r, byte g, byte b)
	{
		int color = ((int)r << 16) + ((int)g << 8) + b;
		return color;
	}
}
