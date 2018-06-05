package algraph.utils;

public class CircleLine {
		/*
		 * pos = 0 se intersezione tra retta e nodo partenza
		 * 		 1 									arrivo
		 * 
		 * Trova le intersezioni tra la retta passante da start a end
		 * e la circonferenza di centro c e raggio r
		 */
	    public static Point getCircleLineIntersectionPoint(Point start,Point end, Point center, double radius, int pos) {
	        double baX = end.getX() - start.getX();
	        double baY = end.getY() - start.getY();
	        double caX = center.getX() - start.getX();
	        double caY = center.getY() - start.getY();

	        double a = baX * baX + baY * baY;
	        double bBy2 = baX * caX + baY * caY;
	        double c = caX * caX + caY * caY - radius * radius;

	        double pBy2 = bBy2 / a;
	        double q = c / a;

	        double disc = pBy2 * pBy2 - q;
	        if (disc < 0) {
	            return null;
	        }
	        // if disc == 0 ... dealt with later
	        double tmpSqrt = Math.sqrt(disc);
	        double abScalingFactor1 = -pBy2 + tmpSqrt;
	        double abScalingFactor2 = -pBy2 - tmpSqrt;

	        Point p1 = new Point(start.getX() - baX * abScalingFactor1, start.getY() - baY * abScalingFactor1);
	        
	        if (disc == 0) return p1;
	       
	        Point p2 =  new Point(start.getX() - baX * abScalingFactor2, start.getY() - baY * abScalingFactor2);
	       
	        if(pos == 0)
	        	return p2;
	        else 
	        	return p1;
	    }

}
