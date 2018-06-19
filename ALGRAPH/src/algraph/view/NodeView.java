package algraph.view;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


import algraph.model.NodeModel;
import algraph.utils.*;


public class NodeView {
	private int number; //indice del nodo
	private Text label; //testo nel nodo
	Boolean isVisible;
	
	private Point center; //centro del nodo
	private Pane sp;
	private Circle circle;
	private Color color;
	
	public NodeView() {
		this.isVisible = false;
	}
	
	/*
	 * n = label of node
	 * c = center of the node
	 */
	public NodeView(NodeModel node, Point center) {
		this.number = node.getIndex();
		this.isVisible = true;
		this.center = center;
		this.color = Colors.DEFAULT;
		
		this.circle = new Circle(25,Color.WHITE);
		circle.setCenterX(center.getX()+25);
		circle.setCenterY(center.getY()+25);
		circle.setStroke(this.color); //nero
		circle.setStrokeWidth(2);
		
		this.label = new Text();
		this.label.setText(node.getLabel());
	 	Font font = new Font("Arial",30);
	 	this.label.setFill(Color.BLACK);
	 	this.label.setFont(font);
	 	
	 	this.sp = new StackPane();
	 	this.sp.getChildren().add(circle);
	 	this.sp.getChildren().add(this.label);
	 	sp.setLayoutX(center.getX());
	 	sp.setLayoutY(center.getY());
	 	
	}
	
	public Pane printNode() {
		return this.sp;
	}
	
	public int get_number() {
		return this.number;
	}
	
	public void switchColor(Color newColor) {
		this.circle.setFill(newColor);
		this.label.setFill(Color.WHITE);
		this.color = newColor;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	
	public double getPosX() {
		return this.sp.getLayoutX();
	}
	public double getPosY() {
		return this.sp.getLayoutY();
	}
	public void setPosition(Point p2) {
		this.sp.setLayoutX(p2.getX());
		this.sp.setLayoutY(p2.getY());
	}
	public int getPosXCircle() {
		return (int) this.circle.getCenterX();
	}
	
	public int getPosYCircle() {
		return (int) this.circle.getCenterY();
	}

	public Boolean getIsVisible() {
		return isVisible;
	}
	
	/*
	 * @param isVisible, true iff the node will be printed
	 * false otherwise
	 */
	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}
	
}
