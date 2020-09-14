package flumptabot.yee;

import java.awt.Color;
import java.util.HashMap;

import flumptabot.Command;
import flumptabot.CommandManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class ping extends Command {
	
	public static final HashMap<MessageChannel, Long> pingsSent = new HashMap();

	public ping() {
		super("ping");
	}

	@Override
	public String getHelp() {
		return "test response times";
	}

	@Override
	public boolean execute(String args, User u, MessageChannel c, CommandManager mgr) {
		c.sendTyping().queue();
		pingsSent.put(c, System.currentTimeMillis());
		c.sendMessage(new EmbedBuilder().setTitle("🏓 Pinging...", null).setColor(new Color(0xDD2E44)).build()).queue();
		return true;
	}

}
