package org.usfirst.frc.team5806.robot;

import edu.wpi.first.wpilibj.AnalogInput;

public class Sonar extends AnalogInput {
	//At <= 4.88 mV, use 1.0246 mm / mV
	//At 4.88 mV < V <= 293 mV, use 1.0239 mm / mV
	//At 293 mV < V <= 4885 mV, use 1.0235 mm / mV
	private static final double[] mvThresholds = {4.88, 293, 4885};
	private static final double[] voltDistanceConstants = {
		//each constant is a number of mm per mV with a certain mV value
		1.0246, 1.0239, 1.0235
	};
	private static final double FT_PER_MM = 0.00328084;
	
	private int channel;
	
	public Sonar(int c) {
		super(c);
		channel = c;
	}
	
	public double getMM() {
		double milivolts = getVoltage() * 1000;
		double constant = -1;
		for (int i = 0; i < mvThresholds.length; i++) {
			if (milivolts <= mvThresholds[i]) {
				constant = voltDistanceConstants[i];
				break;
			}
		}
		if (constant == -1) constant = voltDistanceConstants[voltDistanceConstants.length - 1];
		double milimeters = milivolts * constant;
		return milimeters;
	}
	public double getFeet() {
		return getMM() * FT_PER_MM;
	}
	public int getChannel() {
		return channel;
	}
}