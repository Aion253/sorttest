package net.aionstudios.sorttest.algorithm;

import java.util.Arrays;

import net.aionstudios.sorttest.SortingAlgorithm;

public class Bubble extends SortingAlgorithm {
	
	private int last = 0;
	private boolean up = true;

	public Bubble() {
		super("bubble", "Swaps the number if the latter is of grater value than the former.");
		enableParallel();
	}

	@Override
	public int[] sortingPass(int[] ia) {
		if(up){
			if(!(last>=ia.length)){
				if(ia[last+1] <= ia[last]){
					int t = ia[last];
					ia[last] = ia[last+1];
					ia[last+1] = t;
				}
			}
			last++;
			if(last>=ia.length-1){
				up = false;
			}
		} else {
			if(!(last<=0)){
				if(ia[last-1] >= ia[last]){
					int t = ia[last];
					ia[last] = ia[last-1];
					ia[last-1] = t;
				}
			}
			last--;
			if(last<=0){
				up = true;
			}
		}
		return ia;
	}
	
}
