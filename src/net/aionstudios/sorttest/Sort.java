package net.aionstudios.sorttest;

import net.aionstudios.sorttest.algorithm.BozoSort;

public class Sort {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		BozoSort bs = new BozoSort();
		if(args.length > 0){
			for(int i = 0; i < args.length;i++){
				args[i] = args[i].toLowerCase();
			}
			SortingManager.runAlgorithm(args[0], args);
		} else {
			printHelp();
		}
	}
	
	public static final void printHelp(){
		System.out.println("Aion Studios Sort Test help:");
		System.out.println("  - 'help' or '?' for Help.");
		System.out.println("  Sorting Algorithms Available:");
		for(SortingAlgorithm sa : SortingManager.getAlgs()){
			System.out.println("    - '"+sa.getName()+"' - "+sa.getDesc());
		}
		System.out.println("  Algorithm Arguments:");
		System.out.println("    - '-a' - Specifies the size of the array.");
		System.out.println("    - '-v' - Specifies the sort be verbose.");
		System.out.println("    - '-f' - How often (milliseconds) to print verbose information.");
	}

}
