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
            model.scale(300f);
            setPosition(40, 40);
            spawn();
        }
        
	@Override
	public void tick(float delta) {          
            System.out.println(buttons[EotlInputManager.RIGHT]);
            
            if(buttons[EotlInputManager.RIGHT]) 
                physObject.applyForce(new Vector2(1000, 0).scl(1f / 60f), physObject.getPosition(), true);
            
            if(buttons[EotlInputManager.LEFT]) 
                physObject.applyForce(new Vector2(-1000, 0).scl(1f / 60f), physObject.getPosition(), true);
            
            if(buttons[EotlInputManager.UP]) 
                physObject.applyForce(new Vector2(0, 1000).scl(1f / 60f), physObject.getPosition(), true);
            
            if(buttons[EotlInputManager.DOWN]) 
                physObject.applyForce(new Vector2(0, -1000).scl(1f / 60f), physObject.getPosition(), true);
            
            System.out.println(physObject.getPosition().toString());
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
