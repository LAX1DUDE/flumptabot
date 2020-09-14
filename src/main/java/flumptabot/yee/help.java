package flumptabot.yee;

import java.awt.Color;

import org.apache.commons.lang3.StringUtils;

import flumptabot.Command;
import flumptabot.CommandManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class help extends Command {
	
	public static final String HelpIcon = "https://cdn.discordapp.com/attachments/237080999563886594/305536562773491712/question.png";
	public static final String ArrIcon = "https://cdn.discordapp.com/attachments/237080999563886594/305534814017028116/arrow.png";

	public help() {
		super("help");
	}

	@Override public String getHelp() {
		return "shows this list";
	}

	@Override public boolean execute(String args, User u, MessageChannel c, CommandManager mgr) {
		c.sendTyping().queue();
		EmbedBuilder eb = new EmbedBuilder();
		eb.setAuthor("FlumptaBot Help", null, HelpIcon);
		eb.setDescription("Only commands you have access to in this channel are shown");
		
		StringBuilder str = new StringBuilder();
		String prefix = mgr.getPrefix(c);
		if("`".equals(prefix)){
			prefix = "\\`";
		}
		for(Command cmd : mgr.getAllCommands()){
			String tmp = prefix+cmd.name;
			tmp = tmp.concat(StringUtils.repeat(" ", 15 - tmp.length())).concat(" ").concat(cmd.getHelp());
			tmp = tmp.concat(StringUtils.repeat(" ", 65 - tmp.length()) + "⎹");
			str.append("`"+tmp+"`\n");
		}
		
		eb.addField("My Commands:", str.toString(), false);
		eb.setFooter("FlumptaBot Credits: LAX1DUDE", ArrIcon);
		eb.setColor(new Color(23, 139, 216));
		c.sendMessage(eb.build()).queue();
		return true;
	}

}
