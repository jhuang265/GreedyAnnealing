import java.util.ArrayList;
public class CityKeep {

    // Create an array list to hold all the cities
    private static ArrayList tour = new ArrayList<City>();

    // Add a new destination city to the tour
    public static void addToTour(City newCity) {
        tour.add(newCity);
    }
    
    // Find the the city at a specific position at the tour
    public static City getCity(int place){
        return (City)tour.get(place);
    }
    
    // Find how many cities are in the tour
    public static int tourSize(){
        return tour.size();
    }
    
}