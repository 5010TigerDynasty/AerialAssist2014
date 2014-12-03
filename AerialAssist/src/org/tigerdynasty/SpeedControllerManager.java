/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tigerdynasty;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.PIDSource;
/**
 *
 * @author HP Customer
 */
public class SpeedControllerManager implements SpeedController, PIDOutput {
    private SpeedController num1,num2,num3;
    public SpeedControllerManager(SpeedController num1, SpeedController num2, SpeedController num3){
	this.num1 = num1;
	this.num2 = num2;
	this.num3 = num3;
    }
    public SpeedControllerManager(SpeedController num1,SpeedController num2){
	this(num1,num1,num2);
    }

    public double get() {
	return num1.get();
    }

    public void set(double d, byte b) {
	set(d);
    }

    public void set(double d) {
	num1.set(d);
	num2.set(d);
	num3.set(d);
    }

    public void disable() {
	num1.disable();
	num2.disable();
	num3.disable();
    }

    public void pidWrite(double d) {
	num1.pidWrite(d);
	num2.pidWrite(d);
	num3.pidWrite(d);
    }

}