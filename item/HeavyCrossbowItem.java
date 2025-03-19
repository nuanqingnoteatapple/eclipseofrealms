package com.oblivioussp.spartanweaponry.item;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.joml.Quaternionf;
import org.joml.Vector3f;

import com.oblivioussp.spartanweaponry.ModSpartanWeaponry;
import com.oblivioussp.spartanweaponry.api.IReloadable;
import com.oblivioussp.spartanweaponry.api.ReloadableHandler;
import com.oblivioussp.spartanweaponry.api.WeaponMaterial;
import com.oblivioussp.spartanweaponry.api.tags.ModItemTags;
import com.oblivioussp.spartanweaponry.api.trait.IRangedTraitCallback;
import com.oblivioussp.spartanweaponry.api.trait.WeaponTrait;
import com.oblivioussp.spartanweaponry.client.ClientHelper;
import com.oblivioussp.spartanweaponry.client.gui.HudCrosshairHeavyCrossbow;
import com.oblivioussp.spartanweaponry.client.gui.ICrosshairOverlay;
import com.oblivioussp.spartanweaponry.entity.projectile.BoltEntity;
import com.oblivioussp.spartanweaponry.init.ModEnchantments;
import com.oblivioussp.spartanweaponry.init.ModItems;
import com.oblivioussp.spartanweaponry.util.ClientConfig;
import com.oblivioussp.spartanweaponry.util.Defaults;
import com.oblivioussp.spartanweaponry.util.WeaponType;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITagManager;

public class HeavyCrossbowItem extends CrossbowItem implements IReloadable, IHudLoadState, IHudCrosshair
{
	protected WeaponMaterial material;
	protected String modId = null;
	protected int loadTicks;
	protected int aimTicks;
	
	protected String customDisplayName = null;
	
	protected boolean doCraftCheck = true;
	protected boolean canBeCrafted = true;
	
	protected final WeaponType type = WeaponType.RANGED;
	protected List<WeaponTrait> rangedTraits;
	
	public static final String NBT_CHARGED = "Charged";
	public static final String NBT_PROJECTILE = "Projectile";
	public static final Predicate<ItemStack> BOLT = (stack) -> stack.is(ModItemTags.BOLTS);

	public HeavyCrossbowItem(Item.Properties prop, WeaponMaterial material) 
	{
		super(prop.durability((int)(material.getUses() * 1.5f)));
		this.material = material;
		loadTicks = Defaults.CrossbowTicksToLoad;
		aimTicks = Defaults.CrossbowInaccuracyTicks;
		
		// Add property overrides on client only
		if(FMLEnvironment.dist.isClient())
			ClientHelper.registerHeavyCrossbowPropertyOverrides(this);
		
		ReloadableHandler.addToReloadList(this);
	}

	public HeavyCrossbowItem(Item.Properties prop, WeaponMaterial material, String customDisplayName)
	{
		this(prop, material);
		if(material.useCustomDisplayName())
			this.customDisplayName = customDisplayName;
	}
	
	// ---- ---- ---- ---- ---- ---- ---- ----
	// Overriding methods
	// ---- ---- ---- ---- ---- ---- ---- ----
	@Override
	public void reload() 
	{
		loadTicks = Defaults.CrossbowTicksToLoad;
		aimTicks = Defaults.CrossbowInaccuracyTicks;
		rangedTraits = material.getBonusTraits(type);
		for(WeaponTrait trait : rangedTraits)
		{
			Optional<IRangedTraitCallback> opt = trait.getRangedCallback();
			if(opt.isPresent())
			{
				IRangedTraitCallback callback = opt.get();
				loadTicks = callback.modifyHeavyCrossbowLoadTime(material, loadTicks);
				aimTicks = callback.modifyHeavyCrossbowAimTime(material, aimTicks);
			}
		}
	}
	
	@Override
	public int getMaxDamage(ItemStack stack) 
	{
		return material.getUses();
	}
	
	@Override
	public Predicate<ItemStack> getAllSupportedProjectiles()
	{
		return BOLT;
	}
	
	@Override
	public Predicate<ItemStack> getSupportedHeldProjectiles() 
	{
		return BOLT;
	}
	
	@Override
	public int getUseDuration(ItemStack stack)
	{
		return 72000;
	}
	
