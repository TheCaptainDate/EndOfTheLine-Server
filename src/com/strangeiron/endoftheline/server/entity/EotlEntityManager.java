/**
 * Strange Iron indie-gamedev studio
 * @descr: Данный класс реализует управление энтитями
 * @author: Maksim Fomin (Date)
 */

package com.strangeiron.endoftheline.server.entity;

import java.io.EOFException;
import java.util.ArrayList;

import com.strangeiron.endoftheline.server.EotlNetwork;
import com.strangeiron.endoftheline.server.EotlUtils;

public class EotlEntityManager {
    
	public static EotlEntity[] entites = new EotlEntity[100]; 
        
        public static void init()
        {
            
        }
        
	public static void tick(double delta) 
	{
            for (int i = 0; i < entites.length; i++) 
            {
                EotlEntity eotlEntity = entites[i];
                
                if(eotlEntity != null)
                {
                    eotlEntity._tick(delta);
                    eotlEntity.tick(delta);
                }
            }
	}
	
	public static void registerEntity(EotlEntity ent) 
	{
            int id = getFreeId();
            entites[id] = ent;
            ent.id = id;
	}
        
        public static int getFreeId()
        {
            for (int i = 0; i < entites.length; i++) {
                if(checkIsIdFree(i))
                {
                    return i;
                }
            }
            
            EotlUtils.log_error("Error: no more space for ents!");
            return -1;           
        }
        
        public static boolean checkIsIdFree(int id)
        {
            if(entites[id] != null || id == 0)
            {
                return false;
            }
            
            return true;
        }
}
