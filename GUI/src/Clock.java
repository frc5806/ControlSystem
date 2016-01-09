import java.awt.FlowLayout;
import javax.swing.*;
/*
 * Clock is a panel which displays the time passed, time remaining,
 * and current stage of the competition.
 */
public class Clock extends JPanel
		implements Runnable
{
	private int totalTime = 0;
	private JLabel time;
	
	public Clock() {
		setLayout( new FlowLayout() );
		
		new JLabel("Time");
		add(time);
		
		Thread t = new Thread( this );
		t.start();
	}
	
	private String getState() {
		if (totalTime <= 15) {
			return " Autonomous";
		}
		
		return " Tele Op";
	}
	
	public void run() {
		try {
			while (totalTime <= 150) {
				Thread.sleep(1000);
				
				totalTime++;
				time.setText(" Remaining: " +((150-totalTime)/60) +":" +((150-totalTime)%60) + " Passed: " +(totalTime/60) +":" +(totalTime%60) +getState() );
			}
			time.setText("FINISHED!!!");
		} catch (InterruptedException ie) {
			System.out.println("Interrupted Exection on Clock thread."); 
		} 
	}

}
