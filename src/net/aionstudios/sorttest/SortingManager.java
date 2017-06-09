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
		if (san.equals("-b")) {
			san = "bozo";
			args = new String[2];
			args[0] = san;
			args[1] = "-b";
		}
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
			boolean parallel = false;
			boolean half = false;
			boolean timeout = false;
			boolean benchmark = false;
			int size = 25;
			long timeot = 0;
			int passFreq = 2000;
			if(!args[1].equals("help")&&!args[1].equals("desc")&&!args[1].equals("?")){
				for(int i = 1; i < args.length;i++){
					if(args[i].equals("-t")) {
						try {
							int i2 = Integer.parseInt(args[i+1]);
							timeot = i2;
							i++;
							timeout = true;
						} catch(NumberFormatException e){
							System.out.println("Argument following the -t tag must be a number!");
							System.out.println("Use the argument 'help' for help.");
							return;
						}
					} else if(args[i].equals("-a")){
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
					} else if (args[i].equals("-b")) {
						//placement prevents non-negotiable settings from changing.
						benchmark = true;
						timeout = true;
						System.out.println("Initializing a benchmark with default settings.");
						System.out.println("Benchmarks default to one core. Use -p to test on all cores if you haven't.");
						change = true;
					}else if(args[i].equals("-f")){
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
					} else if (args[i].equals("-p")){
						parallel = true;
						change = true;
					} else if (args[i].equals("-h")){
						half = true;
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
				if(benchmark) {
					System.out.println("Starting Benchmark Sort -   Algorithm: "+san+"   Array Size: "+1000+"   Verbose: "+verbose+"   Frequency: "+passFreq+"   Timeout: "+900000);
					sa.sortCall(25, true, passFreq, parallel, half, 900000, true);
				} else {
					System.out.println("Starting Sort -   Algorithm: "+san+"   Array Size: "+size+"   Verbose: "+verbose+"   Frequency: "+passFreq+"   Timeout: "+timeot);
					sa.sortCall(size, verbose, passFreq, parallel, half, timeot, false);
				}
			} else {
				System.out.println("Desc for '"+san+"': "+sa.getDesc());
				return;
			}
		}
	}

}
