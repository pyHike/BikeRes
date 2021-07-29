import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Scanner;


public final class Customer extends User {   
    /*  Customer account information ***key: userName
    userID / userPwd / need add/remove booking + userName record in bookings database
    can serve as CRM source  */        
    Scanner sc;
    
    public Customer() throws ClassNotFoundException, SQLException, NoSuchAlgorithmException, NoSuchElementException {                
        sc = new Scanner(System.in);        
        System.out.println("Please enter userName: ");                       
        userName = sc.nextLine().toUpperCase();
        
        if (!User.isUser(userName)) {
            System.out.println("Username is available.");
        } else if (User.isUser(userName)) {
            System.out.println("I'm sorry, " + userName + " is already a registered user.");            
        }                       
        
        System.out.println("Please enter a first name: ");    
        
        if (firstName == null) {            
            firstName = sc.nextLine();
            System.out.println(firstName);
        }                                      
        
        System.out.println("Please enter a last name: ");       
        
        if (lastName == null) {
            lastName = sc.nextLine();
            System.out.println(lastName);
        }         
        
        System.out.println("Please enter your email address: ");
        
        if (email == null) {            
            email = sc.nextLine();
            if (Database.dbContains("users", "email", email))  {
                System.out.println("That email already exists in the database. Please provide a different email or sign in under the other account");                
            }
        } 
        
        System.out.println("Please enter a phone number: ");       
        
        if (phone == null) {
            phone = sc.nextLine();
            System.out.println(phone);
        } 
        
        this.addUsertoDB();        
        this.generatePwd();        
        this.userSetupComplete();                
        sc.close(); 
    }    
    
    public Customer(String lastName, String firstName, String userName, String userPwd, String email, String phone) throws SQLException, NoSuchAlgorithmException {
        super(lastName, firstName, userName, userPwd, email, phone);
                
        try {
            if (!Database.dbContains("users", "userName", userName)) {                
                this.addUsertoDB();                
                this.setUserPwd();            // validate & confirm pwd is ok
            } else if (Database.dbContains("users", "userName", userName)) {                
                System.out.println("I'm sorry, user name " + userName + " appears to be in use. User[89]");
            } 
        } catch (ClassNotFoundException ex) {            
            ex.printStackTrace();
        }
        
        this.userSetupComplete();
    }              
    
    @Override
    protected void addUsertoDB() throws ClassNotFoundException, NoSuchAlgorithmException {

        Connection con;
        PreparedStatement ps;

        try {
            
            Class.forName(Database.DRIVER);
            con = DriverManager
                  .getConnection(Database.URL, Database.DBUSERNAME, Database.DBPASSWORD);      
                       
            StringBuilder sb = new StringBuilder();
            
            String query = sb.append("INSERT INTO testdb1.users")
                    .append("(userName, lastName, firstName, email, phone) VALUES( ?, ?, ?, ?, ?")
                    .append(")").toString();
            
            ps = con.prepareStatement(query);
            ps.setString(1, userName);
            ps.setString(2, lastName);
            ps.setString(3, firstName);      
            ps.setString(4, email);                   
            ps.setString(5, phone);
            ps.executeUpdate();      // execute database update
            
            System.out.println("Record is inserted successfully in the database......USER[72]");            

        } catch (SQLException ex) {
            
            System.out.println("user[88] SQLException: " + ex.getMessage());
            System.out.println("user[89] SQLState: " + ex.getSQLState());
            System.out.println("user[90] VendorError: " + ex.getErrorCode());
        }     
    }    
    
}