package edu.utdallas;
import java.io.*;
import java.util.Arrays;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntSet;

/**
 * @author: Miti Desai
 * Create Time: 03/15/17
 */

public class MyJUnitExecutionListener extends RunListener {

	// Called before all tests
	public void testRunStarted(Description description) throws Exception {
		if (null == CodeCoverageCollect.Coverages_testCase)
		{
			CodeCoverageCollect.Coverages_testCase = new Object2ObjectOpenHashMap<String, Object2ObjectOpenHashMap<String, IntSet>>();
		}
		
        System.out.println("\n\nTesting Started!!!");
    }
	
	// Called before each @Test
    public void testStarted(Description description) {
    	// Note: Java is pass by value, so this works
    	CodeCoverageCollect.Name_testCase = "[TEST] " + description.getClassName() + ":" + description.getMethodName();
    	CodeCoverageCollect.Coverage_testCase = new Object2ObjectOpenHashMap<String, IntSet>();
    }
    
    // Called after each @Test Finishes
    public void testFinished(Description description) {
    	CodeCoverageCollect.Coverages_testCase.put(CodeCoverageCollect.Name_testCase, CodeCoverageCollect.Coverage_testCase);
    }
    
    // Called after all tests finish
    public void testRunFinished(Result result) throws IOException {
        System.out.println("Testing Finished!!!\n\n");
        
        // Write to stmt-cov.txt
        File fout = new File("stmt-cov.txt");
        FileOutputStream fos = new FileOutputStream(fout);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        StringBuilder builder = new StringBuilder();
        for (String testCaseName : CodeCoverageCollect.Coverages_testCase.keySet()) {
        	builder.append(testCaseName + "\n");
        	
        	Object2ObjectOpenHashMap<String, IntSet> caseCoverage = 
        			CodeCoverageCollect.Coverages_testCase.get(testCaseName);
        	
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
    }
}
