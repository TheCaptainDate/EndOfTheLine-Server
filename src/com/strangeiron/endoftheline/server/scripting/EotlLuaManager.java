
package com.strangeiron.endoftheline.server.scripting;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

public class LuaManager {
    
    public static void init()
    {
        Globals globals = JsePlatform.standardGlobals();
        LuaValue chunk = globals.loadString("function absd() print('R@P') end", "some_lua_chunk");
        LuaValue chunk2 = globals.loadString("absd()", "some_lua_chunk2");
        chunk.call();
        chunk2.call();
    }
}
