package algraph.service;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import algraph.controller.GraphController;
import algraph.controller.HomeController;
import algraph.controller.PriorityController;
import algraph.controller.PseudoCodeController;
import algraph.controller.VisitedController;
import algraph.model.GraphModel;
import algraph.model.NodeModel;
import algraph.utils.Colors;
import algraph.view.BoolItem;
import algraph.view.GraphView;
import algraph.view.PriorityItem;
import javafx.scene.paint.Color;

public class AlgorithmHandler extends Thread {
	private static final int PROGRAM_COUNTER_END = 5;
	
	//controllers
	private HomeController homeController;
	private GraphController graphController;
	private VisitedController visitedController;
	private PriorityController priorityController;
	private PseudoCodeController pseudoCodeController;
	
    //auxiliary structures
	private TreeMap<NodeModel,Boolean> visitedMap = new TreeMap<NodeModel,Boolean>();
	//leaf ---> parent
	private HashMap<NodeModel,NodeModel> parentMap = new HashMap<NodeModel,NodeModel>(); 
	private TreeMap<NodeModel,Integer> priorityMap = new TreeMap<NodeModel,Integer>();
	private NodeModel root = null;
	
	private GraphModel graphM;
    private GraphView graphV;
	private NodeModel currentNode;
    private NodeModel adjNode;
 
    private int speed;
	private int programCounter = 0;
    
    //========================================================================================
	
	/*
	 * @param startNode = root of the tree
	 */
	public AlgorithmHandler(HomeController homeController,GraphController graphController, VisitedController visitedController,
							  PriorityController priorityController, PseudoCodeController pseudoCodeController) {
											
	this.homeController = homeController;
	this.graphController = graphController;
	this.visitedController = visitedController;
	this.priorityController = priorityController;
	this.pseudoCodeController = pseudoCodeController;
	
	this.graphM = this.graphController.getGraphModel();
	this.graphV = this.graphController.getGraphView();
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
    
    public void restartAlgotithm(NodeModel root,int s) {
    	this.root = root;
		this.speed=s;
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
			this.visitedController.add(node.getValue(),newBoolItem);
			
			//add n +INF item to the priority vect
			newPriorityItem = new PriorityItem(node.getValue().getLabel());
			this.priorityController.add(node.getValue(),newPriorityItem);
		}		
    }
    
