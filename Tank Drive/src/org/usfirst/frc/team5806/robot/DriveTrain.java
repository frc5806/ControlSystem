package org.usfirst.frc.team5806.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

public class DriveTrain extends PIDSubsystem {
	private static final int SAMPLE_PERIOD_MILLIS = 100;
	public static final int MAXIMUM_ENCODERS_PER_SECOND = 10000;
	
	Talon motorController;
	Encoder encoder;
	
	double targetEncoderSpeed;
	public double lastMotorSpeed;
	
	public DriveTrain(Talon motorController, Encoder encoder, int startingEncoderSpeed) {
		super("DriveTrain", 1, 0, 0);
		
		setAbsoluteTolerance(0.1);
		getPIDController().setContinuous(false);
		setInputRange(-MAXIMUM_ENCODERS_PER_SECOND, MAXIMUM_ENCODERS_PER_SECOND);
		
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
		return targetEncoderSpeed - encoderSpeed;
	}

	@Override
	protected void usePIDOutput(double output) {
		//System.out.println("output: " + output);
		motorController.pidWrite(output* 0.2);
	}

}
