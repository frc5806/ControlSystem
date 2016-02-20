package org.usfirst.frc.team5806.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Arm {
	DoubleSolenoid armSolenoid;
	DoubleSolenoid pushSolenoid;
	boolean isRaised;
	boolean isPushed;
	
	public Arm(DoubleSolenoid armSolenoid, DoubleSolenoid pushSolenoid) {
		this.armSolenoid = armSolenoid;
		this.armSolenoid.set(DoubleSolenoid.Value.kForward);
		
		this.pushSolenoid = pushSolenoid;
		this.pushSolenoid.set(DoubleSolenoid.Value.kReverse);
		
		isRaised = false;
		isPushed = false;
	}
	
	public void raise() {
		armSolenoid.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void lower() {
		armSolenoid.set(DoubleSolenoid.Value.kForward);
	}
	
	public void push() {
		pushSolenoid.set(DoubleSolenoid.Value.kForward);
	}
	
	public void retract() {
		pushSolenoid.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void toggleElevation() {
		if(isRaised) lower();
		else raise();
		
		isRaised = !isRaised;
	}
	
	public void togglePusher() {
		if(isPushed) retract();
		else push();
		
		isPushed = !isPushed;
	}
}
