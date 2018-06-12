package algraph.window;

import algraph.model.EdgeModel;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/*
public class InsertEdge {
	private static EdgeModel edge;
	
	public static void display(String message) {
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Inserisci arco");
		window.setMinWidth(500);
		window.setMinHeight(500);
*/		
		/*
		 * scegli nodo u e v
		 */
/*
		NodeModel start = new NodeModel(0);
		NodeModel end = new NodeModel(1);
		Button closeButton = new Button("CREA");
		closeButton.setOnAction(e -> {
			edge =  new EdgeModel(start,end,1);
			window.close();
		});
		
		VBox layout = new VBox(35);
		layout.getChildren().addAll(label, closeButton);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();		
	}
	
	public EdgeModel getEdge() {
		return edge;
	}
}
*/