package com.oblivioussp.spartanweaponry.compat.jei;

import java.util.Arrays;

import com.oblivioussp.spartanweaponry.api.tags.ModItemTags;
import com.oblivioussp.spartanweaponry.init.ModItems;
import com.oblivioussp.spartanweaponry.util.Config;
import com.oblivioussp.spartanweaponry.util.Log;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

@JeiPlugin
public class SpartanWeaponryPlugin implements IModPlugin
{
	private final ResourceLocation PLUGIN_UID = new ResourceLocation("spartanweaponry", "jei_plugin");

	public ResourceLocation getPluginUid()
	{ 
		return this.PLUGIN_UID;
	}
  
	public void registerItemSubtypes(ISubtypeRegistration subtypeRegistry)
	{
		Log.info("JEI Plugin is Registering subtypes");
		
		subtypeRegistry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, ModItems.TIPPED_WOODEN_ARROW.get(), TippedProjectileSubtypeInterpreter.INSTANCE);
		subtypeRegistry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, ModItems.TIPPED_IRON_ARROW.get(), TippedProjectileSubtypeInterpreter.INSTANCE);
		subtypeRegistry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, ModItems.TIPPED_DIAMOND_ARROW.get(), TippedProjectileSubtypeInterpreter.INSTANCE);
		subtypeRegistry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, ModItems.TIPPED_NETHERITE_ARROW.get(), TippedProjectileSubtypeInterpreter.INSTANCE);
		subtypeRegistry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, ModItems.TIPPED_BOLT.get(), TippedProjectileSubtypeInterpreter.INSTANCE);
		subtypeRegistry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, ModItems.TIPPED_DIAMOND_BOLT.get(), TippedProjectileSubtypeInterpreter.INSTANCE);
		subtypeRegistry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, ModItems.TIPPED_NETHERITE_BOLT.get(), TippedProjectileSubtypeInterpreter.INSTANCE);
	}

	public void registerRecipes(IRecipeRegistration reg)
	{
		reg.addRecipes(RecipeTypes.CRAFTING, TippedProjectileRecipeMaker.getRecipes(ModItems.BOLT.get(), ModItems.TIPPED_BOLT.get()));
		
		if (!Config.INSTANCE.disableNewArrowRecipes.get())
		{
			reg.addRecipes(RecipeTypes.CRAFTING, TippedProjectileRecipeMaker.getRecipes(ModItems.WOODEN_ARROW.get(), ModItems.TIPPED_WOODEN_ARROW.get()));
			reg.addRecipes(RecipeTypes.CRAFTING, TippedProjectileRecipeMaker.getRecipes(ModItems.IRON_ARROW.get(), ModItems.TIPPED_IRON_ARROW.get()));
		} 

		if (!Config.INSTANCE.disableDiamondAmmoRecipes.get()) 
		{
			reg.addRecipes(RecipeTypes.CRAFTING, TippedProjectileRecipeMaker.getRecipes(ModItems.DIAMOND_BOLT.get(), ModItems.TIPPED_DIAMOND_BOLT.get()));
			if (!((Boolean)Config.INSTANCE.disableNewArrowRecipes.get()).booleanValue())
				reg.addRecipes(RecipeTypes.CRAFTING, TippedProjectileRecipeMaker.getRecipes(ModItems.DIAMOND_ARROW.get(), ModItems.TIPPED_DIAMOND_ARROW.get())); 
		} 
		if (!Config.INSTANCE.disableNetheriteAmmoRecipes.get()) 
		{
			reg.addRecipes(RecipeTypes.CRAFTING, TippedProjectileRecipeMaker.getRecipes(ModItems.NETHERITE_BOLT.get(), ModItems.TIPPED_NETHERITE_BOLT.get()));
			if (!((Boolean)Config.INSTANCE.disableNewArrowRecipes.get()).booleanValue())
				reg.addRecipes(RecipeTypes.CRAFTING, TippedProjectileRecipeMaker.getRecipes(ModItems.NETHERITE_ARROW.get(), ModItems.TIPPED_NETHERITE_ARROW.get())); 
		}

	}
	
	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime)
	{
		if(Config.INSTANCE.forceShowDisabledItems.get())	// Skip disabling items if this config option is enabled
			return;

		if(Config.INSTANCE.daggers.disableRecipes.get())
			removeItemTagFromJEI(jeiRuntime, ModItemTags.DAGGERS);

		if(Config.INSTANCE.parryingDaggers.disableRecipes.get())
			removeItemTagFromJEI(jeiRuntime, ModItemTags.PARRYING_DAGGERS);

		if(Config.INSTANCE.greatswords.disableRecipes.get())
			removeItemTagFromJEI(jeiRuntime, ModItemTags.GREATSWORDS);

		if(Config.INSTANCE.spears.disableRecipes.get())
			removeItemTagFromJEI(jeiRuntime, ModItemTags.SPEARS);

		if(Config.INSTANCE.lances.disableRecipes.get())
			removeItemTagFromJEI(jeiRuntime, ModItemTags.LANCES);

		if(Config.INSTANCE.longbows.disableRecipes.get())
			removeItemTagFromJEI(jeiRuntime, ModItemTags.LONGBOWS);

		if(Config.INSTANCE.heavyCrossbows.disableRecipes.get())
			removeItemTagFromJEI(jeiRuntime, ModItemTags.HEAVY_CROSSBOWS);

		if(Config.INSTANCE.glaives.disableRecipes.get())
			removeItemTagFromJEI(jeiRuntime, ModItemTags.GLAIVES);

		if(Config.INSTANCE.quarterstaves.disableRecipes.get())
			removeItemTagFromJEI(jeiRuntime, ModItemTags.QUARTERSTAVES);

		if(Config.INSTANCE.scythes.disableRecipes.get())
			removeItemTagFromJEI(jeiRuntime, ModItemTags.SCYTHES);

		if(Config.INSTANCE.disableNewArrowRecipes.get())
			removeItemTagFromJEI(jeiRuntime, ModItemTags.ARROWS);

		if(Config.INSTANCE.disableDiamondAmmoRecipes.get())
			removeItemTagFromJEI(jeiRuntime, ModItemTags.DIAMOND_PROJECTILES);

	}
	
	private void removeItemTagFromJEI(IJeiRuntime jeiRuntime, TagKey<Item> tag)
	{
		jeiRuntime.getIngredientManager().removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK, Arrays.asList(Ingredient.of(tag).getItems()));		
	}
}