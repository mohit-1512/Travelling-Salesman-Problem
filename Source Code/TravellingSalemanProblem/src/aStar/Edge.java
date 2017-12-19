package aStar;

public class Edge implements Comparable<Edge>{
	int vertex1;
	int vertex2;
	int weight;
	public Edge(int vertex1, int vertex2, int weight) {
		super();
		this.vertex1 = vertex1;
		this.vertex2 = vertex2;
		this.weight = weight;
	}
	@Override
	public int compareTo(Edge o) {
		if(this.weight<o.weight) {
			return -1;
		}
		if(this.weight>o.weight) {
			return 1;
		}
		return 0;
	}
	
	
}
