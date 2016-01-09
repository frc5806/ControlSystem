//import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * A GUI menu for the user. Entry point into the program. Allows the user to
 * stop the program.
 */
public class Gooey extends JFrame {
    // Clock Panel
    private Clock clockPanel;

    // Start and stop buttons.
    private JButton startButton, stopButton;
    private static final String CAMERA_IMAGE_PATH = "pic1.jpeg";
    private JLabel cameraFeed;

    // Makes and attaches methods to buttons, and starts a listener thread.
    
    public Gooey() {
        super("Driver Controller");
        
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        startButton = new JButton("START");
        stopButton = new JButton("STOP");

        cameraFeed = new JLabel(new ImageIcon(CAMERA_IMAGE_PATH));

        startButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) { start(); }
        });

        stopButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) { stop(); }
        });

        addWindowListener(new WindowAdapter() {
	        public void windowClosing(WindowEvent e) { stop(); }
        });

        add(startButton);
        add(stopButton);
        add(cameraFeed);

        setVisible(true);
    }

    // Start up the robot
    public void start() {
        System.out.println("Start");
        if(clockPanel == null) {
            clockPanel = new Clock();
            add(clockPanel);
            System.out.println("New Clock");
        }
        repaint();
    }

    // Stop the robot
    public void stop() {
        if (clockPanel!=null) {
            remove(clockPanel);
        }
        clockPanel = null;
        System.out.println("Stop");
        repaint();
    }

	public static void main(String[] args) {
		System.out.println("\u000c");
		new Gooey();
	}
}