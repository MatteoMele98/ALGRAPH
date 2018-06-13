package algraph.view;

import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class ParentItem {
	Text number;
	HBox gr;
	
	public ParentItem(Integer x){
		this.gr = new HBox(); 
		this.number = new Text();
		this.number.setText(x.toString());
		
		this.number.setTextAlignment(TextAlignment.CENTER);
		this.number.setStyle("-fx-font: 18 arial;");
		this.number.setStroke(Color.rgb(0, 206, 201,1.0));
		this.number.setStrokeWidth(2);
	
		this.gr.getChildren().add(this.number);
		this.gr.setVisible(true);
		this.gr.setStyle("-fx-padding: 5;" + "-fx-border-style: solid inside;"
		        + "-fx-border-width: 3;" + "-fx-border-insets: 5;"
		        + "-fx-border-radius: 3;" + "-fx-border-color: black;");
		this.gr.setSpacing(10);
	}
	
	public void setNumber(Integer n) {
		this.number.setText(n.toString());
	}
	
	public Text getNumber() {
		return this.number;
	}
	
	public HBox printParentItem() {
		return this.gr;
	}
}
