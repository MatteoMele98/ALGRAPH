package algraph.model;

public class main {
	public static void main(String args[])  throws Exception {
       GraphModel g = new GraphModel(5,true);
       g.printMatrix(); 
       
       System.out.println();
       System.out.println("Inserimento nodo: currentNodes+5, freeSpots 6...");
       g.insertNode();
       g.printMatrix();
       
       System.out.println();
       System.out.println("aggiunta arco 5-3, peso 15");
       System.out.println("modifica arco 0-3, peso -15");
       System.out.println("cancellazione arco arco 2-1");
       NodeModel a1 = new NodeModel(5);
       NodeModel a2 = new NodeModel(3);
       NodeModel b1 = new NodeModel(0);
       NodeModel b2 = new NodeModel(3);
       NodeModel c1 = new NodeModel(2);
       NodeModel c2 = new NodeModel(1);
       NodeModel d1 = new NodeModel(15);
       g.insertEdge(a1, a2, 15);
       g.insertEdge(b1, b2, -15);
       g.deleteEdge(c1, c2);
       System.out.println("arco 5-3 =" + g.getEdge(5,3));
       g.printMatrix();
//       //GESTIONE ERRORI
//       g.insertEdge(a1, a1, 15);
//       g.insertEdge(d1, a2, -15);
//       g.deleteEdge(c1, c1);
       
       
       System.out.println();
       System.out.println("Cancellazione nodo 0");
       g.deleteNode(b1);
       g.printMatrix();
//       //GESTIONE ERRORI
//       g.deleteNode(15);
       
       
       GraphModel g2 = new GraphModel(15,false);
       System.out.println();
       g2.printAdj();
//       //GESTIONE ERRORI
//       g2.insertNode();
       
       
       
       
       
       
    }
}
