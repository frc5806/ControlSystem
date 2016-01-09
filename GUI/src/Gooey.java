import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;


/**
 * A GUI menu for the user. Entry point into the program. Allows the user to
 * stop the program.
 */
public class Gooey extends JFrame {
	// Main panel. Currently not used.
	//private JPanel panel;
	
	// Clock Panel
	private Clock clockPlace;
	
	// Start and stop buttons.
	private JButton startButton, stopButton;
	//private final String cameraImagePath;
	private final String[] testPicNames = {
			"pic1.jpeg", "pic2.jpeg", "pic3.jpg", "pic4.jpg"
	};
	private JLabel cameraFeed;

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

		clockPlace = null;

		ImageIcon[] pics = new ImageIcon[testPicNames.length];
		for (int i = 0; i < pics.length; i++) {
			pics[i] = new ImageIcon(testPicNames[i]);
		}
		cameraFeed = new JLabel(pics[0]);

		startButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
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
		add(cameraFeed);
		
		setVisible( true);
	}

	// Start up the robot
	public void start() {
		System.out.println("Start");
		if(clockPlace == null) {
			clockPlace = new Clock();
			add(clockPlace);
			System.out.println("New Clock");
		}
		repaint();
	}


	// Stop the robot
	public void stop() {
		if (clockPlace!=null) {
			remove(clockPlace);
		}
		clockPlace = null;
		repaint();
	}

	public static void main(String[] args) {
		System.out.println("\u000c");
		Gooey mainWindow = new Gooey();
	}
}