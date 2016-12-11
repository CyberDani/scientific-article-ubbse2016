package weka;

import weka.core.Instances;

public class DataLearnerPredictor {

	private Instances data;
	
	public DataLearnerPredictor(LearningDataSet ds)
	{
		data = ds.getInstances();
	}
	
}
