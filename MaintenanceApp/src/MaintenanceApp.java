import java.sql.*;
import java.util.Scanner;

// --- MODELS ---
enum SiteType {
    VILLA, APARTMENT, INDEPENDENT_HOUSE, OPEN_SITE
}

class Layout {
    private int siteId;
    private SiteType type;
    private int size;
    private int maintenanceCharges;
    private boolean occupied;
    private boolean booked;

    public Layout(int id, SiteType type, boolean occupied, boolean booked,int size) {
        this.siteId = id;
        this.type = type;
        this.occupied = occupied;
        this.booked = booked;
        this.size = size;
        this.maintenanceCharges = (occupied ? 9 : 6) * this.size;
    }

    public int getSiteId() {
        return siteId;
    }

    public SiteType getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    public int getMaintenanceCharges() {
        return maintenanceCharges;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public boolean isBooked() {
        return booked;
    }
}

class User {
    private String role;
    private int ownerId;

    public User(String role, int ownerId) {
        this.role = role;
        this.ownerId = ownerId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public String getRole() {
        return role;
    }
}

class Admin extends User {
    private static Admin instance = null;

    // Private constructor
    private Admin() {
        super("admin", 0); // Admin usually has a reserved ID like 0
    }

    // Static method to get the single instance
    public static Admin getInstance() {
        if (instance == null) {
            instance = new Admin();
        }
        return instance;
    }
}

// --- DAO INTERFACE ---
interface SiteDAO {
    // Admin operations
    void addSite(Layout l) throws SQLException;

    void addOwner(int siteId, int ownerId, String name, long phone) throws SQLException;

    void removeOwner(int ownerId) throws SQLException;

    void createCredentials(int ownerId, String user, String pass) throws SQLException;

    void collectMaintenance(int siteId) throws SQLException;

    void viewPendingApprovals() throws SQLException;

    void processRequest(int requestId, boolean isApproved) throws SQLException;

    void viewAllSitesWithDetails() throws SQLException;

    // Owner & Security operations
    int validateLogin(String user, String pass) throws SQLException;

    void viewOwnerSiteDetails(int ownerId) throws SQLException;

    void submitUpdateRequest(int ownerId, String name, long phone) throws SQLException;
}

class DBConnectionHelper {
    private static Connection connection = null;
    private static final String URL = "jdbc:postgresql://localhost:5433/data";
    private static final String USER = "postgres";
    private static final String PASS = "root";

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASS);
        }
        return connection;
    }
}

class DAOImplementation implements SiteDAO {

