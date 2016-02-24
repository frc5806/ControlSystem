package org.usfirst.frc.team5806.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain extends PIDSubsystem {
	private static final int SAMPLE_PERIOD_MILLIS = 50;
	public static final int MAXIMUM_ENCODERS_PER_SECOND = 100000;
	
	public Talon motorController;
	public Encoder encoder;
	
	private double targetEncoderSpeed;
	
	public DriveTrain(Talon motorController, Encoder encoder, int startingEncoderSpeed) {
		super("DriveTrain", 1, 0, 0);
		
		setAbsoluteTolerance(0.05);
		getPIDController().setContinuous(false);
		
		this.motorController = motorController;
		this.encoder = encoder;
		this.targetEncoderSpeed = startingEncoderSpeed;
	}
	
	public double getTargetSpeed() { return targetEncoderSpeed; }
	public void setTargetSpeed(double targetEncoderSpeed) {
		if(targetEncoderSpeed > MAXIMUM_ENCODERS_PER_SECOND) targetEncoderSpeed = MAXIMUM_ENCODERS_PER_SECOND;
		if(targetEncoderSpeed < -MAXIMUM_ENCODERS_PER_SECOND) targetEncoderSpeed = -MAXIMUM_ENCODERS_PER_SECOND;
		
		this.targetEncoderSpeed = targetEncoderSpeed;
	}
	
	@Override
	protected void initDefaultCommand() {}
	
	@Override
	protected double returnPIDInput() {
		long currentEncoderTicks = encoder.get();
		Timer.delay(SAMPLE_PERIOD_MILLIS/1000.0);
		double encoderSpeed = (encoder.get() - currentEncoderTicks) / (double)(SAMPLE_PERIOD_MILLIS / 1000.0f);
		return (targetEncoderSpeed - encoderSpeed) / MAXIMUM_ENCODERS_PER_SECOND;
	}

	@Override
	protected void usePIDOutput(double output) {
		if(Math.abs(targetEncoderSpeed) > MAXIMUM_ENCODERS_PER_SECOND * 0.15) motorController.pidWrite(output);
		else motorController.set(0);
	}

}
