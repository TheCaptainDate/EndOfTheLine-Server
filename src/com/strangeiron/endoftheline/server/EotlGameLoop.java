package com.strangeiron.endoftheline.server;

import com.strangeiron.endoftheline.server.entity.EotlEntityManager;

public class EotlGameLoop implements Runnable {

	public int FPS = 0;
	
	private Double delta;
	private long lastFps = 0;
	
	private final int TARGET = 30;
	private final long OPTIMAL_TIME = 1000000000 / TARGET;   
	private boolean serverRunning = true;
	private EotlEntityManager entityManager = EotlEntityManager.GetInstance();
	
	@Override
	public void run() {
	
	    long lastLoop = System.currentTimeMillis();
	
	    while (serverRunning){
	
	        //Figure out how long its been since last update - used for entity calculation
	        long current = System.currentTimeMillis();
	        long updateTime = current - lastLoop;
	        lastLoop = current;
	
	        delta = updateTime / ((double)OPTIMAL_TIME);
	
	        lastFps += updateTime;
	        FPS++;
	        
	        System.out.println(FPS);
	        System.out.println(lastFps);
	        if(lastFps >= 1000000000){
	            System.out.println("(FPS: "+ FPS +")");
	            lastFps = 0;
	            FPS = 0;
	        }
	
	        //Updates state + calls render
	        entityManager.tick(delta);
	
	        /*
	         * We need each frame to take 10mill-sec
	         * Take recorded time + 10 milli
	         * then factor current time
	         * this ^ is final value to wait
	         */
	        try{
	            Thread.sleep( (lastLoop - System.currentTimeMillis() + OPTIMAL_TIME) / 1000000 );
	        }
	        catch(InterruptedException exception){
	            exception.printStackTrace();
	        }
	    }
	}
}
