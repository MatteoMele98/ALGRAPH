package algraph.service;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import algraph.controller.GraphController;
import algraph.controller.PriorityController;
import algraph.controller.VisitedController;
import algraph.model.GraphModel;
import algraph.model.NodeModel;
import algraph.utils.Colors;
import algraph.view.BoolItem;
import algraph.view.GraphView;
import algraph.view.PriorityItem;
import javafx.scene.paint.Color;

public class AlgorithmHandler {
	private static final int PROGRAM_COUNTER_END = 5;
	
	//controllers
	private GraphController graphController;
	private VisitedController visitedController;
	private PriorityController priorityController;
	
    //auxiliary structures
	private TreeMap<NodeModel,Boolean> visitedMap = new TreeMap<NodeModel,Boolean>();
	private HashMap<NodeModel,NodeModel> parentMap = new HashMap<NodeModel,NodeModel>(); 
	private TreeMap<NodeModel,Integer> priorityMap = new TreeMap<NodeModel,Integer>();
	private NodeModel root = null;
	
	private GraphModel graphM;
    private GraphView graphV;
	private NodeModel currentNode;
    private NodeModel adjNode;
    	
	private int programCounter = 0;
    
    //========================================================================================
	
	/*
	 * @param startNode = root of the tree
	 */
	public AlgorithmHandler(GraphController graphController, VisitedController visitedController,
							  PriorityController priorityController) {
		
	this.graphController = graphController;
	this.visitedController = visitedController;
	this.priorityController = priorityController;
	
	this.graphM = this.graphController.getGraphModel();
	this.graphV = this.graphController.getGraphView();
	
	restartAlgorithm();
	}
	
    
    /*
     * @return true iff the execution is started
     */
    public Boolean isStarted() {
    	return (this.programCounter > 0);
    }
    
    public Boolean isFinish() {
    	return (this.programCounter >= PROGRAM_COUNTER_END);
    }
    
    public void restartAlgotithm(NodeModel root) {
    	this.root = root;
    	
    	restartAlgorithm();
    }
        
