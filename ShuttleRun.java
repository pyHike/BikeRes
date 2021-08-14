// ShuttleRun Class

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ShuttleRun extends Shuttle {

    protected String routeCode; //***key
    private int year;
    private int month;
    private int day;
    private String route;
    private double price;

    public ShuttleRun(int month, int day, int year, String route,
            String shuttleName, int capacity) throws SQLException, ClassNotFoundException {
        super(shuttleName, capacity);
        this.day = day;
        this.month = month;
        this.year = year;
        this.route = route.toUpperCase();
        this.capacity = capacity;
        this.routeCode = String.format("%S-%02d-%02d-%02d", this.route, this.month, this.day, this.year);

        if (!isShuttleRun(routeCode)) {
            addShuttleRuntoDB();
        } else if (isShuttleRun(routeCode)) {
            System.out.println("The routeCode " + routeCode + " already exists in the system. Please try another routeCode.");
        }
    }

    private void addShuttleRuntoDB() throws ClassNotFoundException {
        Connection con;
        PreparedStatement ps;

        try {
            con = Database.dbConnect();
            if (con == null) {
                Database.dbConFail();
            }
            StringBuilder sb = new StringBuilder();
            String query = sb.append("INSERT INTO testdb1.shuttleruns")
                    .append("(shuttleName, routeCode, year, month, day, route, price, seatsLeft) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)").toString();
            // INSERT INTO table (col1, col2, ...) VALUES(1, 2, ... )
            ps = con.prepareStatement(query);
            ps.setString(1, shuttleName);
            ps.setString(2, routeCode);
            ps.setInt(3, year);
            ps.setInt(4, month);
            ps.setInt(5, day);
            ps.setString(6, route);
            ps.setDouble(7, price);
            ps.setInt(8, capacity);
            ps.executeUpdate();
            System.out.println("ShuttleRun " + shuttleName + " was inserted successfully in the database......");
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    public static boolean isShuttleRun(String routeCode) throws ClassNotFoundException, SQLException {
        return Database.dbContains("shuttleruns", "routeCode", routeCode);
    }

    private static String noShuttleRun(String shuttleRun) {
        return "I'm sorry, " + shuttleRun + " does not exist in the system.";
    }

    public static void setShuttleName(String routeCode, String shuttleName, String newName) throws ClassNotFoundException, SQLException {
        Shuttle.setShuttleName(shuttleName, newName);
        if (Shuttle.isShuttle(newName) && isShuttleRun(routeCode)) {
            Database.dbUpdateString("shuttleruns", "routeCode", routeCode, "shuttleName", newName);
        } else {
            System.out.println(noShuttleRun(routeCode));
        }
    }

    public static boolean hasCapacity(String routeCode, int seats) throws ClassNotFoundException, SQLException {
        if (isShuttleRun(routeCode)) {
            return Database.dbValueInt("shuttleruns", "routeCode", routeCode, "seatsLeft") >= seats;
        } else {
            System.out.println(noShuttleRun(routeCode));;
            return false;
        }
    }

    protected static void updateInventory(String routeCode, int seats) throws ClassNotFoundException, SQLException {
        if (isShuttleRun(routeCode)) {
            System.out.println("Inventory pre-update: " + Database.dbValueInt("shuttleruns", "routeCode", routeCode, "seatsLeft") + " will remove " + seats);
            Database.dbUpdateInt("shuttleruns", "routeCode", routeCode, "seatsLeft", Database.dbValueInt("shuttleruns", "routeCode", routeCode, "seatsLeft") - seats);
            System.out.println("Inventory updated. Now: " + Database.dbValueInt("shuttleruns", "routeCode", routeCode, "seatsLeft") + " after removing " + seats + " seats.");
        } else {
            System.out.println(noShuttleRun(routeCode));
        }
    }

    public static String getRoute(String routeCode) throws ClassNotFoundException, SQLException {
        if (isShuttleRun(routeCode)) {
            return Database.dbValueStr("shuttleruns", "routeCode", routeCode, "route");
        } else {
            return noShuttleRun(routeCode);
        }
    }

    public String getPrice(String routeCode) throws ClassNotFoundException, SQLException {
        if (isShuttleRun(routeCode)) {
            return Database.dbValueStr("shuttleruns", "routeCode", routeCode, "price");
        } else {
            return noShuttleRun(routeCode);
        }
    }

    protected static void setPrice(String routeCode, double price) throws ClassNotFoundException, SQLException {
        if (isShuttleRun(routeCode)) {
            Database.dbUpdateDouble("shuttleruns", "routeCode", routeCode, "price", price);
        } else {
            System.out.println(noShuttleRun(routeCode));
        }
    }

    public static String getYear(String routeCode) throws ClassNotFoundException, SQLException {
        if (isShuttleRun(routeCode)) {
            return Database.dbValueStr("shuttleruns", "routeCode", routeCode, "year");
        } else {
            return noShuttleRun(routeCode);
        }
    }

    public static String getMonth(String routeCode) throws ClassNotFoundException, SQLException {
        if (isShuttleRun(routeCode)) {
            return Database.dbValueStr("shuttleruns", "routeCode", routeCode, "month");
        } else {
            return noShuttleRun(routeCode);
        }
    }

    public static String getDay(String routeCode) throws ClassNotFoundException, SQLException {
        if (isShuttleRun(routeCode)) {
            return Database.dbValueStr("shuttleruns", "routeCode", routeCode, "day");
        } else {
            return noShuttleRun(routeCode);
        }
    }

}
