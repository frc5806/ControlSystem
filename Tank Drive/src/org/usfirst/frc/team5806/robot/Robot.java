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
	
	private static double limitedJoyL, limitedJoyR;
	
	// Driving objects
	RobotDrive robot;
	Joystick joystick;
	
	// Sensors
	Encoder[] encoders;
	IMU imu;
	Sonar[] sonars;

	// HAS TO BE A NEGATIVE NUMBER SO IT GOES THE RIGHT WAY
	private static final double DAMPENING_COEFFICIENT = -0.9;
	// MINIMUM CHANGE IN JOYSTICK POSITION TO CAUSE CHANGE IN MOTORS
	private static double rampCoefficient = 0.07;
	
	ButtonHandler buttonHandler;
	
	// Pneumatics
	//Compressor compressor;
	DoubleSolenoid solen;
	
	Roller roller;
	Arm arm;
	
	public void robotInit() {
		robot = new RobotDrive(1, 0);
		joystick = new Joystick(1);

		encoders = new Encoder[2];
		encoders[0] = new Encoder(0, 1);
		encoders[0].reset();
		encoders[1] = new Encoder(2, 3);
		encoders[1].reset();
		
		sonars = new Sonar[]{new Sonar(2), new Sonar(3)};
		
		buttonHandler = new ButtonHandler(joystick);
		
		//compressor = new Compressor();
		//compressor.start();
		
		roller = new Roller(2, 4);
		arm = new Arm(1, 0);
	}
	
	public void testInit() {
		System.out.println("Init test");
		teleopInit();
	}
	
	public void testPeriodic() {
		LiveWindow.run();
		teleopPeriodic();
	}

	public void teleopInit() {
		limitedJoyL = 0.1;
		limitedJoyR = 0.1;
	}
	
	public void teleopPeriodic() {
		if(buttonHandler.isDown('A')) {
			roller.forward();
		} else if(buttonHandler.isDown('B')) {
			roller.reverse();
		} else {
			roller.stop();
		}
		
		if(buttonHandler.readButton('Y')) {
			// Add the servo control for pushing ball in
		}
		
		if(buttonHandler.readButton('X')) {
			arm.toggle();
		}
		
		System.out.println("Sonars: " + sonars[0].getMM() + " " + sonars[1].getMM());
		
		//using exponential moving averages for joystick limiting
		double desiredL = joystick.getRawAxis(1);
		double desiredR = joystick.getRawAxis(5);
		double errorL = desiredL - limitedJoyL;
		double errorR = desiredR - limitedJoyR;
		limitedJoyL += errorL * rampCoefficient;
		limitedJoyR += errorR * rampCoefficient;
		robot.tankDrive(DAMPENING_COEFFICIENT*limitedJoyL, DAMPENING_COEFFICIENT*limitedJoyR, true);
		
		roller.update();
	}
	
	public void disableInit() {
		//compressor.stop();
	}
}
