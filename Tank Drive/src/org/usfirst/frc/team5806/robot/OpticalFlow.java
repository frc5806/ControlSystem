package org.usfirst.frc.team5806.robot;

import edu.wpi.first.wpilibj.SerialPort;

public class OpticalFlow {
	class OpticalFlowReader implements Runnable {

		@Override
		public void run() {
			int[] newDisplacement = readDisplacement();
			totalDisplacement[0] += newDisplacement[0];
			totalDisplacement[1] += newDisplacement[1];
		}
		
	}
	
	SerialPort connection;
	int[] totalDisplacement;
	
	public OpticalFlow() {
		connection = new SerialPort(9600, SerialPort.Port.kUSB);
		totalDisplacement = new int[]{0, 0};
	}
	
	public int[] getDisplacement() {
		return totalDisplacement;
	}
	
	private int[] readDisplacement() {
		String line = readString();
		String[] components = line.split(" ");
		return new int[]{Integer.parseInt(components[0]), Integer.parseInt(components[1])};
	}
	
	private String readString() {
		String returnString = "";
		
		while(true) {
			char buffer = (char)connection.read(1)[0];
			if(buffer != '\n') returnString += buffer;
			else break;
		}
		
		return returnString;
	}

}
