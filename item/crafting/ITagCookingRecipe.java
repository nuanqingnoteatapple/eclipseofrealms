package com.oblivioussp.spartanweaponry.item.crafting;

import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

public interface ITagCookingRecipe extends Recipe<Container>
{
	public CookingBookCategory getCategory();
	public Ingredient getInputIngredient();
	public Ingredient getResultIngredient();
	public float getExperienceDrop();
	public int getCookTime();
}