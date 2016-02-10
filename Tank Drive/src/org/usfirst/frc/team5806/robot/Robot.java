package org.usfirst.frc.team5806.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Robot extends IterativeRobot {
	private static double limitedJoyL, limitedJoyR;
	
	// Driving objects
	RobotDrive robot;
	Joystick joystick;
	
	// Sensors
	Encoder[] encoders;
	DigitalInput magnetSwitch;
	boolean magnetSwitched;

	IMU imu;
	Sonar[] sonars;

	// HAS TO BE A NEGATIVE NUMBER SO IT GOES THE RIGHT WAY
	private static final double DAMPENING_COEFFICIENT = -0.75;
	// MINIMUM CHANGE IN JOYSTICK POSITION TO CAUSE CHANGE IN MOTORS
	private static double rampCoefficient = 0.05;
	
	Button addButton;
	Button subtractButton;
	
	// Pneumatics
	Compressor compressor;
	DoubleSolenoid solen;
	
	public void forward(DoubleSolenoid s) {
	    s.set (DoubleSolenoid.Value.kForward);
	}
	
	public void reverse(DoubleSolenoid s) {
	    s.set (DoubleSolenoid.Value.kReverse);
	}
	
	public void off(Compressor c, DoubleSolenoid s) {
	    c.stop();
	    s.set (DoubleSolenoid.Value.kOff);
	}
	
	public float getRollerRPM(int samplePeriodMillis) {
		int magnetCounter = 0;
		boolean detectedLastTime = false;
		long startingTime = System.currentTimeMillis();
		
		while(System.currentTimeMillis() - startingTime < samplePeriodMillis) {
			if (magnetSwitch.get() != magnetSwitched) {
				if(detectedLastTime == false){
					magnetCounter++;
					detectedLastTime = true;
				}
			} else {
				detectedLastTime = false;
			}
		}
		
		return magnetCounter / (float)(samplePeriodMillis / (float)1000);
	}
	
	public void robotInit() {
		robot = new RobotDrive(1, 0);
		joystick = new Joystick(1);
		magnetSwitch = new DigitalInput(4);
		magnetSwitched = false;

		encoders = new Encoder[2];
		encoders[0] = new Encoder(0, 1);
		encoders[0].reset();
		encoders[1] = new Encoder(2, 3);
		encoders[1].reset();
		
		addButton = new Button(joystick, 1);
		subtractButton = new Button(joystick, 2);
		
		sonars = new Sonar[]{new Sonar(2), new Sonar(3)};
		
		compressor = new Compressor();
		compressor.start();
		
		solen = new DoubleSolenoid(0,1);
	}
	
	public void testInit() {
		System.out.println("Init test");
		imu = new IMU();
	}
	
	public void testPeriodic() {
		LiveWindow.run();
	}

	public void teleopInit() {
		limitedJoyL = 0.1;
		limitedJoyR = 0.1;
		LiveWindow.run();
		solen.startLiveWindowMode();
	}
	
	public void teleopPeriodic() {
		if(addButton.readButton()) {
			//rampCoefficient += 0.01;
			//System.out.println("Button: " + rampCoefficient);
			System.out.println("Fired forwards");
			forward(solen);
		}
		if(subtractButton.readButton()) {
			//rampCoefficient -= 0.01;
			//System.out.println("Button: " + rampCoefficient);
			System.out.println("Fired reverse");
			reverse(solen);
		}
		
		//using exponential moving averages for joystick limiting
		double desiredL = joystick.getRawAxis(1);
		double desiredR = joystick.getRawAxis(5);
		double errorL = desiredL - limitedJoyL;
		double errorR = desiredR - limitedJoyR;
		limitedJoyL += errorL * rampCoefficient;
		limitedJoyR += errorR * rampCoefficient;
		//robot.tankDrive(DAMPENING_COEFFICIENT*limitedJoyL, DAMPENING_COEFFICIENT*limitedJoyR, true);
	}
	
	public void disableInit() {
		off(compressor, solen);
	}
}
