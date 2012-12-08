public class Main {
	public static void main(String[] args) {
		Field field = new Field(30);
		Neighbor test1 = new Neighbor(field,1);
		Neighbor test2 = new Neighbor(field,2);
		
		new Thread(test1,"Neighbor1").start();
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new Thread(test2,"Neighbor2").start();
		
		field.setVisible(true);
	}
}
