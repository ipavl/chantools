package org.pavlinic.ircbot;

import java.io.*;
import java.util.Properties;

public class BotMain {
    public static String idleChannel, staffChannel, botOwner, botUsername;
    public static boolean allowTime, allowKick, allowBan, allowOps, allowDebug;
    
    public static void main(String[] args) throws Exception {
        Properties prop = new Properties();
        String configFile = "data/bot.properties";
        
        try {
            File dir = new File("data");
            dir.mkdir(); 
            
            // If the settings doesn't exist, create it
            File file = new File(configFile);
            if(!file.exists()) {
                System.out.println("\n ******************************************************************************");
                System.out.println(" ** NOTICE ** THIS APPEARS TO BE THE FIRST TIME YOU'VE RUN THE BOT. YOU MUST **");
                System.out.println(" ** NOTICE ** EDIT THE bot.properties FILE IN THE data FOLDER BEFORE THE BOT **");
                System.out.println(" ** NOTICE ** WILL BE ABLE TO CONNECT TO A SERVER THAT ISN'T A LOCALHOST ONE **");
                System.out.println(" ******************************************************************************\n");
                
                // Set default property values
                prop.setProperty("owner", "ipavl");
                prop.setProperty("username", "myBotName");
                prop.setProperty("password", "password");
                prop.setProperty("server", "localhost");
                prop.setProperty("idleChannel", "#NOTSET");
                prop.setProperty("staffChannel", "#NOTSET-staff");
                
                // Command permissions
                prop.setProperty("allowTime", "true");
                prop.setProperty("allowKick", "true");
                prop.setProperty("allowBan", "true");
                prop.setProperty("allowOps", "true");
                prop.setProperty("allowDebug", "true");

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
            
            Boolean.valueOf(prop.getProperty("allowTime"));
            Boolean.valueOf(prop.getProperty("allowKick"));
            Boolean.valueOf(prop.getProperty("allowBan"));
            Boolean.valueOf(prop.getProperty("allowOps"));
            Boolean.valueOf(prop.getProperty("allowDebug"));
            
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