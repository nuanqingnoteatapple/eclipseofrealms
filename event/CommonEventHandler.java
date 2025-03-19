package com.oblivioussp.spartanweaponry.event;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Random;

import com.google.common.collect.ImmutableMap;
import com.oblivioussp.spartanweaponry.ModSpartanWeaponry;
import com.oblivioussp.spartanweaponry.api.IWeaponTraitContainer;
import com.oblivioussp.spartanweaponry.api.WeaponTraits;
import com.oblivioussp.spartanweaponry.api.trait.IMeleeTraitCallback;
import com.oblivioussp.spartanweaponry.api.trait.WeaponTrait;
import com.oblivioussp.spartanweaponry.entity.projectile.ThrowingWeaponEntity;
import com.oblivioussp.spartanweaponry.init.ModEnchantments;
import com.oblivioussp.spartanweaponry.init.ModItems;
import com.oblivioussp.spartanweaponry.init.ModMobEffects;
import com.oblivioussp.spartanweaponry.init.ModParticles;
import com.oblivioussp.spartanweaponry.item.SwordBaseItem;
import com.oblivioussp.spartanweaponry.item.ThrowingWeaponItem;
import com.oblivioussp.spartanweaponry.loot.ModLootTables;
import com.oblivioussp.spartanweaponry.merchant.villager.FletcherTrades;
import com.oblivioussp.spartanweaponry.merchant.villager.WeaponsmithTrades;
import com.oblivioussp.spartanweaponry.util.Config;
import com.oblivioussp.spartanweaponry.util.Log;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonEventHandler
{
	public static Random rand = new Random();
	
	@SubscribeEvent
	public static void onLivingHurt(LivingHurtEvent ev)
	{
		DamageSource source = ev.getSource();
		float dmgDealt = ev.getAmount();
		LivingEntity target = ev.getEntity();
		
		// Debug crap (code doesn't seem to be called anymore, even in an IDE)
//		if(SharedConstants.IS_RUNNING_IN_IDE)
//			Log.info("Damage: Entity: " + target.getDisplayName().getContents() + " Armour value: " + target.getArmorValue() + " Damage value: " + dmgDealt + " Source: " + src.msgId);			
		
		
		if(dmgDealt == 0.0f || source.is(DamageTypeTags.IS_PROJECTILE) || source.is(DamageTypeTags.IS_FIRE) || source.is(DamageTypeTags.IS_EXPLOSION) || /*src.isMagic() ||*/
				(!source.getMsgId().equals("player") && !source.getMsgId().equals("mob")))
			return;
		
		// Ensure that the source of damage is direct (not from projectiles, etc)
		if(source.getDirectEntity() == source.getEntity() && source.getEntity() instanceof LivingEntity && target != null)
		{
			LivingEntity attacker = (LivingEntity)source.getEntity();
			Level level = attacker.level();
			
			ItemStack attackerStack = attacker.getMainHandItem();
			ItemStack targetStack = target.getMainHandItem();

			boolean doTraitDamageParticles = false;
			boolean doOilDamageParticles = false;
			
			if(!attackerStack.isEmpty() && attackerStack.getItem() instanceof IWeaponTraitContainer<?> container)
			{
				float dmgUnmodified = dmgDealt;
				
				for(WeaponTrait trait : container.getAllWeaponTraits())
				{
					Optional<IMeleeTraitCallback> opt = trait.getMeleeCallback();
					if(opt.isPresent())
						dmgDealt = opt.get().modifyDamageDealt(container.getMaterial(), dmgDealt, source, attacker, target);
				}
				
				if(dmgDealt > dmgUnmodified)
					doTraitDamageParticles = true;
			}
			if(attackerStack.getItem() instanceof ThrowingWeaponItem && attackerStack.hasTag() && 
					attackerStack.getTag().getInt(ThrowingWeaponItem.NBT_AMMO_USED) >= ((ThrowingWeaponItem)attackerStack.getItem()).getMaxAmmo(attackerStack))
				// Only do punching damage when melee attacking using a throwing weapon without ammo
				dmgDealt = 1.0f;
			

			if(!targetStack.isEmpty() && targetStack.getItem() instanceof IWeaponTraitContainer<?> container)
			{
				for(WeaponTrait trait : container.getAllWeaponTraits())
				{
					Optional<IMeleeTraitCallback> opt = trait.getMeleeCallback();
					if(opt.isPresent())
						dmgDealt = opt.get().modifyDamageTaken(container.getMaterial(), dmgDealt, source, attacker, target);
				}
			}
			
			if(dmgDealt != ev.getAmount())
			{
				if(!level.isClientSide) 
				{
					// Emit particles when damage has been enhanced or mitigated, depending on what has happened
					if(doTraitDamageParticles && dmgDealt > ev.getAmount())
						((ServerLevel)level).sendParticles(ModParticles.DAMAGE_BOOSTED.get(), target.getX(), target.getY() + (target.getBbHeight() / 2.0f), target.getZ(), 8, 0.2d, 0.2d, 0.2d, 0.5d);
					else if(dmgDealt < ev.getAmount())
						((ServerLevel)level).sendParticles(ModParticles.DAMAGE_REDUCED.get(), target.getX(), target.getY() + (target.getBbHeight() / 2.0f), target.getZ(), 8, 0.2d, 0.2d, 0.2d, 0.5d);
				}
				
				//Log.info(String.format("Changed damage dealt! %f -> %f", ev.getAmount(), dmgDealt));
				ev.setAmount(dmgDealt);
			}
		}
	}
	
	@SubscribeEvent
	public static void attackEvent(LivingAttackEvent ev)
	{
		if(ev.getEntity() instanceof Player player && player.isUsingItem() && !player.getUseItem().isEmpty())
		{
			ItemStack activeStack = player.getUseItem();
			
			if(activeStack.getItem() instanceof SwordBaseItem weapon && weapon.hasWeaponTrait(WeaponTraits.BLOCK_MELEE.get()))
			{
				DamageSource source = ev.getSource();
				boolean damageItem = false;
				
				// Block Melee attacks only! Explosion, Fire, Magic, Projectile and unblockable damage won't be blocked!
				if(source.getMsgId().equals("player") || source.getMsgId().equals("mob") &&
						!source.is(DamageTypeTags.IS_EXPLOSION) && !source.is(DamageTypeTags.IS_FIRE) && !source.is(DamageTypeTags.IS_PROJECTILE) && !source.is(DamageTypeTags.BYPASSES_ARMOR))
				{
					// Do knockback due to damage.
					Entity trueSourceEntity = source.getEntity();
					
                    if (trueSourceEntity instanceof LivingEntity)
                    {
                    	LivingEntity living = (LivingEntity)source.getEntity();
                    	living.knockback(0.3F, player.getX() - living.getX(), player.getZ() - living.getZ());
                    }
                    damageItem = true;
				}
				if(damageItem)
				{
                    int itemDamage = 1 + Mth.floor(ev.getAmount());
                    activeStack.hurtAndBreak(itemDamage, player, (playerEntity) -> playerEntity.broadcastBreakEvent(playerEntity.getUsedItemHand()));
                    player.level().playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_KNOCKBACK, SoundSource.PLAYERS, 0.8f, 0.8f);
                    ev.setCanceled(true);                        
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void modifyLootLevel(LootingLevelEvent ev)
	{
		// Null-check the passed damage source itself (fixes issue #150)
		if(ev.getDamageSource() == null)	return;
		
		Entity e = ev.getDamageSource().getDirectEntity();
		
		// Null-check the passed damage source's immediate entity (fixes issue #150)
		if(e != null && e instanceof ThrowingWeaponEntity throwingWeapon)
		{
			int luckLevel = throwingWeapon.getWeaponItem().getEnchantmentLevel(ModEnchantments.LUCKY_THROW.get());
			ev.setLootingLevel(luckLevel);
		}
	}

	@SubscribeEvent
	public static void onLootTableLoad(LootTableLoadEvent ev)
	{
		if(Config.INSTANCE.addIronWeaponsToVillageWeaponsmith.get() && 
				ev.getName().equals(BuiltInLootTables.VILLAGE_WEAPONSMITH))
		{
			Log.info("Adding Iron Weapons to the Village Weaponsmith Loot Table!");
			ev.getTable().addPool(generateLootPool(ModLootTables.INJECT_VILLAGE_WEAPONSMITH));
		}
		else if(Config.INSTANCE.addBowAndCrossbowLootToVillageFletcher.get() && 
				ev.getName().equals(BuiltInLootTables.VILLAGE_FLETCHER))
		{
			Log.info("Adding Longbow and Heavy Crossbow related loot to the Village Fletcher Loot Table!");
			ev.getTable().addPool(generateLootPool(ModLootTables.INJECT_VILLAGE_FLETCHER));
		}
		else if(Config.INSTANCE.addDiamondWeaponsToEndCity.get() && 
				ev.getName().equals(BuiltInLootTables.END_CITY_TREASURE))
		{
			Log.info("Adding Diamond Weapons to the End City Treasure Loot Table!");
			ev.getTable().addPool(generateLootPool(ModLootTables.INJECT_END_CITY_TREASURE));
		}
	}
	
	private static LootPool generateLootPool(ResourceLocation lootName)
	{
		return LootPool.lootPool().add(generateLootEntry(lootName))
						.setBonusRolls(UniformGenerator.between(0, 1))
						.name(ModSpartanWeaponry.ID + "_inject")
						.build();
	}
	
	private static LootPoolEntryContainer.Builder<?> generateLootEntry(ResourceLocation lootName)
	{
		return LootTableReference.lootTableReference(lootName).setWeight(1);
	}
	
	/**
	 * Allow Mobs to spawn with weapons from this mod; Zombies with most melee weapons and Skeletons with Longbows
	 * @param ev
	 */
	/*@SubscribeEvent
	public static void onJoinWorld(SpecialSpawn ev)
	{
		if(!Config.INSTANCE.disableSpawningZombieWithWeapon.get() && ev.getEntity() instanceof Zombie zombie)
		{
			float rand = zombie.level.random.nextFloat();
			float chance = zombie.level.getDifficulty() == Difficulty.HARD ? 
					Config.INSTANCE.zombieWithMeleeSpawnChanceHard.get().floatValue() : 
					Config.INSTANCE.zombieWithMeleeSpawnChanceNormal.get().floatValue();
			
			if(rand > 1 - chance)
			{
				ITag<Item> tag = ForgeRegistries.ITEMS.tags().getTag(ModItemTags.ZOMBIE_SPAWN_WEAPONS);
				if(!tag.isEmpty())
				{
					ItemStack weapon = ItemStack.EMPTY;
					List<Item> possibleWeapons = tag.stream().toList();
					
					weapon = generateRandomItem(zombie.level, possibleWeapons);
				
					zombie.setItemSlot(EquipmentSlot.MAINHAND, weapon);
				}
			}
		}
		if(!Config.INSTANCE.disableSpawningSkeletonWithLongbow.get() && ev.getEntity() instanceof AbstractSkeleton skeleton)
		{
			float rand = skeleton.level.random.nextFloat();
			float chance = skeleton.level.getDifficulty() == Difficulty.HARD ? 
					Config.INSTANCE.skeletonWithLongbowSpawnChanceHard.get().floatValue() : 
					Config.INSTANCE.skeletonWithLongbowSpawnChanceNormal.get().floatValue();
			
			if(rand > 1 - chance)
			{
				ITag<Item> tag = ForgeRegistries.ITEMS.tags().getTag(ModItemTags.SKELETON_SPAWN_LONGBOWS);
				if(!tag.isEmpty())
				{
					ItemStack weapon = ItemStack.EMPTY;
					List<Item> possibleWeapons = tag.stream().toList();
					weapon = generateRandomItem(skeleton.level, possibleWeapons);
					skeleton.setItemSlot(EquipmentSlot.MAINHAND, weapon);
				}
			}
		}
	}
	
	private static ItemStack generateRandomItem(Level level, List<Item> items)
	{
		float weaponRand = level.random.nextFloat();
		float divider = 1.0f / items.size();
		int idx = Mth.floor(weaponRand / divider);
		idx = idx > items.size() - 1 ? items.size() - 1 : idx;
		
		return new ItemStack(items.get(idx));
	}*/
	
	@SubscribeEvent
	public static void addVillagerTrades(VillagerTradesEvent ev)
	{
		if(Config.INSTANCE.disableVillagerTrading.get())
			return;
		
		if(ev.getType() == VillagerProfession.WEAPONSMITH)
		{
			List<ItemListing> tradesLv1 = ev.getTrades().get(1);
			List<ItemListing> tradesLv2 = ev.getTrades().get(2);
			List<ItemListing> tradesLv3 = ev.getTrades().get(3);
			List<ItemListing> tradesLv4 = ev.getTrades().get(4);
			List<ItemListing> tradesLv5 = ev.getTrades().get(5);
			if(!WeaponsmithTrades.LVL1_ITEMS.isEmpty())		tradesLv1.add(WeaponsmithTrades.LVL1_TRADE);
			if(!WeaponsmithTrades.LVL2_ITEMS.isEmpty())		tradesLv2.add(WeaponsmithTrades.LVL2_TRADE);
			if(!WeaponsmithTrades.LVL3_ITEMS.isEmpty())		tradesLv3.add(WeaponsmithTrades.LVL3_TRADE);
			if(!WeaponsmithTrades.LVL4_ITEMS.isEmpty())		tradesLv4.add(WeaponsmithTrades.LVL4_TRADE);
			if(!WeaponsmithTrades.LVL5_ITEMS.isEmpty())		tradesLv5.add(WeaponsmithTrades.LVL5_TRADE);
		}
		else if(ev.getType() == VillagerProfession.FLETCHER)
		{
			List<ItemListing> tradesLv1 = ev.getTrades().get(1);
			List<ItemListing> tradesLv3 = ev.getTrades().get(3);
			List<ItemListing> tradesLv5 = ev.getTrades().get(5);
			if(!Config.INSTANCE.longbows.disableRecipes.get())			tradesLv1.add(FletcherTrades.LONGBOW_WOOD_TRADE);
			if(!Config.INSTANCE.longbows.disableRecipes.get())			tradesLv3.add(FletcherTrades.LONGBOW_IRON_TRADE);
			if(!Config.INSTANCE.heavyCrossbows.disableRecipes.get())	tradesLv3.add(FletcherTrades.HEAVY_CROSSBOW_TRADE);
			if(!Config.INSTANCE.heavyCrossbows.disableRecipes.get())	tradesLv3.add(FletcherTrades.BOLT_TRADE);
			if(!Config.INSTANCE.longbows.disableRecipes.get())			tradesLv5.add(FletcherTrades.ENCHANTED_DIAMOND_LONGBOW_TRADE);
			if(!Config.INSTANCE.heavyCrossbows.disableRecipes.get())	tradesLv5.add(FletcherTrades.ENCHANTED_DIAMOND_HEAVY_CROSSBOW_TRADE);
		}
	}
	
	/**
	 * Events to supress Ender Teleportation using the Ender Disruption Mob Effect
	 */
	@SubscribeEvent
	public static void onEnderTeleport(EntityTeleportEvent.EnderEntity ev)
	{
		ev.setCanceled(checkToCancelTeleport(ev.getEntityLiving()));
	}
	
	@SubscribeEvent
	public static void onEnderTeleport(EntityTeleportEvent.EnderPearl ev)
	{
		ev.setCanceled(checkToCancelTeleport(ev.getPlayer()));
	}
	
	@SubscribeEvent
	public static void onEnderTeleport(EntityTeleportEvent.ChorusFruit ev)
	{
		ev.setCanceled(checkToCancelTeleport(ev.getEntityLiving()));
	}
	
	public static boolean checkToCancelTeleport(LivingEntity teleportingEntity)
	{
		return teleportingEntity.hasEffect(ModMobEffects.ENDER_DISRPUTION.get());
	}
	
	/**
	 * Repair Throwing Weapons with other Throwing Weapons of the same type<br>
	 * Allows replenishing of ammo for enchanted Throwing Weapons at an XP cost per enchantment
	 * @param ev
	 */
	@SubscribeEvent
	public static void handleAnvilUpdate(AnvilUpdateEvent ev)
	{
		ItemStack left = ev.getLeft();
		ItemStack right = ev.getRight();
		if(left.getItem() instanceof ThrowingWeaponItem && left.hasTag() && left.getTag().getBoolean(ThrowingWeaponItem.NBT_ORIGINAL)
				&& ItemStack.isSameItem(left, right))
		{
			ThrowingWeaponItem throwingWeapon = (ThrowingWeaponItem)left.getItem();
			int leftAmmo = left.getTag().getInt(ThrowingWeaponItem.NBT_AMMO_USED);
			int rightAmmo = right.getTag().getInt(ThrowingWeaponItem.NBT_AMMO_USED);
			
			if(leftAmmo == 0)	// Used ammo is zero when ammo is full
				return;
			
			// Combine ammo and durability
			int maxAmmo = ((ThrowingWeaponItem)left.getItem()).getMaxAmmo(left);
			int durability = left.getDamageValue() + right.getDamageValue();
			int combinedAmmo = Mth.clamp((maxAmmo - leftAmmo) + (maxAmmo - rightAmmo), 0, throwingWeapon.getMaxAmmo(left));
			// Reduce ammo count if combined durability value exceeds maximum durability value
			if(durability > left.getMaxDamage())
			{
				combinedAmmo = Math.max(combinedAmmo - 1, 0);
				durability -= left.getMaxDamage();
			}
			ItemStack resultStack = ev.getLeft().copy();
			resultStack.getTag().putInt(ThrowingWeaponItem.NBT_AMMO_USED, maxAmmo - combinedAmmo);
			resultStack.setDamageValue(durability);
			
			// Calculate enchantment level to set the XP cost (This should help discourage potential duping)
			Map<Enchantment, Integer> enchantmentMap = EnchantmentHelper.getEnchantments(left);
			int cost = 1;	// 1 Level of cost per total levels of enchantment
			
			for(Entry<Enchantment, Integer> enchantment : enchantmentMap.entrySet())
			{
				cost += (enchantment.getValue());
			}
			
			ev.setCost(cost);
			ev.setOutput(resultStack);
		}
	}

	/**
	 * Simple Handle in-world conversion -> Stick in hand + Use on Grass
	 */
	
	// Conversion of blocks when Simple Handles are crafted. Tall blocks turn into their smaller variants
	private static ImmutableMap<Block, Block> conversionMap = ImmutableMap.of(Blocks.GRASS, Blocks.AIR,
																Blocks.TALL_GRASS, Blocks.GRASS,
																Blocks.SEAGRASS, Blocks.WATER,
																Blocks.TALL_SEAGRASS, Blocks.SEAGRASS,
																Blocks.FERN, Blocks.AIR,
																Blocks.LARGE_FERN, Blocks.FERN);
	
	@SubscribeEvent
	public static void onItemRightClick(PlayerInteractEvent.RightClickBlock ev)
	{
		// Skip if the item is not some form of stick or if the stick is on a cooldown
		Player player = ev.getEntity();
		InteractionHand hand = ev.getHand();
		ItemStack stack = player.getItemInHand(hand);
		if(!stack.is(Tags.Items.RODS_WOODEN) || player.getCooldowns().isOnCooldown(stack.getItem()))
			return;

		Level level = ev.getLevel();
		BlockPos pos = ev.getPos();
		BlockState state = level.getBlockState(pos);
		Block block = state.getBlock();
		Block blockTo;
		
		// Check the conversion map to determine what the block turns into
		if((blockTo = conversionMap.get(block)) != null)
		{
			if(block == Blocks.TALL_GRASS || block == Blocks.TALL_SEAGRASS || block == Blocks.LARGE_FERN)
			{
				// Check to see what half of the tall block has been clicked to get the proper block to convert
				if(state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.UPPER)
				{
					pos = pos.below();
					state = level.getBlockState(pos);
				}
			}
			// Remove an item of the main stack
			player.getCooldowns().addCooldown(stack.getItem(), 5);
			stack.shrink(1);
			if(stack.getCount() <= 0)
			{
				stack = ItemStack.EMPTY;
				player.setItemInHand(hand, stack);
			}
			// Now spawn the converted item on the ground
			stack = new ItemStack(ModItems.SIMPLE_HANDLE.get());
			Block.popResource(level, pos, stack);
			
			// Now change the block appropriately
			level.setBlock(pos, blockTo.defaultBlockState(), 35);
			level.levelEvent(player, 2001, pos, Block.getId(state));
			ev.setCancellationResult(InteractionResult.CONSUME);
		}
	}
}
