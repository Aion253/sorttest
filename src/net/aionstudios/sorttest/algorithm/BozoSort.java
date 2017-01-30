package net.aionstudios.sorttest.algorithm;

import java.util.Random;

import net.aionstudios.sorttest.SortingAlgorithm;

public class BozoSort extends SortingAlgorithm {

	public BozoSort() {
		super("bozo", "Randomly switches 2 points in the array, mostly for stress testing.");
	}

	@Override
	public int[] sortingPass(int[] ia) {
		Random rand = new Random();
		int n = Math.round(rand.nextInt(ia.length));
		int n2 = Math.round(rand.nextInt(ia.length));
		int s1 = ia[n2];
		ia[n2] = ia[n];
		ia[n] = s1;
		return ia;
	}

}
