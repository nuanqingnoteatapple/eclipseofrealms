package com.oblivioussp.spartanweaponry.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.oblivioussp.spartanweaponry.api.IWeaponTraitContainer;
import com.oblivioussp.spartanweaponry.api.WeaponTraits;
import com.oblivioussp.spartanweaponry.api.trait.WeaponTrait;
import com.oblivioussp.spartanweaponry.init.ModDamageTypes;
import com.oblivioussp.spartanweaponry.util.Log;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

@Mixin(Player.class)
public class PlayerMixin 
{
	@Redirect(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;getSweepingDamageRatio(Lnet/minecraft/world/entity/LivingEntity;)F"))
	private float getSweepingDamageRatio(LivingEntity entityIn)
	{
		Log.debug("Intercepted EnchantmentHelper.getSweepingDamageRatio() method!");
		ItemStack weaponStack = entityIn.getMainHandItem();
		if(weaponStack.getItem() instanceof IWeaponTraitContainer<?> container)
		{
			WeaponTrait sweepTrait = container.getFirstWeaponTraitWithType(WeaponTraits.TYPE_SWEEP_DAMAGE);
			if(sweepTrait != null && sweepTrait.getLevel() > 1)
			{
				float damage = (float)entityIn.getAttributeValue(Attributes.ATTACK_DAMAGE);
				float resultDamage = damage * sweepTrait.getMagnitude();
				float result = ((resultDamage - 1.0f) / resultDamage) * sweepTrait.getMagnitude();;
				Log.debug("Base damage: " + damage + " * Magnitude: " + sweepTrait.getMagnitude() + " = Result damage: " + resultDamage + " - Overridden sweep damage ratio to " + result);
				return result;
			}
		}
		// Otherwise, return the standard sweep ratio method
		return EnchantmentHelper.getSweepingDamageRatio(entityIn);
	}
	
//	@Redirect(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/damagesource/DamageSources;playerAttack(Lnet/minecraft/world/entity/player/Player;)Lnet/minecraft/world/damagesource/DamageSource;"))
	@ModifyExpressionValue(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/damagesource/DamageSources;playerAttack(Lnet/minecraft/world/entity/player/Player;)Lnet/minecraft/world/damagesource/DamageSource;"))
	private DamageSource damagePlayerAttack(DamageSource originalSource, Entity targetIn)
	{
		Log.debug("Intercepted DamageSource.playerAttack() method!");
		Player playerIn = ((Player)originalSource.getEntity());
		ItemStack weaponStack = playerIn.getMainHandItem();
		if(weaponStack.getItem() instanceof IWeaponTraitContainer<?> container)
		{
			WeaponTrait armorPiercingTrait = container.getFirstWeaponTraitWithType(WeaponTraits.TYPE_ARMOUR_PIERCING);
			if(armorPiercingTrait != null)
			{
				Log.debug("Set damage type to Armor Piercing");
//				float armorPiercingPercentage = armorPiercingTrait.getMagnitude() / 100.0f;
				return /*ModList.get().isLoaded("footwork") ? FootworkHybridDamageSource.create(playerIn, armorPiercingPercentage, originalSource) :*/
					ModDamageTypes.armorPiercingMelee(playerIn);

//				return ModDamageTypes.armorPiercingMelee(playerIn);
			}
		}
		// Otherwise, return the basic player attack damage source
//		return playerIn.damageSources().playerAttack(playerIn);
		return originalSource;
	}
	
/*	@Redirect(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;doPostDamageEffects(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/Entity;)V"))
	private void doPostDamageEffects(LivingEntity livingEntityIn, Entity entityIn)
	{
		Log.debug("Intercepted EnchantmentHelper.doPostDamageEffects(Entity) method!");
		ItemStack weaponStack = livingEntityIn.getMainHandItem();
		if(weaponStack.getItem() instanceof IWeaponTraitContainer<?> container && 
				container.hasWeaponTraitWithType(WeaponTraits.TYPE_QUICK_STRIKE) && entityIn instanceof LivingEntity living)
		{
			living.invulnerableTime = Config.INSTANCE.quickStrikeHurtResistTicks.get();
			Log.debug("Set hurt time to " + Config.INSTANCE.quickStrikeHurtResistTicks.get() + " ticks");
		}
		livingEntityIn.setLastHurtMob(entityIn);
	}*/
}
