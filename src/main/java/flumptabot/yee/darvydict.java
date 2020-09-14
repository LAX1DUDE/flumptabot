package flumptabot.yee;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import flumptabot.Command;
import flumptabot.CommandManager;
import flumptabot.Embedder;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class darvydict extends Command {
	
	private static final String apiEndpoint = "http://192.168.3.80:6996/api.v0.php";
	
	public darvydict() {
		super("darvydict");
	}

	@Override
	public String getHelp() {
		return "searches darvydict for words";
	}

	@Override
	public boolean execute(String args, User u, MessageChannel c, CommandManager mgr) {
		c.sendTyping().queue();
		HttpURLConnection deev = null;
		InputStream e = null;
		try {
			deev = (HttpURLConnection) (new URL(apiEndpoint+"?r=true&w="+URLEncoder.encode(args,"UTF-8")).openConnection());
			e = deev.getInputStream();
			JSONObject j = new JSONObject(IOUtils.toString(e,"UTF-8"));
			if(j.getString("result").equals("success")) {
				EmbedBuilder eb = new EmbedBuilder();
				eb.setColor(new Color(180, 150, 0));
				eb.setTitle("Definition of '"+j.getString("word")+"'");
				eb.setAuthor("ᵈᵃʳᵛʸᵈⁱᶜᵗ", "https://darvydict.deev.is/word.php?w="+URLEncoder.encode(j.getString("word"),"UTF-8"), "https://darvydict.deev.is/logo.png");
				eb.setDescription(j.getString("definition"));
				c.sendMessage(eb.build()).queue();
			}else {
				c.sendMessage(Embedder.CommandError("No word found")).queue();
			}
		} catch (Throwable yeee) {
			c.sendMessage(Embedder.CommandError("An error occured searching DarvyDict")).queue();
			yeee.printStackTrace();
		}
		if(e != null) {
			try {
				e.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if(deev != null) deev.disconnect();
		return true;
	}

}
