package org.usfirst.frc.team5806.robot;


import java.util.function.Consumer;
import java.util.function.Function;

import edu.wpi.first.wpilibj.CameraServer;

import edu.wpi.first.wpilibj.Encoder;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.USBCamera;

public class Robot extends IterativeRobot {
	// HAS TO BE A NEGATIVE NUMBER SO IT GOES THE RIGHT WAY
	//unused: private static final double DAMPENING_COEFFICIENT = -0.9;
	// MINIMUM CHANGE IN JOYSTICK POSITION TO CAUSE CHANGE IN MOTORS
	private static double rampCoefficient = 0.07;
	private static final String CAMERA_NAME = "cam0";
	private static final double[] SHOOTING_RANGE_FEET = {3.75, 4.25};
	private static final double[] GOAL_CENTERED_COORDS = {500, 500};
	private static final double GOAL_CENTERED_ERROR = 10;
		
	private static double limitedJoyL, limitedJoyR;

	IMU imu;
	Sonar[] sonars;
	
	Joystick joystick;
	RobotDrive drive;
	
	USBCamera camera;
	CameraServer cameraServer;
	GoalFinder finder;
	
	// Compressor compressor;
	Roller roller;
	Arm arm;
	
	ButtonHandler buttonHandler;

	public boolean inShootingRange() {
		//assuming the robot is facing 90 deg toward wall
		double feetFromWall = sonars[0].getFeet();
		double[][] centerGuesses = finder.getGoalCenters();
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
		sonars = new Sonar[] { new Sonar(2), new Sonar(3) };
		
		joystick = new Joystick(1);
		drive = new RobotDrive(
					new DriveTrain(new Talon(1), new Encoder(0, 1), 0), 
					new DriveTrain(new Talon(0), new Encoder(2, 3), 0), 
					sonars[0],
					sonars[1]);
		
		cameraServer = CameraServer.getInstance();
		camera = new USBCamera(CAMERA_NAME);
		finder = new GoalFinder(); 
		
		// compressor = new Compressor();
		// compressor.start();

		//roller = new Roller(2, 4);
		arm = new Arm(1, 0);

		buttonHandler = new ButtonHandler(joystick);
	}
	
	public void autonomousInit() {
		
	}
	
	public void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void autonomousPeriodic() {
		// Move to the low goal
		drive.setSpeed(0.5);
		
		// Deadreckon forward
		
		// Get into position using sonars
		
		// Dead reckon turn
		
		// Move up to castle. Stop at correct shooting distance
		
		// Position using vision processing
		double acceptableDistance = 5;
		double[] targetCenter = new double[]{500, 500};
		double[] goalCenter;
		do {
			double[][] centers = finder.getGoalCenters();
			
			// goalCenter is the left most center
			goalCenter = new double[]{Integer.MAX_VALUE, Integer.MAX_VALUE};
			for(int a = 0; a < centers.length; a++) if(centers[a][0] < goalCenter[0]) goalCenter = centers[a];
			
			// Turn based on x position difference
			
			// Move back or forward based on y position difference
			
		} while(Math.sqrt(Math.pow(goalCenter[0] - targetCenter[0], 2) + Math.pow(goalCenter[1] - targetCenter[1], 2)) > acceptableDistance);
		
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
		drive.setSpeed(DriveTrain.MAXIMUM_ENCODERS_PER_SECOND * desiredL, DriveTrain.MAXIMUM_ENCODERS_PER_SECOND * desiredR);
	}

	public void disableInit() {
		//compressor.stop();
	}
}
