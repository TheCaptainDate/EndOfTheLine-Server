package com.strangeiron.endoftheline;

import com.strangeiron.endoftheline.server.components.Eotl2DModel;
import com.strangeiron.endoftheline.server.components.EotlModelsHandler;
import java.util.HashMap;

public class EotlResourcesManager {
	
        private static final HashMap<String, Eotl2DModel> models = new HashMap<String, Eotl2DModel>();
        
        public static Eotl2DModel getModel(String path)
        {
            if(models.containsKey(path)) {
                try {
                    return models.get(path).clone();
                } catch (CloneNotSupportedException ex) {
                    // @TODO: ERROR
                }
            }
            
            if(loadModel(path))
            {
                try {
                    return models.get(path).clone();
                } catch (CloneNotSupportedException ex) {
                    // @TODO: ERROR
                }
            }
            
            throw new RuntimeException("Model \"models/" + path + "\" was not found."); // @TODO: сделать, чтобы не крашило, а выводило ошибку!!!
        }
        
        public static boolean loadModel(String path)
        {
            Eotl2DModel model = EotlModelsHandler.handle(path);
            
            if(model != null)
            {
                models.put(path, model);
                return true;
            }
            
            return false;
        }
}
