package org.usfirst.frc.team5806.robot;

import edu.wpi.first.wpilibj.SerialPort;

public class IMU {
	SerialPort serial;
	
	public IMU() {
		System.out.println("Going to start");
		try {
			serial = new SerialPort(57600, SerialPort.Port.kOnboard);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getRotationalDisplacement() {
		return serial.readString(1);
	}
}
