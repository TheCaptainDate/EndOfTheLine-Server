// base entity
package com.strangeiron.endoftheline.server.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.strangeiron.endoftheline.EotlResourcesManager;
import com.strangeiron.endoftheline.server.EotlWorld;
import com.strangeiron.endoftheline.server.components.Eotl2DModel;
import java.util.HashMap;

public abstract class EotlEntity {
    public int id;
    public boolean initiated;
    public float x;
    public float y;
    
    private BodyDef bodyDef;
    private boolean spawned;
    public Body physObject;
    public Eotl2DModel model;

    public void _init()
    {
        bodyDef = new BodyDef();
        bodyDef.position.set(0f, 0f);

        
        // now real init
        initiated = true;
        init();
    }
    
    public void _tick(float delta)
    {
        if(spawned)
        {
            x = physObject.getPosition().x;
            y = physObject.getPosition().y;
        }
    }
    
    public void spawn()
    {
        physObject = EotlWorld.b2dworld.createBody(bodyDef);
        model.applyToBody(physObject);
        spawned = true;
    }
    
    public void setModel(String modelPath)
    {
        model = EotlResourcesManager.getModel(modelPath);
    }
    
    public void setPhysicsType(BodyDef.BodyType type)
    {
        if(!spawned) bodyDef.type = type; else physObject.setType(type);
   }
    
    public  void setPosition(Vector2 vec)
    {
        if(!spawned) bodyDef.position.set(vec); else physObject.setTransform(vec, physObject.getAngle());
    }
    
        public  void setPosition(float x, float y)
    {
        if(!spawned) bodyDef.position.set(x, y); else physObject.setTransform(x, y, physObject.getAngle());
    }

    public abstract void tick(float delta);
    public abstract void init();
    public abstract HashMap<String, String> generateUpdateData();

}
