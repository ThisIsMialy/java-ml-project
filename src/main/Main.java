/**
 * MAIN.JAVA
 * dependencies:
 * Kernel.java, ReadFile.java, SMOptim.java, SVM.java, TwoFoldCrossValidation.java, Vector.java
 * 
 * CST3170 AI CW2 - Hand written digit recognition 
 * 
 * @author Mialy Andrianarivony
 */

package main;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
	
	// Default hyperparameters for best results
	private final static double defaultC = 100;
	private final static double defaultTol = (double) 0.01; 
	private final static double defaultGamma = (double) 1.0 * 0.001;
	
	private final static String TRAINING_DATASET = "cw2Dataset1.csv";
	private final static String TESTING_DATASET = "cw2Dataset2.csv";
	
	static String trainingDataset;
	static String testingDataset;
	
	
	//****************************************************************
	// Change parameters here
	static double C = 100;
	static double tolerance = 0.01;
	static double gamma = (double) 1.0 * 0.001; // default (-1) for RBF kernel
	//****************************************************************
	
	static List<SVM> mutliClassSVM;
	
	static List<SVM> crossValidationSVM1;
	static List<SVM> crossValidationSVM2;
	
	// List of vectors for cross validation 
	List<Vector> crossValidationData1;
	List<Vector> crossValidationData2;
	
	static ReadFile read = new ReadFile();


	/**
	 * Test a list of Vectors on a given multiSVM
	 * Prints the result: correct and incorrect predictions and accuracy
	 * Test each data on every SVM in the MultiSVM list
	 * Highest output in double[] outputs will be the output prediction
	 * Prediction is then compared to the Vector.y value
	 * 
	 * @param multiClassSVM - List<SVM>
	 * @param testingDataset - List<Vector>
	 */
	private static void startTesting(List<SVM> multiClassSVM, List<Vector> testingDataset) {
		DecimalFormat df = new DecimalFormat("###.###"); // to display accuracy
		int incorrectPredictions = 0;
		int correctPredictions = 0;
		
		for(Vector data: testingDataset){
			double[] outputs = new double[10];
			for(SVM svm : multiClassSVM) {
				double out = svm.testDatapoint(data.X);
				outputs[svm.testTarget] = out;
			}
			
			double max = -1;
			int predictedValue = -1;
			for(int predicted = 0; predicted < 10; predicted++) {
				if(outputs[predicted] > max) {
					max = outputs[predicted];
					predictedValue = predicted;
				}
			}
			
			if (predictedValue == data.y) {
				correctPredictions++;
			} else {
				incorrectPredictions++;
				
			}
			
		}
		System.out.println("::::::::::::::::::::::::::::::");
		System.out.println(":::TESTING RESULTS:::");
		System.out.println("Testing dataset size :" + testingDataset.size());
		System.out.println("Correct Predictions: " + correctPredictions);
		System.out.println("Incorrect Predictions: " + incorrectPredictions);
		double acc = 1.0 * correctPredictions / testingDataset.size();
		double accPerc = 100 * acc;
		System.out.println("Accuracy: " + acc);
		System.out.println("Accuracy Percentage: " + df.format(accPerc) + "%");
		System.out.println("::::::::::::::::::::::::::::::");
	}
	
	/**
	 * Convert file in trainingDataset String to list of Vectors
	 * Construct a new multiSVM list
	 * Calculate training time and print
	 * @param trainingDataset - String - CSV filename
	 */
	private static void startTraining(String trainingDataset) {
		
		System.out.println(":::TRAINING MULTICLASS SVM:::");
		mutliClassSVM  = new ArrayList<SVM>();
		
		List<Vector> trainingVectors = read.csvToVectors(trainingDataset);
		
		long start = System.nanoTime();
		
		for(int testTarget = 0; testTarget < 10; testTarget++) {
			SVM svm = new SVM (testTarget);
			svm.setParameters(C, tolerance, gamma);
			svm.setTrainingData(trainingVectors);
			svm.train();
			mutliClassSVM.add(svm);
		}
		
		long finish = System.nanoTime();
		long timeElapsed = finish - start;
		System.out.println("Elapsed training time: " + timeElapsed + " ns");
		double elapsedTimeInSecond = (double) timeElapsed / 1_000_000_000;
        System.out.println(elapsedTimeInSecond + " seconds");
        System.out.println(elapsedTimeInSecond / 60 + " minutes");
		
	}

	/**
	 * Called when prompted (answer is Y)
	 * Convert file in trainingDataset String to list of Vectors
	 * separate training dataset into 2 list of Vectors - data1 and data2
	 * start 2 tests on data1 and data2
	 * 
	 * @param trainingDataset
	 */
	private static void twoFoldCrossValidation(String trainingDataset) {
		
		System.out.println(":::2 FOLD CROSS VALIDATION:::");
		
		List<Vector> trainingVectors = read.csvToVectors(trainingDataset);
		
		TwoFoldCrossValidation twoFold = new TwoFoldCrossValidation(trainingVectors);
		
		twoFold.applyRandomTwoFold();
		
		List<Vector> data1 = twoFold.getCrossValidationData1();
		List<Vector> data2 = twoFold.getCrossValidationData2();
		
		System.out.println("data1 size: " + data1.size());
		System.out.println("data2 size: " + data2.size());
		
		crossValidationSVM1  = new ArrayList<SVM>();
		crossValidationSVM2  = new ArrayList<SVM>();

		// train on data 1, test on data 2
		long start = System.nanoTime();
		
		for(int testTarget = 0; testTarget < 10; testTarget++) {
			SVM svm = new SVM(testTarget);
			svm.setParameters(C, tolerance, gamma);
			svm.setTrainingData(data1);
			svm.train();
			crossValidationSVM1.add(svm);
		}
		
		long finish = System.nanoTime();
		long timeElapsed = finish - start;
		System.out.println(":::CrossValidation Part 1:::");
		System.out.println("Elapsed training time: " + timeElapsed);
		double elapsedTimeInSecond = (double) timeElapsed / 1_000_000_000;
        System.out.println(elapsedTimeInSecond + " seconds");
        System.out.println(elapsedTimeInSecond / 60 + " minutes");
        
		startTesting(crossValidationSVM1, data2);
		

		
		// train on data 2, test on data 1
		long start2 = System.nanoTime();
		
		for(int testTarget = 0; testTarget < 10; testTarget++) {
			SVM svm = new SVM (testTarget);
			svm.setParameters(C, tolerance, gamma);
			svm.setTrainingData(data2);
			svm.train();
			crossValidationSVM2.add(svm);
		}
		
		long finish2 = System.nanoTime();
		long timeElapsed2 = finish2 - start2;
		System.out.println(":::CrossValidation Part 2:::");
		System.out.println("Elapsed training time: " + timeElapsed2);
		double elapsedTimeInSecond2 = (double) timeElapsed2 / 1_000_000_000;
        System.out.println(elapsedTimeInSecond2 + " seconds");
        System.out.println(elapsedTimeInSecond2 / 60 + " minutes");
        
		startTesting(crossValidationSVM2, data1);
	}



	/**
	 * Check that hyperparameters make sense
	 * Else use default values
	 */
	private static void validateParameters() {
		C = C > 0 && C < 10_001 ? C : defaultC;
		tolerance = tolerance > 0 && tolerance < 1 ? tolerance : defaultTol;
		gamma = gamma > 0 && gamma < 1 ? gamma : defaultGamma;

	}
	
	/**
	 * Main Driver
	 * @param args
	 */
	public static void main(String[] args) {
		// User Input 
		String trainSetInput;
		String testSetInput;
		String crossValInput;
		String performTestInput;

		validateParameters();
		
		
		System.out.println("::::::::DEFAULT DATASETS::::::::");
		System.out.println("TRAINING DATASET: " + TRAINING_DATASET);
		System.out.println("TESTING DATASET: " +  TESTING_DATASET);
		System.out.println("::::::::CURRENT PARAMETERS::::::::");
		System.out.println("C <Soft margin hyperparameter>: " + C);
		System.out.println("tolerance <for SMO>: " + tolerance);
		System.out.println("Gamma <for RBF>: " + gamma);
		System.out.println("----------------------------------------------------------------");
		
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter TRAINING DATASET filename (remain blank to use default): ");
		trainSetInput =  scanner.nextLine(); 
   
		System.out.println("Enter TESTING DATASET filename (remain blank to use default): ");
		testSetInput = scanner.nextLine(); 
		
		System.out.println("Perform Two-fold Cross Validation? (Y/N): ");
		crossValInput = scanner.nextLine();
		
		System.out.println("Perform Testing on Testing Dataset? (Y/N): ");
		performTestInput = scanner.nextLine();
			
		scanner.close();
		

		
		if (trainSetInput.length() > 0) { 
			trainingDataset = trainSetInput;
		} else {
			trainingDataset = TRAINING_DATASET;
		}
		
		if (testSetInput.length() > 0) {
			testingDataset = testSetInput;
		} else {
			testingDataset = TESTING_DATASET;
		}
		
		if (crossValInput.equals("Y")) {
			twoFoldCrossValidation(trainingDataset);
		}
		
		
		if (performTestInput.equals("Y")) {
			startTraining(trainingDataset);
			List<Vector> testingVectors = read.csvToVectors(testingDataset);
			startTesting(mutliClassSVM, testingVectors);
		}
		
		System.out.println("----------------------------------------------------------------");

	}
	
}




