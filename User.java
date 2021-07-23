/* Lightweight reservation model for bike shuttle business */

/** @author mb 07_18_2021 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public abstract class User {    
    protected String lastName;
    protected String firstName;
    protected String userName;
    protected String userPwd;
    public Scanner sc = new Scanner(System.in);
    static protected Map<String, String> userDirectory = new HashMap<>();
    
    public User() {
        this.setFirstName();
        this.setLastName();
        this.setUserName();
        this.generatePwd();
        Customer.custList.add(this.userName);
        this.userSetupComplete();        
    }    

    // Constructor overload to bypass prompts for testing purposes
    public User(String lastName, String firstName, String userName, String userPwd){
        
        if ( userDirectory.containsKey(userName) ) {
            System.out.println("I'm sorry, user name " + userName + " appears to be in use. User[28]");
            System.exit(0);
        }

        if ( !userDirectory.containsKey(userName) ) { 
            this.userName = userName;
            if ( userPwd != null ) {
                setUserPwd(userPwd);
            }                        
            else if ( userPwd == null ) {
                generatePwd();
            }  
            this.lastName = lastName;
            this.firstName = firstName;
        }
               
        userDirectory.put(userName, userPwd);
        Customer.custList.add(this.userName);
        this.userSetupComplete();
    }       
    
    public String toString() {
        return this.userName + " " + this.lastName + " " + this.firstName + " " + this.isUser();
    }
    
    protected boolean isUser() {
        return userDirectory.containsKey(this.userName);
    }
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setFirstName() {
        System.out.println("User[65] Please enter user's first name: ");
        firstName = sc.next();
    }    
    
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public void setLastName() {
        System.out.println("User[78] Please enter user's last name: ");
        lastName = sc.next();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserName() {
        System.out.println("User[91] Please enter a username: ");
        String newString = sc.next();
        if ( !User.userDirectory.containsKey(newString) ) {
            userName = newString;        
        }
        else if ( User.userDirectory.containsKey(newString) ) {
            System.out.println("User[97] I'm sorry, that username already exists.");
            System.exit(0);
        }
    }    
    
    public String getUserPwd() {
        return userPwd;
    }

    private void generatePwd() {
        System.out.println("Hello " + userName + ", please enter a new password: ");
        String pwd = sc.next();
        setUserPwd(pwd);
    }
    
    private void setUserPwd(String userPsswd) {
        if ( PwLength(userPsswd) && HasDigits(userPsswd) && HasLowerCase(userPsswd) && HasUpperCase(userPsswd) && !HasSpecChar(userPsswd) ) {
            System.out.println("User[108] Password is valid");                        
            this.userPwd = userPsswd;
        }        
        else if ( PwLength(userPsswd) == false ) {
            System.out.println("User[112] A password must have at least 10 characters.");
            generatePwd();
        }  
        else if ( HasDigits(userPsswd) == false ) {
            System.out.println("User[116] A password must have digits.");
            generatePwd();
        }      
        else if ( HasUpperCase(userPsswd) == false ) {
            System.out.println("User[120] A password must have upper and lower case characters.");
            generatePwd();
        }
        else if ( HasLowerCase(userPsswd) == false ) {
            System.out.println("User[125] A password must have upper and lower case characters.");
            generatePwd();
        }        
        else if ( HasSpecChar(userPsswd) == true ) {
            System.out.println("User[130] A password consists of only letters and digits.");
            generatePwd();
        }
    }
//    
//    private void setUserPwd2(String userPsswd) {
//        this.userPwd = userPsswd;
//        System.out.println("User password now set. Thank you!");
//    }
    
    public boolean PwLength(String str) {
        boolean a = false;
        if ( str.length() > 9 )
            a = true;
        return a;
    }
    
    public boolean HasDigits(String str) {
        boolean a = false;
        for ( int b = 0 ; b < str.length() ; b++ ) {
            if ( (str.charAt(b) >= '0') && (str.charAt(b) <= '9') )
                a = true;
        }
        return a;
    }

    public boolean HasSpecChar(String str) {
        boolean z = false;
        for ( int a = 0 ; a < str.length() ; a++ ) {
            if ( 
                ( (str.charAt(a) < '0') || ((str.charAt(a) > '9') && (str.charAt(a) < 'A')) ) || 
                ( (str.charAt(a) > 'Z') && (str.charAt(a) < 'a') ) || 
                ( (str.charAt(a) > '9') && (str.charAt(a) > 'z') ) 
            )
                z = true;
        }
        return z;
    }
    
    public boolean HasUpperCase(String str) {
        int z = 0;
        for ( int a = 0; a < str.length() ; a++ ) {
            
            if ( (str.charAt(a) >= 'A') && (str.charAt(a) <= 'Z') )
            {
                    z++;
            }
        }
        return (z != 0);        
    }    
    
    public boolean HasLowerCase(String str) {
        int z = 0;
        for ( int a = 0; a < str.length() ; a++ ) {
            
            if ( (str.charAt(a) >= 'a') && (str.charAt(a) <= 'z') )
            {
                    z++;
            }
        }
        return (z != 0);        
    }    
        
    public boolean HasSpecCharOld(String str) {
        boolean z = false;
        String str2 = str.toLowerCase();
        for ( int a = 0 ; a < str2.length() ; a++ ) {                        
            if ( 
                    (str2.charAt(a) == '.') || 
                    (str2.charAt(a) == '-') || 
                    (str2.charAt(a) == '=') || 
                    (str2.charAt(a) == '+') || 
                    (str2.charAt(a) == ')') || 
                    (str2.charAt(a) == '(') || 
                    (str2.charAt(a) == '*') || 
                    (str2.charAt(a) == '&') || 
                    (str2.charAt(a) == '^') || 
                    (str2.charAt(a) == '%') || 
                    (str2.charAt(a) == '$') || 
                    (str2.charAt(a) == '#') ||                     
                    (str2.charAt(a) == '@') || 
                    (str2.charAt(a) == '!') || 
                    (str2.charAt(a) == '~') || 
                    (str2.charAt(a) == '"') || 
                    (str2.charAt(a) == '>') || 
                    (str2.charAt(a) == '<') || 
                    (str2.charAt(a) == ',') || 
                    (str2.charAt(a) == '/') ||                     
                    (str2.charAt(a) == '{') || 
                    (str2.charAt(a) == '}') || 
                    (str2.charAt(a) == '[') || 
                    (str2.charAt(a) == ']') || 
                    (str2.charAt(a) == '|') || 
                    (str2.charAt(a) == '_') || 
                    (str2.charAt(a) == '`') || 
                    (str2.charAt(a) == '?')  
                ) {
                    z = true;
            }
        }
        return z;                
    } 
    
    public void userSetupComplete() {
        System.out.println("User setup completed.");
    }
}