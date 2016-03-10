package org.usfirst.frc.team5806.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotDrive {
	public static final double WHEEL_DIAMETER = 7.65;
	public static final double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI;
	public static final long TICKS_PER_MOTOR_REV = 64;
	public static final double MOTOR_REVS_PER_WHEEL_REV = 1 / 0.28;
	public static final long TICKS_PER_WHEEL_REV = (long)(TICKS_PER_MOTOR_REV * MOTOR_REVS_PER_WHEEL_REV);
	public static final long TICKS_PER_INCH = (long)((double)TICKS_PER_WHEEL_REV / WHEEL_CIRCUMFERENCE);
	public static final int NINETY_DEGREE_TURN = 190;
	public static final int MAX_DIPLACEMENT_DIFFERENCE = 200;
	
	public DriveTrain leftDrive, rightDrive;
	public Sonar leftSonar, rightSonar;

	public RobotDrive(DriveTrain leftDrive, DriveTrain rightDrive, Sonar leftSonar, Sonar rightSonar) {
		this.leftDrive = leftDrive;
		this.rightDrive = rightDrive;
		this.leftDrive.enable();
		this.rightDrive.enable();

		this.leftSonar = leftSonar;
		this.rightSonar = rightSonar;
	}

	public void setSpeed(double speed) {
		setSpeed(speed, speed);
	}

	public void setSpeed(double leftSpeed, double rightSpeed) {
		leftDrive.setTargetSpeed(DriveTrain.MAXIMUM_ENCODERS_PER_SECOND*leftSpeed);
		rightDrive.setTargetSpeed(-DriveTrain.MAXIMUM_ENCODERS_PER_SECOND*rightSpeed);
	}
	
	public void addToSpeed(double leftAdd, double rightAdd) {
		leftDrive.setTargetSpeed(DriveTrain.MAXIMUM_ENCODERS_PER_SECOND*leftAdd + leftDrive.getTargetSpeed());
		rightDrive.setTargetSpeed(DriveTrain.MAXIMUM_ENCODERS_PER_SECOND*rightAdd + rightDrive.getTargetSpeed());
	}

	public void move(int encoderTicks, double speed) {
		setSpeed(speed);

		long leftStartingTicks = leftDrive.encoder.get();
		long rightStartingTicks = rightDrive.encoder.get();
		boolean leftDone, rightDone;
		do {
			int leftDisplacement = (int) (leftDrive.encoder.get() - leftStartingTicks);
			int rightDisplacement = (int) (rightDrive.encoder.get() - rightStartingTicks);
			
			int displacementDifference = leftDisplacement - rightDisplacement;
			if(Math.abs(displacementDifference) > 100) {
				double deltaSpeed = displacementDifference/(double)MAX_DIPLACEMENT_DIFFERENCE;
				if(rightDisplacement > leftDisplacement) {
					//setSpeed(leftDrive.getTargetSpeed() < rightDrive.getTargetSpeed() ? rightDrive.getTargetSpeed() : leftDrive.getTargetSpeed(), 
					//		rightDrive.getTargetSpeed());
					//addToSpeed(0.01, -0.01);
				} else {
					//setSpeed(rightDrive.getTargetSpeed() < leftDrive.getTargetSpeed() ? leftDrive.getTargetSpeed() : rightDrive.getTargetSpeed(), 
					//		leftDrive.getTargetSpeed());
					//addToSpeed(-0.01, 0.01);
				}
			}
			
			leftDone = Math.abs(leftDisplacement) > encoderTicks;
			rightDone = Math.abs(rightDisplacement) > encoderTicks;
			
			Timer.delay(0.05);
		} while (!leftDone || !rightDone);

		setSpeed(0.0);
	};

	public void moveDistance(double distance, double speed) {
		move((int)(distance*TICKS_PER_INCH), speed);
		setSpeed(0.0);
	}

	public void turn(double degrees, double speed) {
		setSpeed(speed, -speed);
		
		long leftStartingTicks = leftDrive.encoder.get();
		long rightStartingTicks = rightDrive.encoder.get();
		
		while((Math.abs(leftDrive.encoder.get() - leftStartingTicks) + Math.abs(rightDrive.encoder.get() - rightStartingTicks))/2 < (degrees/90.0)*NINETY_DEGREE_TURN) {
			Timer.delay(0.01);
		}
		
		setSpeed(0.0);
	}

}
