package edu.utdallas;

/**
 * @author: Miti Desai
 * Create Time: 03/15/17
 */
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

public class CodeCoverageCollect {
	// Need to Map: Test Case -> Class -> Statement Coverage
	public static Object2ObjectOpenHashMap<String, Object2ObjectOpenHashMap<String, IntSet>> Coverages_testCase;
	public static Object2ObjectOpenHashMap<String, IntSet> Coverage_testCase;
	public static String Name_testCase;

    // Called whenever executing a line
    public static void addMethodLine(String className, Integer line){
    	if (Coverage_testCase == null) {
    		return;
    	}
    	
    	IntSet lines = Coverage_testCase.get(className);
        if (lines != null) {
        	lines.add(line);
        }
        else {
        	lines = new IntOpenHashSet(new int[]{line});
            Coverage_testCase.put(className, lines);
        }
    }
}
