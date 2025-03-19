package com.oblivioussp.spartanweaponry.api.trait;

import java.util.List;

import com.oblivioussp.spartanweaponry.api.SpartanWeaponryAPI;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

public class SweepWeaponTrait extends WeaponTraitWithMagnitude 
{

	public SweepWeaponTrait(String propType, String propModId) 
	{
		super(propType, propModId, TraitQuality.POSITIVE);
		isMelee = true;
	}

	@Override
	protected void addTooltipDescription(ItemStack stack, List<Component> tooltip)
	{
		if(level == 1.0f)
			tooltip.add(tooltipIndent().append(Component.translatable(String.format("tooltip.%s.trait.%s.fixed.desc", SpartanWeaponryAPI.MOD_ID, this.type), magnitude * 100.0f).withStyle(WeaponTrait.DESCRIPTION_FORMAT)));
		else
			tooltip.add(tooltipIndent().append(Component.translatable(String.format("tooltip.%s.trait.%s.desc", modId, this.type), magnitude * 100.0f).withStyle(WeaponTrait.DESCRIPTION_FORMAT)));
	}
	
	@Override
	public boolean isEnchantmentCompatible(Enchantment enchantIn) 
	{
		return magnitude == 1.0f && enchantIn == Enchantments.SWEEPING_EDGE;
	}
	
	@Override
	public boolean canPerformToolAction(ItemStack stack, ToolAction action) 
	{
		return action == ToolActions.SWORD_SWEEP;
	}
}
