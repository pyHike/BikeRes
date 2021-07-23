import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Scanner;

public class Shuttle {

    protected String shuttleName;
    protected int capacity;
    static Set<String> shuttles = new HashSet<>();
    static protected Map<String, Integer> shuttleCapacity = new HashMap<>();
    Scanner sc = new Scanner(System.in);
    
    public Shuttle(String shuttleName, int capacity) {
        this.capacity = capacity;
        if ( !shuttles.contains(shuttleName) ) {
            shuttles.add(shuttleName);        
            shuttleCapacity.putIfAbsent(shuttleName, capacity);
            this.shuttleName = shuttleName;

            System.out.println("The shuttle " + this.shuttleName + " has been added to shuttles, capacity of " + this.capacity + ".");
        }
        else {
            System.out.println("I'm sorry, this shuttle name is already taken. Please try again.");
            this.shuttleName = null;
        }            
    }    
    
    public boolean isShuttle(String shuttle) {
        return shuttles.contains(shuttle);
    }    

    public String getShuttleName() {
        return shuttleName;
    }

    public void setShuttleName(String shuttleName) {
        this.shuttleName = shuttleName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }    
}