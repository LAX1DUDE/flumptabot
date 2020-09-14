package flumptabot.yee;

import java.awt.Color;
import java.util.Random;

import flumptabot.Command;
import flumptabot.CommandManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class darviglet extends Command {
	
	private static final Random r = new Random(420L);

	public darviglet() {
		super("darviglet");
	}

	@Override public String getHelp() { return "spice up the moment"; }
	@Override public boolean execute(String args, User u, MessageChannel c, CommandManager mgr) {
		c.sendTyping().queue();
		c.sendMessage(new EmbedBuilder()
				.setAuthor("𝗗𝗮𝗿𝘃𝘆 ( ͡° ͜ʖ ͡°)", "https://www.youtube.com/watch?v=cHBlv8uER0s", "https://cdn.discordapp.com/attachments/237080999563886594/305490776236883969/deevile.png")
				.setFooter("enjoy", null)
				.setImage(getImage())
				.setColor(new Color(105, 165, 53))
				.build()).queue();
		return true;
	}
	
	private static String getImage(){
		int id = r.nextInt(14);
		switch(id){
		case 0:
		case 6:
		case 7:
		case 12:
			return "https://cdn.discordapp.com/attachments/237080999563886594/305483734130491392/thumb3.jpg";
		case 1:
		case 8:
		case 11:
		case 13:
			return "https://cdn.discordapp.com/attachments/220769496829394944/304462922145595402/darviglet.png";
		case 2:
		case 9:
			return "https://cdn.discordapp.com/attachments/237080999563886594/305490776236883969/deevile.png";
		case 3:
			return "https://cdn.discordapp.com/attachments/237080999563886594/305490858495574016/deev.png";
		case 4:
			return "https://cdn.discordapp.com/attachments/237080999563886594/305491619245719563/you_eagler.png";
		case 5:
		default:
			return "https://cdn.discordapp.com/attachments/237080999563886594/305491935051776000/11352156_1440208519620755_198653212_n.png";
		}
	}

}
