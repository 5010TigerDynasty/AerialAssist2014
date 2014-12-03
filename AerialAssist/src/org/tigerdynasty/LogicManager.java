/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tigerdynasty;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author HP Customer
 */
public class LogicManager {

    private static final LogicManager instance = new LogicManager();

    public static LogicManager getInstance() {
	return instance;
    }

    private LogicManager() {
    }
    private DriveTrainManager driveTrain = DriveTrainManager.getInstance();
    private InputManager input = InputManager.getInstance();
    private RobotMapManager robot = RobotMapManager.getInstance();
//    private CameraManager cam = CameraManager.getInstance();
    private LauncherManager launch = LauncherManager.getInstance();
    public final int XBOX = 1, JOY = 0;
    public final int controllerState1 = XBOX, controllerState2 = JOY;
    private int mode = 1;
    public long autonStartTime;
    boolean autonHasFired = false, autonHasLowered = false;

    public void updateAuton() {
//	CameraDashboardManager.displayCameraThings(CameraManager.getInstance().getPARs());
	//driveTrain.setPID(SmartDashboard.getNumber("Pid P:"), SmartDashboard.getNumber("Pid I:"), SmartDashboard.getNumber("Pid D:"));
//        SmartDashboard.putNumber("Camera percentage: ", cam.getCoveredFraction());

	driveTrain.setPIDEnable(false);
	long forwardTime = 2000;

	    if (System.currentTimeMillis() < autonStartTime + forwardTime) {

		driveTrain.setForward(.5);
	    } else {
		driveTrain.setForward(0);
	    }



//	if (true) {//no ball auton
//	    return;
//	}








//	if (System.currentTimeMillis() - autonStartTime > forwardTime + 1000 && !autonHasFired && !autonHasLowered) {
//	    launch.armThingDown();
//	    autonHasLowered = true;
//	}
	if (System.currentTimeMillis() - autonStartTime > forwardTime + 2000 && !autonHasFired) {
	    autonHasFired = true;
	    System.out.println("Shooting");
	    launch.superLaunch();
	}
//        if(cam.getCoveredFraction() > .30)
//        {
//            launch.releaseWinch();
//        }
    }
    private int armDownButton, inTakeButton, outTakeButton, winchButton, launchButton, superLaunchButton, permissionButton;
    private int armToggleButton2, inTakeButton2, outTakeButton2, winchButton2, launchButton2, superLaunchButton2;

    public void update() {
	if (input.getButtonPressedEvent(8) == 1) {
	    robot.gyro.reset();
	}
	SmartDashboard.putNumber("Gyro angle:", robot.gyro.getAngle());
	SmartDashboard.putNumber("Mode = ", mode);
	driveTrain.setPID(SmartDashboard.getNumber("Pid P:"), SmartDashboard.getNumber("Pid I:"), SmartDashboard.getNumber("Pid D:"));
	SmartDashboard.putNumber("PID P", SmartDashboard.getNumber("Pid P:"));
	SmartDashboard.putNumber("PID I", SmartDashboard.getNumber("Pid I:"));
	SmartDashboard.putNumber("PID D", SmartDashboard.getNumber("Pid D:"));
//	SmartDashboard.putNumber("Camera Pixels", CameraInterpretingManager.totalArea(CameraManager.getInstance().getPARs()));
	if (input.getButtonPressedEvent(7) == 1) {
	    mode++;
	    if (mode != 2) {
		driveTrain.setPIDEnable(false);
	    } else {
		driveTrain.setPIDEnable(true);
	    }
	}
	if (mode > 2) {
	    mode = 1;
	}
	switch (mode) {
	    case 1:
		updateNonPID();
		break;
	    case 2:
		driveTrain.setPIDEnable(true);
		driveTrain.setSetpoint(robot.gyro.getAngle());
		updatePID();
		break;
	}
	updateLaunchLogic();
    }

    private void updateLaunchLogic() {

	int armUpButton;
	if (input.joy1IsXbox) {
	    armDownButton = 3;
	    armUpButton = 4;
	    inTakeButton = 1;
	    outTakeButton = 2;
	    winchButton = 7;
	    launchButton = 6;
	    superLaunchButton = 5;
	    permissionButton = 8;
	} else {
	    armDownButton = 3;
	    armUpButton = 4;
	    inTakeButton = 5;
	    outTakeButton = 6;
	    winchButton = 2;
	    launchButton = 1;
	    superLaunchButton = 12;
	}
	if (input.joy2IsXbox) {
	    armToggleButton2 = 3;
	    inTakeButton2 = 1;
	    outTakeButton2 = 2;
	    winchButton2 = 5;
	    launchButton2 = 6;

	} else {
	    armToggleButton2 = 3;
	    inTakeButton2 = 5;
	    outTakeButton2 = 6;
	    winchButton2 = 2;
	    launchButton2 = 1;
	    superLaunchButton2 = 7;

	}
	if (input.getButtonPressedEvent(winchButton) == 1 || input.getButtonPressedEvent(winchButton2) == 2) {
	    launch.pullWinch();
	} else if (input.getButtonReleasedEvent(winchButton) == 1 || (input.getButtonReleasedEvent(winchButton2) == 2)) {
	    launch.stopWinch();
	}
	if (input.getButtonPressedEvent(launchButton) == 1 || (input.getButtonPressedEvent(launchButton2) == 2)) {

	    launch.releaseWinch();
	}
//	if (input.getButtonPressedEvent(armDownButton) == 1 || input.getButtonPressedEvent(armToggleButton2) == 2) {
//	    launch.armThingyToggle();
//	}
	if (input.getButtonPressedEvent(armUpButton) == 1) {
	    launch.armThingyToggle();
	}
	if (input.getButtonPressedEvent(inTakeButton) == 1 || input.getButtonPressedEvent(inTakeButton2) == 2) {
	    launch.toggleInTake();
	} else if (input.getButtonPressedEvent(outTakeButton) == 1 || input.getButtonPressedEvent(outTakeButton2) == 2) {
	    launch.toggleOutTake();
	}
	if (input.getButtonPressedEvent(superLaunchButton) == 1 || input.getButtonPressedEvent(superLaunchButton2) == 2) {
	    launch.superLaunch();

	}
    }

    public void updatePID() {
	double forward = input.getForwardAxis(1);
	double right = input.getRightAxis(1);

	double deadZone = .2;
	if (Math.abs(right) < deadZone && Math.abs(forward) > deadZone) {
	    if (!driveTrain.isPIDing()) {
		driveTrain.setPIDEnable(true);
		driveTrain.setSetpoint(robot.gyro.getAngle());
	    }
	    driveTrain.setForward(forward);
	    return;
	} else if (Math.abs(right)
		< deadZone) {
	    driveTrain.setPIDEnable(false);
	    driveTrain.setLeftNormal(0);
	    driveTrain.setRightNormal(0);
	    return;
	} else {
	    driveTrain.setPIDEnable(false);
	}
	updateNonPID();
    }

    public void updateNonPID() {
	//axis 3 
	//note, this is called by PID controller code
	double forward = input.getForwardAxis(1);
	double right = input.getRightAxis(1);
	double rightM, leftM;
	if (right > 0) {
	    right *= 4.0 / 3;
	}
	rightM = forward + right;
	leftM = forward - right;
	SmartDashboard.putNumber("forward", forward);
	SmartDashboard.putNumber("right", right);
	driveTrain.setPIDEnable(false);
	driveTrain.setLeftNormal(leftM);
	driveTrain.setRightNormal(rightM);
    }
}
