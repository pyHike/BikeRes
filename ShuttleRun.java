import java.util.HashMap;
import java.util.Map;

public class ShuttleRun extends Shuttle {
    /* Array of booking objects, if < capacity then have availability */

    protected String routeCode;    //***key
    private int year;
    private int month;
    private int day;
    private String route;
    private double price;
    static protected Map<String, Integer> shuttleBookings = new HashMap<>();    // routeCode X inventoryLeft
    
    public ShuttleRun(int day, int month, int year, String route, 
            String shuttleName, int capacity) {
        super(shuttleName, capacity);
        this.day = day;
        this.month = month;
        this.year = year;
        this.route = route;
        this.capacity = capacity;
        this.routeCode = String.format("%S-%02d-%02d-%02d", this.route, this.year, this.month, this.day);
        shuttleBookings.put(this.routeCode, this.capacity);
    }
    
    @Override
    public String toString() {
        return "RouteCode: " + routeCode + "\tShuttleName: " + shuttleName 
                + "\tCapacity: " + capacity + "\tInventoryLeft: " + shuttleBookings.get(routeCode);
    }
    
    public static boolean hasCapacity(String routeCode, int seats) {
        if ( shuttleBookings.get(routeCode) < seats ) {
        System.out.println("There are not enough seats on the requested Shuttle. " + seats 
                + " requested versus " + shuttleBookings.get(routeCode) + " available.");
        }
        return shuttleBookings.get(routeCode) >= seats;
    }
    
    public static void updateInventory(String routeCode, int seats) {       
        if ( !(shuttleBookings.get(routeCode) == null) && (hasCapacity(routeCode, seats)) )  {
            shuttleBookings.replace(routeCode, shuttleBookings.get(routeCode)-seats);                
        }
        else if ( shuttleBookings.get(routeCode) == null ) {       
            System.exit(0);
        }
//        System.out.println(shuttleBookings.get(routeCode));
    }
    
    public int getCapacity() {
        return capacity;
    }
 
    public String getRouteCode() {
        return routeCode;
    }

    public static Map<String, Integer> getShuttleBookings() {
        return shuttleBookings;
    }

    public boolean hasAvailability() {
        return shuttleBookings.get(routeCode) > 0;
    }
    
    public void printHashMap() {
        System.out.println(shuttleBookings);
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }    
}