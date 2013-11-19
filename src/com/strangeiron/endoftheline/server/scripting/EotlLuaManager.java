
package com.strangeiron.endoftheline.server.scripting;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

public class EotlLuaManager {
    
    public static void init()
    {
        Globals globals = JsePlatform.standardGlobals();
    }
}
