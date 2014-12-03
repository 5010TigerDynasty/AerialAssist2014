/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tigerdynasty;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;

/**
 *
 * @author HP Customer
 */
public class LauncherManager {

    private static final LauncherManager instance = new LauncherManager();

    public static LauncherManager getInstance() {
	return instance;
    }

    private LauncherManager() {
    }
    private RobotMapManager robot = RobotMapManager.getInstance();
    private Talon winch = robot.winch;
//    private Relay launcher = robot.launcher;
    private Solenoid pistonRetract = robot.pistonRetract;
    private Solenoid pistonPunch = robot.pistonPunch;
    private DigitalInput lowPressure = robot.lowPressure;
    private DigitalInput winchDone = robot.winchDone;
    private InputManager input = InputManager.getInstance();
    private LogicManager logic = LogicManager.getInstance();
    private boolean hasBall = true;//should start as true
    private boolean intakeHasHadBall = true;//should start as true

    public void update() {
	robot.board.putNumber("WINCH = ", winch.getSpeed());
	robot.board.putNumber("WINCH_STATE = ", state);
	robot.board.putBoolean("PISTON_RETRACT = ", pistonRetract.get());
	robot.board.putBoolean("PISTON_PUNCH = ", pistonPunch.get());
	robot.board.putNumber("WINCH POWER", winch.get());
	robot.board.putNumber("INTAKE", robot.spinnyIntake.get());
	robot.board.putBoolean("PRESSURE SENSOR", robot.lowPressure.get());
	robot.board.putBoolean("WINCH SENSOR", robot.winchDone.get());
	robot.board.putBoolean("BALL SENSOR", robot.ballIn.get());
	robot.board.putBoolean("ARM SENSOR", robot.ballEntering.get());
	robot.board.putBoolean("ARM SENSOR", robot.ballEntering.get());
	if (!lowPressure.get()) {
	    robot.pressurizer.set(Relay.Value.kForward);
	} else {
	    robot.pressurizer.set(Relay.Value.kOff);
	}
	if (state == MOVINGDOWN && winchDone.get()) {
	    System.out.println("Stopping winch");
	    state = CLOSETODOWN;
	    lastSensorTime = System.currentTimeMillis();
	}
	if (state == CLOSETODOWN) {
	    winch.set(0);
	}
	if (state == MOVINGUP && System.currentTimeMillis() > time + cooldown) {
	    System.out.println("RETRACTING!");
	    pistonPunch.set(false);
	    pistonRetract.set(true);
	    state = UP;
	}
	if (state == UP && System.currentTimeMillis() > time + cooldown + 200) {
	    pullWinch();

	}
	if (intakeOnPullDown) {
	    if (System.currentTimeMillis() - lastIntakeDownTime > armDownRunTime) {
		System.out.println("Stopping wheels down at : " + (System.currentTimeMillis() - lastIntakeDownTime));
		intakeOnPullDown = false;
		stopTake();
	    } else if (superLaunching && System.currentTimeMillis() - lastIntakeDownTime > armDownRunTime - 700) {

		releaseWinch();
		if (superLaunching && System.currentTimeMillis() - lastIntakeDownTime > 800) {
		    armThingUp();
		    superLaunching = false;
		}
	    }
	}
    }
    //jake sucks a lot.  Doesn't even know how to code.
    private int state = DOWN, armState = UP, spinState;
    public final int ON = 1, OFF = 0;
    private static final int DOWN = 0, UP = 1, MOVINGDOWN = 2, MOVINGUP = 3, CLOSETODOWN = 4;
    private long lastSensorTime = System.currentTimeMillis();
    private long lastTime = System.currentTimeMillis();
    private static final long cooldown = 400;
    private static long time = 0;
    private static final long armDownRunTime = 1000;
    private long lastIntakeDownTime = 0;
    boolean intakeOnPullDown = false, superLaunching = false;

    public void pullWinch() {
	System.out.println("PULLING!");
	state = MOVINGDOWN;
	winch.set(1);
    }

    public void stopWinch() {
	if (state != MOVINGDOWN) {
	    return;
	}
	winch.set(0);
	state = DOWN;
	System.out.println("STOPPING!");
    }

    public void releaseWinch() {
//	if (state != DOWN) {
//	    return;
//	}
	System.out.println("PUNCHING");
	pistonPunch.set(true);
	pistonRetract.set(false);
//	launcher.set(Relay.Value.kForward);
	lastTime = System.currentTimeMillis();
	state = MOVINGUP;
	time = System.currentTimeMillis();
    }

    public void armThingyToggle() {
	if (armState == DOWN) {
	    armThingUp();
	} else if (armState == UP) {
	    armThingDown();
	}
    }

    public void armThingDown() {
	robot.armThingUp.set(false);
	robot.armThingDown.set(true);
	armState = DOWN;
	System.out.println("Pulling arm down");
	if (true) {//if we make it spin the motors whenever we turn it down
	    intakeOnPullDown = true;
	    lastIntakeDownTime = System.currentTimeMillis();
	    spinState = ON;
	    robot.spinnyIntake.set(.8);
	}
    }

    public void armThingUp() {
	robot.armThingUp.set(true);
	robot.armThingDown.set(false);
	armState = UP;
    }

    public void toggleOutTake() {
	if (spinState == ON) {
	    stopTake();
	} else if (spinState == OFF) {
	    outTake();
	}
    }

    public void outTake() {

	intakeOnPullDown = false;
	spinState = ON;
	robot.spinnyIntake.set(-1);
    }

    public void stopTake() {
	spinState = OFF;
	robot.spinnyIntake.set(0);
    }

    public void inTake() {
	intakeOnPullDown = false;
	spinState = ON;
	robot.spinnyIntake.set(1);
    }

    public void toggleInTake() {
	if (spinState == ON) {
	    stopTake();
	} else if (spinState == OFF) {
	    inTake();
	}
    }

    public void superLaunch() {
	superLaunching = true;
	armThingDown();
    }
}
