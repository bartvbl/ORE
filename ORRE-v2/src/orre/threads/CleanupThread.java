package orre.threads;

public class CleanupThread extends Thread {
	public void run() {
		while(true) {
			System.gc();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
	}
}
