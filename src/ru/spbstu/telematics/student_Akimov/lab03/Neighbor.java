
import java.util.Random;

public class Neighbor implements Runnable {

	private Field field;
	int count = 0;
	int id;
	
	public int getCount(){
		return count;
	}

	public Neighbor(Field field, int id) {
		super();
		this.field = field;
		this.id = id;
	}

	@Override
	public void run() {
		while(!Thread.currentThread().isInterrupted()) {
			new Random().nextInt(1000);
			field.test(this);
			try {
				Thread.sleep(700);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		field.messageLabel.setText("Ягоды кончились )");
	}

}
