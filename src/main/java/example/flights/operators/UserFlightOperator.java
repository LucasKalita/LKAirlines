package example.flights.operators;

import org.example.flights.FlightFilter;

import java.util.Arrays;
import java.util.Scanner;

public class UserFlightOperator {
    FlightFilter flightFilter = new FlightFilter();
    private boolean working = true;

    public void openUser() {
        Scanner scanner = new Scanner(System.in);
        String[] inputs = {"1", "2"};
        while (working) {
            System.out.println("Welcome to Airlines management, please choose what you wish to do:");
            System.out.println("Press 1 filter flights");
            System.out.println("Press 2 to log out");

            System.out.print("Enter message:");
            String userInput = scanner.next();
            if (!Arrays.asList(inputs).contains(userInput)) {
                System.out.println("Unknown Message, try again");
                continue;
            }
            switch (userInput) {
                case "1" -> flightFilter.openFlightFilters();
                case "2" -> working = false;
            }
        }
    }
}
