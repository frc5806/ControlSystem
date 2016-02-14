package org.usfirst.frc.team5806.robot;

import edu.wpi.first.wpilibj.Talon;

public class Roller extends PIDSubsystem {
	private static final int SAMPLE_PERIOD_MILLIS = 100;
	public static final int MAXIMUM_RPM = 40000;

	Talon motorController;
	MagnetSensor encoder;

	boolean isForwards;
	float lastMotorSpeed;
	float targetRPM;
	
	public Roller(int talonChannel, int magneticChannel) {
		super("Roller", 1, 0, 0);

		motorController = new Talon(talonChannel);
		encoder = new MagnetSensor(magneticChannel);

		stop();
	}
	
	public void forward() {
		targetRPM = 3600;
		isForwards = true;
	}
	
	public void reverse() {
		targetRPM = 1800;
		isForwards = false;
	}
	
	public void stop() {
		targetRPM = 0;
	}

	@Override
	protected void initDefaultCommand() {}
	
	@Override
	protected double returnPIDInput() {
		return targetRPM - encoder.getRPM(SAMPLE_PERIOD_MILLIS);
	}

	@Override
	protected void usePIDOutput(double output) {
		lastMotorSpeed += (output / MAXIMUM_RPM)*(isForwards ? 1 : -1);
		motorController.set(lastMotorSpeed);
	}
}
 