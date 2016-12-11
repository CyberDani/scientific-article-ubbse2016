package weka;

import common.LearningAlgorithm;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.Utils;
import weka.classifiers.Evaluation;
import java.util.Random;

public class DataLearnerPredictor {

	private Instances data;
	private LearningAlgorithm learnAlg;
	private J48 tree = null;
	
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
        	tree = new J48(); // new instance of tree
        	
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
	
	public void predict(){
		
		switch (learnAlg) {
        case DecisionTree_J48:
        	// output predictions
        	System.out.println("# - actual - predicted - distribution");
        	
        	double pred = 0;;
        	double[] dist = null;
        	
			try {
				pred = tree.classifyInstance(data.instance(0));
				dist = tree.distributionForInstance(data.instance(0));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
        	System.out.print((0+1) + " - ");
        	System.out.print(data.instance(0).toString(data.classIndex()) + " - ");
        	System.out.print(data.classAttribute().value((int) pred) + " - ");
        	System.out.println(Utils.arrayToString(dist));
        	
        	
            break;
        default: //throw Exception
                 break;
		}
	}
}
