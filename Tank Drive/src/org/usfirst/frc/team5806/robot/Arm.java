package org.usfirst.frc.team5806.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Arm {
	public DoubleSolenoid armSolenoid;
	DoubleSolenoid pushSolenoid;
	boolean isRaised;
	boolean isPushed;
	
	public Arm(DoubleSolenoid armSolenoid, DoubleSolenoid pushSolenoid) {
		this.armSolenoid = armSolenoid;
		this.armSolenoid.set(DoubleSolenoid.Value.kReverse);
		
		this.pushSolenoid = pushSolenoid;
		this.pushSolenoid.set(DoubleSolenoid.Value.kForward);
		
		isRaised = false;
		isPushed = false;
	}
	
	public void raise() {
		SmartDashboard.putBoolean("Arm raised", true);
		armSolenoid.set(DoubleSolenoid.Value.kForward);
		isRaised = true;
	}
	
	public void lower() {
		SmartDashboard.putBoolean("Arm raised", false);
		armSolenoid.set(DoubleSolenoid.Value.kReverse);
		isRaised = false;
	}
	
	public void push() {
		SmartDashboard.putBoolean("Pusher out", true);
		pushSolenoid.set(DoubleSolenoid.Value.kReverse);
		isPushed = true;
	}
	
	public void retract() {
		SmartDashboard.putBoolean("Pusher out", false);
		pushSolenoid.set(DoubleSolenoid.Value.kForward);
		isPushed = false;
	}
	
	public void toggleElevation() {
		if(isRaised) lower();
		else raise();
	}
	
	public void togglePusher() {
		if(isPushed) retract();
		else push();
	}
}
