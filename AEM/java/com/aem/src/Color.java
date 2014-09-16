package com.aem.src;

public class Color {

	private int r, g, b, a;
	
	public Color(int red, int green, int blue) {
		
		this(red, green, blue, 255);
	}
	
	public Color(int red, int green, int blue, int alpha) {
		r = red;
		g = green;
		b = blue;
		a = alpha;
	}

	public int getRed() {
		return r;
	}
	
	public int getGreen() {
		return g;
	}
	
	public int getBlue() {
		return b;
	}
	
	public int getAlpha() {
		return a;
	}
	
	public int getRGB() {
		
		return android.graphics.Color.argb(a, r, g, b);
	}
}
