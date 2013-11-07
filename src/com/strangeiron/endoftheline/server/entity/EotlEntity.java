// base entity
package com.strangeiron.endoftheline.server.entity;

import java.util.HashMap;

public abstract class EotlEntity {
	public float x;
	public float y;

	public abstract void tick(double delta);
	public abstract HashMap<String, String> generateUpdateData();
}
