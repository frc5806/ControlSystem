package org.usfirst.frc.team5806.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;

public class RobotDrive {
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
	}

	public void setSpeed(double speed) {
		setSpeed(speed, speed);
	}

	public void setSpeed(double leftSpeed, double rightSpeed) {
		leftDrive.setTargetSpeed(leftSpeed);
		rightDrive.setTargetSpeed(rightSpeed);
	}

	public void move(int encoderTicks) {
		move(encoderTicks, encoderTicks);
	};

	public void move(int leftEncoderTicks, int rightEncoderTicks) {
		double leftOriginalSpeed = rightDrive.getTargetSpeed();
		double rightOriginalSpeed = rightDrive.getTargetSpeed();
		long leftStartingTicks = leftDrive.encoder.get();
		long rightStartingTicks = rightDrive.encoder.get();

		boolean leftDone, rightDone;
		do {
			leftDone = leftDrive.encoder.get() - leftStartingTicks < leftEncoderTicks;
			rightDone = rightDrive.encoder.get() - rightStartingTicks < rightEncoderTicks;

			// Turn off the motor if that side has moved its necessary encoder
			// ticks
			// THIS IS A PROBLEM. Will not acctually stay at 0 encoder ticks.
			// Instead, will move in conjunction with the other side of the
			// robot a little bit
			if (leftDone) leftDrive.setTargetSpeed(0.0f);
			if (rightDone) rightDrive.setTargetSpeed(0.0f);
		} while (!leftDone && !rightDone);

		setSpeed(leftOriginalSpeed, rightOriginalSpeed);
	}

	public void pointTurn(double degrees, double speed) {
		double startingAngle = gyro.getAngle();

		if (speed > 0)setSpeed(speed, -speed);
		else setSpeed(-speed, speed);
		
		while (gyro.getAngle() - startingAngle < degrees) Timer.delay(0.01);
		
		setSpeed(0.0);
	}

}
