package algraph.model;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JOptionPane;

import java.util.Random;

public class GraphModel {
	public final int MAX_NODES = 15;
	public final int MIN_NODES = 3;
	
	private static final int MAX_WEIGHT = 30;
	private static final int MIN_WEIGHT = -30;
	
	private int currentNumberNodes;
	public TreeMap<Integer,NodeModel> currentNodesMap = new TreeMap<Integer,NodeModel>();
	public TreeMap<Integer,NodeModel> freeSpotsMap = new TreeMap<Integer,NodeModel>();
	
	private Integer [][] adjMatrix = new Integer [MAX_NODES][MAX_NODES];
	
	//nodi isolati
	public ArrayList<NodeModel> noLinkedNode = new ArrayList<NodeModel>();
	
	
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
			this.currentNodesMap.put(i, tmp);
		}
		for(int j = this.currentNumberNodes; j < MAX_NODES; j++) {
			tmp = new NodeModel(j);
			this.freeSpotsMap.put(j, tmp);
		}
		
		
		//set random weights to edges. Loops are forbidden. 
		Random rand = new Random();
		double percentage0;
		for(int row = 0; row < this.currentNumberNodes; row++) {
			for(int col = 0; col < this.currentNumberNodes; col++) {
				percentage0 = Math.random();
				if(row == col || (percentage0 < 0.4 || percentage0 > 0.7)) {
					this.adjMatrix[row][col] = 0;
					this.adjMatrix[col][row] = 0;
				}
				else {
					int weight = rand.nextInt(MAX_WEIGHT - MIN_WEIGHT) + MIN_WEIGHT;
					this.adjMatrix[row][col] = weight;
					this.adjMatrix[col][row] = weight;
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
			this.currentNodesMap.put(i, tmp);
		}
		for(int j = this.currentNumberNodes; j < MAX_NODES; j++) {
			tmp = new NodeModel(j);
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
		if(edge.getStartNode().getLabel() == edge.getEndNode().getLabel()) {
			throw new Exception();
		} else {
			this.adjMatrix[edge.getStartNode().getIndex()][edge.getEndNode().getIndex()] = edge.getWeight();
			this.adjMatrix[edge.getEndNode().getIndex()][edge.getStartNode().getIndex()] = edge.getWeight();
		}
	}
	
	/*
	 * @param edge = deleted edge
	 * Delete a edge from u to v (matrix[u][v] = 0), if exists.
	 * 
	 * EXEPTION:
	 * - u != v
	 * - if u or v don't exist
	 */
	public void deleteEdge(EdgeModel edge) {		
		this.adjMatrix[edge.getStartNode().getIndex()][edge.getEndNode().getIndex()] = 0;
		this.adjMatrix[edge.getEndNode().getIndex()][edge.getStartNode().getIndex()] = 0;
	}

	public Integer[][] getMatrix(){
		return this.adjMatrix;
	}
	
	
	/*
	 * @param u = start node
	 * @param v = end node
	 * return the weight of u->v edge. null if it doesn't exits.
	 */
	public Integer getWeight(int u, int v) {
		return this.adjMatrix[u][v];
	}
	
	public Integer getWeight(NodeModel u,NodeModel v) {
		return this.adjMatrix[u.getIndex()][v.getIndex()];
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
			
			for(int i = 0; i < this.currentNumberNodes; i++) {
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
		if(this.freeSpotsMap.containsKey(node.getIndex())) throw new Exception();
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
		boolean areConnected = true;		
		if(this.adjMatrix[u][v] == 0 || this.adjMatrix[u][v] == null)
			areConnected = false;
		return areConnected;
	}

	public boolean areConnected(NodeModel u, NodeModel v) {
		boolean areConnected = true;		
		if(u == null || v == null || u == v || this.adjMatrix[u.getIndex()][v.getIndex()] == 0 || this.adjMatrix[u.getIndex()][v.getIndex()] == null)
			areConnected = false;
		
		return areConnected;
	}

	public int getCurrentNumberNodes() {
		return this.currentNumberNodes;
	}
	
	//====================================================================================	
	public String getCurrentNodesString() {
		String s = "";
		
		for(Map.Entry<Integer, NodeModel> node : this.currentNodesMap.entrySet()) {
			s +=  node.getValue().getLabel() + "\t";
		}
		return s;
	}
	
	public String getFreeSpotsString() {
		String s = "";
		
		for(Map.Entry<Integer, NodeModel> node : this.freeSpotsMap.entrySet()) {
			s +=  node.getValue().getLabel() + "\t";
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
	 * ES. 
	 */
	public String matrixAdjNode(NodeModel node) {
		String adj;
		adj = "Node " + node.getLabel() +"\t" +"| ";
		for(int i = 0; i<MAX_NODES; i++) {
			if(this.adjMatrix[node.getIndex()][i] != null)
				adj += "\t" + this.adjMatrix[node.getIndex()][i] + "\t|";
		}
		return adj;
	}
	
	
	/*
	 * Print the adjacency list of the existing nodes
	 */
	public void printAdj() {
		System.out.println("Current Nodes: " + this.getCurrentNodesString());
		System.out.println("Free Spots   : " + this.getFreeSpotsString());
		for(Map.Entry<Integer, NodeModel> node : this.currentNodesMap.entrySet()) {
			System.out.println(textAdjNode(node.getValue()));
		}
	};
	
	public StringBuilder printMatrix() {
		StringBuilder s = new StringBuilder();

		String n="\t\t";
		for(Map.Entry<Integer, NodeModel> node : this.currentNodesMap.entrySet()) {
			n +="| Node " +  node.getValue().getLabel() + "\t";
		}
		
		n+='|';
		s.append("Current Nodes: " + this.getCurrentNodesString());
		s.append('\n');
		s.append("Free Spots   : " + this.getFreeSpotsString());
		s.append("\n\n");
		s.append(n);
		s.append("\n");
		n="";
		
		for(int i = 0; i < this.currentNodesMap.size(); i++)
			n +="____________";
		
		s.append(n);
		s.append("\n");
		for(Map.Entry<Integer, NodeModel> node : this.currentNodesMap.entrySet()) {
			s.append(matrixAdjNode(node.getValue()));
			s.append('\n');
		}
		return s;
	}
	
	public boolean isConnected(int n) {
		int size = this.currentNodesMap.size();
		int visited[] = new int[size];
		int nodeNc[] = new int[size];//contiene i nodi non connessi
		
		for(int row=0; row < size; row++) {
			for(int col=0; col < size; col++){
				if(this.adjMatrix[row][col]!=0 && visited[row]==0) {
					visited[row] = 1;
					//System.out.println("new visit");
				}
			}
		}
		//visited contiene 0 oppure 1
		//se contiene 0 in posizione i significa che il nodo i non ha archi uscenti
		//se contiene 1 in posizione i significa che il nodo i ha archi uscenti (almeno uno, ma può averne più di uno)

		boolean connected = false;

		for (int vertex = 1; vertex < size; vertex++){
			if (visited[vertex] == 1) connected = true;
			else{
				nodeNc[vertex]=vertex;
				connected = false;
			}
		}
		
		//dobbiamo fare il caso in cui un nodo contenga solo un arco uscente.
		//in questo caso dobbiamo far partire l'algoritmo da questo nodo.
		boolean uscenti[]=new boolean[size];
		if(connected){
			//dobbiamo vedere se c'è qualche nodo con soli archi uscenti
			for(int row=0; row < size; row++) {
				
				uscenti[row]=true;
				for(int col=0; col < size; col++){
					if(this.adjMatrix[col][row]==0){
						uscenti[row]=uscenti[row] && true;
					}else{
						uscenti[row]=uscenti[row] && false;
					}
					if(this.adjMatrix[row][col]!=0 && visited[row]==0) {
						visited[row] = 1;
						//System.out.println("new visit");
					}
				}
			}
		}
		String allExitNode="";
		String noLinkedNode="";
		for(Integer i=0; i<size; i++){
			if(uscenti[i]){
				allExitNode +=' '+i.toString()+',';
			}
			if(nodeNc[i]!=0){
				NodeModel tmp = new NodeModel(i);
				this.noLinkedNode.add(tmp);
				noLinkedNode +=' '+i.toString()+',';
			}
		}
		if(allExitNode!="")JOptionPane.showMessageDialog(null,"Attenzione: devi partire necessariamente dal nodo" + allExitNode);
		if(noLinkedNode!="")JOptionPane.showMessageDialog(null,"Attenzione: NON devi partire dal nodo"+noLinkedNode);

		return (connected);
	}
	
	
	public void setAdjMatrix(Integer x[][]){
		this.adjMatrix=x;
	}
			
}
	
	
