public class City {
    double lat;
    double lon;
    
    // Create a city at a given latitude or longitude
    public City(double lat, double lon){
        this.lat = lat;
        this.lon = lon;
    }
    
    // Get the latitude
    public double getLat(){
        return this.lat;
    }
    
    // Get the longitude
    public double getLon(){
        return this.lon;
    }
    
    // Find the distance between two cities
    public double distanceTo(City city){
        
        double theta = getLon()-city.getLon();
        double dist = Math.sin(toRad(getLat()))*Math.sin(toRad(city.getLat()))
                    + Math.cos(toRad(getLat()))*Math.cos(toRad(city.getLat()))*Math.cos(toRad(theta));
        dist = Math.acos(dist);
        dist = toDeg(dist);
        dist = dist * 60 * 1.1515 * 1.609344;
        
        // Determines whether the shortest distance crosses the edge of map or not and return the shortest distance if it does
		
        if(getLon()*city.getLon()<0){
            double theta2;
            if(getLon()<0){
                double newLon = 360+getLon();
                theta2 = newLon-city.getLon();
            }
            else{
                double newLon = 360+city.getLon();
                theta2 = getLon()-newLon;
            }
            double dist2 = Math.sin(toRad(getLat()))*Math.sin(toRad(city.getLat()))
                        + Math.cos(toRad(getLat()))*Math.cos(toRad(city.getLat()))*Math.cos(toRad(theta2));
            dist2 = Math.acos(dist2);
            dist2 = toDeg(dist2);
            dist2 = dist2 * 60 * 1.1515 * 1.609344;
            
            return Math.min(dist,dist2);
        }
        else{
        
            return dist;
        
        }
    }
    
    // Convert degrees to radians
    private double toRad(double deg){
        return (deg * Math.PI / 180.0);
    }
    
    // Convert radians to degrees
    private double toDeg(double rad){
        return (rad * 180 / Math.PI);
    }
    
}
