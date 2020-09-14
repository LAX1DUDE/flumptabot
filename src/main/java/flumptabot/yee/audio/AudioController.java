package flumptabot.yee.audio;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import flumptabot.Embedder;
import flumptabot.Log;
import flumptabot.yee.gui.MusicEmbeds;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.managers.AudioManager;

public class AudioController {
	
	private static final AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
	private static final Map<Long, GuildMusicManager> musicManagers = new HashMap();
	
	static{
		AudioSourceManagers.registerRemoteSources(playerManager);
		AudioSourceManagers.registerLocalSource(playerManager);
	}
	
	public static synchronized GuildMusicManager getGAP(Guild guild){
		long uid = Long.parseLong(guild.getId());
		GuildMusicManager musicManager = musicManagers.get(uid);
		if(musicManager == null){
			musicManager = new GuildMusicManager(playerManager, guild);
		    musicManagers.put(uid, musicManager);
		}
		
		guild.getAudioManager().setSendingHandler(musicManager.makeSender());
		
		return musicManager;
	}
	
	/**
	 * 
	 * Note: double purpose; also sets last channel sent for mid song messages
	 * 
	 */
	public static boolean isPlaying(TextChannel textChannel){
		GuildMusicManager gmm = getGAP(textChannel.getGuild());
		boolean is = gmm.player.getPlayingTrack() != null;
		if(is){
			gmm.setLastChannel(textChannel);
		}
		return is;
	}
	
	public static void load(Guild g, User u, TextChannel c, String trackUrl){
		GuildMusicManager gmm = getGAP(g);
		gmm.queue.setLastChannel(c);
		if(gmm.queue.getQueued().size() < 15){
			final User u2 = u;
			final TextChannel c2 = c;
			playerManager.loadItemOrdered(gmm, trackUrl, new AudioLoadResultHandler(){
				
				@Override public void trackLoaded(AudioTrack track) {
					connectIfNecessary(u2, c2.getGuild());
					getGAP(g).getQueueMgr().queue(track);
					MusicEmbeds.onPlay(track, c2);
				}
	
				@Override public void playlistLoaded(AudioPlaylist playlist) {
					connectIfNecessary(u2, c2.getGuild());
					AudioTrack firstTrack = playlist.getSelectedTrack();
					if(firstTrack == null){
						firstTrack = playlist.getTracks().get(0);
					}
					getGAP(g).getQueueMgr().queue(firstTrack);
					MusicEmbeds.onPlaylist(playlist, c2);
					MusicEmbeds.onPlay(firstTrack, c2);
				}
	
				@Override public void noMatches() {
					c2.sendMessage(Embedder.CommandError("No Songs Found!")).queue();
				}
	
				@Override public void loadFailed(FriendlyException exception) {
					c2.sendMessage(Embedder.CommandError("Error Loading Song: "+exception.toString())).queue();
				}
				
			});
		}else{
			c.sendMessage(Embedder.CommandError("Max queue size of 15 has been reached!")).queue();
		}
	}
	
	protected static void connectIfNecessary(User u2, Guild guild) {
		AudioManager audioManager = guild.getAudioManager();
		if (!audioManager.isConnected() && !audioManager.isAttemptingToConnect()) {
			audioManager.openAudioConnection(getChannelWith(u2, guild));
		}
	}

	private static VoiceChannel getChannelWith(User u, Guild g){
		Member m = g.getMember(u);
		for(VoiceChannel c : g.getVoiceChannels()){
			if(c.getMembers().contains(m)){
				return c;
			}
		}
		
		VoiceChannel most = null;
		
		for(VoiceChannel c : g.getVoiceChannels()){
			if(most == null || c.getMembers().size() > most.getMembers().size()){
				most = c;
			}
		}
		
		return most;
		
	}

	public static void gc() {
		Iterator<Entry<Long, GuildMusicManager>> i = musicManagers.entrySet().iterator();
		while (i.hasNext()){
			Entry<Long, GuildMusicManager> ent = i.next();
			GuildMusicManager m = ent.getValue();
			if(m.getPlaying() == null || m.getPlayer().isPaused()){
				m.player.stopTrack();
				m.gc();
				m.guild.getAudioManager().closeAudioConnection();
				i.remove();
			}
		}
	}

	public static void gc(Guild g) {
		GuildMusicManager m;
		if((m = musicManagers.get(g.getIdLong())) != null){
			Log.info("Running Music GC for Guild '"+g.getName()+"'...");
			m.player.stopTrack();
			m.gc();
			m.guild.getAudioManager().closeAudioConnection();
			System.gc();
			musicManagers.remove(g.getIdLong());
			Log.info("Complete");
		}
	}
	
}
