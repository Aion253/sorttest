package net.aionstudios.sorttest.algorithm;

import java.util.concurrent.ThreadLocalRandom;

import net.aionstudios.sorttest.SortingAlgorithm;

public class BozoSort extends SortingAlgorithm {
	
	public BozoSort() {
		super("bozo", "Randomly switches 2 points in the array, mostly for stress testing.");
		enableParallel();
	}

	@Override
	public int[] sortingPass(int[] ia) {
		int n = Math.round(ThreadLocalRandom.current().nextInt(ia.length));
		int n2 = Math.round(ThreadLocalRandom.current().nextInt(ia.length));
		int s1 = ia[n2];
		ia[n2] = ia[n];
		ia[n] = s1;
		return ia;
	}

}
