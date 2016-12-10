package weka;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import common.PDFContainer;
import common.Scientific;
import mongodb.PDF;
import weka.clusterers.Cobweb;

public class LearningDataSet {

	private ArrayList<Attribute> atts = null;
	private Instances data = null;
	private ArrayList<String> attVals;
	DateFormat generalDateFormat;
	
	private void init() {
		DateFormat generalDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		attVals = new ArrayList<String>();
	    attVals.add("true");
	    attVals.add("false");
	}
	
	private void generateFormat() {
		atts = new ArrayList<Attribute>();
		
		// 1. set up attributes
		for(int i=0;i<PDFContainer.attrNo;++i){
			
			if(PDFContainer.PDFAttrTypes[i] == int.class || 
					PDFContainer.PDFAttrTypes[i] == float.class ||
						PDFContainer.PDFAttrTypes[i] == double.class ||
							PDFContainer.PDFAttrTypes[i] == long.class)
			{
				// - numeric
				atts.add(new Attribute(PDFContainer.PDFAttrNames[i]));
			}else if(PDFContainer.PDFAttrTypes[i] == Date.class){
				// - date
			    atts.add(new Attribute(PDFContainer.PDFAttrNames[i], "yyyy-MM-dd"));
			}else if(PDFContainer.PDFAttrTypes[i] == boolean.class ||
					PDFContainer.PDFAttrTypes[i] == Boolean.class ){
				// - nominal
			    atts.add(new Attribute(PDFContainer.PDFAttrNames[i], attVals));
			}else if(PDFContainer.PDFAttrTypes[i] == String.class){
				// - string
			    atts.add(new Attribute(PDFContainer.PDFAttrNames[i], 
			    		(ArrayList<String>) null));
			}else if(PDFContainer.PDFAttrTypes[i] == int[].class || 
						PDFContainer.PDFAttrTypes[i] == float[].class ||
							PDFContainer.PDFAttrTypes[i] == double[].class ||
								PDFContainer.PDFAttrTypes[i] == long[].class){
				// - relational
				ArrayList<Attribute> attsRel = new ArrayList<Attribute>();
			    // -- numeric
			    attsRel.add(new Attribute("Value"));
			    Instances dataRel = new Instances(PDFContainer.PDFAttrNames[i], attsRel, 0);
			    atts.add(new Attribute(PDFContainer.PDFAttrNames[i], dataRel, 0));
			}else if(PDFContainer.PDFAttrTypes[i] == String[].class){
				// - relational
				ArrayList<Attribute> attsRel = new ArrayList<Attribute>();
				// - string
				attsRel.add(new Attribute("Value", 
						(ArrayList<String>) null));;
			    Instances dataRel = new Instances(PDFContainer.PDFAttrNames[i], attsRel, 0);
			    atts.add(new Attribute(PDFContainer.PDFAttrNames[i], dataRel, 0));
			}	  
		}
		
	    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	    Date date = new Date();
	    
		// create Instances object
	    data = new Instances("PDFCollection_"+dateFormat.format(date).toString(), atts, 0);
	}
	