    @Override
    public void addSite(Layout l) throws SQLException {
        String sql = "INSERT INTO site_table (site_id, type, size, maintenance_charges, occupied, booked) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = DBConnectionHelper.getConnection().prepareStatement(sql)) {
            ps.setInt(1, l.getSiteId());
            ps.setString(2, l.getType().name());
            ps.setInt(3, l.getSize());
            ps.setInt(4, l.getMaintenanceCharges());
            ps.setBoolean(5, l.isOccupied());
            ps.setBoolean(6, l.isBooked());
            ps.executeUpdate();
            System.out.println("Site created successfully.");
        }
    }

    @Override
    public void addOwner(int siteId, int ownerId, String name, long phone) throws SQLException {
        Connection conn = DBConnectionHelper.getConnection();
        conn.setAutoCommit(false);
        try {
            String checkSql = "SELECT booked FROM site_table WHERE site_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(checkSql)) {
                ps.setInt(1, siteId);
                ResultSet rs = ps.executeQuery();
                if (rs.next() && rs.getBoolean("booked")) {
                    System.out.println("Error: Site is already booked.");
                    return;
                }
            }

            String ownerSql = "INSERT INTO owner_table (owner_id, site_id, owner_name, owner_phone_no, maintenance_paid) VALUES (?, ?, ?, ?, false)";
            try (PreparedStatement ps = conn.prepareStatement(ownerSql)) {
                ps.setInt(1, ownerId);
                ps.setInt(2, siteId);
                ps.setString(3, name);
                ps.setLong(4, phone);
                ps.executeUpdate();
            }

            conn.createStatement().execute("UPDATE site_table SET booked = true WHERE site_id = " + siteId);
            conn.commit();
            System.out.println("Owner assigned successfully.");
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    @Override
    public void removeOwner(int ownerId) throws SQLException {
        Connection conn = DBConnectionHelper.getConnection();
        conn.setAutoCommit(false);
        try {
            int siteId = -1;
            String findSite = "SELECT site_id FROM owner_table WHERE owner_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(findSite)) {
                ps.setInt(1, ownerId);
                ResultSet rs = ps.executeQuery();
                if (rs.next())
                    siteId = rs.getInt("site_id");
            }

            if (siteId != -1) {
                conn.createStatement().execute("DELETE FROM password WHERE owner_id = " + ownerId);
                conn.createStatement().execute("DELETE FROM owner_table WHERE owner_id = " + ownerId);
                conn.createStatement().execute("UPDATE site_table SET booked = false WHERE site_id = " + siteId);
                conn.commit();
                System.out.println("Owner removed and Site unbooked.");
            }
        } catch (SQLException e) {
            conn.rollback();
            throw new SQLException("please handle the request of owner before removing.");
        } finally {
            conn.setAutoCommit(true);
        }
    }

    @Override
    public void createCredentials(int ownerId, String user, String pass) throws SQLException {
        String sql = "INSERT INTO password (username, pass, owner_id) VALUES (?, ?, ?)";
        try (PreparedStatement ps = DBConnectionHelper.getConnection().prepareStatement(sql)) {
            ps.setString(1, user);
            ps.setString(2, pass);
            ps.setInt(3, ownerId);
            ps.executeUpdate();
            System.out.println("Credentials created.");
        }
        catch (SQLException e) {
            throw new SQLException("Username already exists. Choose a different one.");
        }
    }

    @Override
    public void collectMaintenance(int siteId) throws SQLException {
        String checkSql = "SELECT Booked FROM site_table WHERE site_id = ?";
        try (PreparedStatement ps = DBConnectionHelper.getConnection().prepareStatement(checkSql)) {
            ps.setInt(1, siteId);
            ResultSet rs = ps.executeQuery();
            if (rs.next() && !rs.getBoolean("booked")) {
                System.out.println("Error: Site is not booked. No maintenance to collect.");
                return;
            }
        }

        String sql = "UPDATE owner_table SET maintenance_paid = true WHERE site_id = ?";
        try (PreparedStatement ps = DBConnectionHelper.getConnection().prepareStatement(sql)) {
            ps.setInt(1, siteId);
            ps.executeUpdate();
            System.out.println("Payment recorded.");
        }
    }

    @Override
    public void viewPendingApprovals() throws SQLException {
        String sql = "SELECT * FROM request_table";
        try (Statement st = DBConnectionHelper.getConnection().createStatement(); ResultSet rs = st.executeQuery(sql)) {
            System.out.println("\n--- Pending Owner Updates ---");
            while (rs.next()) {
                System.out.printf("ReqID: %d | OwnerID: %d | New Name: %s\n",
                        rs.getInt("request_id"), rs.getInt("owner_id"), rs.getString("owner_name"));
            }
        }
    }

    @Override
    public void processRequest(int requestId, boolean isApproved) throws SQLException {
        Connection conn = DBConnectionHelper.getConnection();
        conn.setAutoCommit(false);
        try {
            if (isApproved) {
                String fetchSql = "SELECT * FROM request_table WHERE request_id = ?";
                try (PreparedStatement ps = conn.prepareStatement(fetchSql)) {
                    ps.setInt(1, requestId);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        String updateSql = "UPDATE owner_table SET owner_name = ?, owner_phone_no = ? WHERE owner_id = ?";
                        try (PreparedStatement ups = conn.prepareStatement(updateSql)) {
                            ups.setString(1, rs.getString("owner_name"));
                            ups.setLong(2, rs.getLong("owner_phone"));
                            ups.setInt(3, rs.getInt("owner_id"));
                            ups.executeUpdate();
                        }
                    }
                }
            }
            conn.createStatement().execute("DELETE FROM request_table WHERE request_id = " + requestId);
            conn.commit();
            System.out.println(isApproved ? "Approved and Updated." : "Rejected and Discarded.");
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    @Override
    public int validateLogin(String user, String pass) throws SQLException {
        String sql = "SELECT owner_id FROM password WHERE username=? AND pass=?";
        try (PreparedStatement ps = DBConnectionHelper.getConnection().prepareStatement(sql)) {
            ps.setString(1, user);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt("owner_id") : -1;
        }
    }

    @Override
    public void viewOwnerSiteDetails(int ownerId) throws SQLException {
        String sql = "SELECT s.*, o.owner_name, o.owner_phone_no, o.maintenance_paid FROM site_table s JOIN owner_table o ON s.site_id = o.site_id WHERE o.owner_id = ?";
        try (PreparedStatement ps = DBConnectionHelper.getConnection().prepareStatement(sql)) {
            ps.setInt(1, ownerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("\n--- My Profile & Site ---");
                System.out.printf("Owner: %s | Phone: %d\n", rs.getString("owner_name"), rs.getLong("owner_phone_no"));
                System.out.printf("Site: %d (%s) | Size: %d | Charges: %d\n", rs.getInt("site_id"),
                        rs.getString("type"), rs.getInt("size"), rs.getInt("maintenance_charges"));
                System.out.println("Occupied: " + rs.getBoolean("occupied") + " | Maint. Paid: "
                        + rs.getBoolean("maintenance_paid"));
            }
        }
    }

    @Override
    public void submitUpdateRequest(int ownerId, String name, long phone) throws SQLException {
        String sql = "INSERT INTO request_table (owner_id, owner_name, owner_phone) VALUES (?, ?, ?)";
        try (PreparedStatement ps = DBConnectionHelper.getConnection().prepareStatement(sql)) {
            ps.setInt(1, ownerId);
            ps.setString(2, name);
            ps.setLong(3, phone);
            ps.executeUpdate();
            System.out.println("Update request submitted.");
        }
    }

    @Override
    public void viewAllSitesWithDetails() throws SQLException {
        // We select all specific columns from site_table and join with owner_table
        String sql = "SELECT s.site_id, s.type, s.size, s.maintenance_charges, s.occupied, s.booked, " +
                "o.owner_name, o.owner_phone_no " +
                "FROM site_table s " +
                "LEFT JOIN owner_table o ON s.site_id = o.site_id " +
                "ORDER BY s.site_id ASC";

        try (Statement st = DBConnectionHelper.getConnection().createStatement();
                ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\n" + "=".repeat(120));
            System.out.printf("%-5s | %-30s | %-15s | %-15s | %-10s | %-20s | %-25s\n",
                    "ID", "Type", "Size", "Charges", "Occupied", "Status", "Owner & Contact");
            System.out.println("-".repeat(120));

            while (rs.next()) {
                int id = rs.getInt("site_id");
                String type = rs.getString("type");
                int size = rs.getInt("size");
                int charges = rs.getInt("maintenance_charges");
                String occupied = rs.getBoolean("occupied") ? "YES" : "NO";
                boolean isBooked = rs.getBoolean("booked");

                String status = isBooked ? "BOOKED" : "AVAILABLE";

                // Logic to handle unbooked sites
                String ownerName = rs.getString("owner_name");
                String contactInfo = isBooked ? (ownerName + " (" + rs.getLong("owner_phone_no") + ")") : "---";

                System.out.printf("%-5s | %-30s | %-15s | %-15s | %-10s | %-20s | %-25s\n",
                        id, type, size, charges, occupied, status, contactInfo);
            }
            System.out.println("=".repeat(120));
        }
    }
}

