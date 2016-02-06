package org.usfirst.frc.team5806.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class Robot extends IterativeRobot {
	RobotDrive robot;
	Joystick joystick;
	Encoder[] encoders;

	// HAS TO BE A NEGATIVE NUMBER SO IT GOES THE RIGHT WAY
	private static final double DAMPENING_COEFFICIENT = -0.75;
	// MINIMUM CHANGE IN JOYSTICK POSITION TO CAUSE CHANGE IN MOTORS
	private static final double RAMP_COEFFICIENT = 0.1;
	
	private static double limitedJoyL, limitedJoyR;

	public void robotInit() {
		robot = new RobotDrive(1, 0);
		joystick = new Joystick(1);

		encoders = new Encoder[2];
		encoders[0] = new Encoder(0, 1);
		encoders[0].reset();
		encoders[1] = new Encoder(2, 3);
		encoders[1].reset();
	}

	public void testPeriodic() {
		LiveWindow.run();

		System.out.println("encoders: " + encoders[0].getDistance() + "\t" + encoders[1].getDistance());

		teleopPeriodic();
	}

	public void autonomousInit() {
	}

	public void autonomousPeriodic() {
	}

	public void teleopInit() {
		limitedJoyL = 0;
		limitedJoyR = 0;
	}
	
	public void teleopPeriodic() {
		//using exponential moving averages for joystick limiting
		double desiredL = joystick.getRawAxis(1);
		double desiredR = joystick.getRawAxis(5);
		double errorL = desiredL - limitedJoyL;
		double errorR = desiredR - limitedJoyR;
		limitedJoyL += errorL * RAMP_COEFFICIENT;
		limitedJoyR += errorR * RAMP_COEFFICIENT;
		robot.tankDrive(DAMPENING_COEFFICIENT*limitedJoyL, DAMPENING_COEFFICIENT*limitedJoyR, true);
	}
}
