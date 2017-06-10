package net.aionstudios.sorttest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public abstract class SortingAlgorithm {
	
	private String name;
	private String desc;
	private int[] a;
	private boolean started = false;
	protected long passes = 0;
	private int lastPasses = 0;
	private boolean solved;
	private boolean canParallel = true;
	private ReentrantLock lock;

	public SortingAlgorithm(String name){
		this.name = name;
		lock = new ReentrantLock();
		SortingManager.addAlg(this);
	}
	
	public SortingAlgorithm(String name, String desc){
		this.name = name;
		this.desc = desc;
		lock = new ReentrantLock();
		SortingManager.addAlg(this);
	}

	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}
	
	public final void sortCall(int arraySize, boolean verbose, int passFreq, boolean parallel, boolean half, long timeout, boolean bench){
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
		a = array;
		long start = System.currentTimeMillis()-1000;
		if(parallel&&!canParallel){
			System.out.println("This sorting algorithm does not support parallel processing.");
			System.out.println("Sorting will occur on a single processor.");
		}
		if(parallel&&canParallel){
			int processors = Runtime.getRuntime().availableProcessors();
			if(half){
				processors = processors / 2;
			}
			ExecutorService es = Executors.newFixedThreadPool(processors-1);
			for(int i = 0; i < processors-1; i++){
				es.submit( new Runnable(){

					@Override
					public void run() {
						parallelSort();
						es.shutdown();
					}
					
				});
			}
			sort(verbose, passFreq, start, 0, passFreq, timeout, bench);
			es.shutdown();
		} else {
			sort(verbose, passFreq, start, 0, passFreq, timeout, bench);
		}
	}
	
	private final void sort(boolean verbose, int passFreq, long start, int elapsed, int lastElapsed, long timeout, boolean bench){
		if(passFreq<1000){
			passFreq=1000;
		}
		started = true;
		if(verbose){
			while(!isSorted(a)&&!solved){
				lock.lock();
				a = sortingPass(a);
				lock.unlock();
				passes++;
				lastPasses++;
				elapsed= (int) (System.currentTimeMillis()-start);
				if(elapsed>=lastElapsed){
					lastElapsed=elapsed+passFreq;
					System.out.println("A rotation has completed: "+passFreq+" milliseconds have passed!");
					System.out.println("  Passes this rotation: "+lastPasses);
					lastPasses=0;
					System.out.println("  Total Time: "+elapsed);
					System.out.println("  Total Passes: "+passes);
				}
				if(elapsed>=timeout&&timeout!=0) {
					break;
				}
			}
			if(passes!=0&&elapsed!=0){
				if(bench) {
					System.out.println("Benchmark completed in "+elapsed/1000+" seconds");
				} else {
					System.out.println("Sorting '"+this.getName()+"' completed in "+elapsed/1000+" seconds");
				}
				System.out.println("  Array length: "+a.length+" ints.");
				System.out.println("  Total Passes: "+passes);
				System.out.println("  Avg passes/sec: "+passes/((elapsed)/1000));
			} else {
				System.out.println("  0 passes or no time :C, the array was sorted so quickly it couldn't be measured!");
				System.out.println("    - Try passing a larger array argument like '-a 100'");
			}
			System.exit(0);
		} else {
			while(!isSorted(a)&&!solved){
				lock.lock();
				a = sortingPass(a);
				lock.unlock();
				passes++;
			}
			elapsed=(int) (System.currentTimeMillis()-start);
			if(passes!=0){
				if(bench) {
					System.out.println("Benchmark completed in "+elapsed/1000+" seconds");
				} else {
					System.out.println("Sorting '"+this.getName()+"' completed in "+elapsed/1000+" seconds");
				}
				System.out.println("  Array length: "+a.length+" ints.");
				System.out.println("  Total Passes: "+passes);
				System.out.println("  Avg passes/sec: "+passes/((elapsed)/1000));
			} else {
				System.out.println("  0 passes :C, the array was sorted so quickly it couldn't be measured!");
				System.out.println("    - Try passing a larger array argument like '-a 100'");
			}
			System.exit(0);
		}
	}
	
	/**
	 * An simplified sorting method created for multi-threaded sorting where possible
	 */
	private final void parallelSort(){
		while(!started){}
		int pass = 0;
		while(!isSorted(a)&&!solved){
			lock.lock();
			a = sortingPass(a);
			lock.unlock();
			pass++;
			if(pass>10000){
				//to reduce some parallel overhead
				passes+=pass;
				lastPasses+=pass;
				pass = 0;
			}
		}
	}
	
	private final boolean isSorted(int[] a) {
		for(int i = 1; i < a.length;i++){
			if(!(a[i]>a[i-1])){
				return false;
			}
		}
		solved = true;
		return true;
	}
	
	public void enableParallel(){
		canParallel = true;
	}
	
	public void disableParallel(){
		canParallel = false;
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
