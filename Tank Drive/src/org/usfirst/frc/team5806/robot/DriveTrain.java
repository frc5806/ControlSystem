package org.usfirst.frc.team5806.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain extends PIDSubsystem {
	private static final int SAMPLE_PERIOD_MILLIS = 100;
	public static final int MAXIMUM_ENCODERS_PER_SECOND = 100000;
	
	public Talon motorController;
	public Encoder encoder;
	
	double targetEncoderSpeed;
	public double lastMotorSpeed;
	
	public DriveTrain(Talon motorController, Encoder encoder, int startingEncoderSpeed) {
		super("DriveTrain", 1, 0, 0);
		
		setAbsoluteTolerance(0.1);
		getPIDController().setContinuous(false);
		
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
		float encoderSpeed = (float)(encoder.get() - currentEncoderTicks) / (float)(SAMPLE_PERIOD_MILLIS / 1000.0f);
		SmartDashboard.putNumber("Speed", encoderSpeed);
		return (targetEncoderSpeed - encoderSpeed) / MAXIMUM_ENCODERS_PER_SECOND;
	}

	@Override
	protected void usePIDOutput(double output) {
		SmartDashboard.putNumber("PID output", output);
		//motorController.set(0.0f);
		motorController.pidWrite(output);
	}

}
