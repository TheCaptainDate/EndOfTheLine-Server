/**
 * Strange Iron indie-gamedev studio
 * @author: Maksim Fomin (Date)
 */
package com.strangeiron.endoftheline.server;

import java.io.IOException;
import java.util.HashSet;
import java.util.logging.Logger;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.strangeiron.endoftheline.server.entity.EotlCharacter;
import com.strangeiron.endoftheline.server.entity.EotlEntity;
import com.strangeiron.endoftheline.server.entity.EotlEntityManager;
import com.strangeiron.endoftheline.server.protocol.EotlLoginPacket;
import com.strangeiron.endoftheline.server.protocol.EotlNewEntityPacket;
import com.strangeiron.endoftheline.server.protocol.EotlPlayer;
import com.strangeiron.endoftheline.server.protocol.EotlPlayerConnection;

public class EotlNetwork {
	
	private static EotlNetwork __instance = new EotlNetwork();
	private Server server; // okay ;/
	private EotlSettings settings;
	private final Logger log = Logger.getLogger("");
	private HashSet<EotlPlayer> players = new HashSet<EotlPlayer>();
	private EotlEntityManager entityManager = EotlEntityManager.GetInstance();
	
	private EotlNetwork() {
		settings = EotlSettings.GetInstance();
	}
	
	public static EotlNetwork GetInstance() {
		return __instance;
	}
	
	public void init() 
	{
		// Создаем сервер со своей реализацией класса Connection 
		// (наша реализация будет хранить класс информации игрока, в отличии от дефолной. Вот :P) // А это ок? 
		server = new Server() {
			protected Connection newConnection() {
				return new EotlPlayerConnection();
			}
		};
		
		loadClasses(server);
		
		// listener
        server.addListener(new Listener() {
            public void received (Connection c, Object object) {
                    EotlPlayerConnection connection = (EotlPlayerConnection)c;
                    
                    if(object instanceof EotlLoginPacket)
                    {
                    	if(connection.Player != null) return; // повторная авторизация? Ну вдруг... 	
                    	EotlLoginPacket packet = (EotlLoginPacket) object;
                   
                    	if(!isValid(packet.Name)) 
                    	{
                    		connection.close();
                    		return;
                    	}
                    	
                    	for(EotlPlayer ply : players)
                    	{
                    		if(ply.Name.equals(packet.Name)) 
                    		{
                    			c.close();
                    			return;
                    		}
                    	}
                    	// Создаем класс игрока
                    	EotlPlayer ply = new EotlPlayer();
                    	ply.Name = packet.Name;
                    	ply.connection = connection;
                    	players.add(ply);
                    	
                    	// Создаем энтити игрока и отсылаем ее всем.
                    	 EotlCharacter character = new EotlCharacter();
                    	 character.x = 50;
                    	 character.y = 50;
                    	 entityManager.registerEntity(character);
                    	
                    	log.info("Player \"" + packet.Name + "\" has connected");
                    	return;
                    }
            }
            
            private boolean isValid (String value) {
                if (value == null) return false;
                value = value.trim();
                if (value.length() == 0) return false;
                return true;
		    }
		
		    public void disconnected (Connection c) {
		           
		    }
        });
        
        try {
			server.bind(settings.getPort());
		} catch (IOException e) {
			e.printStackTrace();
			EotlServer.readAnyKeyExit("Error: Can't bind port!");
		}
        server.start();
	}
	
	private void loadClasses(EndPoint endpoint)
	{
		Kryo kryo = endpoint.getKryo();
		kryo.register(EotlLoginPacket.class);
	}
	
	public void createEntity(EotlEntity ent)
	{
		EotlNewEntityPacket packet = new EotlNewEntityPacket();
		packet.entity = ent;
		
		for(EotlPlayer ply : players)
		{
			ply.connection.sendTCP(packet);
		}
	}

}
