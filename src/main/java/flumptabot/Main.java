package flumptabot;

public class Main {
	public static void main(String[] args){
		System.out.println("  _____ _                       _        ____        _   ");
		System.out.println(" |  ___| |_   _ _ __ ___  _ __ | |_ __ _| __ )  ___ | |_ ");
		System.out.println(" | |_  | | | | | '_ ` _ \\| '_ \\| __/ _` |  _ \\ / _ \\| __|");
		System.out.println(" |  _| | | |_| | | | | | | |_) | || (_| | |_) | (_) | |_ ");
		System.out.println(" |_|   |_|\\__,_|_| |_| |_| .__/ \\__\\__,_|____/ \\___/ \\__|");
		System.out.println("                         |_|                             ");
		System.out.println("==========================================================");
		System.out.println("           VERSION: 1.0      AUTHOR: LAX1DUDE            ");
		System.out.println("          JDA: 3.0.0_185    LAVAPLAYER: 1.2.36           ");
		System.out.println("==========================================================\n");
		Log.info("Starting...");
		new FlumptaBot("Insert API key here", new FlumptaBotLogic()).start();
	}
}
