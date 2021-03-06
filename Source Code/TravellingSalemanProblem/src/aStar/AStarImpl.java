package aStar;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

public class AStarImpl {
	static int noOfCities;
	static int distance[][];
	public AStarImpl(int noOfCities,int MEBBound,int distance[][]) {
		//		String outputFileName="../2runs/2runs.txt";
		this.noOfCities=noOfCities;
		this.distance=distance;
		long startTime = System.nanoTime();
		int[] pathCost=new int[noOfCities];
		Arrays.fill(pathCost, Integer.MAX_VALUE);
		boolean explored[]=new boolean[noOfCities];
		int start=0;
		PriorityQueue<Node> pq=new PriorityQueue<>();
		Node startNode=new Node(start, 0,explored,null,0);
		pq.add(startNode);
		int MEB=1;
		Node currentNode=null;
		while(!pq.isEmpty()) {
			currentNode=pq.poll();
			pathCost[currentNode.cityNo]=Integer.MAX_VALUE;
			currentNode.explored[currentNode.cityNo]=true;
			if(goalStateReached(currentNode.explored,noOfCities)) {
				break;
			}
			for(int neighbour=0;neighbour<noOfCities;neighbour++) {
				if(neighbour!=currentNode.cityNo && currentNode.explored[neighbour]==false) {
					boolean[] tempExplored=currentNode.explored.clone();
					boolean tempExploredStatus=tempExplored[neighbour];
					tempExplored[neighbour]=true;
					int getFn=currentNode.costTillNode+distance[currentNode.cityNo][neighbour] 
							+getPathCost(tempExplored,neighbour,noOfCities,start);
					if(tempExploredStatus==false && !pq.contains(new Node(neighbour))) {
						pq.add(new Node(neighbour,getFn,tempExplored,currentNode,currentNode.costTillNode+distance[currentNode.cityNo][neighbour]));
						MEB++;
						pathCost[neighbour]=getFn;
					}else if(pq.contains(new Node(neighbour))) {
						if(pathCost[neighbour]>getFn) {
							pq.remove(new Node(neighbour));
							pq.add(new Node(neighbour,getFn,tempExplored,currentNode,currentNode.costTillNode+distance[currentNode.cityNo][neighbour]));
							MEB++;
							pathCost[neighbour]=getFn;						}
					}
					tempExplored[neighbour]=tempExploredStatus;
				}
			}
		}
		int cost=0;
		int t=start;
		System.out.print(t);
		Node temp=currentNode;
		while(temp!=null) {
			System.out.print("->");
			cost+=distance[t][temp.cityNo];
			System.out.print(temp.cityNo);
			t=temp.cityNo;
			temp=temp.parent;
		}
		System.out.println("\nTotal Cost-"+cost);
		long endTime = System.nanoTime();
		long elapsedTimeInMillis = TimeUnit.MILLISECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS);
		System.out.println("Total elapsed time: " +(elapsedTimeInMillis / 1000)  / 60  + " min "+ (elapsedTimeInMillis / 1000) % 60 +" sec");
		if(MEB>MEBBound) {
			System.out.println("MEB Exceeded- Current MEB"+MEB);
		}else {
			System.out.println("MEB-"+MEB);
		}


		//		try {
		//			FileWriter fileWriter = new FileWriter(new File(outputFileName));
		//			fileWriter.write("TSP by ASTAR");
		//			//		System.out.println("Path for TSP");
		//			//		System.out.print(t);
		//			fileWriter.write("\n"+t);
		//			Node temp=currentNode;
		//			while(temp!=null) {
		//				//			System.out.print("->");
		//				fileWriter.write("-->");
		//				cost+=distance[t][temp.cityNo];
		//				//			System.out.print(temp.cityNo);
		//				fileWriter.write(""+temp.cityNo);
		//				t=temp.cityNo;
		//				temp=temp.parent;
		//			}
		//
		//			//		System.out.println("\nTotal Cost-"+cost);
		//			fileWriter.write("\nTotal Cost-"+cost);
		//			long endTime = System.nanoTime();
		//			long elapsedTimeInMillis = TimeUnit.MILLISECONDS.convert((endTime - startTime), TimeUnit.NANOSECONDS);
		//			//		System.out.println("Total elapsed time: " +(elapsedTimeInMillis / 1000)  / 60  + " min "+ (elapsedTimeInMillis / 1000) % 60 +" sec");
		//			fileWriter.write("\nTotal elapsed time: " +(elapsedTimeInMillis / 1000)  / 60  + " min "+ (elapsedTimeInMillis / 1000) % 60 +" sec");
		//
		//			if(MEB>MEBBound) {
		//				//			System.out.println("MEB Exceeded- Current MEB"+MEB);
		//				fileWriter.write("\nMEB Exceeded- Current MEB"+MEB);
		//			}else {
		//				//			System.out.println("MEB-"+MEB);
		//				fileWriter.write("\nMEB-"+MEB);
		//			}
		//			fileWriter.close();
		//		} catch (IOException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}

