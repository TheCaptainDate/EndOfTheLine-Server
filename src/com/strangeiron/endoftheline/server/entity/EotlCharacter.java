package com.strangeiron.endoftheline.server.entity;

import java.util.HashMap;

public class EotlCharacter extends EotlEntity{
	private final String TYPE = "Character";
	
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {

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
