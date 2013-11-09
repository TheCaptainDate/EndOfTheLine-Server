package com.strangeiron.endoftheline.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;
import java.util.logging.Logger;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

public class EotlSettings {

    private static int port;
    private static String pathToJar;
    private static int maxPlayers;
    public static final int default_port = 12345;
    public static final int default_maxplrs = 5;
    private static boolean consoleMode = false;

    public static void LoadSettings() {
        YamlReader reader = null;
        Map<?, ?> map = null; // @TODO: should that ?, ? be there? Or Map(Object, Object?)?
        try {
            reader = new YamlReader(new FileReader(pathToJar + File.separator + "config.yml"));
        } catch (FileNotFoundException e) {
            EotlUtils.log_error("Can't find config.yml!");
        }
        try {
            map = (Map<?, ?>) reader.read();
        } catch (YamlException e) {
            EotlUtils.log_error("Can't load config.yml (wrong yml syntax?!)");
        }

        int port = Integer.parseInt((String) ((map.get("port") == null) ? default_port : map.get("port")));
        setPort(port);
        int max_players = Integer.parseInt((String) ((map.get("max_players") == null) ? default_maxplrs : map.get("max_players")));
        setMaxPlayers(max_players);
    }

    public static int getPort() {
        return port;
    }


    public static void setPort(int port) {
        EotlSettings.port = port;
    }


    public static String getPathToJar() {
        return pathToJar;
    }


    public static void setPathToJar(String pathToJar) {
        EotlSettings.pathToJar = pathToJar;
    }


    public static int getMaxPlayers() {
        return maxPlayers;
    }


    public static void setMaxPlayers(int max_players) {
       EotlSettings.maxPlayers = max_players;
    }
        
    public static boolean isConsoleMode() {
        return consoleMode;
    }

    public static void setConsoleMode(boolean consoleMode) {
        EotlSettings.consoleMode = consoleMode;
    }

}
