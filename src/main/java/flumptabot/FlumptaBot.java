package flumptabot;

import javax.security.auth.login.LoginException;

import com.sedmelluq.discord.lavaplayer.jdaudp.NativeAudioSendFactory;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

public class FlumptaBot {

	protected final ILogic logic;
	protected final String token;
	protected JDA jda;

	public FlumptaBot(String string, ILogic flumptaBotLogic) {
		this.logic = flumptaBotLogic;
		this.token = string;
		Log.debug("Token is:     "+string);
		Log.debug("Program Set:  "+flumptaBotLogic.toString());
	}

	public void start() {
		Log.info("Connecting to Discord...");
		int shards = this.logic.getNumShards(this);
		if(shards == 1){
			Log.debug("Sharding Disabled. Connecting Normally...");
			Thread thread = new Thread(null, new Runnable() {
				@Override public void run(){
					try{
						JDABuilder builder = (new JDABuilder(AccountType.BOT));
						FlumptaBot.this.logic.setupJDA(builder);
					 builder.setAutoReconnect(true)
							.setAudioEnabled(true)
							.addEventListener(FlumptaBot.this.logic.getListener(FlumptaBot.this))
							.addEventListener(new JDAManager())
							.setAudioSendFactory(new NativeAudioSendFactory())
							.setToken(FlumptaBot.this.token)
							.buildBlocking();
					} catch (LoginException | IllegalArgumentException | InterruptedException e) {
						Log.severe("Could not connect!");
						e.printStackTrace();
						Log.warn("Retrying in 5...");
						try { Thread.sleep(5000L); }
						catch (InterruptedException e1) { e1.printStackTrace(); }
						this.run();
						return;
					}
				}
			}, "JDAThread");
			thread.setDaemon(true);
			thread.start();
		}else{
			Log.debug("Number of shards: "+shards);
			for(int i = 0; i < shards; i++){
				Log.debug("    Shard "+i+"...");
				final int shardid = i;
				Thread thread = new Thread(null, new Runnable() {
					@Override public void run(){
						try{
							JDABuilder builder = (new JDABuilder(AccountType.BOT));
							FlumptaBot.this.logic.setupJDA(builder);
						 builder.useSharding(shardid, shards)
								.setEnableShutdownHook(true)
								.setAutoReconnect(true)
								.setAudioEnabled(true)
								.addEventListener(FlumptaBot.this.logic.getListener(FlumptaBot.this))
								.addEventListener(new JDAManager())
								.setToken(FlumptaBot.this.token)
								.buildBlocking();
						} catch (LoginException | IllegalArgumentException | InterruptedException e) {
							Log.severe("Could not connect!");
							e.printStackTrace();
							Log.warn("Retrying in 5...");
							try { Thread.sleep(5000L); }
							catch (InterruptedException e1) { e1.printStackTrace(); }
							this.run();
							return;
						}
					}
				}, "JDAShard"+i);
				thread.setDaemon(true);
				thread.start();
			}
		}
		
		this.logic.init(this);
		
		while(true){try {Thread.sleep(Long.MAX_VALUE);} catch (InterruptedException e) {e.printStackTrace();}}
	}

}
