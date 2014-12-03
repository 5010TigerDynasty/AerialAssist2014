/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tigerdynasty;

import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author HP Customer
 */
public class CameraDashboardManager {
    
    static int max=0;
    public static void displayCameraThings(ParticleAnalysisReport[] pars){
	max = Math.max(max,pars.length);
	for(int i = 0; i < pars.length; i++){
	    ParticleAnalysisReport cur = pars[i];
	    SmartDashboard.putString("Par" + i,formatString(cur.boundingRectLeft,cur.boundingRectTop,cur.boundingRectWidth,cur.boundingRectHeight,cur.particleArea));
	}
	for(int i = pars.length; i < max; i++){
	    SmartDashboard.putString("Par" + i,"");
	}
    }
    private static String formatString(int left, int top, int xsize, int ysize, double area){
	return "Left:" + left + " Top:" + top + " xsize:" + xsize + " ysize" + ysize + " area:" + area;
    }
}
