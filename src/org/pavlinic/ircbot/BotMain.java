package org.pavlinic.ircbot;

import java.io.*;
import java.util.Properties;

public class BotMain {
    public static String idleChannel, staffChannel, botOwner, botUsername;
    
    public static void main(String[] args) throws Exception {
        Properties prop = new Properties();
        String configFile = "data/bot.properties";
        
        try {
            // If the settings doesn't exist, create it
            File file = new File(configFile);
            if(!file.exists()) {
                // Set default property values
                prop.setProperty("owner", "ipavl");
                prop.setProperty("username", "myBotName");
                prop.setProperty("password", "password");
                prop.setProperty("server", "localhost");
                prop.setProperty("idleChannel", "#NOTSET");
                prop.setProperty("staffChannel", "#NOTSET-staff");

                // Save properties
                prop.store(new FileOutputStream(configFile), null);
            }

            // Load properties file
            prop.load(new FileInputStream(configFile));

            // Store properties for later
            botOwner = prop.getProperty("owner");
            botUsername = prop.getProperty("username");
            idleChannel = prop.getProperty("idleChannel");
            staffChannel = prop.getProperty("staffChannel");
            
            // Start the bot
            Bot bot = new Bot();

            // Enable debugging output
            bot.setVerbose(true);
            
            // Connect to an IRC server
            bot.connect(prop.getProperty("server"));

            // Identify the bot with services
            bot.identify(prop.getProperty("password"));

            // Join channels
            bot.joinChannel(prop.getProperty("idleChannel"));
            bot.joinChannel(prop.getProperty("staffChannel"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}