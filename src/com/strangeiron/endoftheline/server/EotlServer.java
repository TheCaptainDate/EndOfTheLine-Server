/*
 * Strange Iron indie-gamedev studio
 * @author: Maksim Fomin (Date)
 */

package com.strangeiron.endoftheline.server;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import com.strangeiron.endoftheline.server.entity.EotlEntityManager;
import com.strangeiron.endoftheline.server.gui.EotlConsole;
import com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel;
import java.io.Console;

public class EotlServer {

	private static Logger log = Logger.getLogger("");
	private static String version = "0.0.1";
	public static String Tag = "End Of The Line";
	public static String ShortTag = "EOTL";
	
	private static EotlSettings settings;
	private static EotlNetwork network;
	
	public static final void main(String[] args) {
		log.info("\"" + Tag + "\" server v" + version + " is loading");
		settings = EotlSettings.GetInstance();
		settings.setPathToJar(GetExecutionPath());
                
                Console console = System.console();
                if (console == null) 
                {
                    settings.setConsoleMode(false);
                   
                    /* Set the Nimbus look and feel */
                    //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
                    try {
                        javax.swing.UIManager.setLookAndFeel(new WindowsClassicLookAndFeel());
                    } catch (Exception e) {}
                    //</editor-fold>
                    
                    new EotlConsole().setVisible(true);
                } else settings.setConsoleMode(true);
		
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
			EotlUtils.readAnyKeyExit("Please fill the config file - %somelinkinthefuture%");
		}
		
		// загружаем конфигурации
		settings.LoadSettings();
		
		// загружаем сервер
		network = EotlNetwork.GetInstance();
		network.init();
		
		final EotlEntityManager entityManager = EotlEntityManager.GetInstance();
		
		 Thread GameLoop = new Thread(new EotlGameLoop());
		 GameLoop.start();	
	}
	
	private static String GetExecutionPath(){
	    String absolutePath = EotlServer.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	    absolutePath = absolutePath.substring(0, absolutePath.lastIndexOf("/"));
	    return absolutePath;
	}

}
