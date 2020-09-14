package flumptabot.yee.gui;

import flumptabot.Log;

public class GuiGC extends Thread {
	
	public GuiGC(){
		super(null, null, "dyngui-gc");
	}
	
	@Override public void run(){
		while(true){
			try                            { Thread.sleep(60000L * 5L); }
			catch (InterruptedException e) { e.printStackTrace();        }
			Log.info("Running Gui GC...");
			DynGuiManager.runGC();
			Log.info("Complete");
		}
	}
	
}
