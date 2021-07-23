import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class Customer extends User {   
    /*  Customer account information ***key: userName
    userID / userPwd / ArrayList of routeCodes... need add/remove booking somehow
    can serve as CRM source  */
    
    static ArrayList<String> custList = new ArrayList<>();   //ArrayList of customers
    static Map<String, ArrayList<String>> userBookings = new HashMap<>();

    
    public Customer(String lastName, String firstName, String userName, String userPsswd) {
        super(lastName, firstName, userName, userPsswd);
        Customer.updateCustomer(userName);
    }    
       
    public ArrayList<String> printUsers() {
        return custList;
    }    
    
    static void updateCustomer(String userName) {
        if ( !custList.contains(userName) ) {
            custList.add(userName); 
            //userBookings.put(userName, );
        }
    }
    
    static boolean isCustomer(String userName) {
        return custList.contains(userName);
    }
    
    static void addBooking(String userName, String bookingID) {        
        if ( userBookings.containsKey(userName) ) {
            ArrayList<String> a = userBookings.get(userName);
            a.add(bookingID);
            userBookings.replace(userName, a);
        }
        else if ( !userBookings.containsKey(userName) ) {
            ArrayList<String> a = new ArrayList<>();
            a.add(bookingID);
            userBookings.put(userName, a);
        }        
    }
    
    static void delBooking(String userName, String bookingID) {
        if ( userBookings.containsValue(bookingID) ) {
            ArrayList<String> a = userBookings.get(userName);
            a.remove(bookingID);
        }
    }
    
}