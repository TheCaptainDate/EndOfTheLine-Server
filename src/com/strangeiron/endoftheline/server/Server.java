/*
 * Strange Iron indie-gamedev studio
 * @author: Maksim Fomin (Date)
 */

package com.strangeiron.endoftheline.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.logging.Logger;

public class Server {

	private static Logger log = Logger.getLogger("");
	private static String version = "0.0.1";
	public static String Tag = "End Of The Line";
	public static String ShortTag = "EOTL";
	private static BufferedReader br;
	
	public static final void main(String[] args) {
		log.info("\"" + Tag + "\" server v" + version + " is loading");
		
		br = new BufferedReader(new InputStreamReader(System.in));
		Settings settings = Settings.GetInstance();
		
		try {
			settings.setPathToJar(Server.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
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
	}
	
	private static void readAnyKeyExit(String msg) {
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
