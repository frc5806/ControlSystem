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
	
	// HAS TO BE A NEGATIVE NUMBER SO IT GOES THE RIGHT WAY
	private static final double DAMPENING_COEFFICIENT = -0.75;
    
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
    
    public void teleopPeriodic() {
    	// For Xbox Controller
    	robot.tankDrive(DAMPENING_COEFFICIENT*joystick.getRawAxis(1), DAMPENING_COEFFICIENT*joystick.getRawAxis(5), true);
    }
}
