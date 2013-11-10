package com.strangeiron.endoftheline.server.entity;

import java.util.HashMap;

public class EotlCharacter extends EotlEntity{
	private final String TYPE = "Character";
	private int modificator = 3;
	
	@Override
	public void tick(double delta) {
		if(x <= 10) modificator = 3;
		if(x >= 1000) modificator = -3;
		x = x + (float) (modificator * delta);
	}

	@Override
	public HashMap<String, String> generateUpdateData() {
		HashMap<String, String> data = new HashMap<String, String>();
		
		data.put("type", TYPE); 
		data.put("x", String.valueOf(x)); 
		data.put("y", String.valueOf(y)); 
		
		return data;
	}
	
}
