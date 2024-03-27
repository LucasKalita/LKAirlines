package example.flights;

import java.util.Arrays;
import java.util.Scanner;

public class FlightFilter {
    private boolean working = true;

    public void openFlightFilters() {
        Scanner scanner = new Scanner(System.in);
        String[] inputs = {"1", "2", "3", "4", "5", "6", "7", "8"};
        while (working) {
            System.out.println("Welcome to Airlines, please choose what you wish to do:");
            System.out.println("Press 1 for showing all available flights");
            System.out.println("Press 2 to filter departue flights by date by: ");
            System.out.println("Press 3 to filer arrival flights after date");
            System.out.println("Press 5 to filter by departue airport");
            System.out.println("Press 6 to filter by arrival airport");
            System.out.println("Press 7 to find flight by Flight Number:");
            System.out.println("Press 8 to log out");
            System.out.print("Enter message:");
            String userInput = scanner.next();
            if (!Arrays.asList(inputs).contains(userInput)) {
                System.out.println("Unknown Message, try again");
            } else {
                switch (userInput) {
                    case "1" -> FlightOperatorService.getInstance().getFlights().forEach(System.out::println);

                    case "2" -> FlightOperatorService.getInstance().showFlightsByDepartureDate();

                    case "3" -> FlightOperatorService.getInstance().showFlightsBeforeArrivalDate();

                    case "4" -> FlightOperatorService.getInstance().showFlightsAfterArrivalDate();

                    case "5" -> FlightOperatorService.getInstance().showFlightsByDepartureAirport();

                    case "6" -> FlightOperatorService.getInstance().showFlightsByArrivalAirport();

                    case "7" -> FlightOperatorService.getInstance().findFlightByID();

                    case "8" -> working = false;
                }
            }
        }
    }
}