package org.usfirst.frc.team5806.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;


public class Robot extends IterativeRobot {
	RobotDrive robot;
	Joystick joystick;
	
	Encoder encoder;
    
    public void robotInit() {
         robot = new RobotDrive(0, 1);
         joystick = new Joystick(1);
         encoder = new Encoder(0, 1);
    }
    
    public void testPeriodic() {
        LiveWindow.run();
        robot.tankDrive(joystick.getRawAxis(1), joystick.getRawAxis(5));
        System.out.println("Distance: " + encoder.getDistance());
   }
    
    public void autonomousInit() {}
    public void autonomousPeriodic() {}
    public void teleopInit() {}
    public void teleopPeriodic() {}
}
