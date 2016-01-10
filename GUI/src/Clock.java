import java.util.function.Function;

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
	
	// Update the timer label every second
	public void run() {
		long pastTime = 0;
		while (totalSeconds <= 150) {
			long current = System.currentTimeMillis();
			if ((current - pastTime) >= 1000) {
				pastTime = current;
				
				Function<Integer, String> formatTime = (Integer s) -> String.format("%d:%02d", s/60, s%60); 
				time.setText(
						" Remaining: " + formatTime.apply(150-totalSeconds) 
						+ " Passed: " + formatTime.apply(totalSeconds)
						+ (totalSeconds <= 15 ? " State: Autonomous" : " State: Tele Op"));
				
				totalSeconds++;
			}
		}
		time.setText("Game Over");
	}

}
