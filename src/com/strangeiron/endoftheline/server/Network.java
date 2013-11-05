/**
 * Strange Iron indie-gamedev studio
 * @author: Maksim Fomin (Date)
 */
package com.strangeiron.endoftheline.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

public class Network {
	
	private static Network __instance = new Network();
	private Server server; // okay ;/
	
	private Network() {
	}
	
	public Network GetInstance() {
		return __instance;
	}
	
	public void init() 
	{
		// Создаем сервер со своей реализацией класса Connection 
		// (наша реализация будет хранить всю информацию игрока, в отличии от дефолной. Вот :P) // А это ок? 
		server = new Server() {
			protected Connection newConnection() {
				return new Player();
			}
		};
	}

}
