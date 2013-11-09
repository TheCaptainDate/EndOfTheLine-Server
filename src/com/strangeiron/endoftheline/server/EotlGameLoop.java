package com.strangeiron.endoftheline.server;

import com.strangeiron.endoftheline.server.entity.EotlEntityManager;

public class EotlGameLoop implements Runnable {

	public int FPS = 0;
	
	private Double delta;
	private long lastFps = 0;
	
	private final int TARGET = 30;
	private final long OPTIMAL_TIME = 1000000000 / TARGET;   
	private boolean serverRunning = true;
	
	@Override
        @SuppressWarnings("SleepWhileInLoop")
	public void run() {
	
	    long lastLoop = System.currentTimeMillis();
	
	    while (serverRunning){
	        long current = System.currentTimeMillis();
	        long updateTime = current - lastLoop;
	        lastLoop = current;
	
	        delta = updateTime / ((double)OPTIMAL_TIME);
	        lastFps += updateTime;
	        FPS++;
	        
	        if(lastFps >= 1000){
	            lastFps = 0;
	            FPS = 0;
	        }
	
	        EotlEntityManager.tick(delta);
                
	        try{
	            Thread.sleep( (lastLoop - System.currentTimeMillis() + OPTIMAL_TIME) / 1000000 );
	        }
	        catch(InterruptedException exception){
	            exception.printStackTrace();
	        }
	    }
	}
}
