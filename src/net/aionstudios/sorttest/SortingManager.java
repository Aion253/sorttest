package net.aionstudios.sorttest;

import java.util.ArrayList;
import java.util.List;

public class SortingManager {
	
	private static List<SortingAlgorithm> alg = new ArrayList<SortingAlgorithm>();
	
	public static void addAlg(SortingAlgorithm add){
		for(SortingAlgorithm s : alg){
			if(s.getName()==add.getName()){
				return;
			}
		}
		alg.add(add);
	}
	
	public static List<SortingAlgorithm> getAlgs() {
		return alg;
	}

	public static void runAlgorithm(String san, String[] args){
		SortingAlgorithm sa = null;
		for(SortingAlgorithm s : alg){
			if(s.getName().equals(san)){
				sa = s;
				break;
			}
		}
		if(sa == null){
			System.out.println("NO SUCH ALGORITHM: '"+san+"'");
			return;
		} else {
			boolean change = false;
			boolean verbose = false;
			int size = 25;
			long passFreq = 2000;
			if(!args[1].equals("help")&&!args[1].equals("desc")&&!args[1].equals("?")){
				for(int i = 1; i < args.length;i++){
					if(args[i].equals("-a")){
						try {
							int i2 = Integer.parseInt(args[i+1]);
							size = i2;
							i++;
							change = true;
						} catch(NumberFormatException e){
							System.out.println("Argument following the -a tag must be a number!");
							System.out.println("Use the argument 'help' for help.");
							return;
						}
					} else if(args[i].equals("-f")){
						try {
							int i2 = Integer.parseInt(args[i+1]);
							passFreq = i2;
							i++;
							change = true;
						} catch(NumberFormatException e){
							System.out.println("Argument following the -f tag must be a number!");
							System.out.println("Use the argument 'help' for help.");
							return;
						}
					} else if (args[i].equals("-v")){
						verbose = true;
						change = true;
					} else if (!args[i].equals("")||!args[i].equals(null)){
						System.out.println("Invalid command arguments: '"+args[i]+"'");
						System.out.println("Use the argument 'help' for help.");
						return;
					}
				}
				long dumb = 1337420;
				if(size==dumb){
					System.out.println("lmao xddd fam u type the special numbere good job Xdddd ayy lmoe");
					return;
				}
				if(size<=1){
					System.out.println("Array size must be greater than 1!");
					return;
				}
				if(!change){
					System.out.println("No arguments provided!");
				}
				System.out.println("Starting Sort -   Algorithm: "+san+"   Array Size: "+size+"   Verbose: "+verbose+"   Frequency: "+passFreq);
				sa.sortCall(size, verbose, passFreq);
			} else {
				System.out.println("Desc for '"+san+"': "+sa.getDesc());
				return;
			}
		}
	}

}
