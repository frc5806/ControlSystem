package org.usfirst.frc.team5806.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Arm {
	DoubleSolenoid solenoid;
	boolean isRaised;
	
	public Arm(int solenoidValve1, int solenoidValve2) {
		solenoid = new DoubleSolenoid(solenoidValve1, solenoidValve2);
		solenoid.set(DoubleSolenoid.Value.kForward);
		
		isRaised = false;
	}
	
	public void raise() {
		solenoid.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void lower() {
		solenoid.set(DoubleSolenoid.Value.kForward);
	}
	
	public void toggle() {
		if(isRaised) lower();
		else raise();
		
		isRaised = !isRaised;
	}
}
