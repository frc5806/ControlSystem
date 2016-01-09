import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * A GUI menu for the user. Entry point into the program. Allows the user to
 * stop the program.
 */
public class Gooey extends JFrame {
	// Main panel.
	private JPanel panel;
	
	// Start and stop buttons.
	private JButton startButton, stopButton;

	/*
	 * GUI constructor.
	 *
	 * Makes and attaches methods to buttons, and starts a listener thread.
	 */
	public Gooey() {
		super("Driver Controller");
		setSize(300, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());

		startButton = new JButton("START");
		stopButton = new JButton("STOP");

		startButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("action");
					start();
				}
			});

		stopButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					stop();
				}
			});

		addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					stop();
				}
			});

		add(startButton);
		add(stopButton);
		setVisible( true);
	}

	// Start up the robot
	public void start() {
		
	}


	// Stop the robot
	public void stop() {
		if(listenerThread != null) {
			listenerThread.close();
			listenerThreadShell.interrupt();
		}
	}

	public static void main(String[] args) {
		System.out.println("\u000c");
		Gooey mainWindow = new Gooey();
	}
}