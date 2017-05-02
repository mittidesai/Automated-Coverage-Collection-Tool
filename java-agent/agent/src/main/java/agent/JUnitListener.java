
/**
 * 
 @author PragatiPrakashSrivastava
 */
package agent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;

import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

public class JUnitListener extends RunListener {

	// Called before all tests
	public void testRunStarted(Description description) throws Exception {

		if (null == CoverageCollector.testCaseCoverages) {
			CoverageCollector.testCaseCoverages = new Object2ObjectOpenHashMap<String, Object2ObjectOpenHashMap<String, IntSet>>();
		}

		// System.out.println("\n\nStart!");
	}

	// Called before each @Test
	public void testStarted(Description description) {
		// Note: Java is pass by value, so this works
		CoverageCollector.testCaseName = "" + description.getClassName() + ":" + description.getMethodName();
		CoverageCollector.testCaseCoverage = new Object2ObjectOpenHashMap<String, IntSet>();
	}

	// Called after each @Test Finishes
	public void testFinished(Description description) {
		/*
		 * Additional strategy here --> before adding to testCaseCoverages check
		 * the testCaseCoverages and the new testCaseCoverage to remove already
		 * tested lines and add the remaining lines to the data structure
		 */
		CoverageCollector.testCaseCoverages.put(CoverageCollector.testCaseName, CoverageCollector.testCaseCoverage);
	}

	// Called after all tests finish
	public void testRunFinished(Result result) throws IOException {
	        System.out.println("Done!\n\n");
	        
	        // Write to stmt-cov.txt
	        File fout = new File("Report_StatementCoverage.txt");
	        FileOutputStream fos = new FileOutputStream(fout);
	        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
	
	        StringBuilder builder = new StringBuilder();
	        for (String testCaseName : CoverageCollector.testCaseCoverages.keySet()) {
	        	builder.append(testCaseName + "\n");
	        	
	        	Object2ObjectOpenHashMap<String, IntSet> caseCoverage = 
	        			CoverageCollector.testCaseCoverages.get(testCaseName);
	        	
	            for (String className : caseCoverage.keySet()) {
	            	int[] lines = caseCoverage.get(className).toIntArray();
	            	
	            	Arrays.sort(lines);
	            	for (int i = 0; i < lines.length; i++) {
	                	builder.append(className + ":" + lines[i] + "\n");
					}
	            }
	        }
	        
	        bw.write(builder.toString());
	        bw.close();
	        
	        
	        
	        
	     // Total prioritization file write
		HashMap<String, HashSet<String>> caseCoverage1 = new HashMap<String, HashSet<String>>();
		LinkedHashMap<String, Integer> linkhashmap = new LinkedHashMap<String,Integer>();
	
		for (String testCaseName : CoverageCollector.testCaseCoverages.keySet()) {
			HashSet<String> hashSet = new HashSet<String>();
			Object2ObjectOpenHashMap<String, IntSet> caseCoverage11 = CoverageCollector.testCaseCoverages.get(testCaseName);
		
			for (String className : caseCoverage11.keySet()) {
				int[] lines = caseCoverage11.get(className).toIntArray();
	
				for (int i = 0; i < lines.length; i++) {
					hashSet.add(className + ":" + lines[i]);
					}
			}
			caseCoverage1.put(testCaseName,hashSet);
			linkhashmap.put(testCaseName, hashSet.size());
		}
		
		File fout1 = new File("stmt-cov-prioritized-total.txt");
		FileOutputStream fos1 = new FileOutputStream(fout1);
		BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(fos1));
		StringBuilder builder1 = new StringBuilder();
		Object[] a = linkhashmap.entrySet().toArray();
		
		Arrays.sort(a, new Comparator<Object>() {
	    public int compare(Object o1, Object o2) {
			   return ((Map.Entry<String, Integer>) o2).getValue()
			         .compareTo(((Map.Entry<String, Integer>) o1).getValue());
			    }
		});
		
		for (Object e : a) {
			   builder1.append(((Map.Entry<String, Integer>) e).getKey() + " : "
			    + ((Map.Entry<String, Integer>) e).getValue()+"\n");
			}
			
		 bw1.write(builder1.toString());
		 bw1.close();
	   
		 
		 try{
			 LinkedHashMap<String, Integer> additionalPriorityOrder = new LinkedHashMap<String,Integer>();
			 Set<String> coveredStmts = new HashSet<String>();	
			 for(int i=0;i<linkhashmap.size();i++){
				 String nextTest = "";
				 int maxAdditionalCoverage= 0;
					 for( String tName : caseCoverage1.keySet()){
						 caseCoverage1.get(tName).removeAll(coveredStmts);
					     if(maxAdditionalCoverage < caseCoverage1.get(tName).size()){
					    	 maxAdditionalCoverage = caseCoverage1.get(tName).size();
					    	 nextTest = tName;
					     }
					 } 
				
				 additionalPriorityOrder.put(nextTest, caseCoverage1.get(nextTest).size());
				 coveredStmts.addAll(caseCoverage1.get(nextTest));
			
				 caseCoverage1.remove(nextTest);
			 }
			 
			 File fout2 = new File("stmt-cov-prioritized-additional.txt");
				FileOutputStream fos2 = new FileOutputStream(fout2);
				BufferedWriter bw2 = new BufferedWriter(new OutputStreamWriter(fos2));
				StringBuilder builder2 = new StringBuilder();
				
				for (String testName : additionalPriorityOrder.keySet()) {
					   builder2.append(testName + " : "
					    + additionalPriorityOrder.get(testName)+"\n");
					}
					
				 bw2.write(builder2.toString());
				 bw2.close();
			   
			 
		 //creating total test suite
				 //creating a test suite
				 PrintWriter writer = new PrintWriter("src/test/java/TotalTestsSuiteClasses.java", "UTF-8");
				 	
			        writer.println("import org.junit.runner.RunWith;");
			        writer.println("import org.junit.runners.Suite;");
			        writer.println("import org.junit.runners.Suite.SuiteClasses;");
			        writer.println("@RunWith(Suite.class)");
			        writer.println("@SuiteClasses({");
			        int counter = 0;
			        for (String testName : linkhashmap.keySet()) {
			        	counter++;
			        		String[] splitTestName = testName.split(":");
			        		String writeString = splitTestName[0] + ".class";
			        		if(counter<linkhashmap.size()){
			        			writeString=writeString+",";
			        		}
			        		writer.println(writeString);
						}
			        
			        writer.println("})");
			        writer.println("public class TotalTestsSuiteClasses {");
			        writer.println("}");
			        writer.close();
		 //end
		 
		 //creating additional test suite
		/* PrintWriter writer = new PrintWriter("src/test/java/AdditionalTestsSuiteClasses.java", "UTF-8");
		 	
	        writer.println("import org.junit.runner.RunWith;");
	        writer.println("import org.junit.runners.Suite;");
	        writer.println("import org.junit.runners.Suite.SuiteClasses;");
	        writer.println("@RunWith(Suite.class)");
	        writer.println("@SuiteClasses({");
	        int counter = 0;
	        for (String testName : additionalPriorityOrder.keySet()) {
	        	counter++;
	        		String[] splitTestName = testName.split(":");
	        		String writeString = splitTestName[0] + ".class";
	        		if(counter<additionalPriorityOrder.size()){
	        			writeString=writeString+",";
	        		}
	        		writer.println(writeString);
				}
	        
	        writer.println("})");
	        writer.println("public class AdditionalTestsSuiteClasses {");
	        writer.println("}");
	        writer.close();*/
			
		 } catch(Exception e){
			 e.printStackTrace();
		 }


	// end
}

}
