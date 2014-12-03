/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tigerdynasty;

/**
 *
 * @author HP Customer
 */
//right now useless
public class MathManager {
    public static double exp(double a, int b){
	//more reliable
	if(b>0){
	    return a*exp(b-1);
	}else if(b<0){
	    return exp(b+1)/a;
	}else{
	    return 1;
	}
    }
    public static double exp( double x){
	double result = 1;
	double xToThei = 1;
	double iFactorial = 1;
	for(int i = 1;i<200;i++){
	    iFactorial*=i;
	    xToThei *=x;
	    result+=xToThei/iFactorial;
	}
	return result;
    }
    public static double pow(double a, double b){
	return exp(log(a)*b);
    }
    public static double log(double x){
	if(x<=0)return Double.NaN;
	if(x>=2){
	    return 1+log(x/Math.E);
	}
	if(x<1/2.0){
	    return -1+log(x*Math.E);
	}
	double sign = 1;
	double xminus1 = x -1;
	double xminus1tothen = 1;
	double result = 0;
	for(int i = 1; i < 200; i++){
	    sign*=-1;
	    xminus1tothen *=xminus1;
	    result -= sign*xminus1tothen/i;
	}
	return result;
    }
}
