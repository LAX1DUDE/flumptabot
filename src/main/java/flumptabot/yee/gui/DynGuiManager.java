package flumptabot.yee.gui;

import java.awt.Color;
import java.util.ArrayList;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;

public class DynGuiManager {
	
	private static final ArrayList<DynGui> GUIs = new ArrayList();

	public static void onReaction(MessageReactionAddEvent event) {
		if(!event.getUser().equals(event.getJDA().getSelfUser())){
			for(DynGui gui : GUIs){
				if(gui.getMessage().getIdLong() == event.getMessageIdLong()){
					event.getReaction().removeReaction(event.getUser()).complete();
					if(gui.onReact(event)){
						gui.rebuild();
					}
					return;
				}
			}
		}
	}
	
	public static void buildGUI(TextChannel ch, String[] emotes, DynGuiListener listener){
		ch.sendMessage(listener.rebuild()).queue((message) -> {
			DynGui gui = new DynGui(message, listener, emotes);
			gui.addEmotes(message);
			GUIs.add(gui);
		});
	}
	
	public static void runGC(){
		ArrayList<DynGui> deletable = new ArrayList();
		for(DynGui gui : GUIs){
			if(!gui.isStillNeeded()){
				deletable.add(gui);
			}
		}
		
		for(DynGui gui : deletable){
			gui.invalidate();
		}
	}

	public static void invalidate(DynGui g, boolean b) {
		Color color = g.getMessage().getEmbeds().get(0).getColor();
		if(!b){g.getMessage().editMessage(new EmbedBuilder().setColor(color).setTitle("❌ GUI Expired", null).build()).queue();}
		else{ g.getMessage().delete().queue(); }
		GUIs.remove(g);
	}
}
