package application;

import java.util.concurrent.TimeUnit;

public class UpdateField implements Runnable {

	private Main main;
	private Field field;

	public UpdateField(Main main, Field field) {
		this.main = main;
		this.field = field;
	}

	@Override
	public void run() {
		try {
			TimeUnit.MILLISECONDS.sleep(2000);
		} catch (InterruptedException e) {
		}
		boolean running = true;
		while(running){
			try {
				TimeUnit.MILLISECONDS.sleep(1000);
			} catch (InterruptedException e) {
			}
//			main.printArray(field.getTileArray());
		}
	}
}
