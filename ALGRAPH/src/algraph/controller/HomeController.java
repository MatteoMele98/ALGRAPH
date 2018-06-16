package algraph.controller;


import java.awt.Desktop;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;

import javax.swing.JOptionPane;

import algraph.model.NodeModel;
import algraph.service.AlgorithmHandler;
import algraph.utils.Colors;
import algraph.view.GraphView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import algraph.model.EdgeModel;
import algraph.model.GraphModel;

public class HomeController {
	private static final int MAX_NODES = 15;

	private GraphController graphController;
	private PriorityController priorityController;
	private VisitedController visitedController;
	
	private AlgorithmHandler algorithmHandler;
	
	private boolean pendingExecution;
	//====================================================
	
	public HomeController() throws Exception {
		this.graphController = new GraphController();
		this.visitedController = new VisitedController();
		this.priorityController = new PriorityController();
		
		/*
		 * add alg handler
		 * 
		 */
	}
	
	public void algorithmHandlerGenerator() {
		this.algorithmHandler = new AlgorithmHandler(graphController,visitedController,priorityController);
	}

    @FXML
    private TextField nodeToDelete;
    
    @FXML
    private ResourceBundle resources;

    @FXML
    private TextField n_nodi;
    
    @FXML
    private URL location;


    @FXML
    private MenuItem runMenuItem;

    @FXML
    private MenuItem sbsMenuItem;

    @FXML
    private MenuItem stopMenuItem;

    @FXML
    private MenuItem stepMenuItem;

    @FXML
    private Pane graphPane;

    @FXML
    private TextArea outputTextArea;

    @FXML
    private TextArea outputTextArea1;

    @FXML
    private Text coordinateLabel;

    @FXML
    private ScrollPane scrollPaneVisited;

    @FXML
    private VBox vBoxVisited;

    @FXML
    private ScrollPane scrollPaneParents;

    @FXML
    private VBox vBoxParents;
    @FXML
    private TextField nodeOne;

    @FXML
    private TextField nodeTwo;

    @FXML
    private TextField peso;
    
    private File selectedFile;
    private Scanner scanner;
    
    @FXML
    private ComboBox deleteComboBox;
    
    @FXML
    private ComboBox edgeCBoxOne;

    @FXML
    private ComboBox edgeCBoxTwo;

    @FXML
    private ComboBox startComboBox1;

    @FXML
    private ComboBox startComboBox3;

    @FXML
    private ComboBox edgeDBox1;

    @FXML
    private ComboBox edgeDBox2;
    
    /*
     * print the entire updated graph
     */
    private void print() {
    	this.graphPane.getChildren().clear();
    	for(Integer i=0; i < MAX_NODES; i++) {
            if (this.graphController.getGraphView().getNode(i).getIsVisible()) {
                this.graphPane.getChildren().add(this.graphController.getGraphView().getNode(i).printNode());
            }
            for (int j = 0; j < MAX_NODES; j++) {
                if (this.graphController.getGraphView().getEdge(i, j) != null) {
                    this.graphController.getGraphView().getEdge(i, j);
                    this.graphPane.getChildren().add(this.graphController.getGraphView().getEdge(i, j).printEdge());
                }
            }
        }
    	this.printVect();
        this.outputTextArea1.setText(this.graphController.getGraphModel().printMatrix().toString());
    }
    
    private void printVect() {
    	if(!this.visitedController.boolItem.isEmpty()) {
    		this.vBoxVisited.getChildren().clear();
    		for(int i = 0; i < this.visitedController.boolItem.size(); i++)
    		this.vBoxVisited.getChildren().add(this.visitedController.getBoolItem(i).printBoolItem());
    	}
    	
    	if(!this.priorityController.priorityItem.isEmpty()) {
    		this.vBoxParents.getChildren().clear();
    		for(int i = 0; i < this.priorityController.priorityItem.size(); i++)
    		this.vBoxParents.getChildren().add(this.priorityController.getPriorityItem(i).printPriorityItem());
    	}
    		
    }
    
