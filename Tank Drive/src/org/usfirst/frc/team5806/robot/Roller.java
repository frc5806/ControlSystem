package org.usfirst.frc.team5806.robot;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Roller extends PIDSubsystem {
	private static final int SAMPLE_PERIOD_MILLIS = 1000;
	public static final int MAXIMUM_RPM = 1000;
	private static final int TIME_TO_FULL_SPEED_MILLIS = 10000;

	Talon motorController;
	public MagnetSensor encoder;

	boolean isForwards;
	float targetRPM;
	float currentTargetSpeed;
	long startingMillis;
	
	public Roller(Talon motorController, MagnetSensor encoder) {
		super("Roller", 1, 0, 0);

		this.motorController = motorController;
		this.encoder = encoder;

		stop();
	}
	
	public void debugSetSpeed(double speed) {
		motorController.set(speed);
	}
	
	public void forward() {
		setTargetSpeed(1000);
	}
	
	public void reverse() {
		setTargetSpeed(-500);
	}
	
	public void stop() {
		setTargetSpeed(0);
	}
	
	private void setTargetSpeed(double speed) {
		targetRPM = (float) Math.abs(speed);
		isForwards = speed > 0;
		currentTargetSpeed = 0;
		startingMillis = System.currentTimeMillis();
	}

	@Override
	protected void initDefaultCommand() {}
	
	@Override
	protected double returnPIDInput() {
		//return (currentTargetSpeed - encoder.getRPM(SAMPLE_PERIOD_MILLIS)) / MAXIMUM_RPM;
		return 0;
	}

	@Override
	protected void usePIDOutput(double output) {
		SmartDashboard.putNumber("Current target", currentTargetSpeed);
		SmartDashboard.putNumber("Output", output);
		//motorController.pidWrite(output);
		
		// Update speed
		long millisSince = System.currentTimeMillis() - startingMillis;
		if(millisSince < TIME_TO_FULL_SPEED_MILLIS) currentTargetSpeed = (millisSince / TIME_TO_FULL_SPEED_MILLIS) * targetRPM;
	}
}
 