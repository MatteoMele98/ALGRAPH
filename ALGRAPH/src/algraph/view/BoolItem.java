package algraph.view;

import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class BoolItem {
	Text label;
	Text bool;
	HBox gr;
	
	public BoolItem(String s){
		this.gr = new HBox(); 
		this.label = new Text();
		this.bool = new Text();
		
		this.label.setText(s);
		this.label.setTextAlignment(TextAlignment.CENTER);
		this.label.setStyle("-fx-font: 18 arial;");
		this.label.setStroke(Color.BLACK);
		this.label.setStrokeWidth(1);
		
		this.bool.setText("FALSE");
		this.bool.setFill(Color.RED);
		this.bool.setTextAlignment(TextAlignment.CENTER);
		this.bool.setStroke(Color.RED);
		this.bool.setStrokeWidth(1);
		this.bool.setStyle("-fx-font: 18 arial;");
	
		this.gr.getChildren().add(this.label);
		this.gr.getChildren().add(this.bool);
		this.gr.setVisible(true);
		this.gr.setStyle("-fx-padding: 5;" + "-fx-border-style: solid inside;"
		        + "-fx-border-width: 3;" + "-fx-border-insets: 5;"
		        + "-fx-border-radius: 3;" + "-fx-border-color: black;");
		this.gr.setSpacing(10);
	}
	
	public void setBool(boolean v) {
		if(v) {
			this.bool.setText("TRUE");
			this.bool.setFill(Color.GREEN);
			this.bool.setStroke(Color.GREEN);
		}
		else {
			this.bool.setText("FALSE");
			this.bool.setFill(Color.RED);
			this.bool.setStroke(Color.RED);
		}
	}
	
	public Text getBool() {
		return this.bool;
	}
	public HBox printBoolItem() {
		return this.gr;
	}
}
