package algraph.controller;


import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;

import algraph.model.NodeModel;
import algraph.service.AlgorithmHandler;
import algraph.view.BoolItem;
import algraph.view.PriorityItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import algraph.model.EdgeModel;

public class HomeController {
	private static final int MAX_NODES = 15;

	private GraphController graphController;
	private PriorityController priorityController;
	private VisitedController visitedController;
	private PseudoCodeController pseudoCodeController;
	
	private AlgorithmHandler algorithmHandler;
	
	public HomeController() throws Exception {
		this.graphController = new GraphController(this);
		this.visitedController = new VisitedController(this);
		this.priorityController = new PriorityController(this);
		this.pseudoCodeController = new PseudoCodeController(this);
	}

    @FXML
    private ResourceBundle resources;

    @FXML
    private TextField n_nodi;
    
    @FXML
    private URL location;

    @FXML
    private Pane graphPane;

    @FXML
    private TextArea outputTextArea;

    @FXML
    private TextArea outputTextArea1;

    @FXML
    private ScrollPane scrollPaneVisited;

    @FXML
    private VBox vBoxVisited;

    @FXML
    private ScrollPane scrollPaneParents;

    @FXML
    private VBox vBoxParents;

    @FXML
    private TextField peso;
    
    private File selectedFile;

    private Scanner scanner;
    
    @FXML
    private ComboBox<String> deleteComboBox;
    
    @FXML
    private ComboBox<String> edgeCBoxOne;

    @FXML
    private ComboBox<String> edgeCBoxTwo;

    @FXML
    private ComboBox<String> startComboBox1;

    @FXML
    private ComboBox<String> startComboBox3;

    @FXML
    private ComboBox<String> edgeDBox1;

    @FXML
    private ComboBox<String> edgeDBox2;

    @FXML
    private ComboBox<String> stepComboBox2;

    @FXML
    private MenuItem info;
    @FXML
    private MenuItem openFile;

    @FXML
    private MenuItem save;
    @FXML
    private MenuItem randomGraph;
    @FXML
    private MenuItem insertNode;

    @FXML
    private MenuItem deleteNode;
    @FXML
    private MenuItem createEdge;
    @FXML
    private MenuItem deleteEdge1;
    @FXML
    private MenuItem deleteEdge2;

    @FXML
    private MenuItem startAnimAll;
    @FXML
    private MenuItem startAnimStep;
    @FXML
    private MenuItem startStep;
    @FXML
    private MenuItem graphNoEdge;

    @FXML
    private MenuItem nextStep;
    Integer start=null;

    //======================= FUNZIONI DI STAMPA E AGGIORNAMENTO GRAFICO ============================
    @FXML
    void handleButtonClick_GraphPane(MouseEvent event) {

    }
    
    @FXML
    void handleMouseMove_GraphPane(MouseEvent event) {

    }
    
    private void disableButton(boolean disable) {
    	 this.openFile.setDisable(disable);
         this.save.setDisable(disable);
         this.randomGraph.setDisable(disable);
         this.insertNode.setDisable(disable);
         this.deleteNode.setDisable(disable);
         this.createEdge.setDisable(disable);
         this.deleteEdge1.setDisable(disable);
         this.deleteEdge2.setDisable(disable);
         this.startAnimAll.setDisable(disable);
         this.startAnimStep.setDisable(disable);
         this.startStep.setDisable(disable);
         this.graphNoEdge.setDisable(disable);
    }

    /*
     * print the entire updated graph
     */
    private void print() {
    	this.graphPane.getChildren().clear();
    	this.vBoxVisited.getChildren().clear();
    	this.vBoxParents.getChildren().clear();
    	
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
    	this.printPseudoCode();
    	this.printVect();
        this.outputTextArea1.setText(this.graphController.getGraphModel().printMatrix().toString());
    }
    
    private void printVect() {
    	this.vBoxVisited.getChildren().clear();
    	this.vBoxParents.getChildren().clear();
    	
    	BoolItem n;
    	for(Map.Entry<NodeModel, BoolItem> visit : this.visitedController.boolItemMap.entrySet()) {
    		n = visit.getValue();
    		this.vBoxVisited.getChildren().add(n.printBoolItem());
    	}
    			
    	
    	PriorityItem p;
    	for(Map.Entry<NodeModel, PriorityItem> priority : this.priorityController.priorityItemMap.entrySet()) {
    		p = priority.getValue();
    		this.vBoxParents.getChildren().add(p.printPriorityItem());
    	}	
    }
    
