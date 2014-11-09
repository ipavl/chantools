IRCBot
======

An IRC bot wrapper for [PircBot](http://www.jibble.org/pircbot.php).

Usage
-----

 1. Make sure PircBot.jar is present in /lib OR in the IRCBot.jar file (in org.jibble.pircbot).
 	a) If not, download it from GitHub, or www.jibble.org/pircbot.php.
 2. Open a command prompt window and enter these commands (this can be automated with a batch file):
 	a) cd path/to/where/you/saved/the/bot
 	b) java -jar IRCBot.jar (where IRCBot.jar is the name of the bot file, NOT PircBot.jar)
 3. On first startup, you should receive a notice telling you to edit the /data/bot.properties file. Do so via a text editor.
 	a) change the 'owner' key to *YOUR* IRC username (if it's registered, otherwise it's recommended you set the allowDebug key to false)
 	b) change the 'username' key to the name you want your IRC bot to use
 	c) change the 'password' key to the password that your bot will identify to services (e.g. NickServ) with
 	d) change the 'server' key to the address of the IRC server you want to connect to (e.g. irc.freenode.net)
 	e) change the 'idleChannel' key to the channel you want to bot to sit in and watch over
 	f) change the 'staffChannel' key to a (secured) channel that your channel's operators are also in to be alerted via !ops (if enabled)
 		i) otherwise leave blank, or set to the same as the idle channel
	* NOTE: when using hashtags for idleChannel and staffChannel, it is important that you precede them with a backslash (i.e. \#\#channel)
	g) any "allow<X>" permissions are for commands. Set these as you desire. (See COMMANDS.txt for details.)
 4. After configuring the bot.properties file, save it, and re-do step 2b.
 5. Enjoy using your new bot.

Commands
--------

See `docs/commands.txt'.
