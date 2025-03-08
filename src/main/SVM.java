/**
 * Support Vector Machine
 * Trains only on one target/class 
 * @author Mialy Andrianarivony
 */
package main;

import java.util.*;


/**
 * Support Vector Machine for one Classification
 * Applies Sequential Minimal Optimization 
 */
public class SVM {
	double C;
	double tolerance;
	double gamma;
	double b;
	
	ReadFile read = new ReadFile();

	List<Vector> trainingPoints;
	List<Vector> testingPoints;

	
	SMOptim smo;
	int testTarget = 0; // default zero if not given
	
	/**
	 * Constructor
	 * testTarget is the value of a certain data point 
	 * @param testTarget - classification target
	 */
	SVM(int testTarget){
	
		if (testTarget != -1) {
			this.testTarget = testTarget;
		}
		
		trainingPoints = new ArrayList<Vector>();
		testingPoints = new ArrayList<Vector>();
	}
	
	/**
	 * Cleans data for training
	 * Create a List of Vectors, List<Vector> and change
	 * y-value to -1 or 1 based on the Test target
	 * @param trainingVectors
	 */
	public void setTrainingData(List<Vector> trainingVectors) {
		this.trainingPoints = new ArrayList<Vector>();
		for (Vector v: trainingVectors){
			List<Integer> X = v.X;
			int y;
			if (v.y == testTarget) {
				y = 1;
			} else { 
				y = -1;
			};
			Vector trainingVector = new Vector(X, y);
			this.trainingPoints.add(trainingVector);
		}
		
	}
	
	
	public void setTestingData(List<Vector> trainingVectors) {
		if (trainingVectors.size() > 0) {
			
			this.testingPoints = trainingVectors;
		}
	}
	
	/**
	 * Set Hyperparameters for the machine to work on
	 * C - soft margin hyperparameter 
	 * @param C
	 * @param tol
	 * @param gamma
	 */
	public void setParameters(double C, double tol, double gamma) {
		this.C = C;
		this.tolerance = tol;
		this.gamma = gamma;	
	}


	/**
	 * create a SMO for each SVM
	 * call SMO.mainRoutine to start
	 */
	void train() {
		System.out.println("Training...");
		System.out.println("Target: " + this.testTarget);
		this.smo = new SMOptim(this.trainingPoints, this.C, this.tolerance, this.gamma);
		this.smo.mainRoutine();
	}
	
	/**
	 * Test a Vector.X value on the trained SMO
	 * @param X
	 * @return
	 */
	double testDatapoint(List<Integer> X) {
		double output = 0;
		output = this.smo.testOutput(X);
		return output;
	}
	

	
	
	


}
