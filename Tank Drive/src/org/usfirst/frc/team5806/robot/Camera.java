package org.usfirst.frc.team5806.robot;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.vision.USBCamera;

public class Camera extends USBCamera {

	private NetworkTable table;
	private static final double[] defaultValue = {-20.0, -20.0, -20.0};
	
	public Camera(String name) {
		super(name);
		table = NetworkTable.getTable("GRIP/myContoursReport");
		
	}

	// Wrap all calls in an if (contoursExist()) {...}
	public boolean contoursExist (int index) {
		return ( (table.getNumberArray("area", defaultValue ))[index] != -20);
	}
	
	// returns X value of contour index
	public double getContourX (int index) {
		return  table.getNumberArray("centerX", defaultValue)[index];
	}
	
	public double getContourY (int index) {
		return table.getNumberArray("centerY", defaultValue)[index];
	}
	
	public double getContourHeight (int index) {
		return  table.getNumberArray("height", defaultValue)[index];
	}
	
	public double getContourWidth (int index) {
		return table.getNumberArray("width", defaultValue)[index];
	}
	
}
