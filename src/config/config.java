package config;

import java.sql.*;

public class config {

    // ✅ Connect to SQLite and initialize tables
    public static Connection connectDB() {
        Connection con = null;
        try {
            Class.forName("org.sqlite.JDBC"); // Load SQLite JDBC driver
            con = DriverManager.getConnection("jdbc:sqlite:shirt.db"); // Connect to DB
            System.out.println("✅ Connection Successful");

            try (Statement stmt = con.createStatement()) {

                // --- CUSTOMER TABLE ---
                String sqlCustomer = "CREATE TABLE IF NOT EXISTS customer ("
                        + "customer_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "first_name TEXT NOT NULL,"
                        + "last_name TEXT NOT NULL,"
                        + "email TEXT,"
                        + "contact TEXT,"
                        + "address TEXT,"
                        + "status TEXT DEFAULT 'Active'"
                        + ");";
                stmt.execute(sqlCustomer);

                // --- TSHIRT TABLE ---
                String sqlTShirt = "CREATE TABLE IF NOT EXISTS TShirt ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "brand TEXT NOT NULL,"
                        + "size TEXT NOT NULL,"
                        + "color_design TEXT NOT NULL"
                        + ");";
                stmt.execute(sqlTShirt);

                // --- ORDERS TABLE ---
                String sqlOrders = "CREATE TABLE IF NOT EXISTS orders ("
                        + "order_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "customer_id INTEGER NOT NULL,"
                        + "tshirt_id INTEGER NOT NULL,"
                        + "quantity INTEGER NOT NULL,"
                        + "total_price NUMERIC(10,2) NOT NULL,"
                        + "order_date DATETIME DEFAULT CURRENT_TIMESTAMP,"
                        + "status TEXT DEFAULT 'Pending',"
                        + "FOREIGN KEY (customer_id) REFERENCES customer(customer_id),"
                        + "FOREIGN KEY (tshirt_id) REFERENCES TShirt(id)"
                        + ");";
                stmt.execute(sqlOrders);

                System.out.println("✅ Database initialized successfully!");

            } catch (SQLException e) {
                System.out.println("❌ Table creation error: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("❌ Connection Failed: " + e.getMessage());
        }
        return con;
    }

    // ✅ Add Record
    public void addRecord(String sql, Object... values) {
        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < values.length; i++) {
                if (values[i] instanceof Integer) pstmt.setInt(i + 1, (Integer) values[i]);
                else if (values[i] instanceof Double) pstmt.setDouble(i + 1, (Double) values[i]);
                else pstmt.setString(i + 1, values[i].toString());
            }

            pstmt.executeUpdate();
            System.out.println("✅ Record added successfully!");
        } catch (SQLException e) {
            System.out.println("❌ Error adding record: " + e.getMessage());
        }
    }

    // ✅ View Records
    public void viewRecords(String sqlQuery, String[] columnHeaders, String[] columnNames) {
        if (columnHeaders.length != columnNames.length) {
            System.out.println("❌ Error: Mismatch between column headers and names.");
            return;
        }

        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sqlQuery);
             ResultSet rs = pstmt.executeQuery()) {

            StringBuilder headerLine = new StringBuilder();
            headerLine.append("--------------------------------------------------------------------------------\n| ");
            for (String header : columnHeaders)
                headerLine.append(String.format("%-20s | ", header));
            headerLine.append("\n--------------------------------------------------------------------------------");
            System.out.println(headerLine);

            while (rs.next()) {
                StringBuilder row = new StringBuilder("| ");
                for (String col : columnNames) {
                    String value = rs.getString(col);
                    row.append(String.format("%-20s | ", value != null ? value : ""));
                }
                System.out.println(row);
            }
            System.out.println("--------------------------------------------------------------------------------");

        } catch (SQLException e) {
            System.out.println("❌ Error retrieving records: " + e.getMessage());
        }
    }

    // ✅ Update Record
    public void updateRecord(String sql, Object... values) {
        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < values.length; i++) {
                if (values[i] instanceof Integer) pstmt.setInt(i + 1, (Integer) values[i]);
                else if (values[i] instanceof Double) pstmt.setDouble(i + 1, (Double) values[i]);
                else pstmt.setString(i + 1, values[i].toString());
            }

            pstmt.executeUpdate();
            System.out.println("✅ Record updated successfully!");
        } catch (SQLException e) {
            System.out.println("❌ Error updating record: " + e.getMessage());
        }
    }

    // ✅ Delete Record
    public void deleteRecord(String sql, Object... values) {
        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < values.length; i++) {
                if (values[i] instanceof Integer)
                    pstmt.setInt(i + 1, (Integer) values[i]);
                else
                    pstmt.setString(i + 1, values[i].toString());
            }

            pstmt.executeUpdate();
            System.out.println("✅ Record deleted successfully!");
        } catch (SQLException e) {
            System.out.println("❌ Error deleting record: " + e.getMessage());
        }
    }
}
