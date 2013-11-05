/*
 * Strange Iron indie-gamedev studio
 * @author: Maksim Fomin (Date)
 */

package com.strangeiron.endoftheline.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.logging.Logger;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

public class Server {

	private static Logger log = Logger.getLogger("1");
	private static String version = "0.0.1";
	private static BufferedReader br;
	
	public static final void main(String[] args) {
		log.info("\"End Of The Line\" server v" + version + " is loading");
		
		br = new BufferedReader(new InputStreamReader(System.in));
		Settings settings = Settings.GetInstance();
		
		try {
			settings.setPathToJar(Server.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
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
		YamlReader reader = null;
		Map map = null;
		try {
			reader = new YamlReader(new FileReader(settings_file));
		} catch (FileNotFoundException e) {
			log.warning("Can't find config.yml!");
			e.printStackTrace();
		}
		try {
			 map = (Map) reader.read();
		} catch (YamlException e) {
			log.warning("Can't load config.yml (wrong yml syntax?!)");
			e.printStackTrace();
		}
		
		int port = Integer.parseInt((String) ((map.get("port") == null) ? settings.default_port : map.get("port")));
		settings.setPort(port);
		int max_players = Integer.parseInt((String) ((map.get("max_players") == null) ? settings.default_maxplrs : map.get("max_players")));
		settings.setMaxPlayers(max_players);
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
