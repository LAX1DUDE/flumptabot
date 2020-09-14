package flumptabot.yee;

import java.awt.Color;
import java.util.List;

import flumptabot.AdminCommand;
import flumptabot.CommandManager;
import flumptabot.JDAManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

public class broadcast extends AdminCommand {

	public broadcast() {
		super("broadcast");
	}

	@Override
	public String getHelp() {
		return "Display a message in every guild. Don't abuse";
	}

	@Override
	public boolean execute(String args, User u, MessageChannel c, CommandManager mgr) {
		for(JDA jda : JDAManager.jdaInstances){
			for(Guild g : jda.getGuilds()){
				MessageChannel deev = getAnnouncementChannel(g);
				if(deev != null) {
					deev.sendMessage(new EmbedBuilder()
						.setAuthor("Announcement", null, "https://cdn.discordapp.com/attachments/267096054137225228/305561584602841098/megaphone.png")
						.setColor(Color.ORANGE)
						.setDescription(args)
						.build()).queue();
				}
			}
		}
		return true;
	}
	
	public static TextChannel getAnnouncementChannel(Guild g){
		List<TextChannel> channels = g.getTextChannelsByName("announcements", true);
		if(channels.isEmpty()){
			channels = g.getTextChannelsByName("chat", true);
			if(channels.isEmpty()){
				channels = g.getTextChannelsByName("discussion", true);
				if(channels.isEmpty()){
					channels = g.getTextChannelsByName("lounge", true);
					if(channels.isEmpty()){
						return g.getDefaultChannel();
					}
				}
			}
		}
		return channels.get(0);
	}

}
