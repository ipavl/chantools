package org.pavlinic.ircbot;

import java.io.IOException;

import org.jibble.pircbot.*;
import org.pavlinic.ircbot.features.*;

public class Bot extends PircBot {
    static String botOwner = BotMain.botOwner; 
    
    // Constructor
    public Bot() {
        this.setName(BotMain.botUsername);
    }

    public void onMessage(String channel, String sender, String login, String hostname, String message) {
        if (message.equalsIgnoreCase("!time") && BotMain.allowTime) {
            String time = new java.util.Date().toString();
            sendMessage(channel, sender + ": The time is currently " + time);
        }
        else if (message.equalsIgnoreCase("!ops") && channel.equalsIgnoreCase(BotMain.idleChannel) && BotMain.allowOps) {
            // no reason specified
            try {
                sendMessage(BotMain.staffChannel, sender + " (" + login + "@" + hostname + ") wants operator attention in " + 
                        BotMain.idleChannel + ": " + OpAlert.readList());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        else if (message.startsWith("!ops") && channel.equalsIgnoreCase(BotMain.idleChannel) && BotMain.allowOps) {
            // specify a reason
            try {
                sendMessage(BotMain.staffChannel, sender + " (" + login + "@" + hostname + ") wants operator attention in " + 
                        BotMain.idleChannel + " (for reason \"" + message.substring(5) + "\"): " + OpAlert.readList());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        else if (message.equalsIgnoreCase("!pingme on") && channel.equalsIgnoreCase(BotMain.staffChannel) && BotMain.allowOps) {
            OpAlert.addName(sender);
            sendMessage(BotMain.staffChannel, sender + " has been added to the ping list.");
        }
        else if (message.equalsIgnoreCase("!pingme off") && channel.equalsIgnoreCase(BotMain.staffChannel) && BotMain.allowOps) {
            OpAlert.delName(sender);
            sendMessage(BotMain.staffChannel, sender + " has been removed from the ping list.");
        }
        else if (message.startsWith("!kick") && channel.equalsIgnoreCase(BotMain.staffChannel) && BotMain.allowKick) {
            sendMessage("ChanServ", "OP " + BotMain.idleChannel);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            kick(BotMain.idleChannel, message.substring(6));
            sendMessage(BotMain.staffChannel, message.substring(6) + " has been kicked from " + 
                    BotMain.idleChannel + " by " + sender);
            sendMessage("ChanServ", "DEOP " + BotMain.idleChannel);
        }
        else if (message.startsWith("!ban") && channel.equalsIgnoreCase(BotMain.staffChannel) && BotMain.allowBan) {
            sendMessage("ChanServ", "OP " + BotMain.idleChannel);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            ban(BotMain.idleChannel, message.substring(5));
            sendMessage(BotMain.staffChannel, "Ban set on " + message.substring(5) + " for " + 
                    BotMain.idleChannel + " by " + sender);
            sendMessage("ChanServ", "DEOP " + BotMain.idleChannel);
        }
        else if (message.startsWith("!debug") && sender.equalsIgnoreCase(botOwner) && BotMain.allowDebug)
        {
            String debugCommand = message.substring(7);
            if (debugCommand.startsWith("join"))            // ask the bot to join a channel
                joinChannel(debugCommand.substring(5));
            else if (debugCommand.equalsIgnoreCase("part")) // ask the bot to part the current channel
                partChannel(channel);
            else if (debugCommand.startsWith("part"))       // ask the bot to part a channel
                partChannel(debugCommand.substring(5));
            else if (debugCommand.startsWith("nick"))
                changeNick(debugCommand.substring(5));
            else if (debugCommand.equalsIgnoreCase("shutdown"))
                System.exit(0);
        }
    }
}
