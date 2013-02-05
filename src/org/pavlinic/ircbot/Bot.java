package org.pavlinic.ircbot;

import org.jibble.pircbot.*;
import org.pavlinic.ircbot.features.*;

public class Bot extends PircBot {
    // Constructor
    public Bot() {
        this.setName(BotMain.botUsername);
        this.setLogin("ChanTools");
        this.setVersion("PircBot 1.5.0 with ChanTools 0.1 by ipavl");
    }

    public void onMessage(String channel, String sender, String login, String hostname, String message) {
        try {
            /*  !time
             * 
             *  Displays the current time.
             */
            if (message.equalsIgnoreCase("!time") && BotMain.allowTime) {
                String time = new java.util.Date().toString();
                sendMessage(channel, sender + ": The time is currently " + time);
            }
            
            /*  !ops [reason]
             *
             *  Used to ping Alert channel operators of problems.
             */
            else if (message.equalsIgnoreCase("!ops") && channel.equalsIgnoreCase(BotMain.idleChannel) && BotMain.allowOps) {
                // no reason specified
                sendMessage(BotMain.staffChannel, sender + " (" + login + "@" + hostname + ") wants operator attention in " + 
                        BotMain.idleChannel + ": " + OpAlert.readList());
            }
            else if (message.startsWith("!ops") && channel.equalsIgnoreCase(BotMain.idleChannel) && BotMain.allowOps) {
                // specify a reason
                sendMessage(BotMain.staffChannel, sender + " (" + login + "@" + hostname + ") wants operator attention in " + 
                        BotMain.idleChannel + " (for reason \"" + message.substring(5) + "\"): " + OpAlert.readList());
            }
            
            /*  !pingme <on/off>
             * 
             *  Used to add/remove yourself from the alert list for !ops.
             */
            else if (message.equalsIgnoreCase("!pingme on") && channel.equalsIgnoreCase(BotMain.staffChannel) && BotMain.allowOps) {
                OpAlert.addName(sender);
                sendMessage(BotMain.staffChannel, sender + " has been added to the ping list.");
            }
            else if (message.equalsIgnoreCase("!pingme off") && channel.equalsIgnoreCase(BotMain.staffChannel) && BotMain.allowOps) {
                OpAlert.delName(sender);
                sendMessage(BotMain.staffChannel, sender + " has been removed from the ping list.");
            }
            
            /*  !kick <user>
             * 
             *  Used to kick a user from the watch channel. Must be sent via the staff channel.
             */
            else if (message.startsWith("!kick") && channel.equalsIgnoreCase(BotMain.staffChannel) && BotMain.allowKick) {
                sendMessage("ChanServ", "OP " + BotMain.idleChannel);
                Thread.sleep(1000); // allow time to receive op status
                kick(BotMain.idleChannel, message.substring(6));
                sendMessage(BotMain.staffChannel, message.substring(6) + " has been kicked from " + 
                        BotMain.idleChannel + " by " + sender);
                sendMessage("ChanServ", "DEOP " + BotMain.idleChannel);
            }
            
            /*  !ban <user>
             * 
             *  Sets a nick!*@* ban on the specified user in the watc channel. Must be sent via the staff channel.
             */
            else if (message.startsWith("!ban") && channel.equalsIgnoreCase(BotMain.staffChannel) && BotMain.allowBan) {
                sendMessage("ChanServ", "OP " + BotMain.idleChannel);
                Thread.sleep(1000); // allow time to receive op status
                ban(BotMain.idleChannel, message.substring(5));
                sendMessage(BotMain.staffChannel, "Ban set on " + message.substring(5) + " for " + 
                        BotMain.idleChannel + " by " + sender);
                sendMessage("ChanServ", "DEOP " + BotMain.idleChannel);
            }
            
            /*  !debug <command> [parameters]
             * 
             *  Commands used for development/debugging to have more control over the bot.
             */
            else if (message.startsWith("!debug") && sender.equalsIgnoreCase(BotMain.botOwner) && BotMain.allowDebug)
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
                else if (debugCommand.startsWith("opme"))
                    op(channel, sender);
                else if (debugCommand.startsWith("say"))
                {
                    String[] split = debugCommand.substring(4).split("\\s+");
                    sendMessage(split[0], split[1]);
                }
                else if (debugCommand.equalsIgnoreCase("shutdown"))
                    System.exit(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
