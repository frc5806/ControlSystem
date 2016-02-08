package org.usfirst.frc.team5806.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Robot extends IterativeRobot {
	private static final double DAMPENING_COEFFICIENT = -0.75; // HAS TO BE A NEGATIVE NUMBER SO IT GOES THE RIGHT WAY
	private static double rampCoefficient = 0.05; // MINIMUM CHANGE IN JOYSTICK POSITION TO CAUSE CHANGE IN MOTORS
	private static double limitedJoyL, limitedJoyR;
	
	RobotDrive robot;
	Joystick joystick;
	Encoder[] encoders;
	DigitalInput magnetSwitch;
	boolean magnetSwitched;

	Button addButton;
	Button subtractButton;
	
	Compressor compressor;
	DoubleSolenoid solenoid;
	public void robotInit() {
		robot = new RobotDrive(1, 0);
		joystick = new Joystick(1);
		magnetSwitch = new DigitalInput(4);
		magnetSwitched = false;

		encoders = new Encoder[2];
		encoders[0] = new Encoder(0, 1);
		encoders[0].reset();
		encoders[1] = new Encoder(2, 3);
		encoders[1].reset();
		
		addButton = new Button(joystick, 3);
		subtractButton = new Button(joystick, 4);
		
		compressor = new Compressor(0);
		compressor.start();
	}
	
	public void testInit() {
	}
	
	public void testPeriodic() {
		LiveWindow.run();
		teleopPeriodic();
		if (magnetSwitch.get() != magnetSwitched) {
		System.out.println("Magnet sensor: " + !magnetSwitch.get()); // has to be !magnetSwitch because it returns false if there is a magnet
		magnetSwitched = magnetSwitch.get();
		}
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
		
		// Using exponential moving averages for joystick limiting
		double desiredL = joystick.getRawAxis(1);
		double desiredR = joystick.getRawAxis(5);
		double errorL = desiredL - limitedJoyL;
		double errorR = desiredR - limitedJoyR;
		limitedJoyL += errorL * rampCoefficient;
		limitedJoyR += errorR * rampCoefficient;
		robot.tankDrive(DAMPENING_COEFFICIENT*limitedJoyL, DAMPENING_COEFFICIENT*limitedJoyR, true);
	}
}
