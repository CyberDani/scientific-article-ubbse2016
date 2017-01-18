package common;

import java.util.ArrayList;

public class Tools {
	
	/**
	 * Simplify a given word by removing characters which potentially 
	 * does not take part of the word.
	 * 
	 * @param word Source variable for the process.
	 */
	public static String simplifyWord(String word){
		int n = word.length();

		//potencialis szo-/mondat eleji jelek eltuntetese
		while ((n > 0) && (word.charAt(0) == '.' || 
				word.charAt(0) == '?' || word.charAt(0) == '!' || 
				word.charAt(0) == ';' || word.charAt(0) == ',' || 
				word.charAt(0) == '(' || word.charAt(0) == '{' || 
				word.charAt(0) == '[' || word.charAt(0) == ':' || 
				word.charAt(0) == '-' || word.charAt(0) == ' ' ||
				word.charAt(0) == '\\' || word.charAt(0) == '/' ||
				word.charAt(0) == '<' || word.charAt(0) == '>' ||
				word.charAt(0) == '<' || word.charAt(0) == '>' ||
				word.charAt(0) == '=' || ((int) word.charAt(0)) < 32)) {
			word = word.substring(1,n);
			--n;
		}
		
		//potencialis szo-/mondatvegi jelek eltuntetese
		while ((n > 0) && (word.charAt(n - 1) == '.' || 
				word.charAt(n - 1) == '?' || word.charAt(n - 1) == '!' || 
				word.charAt(n - 1) == ';' || word.charAt(n - 1) == ',' || 
				word.charAt(n - 1) == ')' || word.charAt(n - 1) == '}' || 
				word.charAt(n - 1) == ']' || word.charAt(n - 1) == ':' || 
				word.charAt(n - 1) == '-' || word.charAt(n - 1) == ' ' ||
				word.charAt(n - 1) == '\\' || word.charAt(n - 1) == '/' ||
				word.charAt(n - 1) == '<' || word.charAt(n - 1) == '>' ||
				word.charAt(n - 1) == '=' || ((int) word.charAt(n - 1)) < 32)) {
			word = word.substring(0,--n);
		}
		
		return word;
	}
	
	/**
	 * Convert <i>ArrayList<Integer></i> data to <i>int[]</i> data.
	 * 
	 * @param arrList Source variable for the conversion.
	 */
	public static int[] intArrListToArray(ArrayList<Integer> arrList)
	{
	    int[] ret = new int[arrList.size()];
	    for (int i=0; i < ret.length; i++)
	    {
	        ret[i] = arrList.get(i).intValue();
	    }
	    return ret;
	}
	
	/**
	 * Convert <i>ArrayList<Double></i> data to <i>double[]</i> data.
	 * 
	 * @param arrList Source variable for the conversion.
	 */
	public static double[] doubleArrListToArray(ArrayList<Double> arrList)
	{
		double[] ret = new double[arrList.size()];
	    for (int i=0; i < ret.length; i++)
	    {
	        ret[i] = arrList.get(i).doubleValue();
	    }
	    return ret;
	}
	
	/**
	 * Convert <i>ArrayList<Float></i> data to <i>float[]</i> data.
	 * 
	 * @param arrList Source variable for the conversion.
	 */
	public static float[] floatArrListToArray(ArrayList<Float> arrList)
	{
		float[] ret = new float[arrList.size()];
	    for (int i=0; i < ret.length; i++)
	    {
	        ret[i] = arrList.get(i).floatValue();
	    }
	    return ret;
	}
	
}
