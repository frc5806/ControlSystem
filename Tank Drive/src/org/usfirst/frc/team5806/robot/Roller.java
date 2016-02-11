package org.usfirst.frc.team5806.robot;

import edu.wpi.first.wpilibj.Talon;

public class Roller {
	Talon motorController;
	MagnetSensor encoder;
	float speed;
	float speedRange;
	float lastRPM;
	float targetRPM;
	
	public Roller(int talonChannel, int magneticChannel) {
		motorController = new Talon(talonChannel);
		encoder = new MagnetSensor(magneticChannel);

		speed = 0;
		targetRPM = 0;
	}
	
	public void forward() {
		setRPM(3600, true);
	}
	
	public void reverse() {
		setRPM(1800, false);
	}
	
	public void stop() {
		targetRPM = 0;
	}
	
	public void update() {
		float rpm = getRPM();
		if(rpm != lastRPM) {
			speedRange /= 2.0f;
			boolean isSlow = rpm < targetRPM;
			boolean isForwards = speed > 0;
			float speedIncrement = (isForwards && isSlow) || (!isForwards && !isSlow) ? speedRange : -speedRange;
			
			speed += speedIncrement;
			lastRPM = rpm;
		}
			
		motorController.set(speed);
	}
	
	private void setRPM(float rpm, boolean isForwards) {
		targetRPM = rpm;
		speed = isForwards ? 50 : -50;
		speedRange = 1;
	}
	
	public float getRPM() {
		return encoder.getRPM();
	}
}
 