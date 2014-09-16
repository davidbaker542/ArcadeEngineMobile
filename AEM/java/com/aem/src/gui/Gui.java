package com.aem.src.gui;

import com.aem.src.AnimActivity;
import com.aem.src.Graphics;

public abstract class Gui {

	private AnimActivity act;
	
	public Gui(AnimActivity act) {
		
		this.act = act;
		
	}
	
	public Gui getParent() {
		
		//TODO
		return null;
	}
	
	public abstract void drawGui(Graphics g);
	public abstract void updateGui();
}
