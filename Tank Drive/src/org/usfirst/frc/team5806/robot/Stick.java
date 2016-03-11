package org.usfirst.frc.team5806.robot;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;

public class Stick {
	Victor motorController;
	public Stick(int controllerChannel) {
		motorController = new Victor(controllerChannel);
	}
	
	public void lift() {
		motorController.set(1);
	}
	
	public void stay() {
		motorController.set(0);
	}
	
	public void lower() {
		motorController.set(-1);
	}
}