    private StringBuilder printMST() {
    	NodeModel root,leaf;
    	StringBuilder s = new StringBuilder();
    	for(Map.Entry<NodeModel, NodeModel> rootLeaf : this.parentMap.entrySet()) {
    		root = rootLeaf.getValue();
    		leaf = rootLeaf.getKey();
    		if(root == null)
    			s.append("Padre: " + "NIL" + " --> Figlio: " + leaf.getLabel() + "\n");	
    		else
    			s.append("Padre: " + root.getLabel() + " --> Figlio: " + leaf.getLabel() + "\n");	
    	}
    	return s;
    }
    
   
    // A utility function to find the vertex with minimum key
    // value, from the set of vertices not yet included in MST
    private NodeModel minKey() throws InterruptedException {
    	String s = new String("\n Cerco il nodo con priorit� minima non ancora visitato." + "\n");
    	System.out.println(s);
    	
    	this.pseudoCodeController.addString(s);
    	this.homeController.printPseudoCode();
    	
    	System.out.println(this.pseudoCodeController.getString());
        
    	// Initialize min value
        Integer min = Integer.MAX_VALUE;
        NodeModel minNode = null;
        
        for (Map.Entry<Integer, NodeModel> node : this.graphM.currentNodesMap.entrySet()) {
        	NodeModel tmp = node.getValue();
        	
        	this.pseudoCodeController.addString("Analizzo nodo " + tmp.getLabel() + "." + "\n");
        	this.homeController.printPseudoCode();
        	//highlith the currently visited node
        	Color prevColor = this.graphV.getNode(tmp.getIndex()).getColor();
        	this.graphV.getNode(tmp.getIndex()).switchColor(Colors.VISITING);
        	
        	// ------------------------ SOSPENDO ESECUZIONE ------------------------------- //
    		synchronized(this) {
				this.wait(this.speed);
			}
        	
        	if (!this.visitedMap.get(tmp) && this.priorityMap.get(tmp) < min)
            {            	
                min = this.priorityMap.get(tmp);	//aggiorno la priorit� minima trovata
                minNode = tmp; 						//setto il nodo con priorit� minima trovato fin ora.
            }
            
            //re-paint with the previous color
            this.graphV.getNode(tmp.getIndex()).switchColor(prevColor);
        }
        
        //ritorna nodo con priorit� minima non ancora visitato
        return minNode; 
    }
 
    
    //execute one step of the alghoritm based on the program counter
    public void executeStep() throws InterruptedException {
    	this.speed=speed;
    	if(this.root == null || this.isFinish()) {
    		return;
    	}
    	switch(programCounter) {
    	case 0:
    		programCounter = 1;
    		break;
    		
    	case 1:
    		//setto la root dell'albero dando la priorit� minima.
    		this.priorityMap.put(this.root, 0);
    		
    		//setto graficamente la priorit� della root
    		this.priorityController.priorityItemMap.get(root).setPriority("0");
    		
    		// ------------------------ SOSPENDO ESECUZIONE ------------------------------- //
    		synchronized(this) {
				this.wait(this.speed);
			}
    		
    		StringBuilder s1 = new StringBuilder();
    		s1.append("Setto la radice dell'albero al Nodo: " + this.graphM.currentNodesMap.get(this.root.getIndex()).getLabel() + "\n");
    		this.pseudoCodeController.addString(s1);
    		this.homeController.printPseudoCode();
    		programCounter = 3;
    		break;
    		
    	case 2:
    		boolean finish = true;
    		StringBuilder s2 = new StringBuilder();    		
    		for(Map.Entry<NodeModel, Boolean> visit : visitedMap.entrySet()) {
    			NodeModel visitNode = visit.getKey();
    			
    			if (!this.graphM.noLinkedNode.contains(visitNode)) {
    				if (visit.getValue()) {
        				finish = finish && visit.getValue();
       				}  else 
       					finish = finish && false;
    			}
    		} 
    		
    		if(finish) {
    			s2.append("Tutti i nodi sono stati visitati." + "\n");
				s2.append("================================" + "\n");
				this.pseudoCodeController.addString(s2);
				this.pseudoCodeController.addString(this.printMST());
				this.homeController.printPseudoCode();
				programCounter = PROGRAM_COUNTER_END;
    		} else {
    			this.pseudoCodeController.addString("Devono essere vistati altri nodi.");
    			this.homeController.printPseudoCode();
    			programCounter = 4;
    		}
    		break;
   		
    	case 3:
    		//il nodo corrente � il nodo con la priorit� minima.
    		//all'inizio � la radice perch� settata nel passo 1
			this.currentNode = minKey();
			
			StringBuilder s3 = new StringBuilder();
			
			if(!(this.currentNode == null)) {			
				s3.append("Aggiungo " + this.currentNode.getLabel() + " all'albero di copertura." + "\n" );
				this.pseudoCodeController.addString(s3);
				this.homeController.printPseudoCode();
				//===========================================================
				
				//update local variable
				//setto a visitato il nodo corrente			
				this.visitedMap.put(this.currentNode,true);
	
				//===========================================================
				
				//update controller:
				//evidenzio il nodo corrente appena inserito
				this.graphV.getNode(this.currentNode).switchColor(Colors.VISITED);
							
				//setto a visitato il nodo corrente nel vettore dei visitati
				this.visitedController.getBoolItem(this.currentNode).setBool(true);
				
				// ------------------------ SOSPENDO ESECUZIONE ------------------------------- //
	    		synchronized(this) {
					this.wait(this.speed);
				}
	
				programCounter = 2;
			} else {
				s3.append("Tutti i nodi sono stati visitati." + "\n");
				s3.append("===========================================" + "\n");
				this.pseudoCodeController.addString(s3);
				this.pseudoCodeController.addString(this.printMST());
				this.homeController.printPseudoCode();
				programCounter = PROGRAM_COUNTER_END;
			}
				
			
			break;
			
    	case 4:
    		//ciclo su tutti i nodi 
    		for(Map.Entry<Integer, NodeModel> adj : this.graphM.currentNodesMap.entrySet()) {
    			this.adjNode = adj.getValue();
    			
    			//se:	il nodo � connesso a currentNode
    			//		il peso dell'arco � minore della priorit� precedente
    			//		non ho visitato prima questo nodo
    			if(this.graphM.areConnected(this.currentNode, this.adjNode) 
    					&& this.graphM.getWeight(this.currentNode, this.adjNode) < this.priorityMap.get(this.adjNode) 
    					&& this.visitedMap.get(this.adjNode) == false) {
    			
    				Integer newPriority = this.graphM.getWeight(this.currentNode, this.adjNode);
    				
    				//setto al colore di defualt l'arco tra il nodo analizzato e il suo vecchio padre (se esisteva)
    				//perch� se sono entrato in questo if � perch� il padre del nodo deve cambiare
    				if(this.graphM.areConnected(this.parentMap.get(this.adjNode), this.adjNode)) {
    					this.graphV.getEdge(this.parentMap.get(this.adjNode), this.adjNode).switchColor(Colors.DEFAULT);
    					this.graphV.getEdge(this.adjNode, this.parentMap.get(this.adjNode)).switchColor(Colors.DEFAULT);
    				}
    				
    				// ------------------------ SOSPENDO ESECUZIONE ------------------------------- //
    	    		synchronized(this) {
    					this.wait(this.speed);
    				}
    				
    				//=============================================================
    				
    				//update local variables
    				//setto il padre del nuovo nodo al nodo corrente
    				this.parentMap.put(this.adjNode, this.currentNode);
    				
    				//aggiorno la priorit� del nodo con il nuovo peso
    				this.priorityMap.put(this.adjNode, newPriority);
    				
    				//=============================================================
    				
    				//update controller
    				//coloro l'arco dal nodo corrente (padre) al nodo che sto analizzando e viceversa
    				this.graphV.getEdge(this.currentNode, this.adjNode).switchColor(Colors.VISITED);
    				this.graphV.getEdge(this.adjNode,this.currentNode).switchColor(Colors.VISITED);
    				
    				//aggiorno la priorit� del nodo con il nuovo peso
    				this.priorityController.getPriorityItem(this.adjNode).setPriority(Integer.toString(newPriority));
    				
    				// ------------------------ SOSPENDO ESECUZIONE ------------------------------- //
    	    		synchronized(this) {
    					this.wait(this.speed);
    				}
    			}
    		}
    		
    		programCounter = 3;
    	}
}

