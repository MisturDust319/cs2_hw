/*******************************************************************
 *  COURSE:             CSC231 Computer Science and Programming II
 *	Lab:			    Number 5
 *	FILE:				CatchDuke.java
 *	TARGET:				Java 6.0 and 7.0
 *	AUTHOR:				Stan Slupecki
 *******************************************************************/

// Import Core Java packages
import java.awt.*;
import java.awt.event.*;

public class CatchDuke extends Frame implements ItemListener, ActionListener {

    static final int MAX_LEVEL = 5;
    static final int INITIAL_LEVEL = 1;

    // Labels and controls
    Label hitLabel;
    Label missLabel;
    Button reset;
    Choice levelChoice;

    // the canvas
    DukeCanvas canvas;

    /**
     * Constructor
     */
	public CatchDuke() {
	    setTitle("Catch the Duke");
        setLayout(new BorderLayout());

        // create display and control
        Panel topPanel = new Panel(new GridLayout(2, 1));
        add(topPanel, BorderLayout.NORTH);
        Panel labelPanel = new Panel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(labelPanel);
        Label label = new Label("Number of Hits:");
        labelPanel.add(label);
        hitLabel = new Label("    0");
        labelPanel.add(hitLabel);
        label = new Label("Number of Misses:");
        labelPanel.add(label);
        missLabel = new Label("    0");
        labelPanel.add(missLabel);

        Panel controlPanel = new Panel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(controlPanel);
        label = new Label("Select Level:");
        controlPanel.add(label);
        levelChoice = new Choice();
        for(int i=1; i<=MAX_LEVEL; i++) {
            levelChoice.add(" "+i);
        }
        controlPanel.add(levelChoice);
        levelChoice.select(INITIAL_LEVEL-1);
        levelChoice.addItemListener(this);
        reset = new Button("Reset");
        controlPanel.add(reset);
        reset.addActionListener(this);

        // create the canvas
        canvas = new DukeCanvas();
        add(canvas, BorderLayout.CENTER);
        canvas.setLevel(levelChoice.getSelectedIndex());
	} // end of constructor

    /**
     * Implementing ItemListener
     */
    public void itemStateChanged(ItemEvent event) {
        canvas.setLevel(levelChoice.getSelectedIndex());
    }
    
    public void actionPerformed(ActionEvent event) {
    	Object source = event.getSource();
    	//check if reset is pressed
    	//	if yes, reset game
    	if(source == reset) {
    		resetGame();
    	}
    }

  //Stan's Additions
    private void resetGame() {
        levelChoice.select(INITIAL_LEVEL-1);
        //reset the levelChoice dropdown
  
    	//reset hit and miss displays
    	hitLabel.setText("    0");
    	missLabel.setText("    0");
    	
    	//reset canvas
    	canvas.reset();
    }
    
    /**
     * the DukeCanvas class
     */
    class DukeCanvas extends Canvas implements MouseListener, MouseMotionListener {

        // duke images
        Image duke, dukeWave, dukeMiss;
        // current position of duke
        int dukeX, dukeY;
        // size of duke
        int dukeWidth, dukeHeight;
        // number of hits and misses
        int hits = 0;
        int misses = 0;
        boolean hit = false;
        boolean moving = true; // check if duke needs to move or it's an actual miss
        // level of difficulty
        int level = 0;
        public void setLevel(int lv) {
            level = lv;
        }

        /**
         * Constructor
         */
	    public DukeCanvas() {
	        setBackground(Color.white);
	        // load images
            Toolkit toolkit = Toolkit.getDefaultToolkit();
	        duke = toolkit.getImage("duke.gif");
	        dukeWave = toolkit.getImage("dukeWave.gif");
	        dukeMiss = toolkit.getImage("dukeMiss.gif");

	        addMouseListener(this);
	        addMouseMotionListener(this);
	    } // end of constructor

	    
	    //reset function
	    public void reset() {
	    	hits = misses = level = 0;
	    	//reset the hits, misses, and the level
	    	repaint(); // redraw the screen
	    }
	    
        /**
         * painting the component
         */
        public void paint(Graphics g) {

            // get canvas width and height
            int canvasWidth = getSize().width-2;
            int canvasHeight = getSize().height-2;
            // draw a border
            g.drawRect(0, 0, getSize().width, getSize().height);

            if(hit) {
                // display hit image
                g.drawImage(dukeWave, dukeX, dukeY, this);
            }
            else if (moving) {
            	
                // get image width and height
                dukeWidth = duke.getWidth(this);
                dukeHeight = duke.getHeight(this);
                // calculate the width and height of the display area
                int dWidth = canvasWidth - dukeWidth;
                int dHeight = canvasHeight - dukeHeight;

                // generate a new position for the duke and draw it
                dukeX = (int)(Math.random()*1000) % dWidth;
                dukeY = (int)(Math.random()*1000) % dHeight;
               	g.drawImage(duke, dukeX, dukeY, this);
            } else {
                // get image width and height
                dukeWidth = duke.getWidth(this);
                dukeHeight = duke.getHeight(this);
                // calculate the width and height of the display area
                int dWidth = canvasWidth - dukeWidth;
                int dHeight = canvasHeight - dukeHeight;

                // generate a new position for the duke and draw it
                dukeX = (int)(Math.random()*1000) % dWidth;
                dukeY = (int)(Math.random()*1000) % dHeight;
               	g.drawImage(dukeMiss, dukeX, dukeY, this);
               	// allow the duke to reset to normal moving image
               	moving = true;
            }
        }

        /**
         * Implementing MouseListener
         */
        public void mouseClicked(MouseEvent event) {
            if(hit) {        //  No successive hits allowed
                hit = false;
                repaint();
                return;
            }
            int x = event.getX();
            int y = event.getY();
            if((dukeX < x && x < dukeX+dukeWidth) &&
               (dukeY < y && y < dukeY+dukeHeight)) {
                hitLabel.setText(String.valueOf(++hits));
                hit = true;
            }
            else {
                Toolkit.getDefaultToolkit().beep();
                missLabel.setText(String.valueOf(++misses));
                hit = false;
                moving = false;
            }
            repaint();
        }

        public void mouseReleased(MouseEvent e) {}
        public void mousePressed(MouseEvent e) {}
        public void mouseEntered(MouseEvent e) {}
        public void mouseExited(MouseEvent e) {}

        /**
         * Implementing MouseMotionListener
         */
        public void mouseMoved(MouseEvent event) {
            if(event.getX()%(MAX_LEVEL - level) == 0 &&
               event.getY()%(MAX_LEVEL - level) == 0) {
            	hit = false;
                repaint();
            }
        }

        public void mouseDragged(MouseEvent event) {
        	if(event.getX()%(MAX_LEVEL - level) == 0 &&
                    event.getY()%(MAX_LEVEL - level) == 0) {
                 	hit = false;
                     repaint();
                 }
        }
    }

    /**
     * the main method
     */
    public static void main(String[] argv) {
        // Create a frame
        CatchDuke frame = new CatchDuke();
	    Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(size.width-50, size.height-50);
        frame.setLocation(20, 20);

        // add window closing listener
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                System.exit(0);
            }
        });
        
        // Show the frame
        frame.setVisible(true);
    }
}