	public LearningDataSet(String path) {
		ArffLoader loader = new ArffLoader();
		try {
			loader.setSource(new File(path));
			data = loader.getDataSet();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public LearningDataSet() {
		init();
		generateFormat();
	}
	
	public LearningDataSet(PDF[] resource, boolean scientific) {
		init();
		generateFormat();
	}
	
	public void addPDF(PDF pdf, Boolean scientific) {
		
		Field[] fields = PDF.class.getDeclaredFields();
		
		for(int j = 1; j< PDFContainer.attrNo; ++j){
			fields[j].setAccessible(true);
		}
		
		double[] vals = new double[data.numAttributes()];
		
		for(int i = 1; i< PDFContainer.attrNo; ++i){
			
			//fields[j].get(pdf);
			
			if(PDFContainer.PDFAttrTypes[i] == int.class || 
					PDFContainer.PDFAttrTypes[i] == float.class ||
						PDFContainer.PDFAttrTypes[i] == double.class ||
							PDFContainer.PDFAttrTypes[i] == long.class)
			{
				// - numeric
				try {
					vals[i-1] = ((Number)fields[i].get(pdf)).doubleValue();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}else if(PDFContainer.PDFAttrTypes[i] == Date.class){
				// - date
				try {
					try {
						vals[i-1] = data.attribute(i).parseDate(
								generalDateFormat.format(fields[i].get(pdf)).toString());
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}else if(PDFContainer.PDFAttrTypes[i] == boolean.class){
				// - nominal
				try {
					vals[i-1] = attVals.indexOf(fields[i].get(pdf).toString());
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}else if(PDFContainer.PDFAttrTypes[i] == Boolean.class){
				// - nominal
				try {
					Boolean boolVal = (Boolean)fields[i].get(pdf);
					if(boolVal != null){
						vals[i-1] = attVals.indexOf(boolVal.toString());
					}else{
						//...
					}
					
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}else if(PDFContainer.PDFAttrTypes[i] == String.class){
				// - string
				try {
					String strData = fields[i].get(pdf).toString();
					vals[i-1] = data.attribute(i-1).addStringValue(strData);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}else if(PDFContainer.PDFAttrTypes[i] == int[].class || 
						PDFContainer.PDFAttrTypes[i] == float[].class ||
							PDFContainer.PDFAttrTypes[i] == double[].class ||
								PDFContainer.PDFAttrTypes[i] == long[].class){
				// - relational
			    Instances dataRel = new Instances(data.attribute(i-1).relation(), 0);
			    double elements[] = null;
			    
			    try {
					elements = (double[]) fields[i].get(pdf);
				} catch (IllegalArgumentException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				}
			    
			    // -- add instances
			    double[] valsRel;
		
			    int n = elements.length;
			    
			    for(int j = 0;j<n;++j){
			    	valsRel = new double[1];
			    	valsRel[0] = elements[j];
				    dataRel.add(new DenseInstance(1.0, valsRel));
			    }
			    
			    vals[i-1] = data.attribute(i-1).addRelation(dataRel);
			}else if(PDFContainer.PDFAttrTypes[i] == String[].class){
				// - relational
			    Instances dataRel = new Instances(data.attribute(i-1).relation(), 0);
			    String elements[] = null;
			    
			    try {
					elements = (String[]) fields[i].get(pdf);
				} catch (IllegalArgumentException e1) {
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				}
			    
			    // -- add instances
			    double[] valsRel;
		
			    int n = elements.length;
			    
			    for(int j = 0;j<n;++j){
			    	valsRel = new double[1];
			    	valsRel[0] = dataRel.attribute(0).addStringValue(elements[j]);
				    dataRel.add(new DenseInstance(1.0, valsRel));
			    }
			    
			    vals[i-1] = data.attribute(i-1).addRelation(dataRel);
			    
			}else if(PDFContainer.PDFAttrTypes[i] == Scientific.class){
				///
			}
		}
		
		// add
	    data.add(new DenseInstance(1.0, vals));
	}
	
	public void addAllPDF(PDF[] pdf, boolean scientific){
		int n = pdf.length;
		for(int i = 0;i<n;++i){
			addPDF(pdf[i], scientific);
		}
	}
	
	public void write(){
		System.out.println(data);
	}
	
	@Override
	public String toString(){
		return data.toString();
	}
	
	public ArrayList<String> getSubtitles(int i) {
	    ArrayList<String> answer = new ArrayList<String>();
	    
	    int subTitleAttrInd = 0;
	    while(common.PDFContainer.PDFAttrNames[++subTitleAttrInd].equals("subtitles")){}
	    
	    long l = data.size();

	    if(i<l && l >= 0)
	    {
	    	Instance inst = data.instance(i);
	    	Attribute attr = inst.attribute(subTitleAttrInd);
	    	String subtitles = inst.stringValue(attr);
	    	
	    	ArrayList<String> arrList = common.Tools.stringToArrList(subtitles);
	    	answer = arrList;
	    }

	    return answer;
	}
	
	public ArrayList<ArrayList<String>> getAllSubtitles() {
		
	    ArrayList<ArrayList<String>> answer = new ArrayList<ArrayList<String>>();
	    long l = data.size();
	    
	    for(long i = 0;i<l;++i)
	    {	    	
	    	ArrayList<String> arrList = getSubtitles((int)i);
	    	answer.add(arrList);
	    }

	    return answer;
	}
}
