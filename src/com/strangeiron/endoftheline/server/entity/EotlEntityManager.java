/**
 * Strange Iron indie-gamedev studio
 * @descr: Данный класс реализует управление энтитями
 * @author: Maksim Fomin (Date)
 */

package com.strangeiron.endoftheline.server.entity;

import java.util.ArrayList;

import com.strangeiron.endoftheline.server.EotlNetwork;

public class EotlEntityManager {
	
	private static  EotlEntityManager __instance = new EotlEntityManager();
	private EotlNetwork network;
	private ArrayList<EotlEntity> entites = new ArrayList<EotlEntity>(); 
	
	private EotlEntityManager() {
		 network = EotlNetwork.GetInstance();
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
