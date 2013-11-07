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
	
	private static  EotlEntityManager __instance = new EotlEntityManager();
	private EotlNetwork network;
	private ArrayList<EotlEntity> entites = new ArrayList<EotlEntity>(); 
	
	private EotlEntityManager() {
		 network = EotlNetwork.GetInstance();
	}
	
	public void tick(double delta) 
	{
		for(EotlEntity ent : entites) 
		{
			ent.tick(delta);
			EotlNetwork.GetInstance().broadcastEntityUpdate(ent);
		}
	}
	
	public static EotlEntityManager GetInstance() 
	{
		return __instance;
	}
	
	public void registerEntity(EotlEntity ent) 
	{
		entites.add(ent);
	}
}