public class MaintenanceApp {
    static Scanner sc = new Scanner(System.in);
    static SiteDAO dao = new DAOImplementation();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Layout Management System ===");
            System.out.println("1. Admin Login\n2. Site Owner Login\n3. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            try {
                if (choice == 1)
                    adminLogin();
                else if (choice == 2)
                    ownerLogin();
                else
                    break;
            } catch (SQLException e) {
                System.out.println("Database Error: " + e.getMessage());
            }
        }
    }

    private static void adminLogin() throws SQLException {
    System.out.print("Password: ");
    if (sc.next().equals("admin123")) {
        // Accessing the Singleton instance
        Admin admin = Admin.getInstance();
        System.out.println("Welcome, " + admin.getRole().toUpperCase());
        adminMenu();
    } else {
        System.out.println("Wrong password.");
    }
}

    private static void adminMenu() throws SQLException {
        Admin currentAdmin = Admin.getInstance();
        if (currentAdmin == null) {
            System.out.println("Admin not initialized properly.");
            return;
        }
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Add Site\n2. Assign Owner\n3. Remove Owner\n4. Credentials");
            System.out.println("5. Collect Maintenance\n6. View Pending\n7. View All Sites\n8. Logout");
            System.out.print("Choose an option: ");
            int op = sc.nextInt();
            if (op == 8)
                break;

            switch (op) {
                case 1 -> {
                    System.out.print("ID: ");
                    int id = sc.nextInt();
                    System.out.print("Type (1:Villa, 2:Apt, 3:House, 4:Open): ");
                    int t = sc.nextInt();
                    if (t < 1 || t > 4) {
                        System.out.println("Invalid type.");
                        break;
                    }
                    boolean occ = t==4 ? false : true; // Open sites are never occupied
                    System.out.print("Size (in sqft): ");
                    int size = sc.nextInt();
                    dao.addSite(new Layout(id, SiteType.values()[t - 1], occ, false, size));
                }
                case 2 -> {
                    System.out.print("Site ID: ");
                    int sid = sc.nextInt();
                    System.out.print("Owner ID: ");
                    int oid = sc.nextInt();
                    System.out.print("Name: ");
                    sc.nextLine();
                    String name = sc.nextLine();
                    System.out.print("Phone: ");
                    long ph = sc.nextLong();
                    dao.addOwner(sid, oid, name, ph);
                }
                case 3 -> {
                    System.out.print("Owner ID: ");
                    dao.removeOwner(sc.nextInt());
                }
                case 4 -> {
                    System.out.print("Owner ID: ");
                    int oid = sc.nextInt();
                    System.out.print("User: ");
                    String u = sc.next();
                    System.out.print("Pass: ");
                    String p = sc.next();
                    dao.createCredentials(oid, u, p);
                }
                case 5 -> {
                    System.out.print("Site ID: ");
                    dao.collectMaintenance(sc.nextInt());
                }
                case 6 -> {
                    dao.viewPendingApprovals();
                    System.out.print("Enter Req ID (0 to exit): ");
                    int rid = sc.nextInt();
                    if (rid != 0) {
                        System.out.print("1. Approve | 2. Reject: ");
                        dao.processRequest(rid, sc.nextInt() == 1);
                    }
                }
                case 7 -> {
                    dao.viewAllSitesWithDetails();
                }
            }
        }
    }

    private static void ownerLogin() throws SQLException {
        System.out.print("User: ");
        String u = sc.next();
        System.out.print("Pass: ");
        String p = sc.next();
        int oid = dao.validateLogin(u, p);
        if (oid != -1)
            ownerMenu(new User("owner", oid));
        else
            System.out.println("Invalid credentials.");
    }

    private static void ownerMenu(User user) throws SQLException {
        while (true) {
            System.out.println("\n--- Owner Menu ---\n1. View Details\n2. Request Update\n3. Logout");
            System.out.print("Choose an option: ");
            int op = sc.nextInt();
            if (op == 3)
                break;
            if (op == 1)
                dao.viewOwnerSiteDetails(user.getOwnerId());
            else if (op == 2) {
                System.out.print("New Name: ");
                sc.nextLine();
                String n = sc.nextLine();
                System.out.print("New Phone: ");
                long ph = sc.nextLong();
                dao.submitUpdateRequest(user.getOwnerId(), n, ph);
            }
        }
    }
}