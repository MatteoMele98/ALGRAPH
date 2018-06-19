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
import javafx.scene.control.Alert;
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
    	Integer tot = 0;
    	StringBuilder s = new StringBuilder();
    	for(Map.Entry<NodeModel, NodeModel> rootLeaf : this.parentMap.entrySet()) {
    		root = rootLeaf.getValue();
    		leaf = rootLeaf.getKey();
    		if(root == null)
    			s.append("Padre: " + "NIL" + " --> Figlio: " + leaf.getLabel() + "\n");
    		else {
    			s.append("Padre: " + root.getLabel() + " --> Figlio: " + leaf.getLabel() + "\n");	
    			tot += this.graphM.getWeight(root, leaf);
    		}
    			
    	}
    	s.append("\n Peso totale dell'albero di copertura: " + tot);
    	return s;
    }
    
        
    /*
     * Clears visitedVect and parentVect.
     * @param startNode = Set root node
     * Initialize all the key of the currentNodes to MAX_VALUE 
     */
   
    
    
   
    // A utility function to find the vertex with minimum key
    // value, from the set of vertices not yet included in MST
    private NodeModel minKey() throws InterruptedException {
    	
    	String s = new String("\nCerco il nodo con priorità  minima non ancora visitato." + "\n");
    	this.pseudoCodeController.addString(s);
		this.homeController.printPseudoCode();
    
        
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
                min = this.priorityMap.get(tmp);	//aggiorno la prioritï¿½ minima trovata
                minNode = tmp; 						//setto il nodo con prioritï¿½ minima trovato fin ora.
            }
            
            //re-paint with the previous color
            this.graphV.getNode(tmp.getIndex()).switchColor(prevColor);
        }
        
        //ritorna nodo con prioritï¿½ minima non ancora visitato
        return minNode; 
    }
 
    
    //execute one step of the alghoritm based on the program counter
    public void executeStep() throws InterruptedException {
    	if(this.root == null || this.isFinish()) {
    		return;
    	}
    	switch(programCounter) {
    	case 0:
    		programCounter = 1;
    		break;
    		
    	case 1:
    		//setto la root dell'albero dando la prioritï¿½ minima.
    		this.priorityMap.put(this.root, 0);
    		
    		//setto graficamente la prioritï¿½ della root
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
    			programCounter = 4;
    		}
    		break;
   		
    	case 3:
    		//il nodo corrente ï¿½ il nodo con la prioritï¿½ minima.
    		//all'inizio ï¿½ la radice perchï¿½ settata nel passo 1
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
    		StringBuilder s4 = new StringBuilder();
    		
    		//ciclo su tutti i nodi 
    		for(Map.Entry<Integer, NodeModel> adj : this.graphM.currentNodesMap.entrySet()) {
    			this.adjNode = adj.getValue();
    			
    			//se:	il nodo ï¿½ connesso a currentNode
    			//		il peso dell'arco ï¿½ minore della prioritï¿½ precedente
    			//		non ho visitato prima questo nodo
    			if(this.graphM.areConnected(this.currentNode, this.adjNode) 
    					&& this.graphM.getWeight(this.currentNode, this.adjNode) < this.priorityMap.get(this.adjNode) 
    					&& this.visitedMap.get(this.adjNode) == false) {
    			
    				Integer newPriority = this.graphM.getWeight(this.currentNode, this.adjNode);
    				
    				//setto al colore di defualt l'arco tra il nodo analizzato e il suo vecchio padre (se esisteva)
    				//perchï¿½ se sono entrato in questo if ï¿½ perchï¿½ il padre del nodo deve cambiare
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
    				
    				//aggiorno la prioritï¿½ del nodo con il nuovo peso
    				this.priorityMap.put(this.adjNode, newPriority);
    				
    				//=============================================================
    				
    				//update controller
    				//coloro l'arco dal nodo corrente (padre) al nodo che sto analizzando e viceversa
    				this.graphV.getEdge(this.currentNode, this.adjNode).switchColor(Colors.VISITED);
    				this.graphV.getEdge(this.adjNode,this.currentNode).switchColor(Colors.VISITED);
					
    				s4.append("Aggiungo l'arco da " + this.currentNode.getLabel() + " a " + this.adjNode.getLabel() + " all'albero minimo di copertura. \n");
					this.pseudoCodeController.addString(s4);
					homeController.printPseudoCode();
					
					// ------------------------ SOSPENDO ESECUZIONE ------------------------------- //
    	    		synchronized(this) {
    					this.wait(this.speed);
    				}					
    				
    				//aggiorno la prioritï¿½ del nodo con il nuovo peso
    				this.priorityController.getPriorityItem(this.adjNode).setPriority(Integer.toString(newPriority));
					s4.append("Aggiorno la prioritá del nodo " + this.adjNode.getLabel() + " con il peso: " + Integer.toString(newPriority) + "\n");
					this.pseudoCodeController.addString(s4);
					homeController.printPseudoCode();
    			}
    		}
    		
    		programCounter = 3;
    	}
}

	private NodeModel noPauseminKey(boolean v){
		// Initialize min value
		Integer min = Integer.MAX_VALUE;
		NodeModel minNode = null;
		
		if(v) {
			String s = new String("\nCerco il nodo con priorità  minima non ancora visitato." + "\n");
	    	this.pseudoCodeController.addString(s);
		}

		for (Map.Entry<Integer, NodeModel> node : this.graphM.currentNodesMap.entrySet()) {
			NodeModel tmp = node.getValue();
			//highlith the currently visited node
			Color prevColor = this.graphV.getNode(tmp.getIndex()).getColor();
			this.graphV.getNode(tmp.getIndex()).switchColor(Colors.VISITING);

			if (!this.visitedMap.get(tmp) && this.priorityMap.get(tmp) < min)
			{
				min = this.priorityMap.get(tmp);	//aggiorno la prioritï¿½ minima trovata
				minNode = tmp; 						//setto il nodo con prioritï¿½ minima trovato fin ora.
			}

			//re-paint with the previous color
			this.graphV.getNode(tmp.getIndex()).switchColor(prevColor);
		}

		//ritorna nodo con prioritï¿½ minima non ancora visitato
		return minNode;
	}


	//execute one step of the alghoritm based on the program counter
	public void noPauseexecuteStep(boolean v) {
		if(this.root == null || this.isFinish()) {
			return;
		}
		switch(programCounter) {
			case 0:
				programCounter = 1;
				break;

			case 1:
				//setto la root dell'albero dando la prioritï¿½ minima.
				this.priorityMap.put(this.root, 0);

				//setto graficamente la prioritï¿½ della root
				this.priorityController.priorityItemMap.get(root).setPriority("0");
				if(v) {
					StringBuilder s1 = new StringBuilder();
					s1.append("Setto la radice dell'albero al Nodo: " + this.graphM.currentNodesMap.get(this.root.getIndex()).getLabel() + "\n");
					this.pseudoCodeController.addString(s1);
				}
				
				programCounter = 3;
				break;

			case 2:
				boolean finish = true;
				for(Map.Entry<NodeModel, Boolean> visit : visitedMap.entrySet()) {
					NodeModel visitNode = visit.getKey();

					if (!this.graphM.noLinkedNode.contains(visitNode)) {
						if (visit.getValue()) {
								StringBuilder s2 = new StringBuilder();
								s2.append("Tutti i nodi sono stati visitati." + "\n");
								s2.append("================================" + "\n");
								this.pseudoCodeController.addString(s2);
								this.pseudoCodeController.addString(this.printMST());

							finish = finish && visit.getValue();
						}  else {
							if(v) {
								this.pseudoCodeController.addString("Devono essere vistati altri nodi.\n");
							}
							finish = finish && false;
						}
					}
				}

				if(finish) {
					programCounter = PROGRAM_COUNTER_END;
				} else {
					programCounter = 4;
				}
				break;

			case 3:
				//il nodo corrente è il nodo con la priorità minima.
				this.currentNode = noPauseminKey(v);

				StringBuilder s3 = new StringBuilder();

				if(!(this.currentNode == null)) {
					if (v){
						s3.append("Aggiungo " + this.currentNode.getLabel() + " all'albero di copertura." + "\n");
						this.pseudoCodeController.addString(s3);
					}
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

						s3.append("Albero minimo di copertura." + "\n");
						s3.append("===========================================" + "\n");
						this.pseudoCodeController.addString(s3);
						this.pseudoCodeController.addString(this.printMST());

					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("Info Dialog");
					alert.setHeaderText(null);
					alert.setContentText("Esecuzione terminata con successo!");
					alert.showAndWait();
					programCounter = PROGRAM_COUNTER_END;
				}


				break;

			case 4:
				StringBuilder s4 = new StringBuilder();
				
				//ciclo su tutti i nodi
				for(Map.Entry<Integer, NodeModel> adj : this.graphM.currentNodesMap.entrySet()) {
					this.adjNode = adj.getValue();

					//se:	il nodo ï¿½ connesso a currentNode
					//		il peso dell'arco ï¿½ minore della prioritï¿½ precedente
					//		non ho visitato prima questo nodo
					if(this.graphM.areConnected(this.currentNode, this.adjNode)
							&& this.graphM.getWeight(this.currentNode, this.adjNode) < this.priorityMap.get(this.adjNode)
							&& this.visitedMap.get(this.adjNode) == false) {

						Integer newPriority = this.graphM.getWeight(this.currentNode, this.adjNode);

						//setto al colore di defualt l'arco tra il nodo analizzato e il suo vecchio padre (se esisteva)
						//perchï¿½ se sono entrato in questo if ï¿½ perchï¿½ il padre del nodo deve cambiare
						if(this.graphM.areConnected(this.parentMap.get(this.adjNode), this.adjNode)) {
							this.graphV.getEdge(this.parentMap.get(this.adjNode), this.adjNode).switchColor(Colors.DEFAULT);
							this.graphV.getEdge(this.adjNode, this.parentMap.get(this.adjNode)).switchColor(Colors.DEFAULT);
						}
						//=============================================================

						//update local variables
						//setto il padre del nuovo nodo al nodo corrente
						this.parentMap.put(this.adjNode, this.currentNode);

						//aggiorno la prioritï¿½ del nodo con il nuovo peso
						this.priorityMap.put(this.adjNode, newPriority);

						//=============================================================

						//update controller
						//coloro l'arco dal nodo corrente (padre) al nodo che sto analizzando e viceversa
						this.graphV.getEdge(this.currentNode, this.adjNode).switchColor(Colors.VISITED);
						this.graphV.getEdge(this.adjNode,this.currentNode).switchColor(Colors.VISITED);
						if(v) {
							s4.append("Aggiungo l'arco da " + this.currentNode.getLabel() + " a " + this.adjNode.getLabel() + " all'albero minimo di copertura. \n");
							this.pseudoCodeController.addString(s4);
						}

						//aggiorno la prioritï¿½ del nodo con il nuovo peso
						this.priorityController.getPriorityItem(this.adjNode).setPriority(Integer.toString(newPriority));
						if(v) {
							s4.append("Aggiorno la prioritá del nodo " + this.adjNode.getLabel() + " con il peso: " + Integer.toString(newPriority) + "\n");
							this.pseudoCodeController.addString(s4);
						}

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
			this.noPauseexecuteStep(false);
		}
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
   
