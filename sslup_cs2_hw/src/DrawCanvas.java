/******************************************************************
 *  COURSE:             CSC231 Computer Science and Programming II
 *	Lab:			    Number 4
 *	FILE:				DrawCanvas.java
 *	TARGET:				Java 6.0 and 7.0
 *****************************************************************/

// Import Core Java packages
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.lang.String;

public class DrawCanvas extends Canvas implements MouseListener,
                                                  MouseMotionListener {

    // Container for shapes
	ArrayList<Drawing> drawings = new ArrayList<Drawing>();
	
	// helper class
	// maintains all the 
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
		
		private void drawShape(Graphics g) {
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
	
	// Constants for shapes
    public static final int CIRCLE = 1;
    public static final int ROUNDED_RECTANGLE = 2;
    public static final int RECTANGLE_3D = 3;

    // Coordinates of points to draw
    private int x1, y1, x2, y2;

    // shape to draw
    private int shape = CIRCLE;
    /**
     * Method to set the shape
     */
    public void setShape(int shape) {
        this.shape = shape;
    }

    // filled color
    private Color filledColor = null;
    /**
     * Method to set filled color
     */
    public void setFilledColor(Color color) {
        filledColor = color;
    }

    /**
     * Constructor
     */
	public DrawCanvas() {
	    addMouseListener(this);
	    addMouseMotionListener(this);
	} // end of constructor

    /**
     * painting the component
     */
    public void paint(Graphics g) {
    	
        // the drawing area
        int x, y, width, height;

        
        // first draw all the stored shapes
        for(int i = 0; i < drawings.size(); i++) {
        	drawings.get(i).drawShape(g);
        }
        
        // determine the upper-left corner of bounding rectangle
        x = Math.min(x1, x2);
        y = Math.min(y1, y2);

        // determine the width and height of bounding rectangle
        width = Math.abs(x1 - x2);
        height = Math.abs(y1 - y2);

        drawShape(g, x, y, width, height, shape); 
    }
    
    /**
     * draw a shape
     */
    private void drawShape(Graphics g, int x, int y, int width, int height, int shape) {
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
    /**
     * Adds a new shape
     */
    private void addNewShape() {
    	Drawing newShape = new Drawing(x1, y1, x2, y2, shape, filledColor);
    	
    	// now store this shape in the list of Drawings
    	drawings.add(newShape);
    }

    /**
     * Implementing MouseListener
     */
    public void mousePressed(MouseEvent event) {
        x1 = event.getX();
        y1 = event.getY();
    }

    public void mouseReleased(MouseEvent event) {
        x2 = event.getX();
        y2 = event.getY();
        
        // once you release, store a new shape
        addNewShape();
        
        repaint();
    }

    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    /**
     * Implementing MouseMotionListener
     */
    public void mouseDragged(MouseEvent event) {
        x2 = event.getX();
        y2 = event.getY();
        repaint();
    }

    public void mouseMoved(MouseEvent e) {}
}
