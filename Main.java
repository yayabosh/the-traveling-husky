// Command to start the program (type into Terminal): javac Main.java && java Main; rm *.class
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
        Scanner console = new Scanner(System.in);
        print("Welcome, Traveling Husky! My name is Main. Let's go traveling!\n");
        Thread.sleep(1000);
        print("First things first: choose a dataset.");
        print("On line 17 in my class, there's a line with a filename. Currently, that file contains locations around UW.");
        print("If you want to change the locations: make a new file and change the filename on line 16, or choose from an existing file in "
              + "the folder titled \"locations\". You can also modify existing files to add new locations.\n(press enter when ready)");
        console.nextLine();
        print("Okay, let's go traveling!");
        // Change this filename to a different dataset to generate a traveler for that dataset.
        City[] cities = generate(new File("locations/uw.csv"));

        for (;;) {
            Traveler traveler = new Traveler(cities);
            System.out.println("\nType the command in parentheses next to each algorithm to view it. If you want an explanation of each algorithm, view README.md.");
            System.out.println("Brute Force (bf) | Nearest Neighbor (nn) | Top Down (td) | Left to Right (lr) | Random (rn)");
            switch (console.next()) {
                case "bf":
                    if (cities.length > 13) {
                        print("😢 Sorry, the brute force algorithm can only handle up to 13 locations 😢!\nit would take " +
                                           "you around factorial time to solve the traveling salesman problem for your dataset.");
                        if (cities.length <= FACTORIALS.length - 1) {
                            print("That's " + 0.1 * FACTORIALS[cities.length] + " ms! 😳");
                        } else {
                            print("Factorial time on your dataset's length is a number so big we can't even calculate it without" +
                                               " blowing up your computer! 😱 (Just kidding. Java's long data type only" +
                                               " fits factorials up to " + FACTORIALS[FACTORIALS.length - 1] + "!");
                        }
                        print("\nYou can still try other algorithms on your datasets or try a dataset with <= 13 locations!");
                    } else {
                        bruteForce(traveler);
                    }
                    break;
                case "nn":
                    nearestNeighbor(traveler);
                    break;
                case "td":
                    topDown(traveler);
                    break;
                case "lr":
                    leftToRight(traveler);
                    break;
                case "rn":
                    random(traveler);
                    break;
                default:
                    System.out.println("You didn't enter a valid algorithm!");
            }
        }
    }

    private static void bruteForce(Traveler t) throws InterruptedException, IOException {
        System.out.println("Currently calculating shortest route between the locations using the brute force algorithm...");
        long before = System.currentTimeMillis();
        Route bf = t.bruteForce();
        long after = System.currentTimeMillis();
        print("Route:\n" + bf);
        statistics(bf, "bf", after - before, t);
    }

    private static void nearestNeighbor(Traveler t) throws InterruptedException, IOException {
        print("Currently calculating shortest route between the locations using the nearest neighbor algorithm...");
        long before = System.currentTimeMillis();
        Route nn = t.nearestNeighbor();
        long after = System.currentTimeMillis();
        print("Route:\n" + nn);
        statistics(nn, "nn", after - before, t);
    }

    private static void topDown(Traveler t) throws InterruptedException, IOException {
        print("Currently calculating shortest route between the locations using the top down algorithm...");
        long before = System.currentTimeMillis();
        Route td = t.topDown();
        long after = System.currentTimeMillis();
        print("Route:\n" + td);
        statistics(td, "td", after - before, t);
    }

    private static void leftToRight(Traveler t) throws InterruptedException, IOException {
        print("Currently calculating shortest route between the locations using the left to right algorithm...");
        long before = System.currentTimeMillis();
        Route lr = t.leftToRight();
        long after = System.currentTimeMillis();
        print("Route:\n" + lr);
        statistics(lr, "lr", after - before, t);
    }

    private static void random(Traveler t) throws InterruptedException, IOException {
        print("Currently calculating shortest route between the locations using the random algorithm...");
        long before = System.currentTimeMillis();
        Route rn = t.random();
        long after = System.currentTimeMillis();
        print("Route:\n" + rn);
        statistics(rn, "rn", after - before, t);
    }

    // Returns route statistics like accuracy, time, and optimization based on the given algorithm and route.
    private static void statistics(Route r, String algo, long time, Traveler t) throws InterruptedException, IOException {
        int actual = t.iterations;
        if (t.cities.length < 13) {
            print("\nRoute accuracy: \t\t" + accuracy(r, t));
        } else {
            print("\nCannot calculate route accuracy because using brute force to calculate the most accurate route would " +
                    "take too long (factorial time)!");
        }
        print("Route distance: \t\t%,.3f kilometers", r.distance);

        long expected = iterations(algo, t.cities.length);
        print("Expected # of iterations: \t%,d", expected);
        print("Actual number of iterations: \t%,d", actual);

        double savings = (expected - actual) / (double) expected;
        print("Expected computation time: \t%,d milliseconds", (long) (time / (1 - savings)));
        print("Actual computation time: \t%,d milliseconds", time);
        if (savings * 100 > 15) {
            print("😱Optimized computation by %,.2f%%!😱", savings * 100);
        } else {
            print("😐Optimized computation by %,.2f%%!😐", savings * 100);
        }
    }

    // Calculates and returns the accuracy of the given route (based off its distance).
    private static String accuracy(Route r, Traveler t) {
        double actual   = r.distance;
        double expected = t.bruteForce().distance;

        double accuracy = expected / actual * 100;
        if (accuracy > 75) {
            return String.format("🔥%.2f%%🔥! LETS GOOOOOOO!!!", accuracy);
        } else {
            return String.format("💩%.2f%%💩. maybe next time :(", accuracy);
        }
    }

    // Returns the given algorithm's expected number of iterations.
    private static long iterations(String algo, int length) {
        if (length > 13 && algo.equals("bf")) return Long.MAX_VALUE;
        switch (algo) {
            case "bf":
                return FACTORIALS[length];
            case "nn":
                return length * length;
            case "td":
            case "lr":
                return length * (int) (Math.log(length) / Math.log(2));
            default:
                return length;
        }
    }

    // Returns an array of Cities consisting of all the locations in the given file/dataset.
    private static City[] generate(File locations) throws IOException {
        List<City> aux = new LinkedList<>();
        int id = 0;
        try (Scanner c = new Scanner(locations)) {
            while (c.hasNextLine()) {
                String line = c.nextLine();
                // Ignore "commented out" lines in the dataset.
                if (line.charAt(0) != '#') {
                    aux.add(City.fromCsv(line + "," + id++));
                }
            }
        }
        return aux.toArray(City[]::new);
    }

    // Prints the given String character by character to the console, pausing for 30 ms between each character print.
    private static void print(String s) throws InterruptedException, IOException {
        int index = 0;
        while (index < s.length() && System.in.available() == 0) {
            System.out.print(s.charAt(index++));
            Thread.sleep(30);
        }
        System.out.println(s.substring(index));
    }

    private static void print(String s, Object... args) throws InterruptedException, IOException {
        print(String.format(s, args));
    }

    // Stores all factorials from 0 factorial to 20 factorial.
    private static final long[] FACTORIALS = new long[] {
                    1l,                  1l,                   2l,
                    6l,                 24l,                 120l,
                  720l,               5040l,               40320l,
               362880l,            3628800l,            39916800l,
            479001600l,         6227020800l,         87178291200l,
        1307674368000l,     20922789888000l,     355687428096000l,
     6402373705728000l, 121645100408832000l, 2432902008176640000l };
}
