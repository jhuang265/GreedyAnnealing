import java.util.Scanner;
import java.io.*;

/**
 * The goal of the SimulatedAnnealing program is to use the simulated annealing heuristic
 * to optimize the tour that goes through a list of given cities.
 *
 * @author  Jerry Huang
 * @version 1.0
 * @since   2017-05-15
 *
 **/

public class GreedyAnnealing{
    public static void main(String[] args) throws IOException{
        
        // Create the output file
        PrintWriter outputFile = null;
        try
        {
            outputFile = new PrintWriter(new FileOutputStream("Tour.txt",false));
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File error.  Program aborted.");
            System.exit(0);
        }
    
        // Import file with city coordiantes
        Scanner cities = new Scanner(new File("Cities.txt"));
        
        // Count the number of cities
        
        int count = 0;
        while(cities.hasNextLine()){
            count++;
            cities.nextLine();
        }
        
        // Create the cities
        City [] cityList = new City [count];
        
        Scanner cities2 = new Scanner(new File("Cities.txt"));
        int i = 0;
        int j = 0;
        double longitude = 0;
        double latitude = 0;
        
        
        while(j < count){
            
            if(i%2==0){
                
                // Store the latitude
                latitude = Double.parseDouble(cities2.next());
                
            }
            else{
                
                // Store the longitude
                longitude = Double.parseDouble(cities2.next());
                
                // Create a city at the given longitude and latitude and add it to the tour
                cityList[j] = new City(latitude,longitude);
                
                CityKeep.addToTour(cityList[j]);
                
                // Go to the next line (next city)
                j++;
                
            }
            
            // Go to the next element to read
            i++;
            
        }
        
        // Initialize intial solution
        Tour solution = new Tour();
        solution.randomize();
                
        // Print out the tour length
        System.out.println("Starting Length of Tour: " + String.format("%.2f",solution.getDistance()) +" km");
        
        // Set as current best
        Tour bestTour = new Tour(solution.getTour());
        
        // Set the initial temperature
        double temperature = 1000000;

        // Cooling rate
        double annealingRate = 0.0001;
        
        // Loop until system has cooled
        int iterations = 0;
        while (temperature > 1) {
            // Cool the system more rapidly until it reaches 2/3 of its original temperature
            annealingRate = (temperature> 66667) ? 0.001 : 0.0001;
            
            // Slow down the cooling rate once the system reaches a third of its original temperature
            annealingRate = (temperature> 33333) ? 0.0001 : 0.000005;
            
            // Create new neighbour tour
            
            Tour neighbor = new Tour(solution.getTour());

            // Get two random positions in the tour
            int tourPos1 = (int) (neighbor.getSize() * Math.random());
            int tourPos2 = (int) (neighbor.getSize() * Math.random());
            
			
			// Set path to be implement greedy algorithm
			int startPos = (tourPos1 < tourPos2) ? tourPos1 : tourPos2;
			int endPos = (tourPos1 < tourPos2) ? tourPos2 : tourPos1;
			
			Tour temp = new Tour(endPos-startPos+1);
			
			for(int i = 0; i<temp.getSize(); i++){
				temp.setCity(i, neighbor.getCity(startPos+i));
			}
			
			// First and last city stay the same
			temp.setCity(0,neighbor.getCity(startPos))
			temp.setCity(temp.getSize()-1,neighbor.getCity(endPos))
			
			// Find closest neighbor and set as next city
			
			for(int i = 0; i<temp.getSize()-1; i++){
				
				// Find distances to each remaining city
				double distances[] = new double[temp.getSize-i-2];
				for (int j = 0; j<distances.length()-1; j++){
					distances[j] = (temp.getCity(i)).distanceTo(temp.getCity(i+j+1))
				}
				
				// Find closest city
				int maxIndex = 0;
				for(k = 0; k<distances.length()-1; k++){
					if (distances(k) > distances(k+1)){
						maxIndex = k+1;
					}
				}
				
				// Switch positions
				City keep = temp.getCity(i+1)
				temp.setCity(i+1, temp.getCity(maxIndex));
				temp.setCity(maxIndex, temp.getCity(i+1));
				
			}
			
			// Set greedy path into new solution
			
			for(int i = 0; i < temp.getSize()-1; i++){
				neighbor.setCity(startPos+i, temp(i))
			}
           
            // Find the lengths of both solutions
            double dist = solution.getDistance();
            double newDist = neighbor.getDistance();

            // Determine if the neighboring solution will replace the current solution
            if (prob(dist, newDist, temperature) > Math.random()) {
                solution = new Tour(neighbor.getTour());
            }

            // Keep track of the best solution found
            if (dist < bestTour.getDistance()) {
                bestTour = new Tour(solution.getTour());
            }
            
            // Cool system
            temperature *= 1-annealingRate;
            
            iterations++;
        }
        
        //Display the final distance
        
        System.out.println("Number of times the system was annealed: "+iterations);
        System.out.println("Final Tour Length: " + String.format("%.2f",bestTour.getDistance()) +" km");
        System.out.println("Path of the tour:");
        for(int position = 0; position<bestTour.getSize(); position++){
            System.out.print("City "+(position+1));
            System.out.println("["+bestTour.getCity(position).getLat()+","+bestTour.getCity(position).getLon()+"]");
        }
        
        // Output the order of cities into the file
        for(int k = 0; k<bestTour.getSize(); k++){
            City spot = bestTour.getCity(k);
            double cityLat = spot.getLat();
            double cityLon = spot.getLon();
                    
            outputFile.println(cityLat + "  " + cityLon);
        }
        outputFile.close();
        
        //Draw the map of cities
        DrawMap.main(new String[0]);
                
        // Plotting
        PlotTrajectories.main(new String [0],count);
    }
    
    /**
    * The goal of the prob method is to find the probability of accepting a neighboring solution.
    *
    * @param    dist            The length of the current solution
    *           newDist         The length of the neighboring solution
    *           temperature     The current temperature of the system
    *
    * @return   a double between 0 and 1 inclusive that indicates a value to be compared with a random
    *           double between 0 and 1
    */
    
    public static double prob(double dist, double newDist, double temperature) {
        // Accept the tour if it is better than the original
        if (newDist < dist) {
            return 1.0;
        }
        // If the new solution is worse, calculate an acceptance probability
        return Math.exp((dist - newDist) / temperature);
    }
    
}