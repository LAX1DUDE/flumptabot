package flumptabot.yee.gui;

import java.awt.Color;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import flumptabot.yee.audio.AudioController;
import flumptabot.yee.audio.GuildMusicManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.MessageEmbed.Footer;
import net.dv8tion.jda.core.entities.TextChannel;

public class MusicEmbeds {

	public static final String MUSIC_URL = "https://cdn.discordapp.com/attachments/237080999563886594/305847512873435149/music.png";
	public static final String THUMB_URL = "https://cdn.discordapp.com/attachments/237080999563886594/305490776236883969/deevile.png";
	
	public static MessageEmbed getStatus(String header, MessageChannel ch){
		if(!(ch instanceof TextChannel))
			throw new RuntimeException("WTF");
		TextChannel c = (TextChannel)ch;
		
		AudioTrack p = AudioController.getGAP(c.getGuild()).getPlaying();
		
		EmbedBuilder eb = new EmbedBuilder();
		eb.setAuthor("Music", null, THUMB_URL).setColor(new Color(0x6EEC5D)).setFooter("yee", null);
		eb.setThumbnail(MUSIC_URL);
		
		if(header != null) eb.setTitle(header, null);
		
		if(p != null){
			eb.addField("", "**- - - Now Playing - - -**", false);
			addTracksInfo(eb, p);
			addQueueToEmbed(eb, AudioController.getGAP(c.getGuild()).getQueueMgr().getQueued());
		}
		
		return eb.build();
	}
	
	public static MessageEmbed getStatus(MessageChannel ch){
		if(!(ch instanceof TextChannel))
			throw new RuntimeException("WTF");
		TextChannel c = (TextChannel)ch;
		EmbedBuilder eb = new EmbedBuilder();
		
		GuildMusicManager mgr = AudioController.getGAP(c.getGuild());
		LinkedBlockingQueue<AudioTrack> queue = mgr.getQueueMgr().getQueued();
		AudioTrack now = mgr.getPlaying();
		AudioTrack next = queue.peek();
		
		eb.setAuthor("Music", null, THUMB_URL).setColor(new Color(0x6EEC5D)).setFooter("yee", null);
		eb.setThumbnail(MUSIC_URL);
		
		eb.addField("", "**- - - Now Playing - - -**", false);
		addTracksInfo(eb, now);
		
		if(queue.size() > 0){
			
			eb.addField("", "**- - - Up Next - - -**", false);
			addTracksInfo(eb, next);

			addQueueToEmbed(eb, AudioController.getGAP(c.getGuild()).getQueueMgr().getQueued(), false);
		}
		
		return eb.build();
	}

	public static void showStatus(MessageChannel ch){
		MessageEmbed eb = getStatus(ch);
		Message m = shouldEditExisting((TextChannel)ch);
		if(m != null) {
			m.editMessage(eb).queue();
		}else {
			ch.sendMessage(eb).queue();
		}
	}
	
	public static void showStatus(String header, MessageChannel ch){
		MessageEmbed eb = getStatus(header, ch);
		Message m = shouldEditExisting((TextChannel)ch);
		if(m != null) {
			m.editMessage(eb).queue();
		}else {
			ch.sendMessage(eb).queue();
		}
	}

	private static final String[] yeeish = {"Maybe.", "Probably...", "It Depends", "**Definatly.**", "java.lang.NullPointerException", "Ask Darver"};
	
