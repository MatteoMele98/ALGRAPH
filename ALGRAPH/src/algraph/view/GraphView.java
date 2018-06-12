package algraph.view;

import algraph.model.EdgeModel;
import algraph.model.NodeModel;
import algraph.utils.*;

public class GraphView {
	public final int MAX_NODES = 15;
	private static final int RADIUS = 25;
	
	private int currentNumberNodes = 0;
	public NodeView nodes[];
	public EdgeView edge[][]; 
	
	
	public GraphView() {

	}
	
	
	
	public GraphView (int numberNodes) {
		this.nodes = new NodeView[MAX_NODES];
		this.currentNumberNodes = numberNodes;
		
		//crea grafo
		for(int i = 0; i<MAX_NODES; i++) {
			if(i < numberNodes) {
				Point coordinates = new Point(550+250*Math.cos(Math.PI*2*i/numberNodes), 300-250*Math.sin(Math.PI*2*i/numberNodes));
				NodeModel tmp = new NodeModel(i);
				this.nodes[i] = new NodeView(tmp, coordinates);
			}
			else
				this.nodes[i] = null;
		}
	
		this.edge = new EdgeView[MAX_NODES][MAX_NODES];
		for(int i=0; i<MAX_NODES; i++) {
			for(int z=0; z<MAX_NODES; z++) 
				this.edge[i][z] = new EdgeView();
		}
	}
	
	
	
	/*
	 * @param node = inserted node
	 */		
	public void insertNode(NodeModel node) {
		this.currentNumberNodes++;
		Point center = new Point(550+250*Math.cos(Math.PI*2*node.getIndex()/this.currentNumberNodes), 300-250*Math.sin(Math.PI*2*node.getIndex()/this.currentNumberNodes));
		this.nodes[node.getIndex()] = new NodeView(node, center);
	}
	
	/*
	 * @param node = deleted node
	 */
	public void deleteNode(NodeModel node) {
		this.currentNumberNodes--;
		this.nodes[node.getIndex()] = null;
	}
	
	/*
	 * @param edge = inserted edge
	 */
	public void insertEdge(EdgeModel edge) {
		Point centerStart = new Point(this.nodes[edge.getStartNode().getIndex()].getPosX(), this.nodes[edge.getStartNode().getIndex()].getPosY());
		Point centerEnd = new Point(this.nodes[edge.getEndNode().getIndex()].getPosX(), this.nodes[edge.getEndNode().getIndex()].getPosY());
		
		this.edge[edge.getStartNode().getIndex()][edge.getEndNode().getIndex()] = new EdgeView(centerStart,centerEnd,RADIUS);			
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
	
	public void deleteEdge(EdgeModel edge) {
		this.edge[edge.getStartNode().getIndex()][edge.getEndNode().getIndex()] = null;			
	}	
	
	public void deleteEdge(int start, int end) {
		this.edge[start][end] = null;	
	}
	
	public NodeView getNode(int n) {
		return this.nodes[n];
	}
	
	
	public EdgeView getEdge(int n, int m) {
		return this.edge[n][m];
	}
}
