/**
 * SMOptim.java
 * @author Mialy Andrianarivony
 */

package main;

import java.util.*;
import java.io.*;


/**
 * Implemented from J. Platt's SMO algorithm
 * src: https://www.microsoft.com/en-us/research/uploads/prod/1998/04/sequential-minimal-optimization.pdf
 * SMO aims to find the Langrage multipliers of each training vector and determine the support vectors
 * 
 */
public class SMOptim {
	double eps; 
	double C;
	double tol;

	List<Vector> datapoints = new ArrayList<Vector>();

	List<List<Integer>> X = new ArrayList<List<Integer>>();
	List<Integer> y = new ArrayList<Integer>();

	
	List<Double> alphas = new ArrayList<Double>();
	List<Double> errors = new ArrayList<Double>();
	

	List<Double> weights = new ArrayList<Double>();

	Kernel kernel;

	int N = 0;
	double b = 0;
	int y2 = 0;
	double a2 = 0; 
	List<Integer> X2 = new ArrayList<Integer>();;
	double E2 = 0;


	/**
	 * Constructor 
	 * Create an optimizer for every SVM 
	 * Initialize the variables required by the SMO 
	 * Variables are taken from the Main 
	 *  -> C = soft margin penalty
	 *  -> tolerance (epsilon) = 
	 * Create a Kernel for the SMO to calculate output
	 *  -> Pass the gamma value to the kernel directly
	 *  -> SMO does not touch or modify gamma
	 *  
	 *  Turn the list of vectors into a list of data X and their value y
	 *  
	 * @param datapoints - list of Vector 
	 * @param C
	 * @param tol
	 * @param gamma
	 */
	SMOptim(List<Vector> datapoints, double C, double tol, double gamma){
		this.datapoints = datapoints;
		this.C = C;
		this.tol = tol;
		this.eps = tol;
		
		for(Vector v : datapoints) {
			this.X.add(v.getX());
			this.y.add(v.getY());
			this.alphas.add(0.0);
			this.errors.add(0.0);
		}

		this.N = datapoints.size();
		this.kernel = new Kernel(gamma);
		
	}
	
	/**
	 * Take a parameter X of type List<Integer> with dimensionality of 64
	 * Output the f(x)/y 
	 * DEFAULT - the threshold (this.b) is not added as it can give inaccurate prediction
	 * @param InX
	 * @return
	 */
	double testOutput(List<Integer> InX) {
		double sum = 0;
		for(int j = 0 ; j < this.N; j++) {
			if (this.alphas.get(j) > this.eps) {
				double kernelOutput = this.kernel.kernelFunction(this.X.get(j), InX);
				sum += this.alphas.get(j) * this.y.get(j) * kernelOutput;
			}
		}
		// sum += this.b; // DO NOT ADD!
		return sum; 
	}

	/**
	 * Gives output of Kernel and current support vectors
	 * @param i1
	 * @return
	 */
	private double output(int i1) {
		double sum = 0;
		for(int j = 0 ; j < this.N; j++) {
			if (this.alphas.get(j) > this.eps) {
				double kernelOutput = this.kernel.kernelFunction(this.X.get(j), this.X.get(i1));
				sum += this.alphas.get(j) * this.y.get(j) * kernelOutput;
			}
		}
		sum -= this.b;
		return sum;
	}
	
	/*
	 * Get error of a data point
	 * If error value is not yet computed and stores,
	 * get error with output() 
	 */
	private double get_error(int i1) {
		if ( (0 < this.alphas.get(i1)) && (this.alphas.get(i1) < this.C) ) {
			return this.errors.get(i1);
		} else {
			return this.output(i1) - this.y.get(i1);
		}
	}
	