	private static void addTracksInfo(EmbedBuilder eb, AudioTrack track){
		AudioTrackInfo info = track.getInfo();
		eb.addField("Title:", info.title, true);
		if(info.author != null){ eb.addField("Author/Uploader:", info.author, true); } else { eb.addField("Author:", "?", true); }
		if(info.isStream){ eb.addField("Duration:", "(Until Stream Ends)", true); }else{
			eb.addField("Duration:", String.format("%02d:%02d:%02d", 
				TimeUnit.MILLISECONDS.toHours(info.length),
				TimeUnit.MILLISECONDS.toMinutes(info.length) -  
				TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(info.length)), // The change is in this line
				TimeUnit.MILLISECONDS.toSeconds(info.length) - 
				TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(info.length))), true);
		}
		eb.addField("Yeeish:", yeeish[(int) (System.nanoTime() % yeeish.length)] + "\n⎻⎻⎻⎻⎻⎻⎻⎻⎻⎻", true);
	}

	public static void onPlay(AudioTrack track, MessageChannel ch){
		if(!(ch instanceof TextChannel))
			throw new RuntimeException("WTF");
		TextChannel c = (TextChannel)ch;
		
		EmbedBuilder eb = new EmbedBuilder();
		eb.setAuthor("Music", null, THUMB_URL).setColor(new Color(0x6EEC5D)).setFooter("yee", null);
		eb.setThumbnail(MUSIC_URL);
		
		eb.addField("", "**- - - Added to Queue - - -**", false);
		
		addTracksInfo(eb, track);
		
		addQueueToEmbed(eb, AudioController.getGAP(c.getGuild()).getQueueMgr().getQueued());
		
		Message m = shouldEditExisting(c);
		if(m != null) {
			m.editMessage(eb.build()).queue();
		}else {
			c.sendMessage(eb.build()).queue();
		}
	}
	
	private static Message shouldEditExisting(TextChannel ch) {
		List<Message> lst = ch.getHistory().retrievePast(3).complete();
		for(Message m : lst){
			if(!m.getEmbeds().isEmpty()){
				Footer em = m.getEmbeds().get(0).getFooter();
				if(em != null){
					if(em.getText().contains("yee")){
						return m;
					}
				}
			}
		}
		return null;
	}

	public static void onPlaylist(AudioPlaylist pl, MessageChannel ch){
		if(!(ch instanceof TextChannel))
			throw new RuntimeException("WTF");
		TextChannel c = (TextChannel)ch;
		
		EmbedBuilder eb = new EmbedBuilder();
		eb.setAuthor("Music", null, THUMB_URL).setColor(new Color(0x6EEC5D)).setFooter("yee", null);
		
		eb.addField("", "**- - - Added to Queue:** `"+pl.getName()+"`** - - -**", false);
		
		addQueueToEmbed(eb, AudioController.getGAP(c.getGuild()).getQueueMgr().getQueued());
		
		Message m = shouldEditExisting(c);
		if(m != null) {
			m.editMessage(eb.build()).queue();
		}else {
			c.sendMessage(eb.build()).queue();
		}
	}
	
	private static void addQueueToEmbed(EmbedBuilder eb, LinkedBlockingQueue<AudioTrack> queue, boolean b) {
		Object[] tracks = queue.toArray();
		int start = (b ? 0 : 1);
		if(tracks.length > start){
			StringBuilder sb = new StringBuilder();
			if(!b){
				sb.append("**- - - After That - - -**\n\n");
			}
			for(int i = start; i < tracks.length; i++){
				AudioTrack at = (AudioTrack)tracks[i];
				String p1 = i < 9 ? " " : "";
				sb.append("`" + (i+1) + ". " + p1 + at.getInfo().title + StringUtils.repeat(' ', 59 - at.getInfo().title.length()) + "⎹`\n");
			}
			eb.addField(b ? "Next Up:" : "", sb.toString(), false);
		}
	}
	
	private static void addQueueToEmbed(EmbedBuilder eb, LinkedBlockingQueue<AudioTrack> queue){
		addQueueToEmbed(eb, queue, true);
	}

	public static MessageEmbed getPausePlay(TextChannel textChannel, boolean paused) {
		EmbedBuilder eb = new EmbedBuilder();
		
		eb.setAuthor("Music", null, THUMB_URL).setColor(new Color(0x6EEC5D)).setFooter("yee", null);
		eb.setThumbnail(MUSIC_URL);
		
		AudioTrack track = AudioController.getGAP(textChannel.getGuild()).getPlayer().getPlayingTrack();
		
		eb.setTitle((paused ? "Paused" : "Resumed") + ": `"+track.getInfo().title+"`", null);
		
		if(!paused){
			eb.addBlankField(false);
			addTracksInfo(eb, track);
		}
		
		return eb.build();
	}
	
	public static void onPausePlay(TextChannel textChannel, boolean paused) {
		MessageEmbed eb = getPausePlay(textChannel, paused);
		Message m = shouldEditExisting(textChannel);
		if(m != null) {
			m.editMessage(eb).queue();
		}else {
			textChannel.sendMessage(eb).queue();
		}
	}
}
