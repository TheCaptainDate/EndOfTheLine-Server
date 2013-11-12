package com.strangeiron.endoftheline.server.entity;

import com.strangeiron.endoftheline.server.EotlInputManager;
import com.strangeiron.endoftheline.server.math.EotlVector2D;
import java.util.HashMap;

public class EotlCharacter extends EotlEntity{
	private final String TYPE = "Character";
        public boolean[] buttons = new boolean[64];
        
        @Override
        public void init() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
	
	@Override
	public void tick(double delta) {
            if(buttons[EotlInputManager.RIGHT]) 
                this.applyForce(new EotlVector2D(10, 0)); // @TODO: delta as FLOAT!!!
            
            if(buttons[EotlInputManager.LEFT]) 
                this.applyForce(new EotlVector2D(-10, 0));
            
            if(buttons[EotlInputManager.UP]) 
                this.applyForce(new EotlVector2D(0, 10));
            
            if(buttons[EotlInputManager.DOWN]) 
                this.applyForce(new EotlVector2D(0, -10));
	}

	@Override
	public HashMap<String, String> generateUpdateData() {
		HashMap<String, String> data = new HashMap<String, String>();
		
		data.put("type", TYPE); 
		data.put("x", String.valueOf(x)); 
		data.put("y", String.valueOf(y));
                data.put("id", String.valueOf(id));
		
		return data;
	}
        
        //public  @TODO: загрузка из класса.
}
