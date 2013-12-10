package com.strangeiron.endoftheline.server.entity;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;
import com.strangeiron.endoftheline.server.EotlInputManager;
import com.strangeiron.endoftheline.server.EotlWorld;
import com.strangeiron.endoftheline.server.math.EotlVector2D;
import java.util.HashMap;

public class EotlCharacter extends EotlEntity{
	private final String TYPE = "Character";
        public boolean[] buttons = new boolean[64];
        
        private PlayerState state = PlayerState.IDLE;
        
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
            Vector2 vel = physObject.getLinearVelocity();
            
            if (Math.abs(vel.x) > MAX_VELOCITY) {
                vel.x = Math.signum(vel.x) * MAX_VELOCITY;
                physObject.setLinearVelocity(vel.x, vel.y);
            }

            
            if(buttons[EotlInputManager.RIGHT] && vel.x < MAX_VELOCITY) 
                applyImpulse(new Vector2(100, 0));
            
            if(buttons[EotlInputManager.LEFT] && vel.x < -MAX_VELOCITY) 
                applyImpulse(new Vector2(-100, 0));
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
        
        private boolean isPlayerGrounded () {
            Array<Contact> contactList = EotlWorld.b2dworld.getContactList();
            
            for (int i = 0; i < contactList.size; i++) {
                Contact contact = contactList.get(i);
                if (contact.isTouching())
                {
                    
                    boolean isplayer = false;
                    
                    for (int j = 0; j < model.fixtures.size(); j++) {
                        Fixture fixture = model.fixtures.get(j);
                        
                        if(contact.getFixtureA() == fixture || contact.getFixtureB() == fixture)
                        {
                            isplayer = true;
                        }
                    }

                    return isplayer;
                }
            }
            return false;
	}
        
        // static things
        private static float MAX_VELOCITY = 10f;
        
        private static enum PlayerState {
            WALK_RIGHT,
            WALK_LEFT,
            JUMP,
            IDLE
        }
        
        //public  @TODO: загрузка из класса.
}
