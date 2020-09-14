package flumptabot.yee;

import flumptabot.AdminCommand;
import flumptabot.CommandManager;
import flumptabot.FlumptaBotLogic;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.Game;

public class play extends AdminCommand {

	public play() {
		super("play");
	}

	@Override public String getHelp() { return "set the bot's 'playing' status, blank = reset"; }

	@Override public boolean execute(String args, User u, MessageChannel c, CommandManager mgr) {
		if(args.isEmpty()){
			u.getJDA().getPresence().setGame(Game.playing(FlumptaBotLogic.playing));
		}else{
			u.getJDA().getPresence().setGame(Game.playing(args));
		}
		return true;
	}

}
