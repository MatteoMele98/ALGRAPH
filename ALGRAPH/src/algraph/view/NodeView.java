package algraph.view;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import algraph.utils.*;


public class NodeView {
	private int number;
	//private Boolean exist
	
	
	//private Integer value;
	private Point p; //centro del nodo
	private boolean col;
	private Pane sp;
	private Circle circle;
	
	/*
	 * n = label of node
	 * c = center of the node
	 */
	public NodeView(int n, Point c) {
		this.number=n;
		this.col=false;
		this.p = c;
		
		this.circle = new Circle(25,Color.WHITE);
		circle.setCenterX(c.getX()+25);
		circle.setCenterY(c.getY()+25);
		circle.setStroke(Color.BLACK);
		circle.setStrokeWidth(4);
		
		Text t2 = new Text();
	 	t2.setText(String.valueOf(this.number));
	 	Font f2 = new Font("Arial",30);
	 	t2.setFill(Color.BLACK);
	 	t2.setFont(f2);
	 	
	 	this.sp = new StackPane();
	 	this.sp.getChildren().add(circle);
	 	this.sp.getChildren().add(t2);
	 	sp.setLayoutX(c.getX());
	 	sp.setLayoutY(c.getY());
	 	
	}
	public Pane getDisegno() {
		return this.sp;
	}
	
	public int get_number() {
		return this.number;
	}
	
	public void setCol(boolean c) {
		this.col=c;
	}
	public boolean getCol() {
		return this.col;
	}
	public double getPosX() {
		return this.p.getX();
	}
	public double getPosY() {
		return this.p.getY();
	}
	public void setPosition(Point p2) {
		this.p = p2;
	}
	public int getPosXCircle() {
		return (int) this.circle.getCenterX();
	}
	
	public int getPosYCircle() {
		return (int) this.circle.getCenterY();
	}
	
}
