package algraph.view;

import algraph.utils.*;

public class GraphView {
	private static final int  MAX_NODES = 15;
	private static final int  MIN_NODES = 3;
	private static final int RADIUS = 25;
	
	private int currentNumberNodes = 0;
	private NodeView nodes[]; //array dei nodi
	private EdgeView edge[][]; //matrice archi
	
	/*
	 * n = number nodes
	 */
	public GraphView (int n) {
		this.nodes = new NodeView[MAX_NODES];
		this.currentNumberNodes = n;
		
		//crea grafo
		for(int i = 0; i<MAX_NODES; i++) {
			if(i < n) {
				Point coordinates = new Point(550+250*Math.cos(Math.PI*2*i/n), 300-250*Math.sin(Math.PI*2*i/n));
				this.nodes[i] = new NodeView(i, coordinates);
			}
			else
				this.nodes[i] = new NodeView();
		}
	
		this.edge = new EdgeView[MAX_NODES][MAX_NODES];
		for(int i=0; i<MAX_NODES; i++) {
			for(int z=0; z<MAX_NODES; z++) 
				this.edge[i][z] = new EdgeView();
		}
	}
	
	
	/*
	 * labeal is text inside the node
	 */		
	public void insertNode(int position, int label) {
		this.currentNumberNodes++;
		Point coordinates = new Point(550+250*Math.cos(Math.PI*2*position/this.currentNumberNodes), 300-250*Math.sin(Math.PI*2*position/this.currentNumberNodes));
		this.nodes[position] = new NodeView(position, coordinates);
	}
	
	public void deleteNode(int position) {
		this.currentNumberNodes--;
		this.nodes[position] = null;
	}
	
	/*
	 * start = start node
	 * end = end node
	 * graph[start][end] = edge
	 */
	public void insertEdge(int start, int end) {
		Point centerStart = new Point(this.nodes[start].getPosX(), this.nodes[start].getPosY());
		Point centerEnd = new Point(this.nodes[end].getPosX(), this.nodes[end].getPosY());
		
		this.edge[start][end] = new EdgeView(centerStart,centerEnd,RADIUS);			
	}
	
	public void deleteEdge(int start, int end) {
		this.edge[start][end] = null;			
	}	
	
	public NodeView getNode(int n) {
		return this.nodes[n];
	}
	
	
	public EdgeView getEdge(int n,int m) {
		return this.edge[n][m];
	}
}
