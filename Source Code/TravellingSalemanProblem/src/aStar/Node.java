package aStar;


import java.util.Arrays;

public class Node implements Comparable<Node>{
	public int cityNo;
	public int pathCost;
	public int costTillNode;
	public boolean explored[];
	public Node parent;
	public Node(int cityNo) {
		this.cityNo=cityNo;
	}
	public Node(int cityNo,int pathCost) {

		this.pathCost=pathCost;
		this.cityNo=cityNo;
	}

	public Node(int cityNo,int pathCost,int n,Node parent) {
		this.pathCost=pathCost;
		this.cityNo=cityNo;
		this.explored=new boolean[n];
		this.parent=parent;
	}
	public Node(int cityNo,int pathCost,boolean[] explored,Node parent,int costTillNode) {
		this.pathCost=pathCost;
		this.cityNo=cityNo;
		this.explored=explored;
		this.parent=parent;
		this.costTillNode=costTillNode;
	}

	@Override
	public int compareTo(Node o) {
		if(this.pathCost>o.pathCost) {
			return 1;
		}
		if(this.pathCost<o.pathCost) {
			return -1;
		}
		return 0;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return this.cityNo;
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		Node obj1=(Node) obj;
		return  this.cityNo==obj1.cityNo;
	}
	@Override
	public String toString() {
		return "Node [cityNo=" + cityNo + ", pathCost=" + pathCost + ", costTillNode=" + costTillNode + ", explored="
				+ Arrays.toString(explored) + ", parent=" + parent + "]";
	}

	


}