		//		System.out.println("Done");
	}

	private static int getPathCost(boolean[] explored, int neighbour, int noOfCities,int start) {
		int estPathCostTillDest=0;
		int closestNeigh=nearestUnvisitedNeigh(neighbour,explored,noOfCities);
		int closestNeigbrDist=0;
		int closestNegbrToStartDist=nearestUnvFromStart(explored,noOfCities,start);
		if(closestNeigh!=Integer.MAX_VALUE) {
			closestNeigbrDist=distance[neighbour][closestNeigh];
		}
		estPathCostTillDest=mstUnvisited(explored,neighbour,noOfCities)+closestNeigbrDist+closestNegbrToStartDist;
		return estPathCostTillDest;
	}
	private static int nearestUnvFromStart(boolean[] explored, int noOfCities,int start) {
		int cost=Integer.MAX_VALUE;
		for (int i = 0; i < explored.length; i++) {
			if(explored[i]==false && cost>distance[start][i]) {
				cost=distance[start][i];
			}
		}
		if(cost==Integer.MAX_VALUE)
			return 0;
		else {
			return cost;
		}
	}
	private static int nearestUnvisitedNeigh(int neighbour, boolean[] explored, int noOfCities) {
		int nearDist=Integer.MAX_VALUE;
		int closestNeigh=Integer.MAX_VALUE;
		for(int i=0;i<noOfCities;i++) {
			if(explored[i]==false && distance[neighbour][i]<nearDist) {
				nearDist=distance[neighbour][i];
				closestNeigh=i;
			}
		}
		return closestNeigh;
	}
	private static int mstUnvisited(boolean[] explored, int neighbour, int noOfCities) {
		int cost=0;
		int[] parent=new int[noOfCities];
		int[] rank=new int[noOfCities];
		int allExplored=0;
		for(int i=0;i<noOfCities;i++) {
			if(explored[i]==false) {
				parent[i]=i;
				rank[i]=0;
			}else {
				allExplored++;
				parent[i]=-1;
				rank[i]=-1;
			}
		}
		if(allExplored==noOfCities)
			return 0;
		ArrayList<Edge> allEdges=new ArrayList<>();

		for(int i=0;i<noOfCities;i++) {
			for(int j=i+1;j<noOfCities;j++) {
				if(explored[i]==false && explored[j]==false) {
					Edge currEdge=new Edge(i,j,distance[i][j]);
					allEdges.add(currEdge);
				}
			}
		}
		LinkedList<Edge> res=new LinkedList<>();
		Collections.sort(allEdges);
		for (Edge edge : allEdges) {
			int root1 = find(edge.vertex1,parent); 
			int root2 = find(edge.vertex2,parent);
			if (root1 != root2) {
				res.push(edge);
				if (rank[root1] > rank[root2]) {
					parent[root2] = root1;
					rank[root1]++;
				} else {
					parent[root1] = root2;
					rank[root2]++;
				}
			}
		}
		for (Edge edge : res) {
			cost+=edge.weight;
		}
		return cost;
	}
	private static int find(int vertex, int[] parent) {
		if (parent[vertex] == vertex) 
			return parent[vertex];
		else
			return find(parent[vertex],parent);
	}
	private static boolean goalStateReached(boolean[] explored, int noOfCities) {
		for(int i=0;i<noOfCities;i++) {
			if(explored[i]==false)
				return false;
		}
		return true;
	}


}
