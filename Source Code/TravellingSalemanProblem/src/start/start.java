package start;

import aStar.AStarImpl;
import simmulatedAnnealing.SimulatedAnnealing;

public class start {
	static int noOfCities;
	static int[][] distance;
	public static void main(String[] args) {
		if(args.length<4) {
			System.out.println("Provide the arguments correctly example and details below");
			System.out.println("java start/start <SearchStrategy(SIM/SOPH)> "
					+ "<Cost Function(C1/C2/C3)> <Number of Cities-N Ex(10,30,60..)>"
					+ "<MEB>"
					+ "<Seed- For SOPH algorithm Optional Parameters Ex(0,20, ...)>");
			System.out.println("Ex1. For Simple-- java start/start SIM C1 10 200000");
			System.out.println("Ex2. For Sophisticated approach--"
					+ "java start/start SOPH C1 10 200000 0");
			System.exit(0);

		}
		noOfCities=Integer.parseInt(args[2]);
		String costFunc=args[1].toLowerCase();
		int MEB=Integer.parseInt(args[3]);
		switch (costFunc) {
		case "c1":
			getDistanceC1();
			break;
		case "c2":
			getDistanceC2();
			break;
		case "c3":
			getDistanceC3();
			break;
		default:
			System.out.println("Invalid cost function");
			System.exit(0);
		}

		if(args[0].equalsIgnoreCase("SIM")) {
			AStarImpl astr=new AStarImpl(noOfCities, MEB, distance);
		}else if(args[0].equalsIgnoreCase("SOPH")){

			if(args.length==5) {
				int seed=Integer.parseInt(args[4]);	
				SimulatedAnnealing sa=new SimulatedAnnealing(noOfCities,seed,MEB,distance);
			}else {
				System.out.println("5 arguments needed for Sophisticated approach example below");
				System.out.println("java start/start <SearchStrategy(SOPH)> "
						+ "<Cost Function(C1/C2/C3)> <Number of Cities-N Ex(10,30,60..)>"
						+ "<MEB>"
						+ "<Seed Ex(0,20, ...)>");
				System.out.println("Ex. For Sophisticated approach--"
						+ "java start/start SOPH C1 10 200000 0");
				System.exit(0);
			}
		}else {
			System.out.println("Invalid search strategy");
			System.exit(0);
		}
	}
	private static void getDistanceC1() {
		distance=new int[noOfCities][noOfCities];
		for(int city1=0;city1<noOfCities;city1++) {
			for(int city2=0;city2<noOfCities;city2++) {
				int dist=0;
				if(city1==city2) {
					dist=Integer.MAX_VALUE;
				}else if (city1<3 && city2<3) {
					dist=1;
				}else if (city1<3) {
					dist=200;
				}else if (city2<3) {
					dist=200;
				}else if (city1%7==city2%7) {
					dist=2;
				}else {
					dist=Math.abs(city1-city2)+3;
				}
				distance[city1][city2]=dist;
			}
		}
	}


	private static void getDistanceC2() {
		distance=new int[noOfCities][noOfCities];
		for(int city1=0;city1<noOfCities;city1++) {
			for(int city2=0;city2<noOfCities;city2++) {
				int dist=0;
				if(city1==city2) {
					dist=Integer.MAX_VALUE;
				}else if (city1+city2<10) {
					dist=Math.abs(city1-city2)+4;
				}else if ((city1+city2)%11==0) {
					dist=3;
				}else {
					dist=(int) (Math.pow((double)Math.abs(city1-city2),2)+10);
				}
				distance[city1][city2]=dist;
			}
		}
	}


	private static void getDistanceC3() {
		distance=new int[noOfCities][noOfCities];
		for(int city1=0;city1<noOfCities;city1++) {
			for(int city2=0;city2<noOfCities;city2++) {
				int dist=0;
				if(city1==city2) {
					dist=Integer.MAX_VALUE;
				}else {
					dist=(int) Math.pow(city1+city2,2);
				}
				distance[city1][city2]=dist;
			}
		}
	}


}
