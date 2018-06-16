package algraph.view;

import algraph.utils.Colors;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class PriorityItem {
	Text label;
	Text priority;
	HBox gr;
	
	public PriorityItem(String s){
		this.gr = new HBox(); 
		this.label = new Text();
		this.priority = new Text();
		
		this.label.setText(s);
		this.label.setTextAlignment(TextAlignment.CENTER);
		this.label.setStyle("-fx-font: 18 arial;");
		this.label.setStroke(Colors.VISITING);
		this.label.setStrokeWidth(2);
		
		this.priority.setText(String.valueOf(Character.toString('\u221E'))); //+Inf symbol
		this.priority.setFill(Color.BLACK);
		this.priority.setTextAlignment(TextAlignment.CENTER);
		this.priority.setStroke(Color.BLACK);
		this.priority.setStrokeWidth(1);
		this.priority.setStyle("-fx-font: 18 arial;"); 
	
		this.gr.getChildren().add(this.label);
		this.gr.setVisible(true);
		this.gr.setStyle("-fx-padding: 5;" + "-fx-border-style: solid inside;"
		        + "-fx-border-width: 3;" + "-fx-border-insets: 5;"
		        + "-fx-border-radius: 3;" + "-fx-border-color: black;");
		this.gr.setSpacing(10);
	}
		
	public void setPriority(String s) {
		this.priority.setText(s);
	}	
	
	public HBox printPriorityItem() {
		return this.gr;
	}
}
