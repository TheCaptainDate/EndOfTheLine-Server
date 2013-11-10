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
    
	private static ArrayList<EotlEntity> entites = new ArrayList<EotlEntity>(); 
	
	public static void tick(double delta) 
	{
		for(EotlEntity ent : entites) 
		{
			ent.tick(delta);
			EotlNetwork.broadcastEntityUpdate(ent);
		}
	}
	
	public static void registerEntity(EotlEntity ent) 
	{
		entites.add(ent);
                ent.id = entites.indexOf(ent);
	}
}