	/**
	 * Decided whether to proceed using vector at i1 and i2 
	 * @param i1
	 * @param i2
	 * @return boolean
	 */
	private boolean takeStep(int i1, int i2) {
		if (i1 == i2) {
			return false;
		}	

		double a1 = this.alphas.get(i1);
		int y1 = this.y.get(i1);
		List<Integer> X1 = this.X.get(i1);
		List<Integer> X2 = this.X.get(i2);
		double E1 = this.get_error(i1);

		double s = y1 * this.y2;

		double L, H;
		if(y1 != y2) {
			// equation (12.3)
			L = Math.max(0, this.a2 - a1);
			H = Math.min(this.C, this.C + this.a2 - a1);
		} else /* v1.y == v2.y */ {
			// equation (12.4)
			L = Math.max(0, this.a2 + a1 - this.C);
			H = Math.min(this.C, this.a2 + a1);
		}
		if(L == H) {
			return false;
			// the alpha values are constrained to a single point
		}
			


		double k11 = this.kernel.kernelFunction(X1, X1);
		double k12 = this.kernel.kernelFunction(X1, X2);
		double k22 = this.kernel.kernelFunction(X2, X2);


		double eta = k11 + k22 - (2*k12);

		double a2_new = 0;
		double f1 = 0;
		double f2 = 0;
		double L1 = 0; 
		double H1 = 0; 
		double Lobj = 0; 
		double Hobj = 0;
		
		if (eta > 0) {
			a2_new = this.a2 + this.y2 * (E1 - this.E2) / eta;

			if (a2_new < L) {
				a2_new = L;
			} else if (a2_new > H){
				a2_new = H;
			}
			
		} else {
			f1 = y1 * (E1 + this.b) - a1 * k11 - s * this.a2 * k12;
			f2 = this.y2 * (this.E2 + this.b) - s * a1 * k12 - this.a2 * k22;

			L1 = a1 + s * (this.a2 - L);
			H1 = a1 + s * (this.a2 - H);

			Lobj = L1 * f1 + L * f2 + 0.5 * (Math.pow(L1, 2)) * k11 + 0.5 * (Math.pow(L, 2)) * k22 + s * L * L1 * k12;

			Hobj = H1 * f1 + H * f2 + 0.5 * (Math.pow(H1, 2)) * k11 + 0.5 * (Math.pow(H, 2)) * k22 + s * H * H1 * k12;

			if (Lobj < Hobj - this.eps) {
				a2_new = L;
			} else if (Lobj > Hobj + this.eps) {
				a2_new = H;
			} else {
				a2_new = this.a2;
			}
		}
	
		if ( (Math.abs(a2_new - this.a2)) < (this.eps * (a2_new + this.a2 + this.eps))) {
			return false;
		}

		double a1_new, new_b;
		double delta_b;


		a1_new = a1 + s * (this.a2 - a2_new);

		new_b = this.computeB(E1, a1, a1_new, a2_new, k11, k12, k22, y1);

		delta_b = new_b - this.b;

		this.b = new_b;

		double delta1, delta2;
		delta1 = y1 * (a1_new - a1);
		delta2 = this.y2 * (a2_new - this.a2);

		// Update error cache
		for(int i = 0; i < N ; i++) {
			if ( (this.alphas.get(i) > 0) && (this.alphas.get(i) < this.C) ){
				double newErrorValue = this.errors.get(i);
				newErrorValue += delta1 * this.kernel.kernelFunction(X1, this.X.get(i)) +
						delta2 * this.kernel.kernelFunction(X2, this.X.get(i)) - delta_b;
				this.errors.set(i, newErrorValue);
			}
		}

		this.errors.set(i1, 0.0);
		this.errors.set(i2, 0.0);
		
		this.alphas.set(i1, a1_new);
		this.alphas.set(i2, a2_new);
		
		return true;
	}
	
	
	/**
	 * Calculate the threshold (b) of the SMO 
	 * @return new value of b
	 */
	private double computeB(double E1, double a1, double a1_new, double a2_new, double k11, double k12, double k22,
			int y1) {
		double b1, b2, new_b;
		
		b1 = E1 + y1 * (a1_new - a1) * k11 + this.y2 * (a2_new - this.a2) * k12 + this.b;
	
		b2 = this.E2 + y1 * (a1_new - a1) * k12 + this.y2 * (a2_new - this.a2) * k22 + this.b;
	
		if ((0 < a1_new) && (this.C > a1_new)){
			new_b = b1;
		} else if ((0 < a2_new) && (this.C > a2_new)) {
			new_b = b2;
		} else {
			new_b = (b1 + b2) / 2.0;
		}
		return new_b;
	}
	
	
	/**
	 * Get the index of the vector that has maximize its error and current E2 of i2
	 * @param nonBoundIndices
	 * @return i1 (index)
	 */
	private int secondHeurisitic(List<Integer> nonBoundIndices) {
		double E1, step;
		double max; 
		int i1 = -1;
		if (nonBoundIndices.size() > 1) {
			max = 0;
			for(int index: nonBoundIndices) {
				E1 = this.errors.get(index) - this.y.get(index);
				step = Math.abs(E1 - this.E2);
				if (step > max) {
					max = step;
					i1 = index;
				}
			}
		}
		return i1;
	}



