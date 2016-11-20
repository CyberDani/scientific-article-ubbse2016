package weka;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;

import mongodb.PDF;
import weka.clusterers.Cobweb;

public class LearningDataSet {

	public LearningDataSet(PDF[] resource)
	{
		// 1. set up attributes
		ArrayList<Attribute> atts = new ArrayList<Attribute>();
		
		// 2. create Instances object
		Instances data = new Instances("MyRelation", atts, 0);
	}
}
