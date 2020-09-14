package flumptabot;

import java.awt.Color;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

public class Embedder {

	//private static final String URL = "http://discord.gg/";
	//private static final String ERR = "https://cdn.discordapp.com/attachments/237080999563886594/305498118525550592/no-entry-edit.png";
	
	public static MessageEmbed CommandError(String text){
		return new EmbedBuilder()
				.setTitle("⛔ "+text, null)
				.setColor(new Color(170, 0, 0))
				.build();
	}
	
}
