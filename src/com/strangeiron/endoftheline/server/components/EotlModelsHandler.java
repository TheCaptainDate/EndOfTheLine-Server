/**
 * Данный класс парсит json данные и формирует из них физ. модели.
 */

package com.strangeiron.endoftheline.server.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.strangeiron.endoftheline.server.EotlSettings;
import com.strangeiron.endoftheline.server.physics.CircleModel;
import com.strangeiron.endoftheline.server.physics.PolygonModel;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EotlModelsHandler {
    
    private static JsonReader reader = new JsonReader();
    private final static List<Eotl2DModel> modelsList = new ArrayList<Eotl2DModel>();
    private static Eotl2DModel model;
    
    public static Eotl2DModel handle(String modelPath)
    {
        FileHandle file = new FileHandle(new File(EotlSettings.getPathToJar() + "/content/models/" + modelPath));
        System.out.println(file.path());
        
        if(!file.exists()) throw new RuntimeException("File \"models/" + modelPath + "\" was not found.");
        
        JsonValue models = reader.parse(file).get("rigidBodies");
        
        
        for (JsonValue.JsonIterator modelsIterator = models.iterator(); modelsIterator.hasNext();) {
            JsonValue modelData = modelsIterator.next();
            model = new Eotl2DModel();
            
            model.name = modelData.getString("name");
            model.texture = modelData.getString("imagePath");
            
            JsonValue origin = modelData.get("origin");
            model.origin.x = origin.getFloat("x");
            model.origin.y = origin.getFloat("y");
            
            // Цикл по полигонам
            JsonValue polygons = modelData.get("polygons");
            for (JsonValue.JsonIterator polygonsIterator = polygons.iterator(); polygonsIterator.hasNext();) {
                JsonValue polygonData = polygonsIterator.next();
                PolygonModel polygon = new PolygonModel();
                model.polygons.add(polygon);
                
                // Цикл по вершинам
                for (JsonValue.JsonIterator verticesIterator = polygonData.iterator(); verticesIterator.hasNext();) {
                    JsonValue vertex = verticesIterator.next();
                    
                    float x = vertex.getFloat("x");
                    float y = vertex.getFloat("y");
                    
                    polygon.vertices.add(new Vector2(x, y));
                }
                
                polygon.buffer = new Vector2[polygon.vertices.size()];
            }
            
            // Цикл по окружностям
            JsonValue circles = modelData.get("circles");
            for (JsonValue.JsonIterator circlesIterator = circles.iterator(); circlesIterator.hasNext();) {
                JsonValue circleData = circlesIterator.next();
                CircleModel circle = new CircleModel();
                model.circles.add(circle);
                
                circle.center.x = circleData.getFloat("cx");
                circle.center.y = circleData.getFloat("cy");
                circle.radius = circleData.getFloat("r");
            }
        }
        
        model.init();
        return model;
    }
}
