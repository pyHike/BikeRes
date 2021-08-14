// Database Abstract Class

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

abstract class Database {

    final static String DRIVER = "com.mysql.TBD";
    final static String URL = "jdbc:mysql://localhost:TBD";
    final static String DBUSERNAME = "TBD";
    final static String DBPASSWORD = "TBD";
    static Connection con;

    static Connection dbConnect() throws ClassNotFoundException {

        con = null;

        try {
            Class.forName(DRIVER);
            con = DriverManager
                    .getConnection(URL, DBUSERNAME, DBPASSWORD);
            return con;
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return con;
        }
    }

    static boolean dbContains(String table, String column,
            String value) throws ClassNotFoundException, SQLException {

        PreparedStatement ps;
        String query;
        boolean result = true;

        try {
            con = dbConnect();
            if (con == null) {
                dbConFail();
            }
            StringBuilder sb = new StringBuilder();
            query = sb.append("select count(*) from testdb1.").append(table)
                    .append(" where ").append(column).append(" = ?").toString();

            ps = con.prepareStatement(query);
            ps.setString(1, value);
            //System.out.println(ps);

            final ResultSet rs = ps.executeQuery();
            rs.next();
            result = (rs.getInt(1) != 0);

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return result;

    }

    static int dbValueInt(String table, String refColumn, String refValue, String dataColumn)
            throws ClassNotFoundException, SQLException {

        int result = 0;

        PreparedStatement ps;
        String query;

        try {
            con = dbConnect();
            StringBuilder sb = new StringBuilder();
            query = sb.append("select ").append(dataColumn).append(" from testdb1.")
                    .append(table).append(" where ").append(refColumn).append(" = ?").toString();
            // SELECT --- FROM table WHERE refColumn = 'refValue'

            ps = con.prepareStatement(query);
            ps.setString(1, refValue);

            final ResultSet rs = ps.executeQuery();

            rs.next();
            result = rs.getInt(dataColumn);

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return result;
    }

    static double dbValueDouble(String table, String refColumn, String refValue, String dataColumn)
            throws ClassNotFoundException, SQLException {

        double result = 0;

        PreparedStatement ps;
        String query;

        try {
            con = dbConnect();
            StringBuilder sb = new StringBuilder();
            query = sb.append("select ").append(dataColumn).append(" from testdb1.")
                    .append(table).append(" where ").append(refColumn).append(" = ?").toString();
            // SELECT --- FROM table WHERE refColumn = 'refValue'

            ps = con.prepareStatement(query);
            ps.setString(1, refValue);

            final ResultSet rs = ps.executeQuery();

            rs.next();
            result = rs.getInt(dataColumn);

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return result;
    }

    static String dbValueStr(String table, String refColumn, String refValue, String dataColumn)
            throws ClassNotFoundException, SQLException {

        String result = "";
        PreparedStatement ps;
        String query;

        try {
            con = dbConnect();
            StringBuilder sb = new StringBuilder();
            query = sb.append("select ").append(dataColumn).append(" from testdb1.")
                    .append(table).append(" where ").append(refColumn).append(" = ?").toString();
            // SELECT --- FROM table WHERE refColumn = 'refValue'

            ps = con.prepareStatement(query);
            ps.setString(1, refValue);

            final ResultSet rs = ps.executeQuery();

            rs.next();
            result = rs.getString(dataColumn);

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return result;
    }

    static boolean dbValueBool(String table, String refColumn, String refValue, String dataColumn)
            throws ClassNotFoundException, SQLException {

        boolean result = false;
        PreparedStatement ps;
        String query;

        try {
            con = dbConnect();
            StringBuilder sb = new StringBuilder();
            query = sb.append("select ").append(dataColumn).append(" from testdb1.").append(table).append(" where ").append(refColumn).append(" = ?").toString();
            // SELECT --- FROM table WHERE refColumn = 'refValue'

            ps = con.prepareStatement(query);
            ps.setString(1, refValue);

            final ResultSet rs = ps.executeQuery();

            rs.next();
            result = rs.getBoolean(dataColumn);

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return result;
    }

    static String dbRecordToString(String table, String refColumn, String refValue) throws ClassNotFoundException, SQLException {

        PreparedStatement ps;
        String query;
        ResultSet rs;
        String result;
        ArrayList columnNames = new ArrayList();
        ArrayList data = new ArrayList();

        try {
            con = dbConnect();
            StringBuilder sb = new StringBuilder();
            query = sb.append("select * from testdb1.").append(table).append(" where ").append(refColumn).append(" = ?").toString();
            // SELECT --- FROM table WHERE refColumn = 'refValue'

            ps = con.prepareStatement(query);
            ps.setString(1, refValue);

            rs = ps.executeQuery();
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();

            System.out.println(ps);

            System.out.println(rs.getRow());

            for (int i = 1; i <= columns; i++) {
                System.out.print(md.getColumnName(i) + "\t\t");
            }
            System.out.println("");

            ArrayList row = new ArrayList(columns);

            while (rs.next()) {
                for (int i = 1; i <= columns; i++) {
                    if (i > 1) {
                        System.out.print("\t\t");
                    }
                    String columnValue = rs.getString(i);
                    System.out.print(columnValue);
                }
                System.out.println("");
            }

        } catch (SQLException ex) {
            // handle any errors
            result = "";
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return "";
    }

    static void dbUpdateString(String table, String keyColumn, String keyValue, String newValCol, String newValue) throws ClassNotFoundException {

        PreparedStatement ps;

        try {
            con = dbConnect();
            StringBuilder sb = new StringBuilder();
            String query = sb.append("UPDATE testdb1.").append(table)
                    .append(" SET ").append(newValCol).append(" = ? WHERE ")
                    .append(keyColumn).append(" = ?").toString();
            // UPDATE table SET newValCol = 'newValue' WHERE keyColumn = 'keyVal'
            ps = con.prepareStatement(query);
            System.out.println(query);
            ps.setString(1, newValue);
            ps.setString(2, keyValue);
            ps.executeUpdate();
            System.out.println("Record is updated successfully in the database......DATABASECHECK[180]");

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    static void dbUpdateInt(String table, String keyColumn, String keyValue, String newValCol, int newValue) throws ClassNotFoundException {

        PreparedStatement ps;

        try {
            con = dbConnect();
            StringBuilder sb = new StringBuilder();
            String query = sb.append("UPDATE testdb1.").append(table)
                    .append(" SET ").append(newValCol).append(" = ? WHERE ")
                    .append(keyColumn).append(" = ?").toString();
            // UPDATE table SET newValCol = 'newValue' WHERE keyColumn = 'keyVal'
            ps = con.prepareStatement(query);
            ps.setInt(1, newValue);
            ps.setString(2, keyValue);
            System.out.println(ps);
            ps.executeUpdate();
            System.out.println("Record is updated successfully in the database......DATABASECHECK[211]");

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    static void dbUpdateDouble(String table, String keyColumn, String keyValue, String newValCol, double newValue) throws ClassNotFoundException {

        PreparedStatement ps;

        try {
            con = dbConnect();
            StringBuilder sb = new StringBuilder();
            String query = sb.append("UPDATE testdb1.").append(table)
                    .append(" SET ").append(newValCol).append(" = ? WHERE ")
                    .append(keyColumn).append(" = ?").toString();
            // UPDATE table SET newValCol = 'newValue' WHERE keyColumn = 'keyVal'
            ps = con.prepareStatement(query);
            ps.setDouble(1, newValue);
            ps.setString(2, keyValue);
            System.out.println(ps);
            ps.executeUpdate();
            System.out.println("Record is updated successfully in the database......DATABASECHECK[211]");

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    static void dbUpdateBool(String table, String keyColumn, String keyValue, String newValCol, boolean newValue) throws ClassNotFoundException {

        PreparedStatement ps;

        try {
            con = dbConnect();
            StringBuilder sb = new StringBuilder();
            String query = sb.append("UPDATE testdb1.").append(table)
                    .append(" SET ").append(newValCol).append(" = ? WHERE ")
                    .append(keyColumn).append(" = ?").toString();
            // UPDATE table SET newValCol = 'newValue' WHERE keyColumn = 'keyVal'
            ps = con.prepareStatement(query);
            ps.setBoolean(1, newValue);
            ps.setString(2, keyValue);
            System.out.println(ps);
            ps.executeUpdate();
            System.out.println("Record is updated successfully in the database......DATABASECHECK[211]");

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    static void dbUpdateKey(String table, String keyColumn, String newValue, String oldValue) throws ClassNotFoundException {

        PreparedStatement ps;

        try {
            con = dbConnect();
            StringBuilder sb = new StringBuilder();
            String query = sb.append("UPDATE testdb1.").append(table)
                    .append(" SET ").append(keyColumn).append(" = ? WHERE ")
                    .append(keyColumn).append(" = ?").toString();
            // UPDATE table SET newValCol = 'newValue' WHERE keyColumn = 'keyVal'
            ps = con.prepareStatement(query);
            ps.setString(1, newValue);
            ps.setString(2, oldValue);
            ps.executeUpdate();
            System.out.println("Record is updated successfully in the database......DATABASECHECK[241]");

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    static void dbRemove(String table, String keyColumn, String keyValue) throws ClassNotFoundException, SQLException {

        PreparedStatement ps;

        try {
            con = dbConnect();
            StringBuilder sb = new StringBuilder();
            String query = sb.append("DELETE FROM testdb1.").append(table)
                    .append(" WHERE ").append(keyColumn).append(" = ?").toString();
            // DELETE FROM table WHERE keyColumn = 'keyValue'
            ps = con.prepareStatement(query);
            ps.setString(1, keyValue);
            ps.executeUpdate();
            System.out.println("Record has been successfully removed from the database......DATABASECHECK[269]");

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    static String dbConFail() {
        return "There was an issue with the connection";
    }

}
