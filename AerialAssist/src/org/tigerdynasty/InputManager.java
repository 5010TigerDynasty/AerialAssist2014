package org.tigerdynasty;

import edu.wpi.first.wpilibj.*;

/**
 *
 * Use class for edge detection on buttons For edge detection, update() must be
 * called in a loop
 *
 * @author George
 */
public class InputManager {

    private static final InputManager instance = new InputManager();
    private RobotMapManager map = RobotMapManager.getInstance();
    public boolean joy1IsXbox;
    public boolean joy2IsXbox;
    private boolean[][] buttons = new boolean[4][13];
    private boolean[][] curEvents;
    private Joystick[] joysticks;

    private InputManager() {
	joy1IsXbox = RobotMapManager.getInstance().joy1IsXbox;
	joy2IsXbox = RobotMapManager.getInstance().joy2IsXbox;
    }

    public static InputManager getInstance() {
	return instance;
    }

    public void update() {
	Joystick[] joysticks = {map.joy1, map.joy2, map.joy3, map.joy4};
	this.joysticks = joysticks;
	boolean[][] newButtons = new boolean[4][13];
	curEvents = new boolean[4][13];
	for (int i = 0; i < 4; i++) {
	    for (int j = 0; j < 12; j++) {
		newButtons[i][j] = joysticks[i].getRawButton(j + 1);
		if (newButtons[i][j] != buttons[i][j]) {
		    curEvents[i][j] = true;
		}
	    }
	}
	buttons = newButtons;

    }

    public boolean getRawButton(int button, int joystick) {
	return joysticks[joystick - 1].getRawButton(button);
    }

    public double getRawAxis(int joystick, int axis) {
	return joysticks[joystick - 1].
		getRawAxis(axis);
    }

    public double getForwardAxis(int joystick) {
	if (joystick == 1 && joy1IsXbox) {
	    double rawAxis =  -getRawAxis(1, 2);//forward left stick, change if needed
	    return rawAxis*rawAxis*rawAxis;
	}
	double rawAxis =-getRawAxis(joystick, 2);
	return rawAxis * rawAxis * rawAxis;
    }

    public double getRightAxis(int joystick) {
	double rawAxis;
	if (joystick == 1 && joy1IsXbox) {
	    rawAxis =  getRawAxis(1, 4);
	}else{
	    rawAxis =  getRawAxis(1, 1);
	}
	return rawAxis * rawAxis * rawAxis;
    }

    public boolean isButtonPressedEvent(int button, int joystick) {
	return curEvents[joystick - 1][button - 1] ? (buttons[joystick - 1][button - 1]) : false;
    }

    public int getButtonPressedEvent(int button) {
	for (int i = 1; i < 4; i++) {
	    if (isButtonPressedEvent(button, i)) {
		return i;
	    }
	}
	return -1;
    }

    public boolean isButtonReleasedEvent(int button, int joystick) {
	return curEvents[joystick - 1][button - 1] ? (!buttons[joystick - 1][button - 1]) : false;
    }

    public int getButtonReleasedEvent(int button) {
	for (int i = 1; i < 4; i++) {
	    if (isButtonReleasedEvent(button, i)) {
		return i;
	    }
	}
	return -1;
    }
}
