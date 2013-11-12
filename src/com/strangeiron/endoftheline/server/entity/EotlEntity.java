// base entity
package com.strangeiron.endoftheline.server.entity;

import com.strangeiron.endoftheline.server.EotlInputManager;
import com.strangeiron.endoftheline.server.math.EotlVector2D;
import java.util.HashMap;

public abstract class EotlEntity {
    public float x;
    public int id;
    public float y;
    public float xVelocity;
    public float yVelocity;
    public boolean initiated;

    public void _init()
    {
        initiated = true;
        init();
    }
    
    public void _tick(double delta)
    {
        x = x + xVelocity;
        y = y + yVelocity;
        xVelocity = 0;
        yVelocity = 0;
    }
    
    public void applyForce(EotlVector2D vector)
    {
        this.xVelocity = this.xVelocity + vector.getX();
        this.yVelocity = this.yVelocity + vector.getY();
    }

    public abstract void tick(double delta);
    public abstract void init();
    public abstract HashMap<String, String> generateUpdateData();
}
