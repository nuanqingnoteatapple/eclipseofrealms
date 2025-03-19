package com.oblivioussp.spartanweaponry.item;

import java.util.List;

import com.oblivioussp.spartanweaponry.ModSpartanWeaponry;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedSkullItem extends StandingAndWallBlockItem 
{

	public ExtendedSkullItem(Block floorBlockIn, Block wallBlockIn, Properties builder, Direction directionIn) 
	{
		super(floorBlockIn, wallBlockIn, builder, directionIn);
	}

	@Override
	public void appendHoverText(ItemStack stack, Level levelIn, List<Component> tooltip, TooltipFlag flagIn) 
	{
		tooltip.add(Component.translatable("tooltip." + ModSpartanWeaponry.ID + "." + ForgeRegistries.ITEMS.getKey(this).getPath() + ".desc").withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
		super.appendHoverText(stack, levelIn, tooltip, flagIn);
	}
}
