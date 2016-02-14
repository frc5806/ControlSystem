package org.usfirst.frc.team5806.robot;

import edu.wpi.first.wpilibj.SerialPort;

public class IMU {
	SerialPort serial;
	
	public IMU() {
		System.out.println("Gonna start IMU!!!!!!!!!!!!!!!!!");
		try{
			serial = new SerialPort(57600, SerialPort.Port.kUSB);
		} catch (Exception e) {
			System.out.println("There was an imu error");
			e.printStackTrace();
		}
		System.out.println("Done starting IMU!!!!!!!!!!!!!!!!!!!!");
	}
	
	public String getRotationalDisplacement() {
		if (serial != null) return ""+serial.getBytesReceived();
		else return "error";
	}
}
