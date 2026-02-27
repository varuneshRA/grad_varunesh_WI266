import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class App {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://db:5432/test"; 
        String user = "postgres";
        String password = "postgres";

        System.out.println("Connecting to database: " + url);

        // 1. Logic to perform the database task
        for (int i = 0; i < 10; i++) {
            try (Connection conn = DriverManager.getConnection(url, user, password)) {
                Statement stmt = conn.createStatement();
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users (id SERIAL, name VARCHAR(255))");
                stmt.executeUpdate("INSERT INTO users (name) VALUES ('Java Docker User')");

                System.out.println("✅ Record inserted successfully into 'test' database!");
                break; 
            } catch (Exception e) {
                System.out.println("Connection failed, retrying... " + e.getMessage());
                try { Thread.sleep(3000); } catch (InterruptedException ie) {}
            }
        }

        // 2. THE KEEP-ALIVE PART
        // This prevents the main thread from exiting, so the container stays "Up"
        System.out.println("🚀 Task finished. Container is staying alive for inspection...");
        try {
            Thread.currentThread().join(); 
        } catch (InterruptedException e) {
            System.out.println("Container interrupted.");
        }
    }
}