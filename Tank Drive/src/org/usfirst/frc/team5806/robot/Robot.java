package org.usfirst.frc.team5806.robot;


import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Encoder;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	private static double limitedJoyL, limitedJoyR;

	// Driving objects
	DriveTrain leftDrive, rightDrive;
	Joystick joystick;

	// Sensors
	IMU imu;
	Sonar[] sonars;

	Camera camera;
	CameraServer cameraServer;

	// HAS TO BE A NEGATIVE NUMBER SO IT GOES THE RIGHT WAY
	//unused: private static final double DAMPENING_COEFFICIENT = -0.9;
	// MINIMUM CHANGE IN JOYSTICK POSITION TO CAUSE CHANGE IN MOTORS
	private static double rampCoefficient = 0.07;
	private static final String CAMERA_NAME = "cam0";

	ButtonHandler buttonHandler;
	Roller roller;
	Arm arm;

	public void robotInit() {
		leftDrive = new DriveTrain(new Talon(1), new Encoder(0, 1), 0);
		rightDrive = new DriveTrain(new Talon(0), new Encoder(2, 3), 0);
		leftDrive.enable();
		rightDrive.enable();
				
		joystick = new Joystick(1);

		sonars = new Sonar[] { new Sonar(2), new Sonar(3) };
		
		cameraServer = CameraServer.getInstance();
		camera = new Camera(CAMERA_NAME);

		buttonHandler = new ButtonHandler(joystick);

		// compressor = new Compressor();
		// compressor.start();

		roller = new Roller(2, 4);
		arm = new Arm(1, 0);
	}

	public void testInit() {
		LiveWindow.run();
		teleopInit();
	}

	public void testPeriodic() {
		LiveWindow.run();
		teleopPeriodic();
	}

	public void teleopInit() {
		limitedJoyL = 0.1;
		limitedJoyR = 0.1;
		cameraServer.startAutomaticCapture(camera);
	}

	public void teleopPeriodic() {
		if (buttonHandler.isDown('A')) {
			roller.forward();
		} else if (buttonHandler.isDown('B')) {
			roller.reverse();
		} else {
			roller.stop();
		}

		if (buttonHandler.readButton('X')) {
			arm.toggle();
		}
		
		// using exponential moving averages for joystick limiting
		double desiredL = joystick.getRawAxis(1);
		double desiredR = -joystick.getRawAxis(5);
		double errorL = desiredL - limitedJoyL;
		double errorR = desiredR - limitedJoyR;
		limitedJoyL += errorL * rampCoefficient;
		limitedJoyR += errorR * rampCoefficient;
		//System.out.println("Speed: " + leftDrive.lastMotorSpeed);
		leftDrive.setSpeed(DriveTrain.MAXIMUM_ENCODERS_PER_SECOND * desiredL);
		rightDrive.setSpeed(DriveTrain.MAXIMUM_ENCODERS_PER_SECOND * desiredR);

		// Update dashboard
		for (int index = 0; index < 4; index++) {
			if (camera.contoursExist(index)) {
				SmartDashboard.putNumber("Contour " + index+1 +": X", camera.getContourX(index));
				SmartDashboard.putNumber("Contour " + index+1 +": Y", camera.getContourY(index));
				SmartDashboard.putNumber("Contour " + index+1 +": Height", camera.getContourHeight(index));
				SmartDashboard.putNumber("Contour " + index+1 +": Width", camera.getContourWidth(index));
			} else {
				break;
			}
		}
	}

	public void disableInit() {
		// compressor.stop();
	}
}
