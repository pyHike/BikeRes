// Shuttle Class

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Shuttle {

    protected String shuttleName;
    protected int capacity;

    public Shuttle(String shuttleName, int capacity) throws SQLException, ClassNotFoundException {
        this.shuttleName = shuttleName;
        this.capacity = capacity;

        if (!isShuttle(shuttleName)) {
            addShuttletoDB();
            if (isShuttle(shuttleName)) {
                System.out.println("The shuttle " + this.shuttleName + " has been added to shuttles, capacity of " + this.capacity + ".");
            } else if (!isShuttle(shuttleName)) {
                System.out.println("I'm sorry, there was a problem adding " + shuttleName + " to the system. Please try again.");
            }
        } else {
            System.out.println("The shuttle referenced is in the shuttle database.");
        }
    }

    private void addShuttletoDB() throws SQLException, ClassNotFoundException {

        Connection con;
        PreparedStatement ps;

        try {
            con = Database.dbConnect();
            if (con == null) {
                Database.dbConFail();
            }
            StringBuilder sb = new StringBuilder();
            String query = sb.append("INSERT INTO testdb1.shuttles")
                    .append("(shuttleName, capacity) VALUES ( ?, ?)").toString();
            // INSERT INTO table VALUES(1, 2, ... )
            ps = con.prepareStatement(query);
            ps.setString(1, shuttleName);
            ps.setInt(2, capacity);
            ps.executeUpdate();
            System.out.println("Record is inserted successfully in the database......");
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    public static boolean isShuttle(String shuttleName) throws SQLException, ClassNotFoundException {
        return Database.dbContains("shuttles", "shuttleName", shuttleName);
    }

    private static String noShuttle(String shuttleName) {
        return "I'm sorry, " + shuttleName + " does not exist in the system.";
    }

    public String getShuttleName() throws ClassNotFoundException, SQLException {
        if (isShuttle(shuttleName)) {
            return Database.dbValueStr("shuttles", "shuttleName", shuttleName, "shuttleName");
        } else {
            return noShuttle(shuttleName);
        }
    }

    static void setShuttleName(String shuttleName, String newName) throws ClassNotFoundException, SQLException {
        if (isShuttle(shuttleName)) {
            Database.dbUpdateString("shuttles", "shuttleName", shuttleName, "shuttleName", newName);
        } else {
            System.out.println(noShuttle(shuttleName));
        }
    }

    public static String getCapacity(String shuttleName) throws SQLException, ClassNotFoundException {
        if (isShuttle(shuttleName)) {
            return Database.dbValueStr("shuttles", "shuttleName", shuttleName, "capacity");
        } else {
            return noShuttle(shuttleName);
        }
    }

    public static void setCapacity(String shuttleName, int capacity) throws SQLException, ClassNotFoundException {
        if (isShuttle(shuttleName)) {
            Database.dbUpdateInt("shuttles", "shuttleName", shuttleName, "capacity", capacity);
        } else {
            System.out.println(noShuttle(shuttleName));
        }
    }

}
