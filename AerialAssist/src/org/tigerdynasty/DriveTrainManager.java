/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tigerdynasty;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author HP Customer
 */
public class DriveTrainManager implements PIDOutput{
    
    private static final DriveTrainManager instance = new DriveTrainManager();
    
    public static DriveTrainManager getInstance()
    {
	return instance;
    }
    private RobotMapManager robot = RobotMapManager.getInstance();
    private SpeedController leftMotors = new SpeedControllerManager(robot.leftMotor1,robot.leftMotor2,robot.leftMotor3);
    private SpeedController rightMotors = new SpeedControllerManager(robot.rightMotor1,robot.rightMotor2,robot.rightMotor3);
    private PIDController pid = new PIDController(.1,0,0,robot.gyro,this);
    private boolean isPIDing = false;
    private double forward = 0;
    private DriveTrainManager(){
	robot.gyro.setPIDSourceParameter(PIDSource.PIDSourceParameter.kAngle);
	pid.setOutputRange(-1, 1);
	pid.enable();
    }
    public void setForward(double power){
	forward = power;
	if(!isPIDing){
	    setLeftAuton(power);setRightNormal(power);//change later
	}
    }
    public void setPID(double P, double I, double D){
	pid.setPID(P, I, D);
    }
    public void setSetpoint(double point){
	pid.setSetpoint(point);
    }
    public void resetGyro(){
	double error = pid.getError();
	robot.gyro.reset();
	pid.setSetpoint(-error);
    }
    public void setPIDEnable(boolean on){
        //It seems like the PID controller doesn't listen to pid.enable/disable
        //pidWrite responds to isPIDing, though
	if(on){
	    //pid.enable();
	    isPIDing = true;
	}else{
	    //pid.disable();
	    isPIDing = false;
	}
    
    }
    public void setLeftAuton(double power){
	
	leftMotors.set(-power * 0.65);//just because something got horribly messed up somewhere
	RobotMapManager.getInstance().board.putNumber("Left power:", leftMotors.get());
    }
    public void setLeftNormal(double power){
	
	leftMotors.set(-power * 0.97);//just because something got horribly messed up somewhere
	RobotMapManager.getInstance().board.putNumber("Left power:", leftMotors.get());
    }
    public void setRightNormal(double power){
	rightMotors.set(power);
	RobotMapManager.getInstance().board.putNumber("Right power:", rightMotors.get());
    }
    public boolean isPIDing(){
	return isPIDing;
    }
    public void pidWrite(double left) {
	SmartDashboard.putNumber("PID left:",left);
	SmartDashboard.putNumber("PID forward",forward);
	SmartDashboard.putNumber("PID P",pid.getP());
	SmartDashboard.putNumber("PID I",pid.getI());
	SmartDashboard.putNumber("PID D",pid.getD());
	if(SmartDashboard.getNumber("Reset gyro",0)!=0){//just because this should get called constantly
	    robot.gyro.reset();
	    SmartDashboard.putNumber("Reset gyro",0);
	}
	SmartDashboard.putBoolean("PID Enabled:", isPIDing);
	if(!isPIDing)return;
	SmartDashboard.putNumber("PID Value", left);
        setRightNormal(forward - left);
        setLeftNormal(forward + left);
	
    }
}
