package org.tigerdynasty;

import edu.wpi.first.wpilibj.camera.*;
import edu.wpi.first.wpilibj.image.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CameraManager {

    private static final CameraManager instance = new CameraManager();

    private CameraManager() {
    }

    public static CameraManager getInstance() {
        return instance;
    }
    AxisCamera c = AxisCamera.getInstance();
    public ParticleAnalysisReport[] getPARs() {
        ParticleAnalysisReport[] pars = new ParticleAnalysisReport[0];
        try {
	    ColorImage ci = c.getImage();
	    
	    BinaryImage bi = ci.thresholdRGB(90,110, 230, 255,200, 240);
            pars = bi.getOrderedParticleAnalysisReports();
	    ci.free();
	    bi.free();
	    SmartDashboard.putNumber("LUMINANCE", SmartDashboard.getNumber("Luminance"));
        } catch (AxisCameraException ex) {
	    System.out.println(ex.getClass() + " at " + System.currentTimeMillis() + ":" + ex.getMessage());
        } catch (NIVisionException ex) {	    
	    System.out.println(ex.getClass() + " at " + System.currentTimeMillis() + ":" + ex.getMessage());
        }
	return pars;

    }
    public double getCoveredFraction() {
        ParticleAnalysisReport[] pars = new ParticleAnalysisReport[0];
        try {
	    ColorImage ci = c.getImage();
	    
	    BinaryImage bi = ci.thresholdRGB(240, 255, 0, 100,0, 100);
            pars = bi.getOrderedParticleAnalysisReports();
	    ci.free();
	    bi.free();
	    SmartDashboard.putNumber("LUMINANCE", SmartDashboard.getNumber("Luminance"));
        } catch (AxisCameraException ex) {
	    System.out.println(ex.getClass() + " at " + System.currentTimeMillis() + ":" + ex.getMessage());
        } catch (NIVisionException ex) {	    
	    System.out.println(ex.getClass() + " at " + System.currentTimeMillis() + ":" + ex.getMessage());
        }
        double covered = 0;
        for (int i = 0; i < pars.length; i++) {
            covered += pars[i].particleToImagePercent;
        }
        return covered;

    }
    public boolean getHorizontalSensorShown(){
	return false;//todo implement
    }
    public BinaryImage getLitPixels(){
	try {	
	    ColorImage ci = c.getImage();
	    
	    BinaryImage bi = ci.thresholdRGB(0,100,240, 255,0, 100);
	    ci.free();
	    return bi;
	} catch (AxisCameraException ex) {
	    ex.printStackTrace();
	} catch (NIVisionException ex) {
	    ex.printStackTrace();
	}
	return null;
    }
}
