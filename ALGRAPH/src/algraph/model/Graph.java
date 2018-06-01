package algraph.model;

import java.util.Random;
import java.util.Stack;

public class Graph implements GraphOperations {
	private static final int  MAX_NODES = 15;
	private static final int  MIN_NODES = 3;
	
	private static final int MAX_WEIGHT = 50;
	private static final int MIN_WEIGHT = -50;
	
	private int currentNumberNodes;
	private Stack<Integer> freeSpots = new Stack<Integer>(); 
	private Integer [][] adjMatrix = new Integer [MAX_NODES][MAX_NODES];
	
	/*
	 * Creates an empty graph(0 nodes)
	 */
	Graph() {
		this.currentNumberNodes = 0;
		for(int i = MAX_NODES-1; i >= 0; i--)
			freeSpots.push(i);
		this.adjMatrix = null;
	};
	
	/*
	 * Creates a graph with n nodes(no connection).
	 * Loop are forbidden. 
	 */
	Graph(int numberNodes) throws Exception{
		//check number of nodes
		if(MAX_NODES >= numberNodes)
			this.currentNumberNodes = numberNodes;
		else 
			throw new Exception();
		
		for(int i = MAX_NODES-numberNodes-1; i >= 0; i--)
			freeSpots.push(i);
		
		this.adjMatrix = null;		
	};
	

	public Graph(int numberNodes, Boolean random) throws Exception{
		//check number of nodes
		if(MAX_NODES >= numberNodes)
			this.currentNumberNodes = numberNodes;
		else 
			throw new Exception();
		
		for(int i = MAX_NODES-numberNodes-1; i >= 0; i--)
			freeSpots.push(i);
		
		//set random weights to edges. Loops are forbidden
		Random rand = new Random();
		for(int i = 0; i<this.currentNumberNodes; i++) {
			for(int j = 0; j<this.currentNumberNodes; j++) {
				if(i == j) this.adjMatrix[i][j] = 0;
				else {
					int w = rand.nextInt(MAX_WEIGHT - MIN_WEIGHT) + MIN_WEIGHT;
					this.adjMatrix[i][j] = w;
				}
			}
		}
	};

	@Override
	public void setEdge(int u, int v, int w) throws Exception {
		if(this.adjMatrix[u][v] == null)
			throw new Exception();
		else 
			this.adjMatrix[u][v] = w;
	}


	@Override
	public Integer getEdge(int u, int v) {
		return this.adjMatrix[u][v];
	}


	@Override
	public void insertNode() throws Exception {
		if(this.currentNumberNodes == MAX_NODES)
			throw new Exception();
		else {
			this.currentNumberNodes++;
			this.freeSpots.pop();
		}
	}


	@Override
	public void deleteNode(int u) throws Exception {
		if(this.currentNumberNodes == MIN_NODES)
			throw new Exception();
		else {
			this.currentNumberNodes--;
			this.freeSpots.push(u);
			
			//remove all the edged from and to u
			for(int i = 0; i < MAX_NODES; i++)
				this.adjMatrix[u][i] = null;
			for(int j = 0; j < MAX_NODES; j++)
				this.adjMatrix[j][u] = null;
		}
	}


	@Override
	/*
	 * node 1 = {2,w = 1}{3,w = 8}{4,w = -3}{6,w = 4}{7,w = 5}
	 */
	public String adj(int u) {
		String adj;
		adj = "Node " + u+1 + "= ";
		for(int i = 0; i<MAX_NODES; i++) {
			if(this.adjMatrix[u][i] != null)
				adj = "{" + i+1 + ",w = " + this.adjMatrix[u][i] + "}";
		}
		
		return adj;
	}


	@Override
	public void allAdj() {
		/*
		 * si assume che la matrice non abbia buchi ---> DA FIXARE
		 */
		for(int i = 0; i < this.currentNumberNodes; i++)
			System.out.println(adj(i));
	};
	
}
