package com.strangeiron.endoftheline.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;
import java.util.logging.Logger;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

public class EotlSettings {

    private static Logger log = Logger.getLogger("");
    private int port;
    private String pathToJar;
    private int maxPlayers;
    private static EotlSettings __instace = new EotlSettings();
    public final int default_port = 12345;
    public final int default_maxplrs = 5;
    private boolean consoleMode = false;



    static EotlSettings GetInstance()
    {
        return __instace;
    }

    public void reset()
    {
        __instace = new EotlSettings();
    }

    public void LoadSettings() {
        YamlReader reader = null;
        Map<?, ?> map = null; // @TODO: should that ?, ? be there? Or Map(Object, Object?)?
        try {
            reader = new YamlReader(new FileReader(pathToJar + File.separator + "config.yml"));
        } catch (FileNotFoundException e) {
            log.warning("Can't find config.yml!");
            e.printStackTrace();
        }
        try {
            map = (Map<?, ?>) reader.read();
        } catch (YamlException e) {
            log.warning("Can't load config.yml (wrong yml syntax?!)");
            e.printStackTrace();
        }

        int port = Integer.parseInt((String) ((map.get("port") == null) ? default_port : map.get("port")));
        setPort(port);
        int max_players = Integer.parseInt((String) ((map.get("max_players") == null) ? default_maxplrs : map.get("max_players")));
        setMaxPlayers(max_players);
    }

    public int getPort() {
        return port;
    }


    public void setPort(int port) {
        this.port = port;
    }


    public String getPathToJar() {
        return pathToJar;
    }


    public void setPathToJar(String pathToJar) {
        this.pathToJar = pathToJar;
    }


    public int getMaxPlayers() {
        return maxPlayers;
    }


    public void setMaxPlayers(int max_players) {
        this.maxPlayers = max_players;
    }
        
    public boolean isConsoleMode() {
        return consoleMode;
    }

    public void setConsoleMode(boolean consoleMode) {
        this.consoleMode = consoleMode;
    }

}
