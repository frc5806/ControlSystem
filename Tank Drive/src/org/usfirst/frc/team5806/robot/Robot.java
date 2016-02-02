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
	private static final double MOVE_THRESHOLD = 0.05;
	// AMOUNT BY WHICH MOTORS ARE INCREMENTED WHEN ACCELERATING
	private static final double SPEED_RAMP_INCREMENT = 0.05;

	private static double leftStick = 0;
	private static double rightStick = 0;
	private static double lsRemaining = 0;
	private static double rsRemaining = 0;
	private static boolean rampingLeftSpeed = false;
	private static boolean rampingRightSpeed = false;

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
	}

	public void teleopPeriodic() {
		// For Xbox Controller

		// Calculate stick amounts.
		calculateStick(true);
		calculateStick(false);

		if (lsRemaining <= 0) {
			// have ramped all the way up to the last inputted joy value
			rampingLeftSpeed = false;
		}
		if (rsRemaining <= 0) {
			// have ramped all the way up to the last inputted joy value
			rampingRightSpeed = false;
		}

		robot.tankDrive(DAMPENING_COEFFICIENT * leftStick, DAMPENING_COEFFICIENT * rightStick, true);
	}

	// set side to true for right side, false for left side
	public void calculateStick(boolean side) {
		// Set variables
		// IF SOMEONE KNOWS HOW TO MAKE REFERENCES IN JAVA
		// THIS FUNCTION WOULD BE MUCH NICER
		boolean ramping = rampingLeftSpeed;
		int joystickNum = 1;
		double stick = leftStick;
		if (side) {
			ramping = rampingRightSpeed;
			joystickNum = 5;
			stick = rightStick;
		}

		if (ramping) {
			if (side) {
				rightStick += SPEED_RAMP_INCREMENT;
				rsRemaining -= SPEED_RAMP_INCREMENT;
			} else {
				leftStick += SPEED_RAMP_INCREMENT;
				lsRemaining -= SPEED_RAMP_INCREMENT;
			}
		} else {
			double incoming = joystick.getRawAxis(joystickNum);
			double desiredChange = incoming - stick;
			if (desiredChange > MOVE_THRESHOLD || desiredChange < -MOVE_THRESHOLD) {
				// joystick input is enough to warrant changing the motor
				if (side) {
					rsRemaining = desiredChange;
					rampingRightSpeed = true;
				} else {
					lsRemaining = desiredChange;
					rampingLeftSpeed = true;
				}
			}
		}

	}
}
