package simmulatedAnnealing;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class SimulatedAnnealing {
	//	String outputFileName="../2runs/2runs.txt";
	static int noOfCities;
	static Random randomno;
	static int distance[][];
	@SuppressWarnings("unchecked")
	public SimulatedAnnealing(int noOfCities,int seed,int MEBBound,int distance[][]) {
		int MEB=1;
		this.distance=distance;
		this.noOfCities=noOfCities;
		double temp =1000;
		double coolingRate = 0.00003;
		long startTime = System.nanoTime();
		randomno=new Random(seed);
		ArrayList<Integer> path=new ArrayList<>();
		for(int city=0;city<noOfCities;city++) {
			path.add(city);
		}
		Collections.shuffle(path, randomno);
		ArrayList<Integer> bestPath=(ArrayList<Integer>) path.clone();
		int bestPathCost=getTotalCost(path);
		while(temp>0.0001) {
			MEB++;
			ArrayList<Integer> nextPath=(ArrayList<Integer>) path.clone();
			int p1 = (int) (this.noOfCities* randomno.nextDouble());
			int p2 = (int) (this.noOfCities* randomno.nextDouble());
			int city1=path.get(p1);
			int city2=path.get(p2);
			nextPath.set(p1, city2);
			nextPath.set(p2, city1);
			int pathCost=getTotalCost(path);
			int nextPathCost=getTotalCost(nextPath);
			if(nextPathCost<pathCost) {
				path=nextPath;
			}else if(Math.exp((pathCost - nextPathCost) / temp) > randomno.nextDouble()) {
				path=nextPath;
			}
			if(bestPathCost>getTotalCost(path)) {
				bestPath=path;
				bestPathCost=getTotalCost(path);
			}
			temp *= 1-coolingRate;
			if(MEB==MEBBound) {
				break;
			}
		}
		System.out.println("TSP by Simulated Annealing");
		getPath(bestPath);
		System.out.println("\nTotal Cost-"+getTotalCost(bestPath));
		long endTime = System.nanoTime();
		long elapsedTimeInMillis = TimeUnit.MILLISECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS);
		System.out.println("Total elapsed time: " +(elapsedTimeInMillis / 1000)  / 60  + " min "+ (elapsedTimeInMillis / 1000) % 60 +" sec");
		System.out.println("MEB-"+MEB);



		//		FileWriter fileWriter;
		//		try {
		//			fileWriter = new FileWriter(new File(outputFileName),true);
		//			fileWriter.write("\nTSP by Simulated Annealing\n");
		//			int i=0;
		//			for(i=0;i<noOfCities-1;i++) {
		//				fileWriter.write(path.get(i)+"-->");
		//			}
		//			fileWriter.write(path.get(i)+"-->"+path.get(0));
		//			fileWriter.write("\nTotal Cost-"+getTotalCost(bestPath));
		//			long endTime = System.nanoTime();
		//			long elapsedTimeInMillis = TimeUnit.MILLISECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS);
		//			//		System.out.println("Total elapsed time: " +(elapsedTimeInMillis / 1000)  / 60  + " min "+ (elapsedTimeInMillis / 1000) % 60 +" sec");
		//			fileWriter.write("\nTotal elapsed time: " +(elapsedTimeInMillis / 1000)  / 60  + " min "+ (elapsedTimeInMillis / 1000) % 60 +" sec");
		//
		//			fileWriter.write("\nMEB-"+MEB);
		//			
		//			fileWriter.close();
		//		} catch (IOException e) {
		//						e.printStackTrace();
		//		}
		//		System.out.println("Done");

	}
	void getPath(ArrayList<Integer> path){
		int i=0;
		for (i=0;i<noOfCities;i++) {
			System.out.print(path.get(i)+"->");
		}
			System.out.print(path.get(0));
		}
	static int getTotalCost(ArrayList<Integer> path) {
		int cost=0;
		int i=0;
		for(i=0;i<noOfCities-1;i++) {
			cost+=distance[path.get(i)][path.get(i+1)];
		}
		cost+=distance[path.get(i)][path.get(0)];
		return cost;
	}
}
