import java.util.ArrayList;
import java.util.Collections;

public class Tour{

    // Arrangement of cities in a tour
    private ArrayList tour = new ArrayList<City>();
    
    // Distance of a specific tour
    private double distance = 0;
    
    // Create a tour of the right length but with no cities in it.
    public Tour(){
        for (int i = 0; i < CityKeep.tourSize(); i++) {
            
			tour.add(null);
			
        }
    }
	
	public Tour(int size){
        for (int i = 0; i < size; i++) {
            
			tour.add(null);
			
        }
	}
    
    // Copies one tour to another
    public Tour(ArrayList tour){
        this.tour = (ArrayList) tour.clone();
    }
    
    // Get the entire tour
    public ArrayList getTour(){
        return tour;
    }

    // Creates a random tour
    public void randomize() {
        // Add all the cities to the tour
        for (int position = 0; position < CityKeep.tourSize(); position++) {
          setCity(position, CityKeep.getCity(position));
        }
        // Create a random tour from the given list of cities
        Collections.shuffle(tour);
    }

    // Gets a city from the tour
    public City getCity(int position) {
        return (City)tour.get(position);
    }

    // Sets a city in a certain position within a tour
    public void setCity(int tourPosition, City city) {
		
		// Set a city in a specific position
        tour.set(tourPosition, city);
		
        // Reset the length of the tour
        distance = 0;
    }
    
    // Gets the total distance of the tour
    public double getDistance(){
        if (distance == 0) {
            double tourDistance = 0;
            // Loop through our tour's cities
            for (int cityIndex=0; cityIndex < getSize(); cityIndex++) {
				
                // Get city that we are travelling from
                City city1 = getCity(cityIndex);
				
                City city2;
                // Set destinatino city. If at the end of the tour, return to the origin city
                if(cityIndex+1 < getSize()){
                    city2 = getCity(cityIndex+1);
                }
                else{
                    city2 = getCity(0);
                }
                // Get the distance between the two cities
                tourDistance += city1.distanceTo(city2);
            }
            distance = tourDistance;
        }
        return distance;
    }

    // Get number of cities on our tour
    public int getSize() {
        return tour.size();
    }
}