package flumptabot;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
	private static final SimpleDateFormat fmt = new SimpleDateFormat("[hh:mm:ss]");

	public static void log(Object o, Level lvl){
		@SuppressWarnings("resource")
		PrintStream stream = lvl.shouldLogAsError ? System.err : System.out;
		stream.println(fmt.format(new Date()) + " [" + Thread.currentThread().getName() + "/" + lvl.name + "] " + o.toString());
	}
	public static void info(Object o){
		log(o, Level.INFO);
	}
	public static void debug(Object o){
		log(o, Level.DEBUG);
	}
	public static void warn(Object o){
		log(o, Level.WARNING);
	}
	public static void error(Object o){
		log(o, Level.ERROR);
	}
	public static void severe(Object o){
		log(o, Level.SEVERE);
	}
	public static void fatal(Object o){
		log(o, Level.FATAL);
	}
	
	static enum Level{
		DEBUG("DEBUG", false),INFO("INFO", false),WARNING("WARNING", false),ERROR("ERROR", true),SEVERE("SEVERE", true),FATAL("FATAL", true);
		
		public final String name;
		public final boolean shouldLogAsError;
		
		private Level(String name, boolean shouldLogAsError){
			this.name = name;
			this.shouldLogAsError = shouldLogAsError;
		}
	}
}
