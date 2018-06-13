package algraph.view;


import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class BoolItem {
	Text number;
	Text bool;
	HBox gr;
	public BoolItem(Integer x, boolean v){
		this.gr = new HBox(); 
		this.number = new Text();
		this.bool = new Text();
		this.number.setText(x.toString());
		if(v) { 
			this.bool.setText("True");
			this.bool.setFill(Color.GREEN);
			this.bool.setTextAlignment(TextAlignment.CENTER);
			this.bool.setStroke(Color.GREEN);
			this.number.setStrokeWidth(1);
			this.bool.setStyle("-fx-font: 18 arial;");
			this.number.setTextAlignment(TextAlignment.CENTER);
			this.number.setStyle("-fx-font: 18 arial;");
			this.number.setStroke(Color.BLACK);
			this.number.setStrokeWidth(1);
		}
		else {
			this.bool.setText("False");
			this.bool.setFill(Color.RED);
			this.bool.setTextAlignment(TextAlignment.CENTER);
			this.bool.setStroke(Color.RED);
			this.bool.setStrokeWidth(1);
			this.bool.setStyle("-fx-font: 18 arial;");
			
			this.number.setTextAlignment(TextAlignment.CENTER);
			this.number.setStyle("-fx-font: 18 arial;");
			this.number.setStroke(Color.BLACK);
			this.number.setStrokeWidth(1);
		}
		this.gr.getChildren().add(this.number);
		this.gr.getChildren().add(this.bool);
		this.gr.setVisible(false);
		this.gr.setStyle("-fx-padding: 5;" + "-fx-border-style: solid inside;"
		        + "-fx-border-width: 3;" + "-fx-border-insets: 5;"
		        + "-fx-border-radius: 3;" + "-fx-border-color: black;");
		this.gr.setSpacing(10);
	}
	public void setBool(boolean v) {
		if(v) {
			this.bool.setText("True");
			this.bool.setFill(Color.GREEN);
		}
		else {
			this.bool.setText("False");
			this.bool.setFill(Color.RED);
		}
	}
	public void setNumber(Integer n) {
		this.number.setText(n.toString());
	}
	public Text getNumber() {
		return this.number;
	}
	public Text getBool() {
		return this.bool;
	}
	public HBox getHBox() {
		return this.gr;
	}
}
