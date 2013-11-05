package com.strangeiron.endoftheline.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;
import java.util.logging.Logger;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

public class Settings {
	
	private static Logger log = Logger.getLogger("1");
	private int port;
	private String pathToJar;
	private int max_players;
	private static Settings __instace = new Settings();
	public final int default_port = 12345;
	public final int default_maxplrs = 5;
	
	static Settings GetInstance()
	{
		return __instace;
	}
	
	public void reset()
	{
		__instace = new Settings();
	}
	
	public void LoadSettings() {
		YamlReader reader = null;
		Map<?, ?> map = null; // @TODO: should that ?, ? be there? Or Map(Object, Object?)?
		try {
			reader = new YamlReader(new FileReader(pathToJar + File.separator + "config.yml"));
		} catch (FileNotFoundException e) {
			log.warning("Can't find config.yml!");
			e.printStackTrace();
		}
		try {
			 map = (Map<?, ?>) reader.read();
		} catch (YamlException e) {
			log.warning("Can't load config.yml (wrong yml syntax?!)");
			e.printStackTrace();
		}
		
		int port = Integer.parseInt((String) ((map.get("port") == null) ? default_port : map.get("port")));
		setPort(port);
		int max_players = Integer.parseInt((String) ((map.get("max_players") == null) ? default_maxplrs : map.get("max_players")));
		setMaxPlayers(max_players);
	}

	public int getPort() {
		return port;
	}


	public void setPort(int port) {
		this.port = port;
	}


	public String getPathToJar() {
		return pathToJar;
	}


	public void setPathToJar(String pathToJar) {
		this.pathToJar = pathToJar;
	}


	public int getMax_players() {
		return max_players;
	}


	public void setMaxPlayers(int max_players) {
		this.max_players = max_players;
	}

}
