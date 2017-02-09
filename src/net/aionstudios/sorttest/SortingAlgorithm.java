package net.aionstudios.sorttest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class SortingAlgorithm {
	
	private String name;
	private String desc;

	public SortingAlgorithm(String name){
		this.name = name;
		SortingManager.addAlg(this);
	}
	
	public SortingAlgorithm(String name, String desc){
		this.name = name;
		this.desc = desc;
		SortingManager.addAlg(this);
	}

	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}
	
	public final void sortCall(int arraySize, boolean verbose, long passFreq){
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 1; i < arraySize+1; i++) {
		    list.add(i);
		}

		// Shuffle it
		Collections.shuffle(list);

		// Get an int[] array
		int[] array = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
		    array[i] = list.get(i);
		}
		sort(array, verbose, passFreq);
	}
	
	private final void sort(int[] a, boolean verbose, long passFreq){
		if(passFreq<1000){
			passFreq=1000;
		}
		long passes = 0;
		long start = System.currentTimeMillis()-1000;
		long elapsed = 0;
		long lastElapsed = 0;
		long lastPasses = 0;
		if(verbose){
			while(!isSorted(a)){
				a = sortingPass(a);
				passes++;
				lastPasses++;
				elapsed=System.currentTimeMillis()-start;
				if(elapsed>=lastElapsed){
					lastElapsed=elapsed+passFreq;
					System.out.println("A rotation has completed: "+passFreq+" milliseconds have passed!");
					System.out.println("  Passes this rotation: "+lastPasses);
					lastPasses=0;
					System.out.println("  Total Time: "+elapsed);
					System.out.println("  Total Passes: "+passes);
				}
			}
			if(passes!=0&&elapsed!=0){
				System.out.println("Sorting '"+this.getName()+"' completed in "+elapsed/1000+" seconds");
				System.out.println("  Array length: "+a.length+" ints.");
				System.out.println("  Total Passes: "+passes);
				System.out.println("  Avg passes/sec: "+passes/((elapsed)/1000));
			} else {
				System.out.println("  0 passes or no time :C, the array was sorted so quickly it couldn't be measured!");
				System.out.println("    - Try passing a larger array argument like '-a 100'");
			}
		} else {
			while(!isSorted(a)){
				a = sortingPass(a);
				passes++;
				elapsed=System.currentTimeMillis()-start;
				lastElapsed = elapsed-lastElapsed;
			}
			if(passes!=0){
				System.out.println("Sorting '"+this.getName()+"' completed in "+elapsed/1000+" seconds");
				System.out.println("  Array length: "+a.length+" ints.");
				System.out.println("  Total Passes: "+passes);
				System.out.println("  Avg passes/sec: "+passes/((elapsed)/1000));
			} else {
				System.out.println("  0 passes :C, the array was sorted so quickly it couldn't be measured!");
				System.out.println("    - Try passing a larger array argument like '-a 100'");
			}
		}
	}
	
	private final boolean isSorted(int[] a) {
		for(int i = 1; i < a.length;i++){
			if(!(a[i]>a[i-1])){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * This method should make one pass to rearrange the array and then return the next pass of the sorted array.
	 * 
	 * @param ia the array of integers to sort.
	 * 
	 * @return the array after one sorting pass
	 */
	public abstract int[] sortingPass(int[] ia);
	
}
