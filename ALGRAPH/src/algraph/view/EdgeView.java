package algraph.view;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Path;
import javafx.scene.shape.StrokeLineJoin;

import algraph.utils.*;


public class EdgeView extends Path{
	  Group edge = new Group();
	  Line arrow;
	  
	  public EdgeView() {
		  
	  }
	 
	  /*
	   * crea una line da start(centro sp1) a end(centro sp2)
	   * r = raggio del nodo
	   */
      public EdgeView (Point start, Point end, double radius){	   
    	   
            Point pos1 = CircleLine.getCircleLineIntersectionPoint(start,end, start, radius,0);
            Point pos2 = CircleLine.getCircleLineIntersectionPoint(start, end, end, radius,1);

            this.arrow = new Line();
            arrow.setStrokeLineJoin(StrokeLineJoin.MITER);
            arrow.setStartX(pos1.getX()+25); 
            arrow.setStartY(pos1.getY()+25);

			Double radius1 = 10.0;
        	double	alphaAngle = Math.atan2(end.getY() - start.getY(), end.getX() - start.getX());

        	Double offsetX = Math.abs(radius1 * Math.cos(alphaAngle));
        	Double offsetY = Math.abs(radius1 * Math.sin(alphaAngle));

        	if (start.getX() > end.getX())
        		offsetX *= -1;

        	if (start.getY() > end.getY())
        		offsetY *= -1;
        	
        	arrow.setEndX(pos2.getX()-offsetX+25);
        	arrow.setEndY(pos2.getY()-offsetY+25);
        	arrow.setStroke(Colors.DEFAULT);
        	arrow.setStrokeWidth(3);
        	
    
            edge.getChildren().add(arrow);
        }
      
      public void switchColor(Color newColor) {
    	 this.arrow.setStroke(newColor); //rosso
      }
   
      
    public Group printEdge() {
      	return this.edge;
    }
}
