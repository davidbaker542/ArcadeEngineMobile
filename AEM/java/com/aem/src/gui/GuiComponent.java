package com.aem.src.gui;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import com.aem.src.AnimActivity;

import android.graphics.Point;

public abstract class GuiComponent {
	
	protected final AnimActivity act;
	protected Rectangle info;
	
	protected boolean enabled = true;
	
	public GuiComponent(AnimActivity act, int x, int y, int w, int h) {
		
		this.act = act;
		
		info = new Rectangle(x, y, w, h);
	}
	
	public boolean isEnabled() {
		return this.enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
