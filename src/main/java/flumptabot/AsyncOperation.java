package flumptabot;

public class AsyncOperation {
	
	public static void queue(Runnable r) {
		Thread t = new Thread(r, "FBAsyncOp");
		t.setDaemon(true);
		t.start();
	}
}
