package flumptabot.yee.audio;

import flumptabot.Log;

public class MusicGC extends Thread {
	
	public MusicGC(){
		super(null, null, "musicmanager-gc");
	}
	
	@Override public void run(){
		while(true){
			try                            { Thread.sleep(60000L * 10L); }
			catch (InterruptedException e) { e.printStackTrace();        }
			Log.info("Running Music GC...");
			AudioController.gc();
			System.gc();
			Log.info("Complete");
		}
	}
	
}
