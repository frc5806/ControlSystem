package org.usfirst.frc.team5806.robot;

import edu.wpi.first.wpilibj.SerialPort;

public class IMU {
	SerialPort serial;
	
	public IMU() {
		serial = new SerialPort(11500, SerialPort.Port.kOnboard);
	}
	
	public String getRotationalDisplacement() {
		return serial.readString(1);
	}
}
