package weka;

import common.LearningAlgorithm;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.classifiers.Evaluation;
import java.util.Random;

public class DataLearnerPredictor {

	private Instances data;
	private LearningAlgorithm learnAlg;
	
	public DataLearnerPredictor(LearningDataSet ds){
		learnAlg = null;
		data = ds.getInstances();
	}
	
	public boolean isSetAlgorithm(){
		return (learnAlg != null);
	}
	
	public void setAlgorithm(LearningAlgorithm alg){
		learnAlg = alg;
	}
	
	public LearningAlgorithm getAlgorithm(){
		return learnAlg;
	}
	
	public void train(){
		switch (learnAlg) {
		
        case DecisionTree_J48:
        	String[] options = new String[1];
        	options[0] = "-U"; // unpruned tree
        	J48 tree = new J48(); // new instance of tree
        	
        	try {
				tree.setOptions(options); // set the options
				tree.buildClassifier(data); // build classifier
			} catch (Exception e) {
				e.printStackTrace();
			} 
        	
            break;
        default: //throw Exception
                 break;
		}
	}
	
	public void crossValidation(){
		switch (learnAlg) {
		
        case DecisionTree_J48:
        	Evaluation eval = null;
        	J48 tree = new J48();
        	
        	try {
        		eval = new Evaluation(data);
				eval.crossValidateModel(tree, data, data.size()-1, new Random(1));
			} catch (Exception e) {
				e.printStackTrace();
			}
        	
        	System.out.println(eval.toSummaryString("\nResults\n\n", false));
        	System.out.println(eval.pctCorrect() + " " +eval.pctIncorrect());
        	
            break;
        default: //throw Exception
                 break;
		}
	}
}
