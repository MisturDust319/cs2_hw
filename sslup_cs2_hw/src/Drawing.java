import java.awt.Color;
import java.awt.Graphics;

class Drawing {
		// store coordinates
		int x1, y1, x2, y2;
		// upper left corner 
		int x, y;
		// width and height
		int width, height;
		// the shape is stored as an int
		int shape;
		// color is a special object
		Color filledColor;
		
		// Constants for shapes
	    public static final int CIRCLE = 1;
	    public static final int ROUNDED_RECTANGLE = 2;
	    public static final int RECTANGLE_3D = 3;
		
		Drawing(int x1, int y1, int x2, int y2, int shape, Color color ) {
			this.x1 = x1;
			this.x2 = x2;
			this.y1 = y1;
			this.y2 = y2;
			this.shape = shape;
			filledColor = color;
			
			// calculate width/height
			this.width = Math.abs(x1 - x2);
			this.height = Math.abs(y1 - y2);
			
			// determine the upper-left corner of bounding rectangle
	        x = Math.min(x1, x2);
	        y = Math.min(y1, y2);
		}
		
		public String getInfo() {
			String shape = "";
			String coordinates = "(" + x + ", " + y + ")";
			
			if(this.shape == ROUNDED_RECTANGLE) {
				shape = "Rounded Rectangle";
			}
			else if(this.shape == CIRCLE) {
				shape = "Circle";
			}
			else {
				shape = "Rectangle";
			}
			
			return shape + " " + coordinates;
		}
		
		public void drawShape(Graphics g) {
	    	if(filledColor != null)
	            g.setColor(filledColor);
	        switch (shape) {
	            case ROUNDED_RECTANGLE :
	                if(filledColor == null)
	                    g.drawRoundRect(x, y, width, height, width/4, height/4);
	                else
	                    g.fillRoundRect(x, y, width, height, width/4, height/4);
	                break;
	            case CIRCLE :
	                int diameter = Math.max(width, height);
	                if(filledColor == null)
	                    g.drawOval(x, y, diameter, diameter);
	                else
	                    g.fillOval(x, y, diameter, diameter);
	                break;
	            case RECTANGLE_3D :
	                if(filledColor == null)
	                    g.draw3DRect(x, y, width, height, true);
	                else
	                    g.fill3DRect(x, y, width, height, true);
	                break;
	        }
		}
}
