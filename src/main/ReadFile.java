/**
 * ReadFile.java
 * Read CSV file and convert each line to a vector (Vector.java)
 * @author Mialy Andrianarivony
 */

package main;

import java.io.*;
import java.util.*;

import main.Vector;

public class ReadFile {
	// CSV delimiter
	private static final String COMMA_DELIMITER = ",";

	/**
	 * default constructor
	 */
	ReadFile(){
		
	}
	
	/**
	 * return appropriate data type for training/testing
	 * Create new List<Integer> as X - list of all values of the dimension
	 * y - last integer is the class of that vector
	 * Create new Vector type as Vector(X, y)
	 * Add to list and return 
	 * @param filename - from Main
	 * @return a list of Vectors
	 */
	List<Vector> csvToVectors(String filename) {
		List<Vector> vectors = new ArrayList<Vector>();
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	List<Integer> X = new ArrayList<Integer>();
		    	int y;
		        String[] valuesStr = line.split(COMMA_DELIMITER);
		        
		        for (int i = 0; i <= valuesStr.length - 2; i++) {
		        	X.add(Integer.valueOf(valuesStr[i]));
		        }
		        y = Integer.valueOf(valuesStr[valuesStr.length - 1]);
		        Vector v = new Vector(X, y);
		        vectors.add(v);
		    }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return vectors;
	}
}

