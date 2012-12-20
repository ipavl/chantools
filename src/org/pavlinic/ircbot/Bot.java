package org.pavlinic.ircbot;

import org.jibble.pircbot.*;
import org.pavlinic.ircbot.features.*;

public class Bot extends PircBot {
    static String botOwner = "ipavl"; 
    
    // Constructor
    public Bot() {
        this.setName("Ordobot");
    }

    public void onMessage(String channel, String sender, String login, String hostname, String message) {
        if (message.equalsIgnoreCase("!time")) {
            String time = new java.util.Date().toString();
            sendMessage(channel, sender + ": The time is currently " + time);
        }
        else if (message.equalsIgnoreCase("!ops") && channel.equalsIgnoreCase(OpAlert.watchChannel)) {
            sendMessage(OpAlert.staffChannel, sender + " (" + login + "@" + hostname + ") wants operator attention in " + 
                    OpAlert.watchChannel + ": " /* TODO: Read list from file */);
        }
        else if (message.equalsIgnoreCase("!pingme on") && channel.equalsIgnoreCase(OpAlert.staffChannel)) {
            OpAlert.addName(sender);
            sendMessage(OpAlert.staffChannel, sender + " has been added to the ping list.");
        }
        else if (message.equalsIgnoreCase("!pingme off") && channel.equalsIgnoreCase(OpAlert.staffChannel)) {
            OpAlert.delName(sender);
            sendMessage(OpAlert.staffChannel, sender + " has been removed from the ping list.");
        }
        
        else if (message.startsWith("!debug") && sender.equalsIgnoreCase(botOwner))
        {
            String debugCommand = message.substring(7);
            if (debugCommand.startsWith("join"))            // ask the bot to join a channel
                joinChannel(debugCommand.substring(5));
            else if (debugCommand.equalsIgnoreCase("part")) // ask the bot to part the current channel
                partChannel(channel);
            else if (debugCommand.startsWith("part"))       // ask the bot to part a channel
                partChannel(debugCommand.substring(5));
        }
    }
}
