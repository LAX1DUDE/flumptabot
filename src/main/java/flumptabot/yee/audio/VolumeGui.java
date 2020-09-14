package flumptabot.yee.audio;

import java.awt.Color;

import flumptabot.yee.gui.DynGui;
import flumptabot.yee.gui.DynGuiListener;
import flumptabot.yee.gui.DynGuiManager;
import flumptabot.yee.gui.MusicEmbeds;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;

public class VolumeGui implements DynGuiListener {
	
	private final Guild guild;
	
	private static final String[] emotes = {"🔇","⏮","◀","🆗","▶","⏭","🔊"};

	public VolumeGui(TextChannel c) {
		this.guild = c.getGuild();
		DynGuiManager.buildGUI(c, emotes, this);
	}

	@Override
	public MessageEmbed rebuild() {
		int volume = AudioController.getGAP(this.guild).getPlayer().getVolume();
		int vol2 = (int) Math.floor(volume / 10F);
		String speaker = "🔊❗️";
		if(vol2 == 0){
			speaker = "🔇";
		}else if(vol2 < 2){
			speaker = "🔈";
		}else if(vol2 < 5){
			speaker = "🔉";
		}else if(vol2 < 8){
			speaker = "🔊";
		}
		//char l = '─';
		//char b = '█';
		//char p = '➕';
		//char m = '➖';
		String indicator = (""+g(0,vol2)+g(1,vol2)+g(2,vol2)+g(3,vol2)+g(4,vol2)+g(5,vol2)+g(6,vol2)+g(7,vol2)+g(8,vol2)+g(9,vol2)+g(10,vol2));
		return new EmbedBuilder().setTitle("Volume: `"+volume+"%`", null).setColor(new Color(0x3B88C3)).addField(speaker+"  ͤ͡ ⌵ ͤ͡", indicator, false).setThumbnail(MusicEmbeds.MUSIC_URL).build();
	}
	
	private static char g(int i, int j){
		if(i == j){
			return '█';
		}else{
			return '─';
		}
	}

	@Override
	public boolean onClick(int index, Message message, DynGui guiObject) {
		GuildMusicManager gmm = AudioController.getGAP(this.guild);
		int oldvolume = gmm.getPlayer().getVolume();
		switch(index){
		case 0:
			oldvolume = 0;
			break;
		case 1:
			oldvolume -= 10;
			break;
		case 2:
			oldvolume -= 1;
			break;
		default:
		case 3:
			guiObject.invalidateAndRemove();
			return false;
		case 4:
			oldvolume += 1;
			break;
		case 5:
			oldvolume += 10;
			break;
		case 6:
			oldvolume = 100;
			break;
		}
		
		if(oldvolume < 0){
			oldvolume = 0;
		}else if(oldvolume > 100){
			oldvolume = 100;
		}
		
		gmm.getPlayer().setVolume(oldvolume);
		
		return true;
	}

}
