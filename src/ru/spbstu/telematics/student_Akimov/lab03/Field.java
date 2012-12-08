import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Field extends JFrame{
	
	private int count;
	private int first = 0;
	private int second = 0;
	private int flag1 = 0;
	private int flag2 = 0;
	private String message;
	
	JLabel messageLabel;
	private JLabel countLabel;
	private JLabel firstLabel;
	private JLabel secondLabel;
	private JLabel flag1Label;
	private JLabel flag2Label;
	private JPanel jp;
	
	public Field(int count){
		super("Berry gatherers");
		this.count = count;
		Component b[] = new Component[6];
		setLayout(new GridLayout(3,2));
		countLabel = new JLabel("Count:" + count);
		firstLabel = new JLabel("First:" + first);
		secondLabel = new JLabel("Second:" + second);
		flag1Label = new JLabel("Flag 1:" + flag1);
		flag2Label = new JLabel("Flag 2:" + flag2);
		messageLabel = new JLabel(message);
		add(b[0]=countLabel);
		add(b[3]=flag1Label);
		add(b[1]=firstLabel);
		add(b[4]=flag2Label);
		add(b[2]=secondLabel);
		add(b[5]=messageLabel);
		setBounds(50, 50, 250, 100);
	}
	
	public void test(Neighbor neighbor){
		int localCount = 0;
		if(neighbor.id==1){
			if(count<=0){
				flag1 = 0;
				flag1Label.setText("Flag 1:" + flag1);
				System.out.println("Ягоды кончились )");
				messageLabel.setText("Ягоды кончились )");
				Thread.currentThread().stop();
			}
			flag1 = 1;
			flag1Label.setText("Flag 1:" + flag1);
			if(flag2 == 0){
				messageLabel.setText("Cобирает первый");
				System.out.println("Собирает ягоды первый, второй ждет");
				for(int i=0;i<new Random().nextInt(10);i++){
					localCount++;
					if(count<=0){
						flag1 = 0;
						flag1Label.setText("Flag 1:" + flag1);
						System.out.println("Ягоды кончились )");
						messageLabel.setText("Ягоды кончились )");
						Thread.currentThread().stop();
					}
					count--;
					neighbor.count++;
					countLabel.setText("Count:" + count);
					firstLabel.setText("First:" + neighbor.getCount());
					
					try {
						Thread.sleep(localCount*500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
				System.out.println("Первый собрал " + localCount + " ягод, осталось " + count);
				System.out.println("В итоге у первого: " + neighbor.getCount());
				
				
				flag1 = 0;
				flag1Label.setText("Flag 1:" + flag1);
			}
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			flag1 = 0;
			flag1Label.setText("Flag 1:" + flag1);
			System.out.println("FIRST :( !");
			
		}
		else if(neighbor.id==2){
			if(count<=0){
				flag2 = 0;
				flag2Label.setText("Flag 2:" + flag2);
				System.out.println("Ягоды кончились )");
				messageLabel.setText("Ягоды кончились )");
				Thread.currentThread().stop();
			}
			flag2 = 1;
			flag2Label.setText("Flag 2:" + flag2);
			if(flag1 == 0){
				messageLabel.setText("Cобирает второй");
				System.out.println("Собирает ягоды второй, первый ждет");
				for(int i=0;i<new Random().nextInt(10);i++){
					localCount++;
					if(count<=0){
						flag2 = 0;
						flag2Label.setText("Flag 2:" + flag2);
						System.out.println("Ягоды кончились )");
						messageLabel.setText("Ягоды кончились )");
						Thread.currentThread().stop();
					}
					count--;
					neighbor.count++;
					countLabel.setText("Count:" + count);
					secondLabel.setText("Second:" + neighbor.getCount());
					
					try {
						Thread.sleep(localCount*500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
				System.out.println("Второй собрал " + localCount + " ягод, осталось " + count);
				System.out.println("В итоге у второго: " + neighbor.getCount());
				
				
				flag2 = 0;
				flag2Label.setText("Flag 2:" + flag2);
			}
			 	try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				flag2 = 0;
				flag2Label.setText("Flag 2:" + flag2);
				System.out.println("SECOND :( !");
			
		}
				
	}
	
}
