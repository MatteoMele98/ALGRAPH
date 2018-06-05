package algraph.model;

public class Main {
	public static void main(String[] args) throws Exception {
       Graph g = new Graph(10);
       
       //g.setEdge(0, 1, 50);
       
       System.out.println(g.adj(0));
    }
}
