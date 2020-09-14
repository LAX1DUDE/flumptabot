package flumptabot.yee.audio;

import java.awt.Color;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import flumptabot.yee.gui.DynGui;
import flumptabot.yee.gui.DynGuiListener;
import flumptabot.yee.gui.DynGuiManager;
import flumptabot.yee.gui.MusicEmbeds;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;

public class MusicGui implements DynGuiListener {
	
	private final String[] emotes = new String[]{"🔇", "🔊", "💉", "⏹", "⏸", "▶", "⏭", "🔁"};
	private final TextChannel deev;
	
	private int lastAction = 0;
	
	private boolean madeVolumeGui = false;
	
	public MusicGui(GuildMusicManager gap, TextChannel c) {
		this.deev = c;
		DynGuiManager.buildGUI(c, emotes, this);
	}

	@Override
	public MessageEmbed rebuild() {
		if(this.lastAction == 4 || this.lastAction == 5) {
			return MusicEmbeds.getPausePlay(deev, AudioController.getGAP(deev.getGuild()).getPlayer().isPaused());
		}
		return MusicEmbeds.getStatus(this.deev);
	}

	@Override
	public boolean onClick(int index, Message message, DynGui guiObject) {
		this.lastAction = index;
		GuildMusicManager gmm = AudioController.getGAP(deev.getGuild());
		if(index == 0) {
			gmm.getPlayer().setVolume(0);
			if(!madeVolumeGui) {
				new VolumeGui((TextChannel)deev);
			}
		}else if(index == 1) {
			gmm.getPlayer().setVolume(100);
			if(!madeVolumeGui) {
				new VolumeGui((TextChannel)deev);
			}
		}else if(index == 2) {
			deev.sendMessage(new EmbedBuilder().setColor(new Color(196, 19, 19)).setTitle("💉 Distort: "+(gmm.toggleDistort() ? "Enabled" : "Disabled"), null).build()).queue();
		}else if(index == 3) {
			guiObject.invalidateAndRemove();
			AudioController.gc(gmm.guild);
		}else if(index == 4) {
			AudioPlayer player = gmm.getPlayer();
			if(player.getPlayingTrack() != null) {
				player.setPaused(!player.isPaused());
				return true;
			}
		}else if(index == 5) {
			AudioPlayer player = gmm.getPlayer();
			if(player.getPlayingTrack() != null && player.isPaused()) {
				player.setPaused(false);
				return true;
			}
		}else if(index == 6) {
			gmm.getQueueMgr().skip();
			return true;
		}else if(index == 7) {
			deev.sendMessage(new EmbedBuilder().setColor(new Color(0x3B88C3)).setTitle("🔁 Loop: "+(gmm.toggleLoop() ? "Enabled" : "Disabled"), null).build()).queue();
		}
		return false;
	}

}
