package com.strangeiron.endoftheline.server;

import com.strangeiron.endoftheline.server.gui.EotlConsole;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EotlUtils {
    private static EotlSettings settings = EotlSettings.GetInstance();
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    public static void readAnyKeyExit(String msg)
    {
        if(settings.isConsoleMode())
        {
            log(msg);
            log("Press ENTER to exit");

            try {
                    br.readLine();
            } catch (IOException e) {
                    e.printStackTrace();
            }
            System.exit(0);
        } else {
           EotlConsole.GetInstance().CrashWindow(msg); 
        }
    }
    
    public static void log(String msg)
    {
         if(settings.isConsoleMode())
         {
             System.out.println("EOTL: " + msg);
         } else {
             EotlConsole.GetInstance().AddLine(msg);
         }
    }
}
