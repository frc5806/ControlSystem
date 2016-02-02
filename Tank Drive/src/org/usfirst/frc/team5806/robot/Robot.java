package org.usfirst.frc.team5806.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;


public class Robot extends IterativeRobot {
	RobotDrive robot;
	Joystick joystick;
	Encoder[] encoders;
	private static final double speedRampIncrement = 0.05;
	
	// HAS TO BE A NEGATIVE NUMBER SO IT GOES THE RIGHT WAY
	private static final double DAMPENING_COEFFICIENT = -0.75;
	private static final double MOVE_THRESHOLD = 0.05;
    
    public void robotInit() {
         robot = new RobotDrive(1, 0);
         joystick = new Joystick(1);
         
         encoders = new Encoder[2];
         encoders[0] = new Encoder(0, 1);
         encoders[0].reset();
         encoders[1] = new Encoder(2, 3);
         encoders[1].reset();
    }
    
    public void testPeriodic() {
        LiveWindow.run();
        
        System.out.println("encoders: " + encoders[0].getDistance() + "\t" + encoders[1].getDistance());
        
        teleopPeriodic();
   }
    
    public void autonomousInit() {}
    public void autonomousPeriodic() {}
    public void teleopInit() {}
    
    private static double leftStick = 0;
    private static double rightStick = 0;
    private static double lsRemaining = 0;
    private static double rsRemaining = 0;
    private static boolean rampingLeftSpeed = false;
    private static boolean rampingRightSpeed = false;
    
    public void teleopPeriodic() {
    	// For Xbox Controller
    	if (rampingLeftSpeed) {
    		leftStick += speedRampIncrement;
    		lsRemaining -= speedRampIncrement;
    	} else {
    		double incoming = joystick.getRawAxis(1);
    		if (incoming - leftStick > MOVE_THRESHOLD || incoming - leftStick < -MOVE_THRESHOLD) {
    			//joystick input is enough to warrant changing the motor
    			lsRemaining = incoming;
    			rampingLeftSpeed = true;
    		}
    	}
    	if (rampingRightSpeed) {
    		rightStick += speedRampIncrement;
    		rsRemaining -= speedRampIncrement;
    	} else {
    		double incoming = joystick.getRawAxis(5);
    		if (incoming - rightStick > MOVE_THRESHOLD || incoming - rightStick < -MOVE_THRESHOLD) {
    			//joystick input is enough to warrant changing the motor
    			rsRemaining = incoming;
    			rampingRightSpeed = true;
    		}
    	}
    	if (lsRemaining <= 0) {
    		//have ramped all the way up to the last inputted joy value
    		rampingLeftSpeed = false;
    	}
    	if (rsRemaining <= 0) {
    		//have ramped all the way up to the last inputted joy value
    		rampingRightSpeed = false;
    	}
    	
    	robot.tankDrive(DAMPENING_COEFFICIENT*leftStick, DAMPENING_COEFFICIENT*rightStick, true);
    }
}
