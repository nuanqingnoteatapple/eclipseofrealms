package com.oblivioussp.spartanweaponry.data;

import java.util.concurrent.CompletableFuture;

import com.oblivioussp.spartanweaponry.ModSpartanWeaponry;
import com.oblivioussp.spartanweaponry.api.tags.ModBlockTags;
import com.oblivioussp.spartanweaponry.api.tags.ModItemTags;
import com.oblivioussp.spartanweaponry.init.ModItems;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemTagsProvider extends ItemTagsProvider
{
	public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registry, CompletableFuture<TagsProvider.TagLookup<Block>> blockTagLookup, ExistingFileHelper existingFileHelper)
	{
		super(output, registry, blockTagLookup, ModSpartanWeaponry.ID, existingFileHelper);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void addTags(HolderLookup.Provider registry)
	{
		final TagKey<Item> WEAPONS = ItemTags.create(new ResourceLocation("forge:weapons"));
		final TagKey<Item> CURIOS_BACK = ItemTags.create(new ResourceLocation("curios:back"));
		final TagKey<Item> CURIOS_QUIVER = ItemTags.create(new ResourceLocation("curios:quiver"));

		// Tags in the Spartan Weaponry domain
		tag(ModItemTags.HANDLES).add(ModItems.SIMPLE_HANDLE.get(), ModItems.HANDLE.get());
		tag(ModItemTags.POLES).add(ModItems.SIMPLE_POLE.get(), ModItems.POLE.get());

		tag(ModItemTags.DAGGERS).add(ModItems.DAGGERS.getAsList().toArray(new Item[0]));
		tag(ModItemTags.PARRYING_DAGGERS).add(ModItems.PARRYING_DAGGERS.getAsList().toArray(new Item[0]));

		tag(ModItemTags.GREATSWORDS).add(ModItems.GREATSWORDS.getAsList().toArray(new Item[0]));

		tag(ModItemTags.SPEARS).add(ModItems.SPEARS.getAsList().toArray(new Item[0]));

		tag(ModItemTags.LANCES).add(ModItems.LANCES.getAsList().toArray(new Item[0]));
		tag(ModItemTags.LONGBOWS).add(ModItems.LONGBOWS.getAsList().toArray(new Item[0]));
		tag(ModItemTags.HEAVY_CROSSBOWS).add(ModItems.HEAVY_CROSSBOWS.getAsList().toArray(new Item[0]));

		tag(ModItemTags.GLAIVES).add(ModItems.GLAIVES.getAsList().toArray(new Item[0]));
		tag(ModItemTags.QUARTERSTAVES).add(ModItems.QUARTERSTAVES.getAsList().toArray(new Item[0]));
		tag(ModItemTags.SCYTHES).add(ModItems.SCYTHES.getAsList().toArray(new Item[0]));

		tag(ModItemTags.WOODEN_WEAPONS).add(ModItems.DAGGERS.wood.get(),
				ModItems.PARRYING_DAGGERS.wood.get(),
				ModItems.GREATSWORDS.wood.get(),
				ModItems.SPEARS.wood.get(),
				ModItems.LANCES.wood.get(),
				ModItems.LONGBOWS.wood.get(),
				ModItems.HEAVY_CROSSBOWS.wood.get(),
				ModItems.GLAIVES.wood.get(),
				ModItems.QUARTERSTAVES.wood.get(),
				ModItems.SCYTHES.wood.get());
		tag(ModItemTags.STONE_WEAPONS).add(ModItems.DAGGERS.stone.get(),
				ModItems.PARRYING_DAGGERS.stone.get(),
				ModItems.GREATSWORDS.stone.get(),
				ModItems.SPEARS.stone.get(),
				ModItems.LANCES.stone.get(),
				ModItems.GLAIVES.stone.get(),
				ModItems.QUARTERSTAVES.stone.get(),
				ModItems.SCYTHES.stone.get());

		tag(ModItemTags.LEATHER_WEAPONS).add(ModItems.LONGBOWS.leather.get(), ModItems.HEAVY_CROSSBOWS.leather.get());

		tag(ModItemTags.IRON_WEAPONS).add(ModItems.DAGGERS.iron.get(),
				ModItems.PARRYING_DAGGERS.iron.get(),
				ModItems.GREATSWORDS.iron.get(),
				ModItems.SPEARS.iron.get(),
				ModItems.LANCES.iron.get(),
				ModItems.LONGBOWS.iron.get(),
				ModItems.HEAVY_CROSSBOWS.iron.get(),
				ModItems.GLAIVES.iron.get(),
				ModItems.QUARTERSTAVES.iron.get(),
				ModItems.SCYTHES.iron.get());

		tag(ModItemTags.GOLDEN_WEAPONS).add(ModItems.DAGGERS.gold.get(),
				ModItems.PARRYING_DAGGERS.gold.get(),
				ModItems.GREATSWORDS.gold.get(),
				ModItems.SPEARS.gold.get(),
				ModItems.LANCES.gold.get(),
				ModItems.LONGBOWS.gold.get(),
				ModItems.HEAVY_CROSSBOWS.gold.get(),
				ModItems.GLAIVES.gold.get(),
				ModItems.QUARTERSTAVES.gold.get(),
				ModItems.SCYTHES.gold.get());

		tag(ModItemTags.DIAMOND_WEAPONS).add(ModItems.DAGGERS.diamond.get(),
				ModItems.PARRYING_DAGGERS.diamond.get(),
				ModItems.GREATSWORDS.diamond.get(),
				ModItems.SPEARS.diamond.get(),
				ModItems.LANCES.diamond.get(),
				ModItems.LONGBOWS.diamond.get(),
				ModItems.HEAVY_CROSSBOWS.diamond.get(),
				ModItems.GLAIVES.diamond.get(),
				ModItems.QUARTERSTAVES.diamond.get(),
				ModItems.SCYTHES.diamond.get());

		tag(ModItemTags.NETHERITE_WEAPONS).add(ModItems.DAGGERS.netherite.get(),
				ModItems.PARRYING_DAGGERS.netherite.get(),
				ModItems.GREATSWORDS.netherite.get(),
				ModItems.SPEARS.netherite.get(),
				ModItems.LANCES.netherite.get(),
				ModItems.LONGBOWS.netherite.get(),
				ModItems.HEAVY_CROSSBOWS.netherite.get(),
				ModItems.GLAIVES.netherite.get(),
				ModItems.QUARTERSTAVES.netherite.get(),
				ModItems.SCYTHES.netherite.get());


		tag(ModItemTags.ARROWS).add(ModItems.WOODEN_ARROW.get(),
				ModItems.TIPPED_WOODEN_ARROW.get(),
				ModItems.IRON_ARROW.get(),
				ModItems.TIPPED_IRON_ARROW.get(),
				ModItems.DIAMOND_ARROW.get(),
				ModItems.TIPPED_DIAMOND_ARROW.get(),
				ModItems.NETHERITE_ARROW.get(),
				ModItems.TIPPED_NETHERITE_ARROW.get());

		tag(ModItemTags.BOLTS).add(ModItems.BOLT.get(),
				ModItems.TIPPED_BOLT.get(),
				ModItems.SPECTRAL_BOLT.get(),
				ModItems.DIAMOND_BOLT.get(),
				ModItems.TIPPED_DIAMOND_BOLT.get(),
				ModItems.NETHERITE_BOLT.get(),
				ModItems.TIPPED_NETHERITE_BOLT.get());


		tag(ModItemTags.DIAMOND_PROJECTILES).add(ModItems.DIAMOND_ARROW.get(), ModItems.TIPPED_DIAMOND_ARROW.get(), ModItems.DIAMOND_BOLT.get(), ModItems.TIPPED_DIAMOND_BOLT.get());

		tag(ModItemTags.NETHERITE_PROJECTILES).add(ModItems.NETHERITE_ARROW.get(), ModItems.TIPPED_NETHERITE_ARROW.get(), ModItems.NETHERITE_BOLT.get(), ModItems.TIPPED_NETHERITE_BOLT.get());

		tag(ModItemTags.HAS_CUSTOM_CROSSHAIR).addTags(ModItemTags.THROWING_WEAPONS, ModItemTags.HEAVY_CROSSBOWS);

		tag(ModItemTags.ZOMBIE_SPAWN_WEAPONS).add(ModItems.DAGGERS.iron.get(), ModItems.GREATSWORDS.iron.get());
		tag(ModItemTags.SKELETON_SPAWN_LONGBOWS).add(ModItems.LONGBOWS.wood.get(), ModItems.LONGBOWS.leather.get(), ModItems.LONGBOWS.iron.get());

		tag(ItemTags.ARROWS).addTag(ModItemTags.ARROWS);

		tag(WEAPONS).addTags(ModItemTags.DAGGERS,
				ModItemTags.PARRYING_DAGGERS,
				ModItemTags.GREATSWORDS,
				ModItemTags.SPEARS,
				ModItemTags.LANCES,
				ModItemTags.LONGBOWS,
				ModItemTags.HEAVY_CROSSBOWS,
				ModItemTags.GLAIVES,
				ModItemTags.QUARTERSTAVES,
				ModItemTags.SCYTHES);
		tag(ModItemTags.RAW_MEAT).add(Items.BEEF, Items.PORKCHOP, Items.CHICKEN, Items.MUTTON, Items.RABBIT);
		copy(ModBlockTags.GRASS, ModItemTags.GRASS);

	}

	@Override
	public String getName()
	{
		return ModSpartanWeaponry.NAME + " Item Tags";
	}
}