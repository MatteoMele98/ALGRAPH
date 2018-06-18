package algraph.view;

import algraph.model.EdgeModel;
import algraph.model.NodeModel;
import algraph.utils.*;

public class GraphView {
	public final int MAX_NODES = 15;
	private static final int RADIUS = 25;
	
	private int currentNumberNodes = 0;
	private int visibleNodes = 0;
	public NodeView nodes[];
	public EdgeView edge[][]; 
	
	
	public GraphView() {

	}
	
	/*
	 * Update the nodes coordinates based on the visibleNodes.
	 * Update edge based on the new nodes coordinates.
	 */
	private void coordinates() {
		//update points coordinates
		for(int i = 0; i < MAX_NODES && this.nodes[i].isVisible; i++) {
			Point newCoord = new Point(550+250*Math.cos(Math.PI*2*i/this.visibleNodes), 300-250*Math.sin(Math.PI*2*i/this.visibleNodes));
			this.nodes[i].setPosition(newCoord);
		}
		//update edges
		for(int i = 0; i < MAX_NODES; i++) {
			for(int j = 0; j < MAX_NODES; j++) {
				if(this.getEdge(i, j) != null) {
					this.insertEdge(i,j);
					this.insertEdge(j,i);
				}
			}
		}
	}
	
	
	public GraphView (int numberNodes) {
		this.nodes = new NodeView[MAX_NODES];
		this.currentNumberNodes = numberNodes;
		this.visibleNodes = numberNodes;
		
		//crea grafo
		for(int i = 0; i<MAX_NODES; i++) {
			if(i < numberNodes) {
				Point coordinates = new Point(550+250*Math.cos(Math.PI*2*i/numberNodes), 300-250*Math.sin(Math.PI*2*i/numberNodes));
				NodeModel tmp = new NodeModel(i);
				this.nodes[i] = new NodeView(tmp, coordinates);
			}
			else
				this.nodes[i] = new NodeView();
		}
		
		this.edge = new EdgeView[MAX_NODES][MAX_NODES];
		for(int i=0; i<MAX_NODES; i++) {
			for(int z=0; z<MAX_NODES; z++) {
				this.edge[i][z] = null;
			}
				
			
		}
	}
	
	
	
	/*
	 * @param node = inserted node
	 */		
	public void insertNode(NodeModel node) {
		this.currentNumberNodes++;
		this.visibleNodes++;
		Point center = new Point(550+250*Math.cos(Math.PI*2*node.getIndex()/this.currentNumberNodes), 300-250*Math.sin(Math.PI*2*node.getIndex()/this.currentNumberNodes));
		this.nodes[node.getIndex()] = new NodeView(node, center);
		this.coordinates();
	}
	
	/*
	 * @param node = deleted node
	 */
	public void deleteNode(NodeModel node) {
		this.currentNumberNodes--;
		this.visibleNodes--;
		this.nodes[node.getIndex()].setIsVisible(false);
	}
	
	/*
	 * @param edge = inserted edge
	 */
	public void insertEdge(EdgeModel edge) {
		if(edge.getStartNode() != edge.getEndNode()) {
			Point centerStart = new Point(this.nodes[edge.getStartNode().getIndex()].getPosX(), this.nodes[edge.getStartNode().getIndex()].getPosY());
			Point centerEnd = new Point(this.nodes[edge.getEndNode().getIndex()].getPosX(), this.nodes[edge.getEndNode().getIndex()].getPosY());
			
			this.edge[edge.getStartNode().getIndex()][edge.getEndNode().getIndex()] = new EdgeView(centerStart,centerEnd,RADIUS);		
			this.edge[edge.getEndNode().getIndex()][edge.getStartNode().getIndex()] = new EdgeView(centerEnd,centerStart,RADIUS);		
		}
		
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
		this.edge[end][start] = new EdgeView(centerEnd,centerStart,RADIUS);
	}
	
	public void deleteEdge(EdgeModel edge) {
		this.edge[edge.getStartNode().getIndex()][edge.getEndNode().getIndex()] = null;	
		this.edge[edge.getEndNode().getIndex()][edge.getStartNode().getIndex()] = null;	
	}	
	
	public void deleteEdge(int start, int end) {
		this.edge[start][end] = null;	
		this.edge[end][start] = null;
	}
	
	public NodeView getNode(int n) {
		return this.nodes[n];
	}
	
	public NodeView getNode(NodeModel u) {
		return this.nodes[u.getIndex()];
	}
	
	public EdgeView getEdge(int u, int v) {
		return this.edge[u][v];
	}
	
	public EdgeView getEdge(NodeModel u, NodeModel v) {
		if(this.edge[u.getIndex()][v.getIndex()] != null) 
			return this.edge[u.getIndex()][v.getIndex()];
		else return null;		
	}
}
