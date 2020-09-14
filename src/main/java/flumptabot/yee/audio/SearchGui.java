package flumptabot.yee.audio;

import java.awt.Color;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.api.services.youtube.model.SearchResult;

import flumptabot.yee.gui.DynGui;
import flumptabot.yee.gui.DynGuiListener;
import flumptabot.yee.gui.DynGuiManager;
import flumptabot.yee.gui.MusicEmbeds;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;

public class SearchGui implements DynGuiListener {
	
	private final List<SearchResult> listler;
	private final String query;
	
	private static final String[] emotes = new String[]{"1⃣", "2⃣", "3⃣", "4⃣", "5⃣", "6⃣", "7⃣", "8⃣", "9⃣", "🔟"};

	public SearchGui(TextChannel c, List<SearchResult> searchList, String query) {
		this.listler = searchList;
		this.query = query;
		DynGuiManager.buildGUI(c, ArrayUtils.subarray(emotes, 0, searchList.size()), this);
	}

	@Override public MessageEmbed rebuild() {
		EmbedBuilder builder = new EmbedBuilder();
		builder.setColor(new Color(0X0aa308))
			.setAuthor("Music", null, MusicEmbeds.THUMB_URL)
			.setThumbnail(MusicEmbeds.MUSIC_URL)
			.setTitle("Results for '"+this.query+"'", null);
		
		StringBuilder results = new StringBuilder();

		for(int i = 0; i < this.listler.size(); i++){
			SearchResult result = this.listler.get(i);
			
			String str = result.getSnippet().getTitle();
			if(str.length() > 29){
				str = str.substring(0, 25) + "...";
			}
			str = str + StringUtils.repeat(' ', 30 - str.length());
			
			String str2 = result.getSnippet().getChannelTitle();
			if(str2.length() > 14){
				str2 = str2.substring(0, 16) + "...";
			}
			str2 = str2 + StringUtils.repeat(' ', 15 - str2.length());
			
			results.append(emotes[i]+"`"+str+" By: "+str2+" ⎹`\n");
		}
		
		builder.addField("", results.toString(), false);
		
		return builder.build();
	}

	@Override public boolean onClick(int index, Message message, DynGui guiObject) {
		AudioController.load(message.getGuild(), message.getAuthor(), message.getTextChannel(), "https://www.youtube.com/watch?v=" + this.listler.get(index).getId().getVideoId());
		return false;
	}

}
