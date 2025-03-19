package com.oblivioussp.spartanweaponry.client.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ToggleImageButton extends Button
{
	protected final ResourceLocation textureLocation;
	protected final int texStartU, texStartV;
	protected final int toggleDiffU, hoverDiffV;
	protected final int textureWidth, textureHeight;
	private boolean toggleState;

	public ToggleImageButton(boolean isToggled, int xPos, int yPos, int width, int height, int texU, int texV,
			int texToggleDiffU, int texHoverDiffV, ResourceLocation textureLoc, int texWidth, int texHeight, OnPress onPress,
			Component component) 
	{
		super(xPos, yPos, width, height, component, onPress, DEFAULT_NARRATION);
		texStartU = texU;
		texStartV = texV;
		toggleDiffU = texToggleDiffU;
		hoverDiffV = texHoverDiffV;
		textureLocation = textureLoc;
		textureWidth = texWidth;
		textureHeight = texHeight;
		toggleState = isToggled;
	}
	
	@Override
	public void onPress()
	{
		toggleState = !toggleState;
		super.onPress();
	}

	@Override
	public void renderWidget(GuiGraphics guiGraphics, int renderX, int renderY, float p_94285_) 
	{
		super.renderWidget(guiGraphics, renderX, renderY, p_94285_);
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, textureLocation);
		float u = texStartU;
		float v = texStartV;
		
		if(toggleState)
			u += toggleDiffU;
		if(isHovered())
			v += hoverDiffV;
		
		RenderSystem.enableDepthTest();
		guiGraphics.blit(textureLocation, getX(), getY(), u, v, width, height, textureWidth, textureHeight);
//		if(isHovered)
//			renderToolTip(guiGraphics, renderX, renderY);
	}
}
