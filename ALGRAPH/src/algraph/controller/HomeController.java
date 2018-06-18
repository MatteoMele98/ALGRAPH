package algraph.controller;


import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.TreeMap;

import javax.swing.JOptionPane;

import algraph.model.NodeModel;
import algraph.service.AlgorithmHandler;
import algraph.utils.Colors;
import algraph.view.BoolItem;
import algraph.view.GraphView;
import algraph.view.PriorityItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
	private PseudoCodeController pseudoCodeController;
	
	private AlgorithmHandler algorithmHandler;
	
	private boolean pendingExecution;
	//====================================================
	
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

    @FXML
    private ComboBox stepComboBox2;

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



    @FXML
    void handleButtonClick_GraphPane(MouseEvent event) {

    }

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

    @FXML
    void handleMenuItem_AnimationSettings(ActionEvent event) {

    }

	@FXML
    void handleMenuItem_Close(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void handleMenuItem_Debug(ActionEvent event) {

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
            this.nextStep.setDisable(false);
            this.openFile.setDisable(true);
            this.save.setDisable(true);
            this.randomGraph.setDisable(true);
            this.insertNode.setDisable(true);
            this.deleteNode.setDisable(true);
            this.createEdge.setDisable(true);
            this.deleteEdge1.setDisable(true);
            this.deleteEdge2.setDisable(true);
            this.startAnimAll.setDisable(true);
            this.startAnimStep.setDisable(true);
            this.startStep.setDisable(true);
            this.graphNoEdge.setDisable(true);
        }else{
            this.algorithmHandler.noPauseexecuteStep(true);
            this.print();
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
                //System.out.println(x);
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
                //System.out.print("Matrice non valida! Controlla!");
                flag = true;
            }
        }
        return !flag;
    }
    

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
    	this.graphController = new GraphController(Integer.parseInt(n_nodi.getText()),false,this);
    	this.initComboBox(); 	
    	
    	
    	this.print();
    	this.graphController.getGraphModel().printMatrix();
    }
    
    @FXML
    void handleMenuItem_RunAnimation(ActionEvent event) throws Exception  {
        this.openFile.setDisable(true);
        this.save.setDisable(true);
        this.randomGraph.setDisable(true);
        this.insertNode.setDisable(true);
        this.deleteNode.setDisable(true);
        this.createEdge.setDisable(true);
        this.deleteEdge1.setDisable(true);
        this.deleteEdge2.setDisable(true);
        this.startAnimAll.setDisable(true);
        this.startAnimStep.setDisable(true);
        this.startStep.setDisable(true);
        this.graphNoEdge.setDisable(true);
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
    void handleMenuItem_Stop(ActionEvent event) {
//    	//AVVIA TUTTO ALGORITMO
//    	this.algorithmHandler = new AlgorithmHandler(this, graphController,visitedController,
//    												priorityController,pseudoCodeController);
//    	NodeModel root = new NodeModel(this.startComboBox1.getValue().toString().charAt(0)-65);
//
//    	this.algorithmHandler.restartAlgotithm(root);
//    	this.algorithmHandler.start();
//    	this.print();
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
        assert graphPane != null : "fx:id=\"graphPane\" was not injected: check your FXML file 'Home.fxml'.";
        assert outputTextArea != null : "fx:id=\"outputTextArea\" was not injected: check your FXML file 'Home.fxml'.";
        assert outputTextArea1 != null : "fx:id=\"outputTextArea1\" was not injected: check your FXML file 'Home.fxml'.";
        assert scrollPaneVisited != null : "fx:id=\"scrollPaneVisited\" was not injected: check your FXML file 'Home.fxml'.";
        assert vBoxVisited != null : "fx:id=\"vBoxVisited\" was not injected: check your FXML file 'Home.fxml'.";
        assert scrollPaneParents != null : "fx:id=\"scrollPaneParents\" was not injected: check your FXML file 'Home.fxml'.";
        assert vBoxParents != null : "fx:id=\"vBoxParents\" was not injected: check your FXML file 'Home.fxml'.";

    }
}

