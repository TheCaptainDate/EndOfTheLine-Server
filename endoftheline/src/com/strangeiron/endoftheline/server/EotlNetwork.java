/**
 * Strange Iron indie-gamedev studio
 * @author: Maksim Fomin (Date)
 */
package com.strangeiron.endoftheline.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.strangeiron.endoftheline.server.entity.EotlCharacter;
import com.strangeiron.endoftheline.server.entity.EotlEntity;
import com.strangeiron.endoftheline.server.entity.EotlEntityManager;
import com.strangeiron.endoftheline.server.protocol.EotlLoginPacket;
import com.strangeiron.endoftheline.server.protocol.EotlEntityUpdatePacket;
import com.strangeiron.endoftheline.server.protocol.EotlGlobalUpdatePacket;
import com.strangeiron.endoftheline.server.protocol.EotlKeysUpdatePacket;
import com.strangeiron.endoftheline.server.protocol.EotlPlayer;
import com.strangeiron.endoftheline.server.protocol.EotlPlayerConnection;

public class EotlNetwork {
	private static Server server; // okay ;/
	private static final HashSet<EotlPlayer> players = new HashSet<EotlPlayer>();
	
	public static void init() 
	{
		// Создаем сервер со своей реализацией класса Connection 
		// (наша реализация будет хранить класс информации игрока, в отличии от дефолной. Вот :P) // А это ок? 
		server = new Server() {
                        @Override
			protected Connection newConnection() {
				return new EotlPlayerConnection();
			}
		};
		
		loadClasses(server);
		
		// listener
        server.addListener(new Listener() {
            @Override
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
                        
                        // записываем его
                        connection.Player = ply;
                    	
                    	// Создаем энтити игрока и отсылаем ее всем.
                    	 EotlCharacter character = new EotlCharacter();
                         ply.character = character;
                    	 character.x = 50;
                    	 character.y = 50;
                         
                         // Перед регистрацией энтити отсылаем игроку уже существующие энтити
                         broadcastEntsForPly(ply);
                         
                         // И только затем создаем его персонажа
                    	 EotlEntityManager.registerEntity(character);                         
                    	 broadcastNewCharacter(character, ply);
                    	
                    	EotlUtils.log("Player \"" + packet.Name + "\" has connected");
                    	return;
                    }
                    
                    if (object instanceof EotlKeysUpdatePacket) 
                    {                        
                        EotlKeysUpdatePacket packet = (EotlKeysUpdatePacket) object;
                        connection.Player.buttons = packet.buttons; 
                        
                        EotlKeysUpdatePacket pac = new EotlKeysUpdatePacket();
                        pac.buttons = packet.buttons;
                        pac.charId = connection.Player.character.id;
                        
                        for(EotlPlayer ply : players)
                    	{
                            if(ply.connection != connection) 
                            {
                                ply.connection.sendTCP(pac);
                            }
                        }
                        
                        connection.Player.character.buttons =  packet.buttons;
                    }
            }
            
            private boolean isValid (String value) {
                if (value == null) return false;
                value = value.trim();
                if (value.length() == 0) return false;
                return true;
		    }
		
		    public void disconnected (Connection c) {
		    	  EotlPlayerConnection connection = (EotlPlayerConnection)c;
		    	  players.remove(connection.Player);
		    	  connection.Player = null;
		    }
        });
        
        try {
			server.bind(EotlSettings.getPort());
		} catch (IOException e) {
			e.printStackTrace();
			EotlUtils.readAnyKeyExit("Error: Can't bind port!");
		}
        server.start();
	}
	
	private static void loadClasses(EndPoint endpoint)
	{
		Kryo kryo = endpoint.getKryo();
		kryo.register(EotlLoginPacket.class);
		kryo.register(HashMap.class);
		kryo.register(EotlEntityUpdatePacket.class);
                kryo.register(EotlKeysUpdatePacket.class);
                kryo.register(boolean[].class);
                kryo.register(HashMap[].class);
                kryo.register(EotlGlobalUpdatePacket.class);
	}
	
	public static void broadcastEntity(EotlEntity ent)
	{
		if(ent == null) return;
		
		EotlEntityUpdatePacket packet = new EotlEntityUpdatePacket();
		packet.data = ent.generateUpdateData();
		packet.data.put("action", "register");
		
		for(EotlPlayer ply : players)
		{
			ply.connection.sendTCP(packet);
		}
	}
        
        public static void broadcastNewCharacter(EotlEntity ent, EotlPlayer player)
	{
		if(ent == null) return;
                if(player == null) return;
		
		EotlEntityUpdatePacket packet = new EotlEntityUpdatePacket();
		packet.data = ent.generateUpdateData();
		packet.data.put("action", "register");
		
		for(EotlPlayer ply : players)
		{
                    if(!player.equals(ply))
                    {
                        ply.connection.sendTCP(packet);
                    }
		}
                
                packet.data.put("type", "LocalCharacter");
                player.connection.sendTCP(packet);
	}
        
        public static void broadcastEntsForPly(EotlPlayer ply)
        {
            if(ply == null) return;
            
            for (int i = 0; i < EotlEntityManager.entites.length; i++) {
                EotlEntity ent = EotlEntityManager.entites[i];
                
                if(ent == null) continue;                
                EotlEntityUpdatePacket packet = new EotlEntityUpdatePacket();
		packet.data = ent.generateUpdateData();
		packet.data.put("action", "register");
                
                ply.connection.sendTCP(packet);   
            }
        }
	
	public static void broadcastEntityUpdate(EotlEntity ent)
	{
		if(ent == null) return;
		EotlEntityUpdatePacket packet = new EotlEntityUpdatePacket();
		packet.data = ent.generateUpdateData();
		packet.data.put("action", "update");
		
		for(EotlPlayer ply : players)
		{
			ply.connection.sendTCP(packet);
		}
	}
        
        public static void globalUpdate()
        {                    
            HashMap[] ents = new HashMap[EotlEntityManager.entites.length];
            
            for (int i = 0; i < EotlEntityManager.entites.length; i++) {
                EotlEntity eotlEntity = EotlEntityManager.entites[i];
                
                if(eotlEntity == null || i == 0) continue;
                ents[i] = eotlEntity.generateUpdateData();
            }
            
            EotlGlobalUpdatePacket packet = new EotlGlobalUpdatePacket();
            packet.ents = ents;
            
            for(EotlPlayer ply : players)
            {
                if(ply == null) continue; // @TODO: добавить isValid и подчищать невалидных!!
                server.sendToTCP(ply.connection.getID(), packet);
            } 
        }
}