    /*
     * Init the comboBox only with the current nodes.
     */
    private void initComboBox(){
        this.deleteComboBox.getItems().clear();
        this.edgeCBoxOne.getItems().clear();
        this.edgeCBoxTwo.getItems().clear();
        this.startComboBox1.getItems().clear();
        this.startComboBox3.getItems().clear();
        this.edgeDBox1.getItems().clear();
        this.edgeDBox2.getItems().clear();
        for(Map.Entry<Integer,NodeModel>node: this.graphController.getGraphModel().currentNodesMap.entrySet()){
            this.deleteComboBox.getItems().add(node.getValue().getLabel());
            this.edgeCBoxOne.getItems().add(node.getValue().getLabel());
            this.edgeCBoxTwo.getItems().add(node.getValue().getLabel());
            this.startComboBox1.getItems().add(node.getValue().getLabel());
            this.startComboBox3.getItems().add(node.getValue().getLabel());
            this.edgeDBox1.getItems().add(node.getValue().getLabel());
            this.edgeDBox2.getItems().add(node.getValue().getLabel());
        }
    }
    
   

    @FXML
    void handleButtonClick_GraphPane(MouseEvent event) {

    }

    @FXML
    void handleMenuItem_About(ActionEvent event) {
    	try {
            Desktop.getDesktop().browse(new URL("http://www.cs.unibo.it/~sacerdot/logica/").toURI());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (java.net.URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleMenuItem_AnimationSettings(ActionEvent event) {

    }

    @FXML
    void handleMenuItem_Close(ActionEvent event) {

    }

    @FXML
    void handleMenuItem_Debug(ActionEvent event) {

    }    

    @FXML
    void handleMenuItem_NextStep(ActionEvent event) {
    	this.algorithmHandler = new AlgorithmHandler(graphController,visitedController,priorityController);
    }

    @FXML
    void handleMenuItem_OpenFile(ActionEvent event) throws IOException {
    	//apre la finestra della scelta del file
    	FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Text File");
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt", "*.csv"));
        this.selectedFile = fileChooser.showOpenDialog(null);
        //controlla che sia stato selezionato un file
        if (selectedFile != null){
            this.scanner = new Scanner(new FileReader(selectedFile));
            int x[][]=new int[20][20];
            int i=0;
            int j=0;
            int dim_int=(int) Math.sqrt(dim(x));
			double dim_double=Math.sqrt(dim(x));
			//devo chiudere e aprire lo scanner per far ripartire lo scanner dalla prima riga
			this.scanner.close();
			this.scanner=new Scanner(new FileReader(selectedFile));
			if(dim_double>dim_int || dim_double>dim_int) {
				System.out.println("C'� un errore nella matrice. Non � una matrice quadrata! Controlla!");
			}
			else {
				//carica la matrice
				while (i<dim_int) {
					if(j<dim_int && this.scanner.hasNextInt()) {
						x[i][j]=this.scanner.nextInt();
						
						j++;
					}else {
						i++;
						j=0;
					}
		        }
				//stampa la matrice
				for(int i1=0; i1<dim_int; i1++) {
					for(int j1=0; j1<dim_int; j1++) {
						System.out.print(x[i1][j1]+" ");
					}
					System.out.println();
				}
			}
			this.scanner.close();
        }
        
      }
    //calcola la dimensione della matrice, serve per fare il controllo se � o meno una matrice n*n
    public double dim(int x[][]){
		double dimensione=0;
		while (this.scanner.hasNextLine()) {
			scanner.nextInt();
			dimensione++;
		}
		return dimensione;
	}
    

    @FXML
    void handleMenuItem_RandomGraph(ActionEvent event) throws Exception {
    	this.graphController = new GraphController(10,true);
    	this.initComboBox();
    	
    	this.print();
    	this.graphController.getGraphModel().printMatrix();
    }
    
    @FXML
    void handleMenuItem_NodesGraph(ActionEvent event) throws NumberFormatException, Exception {
    	this.graphController = new GraphController(Integer.parseInt(n_nodi.getText()),false);
    	this.initComboBox();
    	this.print();
    	
    	this.graphController.getGraphModel().printMatrix();
    }
    
    @FXML
    void handleMenuItem_RunAnimation(ActionEvent event) {
    	String c = this.startComboBox3.getValue().toString();
        int x=c.charAt(0);
        boolean result = this.graphController.getGraphModel().isConnected(x-65);
        if (result) {
            JOptionPane.showMessageDialog(null,"Grafo connesso!");

        } else {
            JOptionPane.showMessageDialog(null,"Grafo non connesso!");
        }
    }

    @FXML
    void handleMenuItem_Save(ActionEvent event) {
    	//AVVIA TUTTO ALGORITMO
    	this.algorithmHandler = new AlgorithmHandler(graphController,visitedController,priorityController);
    	NodeModel root = new NodeModel(this.startComboBox1.getValue().toString().charAt(0)-65);
    	
    	this.algorithmHandler.restartAlgotithm(root);
    	this.visitedController = this.algorithmHandler.getVisitedController();
    	this.priorityController = this.algorithmHandler.getPriorityController();
    	this.algorithmHandler.executeAll();   	
    	this.print();
    }

    @FXML
    void handleMenuItem_Stop(ActionEvent event) {

    }

    @FXML
    void handleMouseMove_GraphPane(MouseEvent event) {

    }
    
    @FXML
    public void handleMenuItem_InsertNode(ActionEvent event) throws Exception {
    	if(graphController.getGraphModel().getCurrentNumberNodes() <= 14) {
    	 		graphController.insertNode();
    	 		this.print();
    	 		this.initComboBox();
    	 		this.graphController.getGraphModel().printMatrix();
    	 } else 
    		JOptionPane.showMessageDialog(null,"Puoi inserire al massimo 15 nodi!");
    }
    
    @FXML
    void handleMenuDeleteNode(ActionEvent event) throws Exception {
    	int c = this.deleteComboBox.getValue().toString().charAt(0);
    	NodeModel deletedNode = new NodeModel(c-65);
    	graphController.deleteNode(deletedNode);
    	this.initComboBox();
    	this.print();
    	
    	this.graphController.getGraphModel().printMatrix();
    }  	

    /*
     * Insert an edge.
     */
    @FXML
    void handleMenuItem_InsertEdge(ActionEvent event) throws Exception {
        Double weight = Double.parseDouble(this.peso.getText());

        if(weight > 30 || weight < -30 || weight.isNaN())
            JOptionPane.showMessageDialog(null,"Inserire un peso da 15 a -15.");
        else {
            NodeModel start = new NodeModel(this.edgeCBoxOne.getValue().toString().charAt(0) - 65);
            NodeModel end = new NodeModel(this.edgeCBoxTwo.getValue().toString().charAt(0) - 65);
            EdgeModel newEdge = new EdgeModel(start, end, weight.intValue());
            graphController.insertEdge(newEdge);
            this.graphPane.getChildren().add(this.graphController.getGraphView().getEdge(start.getIndex(), end.getIndex()).printEdge());

            this.print();
            initComboBox();
            this.peso.setText("");
        }
        this.graphController.getGraphModel().printMatrix();
    }
    
    @FXML
    void handleMenuItem_DeleteEdge(ActionEvent event) throws Exception {
        NodeModel startEdge = new NodeModel((this.edgeDBox1.getValue().toString().charAt(0))-65);
        NodeModel endEdge = new NodeModel(this.edgeDBox2.getValue().toString().charAt(0)-65);
        EdgeModel deleteEdge = new EdgeModel(startEdge, endEdge);
        graphController.deleteEdge(deleteEdge);
        initComboBox();
        
        this.print();
        this.graphController.getGraphModel().printMatrix();
    }
    

    @FXML
    void initialize() {
    	assert n_nodi != null : "fx:id=\"n_nodi\" was not injected: check your FXML file 'Home.fxml'.";
        assert runMenuItem != null : "fx:id=\"runMenuItem\" was not injected: check your FXML file 'Home.fxml'.";
        assert sbsMenuItem != null : "fx:id=\"sbsMenuItem\" was not injected: check your FXML file 'Home.fxml'.";
        assert stopMenuItem != null : "fx:id=\"stopMenuItem\" was not injected: check your FXML file 'Home.fxml'.";
        assert stepMenuItem != null : "fx:id=\"stepMenuItem\" was not injected: check your FXML file 'Home.fxml'.";
        assert graphPane != null : "fx:id=\"graphPane\" was not injected: check your FXML file 'Home.fxml'.";
        assert outputTextArea != null : "fx:id=\"outputTextArea\" was not injected: check your FXML file 'Home.fxml'.";
        assert outputTextArea1 != null : "fx:id=\"outputTextArea1\" was not injected: check your FXML file 'Home.fxml'.";
        assert coordinateLabel != null : "fx:id=\"coordinateLabel\" was not injected: check your FXML file 'Home.fxml'.";
        assert scrollPaneVisited != null : "fx:id=\"scrollPaneVisited\" was not injected: check your FXML file 'Home.fxml'.";
        assert vBoxVisited != null : "fx:id=\"vBoxVisited\" was not injected: check your FXML file 'Home.fxml'.";
        assert scrollPaneParents != null : "fx:id=\"scrollPaneParents\" was not injected: check your FXML file 'Home.fxml'.";
        assert vBoxParents != null : "fx:id=\"vBoxParents\" was not injected: check your FXML file 'Home.fxml'.";

    }
}

