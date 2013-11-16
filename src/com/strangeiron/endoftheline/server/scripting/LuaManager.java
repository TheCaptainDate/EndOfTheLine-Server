
package com.strangeiron.endoftheline.server.scripting;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

public class LuaManager {
    
    public static void init()
    {
        Globals globals = JsePlatform.standardGlobals();
        LuaValue chunk = globals.loadString("print('I love LUA ;3')", "some_lua_chunk");
        chunk.call();
    }
}
