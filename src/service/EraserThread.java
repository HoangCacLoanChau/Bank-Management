package service;

public class EraserThread implements Runnable {
	private boolean stop;
	private int n;

	public EraserThread(String prompt) {
		System.out.print(prompt);
	}

	public void run() {
		stop = true;
		while (stop) {
	         System.out.print("\010*");
		try {
			Thread.currentThread().sleep(1);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
		}
	}

	public void stopMasking() {
		n++;
		this.stop = false;
	}
}
