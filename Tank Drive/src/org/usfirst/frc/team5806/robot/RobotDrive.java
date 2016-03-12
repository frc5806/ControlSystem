package org.usfirst.frc.team5806.robot;
	
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RobotDrive {
	public static final double WHEEL_DIAMETER = 7.65;
	public static final double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI;
	public static final long TICKS_PER_MOTOR_REV = 62;
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
		leftDrive.setTargetSpeed(leftSpeed);
		rightDrive.setTargetSpeed(rightSpeed);
		SmartDashboard.putNumber("Left Target", leftDrive.getTargetSpeed());
		SmartDashboard.putNumber("Right Target", rightDrive.getTargetSpeed());
	}
	
	public void addToSpeed(double leftAdd, double rightAdd) {
		setSpeed(leftAdd + leftDrive.getTargetSpeed(), rightAdd + rightDrive.getTargetSpeed());
	}
	
	
	// TODO: check directions of encoders in teleop
	// 
	public void move(int encoderTicks, double speed) {
		// Set speed to mean speed
		setSpeed(speed);
		
		// Get beginning encoder vals
		long leftStartingTicks = leftDrive.encoder.get();
		long rightStartingTicks = rightDrive.encoder.get();
		
		// Each bool tells us if that side has moved far enough
		// When both have moved far enough, we stop moving
		boolean leftDone, rightDone;
		do {
			// Get total diplacements
			int leftDisplacement = (int) -(leftDrive.encoder.get() - leftStartingTicks);
			int rightDisplacement = (int) -(rightDrive.encoder.get() - rightStartingTicks);
			SmartDashboard.putNumber("Left distance", leftDisplacement);
			SmartDashboard.putNumber("Right distance", rightDisplacement);
			
			// Difference between the two sides diplacement
			int displacementDifference = leftDisplacement - rightDisplacement;
			if(Math.abs(displacementDifference) > 5) {
				// Past this displacement, we just go at our max variance
				double maxDisplacementDifference = 150;
				double maxVariance = 0.4;
				
				// Our variance is the ratio of difference in displacement to max difference
				double speedVariance = maxVariance * Math.abs(displacementDifference) / maxDisplacementDifference;
				SmartDashboard.putNumber("Speed Variance", speedVariance);
				if(speedVariance > maxVariance) speedVariance = maxVariance;
				
				// If right side encoders have moved more forwards, then lessen the right side's motor value
				if(rightDisplacement > leftDisplacement) {
					SmartDashboard.putBoolean("Is right moving faster", true);
					setSpeed(speed + speedVariance, speed - speedVariance);
				} else {
					SmartDashboard.putBoolean("Is right moving faster", false);
					setSpeed(speed - speedVariance, speed + speedVariance);
				}
			}
			
			leftDone = Math.abs(leftDisplacement) > encoderTicks;
			rightDone = Math.abs(rightDisplacement) > encoderTicks;
			Timer.delay(1);
			//Timer.delay(0.01);
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
