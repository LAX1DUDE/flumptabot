package flumptabot;

import java.io.IOException;

import flumptabot.yee.audio.GoogleSearch;
import flumptabot.yee.audio.MusicGC;
import flumptabot.yee.gui.GuiGC;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.hooks.EventListener;

public class FlumptaBotLogic implements ILogic {
	
	public static final String playing = "] 𝚑𝚎𝚕𝚙 𝗒𝖺 𝖿𝗅𝗎𝗆𝗉𝗍𝖺";

	@Override public int getNumShards(FlumptaBot fb) {
		return 1;
	}

	@Override public void init(FlumptaBot fb) {
		Thread t = new MusicGC();
		t.setDaemon(true);
		t.start();
		
		Thread t2 = new GuiGC();
		t2.setDaemon(true);
		t2.start();
		
		try {
			GoogleSearch.init();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override public EventListener getListener(FlumptaBot fb) {
		return new FlumptaBotEvent(this);
	}

	@Override public void setupJDA(JDABuilder b) {
		b.setGame(Game.playing(playing));
	}

}
