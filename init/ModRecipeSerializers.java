package com.oblivioussp.spartanweaponry.init;

import com.oblivioussp.spartanweaponry.ModSpartanWeaponry;
import com.oblivioussp.spartanweaponry.item.crafting.TagBlastingRecipe;
import com.oblivioussp.spartanweaponry.item.crafting.TagCookingRecipeSerializer;
import com.oblivioussp.spartanweaponry.item.crafting.TagSmeltingRecipe;
import com.oblivioussp.spartanweaponry.item.crafting.TippedProjectileBaseRecipe;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipeSerializers
{
	public static final DeferredRegister<RecipeSerializer<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.Keys.RECIPE_SERIALIZERS, ModSpartanWeaponry.ID);
	
	public static final RegistryObject<TippedProjectileBaseRecipe.Serializer> TIPPED_PROJECTILE_BASE = REGISTRY.register("tipped_projectile", () -> new TippedProjectileBaseRecipe.Serializer());
	
	public static final RegistryObject<RecipeSerializer<TagSmeltingRecipe>> TAGGED_SMELTING = REGISTRY.register("tag_smelting", () -> new TagCookingRecipeSerializer<>(TagSmeltingRecipe::new, 200));
	public static final RegistryObject<RecipeSerializer<TagBlastingRecipe>> TAGGED_BLASTING = REGISTRY.register("tag_blasting", () -> new TagCookingRecipeSerializer<>(TagBlastingRecipe::new, 100));
	

}
