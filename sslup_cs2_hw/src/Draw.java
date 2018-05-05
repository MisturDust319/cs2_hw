/******************************************************************
 *  COURSE:             CSC231 Computer Science and Programming II
 *	Lab:			    Number 4
 *	FILE:				Draw.java
 *	TARGET:				Java 6.0 and 7.0
 *	AUTHOR:				Stan Slupecki
 *****************************************************************/

// Import Core Java packages
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.*;

public class Draw extends JFrame implements ActionListener, ItemListener, ListSelectionListener {

	// Initial Frame size
	static final int WIDTH = 400;                // frame width
	static final int HEIGHT = 300;               // frame height

    // Color choices
    static final String COLOR_NAMES[] = {"None", "Red", "Blue", "Green"};
    static final Color COLORS[] = {null, Color.red, Color.blue, Color.green};

    // Button control
    JButton circle;
    JButton roundRec;
    JButton threeDRec;

    // Color choice box
    JComboBox colorChoice;

    // the list of drawing objects
    JList<String> drawingList;
    // the vector that holds the display data for drawing objs
    DefaultListModel<String> drawingData;
    
    // the canvas
    DrawCanvas canvas;

    /**
     * Constructor
     */
	public Draw() {
	    super("Java Draw");
        setLayout(new BorderLayout());

        // create panel for controls
        Panel topPanel = new Panel(new GridLayout(2, 1));
        add(topPanel, BorderLayout.NORTH);

        // create button control
        Panel buttonPanel = new Panel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(buttonPanel);

        circle = new JButton("Circle");
        buttonPanel.add(circle);
        roundRec = new JButton("Rounded Rectangle");
        buttonPanel.add(roundRec);
        threeDRec = new JButton("3D Rectangle");
        buttonPanel.add(threeDRec);

        // add button listener
        circle.addActionListener(this);
        roundRec.addActionListener(this);
        threeDRec.addActionListener(this);

        // create panel for color choices
        Panel colorPanel = new Panel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(colorPanel);
        JLabel label = new JLabel("Filled Color:");
        colorPanel.add(label);
        colorChoice = new JComboBox(COLOR_NAMES);
        
        colorPanel.add(colorChoice);
        colorChoice.addItemListener(this);
        
        // init drawing data
        drawingData = new DefaultListModel<String>();
        
        // create drawing list
        drawingList = new JList<String>(drawingData);
        drawingList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        drawingList.addListSelectionListener(this);
        JScrollPane listScroller = new JScrollPane(drawingList);
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.add(listScroller, BorderLayout.CENTER);
        add(listPanel, BorderLayout.WEST);
        
        // create the canvas
        canvas = new DrawCanvas(drawingData);
        add(canvas, BorderLayout.CENTER);
	} // end of constructor


    /**
     *  Implementing ActionListener
     */
    public void actionPerformed(ActionEvent event) {
        if(event.getSource() == circle) {  // circle button
            canvas.setShape(DrawCanvas.CIRCLE);
        }
        else if(event.getSource() == roundRec) {  // rounded rectangle button
            canvas.setShape(DrawCanvas.ROUNDED_RECTANGLE);
        }
        else if(event.getSource() == threeDRec) { // 3D rectangle button
            canvas.setShape(DrawCanvas.RECTANGLE_3D);
        }
    }

    /**
     * Implementing ItemListener
     */
    public void itemStateChanged(ItemEvent event) {
        Color color = COLORS[colorChoice.getSelectedIndex()];
        canvas.setFilledColor(color);
    }
    
    /**
     * implementing valueChanged
     */
    public void valueChanged(ListSelectionEvent event) {
    }

    /**
     * the main method
     */
    public static void main(String[] argv) {
        // Create a frame
        Draw frame = new Draw();
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocation(150, 100);

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
