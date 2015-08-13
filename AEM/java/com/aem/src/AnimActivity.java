package com.aem.src;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

public abstract class AnimActivity extends Activity implements Runnable{

	private long timerDelay = 60;
	
	
	private SensorManager mSensorManager;
	
	private Sensor senseGyro;
	private Sensor senseAccel;
	
	private TriggerEventListener trigEventGyro;
	private TriggerEventListener trigEventAccel;
	
	protected View decorView;
	protected ActionBar actionBar;

	protected Point scrSize;
	
	private Graphics g;
	private Thread p;
	
	private volatile boolean running = false;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		g = new Graphics(this);
		requestWindowFeature(Window.FEATURE_ACTION_BAR);
		setContentView(g);

		decorView = this.getWindow().getDecorView();
		actionBar = this.getActionBar();
		
		Display display = getWindowManager().getDefaultDisplay();
		scrSize = new Point();
		display.getSize(scrSize);
		
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		
		senseGyro = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
		senseAccel = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		
		trigEventGyro = new TriggerEventListener() {

			@Override
			public void onTrigger(TriggerEvent arg0) {
				
				float[] arr = { arg0.values[0], arg0.values[1], arg0.values[2] };
				
				AnimActivity.this.onEventGyro(arr);			
			}
		};
		
		
		trigEventAccel = new TriggerEventListener() {

			@Override
			public void onTrigger(TriggerEvent arg0) {
				
				float[] arr = { arg0.values[0], arg0.values[1], arg0.values[2] };
				
				AnimActivity.this.onEventAccel(arr);			
			}
		};
		
		mSensorManager.requestTriggerSensor(trigEventGyro, senseGyro);
		mSensorManager.requestTriggerSensor(trigEventAccel, senseAccel);
	}
	
	
	/**
	 * Override this method in your class to use it
	 * 
	 * @param sensors x,y,z - Actual rotation of the device
	 */
	public void onEventGyro(float[] sensors) {
		
		
	}
	
	/**
	 * Override this method in your class to use it
	 * 
	 * @param sensors x,y,z - Change in rotation of the device
	 */
	public void onEventAccel(float[] sensors) {
		
		
	}
	
	

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		this.hideNavigation();
		
		p = new Thread(this);
		p.start();
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		running = true;
		
		p = new Thread(this);
        p.start();
        
		g.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		
		boolean retry = true;
		running = false;
		
		while(retry) {
			try {
				p.join();
				retry = false;
	        } 
	        catch (InterruptedException e) {
	        	e.printStackTrace();
	        }
	    }
		
		g.onPause();
	}
	
	private long lastTime, t;
	
	public void run() {
		
		lastTime = Long.valueOf(System.currentTimeMillis());
		
		while(running) {
			
			t = System.currentTimeMillis() - lastTime;
			
			if(t >= 1000 / timerDelay) {
				
				this.process();
				lastTime = Long.valueOf(System.currentTimeMillis());
			}
		}
	}

	public abstract void renderFrame(Graphics g);
	public abstract void process();
	
	private int FPS = 0, fpsLoop = 0;
	private long nextFPSTime = 0;
	
	public int getFPS() {
		
		return FPS;
	}
	
	public int getWidth() {
		
		return scrSize.x;
	}
	
	public int getHeight() {
		
		return scrSize.y;
	}
	
	public void calculateRenderFPS() {

		if (System.currentTimeMillis() >= nextFPSTime) {

			FPS = Integer.valueOf(fpsLoop);

			fpsLoop = 0;

			nextFPSTime = System.currentTimeMillis() + 1000L;
		} else
			fpsLoop++;
	}
	
	protected void hideNavigation() {

		decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
				| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
				| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
				| View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
				| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
	}

	private boolean closeNav = false;

	private boolean escapeNavigation() {
		
		int test = decorView.getSystemUiVisibility();

		if (test != View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) {
			if (!closeNav)
				closeNav = true;
			else {
				this.hideNavigation();
				closeNav = false;
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if(this.escapeNavigation())
			return true;
		
		return false;
	}
	
//	public int[] getGyroSensors() {
//		
//		
//	}


}
