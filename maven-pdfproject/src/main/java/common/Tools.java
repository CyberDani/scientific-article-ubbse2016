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
	
}
