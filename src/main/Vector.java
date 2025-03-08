/**
 * Vector.java
 * Create vector data type
 * @author Mialy Andrianarivony
 */

package main;

import java.util.List;

public class Vector {
	
	final List<Integer> X;
	final int y;
	
	/**
	 * Vector constructor
	 * @param X - must be a List<Integer> that has all dimensions
	 * @param y - type int - range 0..9
	 */
	Vector(List<Integer> X, Integer y){
		this.X = X;
		this.y = y;
	}

	/**
	 * @return Vector.X
	 */
	public List<Integer> getX() {
		return X;
	}

	/**
	 * 
	 * @return Vector.y
	 */
	public int getY() {
		return y;
	}
	
	

}
