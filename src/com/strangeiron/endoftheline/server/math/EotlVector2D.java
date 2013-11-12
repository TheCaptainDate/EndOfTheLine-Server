package com.strangeiron.endoftheline.server.math;

public class EotlVector2D {
    private float x;
    private float y;

    public EotlVector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    public float getX()
    {
        return x;
    }
    
    public float getY()
    {
        return y;
    }
    
    public EotlVector2D multiply(float number)
    {
        return new EotlVector2D(x * number, y * number);
    }
    
    // пока другие методы не юзаем - не буду их писать. Время еще тратить :d
}
