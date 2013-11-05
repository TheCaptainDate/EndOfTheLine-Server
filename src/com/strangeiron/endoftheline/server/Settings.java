package com.strangeiron.endoftheline.server;

public class Settings {
	
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
	
	static void reset()
	{
		__instace = new Settings();
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
