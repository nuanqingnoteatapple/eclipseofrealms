package com.oblivioussp.spartanweaponry.client;

import com.oblivioussp.spartanweaponry.api.ModToolActions;
import com.oblivioussp.spartanweaponry.api.ModelOverrides;
import com.oblivioussp.spartanweaponry.api.WeaponTraits;
import com.oblivioussp.spartanweaponry.client.gui.HudCrosshair;
import com.oblivioussp.spartanweaponry.client.gui.HudLoadState;
import com.oblivioussp.spartanweaponry.client.renderer.entity.ArrowBaseRenderer;
import com.oblivioussp.spartanweaponry.client.renderer.entity.BoltRenderer;
import com.oblivioussp.spartanweaponry.client.renderer.entity.ThrowingWeaponRenderer;
import com.oblivioussp.spartanweaponry.init.ModEntities;
import com.oblivioussp.spartanweaponry.init.ModItems;
import com.oblivioussp.spartanweaponry.item.HeavyCrossbowItem;
import com.oblivioussp.spartanweaponry.item.LongbowItem;
import com.oblivioussp.spartanweaponry.item.SwordBaseItem;
import com.oblivioussp.spartanweaponry.item.ThrowingWeaponItem;
import com.oblivioussp.spartanweaponry.util.Log;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientHelper 
{
	
	public static final IGuiOverlay LOAD_STATE = HudLoadState::render;
	public static final IGuiOverlay NEW_CROSSHAIR = HudCrosshair::render;
	
	public static final ItemColor COLOR_TIPPED_PROJECTILE = (stack, idx) -> idx == 1 ? PotionUtils.getColor(stack) : 0xFFFFFF;
	
	@SubscribeEvent
	public static void registerItemColoursHandler(RegisterColorHandlersEvent.Item ev)
	{
		ev.register(COLOR_TIPPED_PROJECTILE, ModItems.TIPPED_WOODEN_ARROW.get(),
				ModItems.TIPPED_IRON_ARROW.get(),
				ModItems.TIPPED_DIAMOND_ARROW.get(),
				ModItems.TIPPED_NETHERITE_ARROW.get(),
				ModItems.TIPPED_BOLT.get(),
				ModItems.TIPPED_DIAMOND_BOLT.get(),
				ModItems.TIPPED_NETHERITE_BOLT.get());
	}

	
	public static void registerMeleeWeaponPropertyOverrides(SwordBaseItem meleeWeapon)
	{
		ItemProperties.register(meleeWeapon, ModelOverrides.BLOCKING, (stack, world, living, value) ->
		{
			return meleeWeapon.canPerformAction(stack, ModToolActions.MELEE_BLOCK) &&  living != null && living.isUsingItem() && living.getUseItem() == stack ? 1.0f : 0.0f;
		});
		Item weapon = meleeWeapon.getAsItem();
		ItemProperties.register(weapon, ModelOverrides.THROWING, (stack, world, living, value) ->
		{
			if(living == null || !meleeWeapon.hasWeaponTrait(WeaponTraits.THROWABLE.get()) || !stack.is(living.getUseItem().getItem()))	return 0.0f;
			return living.getTicksUsingItem() > 0 ? 1.0f : 0.0f;
		});
	}

	public static void registerHeavyCrossbowPropertyOverrides(HeavyCrossbowItem crossbow)
	{
		ItemProperties.register(crossbow, ModelOverrides.PULL, (stack, world, living, value) ->
		{
			if(living != null /*&& stack.getItem() == crossbow*/)
				return crossbow.isLoaded(stack) ? 0.0f : (float)(crossbow.getLoadingTicks(stack, living)) / crossbow.getFullLoadTicks(stack);
			return 0.0f;
		});
		ItemProperties.register(crossbow, ModelOverrides.PULLING, (stack, world, living, value) ->
		{
			return living != null && living.isUsingItem() && living.getUseItem() == stack ? 1.0f : 0.0f;
		});
		ItemProperties.register(crossbow, ModelOverrides.CHARGED, (stack, world, living, value) ->
		{
			return crossbow.isLoaded(stack) ? 1.0f : 0.0f;
		});
	}
	
	public static void registerLongbowPropertyOverrides(LongbowItem longbow)
	{
		ItemProperties.register(longbow, ModelOverrides.PULLING, (stack, world, living, value) ->
		{
			return living != null && living.isUsingItem() && living.getUseItem() == stack ? 1.0f : 0.0f;
		});
		ItemProperties.register(longbow, ModelOverrides.PULL, (stack, world, shooter, value) -> 
		{
			return shooter != null && shooter.getUseItem() == stack ? longbow.getNockProgress(stack, shooter) : 0.0f;
		});
	}
	
	public static void registerThrowingWeaponPropertyOverrides(ThrowingWeaponItem throwingWeapon)
	{
//		Log.debug("Registering Throwing Weapon Property Overrides for item: \"" + throwingWeapon.getRegistryName().toString() + "\"");
		ItemProperties.register(throwingWeapon, ModelOverrides.THROWING, (stack, world, living, value) ->
		{
			if(living == null || !stack.is(living.getUseItem().getItem()))	return 0.0f;
			return living.getTicksUsingItem() > 0 ? 1.0f : 0.0f;
		});
		ItemProperties.register(throwingWeapon, ModelOverrides.EMPTY, (stack, world, living, value) ->
		{
			return stack.getOrCreateTag().getInt(ThrowingWeaponItem.NBT_AMMO_USED) == ((ThrowingWeaponItem)throwingWeapon).getMaxAmmo(stack) ? 1 : 0;
		});
	}

	
/*	public static void registerMeleeWeaponBlockingPropertyOverrides(Item meleeWeapon)
	{
		ItemProperties.register(meleeWeapon, Const.MODEL, (stack, world, living, value) ->
		{
			return GearData.getModelIndex(stack);
		});
		ItemProperties.register(meleeWeapon, Const.BROKEN_PROPERTY, (stack, world, living, value) ->
		{
			return GearHelper.isBroken(stack) ? 0 : 1;
		});
		ItemProperties.register(meleeWeapon, ModelOverrides.BLOCKING, (stack, world, living, value) ->
		{
			return meleeWeapon.canPerformAction(stack, ModToolActions.MELEE_BLOCK) && living != null && living.isUsingItem() && living.getUseItem() == stack ? 1.0f : 0.0f;
		});
	}*/
	
	@SubscribeEvent
	public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers ev)
	{
		Log.info("Registering Entity Renderers!");
		ev.registerEntityRenderer(ModEntities.ARROW_SW.get(), ArrowBaseRenderer::new);
		ev.registerEntityRenderer(ModEntities.BOLT.get(), BoltRenderer::new);
		ev.registerEntityRenderer(ModEntities.BOLT_SPECTRAL.get(), BoltRenderer::new);
		ev.registerEntityRenderer(ModEntities.THROWING_WEAPON.get(), ThrowingWeaponRenderer::new);

	}

	@SubscribeEvent
	public static void registerHudOverlays(RegisterGuiOverlaysEvent ev)
	{
		ev.registerAboveAll("load_state", LOAD_STATE);
		ev.registerAbove(VanillaGuiOverlay.CROSSHAIR.id(), "crosshair", NEW_CROSSHAIR);
	}


}
