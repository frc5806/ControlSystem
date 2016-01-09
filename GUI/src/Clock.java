import java.time.LocalTime;

import javax.swing.*;

/*
 * Clock is a panel which displays the time passed, time remaining,
 * and current stage of the competition.
 */
public class Clock extends JPanel implements Runnable
{
	private int totalSeconds = 0;
	private JLabel time;
	
	public Clock() {
		time = new JLabel("Time");
		add(time);
		
		Thread t = new Thread( this );
		t.start();
	}
	
	public void run() {
		long pastTime = System.currentTimeMillis();
		while (totalSeconds <= 150) {
			long current = System.currentTimeMillis();
			if ((current - pastTime) >= 1000) {
				pastTime = current;
				
				String remainingTime = LocalTime.ofSecondOfDay(150-totalSeconds).toString();
				String passedTime = LocalTime.ofSecondOfDay(totalSeconds).toString();
				String gameState = totalSeconds <= 15 ? " State: Autonomous" : " State: Tele Op";
				time.setText(" Remaining: " + remainingTime + " Passed: " + passedTime + gameState);
				
				totalSeconds++;
			}
		}
		time.setText("Game Over");
	}

}
