package backend.weka;

import common.LearningAlgorithm;
import common.PDFContainer;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.M5P;
import weka.core.Instances;
import weka.classifiers.Evaluation;
import java.util.Random;

import backend.model.PDF;

public class DataLearnerPredictor {

	private Instances data;
	private LearningAlgorithm learnAlg;
	private J48 tree = null;
	private M5P m5ptree = null;
	
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
		String[] options = null;
		switch (learnAlg) {
		
        case DecisionTree_J48:
        	options = new String[1];
        	options[0] = "-U"; // unpruned tree
        	tree = new J48(); // new instance of tree
        	
        	try {
        		data.setClassIndex(data.numAttributes()-1);
				//tree.setOptions(options); // set the options
				tree.buildClassifier(data); // build classifier
			} catch (Exception e) {
				e.printStackTrace();
			} 
        	
            break;
        case DecisionTreeLinRegression_M5P:
        	options = new String[1];
        	options[0] = "-U"; // unpruned tree
        	m5ptree = new M5P(); // new instance of tree
        	
        	try {
        		data.setClassIndex(data.numAttributes()-1);
				//tree.setOptions(options); // set the options
        		m5ptree.buildClassifier(data); // build classifier
			} catch (Exception e) {
				e.printStackTrace();
			} 
        	
            break;
        default: //throw Exception
                 break;
		}
	}
	
	public double crossValidation(){
		Evaluation eval = null;
		switch (learnAlg) {
		
        case DecisionTree_J48:
        	J48 tree = new J48();
        	
        	try {
        		eval = new Evaluation(data);
				eval.crossValidateModel(tree, data, data.size()-1, new Random(1));
			} catch (Exception e) {
				e.printStackTrace();
			}
        	
        	System.out.println(eval.toSummaryString("\nResults\n\n", false));
        	System.out.println(eval.pctCorrect() + " " +eval.pctIncorrect());
        	return eval.pctCorrect();
        	
            
        case DecisionTreeLinRegression_M5P:
        	
        	M5P m5ptree = new M5P();
        	
        	try {
        		eval = new Evaluation(data);
				eval.crossValidateModel(m5ptree, data, data.size()-1, new Random(1));
			} catch (Exception e) {
				e.printStackTrace();
			}
        	
        	System.out.println(eval.toSummaryString("\nResults\n\n", false));
        	System.out.println(eval.pctCorrect() + " " +eval.pctIncorrect());
        	
        	return eval.pctCorrect();
        default: //throw Exception
                 break;
		}
		
		return -2.4;
	}
	
	public String predict(PDF pdf){
		
		LearningDataSet lds = new LearningDataSet(PDFContainer.lds.getPdfWords());
		lds.addPDF(pdf);
		Instances insts = lds.getInstances();
		
		//data.add(lds)
		
		switch (learnAlg) {
        case DecisionTree_J48:
        	// output predictions
        	//System.out.println("# - actual - predicted - distribution");
        		
        	double pred = 0;;
        	
			try {
				//J48 tree2 = new J48();
				
				
				insts.setClassIndex(insts.numAttributes()-1);
				//tree2.buildClassifier(insts); // build classifier
				
				pred = tree.classifyInstance(insts.instance(0));
				//dist = tree.distributionForInstance(lds.getInstances().instance(0));
			} catch (Exception e) {
				e.printStackTrace();
			}
        	
        	//System.out.print("prediction: " + insts.classAttribute().value((int) pred));
        	
        	
            return insts.classAttribute().value((int) pred);
            
        default: //throw Exception
                 break;
		}
		return null;
	}
}
