package flumptabot;

import java.util.ArrayList;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.ShutdownEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class JDAManager extends ListenerAdapter {
	
	public static final ArrayList<JDA> jdaInstances = new ArrayList();
	
	@Override
	public void onReady(ReadyEvent event) {
		jdaInstances.add(event.getJDA());
	}

    @Override
	public void onShutdown(ShutdownEvent event) {
    	jdaInstances.remove(event.getJDA());
    }
    
}
