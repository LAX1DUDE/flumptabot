package flumptabot.yee;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

import flumptabot.AsyncOperation;
import flumptabot.Command;
import flumptabot.CommandManager;
import flumptabot.chatmovies;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class chatmovie extends Command {

	public chatmovie() {
		super("chatmovie");
	}

	@Override
	public String getHelp() {
		return "play ascii movies with darvy in them";
	}
	
	private static final int fr = 1000;
	
	@Override
	public boolean execute(String args, User u, MessageChannel c, CommandManager mgr) {
		if(args.length() >= 0) {
			if(args.equals("creech")) {
				c.sendMessage(chatmovies.CREECH_01).queue((m) -> {
					AsyncOperation.queue(() -> {
						try {
							Thread.sleep(fr);
							m.editMessage(chatmovies.CREECH_02).complete();
							Thread.sleep(fr);
							m.editMessage(chatmovies.CREECH_03).complete();
							Thread.sleep(fr);
							m.editMessage(chatmovies.CREECH_04).complete();
							Thread.sleep(fr);
							m.editMessage(chatmovies.CREECH_05).complete();
							Thread.sleep(fr);
							m.editMessage(chatmovies.CREECH_06).complete();
							Thread.sleep(fr);
							m.editMessage(chatmovies.CREECH_07).complete();
							Thread.sleep(fr);
							m.editMessage(chatmovies.CREECH_08).complete();
							Thread.sleep(fr);
							m.editMessage(chatmovies.CREECH_09).complete();
							Thread.sleep(fr);
							m.editMessage(chatmovies.CREECH_10).complete();
							Thread.sleep(fr);
							m.editMessage(chatmovies.CREECH_11).complete();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					});
				});
				return true;
			}else if(args.equals("darvig")) {
				c.sendMessage(chatmovies.DARVY_01).queue((m) -> {
					AsyncOperation.queue(() -> {
						try {
							Thread.sleep(fr);
							m.editMessage(chatmovies.DARVY_02).complete();
							Thread.sleep(fr);
							m.editMessage(chatmovies.DARVY_03).complete();
							Thread.sleep(fr);
							m.editMessage(chatmovies.DARVY_04).complete();
							Thread.sleep(fr);
							m.editMessage(chatmovies.DARVY_05).complete();
							Thread.sleep(fr);
							m.editMessage(chatmovies.DARVY_06).complete();
							Thread.sleep(fr);
							m.editMessage(chatmovies.DARVY_07).complete();
							Thread.sleep(fr);
							m.editMessage(chatmovies.DARVY_08).complete();
							Thread.sleep(fr);
							m.editMessage(chatmovies.DARVY_09).complete();
							Thread.sleep(fr);
							m.editMessage(chatmovies.DARVY_10).complete();
							Thread.sleep(fr);
							m.editMessage(chatmovies.DARVY_11).complete();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					});
				});
				return true;
			}else if(args.equals("eagler")) {
				c.sendMessage(chatmovies.EAGLER_01).queue((m) -> {
					AsyncOperation.queue(() -> {
						try {
							Thread.sleep(fr);
							m.editMessage(chatmovies.EAGLER_02).complete();
							Thread.sleep(fr);
							m.editMessage(chatmovies.EAGLER_03).complete();
							Thread.sleep(fr);
							m.editMessage(chatmovies.EAGLER_04).complete();
							Thread.sleep(fr);
							m.editMessage(chatmovies.EAGLER_05).complete();
							Thread.sleep(fr);
							m.editMessage(chatmovies.EAGLER_06).complete();
							Thread.sleep(fr);
							m.editMessage(chatmovies.EAGLER_07).complete();
							Thread.sleep(fr);
							m.editMessage(chatmovies.EAGLER_08).complete();
							Thread.sleep(fr);
							m.editMessage(chatmovies.EAGLER_09).complete();
							Thread.sleep(fr);
							m.editMessage(chatmovies.EAGLER_10).complete();
							Thread.sleep(fr);
							m.editMessage(chatmovies.EAGLER_11).complete();
							Thread.sleep(fr);
							m.editMessage(chatmovies.EAGLER_12).complete();
							Thread.sleep(fr);
							m.editMessage(chatmovies.EAGLER_13).complete();
							Thread.sleep(fr);
							m.editMessage(chatmovies.EAGLER_14).complete();
							Thread.sleep(fr);
							m.editMessage(chatmovies.EAGLER_15).complete();
							Thread.sleep(fr);
							m.editMessage(chatmovies.EAGLER_16).complete();
							Thread.sleep(fr);
							m.editMessage(chatmovies.EAGLER_17).complete();
							Thread.sleep(fr);
							m.editMessage(chatmovies.EAGLER_18).complete();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					});
				});
				return true;
			}
		}
		EmbedBuilder eb = new EmbedBuilder();
		eb.setAuthor("yeeing fudgli", null, help.HelpIcon);
		
		StringBuilder str = new StringBuilder();
		
		add(str, "creech", "watch yeerney throw a book at darvy");
		add(str, "darvig", "watch darver rape hayden");
		add(str, "eagler", "watch vigg eat an eagle egg");
		
		eb.addField("Available Chat Movies:", str.toString(), false);
		eb.setColor(new Color(23, 139, 216));
		c.sendMessage(eb.build()).queue();
		return true;
	}
	
	private static void add(StringBuilder str, String cmd, String desc){
		cmd = cmd.concat(StringUtils.repeat(" ", 15 - cmd.length())).concat(" ").concat(desc);
		cmd = cmd.concat(StringUtils.repeat(" ", 65 - cmd.length()) + "⎹");
		str.append("`"+cmd+"`\n");
	}

}
