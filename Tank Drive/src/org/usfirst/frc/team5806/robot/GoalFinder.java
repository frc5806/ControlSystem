package org.usfirst.frc.team5806.robot;

import java.io.IOException;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class GoalFinder {
	private NetworkTable table;
	private static final double[] defaultValue = {-20.0, -20.0, -20.0};
	
	public GoalFinder() {
		/*table = NetworkTable.getTable("grip");
		
		try {
            new ProcessBuilder("/home/lvuser/grip").inheritIO().start();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
	}
	
	public double[][] getGoalCenters() {
		/*double[] centerXs = table.getNumberArray("centerX", defaultValue);
		double[] centerYs = table.getNumberArray("centerX", defaultValue);
		double[][] centers = new double[centerXs.length][2];
		for(int a = 0; a < centers.length; a++) {
			centers[a] = new double[]{centerXs[a], centerYs[a]};
		}
		
		return centers;*/
		return null;
	}

	/*// Wrap all calls in an if (contoursExist()) {...}
	public boolean contoursExist (int index) {
		return (table.getNumberArray("area", defaultValue ))[index] != -20;
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
	}*/
}
