package org.usfirst.frc.team5806.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;

public class RobotDrive {
	public static final double WHEEL_DIAMETER = 7.65;
	public static final double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI;
	public static final long TICKS_PER_MOTOR_REV = 1440;
	public static final double MOTOR_REVS_PER_WHEEL_REV = 1 / 0.28;
	public static final long TICKS_PER_WHEEL_REV = (long)(TICKS_PER_MOTOR_REV * MOTOR_REVS_PER_WHEEL_REV);
	public static final long TICKS_PER_INCH = (long)((double)TICKS_PER_WHEEL_REV / WHEEL_CIRCUMFERENCE);
	
	public DriveTrain leftDrive, rightDrive;
	public Sonar leftSonar, rightSonar;
	public ADXRS450_Gyro gyro;

	public RobotDrive(DriveTrain leftDrive, DriveTrain rightDrive, Sonar leftSonar, Sonar rightSonar, ADXRS450_Gyro gyro) {
		this.leftDrive = leftDrive;
		this.rightDrive = rightDrive;
		this.leftDrive.enable();
		this.rightDrive.enable();

		this.leftSonar = leftSonar;
		this.rightSonar = rightSonar;

		this.gyro = gyro;
		this.gyro.calibrate();
	}

	public void setSpeed(double speed) {
		setSpeed(speed, speed);
	}

	public void setSpeed(double leftSpeed, double rightSpeed) {
		leftDrive.setTargetSpeed(leftSpeed);
		rightDrive.setTargetSpeed(rightSpeed);
	}

	public void move(int encoderTicks, double speed) {
		setSpeed(speed);

		long leftStartingTicks = leftDrive.encoder.get();
		long rightStartingTicks = rightDrive.encoder.get();

		boolean leftDone, rightDone;
		do {
			leftDone = leftDrive.encoder.get() - leftStartingTicks < encoderTicks;
			rightDone = rightDrive.encoder.get() - rightStartingTicks < encoderTicks;
		} while (!leftDone && !rightDone);

		setSpeed(0.0);
	};

	public void moveDistance(double distance, double speed) {
		double wheelRevs = distance / WHEEL_CIRCUMFERENCE;
		move((int)(wheelRevs*TICKS_PER_WHEEL_REV), speed);
		
		setSpeed(0.0);
	}

	public void pointTurn(double degrees, double speed) {
		double startingAngle = gyro.getAngle();

		if (speed > 0)setSpeed(speed, -speed);
		else setSpeed(-speed, speed);
		
		while (gyro.getAngle() - startingAngle < degrees) Timer.delay(0.01);
		
		setSpeed(0.0);
	}

}
