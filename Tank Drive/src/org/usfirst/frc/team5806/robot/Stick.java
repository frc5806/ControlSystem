package org.usfirst.frc.team5806.robot;

import edu.wpi.first.wpilibj.Talon;

public class Stick {
	Talon motorController;
	public Stick(int controllerChannel) {
		motorController = new Talon(controllerChannel);
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
