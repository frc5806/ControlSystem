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
	private boolean isReversed;
	
	// ---------------------- Public Methods ------------------------------
	public DriveTrain(Talon motorController, Encoder encoder, int startingSpeed, boolean isReversed) {
		super("DriveTrain", 1, 0, 0);
		
		setAbsoluteTolerance(0.05);
		getPIDController().setContinuous(false);
		
		this.motorController = motorController;
		this.encoder = encoder;
		this.isReversed = isReversed;
		
		setTargetSpeed(startingSpeed);
	}
	
	public double getTargetSpeed() { 
		return targetEncoderSpeed; 
	}
	
	public void setTargetSpeed(double targetSpeed) {
		this.targetEncoderSpeed = targetSpeed*MAXIMUM_ENCODERS_PER_SECOND;
		
		// If this side is reversed, then reverse the target speeds
		if(isReversed) this.targetEncoderSpeed *= -1;
	}
	
	// ---------------------- Protected Methods ------------------------------
	@Override
	protected void initDefaultCommand() {}
	
	@Override
	protected double returnPIDInput() {
		long currentEncoderTicks = encoder.get();
		
		Timer.delay(SAMPLE_PERIOD_MILLIS/1000.0);
		
		double encoderSpeed = (encoder.get() - currentEncoderTicks) / (double)(SAMPLE_PERIOD_MILLIS / 1000.0f);
		if(isReversed) encoderSpeed *= -1; // Reversal
		return (targetEncoderSpeed - encoderSpeed) / MAXIMUM_ENCODERS_PER_SECOND;
	}

	@Override
	protected void usePIDOutput(double output) {
		// Threshold
		if(Math.abs(targetEncoderSpeed) > MAXIMUM_ENCODERS_PER_SECOND * 0.15) motorController.pidWrite(output);
		else motorController.set(0);
	}

}
