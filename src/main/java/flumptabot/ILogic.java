package flumptabot;

import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.hooks.EventListener;

public interface ILogic {
	public int getNumShards(FlumptaBot fb);
	public void init(FlumptaBot fb);
	public EventListener getListener(FlumptaBot fb);
	public void setupJDA(JDABuilder b);
}
