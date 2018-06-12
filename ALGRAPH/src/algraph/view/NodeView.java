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
	
	private Point center; //centro del nodo
	private Pane sp;
	private Circle circle;
	
	/*
	 * n = label of node
	 * c = center of the node
	 */
	public NodeView(NodeModel node, Point center) {
		this.number = node.getIndex();
		this.center = center;
		
		this.circle = new Circle(25,Color.WHITE);
		circle.setCenterX(center.getX()+25);
		circle.setCenterY(center.getY()+25);
		circle.setStroke(Color.rgb(52, 73, 94,1.0)); //nero
		circle.setStrokeWidth(4);
		
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
	
	public void visiting() {
		this.circle.setStroke(Color.rgb(0, 206, 201,1.0)); //verde
	}
	
	public void visited() {
		this.circle.setStroke(Color.rgb(231, 76, 60,1.0)); //rosso
	}
	
	
	public double getPosX() {
		return this.center.getX();
	}
	public double getPosY() {
		return this.center.getY();
	}
	public void setPosition(Point p2) {
		this.center = p2;
	}
	public int getPosXCircle() {
		return (int) this.circle.getCenterX();
	}
	
	public int getPosYCircle() {
		return (int) this.circle.getCenterY();
	}
	
}
