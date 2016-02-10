package org.usfirst.frc.team5806.robot;

import edu.wpi.first.wpilibj.Talon;

public class Roller {
	Talon motorController;
	float speed;
	public Roller(int talonChannel, int magneticSensor) {
		motorController = new Talon(talonChannel);
		
		speed = 0;
	}
	
	public void forward() {
		speed = -1.0f;
	}
	
	public void reverse() {
		speed = 0.5f;
	}
	
	public void stop() {
		speed = 0;
	}
	
	public void update() {
		motorController.set(speed);
	}
}
