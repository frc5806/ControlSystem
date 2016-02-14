package org.usfirst.frc.team5806.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

public class DriveTrain extends PIDSubsystem {
	private static final int SAMPLE_PERIOD_MILLIS = 100;
	public static final int MAXIMUM_ENCODERS_PER_SECOND = 10000;
	
	Talon motorController;
	Encoder encoder;
	
	double targetEncoderSpeed;
	double lastMotorSpeed;
	
	public DriveTrain(Talon motorController, Encoder encoder, int startingEncoderSpeed) {
		super("DriveTrain", 1, 0, 0);
		
		this.motorController = motorController;
		this.encoder = encoder;
		this.targetEncoderSpeed = startingEncoderSpeed;
		this.lastMotorSpeed = 0;
	}
	
	@Override
	protected void initDefaultCommand() {}
	
	public void setSpeed(double targetEncoderSpeed) {
		this.targetEncoderSpeed = targetEncoderSpeed;
	}
	
	@Override
	protected double returnPIDInput() {
		long currentEncoderTicks = encoder.get();
		try {
			Thread.sleep(SAMPLE_PERIOD_MILLIS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int encoderSpeed = (int) (encoder.get() - currentEncoderTicks) / (1000 / SAMPLE_PERIOD_MILLIS);
		
		return targetEncoderSpeed - encoderSpeed;
	}

	@Override
	protected void usePIDOutput(double output) {
		lastMotorSpeed += output / MAXIMUM_ENCODERS_PER_SECOND;
		motorController.set(lastMotorSpeed);
	}

}
