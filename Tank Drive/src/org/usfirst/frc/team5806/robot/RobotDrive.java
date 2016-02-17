package org.usfirst.frc.team5806.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;

public class RobotDrive {
	DriveTrain leftDrive, rightDrive;
	
	public RobotDrive(DriveTrain leftDrive, DriveTrain rightDrive, Sonar leftSonar, Sonar rightSonar) {
		leftDrive = new DriveTrain(new Talon(1), new Encoder(0, 1), 0);
		rightDrive = new DriveTrain(new Talon(0), new Encoder(2, 3), 0);
		leftDrive.enable();
		rightDrive.enable();
	}
	
	public void setSpeed(double leftSpeed, double rightSpeed) {
		leftDrive.setSpeed(leftSpeed);
		rightDrive.setSpeed(rightSpeed);
	}
	
	public void turn(int degrees) {
		// FILL THIS IN
		// USE THE TWO DRIVE TRAINS
		// MEASURE THE ROBOT
		
	}
	
	public void correctTurnUsingSonars(Sonar leftSonar, Sonar rightSonar, int acceptableDistanceDifference) {
		float initialLeftSpeed = (float) leftDrive.targetEncoderSpeed;
		float initialRightSpeed = (float) leftDrive.targetEncoderSpeed;
		
		double sonarDistanceDiff = leftSonar.getMM() - rightSonar.getMM();
		if(Math.abs(sonarDistanceDiff) > acceptableDistanceDifference) {
			if(sonarDistanceDiff > 0) { // Left side is more forward than right
				// Turn a little to the left based on distance difference
				
				
			} else {
				// Turn a little to the right based on distance difference
				
				
			}
		}
		
		leftDrive.setSpeed(initialLeftSpeed);
		rightDrive.setSpeed(initialRightSpeed);
	}

}
