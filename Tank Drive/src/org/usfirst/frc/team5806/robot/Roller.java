package org.usfirst.frc.team5806.robot;

import edu.wpi.first.wpilibj.Talon;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Roller extends PIDSubsystem {
	private enum RollerState {
		FORWARDS, REVERSE, STOPPED;
	}

	private static final int SAMPLE_PERIOD_MILLIS = 50;
	public static final int MAXIMUM_RPM = 9000;
	public static final int TIME_TO_FULL_SPEED_MILLIS = 1000;

	Talon motorController;
	public MagnetSensor encoder;
	
	double targetRPM;
	double currentTargetSpeed;
	long startingMillis;
	
	RollerState state;
	
	public Roller(Talon motorController, MagnetSensor encoder) {
		super("Roller", 1, 0, 0);
		
		setAbsoluteTolerance(0.05);
		getPIDController().setContinuous(false);

		this.motorController = motorController;
		this.encoder = encoder;

		stop();
	}
	
	public void forward() {
		setTargetSpeed(5000);
		state = RollerState.FORWARDS;
	}
	
	public void reverse() {
		setTargetSpeed(-3000);
		state = RollerState.REVERSE;
	}
	
	public void stop() {
		setTargetSpeed(0);
		state = RollerState.STOPPED;
	}
	
	public void toggleReverse() {
		if(state != RollerState.STOPPED) stop();
		else reverse();
	}
	
	private void setTargetSpeed(double speed) {
		if(speed != targetRPM) {
			targetRPM = speed;
			currentTargetSpeed = 0;
			startingMillis = System.currentTimeMillis();
		}
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
		if(state == RollerState.STOPPED) motorController.set(0);
		else if(state == RollerState.FORWARDS) motorController.set(-1);
		else motorController.set(0.23);
		
		// Update speed
		long millisSince = System.currentTimeMillis() - startingMillis;
		if(millisSince < TIME_TO_FULL_SPEED_MILLIS) currentTargetSpeed = (millisSince / (double)TIME_TO_FULL_SPEED_MILLIS) * targetRPM;
		else currentTargetSpeed = targetRPM;
		
		SmartDashboard.putNumber("Millis since last update", millisSince);
		SmartDashboard.putNumber("Current target speed", currentTargetSpeed);
		SmartDashboard.putNumber("Overall target speed", targetRPM);
		SmartDashboard.putNumber("PID Output", output);
		SmartDashboard.putNumber("Robot State", state.ordinal());
	}
}
 