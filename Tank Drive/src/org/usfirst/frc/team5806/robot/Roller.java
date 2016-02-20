package org.usfirst.frc.team5806.robot;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Roller extends PIDSubsystem {
	private static final int SAMPLE_PERIOD_MILLIS = 50;
	public static final int MAXIMUM_RPM = 9000;
	private static final int TIME_TO_FULL_SPEED_MILLIS = 1000;

	Talon motorController;
	public MagnetSensor encoder;
	
	double targetRPM;
	double currentTargetSpeed;
	long startingMillis;
	
	boolean isReverse = false;
	boolean isForward = false;
	
	public Roller(Talon motorController, MagnetSensor encoder) {
		super("Roller", 1, 0, 0);
		
		setAbsoluteTolerance(0.05);
		getPIDController().setContinuous(false);

		this.motorController = motorController;
		this.encoder = encoder;

		stop();
	}
	
	public void debugSetSpeed(double speed) {
		motorController.set(speed);
	}
	
	public void forward() {
		if(isForward == false) {
			setTargetSpeed(7000);
			isForward = true;
			isReverse = false;
		}
	}
	
	public void reverse() {
		if(isReverse == false) {
			setTargetSpeed(-3000);
			isReverse = true;
			isForward = false;
		}
	}
	
	public void stop() {
		if(isForward || isReverse) {
			setTargetSpeed(0);
			isForward = false;
			isReverse = false;
		}
	}
	
	public void toggleReverse() {
		if(isReverse) stop();
		else reverse();
	}
	
	private void setTargetSpeed(double speed) {
		targetRPM = speed;
		currentTargetSpeed = 0;
		startingMillis = System.currentTimeMillis();
	}

	@Override
	protected void initDefaultCommand() {}
	
	@Override
	protected double returnPIDInput() {
		double rpm = encoder.getRPM(SAMPLE_PERIOD_MILLIS);
		if(targetRPM < 0) rpm *= -1;
		SmartDashboard.putNumber("Roller RPM", rpm);
		return (currentTargetSpeed - rpm) / (double)MAXIMUM_RPM;
	}

	@Override
	protected void usePIDOutput(double output) {
		SmartDashboard.putNumber("Current target", currentTargetSpeed);
		SmartDashboard.putNumber("Max target", targetRPM);
		SmartDashboard.putNumber("Output", output);
		if(!isReverse && !isForward) motorController.set(0);
		if(isForward) motorController.set(0.75);
		if(isReverse) motorController.set(-0.35);
		
		// Update speed
		long millisSince = System.currentTimeMillis() - startingMillis;
		if(millisSince < TIME_TO_FULL_SPEED_MILLIS) currentTargetSpeed = (millisSince / (double)TIME_TO_FULL_SPEED_MILLIS) * targetRPM;
		else currentTargetSpeed = targetRPM;
		SmartDashboard.putNumber("Millis since", millisSince);
	}
}
 