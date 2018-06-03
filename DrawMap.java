import java.util.Scanner;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*; 
import javax.swing.ImageIcon;

/**
 * The goal of the DrawMap class is to display each city in the tour onto an equirectangular map
 *
 * @author  Jerry Huang
 * @version 1.0
 * @since   2017-05-15
 *
 **/
 
public class DrawMap
 {
    
	// Import the map and city images
    static ImageIcon map = new ImageIcon("map.png");
    static ImageIcon cityDot = new ImageIcon("City_RedDot.png");
	
	// Put the map onto a label
    static JLabel label = new JLabel(map);
	
	// Put the map into a panel
    static JPanel panel = new JPanel();
	
	// Create a frame
    static JFrame frame = new JFrame("Frame");
	
    public static void main(String[] args) throws IOException {
        // Create and add our cities
        
        String filename="Tour.txt";
        File file = new File(filename);
        
        Scanner cities = new Scanner(file);
        int count = 0;
		
		// Count the number of cities
        while(cities.hasNextLine()){
			//For each line in the file, the counter for the cities is increased by 1
            count++;
            cities.nextLine();
        }
		
		// Create arrays to hold the images for the cities
		ImageIcon[] cityArray = new ImageIcon[count];
		JLabel[] cityLabelArray = new JLabel[count];
		
		// Create an array to hold all the cities
        City [] cityList = new City [count];
        
		// Reimport the file to create and add the cities
        Scanner cities2 = new Scanner(file);

        int i = 0;
        int j = 0;
        double longitude = 0;
        double latitude = 0;
		
		// Create a city at each pair of longitudes and latitudes
        while(cities2.hasNextLine()){
            if(i%2==0){
				
                latitude = Double.parseDouble(cities2.next());
            }
            else{
				
                longitude = Double.parseDouble(cities2.next());
                cities2.nextLine();
				
                cityList[j] = new City(latitude,longitude);
                CityKeep.addToTour(cityList[j]);
                
                j++;
                
            }
            
            i++;
            
        }

        // Initialize a random solution
        Tour currentSolution = new Tour();
        currentSolution.randomize();
        
        // Go through the list of cities
        for(int k = 0;k < count;k++){
			
			// Get the longitude and latitude of each city
            City check = currentSolution.getCity(k);
            double lon = check.getLon();
            double lat = -check.getLat();
            lon = (lon + 180.0)*2000.0/360.0;
            lat = (lat + 90.0)*1000.0/180.0;
            
			// At each city, add a an instance of the city image onto the panel
            cityLabelArray[k] = new JLabel(cityDot);
            cityLabelArray[k].setBounds((int)Math.round(lon)-8,(int)Math.round(lat)-8,16,16);
            panel.add(cityLabelArray[k]);
            
            
        }

        
        label.setBounds(0,0,2000,1000);
        panel.add(label);
        panel.setLayout(null);
		
		// Set the frame and make it visible
        frame.setSize(2000,1000);
        frame.getContentPane().add(panel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    }
}