    /*
     * Clears visitedVect and parentVect.
     * @param startNode = Set root node
     * Initialize all the key of the currentNodes to MAX_VALUE 
     */
    public void restartAlgorithm() {
    	this.programCounter = 0;
      	
    	this.parentMap.clear();
    	this.visitedMap.clear();
    	this.parentMap.clear();
    	
    	BoolItem newBoolItem;
    	PriorityItem newPriorityItem;
		for(Map.Entry<Integer, NodeModel> node : this.graphM.currentNodesMap.entrySet()) {
			
			//=============================================================
			
			//init local variable
			//set all nodes to +INF priority
			this.priorityMap.put(node.getValue(), Integer.MAX_VALUE);
			
			//set all nodes to NOT VISITED
			this.visitedMap.put(node.getValue(), false);
			
			//set parent of all nodes to null
			this.parentMap.put(node.getValue(), null);
			//==============================================================
			
			//init controllers
			//add n FALSE item to the boolean vect
			newBoolItem = new BoolItem(node.getValue().getLabel());
			this.visitedController.add(newBoolItem);
			
			//add n +INF item to the priority vect
			newPriorityItem = new PriorityItem(node.getValue().getLabel());
			this.priorityController.add(newPriorityItem);
		}		
    }
    
   
    // A utility function to find the vertex with minimum key
    // value, from the set of vertices not yet included in MST
    private NodeModel minKey()
    {
    	String s = new String("Cerco il nodo con priorità minima non ancora visitato." + "\n");
    	System.out.println(s);
        
    	// Initialize min value
        Integer min = Integer.MAX_VALUE;
        NodeModel minNode = null;
        
        for (Map.Entry<Integer, NodeModel> node : this.graphM.currentNodesMap.entrySet()) {
        	NodeModel tmp = node.getValue();
        	System.out.println("Analizzo nodo " + tmp.getLabel() + "." + "\n");
        	
        	//highlith the currently visited node
        	Color prevColor = this.graphV.getNode(tmp.getIndex()).getColor();
        	this.graphV.getNode(tmp.getIndex()).switchColor(Colors.VISITING);
        	
        	if (!this.visitedMap.get(tmp) && this.priorityMap.get(tmp) < min)
            {            	
                min = this.priorityMap.get(tmp);	//aggiorno la priorità minima trovata
                minNode = tmp; 						//setto il nodo con priorità minima trovato fin ora.
            }
            
            //re-paint with the previous color
            this.graphV.getNode(tmp.getIndex()).switchColor(prevColor);
        }
        
        //ritorna nodo con priorità minima non ancora visitato
        return minNode; 
    }
 
    
    //execute one step of the alghoritm based on the program counter
    public void executeStep()
    { 
    	if(this.root == null || this.isFinish()) {
    		return;
    	}
    	
    	switch(programCounter) {
    	case 0:
    		this.restartAlgorithm();
    		programCounter = 1;
    		break;
    		
    	case 1:
    		//setto la root dell'albero dando la priorità minima.
    		this.priorityMap.put(this.root, 0);
    		
    		StringBuilder s1 = new StringBuilder();
    		s1.append("Setto la radice dell'albero al Nodo: " + this.graphM.currentNodesMap.get(this.root.getIndex()).getLabel());
    	    System.out.println(s1);
    		
    		programCounter = 3;
    		break;
    		
    	case 2:
    		boolean finish;
    		StringBuilder s2 = new StringBuilder();
//    		for(Map.Entry<NodeModel, Boolean> visit : visitedMap.entrySet()) {
//    			if(visit.getValue()) finish = false;   		
//    		} 
//    		
//    		if(!finish) {
//    			s2.append("Devono essere vistati altri nodi.");
//    			programCounter = 4;
//    		} else {
//				s2.append("Tutti i nodi sono stati visitati." + "\n");
//				s2.append("===========================================" + "\n");
//				//"Albero di copertura minimo: .... "
//				programCounter = PROGRAM_COUNTER_END;
//    		}
    		
    		for(Map.Entry<NodeModel, Boolean> visit : visitedMap.entrySet()) {
    			NodeModel visitNode = visit.getKey();
    			if (visit.getValue() == false) {
    				if (this.graphM.noLinkedNode.contains(visitNode) || this.graphM.allExitEdge.contains(visitNode))
    					
    			}
    				
    		} 
    		
    		
    		
    	case 3:
    		//il nodo corrente è il nodo con la priorità minima.
    		//all'inizio è la radice perchè settata nel passo 1
			this.currentNode = minKey();
						
			StringBuilder s3 = new StringBuilder();
			s3.append("Aggiungo " + this.currentNode.getLabel() + " all'albero di copertura." + "\n" );
			System.out.println(s3);
			
			//===========================================================
			
			//update local variable
			//setto a visitato il nodo corrente			
			this.visitedMap.put(this.currentNode,true);

			//===========================================================
			
			//update controller:
			//evidenzio il nodo corrente appena inserito
			this.graphV.getNode(this.currentNode).switchColor(Colors.VISITED);
						
			//setto a visitato il nodo corrente nel vettore dei visitati
			this.visitedController.getBoolItem(this.currentNode.getIndex()).setBool(true);

			programCounter = 2;
			break;
			
    	case 4:
    		//ciclo su tutti i nodi 
    		for(Map.Entry<Integer, NodeModel> adj : this.graphM.currentNodesMap.entrySet()) {
    			this.adjNode = adj.getValue();
    			
    			//se:	il nodo è connesso a currentNode
    			//		il peso dell'arco è minore della priorità precedente
    			//		non ho visitato prima questo nodo
    			if(this.graphM.areConnected(this.currentNode, this.adjNode) 
    					&& this.graphM.getWeight(this.currentNode, this.adjNode) < this.priorityMap.get(this.adjNode) 
    					&& this.visitedMap.get(this.adjNode) == false) {
    			
    				Integer newPriority = this.graphM.getWeight(this.currentNode, this.adjNode);
    				
    				//setto a colore di defualt l'arco tra il nodo analizzato e il suo vecchio padre (se esisteva)
    				//perchè se sono entrato in questo if è perchè il padre del nodo deve cambiare
    				if(this.graphM.areConnected(this.parentMap.get(this.adjNode), this.adjNode))
    					this.graphV.getEdge(this.parentMap.get(this.adjNode), this.adjNode).switchColor(Colors.DEFAULT);
    				
    				//=============================================================
    				
    				//update local variables
    				//setto il padre del nuovo nodo al nodo corrente
    				this.parentMap.put(this.adjNode, this.currentNode);
    				
    				//aggiorno la priorità del nodo con il nuovo peso
    				this.priorityMap.put(this.adjNode, newPriority);
    				
    				//=============================================================
    				
    				//update controller
    				//coloro l'arco dal nodo corrente (padre) al nodo che sto analizzando
    				this.graphV.getEdge(this.currentNode, this.adjNode).switchColor(Colors.VISITED);
    				
    				//aggiorno la priorità del nodo con il nuovo peso
    				this.priorityController.getPriorityItem(this.adjNode.getIndex()).setPriority(Integer.toString(newPriority));  
    			}
    		}
    		
    		programCounter = 3;
    	}
    }
    
    /*
     * Executes all remaining steps of the algorithm
     */
    public void executeAll() {
        if (this.root == null || isFinish()) {
            return;
        }

        while (!isFinish()) {
            executeStep();
        }
    }
    
}
   
