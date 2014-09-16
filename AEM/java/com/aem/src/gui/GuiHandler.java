package com.aem.src.gui;

import android.graphics.Color;

import com.aem.src.AnimActivity;
import com.aem.src.Graphics;

public class GuiHandler {

	private AnimActivity act;
	
	private Gui currentGui;
	private Gui debugGui;
	
	private int bg = Color.argb(160, 68, 68, 68);
	
	private boolean debugstate = false, debugenabled = false;
	
	public GuiHandler(AnimActivity act, Gui startGui) {
		
		this.act = act;
		this.currentGui = startGui;
	}
	
	public Gui getGui() {
		
		try {
			return currentGui;
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	public void addDebug(Gui debug) {
		this.debugGui = debug;

		this.debugenabled = true;
	}

	/**
	 * The current state of the Debug screen.
	 */
	public boolean getDebugState() {
		return debugstate;
	}

	/**
	 * Set the state of the debug screen.
	 */
	public void setDebugState(boolean state) {
		debugstate = state;
	}

	/**
	 * Inverts the current state of the debug screen. (ON/OFF)
	 */
	public void invertDebugState() {
		debugstate = !debugstate;
	}

	public int getBGColor() {
		try {
			return bg;
		} catch (NullPointerException e) {
			return 0;
		}
	}

	/**
	 * The current GUI being displayed.
	 */
	public Gui getCurrentGui() {
		return this.currentGui;
	}

	/**
	 * The last GUI visited.
	 */
	public Gui getPrevGui() {
		try {
			return this.currentGui.getParent();
		} catch (NullPointerException e) {
			return null;
		}
	}

	/**
	 * Updates everything about the GUI.
	 * 
	 * @param g
	 *            The graphics object.
	 * @param panel
	 *            put 'this' in here if in the class 'Game'.
	 */
	public void drawGui(Graphics g) {
		g.setColor(bg);
		g.fillRect(0, 0, act.getWidth(), act.getHeight());

		this.currentGui.drawGui(g);

		if (this.debugstate && debugenabled)
			this.debugGui.drawGui(g);
	}

	public void updateGui() {
		updateBG();
		this.currentGui.updateGui();

		if (this.debugstate && debugenabled)
			this.debugGui.updateGui();
	}
	
	/**
	 * Updates the color of the GUI to match the currently stored color.
	 */
	public void updateBG() {
		
		
		
//		int r = Color.red(bg), g = Color.green(bg), b = Color.blue(bg), a = Color.alpha(bg);
//
//		for (int loop = 0; loop < 5; loop++) {
//			if (r < this.currentGui.getBGColor().getRed())
//				r++;
//			else if (r > this.currentGui.getBGColor().getRed())
//				r--;
//
//			if (b < this.currentGui.getBGColor().getBlue())
//				b++;
//			else if (b > this.currentGui.getBGColor().getBlue())
//				b--;
//
//			if (g < this.currentGui.getBGColor().getGreen())
//				g++;
//			else if (g > this.currentGui.getBGColor().getGreen())
//				g--;
//
//			if (a < this.currentGui.getBGColor().getAlpha())
//				a++;
//			else if (a > this.currentGui.getBGColor().getAlpha())
//				a--;
//		}
//
//		bg = new Color(r, g, b, a);
	}
	
	/**
	 * ONLY FOR GUITRANSITION!
	 */
	public void setGui(Gui next) {
		currentGui = next;
	}

}
