package algraph.model;

public interface GraphOperations {
	
	/*
	 * Creates a random graph of n nodes with edges. 
	 * Loop are forbidden. 
	 * Edges' weight is between MAX_WEIGHT and MIN_WEIGHT
	 */
	//void randomGraph(int numberNodes) throws Exception;
	
	/*
	 * @param u = start node
	 * @param v = end node
	 * @param w = weight
	 * Creates a edge from u to v weighted w.
	 * If edge already exists change the value to w.
	 * 
	 * EXEPTION:
	 * - u != v
	 * - MIN_WEIGHT < w < MAX_WEIGHT
	 */
	void setEdge(int u, int v, int w) throws Exception;
	
	/*
	 * Return the weight of the edge from u to v.
	 * Return null if it doesn't exist.
	 */
	Integer getEdge(int u, int v);
	
	/*
	 * Insert a new Node in the first free spot, if available
	 */
	void insertNode() throws Exception;
	
	/*
	 * Delete node u if exist
	 */
	void deleteNode(int u) throws Exception;
	
	/*
	 * Print the adj list of the node u, if exist
	 * row1 = 0 1 8 -3 0 4 5 0 0 0
	 * return
	 * 		node 1 = {2,w = 1}{3,w = 8}{4,w = -3}{6,w = 4}{7,w = 5}
	 */
	String adj(int u);
	
	/*
	 * foreach node u, print adj(u)
	 */
	void allAdj();
	
	/*
	 * Print the constructed graph
	 */
	String toString();
}
