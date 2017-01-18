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
	
	/**
	 * <b>UNUSED:</b> Convert <i>String</i> data to <i>ArrayList<String></i> data based 
	 * on newline characters.
	 * 
	 * @param str Source variable for the conversion.
	 */
	public static ArrayList<String> stringToArrList(String str){
		
		ArrayList<String> answer = new ArrayList<String>();
		
		String[] parts = str.split("\n");
		int n = parts.length;
		
		for(int i=0;i<n;++i)
		{
			String title = parts[i];
			int l = title.length();
			
			//remove first and last character
			if(title.startsWith("'")){
				title = title.substring(1, l-1);
				l -= 2;
			}
			
			//remove /r from the ending
			if(title.substring(l-2, l).equals("\\r")){
				title = title.substring(0,l-2);
				l -= 2;
			}
			
			//remove spaces from the beginning
			while(title.charAt(0) == ' '){
				title = title.substring(1,l--);
			}
			
			answer.add(title);
		}
		
		return answer;
	}
	
}
