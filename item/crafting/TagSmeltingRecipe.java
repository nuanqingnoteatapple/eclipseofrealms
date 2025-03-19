package com.oblivioussp.spartanweaponry.item.crafting;

import com.oblivioussp.spartanweaponry.init.ModRecipeSerializers;

import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmeltingRecipe;

public class TagSmeltingRecipe extends SmeltingRecipe implements ITagCookingRecipe
{
	protected final Ingredient result;
	
	public TagSmeltingRecipe(ResourceLocation idIn, String groupIn,
			CookingBookCategory categoryIn, Ingredient inputIngredientIn, Ingredient resultIngredientIn, float experienceIn,
			int cookTimeIn) 
	{
		super(idIn, groupIn, categoryIn, inputIngredientIn, ItemStack.EMPTY, experienceIn, cookTimeIn);
		result = resultIngredientIn;
	}
	
	@Override
	public ItemStack getResultItem(RegistryAccess p_266851_) 
	{
		return result.getItems()[0];
	}
	
	@Override
	public ItemStack assemble(Container containerIn, RegistryAccess registryIn)
	{
		return getResultItem(registryIn).copy();
	}

	@Override
	public RecipeSerializer<?> getSerializer() 
	{
		return ModRecipeSerializers.TAGGED_SMELTING.get();
	}

	@Override
	public CookingBookCategory getCategory() 
	{
		return category();
	}

	@Override
	public Ingredient getInputIngredient() 
	{
		return ingredient;
	}

	@Override
	public Ingredient getResultIngredient() 
	{
		return result;
	}

	@Override
	public int getCookTime() 
	{
		return getCookingTime();
	}

	@Override
	public float getExperienceDrop()
	{
		return getExperience();
	}
}
