package algraph.controller;


import algraph.model.EdgeModel;
import algraph.model.GraphModel;
import algraph.model.NodeModel;
import algraph.view.GraphView;

public class GraphController {
	private GraphModel graphM;
	private GraphView graphV;
	
	//=========================================================================
	public GraphController() {
		graphM = null;
		graphV = null;
	}
	
	
	public GraphController (int numberNodes, Boolean random) throws Exception {
		if(random) {
			graphM = new GraphModel(numberNodes,true);
			graphV = new GraphView(numberNodes);
			
			//inserimento archi
			for(int i=0; i<graphM.getCurrentNumberNodes(); i++) {
				for(int j=0; j<graphM.getCurrentNumberNodes(); j++) {
					if(graphM.areConnected(i, j)) 
						graphV.insertEdge(i, j);
				}
			}
			
		} else {
			graphM = new GraphModel(numberNodes,false);
			graphV = new GraphView(numberNodes);
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
		
		for(int i = 0; i < graphM.MAX_NODES; i++) 
			graphV.deleteEdge(deletedNode.getIndex(), i);
		for(int j = 0; j < graphM.MAX_NODES; j++) 
			graphV.deleteEdge(j,deletedNode.getIndex());
	}
	
	public void insertEdge(EdgeModel newEdge) throws Exception {
		graphM.insertEdge(newEdge);
		graphV.insertEdge(newEdge);
	}
	
	public void deleteEdge(EdgeModel deletedEdge) throws Exception {
		graphM.deleteEdge(deletedEdge);
		graphV.deleteEdge(deletedEdge);
	}
	//=========================================================================0
	public GraphModel getGraphModel() {
		return this.graphM;
	}
	
	public GraphView getGraphView() {
		return this.graphV;
	}
	
}
