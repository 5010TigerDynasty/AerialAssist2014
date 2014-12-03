/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tigerdynasty;

import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;

/**
 *
 * @author HP Customer
 */
public class CameraInterpretingManager {
    public static int getDirection(ParticleAnalysisReport[] pars){
//1 is on right side, 0 is none, -1 is on left side
	
	double area = totalArea(pars);
	if(area<500)return 0;
	double centerX = averageX(pars);
	double leftAvg = leftAverage(pars);
	double rightAvg = rightAverage(pars);
	if(Math.abs(leftAvg - centerX)<Math.abs(rightAvg - centerX)){
	    return -1;
	}
	return 1;
	
    }
    private static int maxStuff(ParticleAnalysisReport[] pars){
	return Math.min(5,pars.length);
    }
    public static double totalArea(ParticleAnalysisReport[] pars){
	double totalArea = 0;
	for(int i = 0; i < maxStuff(pars); i++){
	    totalArea+=pars[i].particleArea;
	}
	
	return totalArea;
    }
    private static double leftAverage(ParticleAnalysisReport[] pars){
	
	double totalX = 0, totalArea = 0;
	for(int i = 0; i < maxStuff(pars); i++){
	    totalX+=pars[i].boundingRectLeft*pars[i].particleArea;
	    totalArea+=pars[i].particleArea;
	}
	
	return totalX/totalArea;
    
    } private static double rightAverage(ParticleAnalysisReport[] pars){
	
	double totalX = 0, totalArea = 0;
	for(int i = 0; i < maxStuff(pars); i++){
	    totalX+=(pars[i].boundingRectLeft + pars[i].boundingRectWidth)*pars[i].particleArea;
	    totalArea+=pars[i].particleArea;
	}
	
	return totalX/totalArea;
    
    }
    private static double averageX(ParticleAnalysisReport[] pars){
	double totalX = 0, totalArea = 0;
	for(int i = 0; i < maxStuff(pars); i++){
	    totalX+=pars[i].center_mass_x*pars[i].particleArea;
	    totalArea+=pars[i].particleArea;
	}
	
	return totalX/totalArea;
    }
    private static double averageY(ParticleAnalysisReport[] pars){
	double totalX = 0, totalArea = 0;
	for(int i = 0; i < 5; i++){
	    totalX+=pars[i].center_mass_y*pars[i].particleArea;
	    totalArea+=pars[i].particleArea;
	}
	
	return totalX/totalArea;
    }
}
