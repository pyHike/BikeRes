// User Class

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.sql.SQLException;

public abstract class User {

    protected String lastName;
    protected String firstName;
    protected String userName;
    protected String userPwd;
    protected String email;
    protected String phone;

    User() {
    }

    User(String lastName, String firstName, String userName, String userPwd, String email, String phone) {
        this.userName = userName;
        this.lastName = lastName;
        this.firstName = firstName;
        this.userPwd = userPwd;
        this.email = email;
        this.phone = phone;
    }

    protected abstract void addUsertoDB() throws ClassNotFoundException, NoSuchAlgorithmException;

    static boolean isUser(String userName) throws SQLException, ClassNotFoundException {
        return Database.dbContains("users", "userName", userName);
    }

    public String getUserName(String email) throws ClassNotFoundException, SQLException {

        if (User.isUser(email)) {

            try {

                return Database.dbValueStr("users", "email", email, "userName");
            } catch (SQLException ex) {

                ex.printStackTrace();
                return "";
            }
        } else {

            return "Email + " + email + " does not appear to be in the system. Please try again.";
        }
    }

    static void updateFirstName(String userName, String firstName) throws ClassNotFoundException, SQLException {
        if (User.isUser(userName)) {
            Database.dbUpdateString("users", "userName", userName, "firstName", firstName);
        } else {
            System.out.println("User does not appear to be in the system. Please try again.");
        }
    }

    public static String getFirstName(String userName) throws ClassNotFoundException, SQLException {
        if (User.isUser(userName)) {
            try {
                return Database.dbValueStr("users", "userName", userName, "firstName");
            } catch (SQLException ex) {
                ex.printStackTrace();
                return "";
            }
        } else {
            return "User + " + userName + " does not appear to be in the system. Please try again.";
        }
    }

    static void updateLastName(String userName, String lastName) throws ClassNotFoundException, SQLException {
        if (User.isUser(userName)) {
            Database.dbUpdateString("users", "userName", userName, "lastName", lastName);
        } else {
            System.out.println("User does not appear to be in the system. Please try again.");
        }
    }

    public static String getLastName(String userName) throws ClassNotFoundException, SQLException {
        if (User.isUser(userName)) {
            try {
                return Database.dbValueStr("users", "userName", userName, "lastName");
            } catch (SQLException ex) {
                ex.printStackTrace();
                return "";
            }
        } else {
            return "User + " + userName + " does not appear to be in the system. Please try again.";
        }
    }

    static void updateEmail(String userName, String email) throws ClassNotFoundException, SQLException {
        if (User.isUser(userName)) {
            Database.dbUpdateString("users", "userName", userName, "email", email);
        } else {
            System.out.println("User does not appear to be in the system. Please try again.");
        }
    }

    public static String getEmail(String userName) throws ClassNotFoundException, SQLException {
        if (User.isUser(userName)) {
            try {
                return Database.dbValueStr("users", "userName", userName, "email");
            } catch (SQLException ex) {
                ex.printStackTrace();
                return "";
            }
        } else {
            return "User + " + userName + " does not appear to be in the system. Please try again.";
        }
    }

    static void updatePhone(String userName, String phone) throws ClassNotFoundException, SQLException {
        if (User.isUser(userName)) {
            Database.dbUpdateString("users", "userName", userName, "phone", phone);
        } else {
            System.out.println("User does not appear to be in the system. Please try again.");
        }
    }

    public static String getPhone(String userName) throws ClassNotFoundException, SQLException {
        if (User.isUser(userName)) {
            try {
                return Database.dbValueStr("users", "userName", userName, "phone");
            } catch (SQLException ex) {
                ex.printStackTrace();
                return "";
            }
        } else {
            return "User + " + userName + " does not appear to be in the system. Please try again.";
        }
    }

    void generatePwd() throws ClassNotFoundException, NoSuchAlgorithmException {
        System.out.println("Hello " + userName + ", please enter a new password: ");
        try ( Scanner sc = new Scanner(System.in)) {
            this.userPwd = sc.nextLine();
            setUserPwd();
        }
    }

    void setUserPwd() throws ClassNotFoundException, NoSuchAlgorithmException {
        if (checkUserPwd(userPwd) == true) {
            System.out.println("Password is valid");
            String hashedPwd = doHashing(userPwd);
            Database.dbUpdateString("users", "userName", this.userName, "userPwd", hashedPwd);
        } else if (checkUserPwd(userPwd) == false) {
            generatePwd();
        }
    }

