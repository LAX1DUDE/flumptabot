package flumptabot;

import java.awt.Color;

import flumptabot.yee.*;
import flumptabot.yee.gui.DynGuiManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.guild.member.GuildMemberNickChangeEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class FlumptaBotEvent extends ListenerAdapter {

	protected final CommandManager adminCommandManager;
	protected final CommandManager defaultCommandManager;
	
	public FlumptaBotEvent(FlumptaBotLogic flumptaBotLogic) {
		this.adminCommandManager = new CommandManager(){
			@Override public String getPrefix(MessageChannel c) {
				return "]";
			}

			@Override protected void registerCommands() {
				this.add(new shutdown());
				this.add(new play());
				this.add(new broadcast());
				this.add(new dm());
				this.add(new ping());
			}
		};
		
		this.defaultCommandManager = new CommandManager(){
			@Override public String getPrefix(MessageChannel c) {
				return "]";
			}

			@Override protected void registerCommands() {
				this.add(new chatmovie());
				this.add(new darviglet());
				this.add(new darvydict());
				this.add(new music());
				this.add(new ping());
				this.add(new TXTCommand("winston", "o\\   /o\n\\        /\n   \\   /", "prints a winson face"));
				this.add(new TXTCommand("deee", "░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░\n░░▄▄▄▄▄▄▄▄▄▄░░░▄▄▄▄▄▄▄▄▄▄▄░░▄▄▄▄▄▄▄▄▄▄▄░░▄▄▄▄▄▄▄▄▄▄▄░░▄░░\n░▐░░░░░░░░░░▌░▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░▌░\n░▐░█▀▀▀▀▀▀▀█░▌▐░█▀▀▀▀▀▀▀▀▀░▐░█▀▀▀▀▀▀▀▀▀░▐░█▀▀▀▀▀▀▀▀▀░▐░▌░\n░▐░▌░░░░░░░▐░▌▐░▌░░░░░░░░░░▐░▌░░░░░░░░░░▐░▌░░░░░░░░░░▐░▌░\n░▐░▌░░░░░░░▐░▌▐░█▄▄▄▄▄▄▄▄▄░▐░█▄▄▄▄▄▄▄▄▄░▐░█▄▄▄▄▄▄▄▄▄░▐░▌░\n░▐░▌░░░░░░░▐░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░▌░\n░▐░▌░░░░░░░▐░▌▐░█▀▀▀▀▀▀▀▀▀░▐░█▀▀▀▀▀▀▀▀▀░▐░█▀▀▀▀▀▀▀▀▀░▐░▌░\n░▐░▌░░░░░░░▐░▌▐░▌░░░░░░░░░░▐░▌░░░░░░░░░░▐░▌░░░░░░░░░░░▀░░\n░▐░█▄▄▄▄▄▄▄█░▌▐░█▄▄▄▄▄▄▄▄▄░▐░█▄▄▄▄▄▄▄▄▄░▐░█▄▄▄▄▄▄▄▄▄░░▄░░\n░▐░░░░░░░░░░▌░▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░░░░░░░░░░░▌▐░▌░\n░░▀▀▀▀▀▀▀▀▀▀░░░▀▀▀▀▀▀▀▀▀▀▀░░▀▀▀▀▀▀▀▀▀▀▀░░▀▀▀▀▀▀▀▀▀▀▀░░▀░░\n░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░", "prints a big deee"));
				this.add(new TXTCommand("yaflumpta", "▓         ▓          ▓            ▓▓▓     ▓             ▓      ▓               ▓          ▓              ▓▓▓     ▓▓▓▓▓         ▓\n.  ▓▓▓          ▓   ▓         ▓           ▓             ▓      ▓            ▓   ▓▓▓    ▓          ▓▓▓            ▓            ▓   ▓\n.     ▓           ▓▓▓▓        ▓▓        ▓             ▓      ▓        ▓                       ▓       ▓                   ▓         ▓▓▓▓\n.     ▓           ▓      ▓        ▓           ▓▓▓          ▓▓           ▓                        ▓      ▓                   ▓         ▓      ▓", "ya flumpta"));
				this.add(new TXTCommand("stenchile", "░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░\n░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░\n░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░\n░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░\n░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░\n░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░\n░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░\n░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░\n░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░\n░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░\n░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░", "censor stuff"));
				this.add(new TXTCommand("yee", "https://www.youtube.com/watch?v=q6EoRBvdVPQ", "darvy's favorate command"));
			}
		};
	}

	@Override public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
		if(!event.getAuthor().equals(event.getJDA().getSelfUser())){
			if(CommandManager.isAdmin(event.getAuthor())){
				if(this.adminCommandManager.process(event.getMessage())){
					Log.info("Recieved Administrator Command: "+event.getMessage().getContentDisplay());
				}
			}else{
				event.getChannel().sendMessage("ok").queue();
			}
		}
	}
	
	@Override public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		 if(!event.getAuthor().equals(event.getJDA().getSelfUser())){
			 this.defaultCommandManager.process(event.getMessage());
		 }
	 }
	
	@Override public void onMessageReceived(MessageReceivedEvent event) {
		if(event.getAuthor().equals(event.getJDA().getSelfUser())){
			 if(!event.getMessage().getEmbeds().isEmpty()){
				 MessageEmbed eb2 = event.getMessage().getEmbeds().get(0);
				 if("🏓 Pinging...".equals(eb2.getTitle()) && ping.pingsSent.containsKey(event.getChannel())){
					 long ps = ping.pingsSent.get(event.getChannel()).longValue();
					 ping.pingsSent.remove(event.getChannel());
					 event.getChannel().sendMessage(new EmbedBuilder().setTitle("🏓  Ping: `"+(System.currentTimeMillis() - ps)+"ms`", null).setColor(new Color(0xDD2E44)).build()).complete();
					 event.getMessage().delete().complete();
				 }
			 }
		 }
	}
	
	@Override public void onMessageReactionAdd(MessageReactionAddEvent event) {
		DynGuiManager.onReaction(event);
	}
	
	private static final String darvystare = "https://cdn.discordapp.com/attachments/220769496829394944/304462922145595402/darviglet.png";
	
    @Override public void onGuildMemberNickChange(GuildMemberNickChangeEvent event) {
    	if(event.getNewNick() != null) {
	    	TextChannel deev = broadcast.getAnnouncementChannel(event.getGuild());
	    	if(deev != null) {
	    		EmbedBuilder b = new EmbedBuilder();
	    		b.setTitle("you eaglers");
	    		b.setDescription(event.getUser().getName()+" just renamed himself to '"+event.getNewNick()+"' because he's an eagler");
	    		b.setThumbnail(event.getUser().getAvatarUrl());
	    		deev.sendMessage(b.build()).queue();
	    	}
    	}
    }
}
