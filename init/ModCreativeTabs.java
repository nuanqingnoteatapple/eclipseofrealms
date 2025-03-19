package com.oblivioussp.spartanweaponry.init;

import com.google.common.collect.ImmutableList;
import com.oblivioussp.spartanweaponry.ModSpartanWeaponry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTabs 
{
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ModSpartanWeaponry.ID);
	
	public static final RegistryObject<CreativeModeTab> BASIC = REGISTRY.register("basic", () -> 
		CreativeModeTab.builder().
		title(Component.translatable("itemGroup." + ModSpartanWeaponry.ID + ".basic")).
		displayItems((itemDisplayParams, output) -> {
			ImmutableList.Builder<ItemStack> builder = ImmutableList.builder();
			builder.add(new ItemStack(ModItems.SIMPLE_HANDLE.get()),
					new ItemStack(ModItems.HANDLE.get()),
					new ItemStack(ModItems.SIMPLE_POLE.get()),
					new ItemStack(ModItems.POLE.get()),
					new ItemStack(ModItems.GREASE_BALL.get()));

			builder.addAll(ModItems.DAGGERS.getVanillaItemStacks());
			builder.addAll(ModItems.PARRYING_DAGGERS.getVanillaItemStacks());
			builder.addAll(ModItems.GREATSWORDS.getVanillaItemStacks());

			builder.addAll(ModItems.SPEARS.getVanillaItemStacks());

			builder.addAll(ModItems.LANCES.getVanillaItemStacks());
			builder.addAll(ModItems.LONGBOWS.getVanillaItemStacks());
			builder.addAll(ModItems.HEAVY_CROSSBOWS.getVanillaItemStacks());
			builder.addAll(ModItems.GLAIVES.getVanillaItemStacks());
			builder.addAll(ModItems.QUARTERSTAVES.getVanillaItemStacks());
			builder.addAll(ModItems.SCYTHES.getVanillaItemStacks());
			output.acceptAll(builder.build());
		}).
		build());

	public static final RegistryObject<CreativeModeTab> MODDED = REGISTRY.register("modded", () -> 
		CreativeModeTab.builder().
		title(Component.translatable("itemGroup." + ModSpartanWeaponry.ID + ".modded")).
		displayItems((itemDisplayParams, output) -> {
			ImmutableList.Builder<ItemStack> builder = ImmutableList.builder();
			output.acceptAll(builder.build());
		}).withTabsBefore(BASIC.getKey()).
		build());
	
	public static final RegistryObject<CreativeModeTab> ARROWS_BOLTS = REGISTRY.register("arrows_bolts", () -> 
		CreativeModeTab.builder().
		title(Component.translatable("itemGroup." + ModSpartanWeaponry.ID + ".arrows_bolts")).
		icon(() -> new ItemStack(ModItems.DIAMOND_ARROW.get())).
		displayItems((itemDisplayParams, output) -> {
			output.accept(ModItems.WOODEN_ARROW.get());
			makeTippedProjectiles(output, ModItems.TIPPED_WOODEN_ARROW.get());
			output.accept(ModItems.IRON_ARROW.get());
			makeTippedProjectiles(output, ModItems.TIPPED_IRON_ARROW.get());
			output.accept(ModItems.DIAMOND_ARROW.get());
			makeTippedProjectiles(output, ModItems.TIPPED_DIAMOND_ARROW.get());
			output.accept(ModItems.NETHERITE_ARROW.get());
			makeTippedProjectiles(output, ModItems.TIPPED_NETHERITE_ARROW.get());
			output.accept(ModItems.BOLT.get());
			makeTippedProjectiles(output, ModItems.TIPPED_BOLT.get());
			output.accept(ModItems.SPECTRAL_BOLT.get());
			output.accept(ModItems.DIAMOND_BOLT.get());
			makeTippedProjectiles(output, ModItems.TIPPED_DIAMOND_BOLT.get());
			output.accept(ModItems.NETHERITE_BOLT.get());
			makeTippedProjectiles(output, ModItems.TIPPED_NETHERITE_BOLT.get());
		}).withTabsBefore(MODDED.getKey()).
		build());
	
	private static void makeTippedProjectiles(CreativeModeTab.Output output, Item item)
	{
		ForgeRegistries.POTIONS.getValues().stream().filter((potion) -> !(potion.getEffects().isEmpty())).forEach((potion) -> output.accept(PotionUtils.setPotion(new ItemStack(item), potion)));
	}

}