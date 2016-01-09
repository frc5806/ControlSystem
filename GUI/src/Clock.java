import java.awt.FlowLayout;
import javax.swing.*;
/*
 * Clock is a panel which displays the time passed, time remaining,
 * and current stage of the competition.
 */
public class Clock extends JPanel
		implements Runnable
{
	private int totalSeconds = 0;
	private JLabel time;
	
	public Clock() {
		setLayout( new FlowLayout() );
		
		time = new JLabel("Time");
		add(time);
		
		Thread t = new Thread( this );
		t.start();
	}
	
	private String getState() {
		if (totalSeconds <= 15) {
			return " Autonomous";
		}
		
		return " Tele Op";
	}
	
	public void run() {
		try {
			while (totalSeconds <= 150) {
				Thread.sleep(1000);
				
				totalSeconds++;
				time.setText(" Remaining: " +((150-totalSeconds)/60) +":" +((150-totalSeconds)%60) + " Passed: " +(totalSeconds/60) +":" +(totalSeconds%60) +getState() );
			}
			time.setText("FINISHED!!!");
		} catch (InterruptedException ie) {
			System.out.println("Interrupted Exection on Clock thread."); 
		} 
	}

}
