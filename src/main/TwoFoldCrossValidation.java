/**
 * TwoFoldCrossValidation.java
 * For SVM
 * @author Mialy Andrianarivony
 */

package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TwoFoldCrossValidation {
	// original List of Vector
	List<Vector> trainingPoints;
	
	// List of vectors for cross validation 
	List<Vector> crossValidationData1;
	List<Vector> crossValidationData2;

	
	/**
	 * Constructor
	 * Create 2 new empty datasets as list of vectors
	 * @param trainingPoints - List of Vectors
	 */
	TwoFoldCrossValidation(List<Vector> trainingPoints){
		this.trainingPoints = trainingPoints;
		this.crossValidationData1 = new ArrayList<Vector>();
		this.crossValidationData2 = new ArrayList<Vector>();
		 
	}
	
	/**
	 * Create a list of vectors per class (0 to 9)
	 * Then call shuffle and divide on each of those class
	 * 
	 */
	public void applyRandomTwoFold() {

		// 2-fold testing
		// populate data1 and data2 
		List<Vector> class0 = new ArrayList<Vector>();
		List<Vector> class1 = new ArrayList<Vector>();
		List<Vector> class2 = new ArrayList<Vector>();
		List<Vector> class3 = new ArrayList<Vector>();
		List<Vector> class4 = new ArrayList<Vector>();
		List<Vector> class5 = new ArrayList<Vector>();
		List<Vector> class6 = new ArrayList<Vector>();
		List<Vector> class7 = new ArrayList<Vector>();
		List<Vector> class8 = new ArrayList<Vector>();
		List<Vector> class9 = new ArrayList<Vector>();
				
		for(Vector v: this.trainingPoints) {
			if (v.y == 0) class0.add(v);
			if (v.y == 1) class1.add(v);
			if (v.y == 2) class2.add(v);
			if (v.y == 3) class3.add(v);
			if (v.y == 4) class4.add(v);
			if (v.y == 5) class5.add(v);
			if (v.y == 6) class6.add(v);
			if (v.y == 7) class7.add(v);
			if (v.y == 8) class8.add(v);
			if (v.y == 9) class9.add(v);
		}
		
		shuffleAndDivide(class0);
		shuffleAndDivide(class1);
		shuffleAndDivide(class2);
		shuffleAndDivide(class3);
		shuffleAndDivide(class4);
		shuffleAndDivide(class5);
		shuffleAndDivide(class6);
		shuffleAndDivide(class7);
		shuffleAndDivide(class8);
		shuffleAndDivide(class9);
	}
	
	/** 
	 * Shuffle each list of vectors and populate data1 and data2
	 * Shuffle data1 and data2 so all values are randomly placed in the lists
	 * @param listOfClass
	 */
	public void shuffleAndDivide(List<Vector> listOfClass) {
		Collections.shuffle(listOfClass);

	    int halfRandom = (int) listOfClass.size() / 2;

	    List<Vector> firstHalf = listOfClass.subList(0, halfRandom);
	    List<Vector> secondHalf = listOfClass.subList(halfRandom, listOfClass.size());
		
	    for(Vector v1: firstHalf) {
	    	this.crossValidationData1.add(v1);
	    }
	    
	    for(Vector v2: secondHalf) {
	    	this.crossValidationData2.add(v2);
	    }
	    
	    // Shuffle datapoints
	    Collections.shuffle(this.crossValidationData1);
	    Collections.shuffle(this.crossValidationData2);
	    
	}

	/**
	 * return Data2
	 * @return List of Vector
	 */
	public List<Vector> getCrossValidationData1() {
		return crossValidationData1;
	}

	/**
	 * return Data2
	 * @return List of Vector
	 */
	public List<Vector> getCrossValidationData2() {
		return crossValidationData2;
	}


}
