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

	private static String version = "0.0.1";
	public static String Tag = "End Of The Line";
	public static String ShortTag = "EOTL";
	
	public static final void main(String[] args) {
            Console console = System.console();
            if (console == null) 
            {
                EotlSettings.setConsoleMode(false);

                /* Set the Nimbus look and feel */
                //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
                try {
                    javax.swing.UIManager.setLookAndFeel(new WindowsClassicLookAndFeel());
                } catch (Exception e) {}
                //</editor-fold>

                new EotlConsole().setVisible(true);
            } else EotlSettings.setConsoleMode(true);
            
            EotlUtils.log("\"" + Tag + "\" server v" + version + " is loading");
            EotlSettings.setPathToJar(GetExecutionPath());
            
            // Проверяем существование файла настроек.
            File settings_file = new File(EotlSettings.getPathToJar() + File.separator + "config.yml");
            
            if(!settings_file.exists())
            {
                // AHTUNG, NO CONFIG!
                // DIE DIE DIE!!!!
                try {
                        settings_file.createNewFile();
                } catch (IOException e) {
                        EotlUtils.log_error("Can't create config file (config.yml)!");
                }
                EotlUtils.readAnyKeyExit("Please fill the config file - %somelinkinthefuture%");
            }

            // загружаем конфигурации
            EotlSettings.LoadSettings();

            // загружаем энтити менеджер
            EotlEntityManager.init();
            
            // загружаем сервер
            EotlNetwork.init();

             Thread GameLoop = new Thread(new EotlGameLoop());
             GameLoop.start();	
    }
	
	private static String GetExecutionPath(){
	    String absolutePath = EotlServer.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	    absolutePath = absolutePath.substring(0, absolutePath.lastIndexOf("/"));
	    return absolutePath;
	}

}
