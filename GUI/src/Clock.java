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
			return " State: Autonomous";
		}
		
		return " State: Tele Op";
	}
	
	private String addZero(int secs) {
		if (secs < 10) {
			return ("0" + Integer.toString(secs));
		}
		return Integer.toString(secs);
	}
	
	public void run() {
		long pastTime = System.currentTimeMillis();
		while (totalSeconds <= 150) {
			long current = System.currentTimeMillis();
			if ((current - pastTime) >= 1000) {
				pastTime = current;
				time.setText(" Remaining: " +((150-totalSeconds)/60) +":" +addZero((150-totalSeconds)%60) + " Passed: " +(totalSeconds/60) +":" +addZero(totalSeconds%60) +getState() );
				totalSeconds++;
				//repaint();
			}
		}
		time.setText("FINISHED!!!");
	}

}
