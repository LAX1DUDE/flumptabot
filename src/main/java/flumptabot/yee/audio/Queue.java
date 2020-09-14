package flumptabot.yee.audio;

import java.util.concurrent.LinkedBlockingQueue;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import flumptabot.yee.gui.MusicEmbeds;
import net.dv8tion.jda.core.entities.TextChannel;

public class Queue extends AudioEventAdapter {
	
	private final AudioPlayer player;
	private final LinkedBlockingQueue<AudioTrack> queue;
	private final GuildMusicManager mgr;
	private TextChannel lastMsg;

	public Queue(GuildMusicManager mgr, AudioPlayer player) {
		this.player = player;
		this.queue = new LinkedBlockingQueue<>();
		this.mgr = mgr;
	}
	
	public void queue(AudioTrack track){
		if(!this.player.startTrack(track, true)){
			this.queue.offer(track);
		}
	}
	
	public LinkedBlockingQueue<AudioTrack> getQueued(){
		return this.queue;
	}
	
	public void skip(){
		this.player.startTrack(queue.poll(), false);
	}
	
	public void setLastChannel(TextChannel ch){
		this.lastMsg = ch;
	}
	
	@Override public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
		if(endReason.mayStartNext){
			AudioTrack started;
			if(this.mgr.isLoop()){
				started = track.makeClone();
				this.player.startTrack(started, false);
			}else{
				started = this.queue.peek();
				skip();
			}
			if(started != null && this.lastMsg != null){
				MusicEmbeds.showStatus(null, this.lastMsg);
			}
		}
	}
}
