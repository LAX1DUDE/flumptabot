package flumptabot;

import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.User;

public abstract class AdminCommand extends Command {

	protected AdminCommand(String name) {
		super(name);
	}
	
	public boolean canBeExecuted(User u, Channel c){
		return CommandManager.isAdmin(u);
	}

}
