/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tigerdynasty;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.*;

/**
 *
 * @author HP Customer
 */
public class RobotMapManager {

    private static final RobotMapManager instance = new RobotMapManager();
    
    public final boolean joy1IsXbox = true;
    public final boolean joy2IsXbox = false;
    
    public final AnalogChannel channel = new AnalogChannel(1);
    public final Gyro gyro = new Gyro(channel);
    public final Relay pressurizer = new Relay(1);
//  public final Relay launcher = null;
    public final Solenoid pistonRetract = new Solenoid(8); //had to switch these
    public final Solenoid pistonPunch = new Solenoid(7);
    public final Solenoid armThingDown = new Solenoid(3);
    //This solenoid was #1, but the pin broke
    public final Solenoid armThingUp = new Solenoid(2);
    //^^^^^^^^//
    
    public final Talon spinnyIntake = new Talon(10);
    public final DigitalInput lowPressure = new DigitalInput(1);
    public final DigitalInput ballEntering = new DigitalInput(3);
    public final DigitalInput ballIn = new DigitalInput(4);
    public final DigitalInput winchDone = new DigitalInput(2);
    public final Victor leftMotor1 = new Victor(1);
    public final Victor rightMotor1 = new Victor(4);
    public final Victor leftMotor2 = new Victor(9);
    public final Victor rightMotor2 = new Victor(5);
    public final Victor leftMotor3 = new Victor(3);
    public final Victor rightMotor3 = new Victor(6);
    public final Talon winch = new Talon(8);
    public final Joystick joy1 = new Joystick(1);
    public final Joystick joy2 = new Joystick(2);
    public final Joystick joy3 = new Joystick(3);
    public final Joystick joy4 = new Joystick(4);
    public SmartDashboard board = new SmartDashboard();

    public static RobotMapManager getInstance() {
	return instance;
    }

    private RobotMapManager() {
    }
}
