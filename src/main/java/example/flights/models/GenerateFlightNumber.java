package example.flights.models;

import java.util.Random;

public class GenerateFlightNumber {
    public static String flightNumber() {
        Random random = new Random();

        String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        int c = random.nextInt(999);

        char x = abc.charAt(random.nextInt(abc.length() - 1));
        char z = abc.charAt(random.nextInt(abc.length() - 1));

        return String.valueOf(x) + z + "-" + c;
    }
}
