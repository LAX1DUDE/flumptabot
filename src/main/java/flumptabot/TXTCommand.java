package flumptabot;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class TXTCommand extends Command {

	private final String text;
	private final String help;

	protected TXTCommand(String name, String text, String help) {
		super(name);
		this.text = text;
		this.help = help;
	}

	@Override
	public boolean execute(String args, User u, MessageChannel c, CommandManager mgr) {
		c.sendTyping().queue();
		c.sendMessage(this.text).queue();
		return true;
	}

	@Override
	public String getHelp() {
		return this.help;
	}

}
