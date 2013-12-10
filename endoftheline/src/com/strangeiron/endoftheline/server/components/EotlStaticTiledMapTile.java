/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.strangeiron.endoftheline.server.components;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTile;

/**
 * @brief Represents a non changing TiledMapTile (can be cached)
 */
public class EotlStaticTiledMapTile implements TiledMapTile {

	private int id;
	
	private TiledMapTile.BlendMode blendMode = TiledMapTile.BlendMode.ALPHA;
	
	private MapProperties properties;

	@Override
	public int getId () {
		return id;
	}

	@Override
	public void setId (int id) {
		this.id = id;
	}
	
	@Override
	public TiledMapTile.BlendMode getBlendMode () {
		return blendMode;
	}

	@Override
	public void setBlendMode (TiledMapTile.BlendMode blendMode) {
		this.blendMode = blendMode;
	}	
	
	/**
	 * @return tile's properties set
	 */
	@Override
	public MapProperties getProperties() {
		if (properties == null) {
			properties = new MapProperties();
		}
		return properties;
	}
	
	/**
	 * Creates a static tile with the given region
	 */
	public EotlStaticTiledMapTile() {
            
	}

    @Override
    public TextureRegion getTextureRegion() {
        throw new UnsupportedOperationException("Go away!"); 
    }
	
}