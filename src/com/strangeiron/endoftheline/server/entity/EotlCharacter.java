package com.strangeiron.endoftheline.server.entity;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.strangeiron.endoftheline.server.EotlInputManager;
import com.strangeiron.endoftheline.server.math.EotlVector2D;
import java.util.HashMap;

public class EotlCharacter extends EotlEntity{
	private final String TYPE = "Character";
        public boolean[] buttons = new boolean[64];
        
	@Override
        public void init()
        {
            setPhysicsType(BodyDef.BodyType.DynamicBody);
            setModel("test.mdl");
            model.setRestitution(1f);
            model.setFriction(0.5f);
            model.scale(300f);
            setPosition(40, 40);
            spawn();
        }
        
	@Override
	public void tick(float delta) {            
            if(buttons[EotlInputManager.RIGHT]) 
                applyImpulse(new Vector2(50, 0));
            
            if(buttons[EotlInputManager.LEFT]) 
                applyImpulse(new Vector2(-50, 0));
            
            if(buttons[EotlInputManager.UP]) 
               applyImpulse(new Vector2(0, 50));
            
            if(buttons[EotlInputManager.DOWN]) 
               applyImpulse(new Vector2(0, -50));
	}

	@Override
	public HashMap<String, String> generateUpdateData() {
		HashMap<String, String> data = new HashMap<String, String>();
               		
		data.put("type", TYPE); 
		data.put("x", String.valueOf(x)); 
		data.put("y", String.valueOf(y));
                data.put("id", String.valueOf(id));
                
                if(spawned)
                {
                    Vector2 vel = physObject.getLinearVelocity();
                    System.out.println("vel: " + vel.x + "  " + vel.y);
                    data.put("xVel", String.valueOf(vel.x));
                    data.put("yVel", String.valueOf(vel.y));
                }
		
		return data;
	}
        
        //public  @TODO: загрузка из класса.
}
