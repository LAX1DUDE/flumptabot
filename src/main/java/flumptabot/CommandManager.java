package flumptabot;

//https://gyazo.com/ccd785ca654c11662f44fa9d3a94f1bb

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;

import flumptabot.yee.help;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public abstract class CommandManager {
	private final HashMap<String, Command> commands;
	private static final ArrayList<String> admins = new ArrayList();
	
	static {
		admins.add("237080395747819520");
		admins.add("185203070928551936");
	}
	
	public CommandManager(){
		this.commands = new HashMap();
		this.add(new help());
		this.registerCommands();
	}
	
	protected void add(Command c){
		this.commands.put(c.name, c);
	}

	public static boolean isAdmin(String uid){ return admins.contains(uid); }
	public static boolean isAdmin(User u){ return admins.contains(u.getId()); }

	public abstract String getPrefix(MessageChannel c);
	protected abstract void registerCommands();
	
	public boolean process(Message m){
		String prefix = this.getPrefix(m.getTextChannel());
		if(m.getContentDisplay().startsWith(prefix)){
			for(Entry<String, Command> cmd : commands.entrySet()){
				if(m.getContentDisplay().startsWith(prefix + cmd.getKey())){
					try {
						if(cmd.getValue().canExecute(m.getAuthor(), m.getChannel(), this)){
							cmd.getValue().execute(m.getContentDisplay().substring((prefix + cmd.getKey()).length()).trim(), m.getAuthor(), m.getChannel(), this);
							if(m.getChannelType() == ChannelType.TEXT) { m.delete().queue(); }
						}else{
							
						}
					}catch(Throwable t) {
						t.printStackTrace();
						m.getChannel().sendMessage(Embedder.CommandError("there was an error completing your command plz tell lax")).queue();
					}
					return true;
				}
			}
			m.getChannel().sendTyping().queue();
			m.getChannel().sendMessage(Embedder.CommandError("that command doesn't exist you eagler")).queue();
			return true;
		}else{
			return false;
		}
	}

	public Collection<Command> getAllCommands() {
		return this.commands.values();
	}
}
