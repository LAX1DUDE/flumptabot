package flumptabot.yee;

import java.awt.Color;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.api.services.youtube.model.SearchResult;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import flumptabot.AsyncOperation;
import flumptabot.Command;
import flumptabot.CommandManager;
import flumptabot.Embedder;
import flumptabot.yee.audio.AudioController;
import flumptabot.yee.audio.GoogleSearch;
import flumptabot.yee.audio.GuildMusicManager;
import flumptabot.yee.audio.MusicGui;
import flumptabot.yee.audio.SearchGui;
import flumptabot.yee.audio.VolumeGui;
import flumptabot.yee.gui.MusicEmbeds;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

public class music extends Command {

	public music() {
		super("music");
	}

	@Override public String getHelp() { return "Stream Music in Your Channels :>"; }

	@Override public boolean execute(String args, User u, MessageChannel c, CommandManager mgr) {
		c.sendTyping().queue();
		if(args.isEmpty()){
			EmbedBuilder eb = new EmbedBuilder();
			eb.setAuthor("Music Help", null, help.HelpIcon);
			eb.setDescription("Only commands you have access to in this channel are shown");
			
			StringBuilder str = new StringBuilder();
			String prefix = mgr.getPrefix(c);
			if("`".equals(prefix)){
				prefix = "\\`";
			}

			add(str, prefix+"music play [url/search]", "Adds a song to the queue");
			add(str, prefix+"music search [search]", "Show YouTube results for a search query");
			add(str, prefix+"music gui", "Show a pause/play/skip/stop information GUI");
			add(str, prefix+"music pause", "Pauses the current song");
			add(str, prefix+"music stop", "Stops the player and clears the queue");
			add(str, prefix+"music skip", "Skip to the next song in queue");
			add(str, prefix+"music loop", "Toggle loop for current song");
			add(str, prefix+"music dj [role]", "Specify a DJ role. Leave blank to disable");
			add(str, prefix+"music volume", "Show the volume controller");
			add(str, prefix+"music distort", "Ever wondered what a dying darviglet sounds like?");
			add(str, prefix+"music unload", "Kicks FlumptaBot from your channel and unloads the audio system. This process is often automatic");
			
			eb.addField("My Commands:", str.toString(), false);
			eb.setFooter("Lavaplayer Version: 1.2.48", help.ArrIcon);
			eb.setColor(new Color(23, 139, 216));
			c.sendMessage(eb.build()).queue();
		}else{
			Guild g = ((TextChannel)c).getGuild();
			
			String[] txt = args.split(" ", 2);
			String arg = txt[0].trim();
			if(txt.length == 2){
				String str = txt[1].trim();
				if("play".equals(arg)){
					if(str.startsWith("https://") || str.startsWith("http://")){
						c.sendMessage(Embedder.CommandError("sorry no ddos for you")).queue();
					}else{
						AsyncOperation.queue(()->{
							String result;
							
							try {
								result = GoogleSearch.searchOne(str);
							} catch (Exception e) {
								c.sendMessage(Embedder.CommandError("An error occured searching YouTube")).queue();
								e.printStackTrace();
								return;
							}
							
							if(result == null){
								c.sendMessage(Embedder.CommandError("No Results")).queue();
								return;
							}
							
							AudioController.load(g, u, ((TextChannel)c), result);
						});
					}
					return true;
				}else if("search".equals(arg)){
					AsyncOperation.queue(()->{
						try {
							List<SearchResult> dickler = GoogleSearch.searchList(txt[1].trim());
							if(dickler.isEmpty()){
								c.sendMessage(Embedder.CommandError("No Results")).queue();
							}
							new SearchGui((TextChannel)c, dickler, txt[1].trim());
						} catch (Exception e) {
							c.sendMessage(Embedder.CommandError("An error occured searching YouTube")).queue();
							e.printStackTrace();
						}
					});
					return true;
				}
			}else{
				if("gui".equals(arg)){
					if(checkNotPlaying(((TextChannel)c))) return true;
					new MusicGui(AudioController.getGAP(g), (TextChannel)c);
					return true;
				}else if("pause".equals(arg)){
					if(checkNotPlaying(((TextChannel)c))) return true;
					AudioPlayer player = AudioController.getGAP(g).getPlayer();
					boolean paused;
					player.setPaused(paused = !player.isPaused());
					MusicEmbeds.onPausePlay(((TextChannel)c), paused);
					return true;
				}else if("stop".equals(arg)){
					if(checkNotPlaying(((TextChannel)c))) return true;
					GuildMusicManager gmm = AudioController.getGAP(g);
					gmm.getPlayer().stopTrack();
					gmm.getQueueMgr().getQueued().clear();
					c.sendMessage(new EmbedBuilder().setColor(new Color(196, 19, 19)).setTitle("🛑 Stopped the Player and Cleared the Queue", null).build()).queue();
					return true;
				}else if("skip".equals(arg)){
					if(checkNotPlaying(((TextChannel)c))) return true;
					GuildMusicManager gmm = AudioController.getGAP(g);
					AudioTrack track = gmm.getPlaying();
					gmm.getQueueMgr().skip();
					MusicEmbeds.showStatus("Skipped: `"+track.getInfo().title+"`", c);
					return true;
				}else if("loop".equals(arg)){
					c.sendMessage(new EmbedBuilder().setColor(new Color(0x3B88C3)).setTitle("🔁 Loop: "+(AudioController.getGAP(g).toggleLoop() ? "Enabled" : "Disabled"), null).build()).queue();
					return true;
				}else if("gui".equals(arg)){
					if(checkNotPlaying(((TextChannel)c))) return true;
					MusicEmbeds.showStatus(c);
					return true;
				}else if("dj".equals(arg)){
					
					return true;
				}else if("volume".equals(arg)){
					new VolumeGui((TextChannel)c);
					return true;
				}else if("play".equals(arg)){
					AudioPlayer player = AudioController.getGAP(g).getPlayer();
					if(!player.isPaused()){
						c.sendMessage(Embedder.CommandError("Not sure what you're trying to do here")).queue();
						return true;
					}
					player.setPaused(false);
					MusicEmbeds.onPausePlay(((TextChannel)c), false);
					return true;
				}else if("distort".equals(arg)){
					c.sendMessage(new EmbedBuilder().setColor(new Color(196, 19, 19)).setTitle("💉 Distort: "+(AudioController.getGAP(g).toggleDistort() ? "Enabled" : "Disabled"), null).build()).queue();
					return true;
				}else if("unload".equals(arg)){
					AudioController.gc(g);
					return true;
				}
			}
			
			c.sendMessage(Embedder.CommandError("That Command Doesn't Exist!")).queue();
		}
		return true;
	}
	
	private boolean checkNotPlaying(TextChannel textChannel) {
		if(AudioController.isPlaying(textChannel)){
			return false;
		}else{
			textChannel.sendMessage(Embedder.CommandError("Nothing is playing!")).queue();
			return true;
		}
	}

	private static void add(StringBuilder str, String cmd, String desc){
		cmd = cmd.concat(StringUtils.repeat(" ", 15 - cmd.length())).concat(" ").concat(desc);
		cmd = cmd.concat(StringUtils.repeat(" ", 65 - cmd.length()) + "⎹");
		str.append("`"+cmd+"`\n");
	}

}
