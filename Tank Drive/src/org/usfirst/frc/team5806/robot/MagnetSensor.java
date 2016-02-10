package org.usfirst.frc.team5806.robot;

import edu.wpi.first.wpilibj.DigitalInput;

public class MagnetSensor {
	float rpm;
	DigitalInput magnetSwitch;
	Thread thread;
	
	
	private class MagnetThread implements Runnable {
		public float getRollerRPM(int samplePeriodMillis) {
			int magnetCounter = 0;
			boolean detectedLastTime = false;
			long startingTime = System.currentTimeMillis();
			
			while(System.currentTimeMillis() - startingTime < samplePeriodMillis) {
				if (!magnetSwitch.get() == true) {
					if(detectedLastTime == false){
						magnetCounter++;
						detectedLastTime = true;
					}
				} else {
					detectedLastTime = false;
				}
			}
			
			return magnetCounter / (float)(samplePeriodMillis / (float)60000);
		}
		
		@Override
		public void run() {
			while(true) {
				rpm = getRollerRPM(1000);
			}
		}
		
	}
	
	public MagnetSensor(int magnetSwitchChannel) {
		magnetSwitch = new DigitalInput(magnetSwitchChannel);
		
		thread = new Thread(new MagnetThread());
		thread.start();
	}
	
	public boolean getMagnet() {
		return !magnetSwitch.get();
	}
	
	public float getRPM() {
		return rpm;
	}
}