	/**
	 * Examine a vector at index i2 
	 * 
	 * @param i2
	 * @return 0 or 1
	 */
	private int examineExample(int i2) {
		this.y2 = this.y.get(i2);
		this.a2 = this.alphas.get(i2); 
		this.X2 = this.X.get(i2);
		this.E2 = this.get_error(i2);
	
		double r2 = this.E2 * this.y2;
	
		boolean kktCond1 = (r2 < -this.tol) && (this.a2 < this.C);
		boolean kktCond2 = (r2 > this.tol) && (this.a2 > 0);
	
		// continue when the KKT condition is violated
		// IF (R1 < -tolerance && alpha1 < C) || (R1 > tolerance && alpha1 > 0) 
		
		// KKT conditions must be violated to move on.
		if (!(kktCond1 || kktCond2)) {
			return 0;
		}
		// Second Heuristic A
		int i1 = 0;
		List<Integer> nonBoundIndices = this.getNonBoundIndices();
		i1 = this.secondHeurisitic(nonBoundIndices);
		
		if (i1 >= 0 && this.takeStep(i1, i2)) {
			return 1;
		}
		
		
		// Second Heuristic B
		int nonBoundSize = nonBoundIndices.size();
		float rand = (float)Math.random();
		if (nonBoundSize > 0) {
			int randPos = (int) (rand * nonBoundSize);;
			
			for(int u: nonBoundIndices.subList(randPos, nonBoundSize)) {
				if (this.takeStep(u, i2)) { 
					return 1;
				}
			}
			for(int r: nonBoundIndices.subList(0, randPos)) {
				if (this.takeStep(r, i2)) {
					return 1;
				}
			}
			
		}
		
		// Second Heuristic C
		float rand2 = (float)Math.random();
		int randPos2 = (int) (rand2 * nonBoundSize);
		List<Integer> allIndices = new ArrayList<Integer>();
		for(int a = 0; a < N; a++) {
			allIndices.add(a);
		}
		
		for(int u: allIndices.subList(randPos2, N)) {
			if (this.takeStep(u, i2)) { 
				return 1;
				};
		}
		for(int r: allIndices.subList(0, randPos2)) {
			if (this.takeStep(r, i2)) { 
				return 1; 
				}
		}
		return 0;
	}
	
	
	/**
	 * Get examples where alpha is not 0 and not C
	 * These are most likely to violate KKT conditions
	 * this.alphas > 0 && this.alphas < this.C
	 * 
	 * @return list of non bound subset
	 */
	private List<Integer> getNonBoundIndices() {
		List<Integer> nonBoundIndices = new ArrayList<Integer>();
		for (int index = 0; index < this.alphas.size(); index++) {
			if ((this.alphas.get(index) > 0) &&(this.alphas.get(index) < this.C)){
				nonBoundIndices.add(index);
			}
		}
		return nonBoundIndices;
	}
	
	
	/**
	 *  pseudocode : loop I over examples where alpha is not 0 & not C
	 * @return int - the number of times example has changed
	 */
	private int firstHeuristic() {
		int numChanged = 0;
		List<Integer> nonBoundIndices = this.getNonBoundIndices();
		for (int index: nonBoundIndices) {
			numChanged += this.examineExample(index);
		}
		return numChanged;
	}

	/**
	 * main driver for the SMO
	 * after calling this, the smo will be trained and finding output of input X
	 * can be found by calling smo.output(X)
	 * 
	 * Training time = around 40 mins with default set hyperparameters and default dataset files
	 * 
	 * no parameter
	 */
	void mainRoutine() {
		int numChanged = 0;
		boolean examineAll = true;
		
		while (numChanged > 0 || examineAll) {
			numChanged = 0;
	
			if (examineAll) {
				for(int i = 0; i < N; i++) {
					numChanged += this.examineExample(i);
				}
			} else {
				numChanged += this.firstHeuristic();
			}
	
			if (examineAll) {
				examineAll = false;
			} else if (numChanged == 0) {
				examineAll = true;
			}
		}
	}
	
	




}
