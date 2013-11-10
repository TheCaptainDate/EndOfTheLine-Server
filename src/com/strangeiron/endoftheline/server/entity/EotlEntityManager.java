/**
 * Strange Iron indie-gamedev studio
 * @descr: Данный класс реализует управление энтитями
 * @author: Maksim Fomin (Date)
 */

package com.strangeiron.endoftheline.server.entity;

import java.io.EOFException;
import java.util.ArrayList;

import com.strangeiron.endoftheline.server.EotlNetwork;

public class EotlEntityManager {
    
	public static ArrayList<EotlEntity> entites = new ArrayList<EotlEntity>(); 
        
        public static void init()
        {
            entites.add(new EotlCharacter()); // Очень грязный способ. Дело в том, что
            // на клиенте 1-й индекс всегда занят локальным игроком, поэтому мы не должны здесь 
            // "случайно" задать 1-му индексу игрока..
        }
        
	public static void tick(double delta) 
	{
		for(EotlEntity ent : entites) 
		{
			ent.tick(delta);
		}
	}
	
	public static void registerEntity(EotlEntity ent) 
	{
		entites.add(ent);
                ent.id = entites.indexOf(ent);
	}
}
