/*
 * Strange Iron indie-gamedev studio
 * @author: Maksim Fomin (Date)
 */

package com.strangeiron.endoftheline.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import com.strangeiron.endoftheline.server.entity.EotlEntityManager;

public class EotlServer {

	private static Logger log = Logger.getLogger("");
	private static String version = "0.0.1";
	public static String Tag = "End Of The Line";
	public static String ShortTag = "EOTL";
	private static BufferedReader br;
	private static EotlSettings settings;
	private static EotlNetwork network;
	private static boolean ServerRunning = true;
	private static long lastLoopTime = System.nanoTime();
	private static long lastFpsTime;
	private static int fps;
	private final static int TARGET_FPS = 30;
	private final static long OPTIMAL_TIME = 1000000000 / TARGET_FPS; 
	
	public static final void main(String[] args) {
		log.info("\"" + Tag + "\" server v" + version + " is loading");
		
		br = new BufferedReader(new InputStreamReader(System.in));
		settings = EotlSettings.GetInstance();
		
		settings.setPathToJar(GetExecutionPath());
		
		// Проверяем существование файла настроек.
		File settings_file = new File(settings.getPathToJar() + File.separator + "config.yml");

		if(!settings_file.exists())
		{
			// AHTUNG, NO CONFIG!
			// DIE DIE DIE!!!!
			try {
				settings_file.createNewFile();
			} catch (IOException e) {
				log.warning("Can't create config.yml!");
				e.printStackTrace();
			}
			readAnyKeyExit("Please fill the config file - %somelinkinthefuture%");
		}
		
		// загружаем конфигурации
		settings.LoadSettings();
		
		// загружаем сервер
		network = EotlNetwork.GetInstance();
		network.init();
		
		EotlEntityManager entityManager = EotlEntityManager.GetInstance();
		
		while(ServerRunning)
		{
			 // work out how long its been since the last update, this
		      // will be used to calculate how far the entities should
		      // move this loop
		      long now = System.nanoTime();
		      long updateLength = now - lastLoopTime;
		      lastLoopTime = now;
		      double delta = updateLength / ((double)OPTIMAL_TIME);

		      // update the frame counter
		      lastFpsTime += updateLength;
		      fps++;
		      
		      // update our FPS counter if a second has passed since
		      // we last recorded
		      if (lastFpsTime >= 1000000000)
		      {
		         System.out.println("(FPS: "+fps+")");
		         lastFpsTime = 0;
		         fps = 0;
		      }
		      
		      // update the game logic
		      entityManager.tick(delta);
		      
		      // we want each frame to take 10 milliseconds, to do this
		      // we've recorded when we started the frame. We add 10 milliseconds
		      // to this and then factor in the current time to give 
		      // us our final value to wait for
		      // remember this is in ms, whereas our lastLoopTime etc. vars are in ns.
	    	  //try {
				//Thread.sleep( (lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000 );
			//} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
			//}
		    	  
		}
	}
	
	private static String GetExecutionPath(){
	    String absolutePath = EotlServer.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	    absolutePath = absolutePath.substring(0, absolutePath.lastIndexOf("/"));
	    return absolutePath;
	}
	
	public static void readAnyKeyExit(String msg) {
		log.warning(msg);
		log.info("Press ENTER to exit");
		
		try {
			br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

}