    public void printPseudoCode() {
    	this.outputTextArea.appendText(this.pseudoCodeController.getString());
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
            this.stepComboBox2.getItems().add(node.getValue().getLabel());
            this.startComboBox3.getItems().add(node.getValue().getLabel());
            this.edgeDBox1.getItems().add(node.getValue().getLabel());
            this.edgeDBox2.getItems().add(node.getValue().getLabel());
        }
    }
    
    //======================================================================================================

    @FXML
    void handleMenuItem_Close(ActionEvent event) {
        System.exit(0);
    }    
    
    //============================================ ABOUT ====================================================

    @FXML
    void handleMenuItem_About(ActionEvent event) {
    	try {
            Desktop.getDesktop().browse(new URL("https://github.com/MatteoMele98/ALGRAPH/blob/master/RELAZIONE.md#algraph").toURI());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (java.net.URISyntaxException e) {
            e.printStackTrace();
        }
    }
    
  //========================================================================================================

  //================================ SALVATAGGIO E APERTURA FILE ===========================================
    @FXML
    void handleMenuItem_Save(ActionEvent event) {
    	try
        {
            FileOutputStream prova = new FileOutputStream("C://Users//utente//Desktop//ALGRAPH//ALGRAPH//SavedFile.txt");
            PrintStream scrivi = new PrintStream(prova);
            for(int i=0; i<this.graphController.getGraphModel().getCurrentNumberNodes(); i++){
                if(i!=0) scrivi.println();
                for(int j=0; j<this.graphController.getGraphModel().getCurrentNumberNodes(); j++){
                    scrivi.print(this.graphController.getGraphModel().getMatrix()[i][j]+" ");
                }
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Save Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Salvataggio avvenuto con successo!");
            alert.showAndWait();

        }
        catch (IOException e)
        {
            System.out.println("Errore: " + e);
            System.exit(1);
        }
    }    
    
    @FXML
    void handleMenuItem_OpenFile(ActionEvent event) throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Text File");
        fileChooser.setInitialDirectory(new File("."));
        this.selectedFile = fileChooser.showOpenDialog(null);
        double dim=0;
        Integer x[][] = new Integer[MAX_NODES][MAX_NODES];
        //controlla che sia stato selezionato un file
        if (selectedFile != null) {
            this.scanner = new Scanner(new FileReader(selectedFile));
            if (checkFile()) {
                this.scanner.close();
                this.scanner=new Scanner(new FileReader(selectedFile));
                //devo chiudere e aprire lo scanner per far ripartire lo scanner dalla prima riga
                dim=dim();
                this.scanner.close();
                this.scanner = new Scanner(new FileReader(selectedFile));
                if (dim==0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("C'è un errore nella matrice.Potresti aver inserito un peso non compreso tra -30 e 30\noppure potrebbe non essere una matrice quadrata! Controlla!");
                    alert.showAndWait();
                }else {
                    //carica la matrice
                    for (int i = 0; i < dim && this.scanner.hasNextInt(); i++){
                        for(int j = 0; j < dim && this.scanner.hasNextInt(); j++){
                            x[i][j] = this.scanner.nextInt();
                        }
                    }
                    if(checkMatrix(x,(int)dim)){
                        //stampa la matrice
                        for (int i1 = 0; i1 < dim; i1++) {
                          for (int j1 = 0; j1 < dim; j1++) {
                             System.out.print(x[i1][j1] + " ");
                         }
                            System.out.println();
                     }
                     this.scanner.close();
                     this.graphController = new GraphController((int)dim,false,this);
                     this.graphController.setMatrix(x);
                     this.initComboBox();
                     this.print();
                     this.graphController.getGraphModel().printMatrix();
                 }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("C'è un errore nel file!");
                    alert.showAndWait();
                    }
                }

            }
        }
    }
    //checkMatrix

    public boolean checkMatrix(Integer mat[][], int d){
        boolean flag=true;
        for(int i = 0; i < d && flag; i++){
            if(mat[i][i]!=0) flag=false;
            for(int j = 0; j < d && flag; j++){
                if(mat[i][j]!=mat[j][i]) flag=false;
            }
        }
        return flag;
    }

    //calcola la dimensione della matrice, serve per fare il controllo se � o meno una matrice n*n
    public double dim(){
        double dimensione=0;
        boolean flag = false;
        while (this.scanner.hasNextInt() && !flag) {
            int x=scanner.nextInt();
            if(x<-30 || x>30){
                flag=true;
                dimensione=0;
            }else
                dimensione++;
        }
        System.out.println(Math.sqrt(dimensione));
        if((Math.sqrt(dimensione) == (int)Math.sqrt(dimensione)) && dimensione!=0)
            return Math.sqrt(dimensione);
        else return dimensione;
    }
    
    
    //controlla file
    public boolean checkFile(){
        boolean flag=false;
        int numero;
        while(this.scanner.hasNext() && !flag){
            try {
                Integer integer = Integer.parseInt(this.scanner.next());
                numero = integer.intValue();
                flag = false;
            }
            catch (Exception e) {
                flag = true;
            }
        }
        return !flag;
    }
   //=====================================================================================================
    
   //====================================== MODIFICA GRAFO ===============================================
    
    @FXML
    void handleMenuItem_RandomGraph(ActionEvent event) throws Exception {
        this.nextStep.setDisable(true);
        this.outputTextArea1.clear();
        this.outputTextArea.clear();
    	this.graphController = new GraphController(10,true,this);
    	
    	this.initComboBox();
    	this.print();    	
    	this.graphController.getGraphModel().printMatrix();
    }
    
    @FXML
    void handleMenuItem_NodesGraph(ActionEvent event) throws NumberFormatException, Exception {
        this.nextStep.setDisable(true);
        this.outputTextArea1.clear();
        this.outputTextArea.clear();
        
        Integer numberNodes = Integer.parseInt(n_nodi.getText());
        if(numberNodes < 3 || numberNodes > 15) {
        	Alert alert = new Alert(AlertType.ERROR);
        	alert.setTitle("Error Dialog");
        	alert.setHeaderText("Inserimento nodi");
        	alert.setContentText("E' possibile inserire sa un minimo di 3 a un massimo di 15 nodi!");

        	alert.showAndWait();
        } else {
        	this.graphController = new GraphController(numberNodes,false,this);
        }   	
        
        this.initComboBox();     	
    	this.print();
    	this.graphController.getGraphModel().printMatrix();
    }
    
    @FXML
    public void handleMenuItem_InsertNode(ActionEvent event) throws Exception {
    	if(graphController.getGraphModel().getCurrentNumberNodes() <= 14) {
    	 		graphController.insertNode();
    	 } else {
    		Alert alert = new Alert(AlertType.ERROR);
         	alert.setTitle("Error Dialog");
         	alert.setHeaderText("Inserimento nodi");
         	alert.setContentText("E' possibile inserire massimo 15 nodi!");

         	alert.showAndWait();
    	 }
    	this.print();
 		this.initComboBox();
 		this.graphController.getGraphModel().printMatrix();    		
    }
    
    @FXML
    void handleMenuDeleteNode(ActionEvent event) throws Exception {
    	if(this.graphController.getGraphModel().getCurrentNumberNodes() == 3) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Error Dialog");
    		alert.setHeaderText("Cancellazione Nodo");
    		alert.setContentText("Il grafo deve avere minimo 3 nodi!");

    		alert.showAndWait();
    	} else {
    		int c = this.deleteComboBox.getValue().toString().charAt(0);
        	NodeModel deletedNode = new NodeModel(c-65);
        	graphController.deleteNode(deletedNode);
    	}
    	this.initComboBox();
    	this.print();
    	
    	this.graphController.getGraphModel().printMatrix();
    }  	

    /*
     * Insert an edge.
     */
    @FXML
    void handleMenuItem_InsertEdge(ActionEvent event) throws Exception {
        Integer weight = Integer.parseInt(this.peso.getText());

        if(weight > 30 || weight < -30) {
        	Alert alert = new Alert(AlertType.ERROR);
        	alert.setTitle("Error Dialog");
        	alert.setHeaderText("Inserimento arco");
        	alert.setContentText("E' possibile un arco di peso compreso tra -15 e +15!");

        	alert.showAndWait();
        }
            
        else {
            NodeModel start = new NodeModel(this.edgeCBoxOne.getValue().toString().charAt(0) - 65);
            NodeModel end = new NodeModel(this.edgeCBoxTwo.getValue().toString().charAt(0) - 65);
        	EdgeModel newEdge = new EdgeModel(start, end, weight);
        	
        	if(newEdge.getStartNode() == newEdge.getEndNode()) {
            	Alert alert = new Alert(AlertType.ERROR);
            	alert.setTitle("Error Dialog");
            	alert.setHeaderText("Inserimento arco");
            	alert.setContentText("Non è possibile inserire un arco tra lo stesso nodo!");

            	alert.showAndWait();
        	} else {
        		 graphController.insertEdge(newEdge);
                 this.graphPane.getChildren().add(this.graphController.getGraphView().getEdge(start.getIndex(), end.getIndex()).printEdge());
        	}        
            
        }
        this.print();
        initComboBox();
        this.peso.setText("");
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
    
    //====================================================================================================
    
    //========================================= ANIMATION ==============================================

    @FXML
    void handleMenuItem_RunAnimation(ActionEvent event) throws Exception  {
        this.disableButton(true);
        
        this.algorithmHandler = new AlgorithmHandler(this,graphController,visitedController,
				priorityController, pseudoCodeController);
        NodeModel root;
		if(this.startComboBox1.getValue()==null) {
            root = new NodeModel(this.startComboBox3.getValue().toString().charAt(0) - 65);
            this.algorithmHandler.restartAlgotithm(root, 1000);
            this.algorithmHandler.start();
        }else{
		    root = new NodeModel(this.startComboBox1.getValue().toString().charAt(0) - 65);
		    this.algorithmHandler.restartAlgotithm(root,1000);
            this.algorithmHandler.noPauseexecuteAll();
        }
		

		
		
		this.print();
    }
    
    @FXML
    void handleMenuItem_NextStep(ActionEvent event) {
        if(this.start==null) {
            this.algorithmHandler = new AlgorithmHandler(this, graphController, visitedController,
                    priorityController, pseudoCodeController);
            NodeModel root;
            root = new NodeModel(this.stepComboBox2.getValue().toString().charAt(0) - 65);
            this.start=this.stepComboBox2.getValue().toString().charAt(0) - 65;
            this.algorithmHandler.restartAlgotithm(root, 1000);
            this.print();
            this.disableButton(true);
            this.nextStep.setDisable(false);
            
           
        }else{
            this.algorithmHandler.noPauseexecuteStep(true);
            this.print();
        }
        
        this.openFile.setDisable(false);
        this.save.setDisable(false);
        this.randomGraph.setDisable(false);
        this.insertNode.setDisable(false);
        this.deleteNode.setDisable(false);
        this.createEdge.setDisable(false);
        this.deleteEdge1.setDisable(false);
        this.deleteEdge2.setDisable(false);
        this.startAnimAll.setDisable(false);
        this.startAnimStep.setDisable(false);
        this.startStep.setDisable(false);
        this.graphNoEdge.setDisable(false);
    }

    //====================================================================================================
    @FXML
    void initialize() {
        assert n_nodi != null : "fx:id=\"n_nodi\" was not injected: check your FXML file 'Home.fxml'.";
        assert graphPane != null : "fx:id=\"graphPane\" was not injected: check your FXML file 'Home.fxml'.";
        assert outputTextArea != null : "fx:id=\"outputTextArea\" was not injected: check your FXML file 'Home.fxml'.";
        assert outputTextArea1 != null : "fx:id=\"outputTextArea1\" was not injected: check your FXML file 'Home.fxml'.";
        assert scrollPaneVisited != null : "fx:id=\"scrollPaneVisited\" was not injected: check your FXML file 'Home.fxml'.";
        assert vBoxVisited != null : "fx:id=\"vBoxVisited\" was not injected: check your FXML file 'Home.fxml'.";
        assert scrollPaneParents != null : "fx:id=\"scrollPaneParents\" was not injected: check your FXML file 'Home.fxml'.";
        assert vBoxParents != null : "fx:id=\"vBoxParents\" was not injected: check your FXML file 'Home.fxml'.";

    }
}

