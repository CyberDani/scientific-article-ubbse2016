package common;

import java.util.ArrayList;

public class Tools {
	
	public static int[] intArrListToArray(ArrayList<Integer> arrList)
	{
	    int[] ret = new int[arrList.size()];
	    for (int i=0; i < ret.length; i++)
	    {
	        ret[i] = arrList.get(i).intValue();
	    }
	    return ret;
	}
	
	public static double[] doubleArrListToArray(ArrayList<Double> arrList)
	{
		double[] ret = new double[arrList.size()];
	    for (int i=0; i < ret.length; i++)
	    {
	        ret[i] = arrList.get(i).doubleValue();
	    }
	    return ret;
	}
	
	public static float[] floatArrListToArray(ArrayList<Float> arrList)
	{
		float[] ret = new float[arrList.size()];
	    for (int i=0; i < ret.length; i++)
	    {
	        ret[i] = arrList.get(i).floatValue();
	    }
	    return ret;
	}
	
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
