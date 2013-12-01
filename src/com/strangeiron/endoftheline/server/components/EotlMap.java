package com.strangeiron.endoftheline.server.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.ExternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.strangeiron.endoftheline.server.EotlSettings;
import com.strangeiron.endoftheline.server.EotlWorld;
import java.io.File;

public class EotlMap {

    private static TiledMap tiledmap;
    private static final TmxMapLoader mapLoader = new TmxMapLoader(new EotlFileHandleResolver());

    public EotlMap(String name) {
        if (!new File(EotlSettings.getPathToContent() + "maps/" + name + ".tmx").exists()) {
            throw new RuntimeException("Can't load map \"" + name + "\": file not found.");
        }
        
        tiledmap = mapLoader.load("maps/" + name + ".tmx");
        loadCollisions();
    }

    private void loadCollisions() {
        TiledMapTileLayer collisionsLayer = (TiledMapTileLayer) tiledmap.getLayers().get("collisions");

        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.StaticBody;

        boolean row = false;
        float xStart = 0, yStart = 0;

        float lenght = 0f;

        for (int y = 1; y <= collisionsLayer.getHeight(); y++) {
            for (int x = 1; x <= collisionsLayer.getWidth(); x++) {
                Cell cell = collisionsLayer.getCell(x, y);

                TiledMapTile tile = null;

                if (cell != null) {
                    tile = cell.getTile();
                }

                if (tile != null) {
                    if (!row) {
                        row = true;
                        xStart = collisionsLayer.getTileWidth() * x - collisionsLayer.getTileWidth();
                        yStart = collisionsLayer.getTileHeight() * y - collisionsLayer.getTileHeight();
                    }

                    lenght = lenght + 1f;
                } else {
                    if (row) {
                        Body collider = EotlWorld.b2dworld.createBody(bd);
                        PolygonShape edge = new PolygonShape();

                        Vector2[] verts = {
                            new Vector2(0f, 0f),
                            new Vector2(collisionsLayer.getTileWidth() * lenght, 0f),
                            new Vector2(0f, collisionsLayer.getTileHeight()),
                            new Vector2(collisionsLayer.getTileWidth() * lenght, collisionsLayer.getTileHeight())
                        };

                        edge.set(verts);

                        collider.createFixture(edge, 1f);
                        collider.setTransform(new Vector2(xStart, yStart), 0f);

                        edge.dispose();
                        lenght = 0f;
                        row = false;
                    }
                }
            }
        }

    }

}
