package org.usfirst.frc.team5806.robot;


import edu.wpi.first.wpilibj.CameraServer;

import edu.wpi.first.wpilibj.Encoder;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.USBCamera;

public class Robot extends IterativeRobot {

	private static double limitedJoyL, limitedJoyR;

	// Driving objects
	DriveTrain leftDrive, rightDrive;
	Joystick joystick;

	// Sensors
	IMU imu;
	Sonar[] sonars;

	USBCamera camera;
	CameraServer cameraServer;
	GoalFinder goalFinder;

	// HAS TO BE A NEGATIVE NUMBER SO IT GOES THE RIGHT WAY
	//unused: private static final double DAMPENING_COEFFICIENT = -0.9;
	// MINIMUM CHANGE IN JOYSTICK POSITION TO CAUSE CHANGE IN MOTORS
	private static double rampCoefficient = 0.07;
	private static final String CAMERA_NAME = "cam0";
	private static final double[] SHOOTING_RANGE_FEET = {3.75, 4.25};
	private static final double[] GOAL_CENTERED_COORDS = {500, 500};
	private static final double GOAL_CENTERED_ERROR = 10;

	ButtonHandler buttonHandler;
	Roller roller;
	Arm arm;
	
	public boolean inShootingRange() {
		//assuming the robot is facing 90 deg toward wall
		double feetFromWall = sonars[0].getFeet();
		double[][] centerGuesses = goalFinder.getGoalCenters();
		double minDist = Double.MAX_VALUE;
		for (int i = 0; i < centerGuesses.length; i++) {
			double[] coords = centerGuesses[i];
			double distSq = Math.pow(GOAL_CENTERED_COORDS[0] - coords[0], 2) + 
							Math.pow(GOAL_CENTERED_COORDS[1] - coords[1], 2);
			double dist = Math.sqrt(distSq);
			if (dist < minDist) {
				minDist = dist;
			}
		}
		return feetFromWall >= SHOOTING_RANGE_FEET[0]
			&& feetFromWall <= SHOOTING_RANGE_FEET[1]
			&& minDist <= GOAL_CENTERED_ERROR;
	}
	
	public void robotInit() {
		leftDrive = new DriveTrain(new Talon(1), new Encoder(0, 1), 0);
		rightDrive = new DriveTrain(new Talon(0), new Encoder(2, 3), 0);
		leftDrive.enable();
		rightDrive.enable();
				
		joystick = new Joystick(1);

		sonars = new Sonar[] { new Sonar(2), new Sonar(3) };
		
		cameraServer = CameraServer.getInstance();
		camera = new USBCamera(CAMERA_NAME);

		buttonHandler = new ButtonHandler(joystick);
		
		goalFinder = new GoalFinder();

		// compressor = new Compressor();
		// compressor.start();

		//roller = new Roller(2, 4);
		arm = new Arm(1, 0);
	}
	
	public void autonomousInit() {
		
	}
	
	public void autonomousPeriodic() {
		
	}

	public void teleopInit() {
		limitedJoyL = 0.1;
		limitedJoyR = 0.1;
		cameraServer.startAutomaticCapture(camera);
	}

	public void teleopPeriodic() {
		if (buttonHandler.isDown('A')) {
			//roller.forward();
		} else if (buttonHandler.isDown('B')) {
			//roller.reverse();
		} else {
			//roller.stop();
		}

		if (buttonHandler.readButton('X')) {
			//arm.toggle();
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
		SmartDashboard.putBoolean("In Shooting Range: ", inShootingRange());
		
	}

	public void disableInit() {
		//compressor.stop();
	}
}
