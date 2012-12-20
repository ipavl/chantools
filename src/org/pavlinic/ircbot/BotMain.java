package org.pavlinic.ircbot;

public class BotMain {
	public static void main(String[] args) throws Exception {
		// Start the bot
		Bot bot = new Bot();

		// Enable debugging output
		bot.setVerbose(true);

		// Connect to an IRC server
		bot.connect("irc.SERVER.net");

		// Auth to services (insecure if NickServ is not being used or is down and the name is not restricted)
		bot.sendMessage("NickServ", "IDENTIFY PASSWORD");
		
		// Join a channel
		bot.joinChannel("#CHANNEL");
	}
}