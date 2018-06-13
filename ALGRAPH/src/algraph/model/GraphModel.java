package algraph.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class GraphModel {
	public final int MAX_NODES = 15;
	public final int MIN_NODES = 3;
	
	private static final int MAX_WEIGHT = 15;
	private static final int MIN_WEIGHT = -15;
	
	private int currentNumberNodes;
	/*
	 * currentNodes and freeSpots memorize the INDEX of elements in increasing order.
	 */
	public ArrayList<NodeModel> currentNodes = new ArrayList<NodeModel>();
	public ArrayList<NodeModel> freeSpots = new ArrayList<NodeModel>();
	public TreeMap<Integer,NodeModel> currentNodesMap = new TreeMap<Integer,NodeModel>();
	public TreeMap<Integer,NodeModel> freeSpotsMap = new TreeMap<Integer,NodeModel>();
	
	private Integer [][] adjMatrix = new Integer [MAX_NODES][MAX_NODES];
	
	//==============================================================================
	public GraphModel() {

	}
	
	
	/*
	 * numberNodes = nodes of the graph. between 3 and 15.
	 * random = if true call a graph constructor with random weights
	 * 				   else call a graph constructor with numberNodes nodes and no edges
	 */
	public GraphModel (int numberNodes, boolean random) throws Exception {
		if(random)
			randomGraph(numberNodes);
		else
			nodesGraph(numberNodes);
	};
	
	//==============================================================================
	
	public void randomGraph(int numberNodes) throws Exception{
		//check number of nodes
		if(numberNodes <= MAX_NODES && numberNodes >= MIN_NODES)
			this.currentNumberNodes = numberNodes;
		else 
			throw new Exception();
		
		//add elements to lists
		NodeModel tmp;
		for(int i = 0; i < this.currentNumberNodes; i++) {
			tmp = new NodeModel(i);
			this.currentNodes.add(tmp);
			this.currentNodesMap.put(i, tmp);
		}
		for(int j = this.currentNumberNodes; j < MAX_NODES; j++) {
			tmp = new NodeModel(j);
			this.freeSpots.add(tmp);
			this.freeSpotsMap.put(j, tmp);
		}
		
		
		//set random weights to edges. Loops are forbidden. 
		Random rand = new Random();
		double percentage0;
		for(int row = 0; row < this.currentNumberNodes; row++) {
			for(int col = 0; col < this.currentNumberNodes; col++) {
				percentage0 = Math.random();
				if(row == col || (percentage0 < 0.4 || percentage0 > 0.7)) this.adjMatrix[row][col] = 0;
				else {
					int weight = rand.nextInt(MAX_WEIGHT - MIN_WEIGHT) + MIN_WEIGHT;
					this.adjMatrix[row][col] = weight;
				}
			}
		}
	}
	
	public void nodesGraph(int numberNodes) throws Exception {
		//check number of nodes
		if(MAX_NODES >= numberNodes)
			this.currentNumberNodes = numberNodes;
		else 
			throw new Exception();
		
		//add elements to lists
		NodeModel tmp;
		for(int i = 0; i < this.currentNumberNodes; i++) {
			tmp = new NodeModel(i);
			this.currentNodes.add(tmp);
			this.currentNodesMap.put(i, tmp);
		}
		for(int j = this.currentNumberNodes; j < MAX_NODES; j++) {
			tmp = new NodeModel(j);
			this.freeSpots.add(tmp);
			this.freeSpotsMap.put(j, tmp);
		}
		
		//matrix[v][u] = 0, for all u,v. 
		for(int row = 0; row < this.currentNumberNodes; row++) {
			for(int col = 0; col < this.currentNumberNodes; col++)
					this.adjMatrix[row][col] = 0;
			}
	}

	
	/*
	 * @param edge = inserted edge
	 * Creates a edge from u to v weighted w.
	 * If edge already exists change the value to w.
	 * 
	 * EXEPTION:
	 * - u != v
	 * - MIN_WEIGHT < w < MAX_WEIGHT
	 */
	public void insertEdge(EdgeModel edge) throws Exception {
		if(edge.getStartNode() == edge.getEndNode()) throw new Exception();
		if(edge.getWeight() > MAX_WEIGHT || edge.getWeight() < MIN_WEIGHT) throw new Exception();
		if(this.freeSpots.contains(edge.getStartNode()) || this.freeSpots.contains(edge.getEndNode())) throw new Exception();
		if(this.freeSpotsMap.containsKey(edge.getStartNode().getIndex()) || this.freeSpotsMap.containsKey(edge.getEndNode().getIndex())) throw new Exception();
		
		this.adjMatrix[edge.getStartNode().getIndex()][edge.getEndNode().getIndex()] = edge.getWeight();
	}
	
	/*
	 * @param edge = deleted edge
	 * Delete a edge from u to v (matrix[u][v] = 0), if exists.
	 * 
	 * EXEPTION:
	 * - u != v
	 * - if u or v don't exist
	 */
	public void deleteEdge(EdgeModel edge) throws Exception {
		if(edge.getStartNode() == edge.getEndNode()) throw new Exception();
		if(this.adjMatrix[edge.getStartNode().getIndex()][edge.getEndNode().getIndex()] == null) throw new Exception();
		
		this.adjMatrix[edge.getStartNode().getIndex()][edge.getStartNode().getIndex()] = 0;
	}

	
	/*
	 * @param u = start node
	 * @param v = end node
	 * return the weight of u->v edge. null if it doesn't exits.
	 */
	public Integer getEdge(int u, int v) {
		return this.adjMatrix[u][v];
	}


	public NodeModel insertNode() throws Exception {
		NodeModel newNode;
		if(this.currentNumberNodes == MAX_NODES)
			throw new Exception();
		else {
			this.currentNumberNodes++;

			Integer key = this.freeSpotsMap.firstKey();
			newNode = new NodeModel(key);
			this.freeSpotsMap.remove(key);
			this.currentNodesMap.put(key, newNode);
			
			for(int i = 0; i < MAX_NODES; i++) {
				this.adjMatrix[newNode.getIndex()][i] = 0;
				this.adjMatrix[i][newNode.getIndex()] = 0;
			}
		}
		return newNode;
	}

	
	/*
	 * @param node = index of the node to delete.
	 * Delete all the edges to and from node.
	 */
	public void deleteNode(NodeModel node) throws Exception {
		if(this.currentNumberNodes == MIN_NODES) throw new Exception();
		if(this.freeSpots.contains(node)) throw new Exception();
		else {
			this.currentNumberNodes--;
			
			this.currentNodesMap.remove(node.getIndex());
			this.freeSpotsMap.put(node.getIndex(), node);
			
			//remove all the edged from and to node
			for(int i = 0; i < MAX_NODES; i++)
				this.adjMatrix[node.getIndex()][i] = null;
			for(int j = 0; j < MAX_NODES; j++)
				this.adjMatrix[j][node.getIndex()] = null;
		}
	}
	
	/*
	 * @param u = startNode
	 * @param v = endNode
	 * @return true iff exists edge from u to v
	 */
	public boolean areConnected(int u, int v) {
		if(this.adjMatrix[u][v] == 0 || 
		this.adjMatrix[u][v ] == null) return false;
		
		else return true;
	}

	public int getCurrentNumberNodes() {
		return this.currentNumberNodes;
	}
	
	//====================================================================================
	
	public Iterator<NodeModel> getCurrentNodes(){
		return this.currentNodes.iterator();
	}
	
	public Iterator<NodeModel> getFreeSpots(){
		return this.freeSpots.iterator();
	}
	
	public String getCurrentNodesString() {
		String s = "";
		
		for(Map.Entry<Integer, NodeModel> node : this.currentNodesMap.entrySet()) {
			s +=  node.getValue().getIndex() + "\t";
//			s +=  node.getValue().getLabel() + "\t";
		}
		return s;
	}
	
	public String getFreeSpotsString() {
		String s = "";
		
		for(Map.Entry<Integer, NodeModel> node : this.freeSpotsMap.entrySet()) {
			s +=  node.getValue().getIndex() + "\t";
//			s +=  node.getValue().getLabel() + "\t";
		}
		return s;
	}
	
	
	
	/* @param node = selected node
	 * return the adjacency of node
	 * ES. node 1 = {2,w = 1}{3,w = 8}{4,w = -3}{6,w = 4}{7,w = 5}
	 */
 	public String textAdjNode(NodeModel node) {
		String adj;
		adj = "Node " + node.getLabel() + " = ";
		for(int j = 0; j<MAX_NODES; j++) {
			if(this.adjMatrix[node.getIndex()][j] != null)
				adj += "{" + j + ",w = " + this.adjMatrix[node.getIndex()][j] + "}";
		}
		return adj;
	}

	/*
	 * /* @param node = selected node
	 * return the adjacency of node
	 * ES. node 1 = {2,w = 1}{3,w = 8}{4,w = -3}{6,w = 4}{7,w = 5}
	 */
	public String matrixAdjNode(NodeModel node) {
		String adj;
		adj = "Node " + node.getIndex() +"\t" +"| ";
		for(int i = 0; i<MAX_NODES; i++) {
			if(this.adjMatrix[node.getIndex()][i] == null)
				adj += "\t"; 
			else
				adj += this.adjMatrix[node.getIndex()][i] + "\t";
		}
		return adj;
	}
	
	
	/*
	 * Print the adjacency list of the existing nodes
	 */
	public void printAdj() {
		System.out.println("Current Nodes: " + this.getCurrentNodesString());
		System.out.println("Free Spots   : " + this.getFreeSpotsString());
		for(NodeModel index : currentNodes)
			System.out.println(textAdjNode(index));
	};
	
	public void printMatrix() {
		System.out.println("Current Nodes: " + this.getCurrentNodesString());
		System.out.println("Free Spots   : " + this.getFreeSpotsString());
		for(Map.Entry<Integer, NodeModel> node : this.currentNodesMap.entrySet()) {
			System.out.println(matrixAdjNode(node.getValue()));
		}
			
	};
	
	
}