	@Override
	public void releaseUsing(ItemStack stack, Level levelIn, LivingEntity entityLiving, int timeLeft) 
	{
		if(entityLiving instanceof Player)
		{
			Player player = (Player)entityLiving;
			boolean isCreativeOrInfinite = player.getAbilities().instabuild || stack.getEnchantmentLevel(Enchantments.INFINITY_ARROWS) > 0;
            
    		if(this.getLoadProgress(stack, entityLiving) == 1.0f)
    		{
    			// Load the Crossbow
    			stack.getOrCreateTag().putBoolean(NBT_CHARGED, true);
    			ItemStack bolt = ItemStack.EMPTY;
    			int count = stack.getEnchantmentLevel(Enchantments.MULTISHOT) > 0 ? 3 : 1;
    			
    			bolt = entityLiving.getProjectile(stack);
    			if(bolt.isEmpty() || !BOLT.test(bolt))			// Fix: When in Creative Mode, the player will return an Arrow item as ammo, which is invalid; replace it with a Bolt
    				bolt = new ItemStack(ModItems.BOLT.get(), count);

        		// Create a copy of the bolt, then save it to NBT.
    			ItemStack boltToStore = bolt.copy();
    			boltToStore.setCount(count);
    			CompoundTag nbtBolt = new CompoundTag();
    			boltToStore.save(nbtBolt);
    			stack.getTag().put(NBT_PROJECTILE, nbtBolt);
    			
    			if(!player.getAbilities().instabuild)
    			{
    				bolt.shrink(1);
    				if(bolt.isEmpty())
    					player.getInventory().removeItem(bolt);
    			}
    			levelIn.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.CROSSBOW_LOADING_END, SoundSource.PLAYERS, 1.0F, 1.0F / (levelIn.random.nextFloat() * 0.5F + 1.0F) + 0.2F);
    		}
    		else
    		{
    			// Fire the Crossbow
    			ItemStack itemstack = ItemStack.EMPTY;
                CompoundTag tag = stack.getOrCreateTag().getCompound(NBT_PROJECTILE);
                
                if(tag != null && !tag.isEmpty())
                	itemstack = ItemStack.of(tag);
                
	            int i = this.getUseDuration(stack) - timeLeft;
	            
	            if (i < 0 || !stack.getTag().getBoolean(NBT_CHARGED)) return;
	
	            if (!itemstack.isEmpty() || isCreativeOrInfinite)
	            {
	                if (itemstack.isEmpty())
	                {
	                    itemstack = new ItemStack(ModItems.BOLT.get());
	                }
	
	                if (!levelIn.isClientSide)
	                {
	                	BoltItem itemBolt = ((BoltItem)(itemstack.getItem() instanceof BoltItem ? itemstack.getItem() : ModItems.BOLT));
	                	
	                	boolean flag1 = player.getAbilities().instabuild || (itemstack.getItem() instanceof BoltItem ? ((BoltItem)itemstack.getItem()).isInfinite(itemstack, stack, player) : false);
	                    
	                	// Account for lack of accuracy.
	                	int stackAimTicks = getAimTicks(stack);
	                    int inaccuracy = Mth.clamp(stackAimTicks - i, 0, stackAimTicks);
	                    float inaccuracyModifier = 0.0f;

	                    if(inaccuracy != 0)		// Apply inaccuracy if there is any.
	                    	inaccuracyModifier = 12.0f * ((float)inaccuracy / stackAimTicks);
	                	
	                	// Fire projectiles.
	                    spawnProjectile(stack, itemBolt, itemstack, levelIn, player, flag1, inaccuracyModifier, 0.0f);
	                    if(itemstack.getCount() > 1)
	                    {
		                    spawnProjectile(stack, itemBolt, itemstack, levelIn, player, flag1, inaccuracyModifier, -10.0f);
		                    spawnProjectile(stack, itemBolt, itemstack, levelIn, player, flag1, inaccuracyModifier, 10.0f);
	                    }
	                    int damage = itemstack.getCount() > 1 ? 3 : 1;
	                    stack.hurtAndBreak(damage, player, (playerEntity) -> playerEntity.broadcastBreakEvent(player.getUsedItemHand()));
	                    
	                    stack.getTag().putBoolean(NBT_CHARGED, false);
	                    stack.getTag().put(NBT_PROJECTILE, new CompoundTag());
	                }
	
	                levelIn.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.CROSSBOW_SHOOT, SoundSource.NEUTRAL, 1.0F, 1.0F / (levelIn.random.nextFloat() * 0.4F + 1.2F) + 1.5f * 0.5F);
	
	                player.awardStat(Stats.ITEM_USED.get(this));
	            }
    		}
		}
	}
	
    /**
     * Gets the velocity of the bolt entity from the crossbow's charge
     */
    public static float getBoltVelocity(BoltEntity bolt)
    {
    	return 4.5f * bolt.getRangeMultiplier();	// 1.5f * 3.0f * rangeMultiplier
    }
    
    protected void spawnProjectile(ItemStack crossbow, BoltItem boltItem, ItemStack boltStack, Level levelIn, Player player, boolean creativeOrInfinite, float inaccuracyModifier, float projectileAngle)
    {
    	BoltEntity bolt = boltItem.createBolt(levelIn, boltStack, player);
    	
    	bolt.setCritArrow(true);
    	bolt.setSoundEvent(SoundEvents.CROSSBOW_HIT);
    	bolt.setShotFromCrossbow(true);
        int pierceLvl = crossbow.getEnchantmentLevel(Enchantments.PIERCING);
    	if(pierceLvl > 0)
    		bolt.setPierceLevel((byte)pierceLvl);
        
    	Vec3 upVector = player.getUpVector(1.0f);
    	Quaternionf quat = new Quaternionf().setAngleAxis((projectileAngle * (Mth.PI / 180.0f)), upVector.x, upVector.y, upVector.z);
    	Vector3f velocityVec = player.getViewVector(1.0f).toVector3f().rotate(quat);
    	bolt.shoot(velocityVec.x, velocityVec.y, velocityVec.z, getBoltVelocity(bolt), inaccuracyModifier);
//        entityBolt.shootFromRotation(player, player.xRotO, player.yRotO, 0.0F, getBoltVelocity() * 3.0F, inaccuracyModifier);

    	for(WeaponTrait trait : rangedTraits)
    		trait.getRangedCallback().ifPresent((callback) -> callback.onProjectileSpawn(material, bolt));
        
        int j = crossbow.getEnchantmentLevel(Enchantments.POWER_ARROWS);

        if (j > 0)
        {
            bolt.setBaseDamage(bolt.getBaseDamage() + j * 0.5D + 0.5D);
        }

        int k = crossbow.getEnchantmentLevel(Enchantments.PUNCH_ARROWS);

        if (k > 0)
        {
            bolt.setKnockback(k);
        }

        if (crossbow.getEnchantmentLevel(Enchantments.FLAMING_ARROWS) > 0)
        {
            bolt.setSecondsOnFire(100);
        }

        if (creativeOrInfinite || projectileAngle != 0.0f)
        {
            bolt.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
        }

        levelIn.addFreshEntity(bolt);
    }
    
    @Override
    public UseAnim getUseAnimation(ItemStack stack) 
    {
    	if(!stack.getOrCreateTag().getBoolean(NBT_CHARGED))
    		return UseAnim.CROSSBOW;
    	return UseAnim.BOW;
    }
    
    @Override
    public InteractionResultHolder<ItemStack> use(Level levelIn, Player playerIn, InteractionHand handIn)
    {
    	ItemStack stack = playerIn.getItemInHand(handIn);
    	ItemStack ammoStack = playerIn.getProjectile(stack);
    	boolean hasAmmo = !ammoStack.isEmpty();
    	
    	if(!playerIn.getAbilities().instabuild && !hasAmmo && !stack.getOrCreateTag().getBoolean(NBT_CHARGED) && stack.getEnchantmentLevel(Enchantments.INFINITY_ARROWS) == 0)
    	{
    		return !hasAmmo ? InteractionResultHolder.fail(stack) : InteractionResultHolder.pass(stack);
    	}
    	playerIn.startUsingItem(handIn);
    	return InteractionResultHolder.consume(stack);
    }
    
    @Override
    public void onUseTick(Level levelIn, LivingEntity livingEntityIn, ItemStack stack, int count) 
    {
    	// Play loading sounds as necessary
    	if(!levelIn.isClientSide && !stack.getOrCreateTag().getBoolean(NBT_CHARGED) && livingEntityIn instanceof Player)
    	{
	    	float loadTicks = getLoadProgress(stack, livingEntityIn);
	    	SoundEvent loadingSound = null;
	    	if(loadTicks == 0.0f)
	    		loadingSound = SoundEvents.CROSSBOW_LOADING_START;
	    	else if(loadTicks == 0.5f || loadTicks == 0.9f)
	    		loadingSound = SoundEvents.CROSSBOW_LOADING_MIDDLE;
	    	if(loadingSound != null)
	    		levelIn.playSound((Player)null, livingEntityIn.getX(), livingEntityIn.getY(), livingEntityIn.getZ(), loadingSound, SoundSource.PLAYERS, 0.5f, 1.0f);
	    	
    	}
    }
    
    @Override
	public int getEnchantmentValue(ItemStack stack)
	{
		return material != null ? material.getEnchantmentValue() : 1;
	}
    
    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) 
    {
    	return this.material.getRepairIngredient().test(repair);
    }

	@Override
	public Component getName(ItemStack stack) 
	{
		if(customDisplayName == null)
			return super.getName(stack);
		return Component.translatable(customDisplayName, material.translateName());
	}
	
    @Override
    public void appendHoverText(ItemStack stack, Level levelIn, List<Component> tooltip, TooltipFlag flagIn) 
    {
		boolean isShiftPressed = Screen.hasShiftDown();
		
    	if(doCraftCheck && levelIn != null)
    	{
    		if(!ClientConfig.INSTANCE.forceDisableUncraftableTooltips.get() && material.getModId() == ModSpartanWeaponry.ID)
    		{
    			ITagManager<Item> tagManager = ForgeRegistries.ITEMS.tags();
        		if(!tagManager.isKnownTagName(material.getRepairTag()) || tagManager.getTag(material.getRepairTag()).isEmpty())
	    			canBeCrafted = false;
    		}
	    	doCraftCheck = false;
    	}

    	if(!canBeCrafted)
    		tooltip.add(Component.translatable(String.format("tooltip.%s.uncraftable_missing_material", ModSpartanWeaponry.ID), material.getRepairTagName()).withStyle(ChatFormatting.RED));

    	material.addTagErrorTooltip(stack, tooltip);
    	
    	if(stack.getOrCreateTag().contains(NBT_CHARGED))
    	{
    		ItemStack bolt = ItemStack.of(stack.getTag().getCompound(NBT_PROJECTILE));
    		if(!bolt.isEmpty())
    		{
	    		tooltip.add(Component.translatable(String.format("tooltip.%s.heavy_crossbow.loaded_bolt", ModSpartanWeaponry.ID), String.format("[%s x%d]", ChatFormatting.AQUA.toString() + bolt.getHoverName().getString() + ChatFormatting.WHITE.toString(), bolt.getCount())));
	        	tooltip.add(Component.empty());
    		}
    	}

    	if(material.hasAnyBonusTraits(type))
    	{
			if(isShiftPressed)
				tooltip.add(Component.translatable(String.format("tooltip.%s.traits", ModSpartanWeaponry.ID), Component.translatable("tooltip." + ModSpartanWeaponry.ID + ".showing_details").withStyle(ChatFormatting.DARK_GRAY)).withStyle(ChatFormatting.GOLD));
			else
				tooltip.add(Component.translatable(String.format("tooltip.%s.traits", ModSpartanWeaponry.ID), Component.translatable("tooltip." + ModSpartanWeaponry.ID + ".show_details", ChatFormatting.DARK_AQUA.toString() + "SHIFT").withStyle(ChatFormatting.DARK_GRAY)).withStyle(ChatFormatting.GOLD));

//			if(!rangedTraits.isEmpty())
//				tooltip.add(Component.translatable(String.format("tooltip.%s.trait.material_bonus", ModSpartanWeaponry.ID)).withStyle(ChatFormatting.AQUA));

//			rangedTraits.forEach((trait) -> trait.addTooltip(stack, tooltip, isShiftPressed));
			material.addTraitsToTooltip(stack, type, tooltip, isShiftPressed);
        	tooltip.add(Component.empty());
    	}
    	
    	if(isShiftPressed)
    	{
			tooltip.add(Component.translatable(String.format("tooltip.%s.description", ModSpartanWeaponry.ID), Component.translatable("tooltip." + ModSpartanWeaponry.ID + ".showing_details").withStyle(ChatFormatting.DARK_GRAY)).withStyle(ChatFormatting.GOLD));
			tooltip.add(Component.translatable(String.format("tooltip.%s.heavy_crossbow.desc", ModSpartanWeaponry.ID)).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
    		tooltip.add(Component.translatable(String.format("tooltip.%s.heavy_crossbow.desc_2", ModSpartanWeaponry.ID)).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
    		tooltip.add(Component.translatable(String.format("tooltip.%s.heavy_crossbow.desc_3", ModSpartanWeaponry.ID)).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
    	}
    	else
			tooltip.add(Component.translatable(String.format("tooltip.%s.description", ModSpartanWeaponry.ID), Component.translatable("tooltip." + ModSpartanWeaponry.ID + ".show_details", ChatFormatting.AQUA.toString() + "SHIFT").withStyle(ChatFormatting.DARK_GRAY)).withStyle(ChatFormatting.GOLD));
    	
    	tooltip.add(Component.empty());
    	tooltip.add(Component.translatable(String.format("tooltip.%s.modifiers.ammo.type", ModSpartanWeaponry.ID), Component.translatable(String.format("tooltip.%s.modifiers.ammo.bolt", ModSpartanWeaponry.ID)).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_AQUA));
    	tooltip.add(Component.translatable(String.format("tooltip.%s.modifiers.heavy_crossbow.load_time", ModSpartanWeaponry.ID), Component.translatable(String.format("tooltip.%s.modifiers.heavy_crossbow.load_time.value", ModSpartanWeaponry.ID), (float)getFullLoadTicks(stack) / 20.0f).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_AQUA));
    	tooltip.add(Component.translatable(String.format("tooltip.%s.modifiers.heavy_crossbow.aim_time", ModSpartanWeaponry.ID), Component.translatable(String.format("tooltip.%s.modifiers.heavy_crossbow.aim_time.value", ModSpartanWeaponry.ID), (float)getAimTicks(stack) / 20.0f).withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_AQUA));
    	tooltip.add(Component.empty());
    }

    @Override
    public Component getHighlightTip(ItemStack item, Component displayName) 
    {
    	if(item.hasTag())
		{
    		ItemStack bolt = ItemStack.of(item.getTag().getCompound(NBT_PROJECTILE));
    		if(!bolt.isEmpty())
    		{
    			return Component.translatable("tooltip." + ModSpartanWeaponry.ID + ".highlight_heavy_crossbow", displayName, bolt.getHoverName(), bolt.getCount());
    		}
		}

    	return super.getHighlightTip(item, displayName);
    }
	
	// ---- ---- ---- ---- ---- ---- ---- ----
	// IHudLoadState
	// ---- ---- ---- ---- ---- ---- ---- ----
	@Override
	public boolean isLoaded(ItemStack stack)
	{
		return stack.getTag() != null && stack.getTag().getBoolean(NBT_CHARGED);
	}

	@Override
	public float getLoadProgress(ItemStack stack, LivingEntity living)
	{
		return !isLoaded(stack) ? Mth.clamp((float)getLoadingTicks(stack, living) / (float)getFullLoadTicks(stack), 0.0f, 1.0f) : 0.0f;
	}
	
	// ---- ---- ---- ---- ---- ---- ---- ----
	// Internal methods
	// ---- ---- ---- ---- ---- ---- ---- ----
	
	public int getFullLoadTicks(ItemStack stack)
	{
		int i = stack.getEnchantmentLevel(Enchantments.QUICK_CHARGE);
		return Mth.clamp(loadTicks - 5 * i, 0, loadTicks);
	}
	
	public int getLoadingTicks(ItemStack stack, LivingEntity living)
	{
		return living.getTicksUsingItem();
	}
	
	public int getAimTicks(ItemStack stack)
	{
		int i = stack.getEnchantmentLevel(ModEnchantments.SHARPSHOOTER.get());
		return Mth.clamp(aimTicks - 2 * i, 0, aimTicks);
	}
    
    @Override
    public ICrosshairOverlay getCrosshairHudElement() 
    {
    	return HudCrosshairHeavyCrossbow::render;
    }

    // TODO: Decide on whether or not to allow Bow enchantments on the Heavy Crossbow
/*	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) 
	{
		//return super.canApplyAtEnchantingTable(stack, enchantment);
		return enchantment == Enchantments.POWER || enchantment == Enchantments.PUNCH || enchantment == Enchantments.FLAME || 
				enchantment == Enchantments.INFINITY || super.canApplyAtEnchantingTable(stack, enchantment);
	}
*/
}