	private NodeModel noPauseminKey(){
		String s = new String("\n Cerco il nodo con priorit� minima non ancora visitato." + "\n");
		System.out.println(s);

		this.pseudoCodeController.addString(s);
		this.homeController.printPseudoCode();

		System.out.println(this.pseudoCodeController.getString());

		// Initialize min value
		Integer min = Integer.MAX_VALUE;
		NodeModel minNode = null;

		for (Map.Entry<Integer, NodeModel> node : this.graphM.currentNodesMap.entrySet()) {
			NodeModel tmp = node.getValue();

			this.pseudoCodeController.addString("Analizzo nodo " + tmp.getLabel() + "." + "\n");
			this.homeController.printPseudoCode();
			//highlith the currently visited node
			Color prevColor = this.graphV.getNode(tmp.getIndex()).getColor();
			this.graphV.getNode(tmp.getIndex()).switchColor(Colors.VISITING);

			if (!this.visitedMap.get(tmp) && this.priorityMap.get(tmp) < min)
			{
				min = this.priorityMap.get(tmp);	//aggiorno la priorit� minima trovata
				minNode = tmp; 						//setto il nodo con priorit� minima trovato fin ora.
			}

			//re-paint with the previous color
			this.graphV.getNode(tmp.getIndex()).switchColor(prevColor);
		}

		//ritorna nodo con priorit� minima non ancora visitato
		return minNode;
	}


