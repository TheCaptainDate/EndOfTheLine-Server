
package com.strangeiron.endoftheline.server.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.strangeiron.endoftheline.server.EotlSettings;

public class EotlFileHandleResolver implements FileHandleResolver {

    @Override
    public FileHandle resolve(String fileName) {
        return new FileHandle(EotlSettings.getPathToContent() + fileName);
    }
    
    
}
