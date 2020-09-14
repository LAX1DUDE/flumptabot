package flumptabot.yee.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;

import net.dv8tion.jda.core.audio.AudioSendHandler;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;

public class GuildMusicManager {

	private class SendHandler implements AudioSendHandler {
		private AudioFrame lastFrame;
		private final GuildMusicManager mgr;
		
		protected SendHandler(GuildMusicManager guildMusicManager) {
			this.mgr = guildMusicManager;
		}
		
		@Override public boolean isOpus() {
			return true;
		}

		@Override public boolean canProvide() {
			if(this.lastFrame == null){
				this.lastFrame = this.mgr.player.provide();
			}
			return this.lastFrame != null;
		}

		@Override public byte[] provide20MsAudio() {
			if(this.lastFrame == null){
				this.lastFrame = this.mgr.player.provide();
			}
			
			byte[] data = this.lastFrame != null ? this.lastFrame.data : null;
			
			if(this.mgr.distort){
				for(int i = 0; i < data.length; i++){
					data[i] = clamp(data[i] * 500 / 496);
				}
			}
			
			this.lastFrame = null;
			
			return data;
		}
		
		private byte clamp(int b){
			if(b > Byte.MAX_VALUE){
				b = Byte.MAX_VALUE;
			}
			if(b < Byte.MIN_VALUE){
				b = Byte.MIN_VALUE;
			}
			return (byte) b;
		}
		
	}

	protected final AudioPlayer player;
	protected final Queue queue;

	protected boolean distort;
	protected boolean loop;
	
	public final Guild guild;

	public GuildMusicManager(AudioPlayerManager playermanager, Guild g) {
		this.player = playermanager.createPlayer();
		this.queue = new Queue(this, this.player);
		this.distort = false;
		this.player.addListener(this.queue);
		this.guild = g;
	}
	
	public AudioPlayer getPlayer(){
		return this.player;
	}
	
	public Queue getQueueMgr(){
		return this.queue;
	}
	
	public SendHandler makeSender() {
		return new SendHandler(this);
	}
	
	public AudioTrack getPlaying(){
		return this.player.getPlayingTrack();
	}

	public boolean toggleDistort(){
		return this.distort = !this.distort;
	}
	public boolean toggleLoop(){
		return this.loop = !this.loop;
	}
	public boolean isLoop(){
		return this.loop;
	}

	public void setLastChannel(TextChannel textChannel) {
		this.queue.setLastChannel(textChannel);
	}

	public void gc() {
		this.queue.getQueued().clear();
		this.player.destroy();
	}
	
}