    boolean checkUserPwd(String userPwd) throws ClassNotFoundException, NoSuchAlgorithmException {
        boolean rv = false;

        if (PwLength(userPwd) && HasDigits(userPwd) && HasLowerCase(userPwd) && HasUpperCase(userPwd) && !HasSpecChar(userPwd)) {
            rv = true;
        } else if (PwLength(userPwd) == false) {
            System.out.println("A password must have at least 10 characters.");
            generatePwd();
        } else if (HasDigits(userPwd) == false) {
            System.out.println("A password must have digits.");
            generatePwd();
        } else if (HasUpperCase(userPwd) == false) {
            System.out.println("A password must have upper and lower case characters.");
            generatePwd();
        } else if (HasLowerCase(userPwd) == false) {
            System.out.println("A password must have upper and lower case characters.");
            generatePwd();
        } else if (HasSpecChar(userPwd) == true) {
            System.out.println("A password consists of only letters and digits.");
            generatePwd();
        }
        return rv;
    }

    static boolean validatePwdPrompt() throws ClassNotFoundException, NoSuchAlgorithmException, SQLException {
        //boolean result = false;
        Scanner sca = new Scanner(System.in);
        System.out.println("Please enter your username: (all caps)");
        String user = sca.next();
        System.out.println("Please enter your password: (case sensitive)");
        String pwd = sca.next();
        pwd = doHashing(pwd);

        return pwd.equals(Database.dbValueStr("users", "userName", user, "userPwd"));
    }       // returns true if pwd entered (hashed) matches database hashed value

    static boolean validatePwd(String userName) throws ClassNotFoundException, NoSuchAlgorithmException, SQLException {
        Scanner sca = new Scanner(System.in);
        System.out.println("Hello " + userName + ", please enter your password: (case sensitive)");
        String pwd = sca.next();
        pwd = doHashing(pwd);

        return pwd.equals(Database.dbValueStr("users", "userName", userName, "userPwd"));
    }       // returns true if pwd entered (hashed) matches database hashed value

    static boolean validatePwd(String userName, String userPwd) throws ClassNotFoundException, NoSuchAlgorithmException, SQLException {
        userPwd = doHashing(userPwd);

        return userPwd.equals(Database.dbValueStr("users", "userName", userName, "userPwd"));
    }       // returns true if pwd provided (after being hashed) matches database hashed value

    private static String doHashing(String userPwd) throws NoSuchAlgorithmException {
        String algorithm = "MD5";

        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(userPwd.getBytes());

            byte[] resultByteArray = messageDigest.digest();

            StringBuilder sb = new StringBuilder();

            for (byte b : resultByteArray) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    private boolean PwLength(String str) {
        boolean a = false;

        if (str.length() > 9) {
            a = true;
        }

        return a;
    }

    private boolean HasDigits(String str) {
        boolean a = false;

        for (int b = 0; b < str.length(); b++) {
            if ((str.charAt(b) >= '0') && (str.charAt(b) <= '9')) {
                a = true;
            }
        }

        return a;
    }

    private boolean HasSpecChar(String str) {
        boolean z = false;

        for (int a = 0; a < str.length(); a++) {
            if ((str.codePointAt(a) >= 32 && str.codePointAt(a) <= 47)
                    || (str.codePointAt(a) >= 58 && str.codePointAt(a) <= 64)
                    || (str.codePointAt(a) >= 91 && str.codePointAt(a) <= 96)
                    || (str.codePointAt(a) >= 123)) {
                z = true;
            }
        }

        return z;
    }

    private boolean HasUpperCase(String str) {
        boolean z = false;

        for (int a = 0; a < str.length(); a++) {
            if ((str.charAt(a) >= 'A') && (str.charAt(a) <= 'Z')) {
                z = true;
            }
        }

        return (z);
    }

    private boolean HasLowerCase(String str) {
        boolean z = false;

        for (int a = 0; a < str.length(); a++) {
            if ((str.charAt(a) >= 'a') && (str.charAt(a) <= 'z')) {
                z = true;
            }
        }

        return (z);
    }

    public void userSetupComplete() {
        System.out.println("User setup completed.");
    }

}
