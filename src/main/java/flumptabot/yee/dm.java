package flumptabot.yee;

import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.commons.lang3.ArrayUtils;

import flumptabot.AdminCommand;
import flumptabot.CommandManager;
import flumptabot.JDAManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;

public class dm extends AdminCommand {

	public dm() {
		super("dm");
	}

	@Override
	public String getHelp() {
		return "DM everyone. DO NOT ABUSE";
	}

	@Override
	public boolean execute(String args, User u, MessageChannel c, CommandManager mgr) {
		HashMap<User, String[]> users_to_dm = new HashMap();
		for(JDA jda : JDAManager.jdaInstances){
			for(Guild g : jda.getGuilds()){
				for(Member m : g.getMembers()){
					User u2 = m.getUser();
					if(!u2.isBot()){
						if(users_to_dm.containsKey(u2)){
							String[] og = users_to_dm.get(u2);
							og = ArrayUtils.add(og, g.getName());
							users_to_dm.put(u2, og);
							
						}else{
							users_to_dm.put(u2, new String[]{g.getName()});
						}
					}
				}
			}
		}
		
		for(Entry<User, String[]> u4 : users_to_dm.entrySet()){
			String footer = "";
			if(u4.getValue().length == 1){
				footer = u4.getValue()[0];
			}else if(u4.getValue().length == 2){
				footer = u4.getValue()[0] + " and " + u4.getValue()[1];
			}else{
				for(int i = 0; i < u4.getValue().length; i++){
					String val = u4.getValue()[i];
					if(i == 0){
						 val = "and ".concat(val);
					}
					footer = val.concat(", ").concat(footer);
				}
			}
			MessageEmbed embed = new EmbedBuilder().setTitle(args, null).setFooter("You are recieving this because you are on "+footer, null).build();
			u4.getKey().openPrivateChannel().queue((ch) -> {ch.sendMessage(embed).queue();});
		}
		
		return true;
	}

}
