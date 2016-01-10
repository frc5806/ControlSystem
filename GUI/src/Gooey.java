//import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * A GUI menu for the user. Entry point into the program. Allows the user to
 * stop the program.
 */
public class Gooey extends JFrame {
	
	/* 
	 * Debug variable for testing and writing purposes
	 * Please wrap all System.out.println();'s in
	 * an if (DEBUG) {}
	 */

	private static final boolean DEBUG = true;
	
	// two main panels for organization
	private JPanel header;
	private JPanel center;
	private JPanel clockBase;
	
	// Clock Panel
	private Clock clockPanel;

	// Start and stop buttons.
	private JButton startButton;
	private static final String CAMERA_IMAGE_PATH = "pic1.jpeg";
	private JLabel cameraFeed;
	private JLabel batteryAmount;
	private boolean timerRunning;
	// Makes and attaches methods to buttons, and starts a listener thread.


	
	public Gooey() {
		// Sets title bar
		super("Driver Controller");
		
		//Sets screen size
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout(5,5));

		//initialize the JPanels
		header = new JPanel();
		center = new JPanel();
		clockBase = new JPanel();
		
		//And the buttons
		startButton = new JButton("START");

		//And the labels
		cameraFeed = new JLabel(new ImageIcon(CAMERA_IMAGE_PATH));
		batteryAmount = new JLabel("Battery Remaining: Infinity %");

		// Add a listener to the start button to call start()/stop()
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { if (timerRunning) {stop();}
			else {start();}}
		});
		
		// Add a listener for when the window closes to call stop()
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) { stop(); }
		});
		
		// Set layouts of panels
		header.setLayout(new GridLayout( 0, 3));
		center.setLayout(new GridLayout( 0, 3, 10, 10));
		
		
		//Add sub-panels to main panels
		center.add(cameraFeed);
		header.add(startButton);
		header.add(clockBase);
		header.add(batteryAmount);
		
		// Add everything to the big panel
		add(header, BorderLayout.PAGE_START);
		add(center, BorderLayout.CENTER);

		setVisible(true);
	}

	// Start up the robot
	// Start clock
	private void start() {
		if (DEBUG) {System.out.println("Start");}
		if(clockPanel == null) {
			clockPanel = new Clock();
			clockBase.add(clockPanel);
			if (DEBUG) {System.out.println("New Clock");}
			startButton.setText("STOP");
			timerRunning = true;
		}
		repaint();
	}

	// Stop the robot
	// Stop clock
	private void stop() {
		if (clockPanel!=null) {
			clockBase.remove(clockPanel);
			startButton.setText("START");
			timerRunning = false;
		}
		clockPanel = null;
		if (DEBUG) {System.out.println("Stop"); }
		repaint();
	}

	public static void main(String[] args) {
		if (DEBUG) {System.out.println("\u000c"); }
		new Gooey();
	}
}