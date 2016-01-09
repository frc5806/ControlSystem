import java.awt.FlowLayout;
import javax.swing.*;
/*
 * Clock is a panel which displays the time passed, time remaining,
 * and current stage of the competition.
 */
public class Clock extends JPanel
		implements Runnable
{
	private final String remain = " Remaining: ";
	private final String passed = " Passed: ";
	private int totalTime = 0;
	private JLabel time = new JLabel("Time");
	
	public Clock() {
		// TODO Make better
		setLayout( new FlowLayout() );
		add(time);
		Thread t = new Thread( this );
        t.start();
	}
	
	private String getState() {
		if (totalTime<15) {
			return " Autonomous";
		}
		
		return " Tele Op";
	}
	
	public void run() {
		try {
			while (totalTime<=150) {
				Thread.sleep(1000);
				totalTime++;
				time.setText(remain +((150-totalTime)/60) +":" +((150-totalTime)%60) +passed +(totalTime/60) +":" +(totalTime%60) +getState() );
			}
			time.setText("FINISHED!!!");
		} catch (InterruptedException ie) {System.out.println("Interrupted Exection on Clock thread."); } 
	}

}
