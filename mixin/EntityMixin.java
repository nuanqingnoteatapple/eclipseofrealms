package com.oblivioussp.spartanweaponry.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

@Mixin(Entity.class)
public class EntityMixin 
{
	@Shadow
	@Final
	public RandomSource random;
	
	@Shadow
	public Level level()
	{
		throw new IllegalStateException("Mixin failed to shadow the \"Entity.level()\" method!");
	}
	
	@Shadow
	public int getId()
	{
		throw new IllegalStateException("Mixin failed to shadow the \"Entity.getId()\" method!");
	}
	
	@Shadow
	public void discard()
	{
		throw new IllegalStateException("Mixin failed to shadow the \"Entity.discard()\" method!");
	}
	
	@Shadow
	public double getX()
	{
		throw new IllegalStateException("Mixin failed to shadow the \"Entity.getX()\" method!");
	}

	@Shadow
	public double getY()
	{
		throw new IllegalStateException("Mixin failed to shadow the \"Entity.getY()\" method!");
	}

	@Shadow
	public double getZ()
	{
		throw new IllegalStateException("Mixin failed to shadow the \"Entity.getZ()\" method!");
	}
}
