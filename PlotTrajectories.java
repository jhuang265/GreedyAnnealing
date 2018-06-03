import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.swing.*;

import java.util.Scanner;        // For the Scanner class
import java.io.*;                // For file I/O classes
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;  // For the JOptionPane class

/**
 * The goal of the PlotTrajectories class is to draw the annealed tour
 *
 * @author  Jerry Huang
 * @version 1.0
 * @since   2017-05-15
 *
 **/

public class PlotTrajectories extends JApplet {

    // Import the colors
    final static Color bg = Color.black;
    final static Color fg = Color.white;
    final static Color green = Color.green;
    
    // Create variables for storing the cities
    static int dataNum =0;
    static double[] lat, lon;

    public static void main(String s[], int n)
    {
        
        dataNum = n;
        
        //Set the file to read data from
        
        String filename="Tour.txt";

        int i =0;

        // Open the file.
        File file = new File(filename);

        try
        {
            Scanner inputFile = new Scanner(file);

            //create the size of the arrays in accordance with the number of cities
            lat = new double[dataNum];
            lon = new double[dataNum];


            while ( inputFile.hasNext() && i < dataNum)
            {
                try
                {
                    //Read the longitudes and latitudes of each city and store them into the respective arrays
                    String str = inputFile.next();
                    lat[i] = Double.parseDouble(str);

                    str = inputFile.next();
                    lon[i] = Double.parseDouble(str);
                }

                catch (java.util.InputMismatchException ex)
                {
                        System.out.println("value " + lat);
                }

                i++;
            }

                if ( i < dataNum ){
                    //make sure we stop at the max number of data in the file
                    dataNum = i;
            }
            
            // Create the frame on which the path will be displayed
            JFrame f = new JFrame("ShapesDemo2D");
            
            f.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {System.exit(0);}
            });
            JApplet applet = new PlotTrajectories();
            f.getContentPane().add("Center", applet);
            applet.init();
            f.pack();
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setSize(new Dimension(2000,1000)); //window size
            f.setVisible(true);
    
        } catch (FileNotFoundException ex) {
                Logger.getLogger(PlotTrajectories.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void init() {
        // Initialize the color of the background
        setBackground(bg);
    }

    public void paint(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
       
        g2.setPaint(fg);
        
        // Draw rectanges at each city
        for ( int i = 0; i < dataNum; i++)
        {
            double lat1 = -lat[i];
            double lon1 = lon[i];

            lon1 = (lon1 + 180.0)*2000.0/360.0;
            lat1 = (lat1 + 90.0)*1000.0/180.0;
            
            g2.draw(new Rectangle2D.Double(lon1, lat1,2,2));
            
            
        }
        
        
        // Draw Lines connecting cities
        for ( int i = 0; i <= dataNum-1 ; i++)
        {   
            // if the city is the last city, the system will draw a line connecting it to the origin city
            if(i==(dataNum-1)){
                
                // Set the latitudes and longitudes of the two cities
                double lat1 = -lat[i];
                double lon1 = lon[i];
                double lat2 = -lat[0];
                double lon2 = lon[0];
                lon1 = (lon1 + 180.0)*2000.0/360.0;
                lat1 = (lat1 + 90.0)*1000.0/180.0;
                lon2 = (lon2 + 180.0)*2000.0/360.0;
                lat2 = (lat2 + 90.0)*1000.0/180.0;
                
                // Determine moving in which direction will produce the shortest path connecting the cities
                if(Math.abs(lon2-lon1)>1000){
                    // If the shorter path requires crossing the edge of the map, the line is split into two
                    // portions and both are drawn onto the screen.
                    
                    // The system also determines which city is positioned on which end of the screen in order to
                    // determine where to display the lines.
                    if(lon1>lon2){
                        double k = (lat2-lat1)/((lon2+2000)-lon1);
                        
                        double rightEdge = lat1+k*(2000-lon1);
                        double leftEdge = lat2+(-lon2)*k;
                        
                        g2.setPaint(green);
                        g2.draw(new Line2D.Double(lon1,lat1,2000,rightEdge));
                        g2.setPaint(green);
                        g2.draw(new Line2D.Double(0,leftEdge,lon2,lat2));
                    }
                    else{
                        double k = (lat1-lat2)/((lon1+2000)-lon1);
                        
                        double rightEdge = lat2+k*(2000-lon2);
                        double leftEdge = lat1+(-lon1)*k;
                        
                        g2.setPaint(green);
                        g2.draw(new Line2D.Double(lon1,lat1,0,leftEdge));
                        g2.setPaint(green);
                        g2.draw(new Line2D.Double(2000,rightEdge,lon2,lat2));
                    }
                }
                else{

                    g2.setPaint(green);
                    g2.draw(new Line2D.Double(lon1,lat1,lon2,lat2));

                }

            }
            else{
                
                // The same process is done with all the cities before the last city
                
                double lat1 = -lat[i];
                double lon1 = lon[i];
                double lat2 = -lat[i+1];
                double lon2 = lon[i+1];

                lon1 = (lon1 + 180.0)*2000.0/360.0;
                lat1 = (lat1 + 90.0)*1000.0/180.0;
                lon2 = (lon2 + 180.0)*2000.0/360.0;
                lat2 = (lat2 + 90.0)*1000.0/180.0;

                

                if(Math.abs(lon2-lon1)>1000){
                    if(lon1>lon2){
                        double k = (lat2-lat1)/((lon2+2000)-lon1);
                        
                        double rightEdge = lat1+k*(2000-lon1);
                        double leftEdge = lat2+(-lon2)*k;
                        
                        g2.setPaint(green);
                        g2.draw(new Line2D.Double(lon1,lat1,2000,rightEdge));
                        g2.setPaint(green);
                        g2.draw(new Line2D.Double(0,leftEdge,lon2,lat2));
                    }
                    else{
                        double k = (lat1-lat2)/((lon1+2000)-lon2);
                        
                        double rightEdge = lat2+k*(2000-lon2);
                        double leftEdge = lat1+(-lon1)*k;
                        
                        g2.setPaint(green);
                        g2.draw(new Line2D.Double(lon1,lat1,0,leftEdge));
                        g2.setPaint(green);
                        g2.draw(new Line2D.Double(2000,rightEdge,lon2,lat2));
                    }
                }
                else{

                    g2.setPaint(green);
                    g2.draw(new Line2D.Double(lon1,lat1,lon2,lat2));

                }

            }

            try {
                Thread.sleep(50);                 //1000 milliseconds is one second.
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        // Indicate key values: MAX VALUE1 

    }

}
