package flumptabot.yee;

import flumptabot.AdminCommand;
import flumptabot.CommandManager;
import flumptabot.Log;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class shutdown extends AdminCommand {

	public shutdown() {
		super("shutdown");
	}

	@Override public String getHelp() {
		return "System.exit(0); ya flumpta";
	}

	@Override public boolean execute(String args, User u, MessageChannel c, CommandManager mgr) {
		Log.info("Recieved Shutdown Command From "+u.getName()+"#"+u.getDiscriminator());
		System.exit(0);
		return true;
	}

}
