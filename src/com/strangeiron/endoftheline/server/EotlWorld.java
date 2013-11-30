/**
 * Данный класс реализует базовые методы для всего в игре: 
 * Доступ к энтитям, физика, загрузка карты - все здесь
 */

package com.strangeiron.endoftheline.server;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.World;

public class EotlWorld {
    
    public static World b2dworld;
    
    public static void init()
    {
        b2dworld = new World(new Vector2(0, 0), true);

        // debug, @TODO: REMOVE!
        Body ground = createEdge(BodyType.StaticBody, 0, 790, 1280,790, 1);
    }
    
    public static void tick(double delta) 
    {
        b2dworld.step(1f / 62f, 4, 4);
    }
    
    /* Physics goes here: 
    @TODO: сделать для физики отдельный враппер?!
    */
    
    public static Body createEdge (BodyType type, float x1, float y1, float x2, float y2, float density) {
        BodyDef def = new BodyDef();
        def.type = type;
        Body box = b2dworld.createBody(def);

        EdgeShape poly = new EdgeShape();
        poly.set(new Vector2(0, 0), new Vector2(x2 - x1, y2 - y1));
        box.createFixture(poly, density);
        box.setTransform(x1, y1, 0);
        poly.dispose();

        return box;
    }
    
}
