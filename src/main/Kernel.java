/**
 * Kernel.java
 * Radial Basis Function as the VSM Kernel
 * @author Mialy Andrianarivony
 */
package main;

import java.util.*;
import java.io.*;

/**
 * Radial Basis Function Kernel
 */
public class Kernel {
	double gamma;
	double C;
	
	/**
	 * RBF Kernel
	 * Default parameter for gamma = 1/72
	 */
	Kernel(double gamma){
		if(gamma > 0 && gamma < 1) {
			this.setGamma(gamma);
		} else {
			this.setGamma(1.0 / 72);
		}
		
	}
	
	
	/**
	 * Compute the output of the formula below:
	 * K(x, y) = exp(-gamma * ||x - y||^2)
	 * ||x - y||^2 find the Euclidian distance squared of between vector x and y
	 * @return (double) Math.exp(-this.gamma*sum)
	 */
	double kernelFunction(List<Integer> x1, List<Integer> x2) {
		// Formula :: K(x, y) = exp(-gamma * ||x - y||^2)
		
		float num_features = 0;
		if (x1.size() == x2.size()) {
			num_features = x1.size();
		}
		
		double sum = 0, euclidian_dist = 0;
		for (int i = 0; i < x1.size(); i++) { 
			double dist = x1.get(i) - x2.get(i);
			sum += Math.pow(dist, 2);
//			euclidian_dist += (x1.get(i)- x2.get(i)) * (x1.get(i)- x2.get(i));
		}
		return Math.exp(-this.gamma*sum);
	}

	
	public double getGamma() {
		return gamma;
	}

	public void setGamma(double gamma) {
		this.gamma = gamma;
	}

	
	
}
