
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Booking {     // getter and setter cleanup
    private String userName;
    private String routeCode;
    private double price;
    public String bookingNum;               // key
    private int seats;
    private boolean paidFor;
       
    public Booking(String userName, String routeCode, 
            double price, int seats, boolean paidFor) throws ClassNotFoundException, SQLException {        
//        ShuttleRun.hasCapacity(routeCode, seats);
        if ( Database.dbContains("users", "userName", userName) ) {
            this.userName = userName;                    
        } else {
            System.out.println("The user " + userName + " does not yet exist. Please create a new user or try again.");
            return;
        }
        if ( ShuttleRun.hasCapacity(routeCode, seats) ) {            
            this.routeCode = routeCode;
            this.price = price;        
            this.seats = seats;
            this.paidFor = paidFor;
            this.bookingNum = String.format("%S-%7S-%02d", userName, routeCode, seats);            
            this.addBookingtoDB(this.bookingNum, this.userName, this.routeCode, this.seats, this.price, this.paidFor);
        }
    }
    
    @Override
    public String toString() {        
        StringBuilder z = new StringBuilder();        
        String output = z.append("BookingNum: ").append(bookingNum)
                .append("\tuserName: ").append(userName).append("\trouteCode: ")
                .append(routeCode).append("\tprice: ").append(price)
                .append("\tseats: ").append(seats).toString();
        
        return output;
    }
              
    private void addBookingtoDB(String bookingNum, String userName, String routeCode, 
            int seats, double price, boolean paidFor) throws ClassNotFoundException {              
        Connection con;
        PreparedStatement ps;
        
        try {
            Class.forName(Database.DRIVER);
            con = DriverManager
                  .getConnection(Database.URL, Database.DBUSERNAME, Database.DBPASSWORD);                      

            StringBuilder sb = new StringBuilder();
            String query = sb.append("insert into testdb1.bookings")
                    .append("(bookingNum, userName, routeCode, seats, price, paidFor) values( ?, ?, ?, ?, ?, ?")
                    .append(")").toString();            
            ps = con.prepareStatement(query);
            ps.setString(1, bookingNum);
            ps.setString(2, userName);
            ps.setString(3, routeCode);
            ps.setInt(4, seats);
            ps.setDouble(5, price);
            ps.setBoolean(6, paidFor);
            ps.executeUpdate();
            ShuttleRun.updateInventory(routeCode, seats);
            System.out.println("Record is inserted successfully in the database......Booking[82]");            
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }     
    }
        
    public static boolean isBooking(String bookingNum) throws SQLException, ClassNotFoundException {       
        return Database.dbContains("bookings", "bookingNum", bookingNum);                                 
    }
        
    protected static void delBooking( String bookingNum ) throws ClassNotFoundException, SQLException {
        ShuttleRun.updateInventory(Database.dbValueStr("bookings", "bookingNum", bookingNum, "routeCode"), -(Database.dbValueInt("bookings", "bookingNum", bookingNum, "seats")));
        Database.dbRemove("bookings", "bookingNum", bookingNum);        
    }       // TESTED to remove booking row in bookings & restore seatsLeft in shuttleruns    

    public static String getPrice(String bookingNum) throws ClassNotFoundException, SQLException {                        
        if ( isBooking(bookingNum) ) {
            return "price = " + Database.dbValueDouble("bookings", "bookingNum", bookingNum, "price");
        } else {
            return "I'm sorry, " + bookingNum + " does not appear in the system. Please try a different booking number.";
        }                 
    }
    
    protected static void setPrice(String bookingNum, double price) throws ClassNotFoundException, SQLException {
        if ( isBooking(bookingNum) ) {
            Database.dbUpdateDouble("bookings", "bookingNum", bookingNum, "price", price);            
        } else {
            System.out.println("I'm sorry, " + bookingNum + " does not appear in the system. Please try a different booking number.");
        }    
    }
    
    public static String getSeats(String bookingNum) throws ClassNotFoundException, SQLException {                        
        if ( isBooking(bookingNum) ) {
            return "seats = " + Database.dbValueInt("bookings", "bookingNum", bookingNum, "seats");
        } else {
            return "I'm sorry, " + bookingNum + " does not appear in the system. Please try a different booking number.";
        }     
    }
    
    protected static void setSeats(String bookingNum, int seats) throws ClassNotFoundException, SQLException {
        if ( isBooking(bookingNum) ) {
            Database.dbUpdateInt("bookings", "bookingNum", bookingNum, "seats", seats);            
        } else {
            System.out.println("I'm sorry, " + bookingNum + " does not appear in the system. Please try a different booking number.");
        }   
    }
    
    public static String getPaidFor(String bookingNum) throws ClassNotFoundException, SQLException {                        
        if ( isBooking(bookingNum) ) {
            return "paidFor = " + Database.dbValueBool("bookings", "bookingNum", bookingNum, "paidFor");
        } else {
            return "I'm sorry, " + bookingNum + " does not appear in the system. Please try a different booking number.";
        }             
    }        
    
    protected static void setPaidFor(String bookingNum, boolean paidFor) throws ClassNotFoundException, SQLException {
        if ( isBooking(bookingNum) ) {
            Database.dbUpdateBool("bookings", "bookingNum", bookingNum, "paidFor", paidFor);
        } else {
            System.out.println("I'm sorry, " + bookingNum + " does not appear in the system. Please try a different booking number.");
        }                
    }        
    
}