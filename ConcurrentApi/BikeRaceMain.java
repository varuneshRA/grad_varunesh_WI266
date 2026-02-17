import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;

class Rider implements Callable<Rider> {
    private final String riderName;
    private final int raceDistance;
    private int covered = 0;
    private LocalTime start;
    private LocalTime finish;
    private final CountDownLatch latch;
    private final Random random = new Random();

    public Rider(String riderName, CountDownLatch latch, int raceDistance) {
        this.riderName = riderName;
        this.latch = latch;
        this.raceDistance = raceDistance;
    }

    @Override
    public Rider call() throws Exception {
        // Wait for the starter pistol
        latch.await();
        
        start = LocalTime.now();
        
        while (covered < raceDistance) {
            // Randomize distance covered in each "burst" (50m to 150m)
            int move = random.nextInt(101) + 50;
            covered = Math.min(covered + move, raceDistance);
            
            printProgressBar();
            
            // Randomize fatigue/speed (pause between 200ms and 600ms)
            Thread.sleep(random.nextInt(401) + 200);
        }

        finish = LocalTime.now();
        System.out.println("\n[!] " + riderName + " HAS FINISHED!");
        return this;
    }

    private void printProgressBar() {
        int barLength = 20;
        double percentage = (double) covered / raceDistance;
        int filled = (int) (percentage * barLength);
        
        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < barLength; i++) {
            if (i < filled) bar.append("=");
            else if (i == filled) bar.append(">");
            else bar.append(" ");
        }
        bar.append("]");
        
        System.out.printf("%-12s %s %d/%dm\n", riderName, bar.toString(), covered, raceDistance);
    }

    public String getRiderName() { return riderName; }
    public LocalTime getStart() { return start; }
    public LocalTime getFinish() { return finish; }
    public long getDurationMs() { return Duration.between(start, finish).toMillis(); }
}

class RaceGame {
    private int totalDistance;
    private int totalRiders;
    private final Scanner sc = new Scanner(System.in);

    public void setupAndBegin() throws Exception {
        System.out.println("--- RACE CONFIGURATION ---");
        System.out.print("Enter Distance (KM): ");
        totalDistance = sc.nextInt() * 1000;

        System.out.print("Enter Number of Riders: ");
        totalRiders = sc.nextInt();

        ExecutorService service = Executors.newFixedThreadPool(totalRiders);
        CountDownLatch latch = new CountDownLatch(1);
        List<Rider> riderTasks = new ArrayList<>();

        for (int i = 0; i < totalRiders; i++) {
            System.out.print("Enter name for Rider " + (i + 1) + ": ");
            riderTasks.add(new Rider(sc.next(), latch, totalDistance));
        }

        System.out.println("\nReady... Set...");
        for (int i = 3; i >= 1; i--) {
            System.out.println(i + "...");
            Thread.sleep(1000);
        }
        
        System.out.println("GO!!\n");
        latch.countDown(); // Start all riders simultaneously

        List<Future<Rider>> futures = new ArrayList<>();
        for (Rider r : riderTasks) {
            futures.add(service.submit(r));
        }

        List<Rider> completedRiders = new ArrayList<>();
        for (Future<Rider> f : futures) {
            completedRiders.add(f.get());
        }

        service.shutdown();
        displayResults(completedRiders);
    }

    private void displayResults(List<Rider> riders) {
        // Sort by duration (fastest time)
        riders.sort(Comparator.comparingLong(Rider::getDurationMs));
        
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

        System.out.println("\n" + "=".repeat(60));
        System.out.println("                      FINAL STANDINGS");
        System.out.println("=".repeat(60));
        System.out.printf("%-5s %-15s %-15s %-15s %-10s\n", "Rank", "Name", "Start", "Finish", "Time(s)");
        System.out.println("-".repeat(60));

        for (int i = 0; i < riders.size(); i++) {
            Rider r = riders.get(i);
            System.out.printf("%-5d %-15s %-15s %-15s %.3fs\n", 
                (i + 1), 
                r.getRiderName(), 
                r.getStart().format(timeFormat), 
                r.getFinish().format(timeFormat), 
                r.getDurationMs() / 1000.0);
        }
        System.out.println("=".repeat(60));
    }
}

public class BikeRaceMain {
    public static void main(String[] args) throws Exception {
        new RaceGame().setupAndBegin();
    }
}