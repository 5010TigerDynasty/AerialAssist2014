/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.tigerdynasty;


import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
//"We need to delete all these stupid comments" -KyleManager
public class RobotManager extends SimpleRobot {
    
    private RobotMapManager robot = RobotMapManager.getInstance();
    private  LogicManager logic = LogicManager.getInstance();
    public RobotManager(){
	super();
	SmartDashboard.putNumber("Pid P:",.002);
	SmartDashboard.putNumber("Pid I:",0);
	SmartDashboard.putNumber("Pid D:",0);
	SmartDashboard.putNumber("Luminance",200);
    }
    /**
     * This function is called once each time the robot enters autonomous mode.
     */
    public void autonomous() {
	logic.autonHasFired = false;
	/*//Test new code
	  
	 
	/*///Dead reckoning
	DriveTrainManager.getInstance().setPIDEnable(false);
	LauncherManager launch = LauncherManager.getInstance();
	LogicManager logic = LogicManager.getInstance();
//	launch.armThingDown();			    //need to do it afterwards--possibly temporary
	logic.autonStartTime = System.currentTimeMillis();
	System.out.println("Auton start");  
	while(isEnabled() && isAutonomous()){
	    try{
		
		Thread.sleep(5);
	    }catch(InterruptedException ie){
		//do nothing
	    }
	    
//	    if(true)continue; //this is for no-moving, goalie zone auton
	    LogicManager.getInstance().updateAuton();
	    LauncherManager.getInstance().update(); //Why is this necessary
	}
	    //*/
    }

    /**
     * This function is called once each time the robot enters operator control.
     */
    public void operatorControl() {
//	while(isEnabled() && isOperatorControl())
//	{
//	    System.out.println("WTF");
//    RobotMapManager.getInstance().leftMotor1.set(1);
//    RobotMapManager.getInstance().leftMotor2.set(1);
//    RobotMapManager.getInstance().leftMotor3.set(1);
//    RobotMapManager.getInstance().rightMotor1.set(1);
//    RobotMapManager.getInstance().rightMotor2.set(1);
//    RobotMapManager.getInstance().rightMotor3.set(1);
//	}
	robot.pistonRetract.set(true);
	System.out.println(isEnabled());
	DriveTrainManager.getInstance().setPIDEnable(false);
	System.out.println("Starting teleop" + isEnabled() + isOperatorControl());
	while(isEnabled() && isOperatorControl())
	{
	    try{
		InputManager.getInstance().update();
		LogicManager.getInstance().update();
		LauncherManager.getInstance().update();
//		double cameraCovered = CameraManager.getInstance().getCoveredFraction();
//		SmartDashboard.putNumber("Camera value:",cameraCovered);
		Thread.sleep(5);
	    }catch(Exception ie){
		ie.printStackTrace();
	    }
	}
	System.out.println("Finished teleop");
    }
    
    /**
     * This function is called once each time the robot enters test mode.
     */
    public void test() {
    
    }
}
