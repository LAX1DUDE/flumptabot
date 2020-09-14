package flumptabot;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public abstract class Command {
	
	public final String name;
	
	protected Command(String name){
		this.name = name;
	}

	public final boolean canExecute(User u, MessageChannel c, CommandManager mgr){
		return this.canBeExecuted(u, c, mgr);
	}
	
	protected boolean canBeExecuted(User u, MessageChannel c, CommandManager mgr){
		return true;
	}
	
	public abstract String getHelp();

	public abstract boolean execute(String args, User u, MessageChannel c, CommandManager mgr);
	
}
