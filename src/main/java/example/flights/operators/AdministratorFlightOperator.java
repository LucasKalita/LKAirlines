package example.flights.operators;

import org.example.flights.CreateFlight;
import org.example.flights.FlightFilter;
import org.example.flights.FlightOperatorService;

import java.util.Arrays;
import java.util.Scanner;

public class AdministratorFlightOperator {
    FlightFilter flightFilter = new FlightFilter();
    private boolean working = true;

    public void openAdmin() {
        Scanner scanner = new Scanner(System.in);
        String[] inputs = {"1", "2", "3", "4", "5"};
        while (working) {
            System.out.println("Welcome to Airlines management, please choose what you wish to do:");
            System.out.println("Press 1 filter flights");
            System.out.println("Press 2 create flight");
            System.out.println("Press 3 delete flight");
            System.out.println("Press 4 to change flight status");
            System.out.println("Press 5 to log out");

            System.out.print("Enter message:");
            String userInput = scanner.next();
            if (!Arrays.asList(inputs).contains(userInput)) {
                System.out.println("Unknown Message, try again");
                continue;
            }
            switch (userInput) {
                case "1" -> flightFilter.openFlightFilters();

                case "2" -> CreateFlight.createFlight();

                case "3" -> FlightOperatorService.getInstance().deleteFlightById();

                case "4" -> FlightOperatorService.getInstance().changeFlightStatusByID();

                case "5" -> working = false;
            }

        }
    }
}
