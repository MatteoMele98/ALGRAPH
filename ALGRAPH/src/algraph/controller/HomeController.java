package algraph.controller;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import algraph.model.NodeModel;
import algraph.view.GraphView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
	

    @FXML
    private TextField nodeToDelete;
    
    @FXML
    private ResourceBundle resources;

    @FXML
    private TextField n_nodi;
    
    @FXML
    private URL location;

    @FXML
    private SplitMenuButton splitMenuItem;

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
    void handleLineOn(MouseEvent event) {
    	
    }
    
    /*
     * print the entire updated graph
     */
    private void print() {
    	this.graphPane.getChildren().clear();
    	for(int i=0; i < MAX_NODES; i++) {
    		if(this.graphController.getGraphView().getNode(i)!=null) {
    			this.graphPane.getChildren().add(this.graphController.getGraphView().getNode(i).printNode());
    		}
			for(int j=0; j < MAX_NODES; j++) {
				if(this.graphController.getGraphView().getEdge(i, j) != null) {
					this.graphPane.getChildren().add(this.graphController.getGraphView().getEdge(i, j).printEdge());
				}
			}
    	}
    }
    
    @FXML
    void handleMenuDeleteNode(ActionEvent event) throws Exception {
    	NodeModel deletedNode = new NodeModel(Integer.parseInt(this.nodeToDelete.getText()));
    	graphController.deleteNode(deletedNode);
    	
    	this.print();
       	this.nodeToDelete.setText("");
    }  	

    @FXML
    void handleButtonClick_GraphPane(MouseEvent event) {

    }

    @FXML
    void handleMenuItem_About(ActionEvent event) {

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
    	this.print();
    }
    
    @FXML
    void handleMenuItem_NodesGraph(ActionEvent event) throws NumberFormatException, Exception {
    	this.graphController = new GraphController(Integer.parseInt(n_nodi.getText()),false);
    	this.print();
    }
    
    @FXML
    void handleMenuItem_RunAnimation(ActionEvent event) {

    }

    @FXML
    void handleMenuItem_Save(ActionEvent event) {

    }

    @FXML
    void handleMenuItem_Stop(ActionEvent event) {

    }

    @FXML
    void handleMouseMove_GraphPane(MouseEvent event) {

    }
    
    @FXML
    public void handleMenuItem_InsertNode(ActionEvent event) throws Exception {
    	graphController.insertNode();
    	this.print();
    }

    @FXML
    void handleMenuItem_InsertEdge(ActionEvent event) throws Exception {
    	if(this.nodeOne.getText().length()!=0 && this.nodeTwo.getText().length()!=0 && this.peso.getText().length()!=0) {
    		NodeModel start = new NodeModel(Integer.parseInt(this.nodeOne.getText()));
    		NodeModel end = new NodeModel(Integer.parseInt(this.nodeTwo.getText()));
    		int weight = Integer.parseInt(this.peso.getText());
    		EdgeModel newEdge = new EdgeModel(start,end,weight);
    		
    		graphController.insertEdge(newEdge);
    		   		
    		this.graphPane.getChildren().add(this.graphController.getGraphView().getEdge(start.getIndex(), end.getIndex()).printEdge());
    		this.nodeOne.setText("");
    		this.nodeTwo.setText("");
    		this.peso.setText("");
    	}else {
    		/*restituisce un messaggio di errore*/
    	}
    }
/*    public static Void showPrefWindow(Event e) {

    	try {
    		Parent root = FXMLLoader.load(getClass().getResource("applicationSettingsView.fxml"));
    		Stage stage = new Stage();
    		stage.setTitle("Animation Settings");
    		stage.setScene(new Scene(root, 400, 150));
    		stage.show();
    		stage.setResizable(false);
    	} catch (IOException exc) {
    		exc.printStackTrace();
    	}
    	
    	return null;
    }*/
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

