package algraph.controller;

import java.util.Iterator;

import algraph.model.EdgeModel;
import algraph.model.GraphModel;
import algraph.model.NodeModel;
import algraph.view.GraphView;

public class GraphController {
	private GraphModel graphM;
	private GraphView graphV;
	
	
	//=========================================================================
	public void randomGraph(int numberNodes) throws Exception {
		graphM = new GraphModel(numberNodes,true);
		
		//inserimento nodi
		Iterator<NodeModel> nodesIT = graphM.getCurrentNodes();
		while(nodesIT.hasNext()) {
			NodeModel newNode = nodesIT.next();
			graphV.insertNode(newNode);
		}
		
		//inserimento archi
		for(int i=0; i<graphM.getCurrentNumberNodes(); i++) {
			for(int j=0; j<graphM.getCurrentNumberNodes(); j++) {
				if(graphM.areConnected(i, j)) 
					graphV.insertEdge(i, j);
			}
		}
		
	}
	
	public void nodesGraph(int numberNodes) throws Exception {
		graphM = new GraphModel(numberNodes,false);
		
		Iterator<NodeModel> nodesIT = graphM.getCurrentNodes();
		while(nodesIT.hasNext()) {
			NodeModel element = nodesIT.next();
			graphV.insertNode(element);
		}
	}
	
	//=======================================================================	
	public void insertNode() throws Exception {
		NodeModel newNode = graphM.insertNode();
		graphV.insertNode(newNode);
	}
	
	public void deleteNode(NodeModel deletedNode) throws Exception {
		graphM.deleteNode(deletedNode);
		graphV.deleteNode(deletedNode);
	}
	
	public void insertEdge(EdgeModel newEdge) throws Exception {
		graphM.insertEdge(newEdge);
		graphV.insertEdge(newEdge);
	}
	
	public void deleteEdge(EdgeModel deletedEdge) throws Exception {
		graphM.deleteEdge(deletedEdge);
		graphV.deleteEdge(deletedEdge);
	}
	
		
}
