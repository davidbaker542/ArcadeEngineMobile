package com.aem.src;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Graphics extends SurfaceView implements Runnable {

	private AnimActivity panel;
	
	private SurfaceHolder sfcHolder;
	private Paint paint;
	private Canvas canvas;
	
	private Thread t;
	
	private volatile boolean running = false;
	
	public Graphics(AnimActivity act) {
		
		super(act);
		
		panel = act;
		
		sfcHolder = this.getHolder();
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		
		t = new Thread(this);
		
		t.start();
	}
	
	public void onResume() {
		
		running = true;
        t = new Thread(this);
        t.start();
	}
	
	public void onPause() {
		
		boolean retry = true;
		running = false;
		while(retry) {
			try {
				t.join();
				retry = false;
	        } 
	        catch (InterruptedException e) {
	        	e.printStackTrace();
	        }
	    }
	}

	public Paint getPaint() {
		
		return paint;
	}
	
	public Canvas getCanvas() {
		
		return canvas;
	}

	public void setColor(int color) {
		
		paint.setColor(color);
	}
	
	public void drawString(String str, float x, float y) {
		
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		paint.setStrokeWidth(1);
		
		canvas.drawText(str, x, y, paint);
	}
	
	public void drawString(String str, float x, float y, float width, float size) {
		
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		paint.setStrokeWidth(width);
		paint.setTextSize(size);

		canvas.drawText(str, x, y, paint);
	}
	
	public void fillRect(float x, float y, float width, float height) {
		
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		paint.setStrokeWidth(1);
		
		canvas.drawRect(x, y, x + width, y + height, paint);
	}
	
	public void drawRect(float x, float y, float width, float height) {
		
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(1);
		
		canvas.drawRect(x, y, x + width, y + height, paint);
	}
	
	public void run() {
		
		while(running) {
			if(sfcHolder.getSurface().isValid()) {
				canvas = sfcHolder.lockCanvas();
				
				canvas.drawRGB(255, 255, 255);
				panel.renderFrame(this);
				 
				sfcHolder.unlockCanvasAndPost(canvas);
			}
		}
	}
	
}