	//execute one step of the alghoritm based on the program counter
	public void noPauseexecuteStep() {
		if(this.root == null || this.isFinish()) {
			return;
		}
		switch(programCounter) {
			case 0:
				programCounter = 1;
				break;

			case 1:
				//setto la root dell'albero dando la priorit� minima.
				this.priorityMap.put(this.root, 0);

				//setto graficamente la priorit� della root
				this.priorityController.priorityItemMap.get(root).setPriority("0");

				StringBuilder s1 = new StringBuilder();
				s1.append("Setto la radice dell'albero al Nodo: " + this.graphM.currentNodesMap.get(this.root.getIndex()).getLabel() + "\n");
				this.pseudoCodeController.addString(s1);
				this.homeController.printPseudoCode();
				programCounter = 3;
				break;

			case 2:
				boolean finish = true;
				StringBuilder s2 = new StringBuilder();
				for(Map.Entry<NodeModel, Boolean> visit : visitedMap.entrySet()) {
					NodeModel visitNode = visit.getKey();

					if (!this.graphM.noLinkedNode.contains(visitNode)) {
						if (visit.getValue()) {
							finish = finish && visit.getValue();
						}  else
							finish = finish && false;
					}
				}

				if(finish) {
					s2.append("Tutti i nodi sono stati visitati." + "\n");
					s2.append("================================" + "\n");
					this.pseudoCodeController.addString(s2);
					this.pseudoCodeController.addString(this.printMST());
					this.homeController.printPseudoCode();
					programCounter = PROGRAM_COUNTER_END;
				} else {
					this.pseudoCodeController.addString("Devono essere vistati altri nodi.");
					this.homeController.printPseudoCode();
					programCounter = 4;
				}
				break;

			case 3:
				//il nodo corrente � il nodo con la priorit� minima.
				//all'inizio � la radice perch� settata nel passo 1
				this.currentNode = noPauseminKey();

				StringBuilder s3 = new StringBuilder();

				if(!(this.currentNode == null)) {
					s3.append("Aggiungo " + this.currentNode.getLabel() + " all'albero di copertura." + "\n" );
					this.pseudoCodeController.addString(s3);
					this.homeController.printPseudoCode();
					//===========================================================

					//update local variable
					//setto a visitato il nodo corrente
					this.visitedMap.put(this.currentNode,true);

					//===========================================================

					//update controller:
					//evidenzio il nodo corrente appena inserito
					this.graphV.getNode(this.currentNode).switchColor(Colors.VISITED);

					//setto a visitato il nodo corrente nel vettore dei visitati
					this.visitedController.getBoolItem(this.currentNode).setBool(true);

					programCounter = 2;
				} else {
					s3.append("Tutti i nodi sono stati visitati." + "\n");
					s3.append("===========================================" + "\n");
					this.pseudoCodeController.addString(s3);
					this.pseudoCodeController.addString(this.printMST());
					this.homeController.printPseudoCode();
					programCounter = PROGRAM_COUNTER_END;
				}


				break;

			case 4:
				//ciclo su tutti i nodi
				for(Map.Entry<Integer, NodeModel> adj : this.graphM.currentNodesMap.entrySet()) {
					this.adjNode = adj.getValue();

					//se:	il nodo � connesso a currentNode
					//		il peso dell'arco � minore della priorit� precedente
					//		non ho visitato prima questo nodo
					if(this.graphM.areConnected(this.currentNode, this.adjNode)
							&& this.graphM.getWeight(this.currentNode, this.adjNode) < this.priorityMap.get(this.adjNode)
							&& this.visitedMap.get(this.adjNode) == false) {

						Integer newPriority = this.graphM.getWeight(this.currentNode, this.adjNode);

						//setto al colore di defualt l'arco tra il nodo analizzato e il suo vecchio padre (se esisteva)
						//perch� se sono entrato in questo if � perch� il padre del nodo deve cambiare
						if(this.graphM.areConnected(this.parentMap.get(this.adjNode), this.adjNode)) {
							this.graphV.getEdge(this.parentMap.get(this.adjNode), this.adjNode).switchColor(Colors.DEFAULT);
							this.graphV.getEdge(this.adjNode, this.parentMap.get(this.adjNode)).switchColor(Colors.DEFAULT);
						}
						//=============================================================

						//update local variables
						//setto il padre del nuovo nodo al nodo corrente
						this.parentMap.put(this.adjNode, this.currentNode);

						//aggiorno la priorit� del nodo con il nuovo peso
						this.priorityMap.put(this.adjNode, newPriority);

						//=============================================================

						//update controller
						//coloro l'arco dal nodo corrente (padre) al nodo che sto analizzando e viceversa
						this.graphV.getEdge(this.currentNode, this.adjNode).switchColor(Colors.VISITED);
						this.graphV.getEdge(this.adjNode,this.currentNode).switchColor(Colors.VISITED);

						//aggiorno la priorit� del nodo con il nuovo peso
						this.priorityController.getPriorityItem(this.adjNode).setPriority(Integer.toString(newPriority));

					}
				}

				programCounter = 3;
		}
	}

	/*
     * Executes all remaining steps of the algorithm
     */
    public void executeAll(int speed) throws InterruptedException {
        if (this.root == null || isFinish()) {
            return;
        }
        this.speed=speed;
        while(!isFinish()) {
				this.executeStep();
        }
    }
	/*
	 * Executes all remaining steps of the algorithm
	 */
	public void noPauseexecuteAll() {
		if (this.root == null || isFinish()) {
			return;
		}
		while(!isFinish()) {
			this.noPauseexecuteStep();
		}
	}

    
    public VisitedController getVisitedController() {
    	return this.visitedController;
    }
    
    public PriorityController getPriorityController() {
    	return this.priorityController;
    }


	@Override
	public void run() {
		try {
			this.executeAll(this.speed);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
   
