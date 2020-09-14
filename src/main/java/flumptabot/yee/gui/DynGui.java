package flumptabot.yee.gui;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;

public class DynGui {

	public final DynGuiListener listener;
	private Message message;
	private final String[] emotes;
	private long lastUsed;

	public DynGui(Message m, DynGuiListener listener, String[] emotes2) {
		this.listener = listener;
		this.message = m;
		this.emotes = emotes2;
		this.lastUsed = System.currentTimeMillis();
	}
	
	public Message getMessage(){
		return this.message;
	}
	
	public boolean onReact(MessageReactionAddEvent event){
		String emote = event.getReactionEmote().getName();
		this.lastUsed = System.currentTimeMillis();
		for(int i = 0; i < this.emotes.length; i++){
			if(this.emotes[i].equals(emote)){
				return this.listener.onClick(i, this.message, this);
			}
		}
		return false;
	}

	public void invalidate(){
		DynGuiManager.invalidate(this, false);
	}
	
	public void invalidateAndRemove(){
		DynGuiManager.invalidate(this, true);
	}
	
	public boolean isStillNeeded(){
		return System.currentTimeMillis() - this.lastUsed < (5000L * 60000L);
	}

	public void rebuild(){
		this.message.editMessage(this.listener.rebuild()).queue((m) -> {
			this.message = m;
		});
	}
	
	public void addEmotes(Message m){
		for(int i = 0; i < this.emotes.length; i++){
			m.addReaction(this.emotes[i]).queue();
		}
	}

}
