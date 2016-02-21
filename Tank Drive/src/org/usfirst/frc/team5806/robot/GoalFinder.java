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
}