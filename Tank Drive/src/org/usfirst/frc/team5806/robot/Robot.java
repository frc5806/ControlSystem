package org.usfirst.frc.team5806.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Robot extends IterativeRobot {
	
	private class Button {
		public boolean alreadyRead = false;
		Joystick joystick;
		private int buttonIndex;
		
		public Button(Joystick joystick, int buttonIndex) {
			this.joystick = joystick;
			this.buttonIndex = buttonIndex;
		}
		
		public boolean readButton() {
			boolean buttonValue = joystick.getRawButton(buttonIndex);
			boolean returnValue = buttonValue == true && alreadyRead == false;
			
			// Set alreadyRead
			if(buttonValue == true) alreadyRead = true;
			else alreadyRead = false;
			
			return returnValue;
		}
	}
	
	RobotDrive robot;
	Joystick joystick;
	Encoder[] encoders;

	// HAS TO BE A NEGATIVE NUMBER SO IT GOES THE RIGHT WAY
	private static final double DAMPENING_COEFFICIENT = -0.75;
	// MINIMUM CHANGE IN JOYSTICK POSITION TO CAUSE CHANGE IN MOTORS
	private static double rampCoefficient = 0.05;
	
	Button addButton;
	Button subtractButton;

	
	private static double limitedJoyL, limitedJoyR;

	public void robotInit() {
		robot = new RobotDrive(1, 0);
		joystick = new Joystick(1);

		encoders = new Encoder[2];
		encoders[0] = new Encoder(0, 1);
		encoders[0].reset();
		encoders[1] = new Encoder(2, 3);
		encoders[1].reset();
		
		addButton = new Button(joystick, 3);
		subtractButton = new Button(joystick, 4);
	}

	public void testPeriodic() {
		LiveWindow.run();
		teleopPeriodic();
	}

	public void autonomousInit() {
	}

	public void autonomousPeriodic() {
	}

	public void teleopInit() {
		limitedJoyL = 0.1;
		limitedJoyR = 0.1;
	}
	
	public void teleopPeriodic() {
		if(addButton.readButton()) {
			rampCoefficient += 0.01;
			System.out.println("Button: " + rampCoefficient);
		}
		if(subtractButton.readButton()) {
			rampCoefficient -= 0.01;
			System.out.println("Button: " + rampCoefficient);
		}
		
		//using exponential moving averages for joystick limiting
		double desiredL = joystick.getRawAxis(1);
		double desiredR = joystick.getRawAxis(5);
		double errorL = desiredL - limitedJoyL;
		double errorR = desiredR - limitedJoyR;
		limitedJoyL += errorL * rampCoefficient;
		limitedJoyR += errorR * rampCoefficient;
		robot.tankDrive(DAMPENING_COEFFICIENT*limitedJoyL, DAMPENING_COEFFICIENT*limitedJoyR, true);
	}
}
