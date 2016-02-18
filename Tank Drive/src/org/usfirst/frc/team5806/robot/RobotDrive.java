package org.usfirst.frc.team5806.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;

public class RobotDrive {
	public static final long ENCODER_TICKS_TO_ROBOT_ROTATION = 1000; 
	
	public DriveTrain leftDrive, rightDrive;
	
	public RobotDrive(DriveTrain leftDrive, DriveTrain rightDrive, Sonar leftSonar, Sonar rightSonar) {
		this.leftDrive = leftDrive;
		this.rightDrive = rightDrive;
		this.leftDrive.enable();
		this.rightDrive.enable();
	}
	
	public void setSpeed(double speed) { setSpeed(speed, speed); }
	public void setSpeed(double leftSpeed, double rightSpeed) {
		leftDrive.setTargetSpeed(leftSpeed);
		rightDrive.setTargetSpeed(rightSpeed);
	}
	
	public void move(int encoderTicks) { move(encoderTicks, encoderTicks); };
	public void move(int leftEncoderTicks, int rightEncoderTicks) {
		double leftOriginalSpeed = rightDrive.getTargetSpeed();
		double rightOriginalSpeed = rightDrive.getTargetSpeed();
		long leftStartingTicks = leftDrive.encoder.get();
		long rightStartingTicks = rightDrive.encoder.get();
		
		boolean leftDone, rightDone;
		do {
			leftDone = leftDrive.encoder.get() - leftStartingTicks < leftEncoderTicks;
			rightDone = rightDrive.encoder.get() - rightStartingTicks < rightEncoderTicks;
			
			// Turn off the motor if that side has moved its necessary encoder ticks
			// THIS IS A PROBLEM. Will not acctually stay at 0 encoder ticks.
			// Instead, will move in conjunction with the other side of the robot a little bit
			if(leftDone) leftDrive.setTargetSpeed(0.0f);
			if(rightDone) rightDrive.setTargetSpeed(0.0f);
		} while(!leftDone && !rightDone);
		
		setSpeed(leftOriginalSpeed, rightOriginalSpeed);
	}
	
	public void pointTurn(double degrees) {
		double factor = (degrees / 360.0);
		move((int)(factor*ENCODER_TICKS_TO_ROBOT_ROTATION), (int)(-factor*ENCODER_TICKS_TO_ROBOT_ROTATION));
	}

}
