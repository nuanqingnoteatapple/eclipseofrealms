package com.oblivioussp.spartanweaponry.data.loot;

import java.util.function.BiConsumer;

import com.oblivioussp.spartanweaponry.init.ModItems;
import com.oblivioussp.spartanweaponry.loot.ModLootTables;

import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class ModChestLoot implements LootTableSubProvider
{
	@Override
	public void generate(BiConsumer<ResourceLocation, LootTable.Builder> consumer)
	{
		consumer.accept(ModLootTables.INJECT_VILLAGE_WEAPONSMITH, LootTable.lootTable().withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1, 2)).

				add(LootItem.lootTableItem(ModItems.DAGGERS.iron.get()).setWeight(5)).
				add(LootItem.lootTableItem(ModItems.PARRYING_DAGGERS.iron.get()).setWeight(5)).
				add(LootItem.lootTableItem(ModItems.GREATSWORDS.iron.get()).setWeight(5)).
				add(LootItem.lootTableItem(ModItems.SPEARS.iron.get()).setWeight(5)).
				add(LootItem.lootTableItem(ModItems.LANCES.iron.get()).setWeight(5)).
				add(LootItem.lootTableItem(ModItems.GLAIVES.iron.get()).setWeight(5)).
				add(LootItem.lootTableItem(ModItems.QUARTERSTAVES.iron.get()).setWeight(5)).
				add(LootItem.lootTableItem(ModItems.SCYTHES.iron.get()).setWeight(5))));
		
		consumer.accept(ModLootTables.INJECT_VILLAGE_FLETCHER, LootTable.lootTable().withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1, 2)).
				add(LootItem.lootTableItem(ModItems.BOLT.get()).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))).add(LootItem.lootTableItem(ModItems.LONGBOWS.wood.get()).setWeight(3)).add(LootItem.lootTableItem(ModItems.LONGBOWS.iron.get()).setWeight(1)).
				add(LootItem.lootTableItem(ModItems.HEAVY_CROSSBOWS.wood.get()).setWeight(3)).add(LootItem.lootTableItem(ModItems.HEAVY_CROSSBOWS.iron.get()).setWeight(1))));
		
		consumer.accept(ModLootTables.INJECT_END_CITY_TREASURE, LootTable.lootTable().withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1, 2)).
				add(LootItem.lootTableItem(ModItems.DAGGERS.diamond.get()).setWeight(5)).
				add(LootItem.lootTableItem(ModItems.PARRYING_DAGGERS.diamond.get()).setWeight(5)).
				add(LootItem.lootTableItem(ModItems.GREATSWORDS.diamond.get()).setWeight(5)).
				add(LootItem.lootTableItem(ModItems.SPEARS.diamond.get()).setWeight(5)).
				add(LootItem.lootTableItem(ModItems.LANCES.diamond.get()).setWeight(5)).
				add(LootItem.lootTableItem(ModItems.LONGBOWS.diamond.get()).setWeight(5)).
				add(LootItem.lootTableItem(ModItems.HEAVY_CROSSBOWS.diamond.get()).setWeight(5)).
				add(LootItem.lootTableItem(ModItems.DIAMOND_BOLT.get()).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))).
				add(LootItem.lootTableItem(ModItems.GLAIVES.diamond.get()).setWeight(5)).
				add(LootItem.lootTableItem(ModItems.QUARTERSTAVES.diamond.get()).setWeight(5)).add(LootItem.lootTableItem(ModItems.SCYTHES.diamond.get()).setWeight(5))));
	}

}
