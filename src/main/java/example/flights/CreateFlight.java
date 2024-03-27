package example.flights;

import org.example.flights.models.Airport;
import org.example.flights.models.Flight;
import org.example.flights.models.PlaneModel;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Scanner;

public class CreateFlight {
    public static void createFlight() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome into flight creator!");
        System.out.println("Available airports: ");
        Airport[] airports = Airport.values();
        System.out.println(Arrays.toString(airports));

        System.out.println("Please enter departure airport");
        String departureAirport = scanner.next().toUpperCase();

        System.out.println("Enter destination: ");
        String destination = scanner.next().toUpperCase();

        System.out.println("Available plane models: ");
        PlaneModel[] planeModels = PlaneModel.values();
        System.out.println(Arrays.toString(planeModels));
        System.out.println("Enter plane model:");
        String plane = scanner.next().toUpperCase();

        System.out.println("Please enter date of flight:");
        System.out.print("Year: ");
        int year = scanner.nextInt();
        System.out.print("Month: ");
        int month = scanner.nextInt();
        System.out.print("Day: ");
        int day = scanner.nextInt();

        System.out.println("Please enter time of departure: ");
        System.out.print("Hour: ");
        int hour = scanner.nextInt();
        System.out.print("Minute: ");
        int minute = scanner.nextInt();

        LocalDateTime dateOfDeparture = LocalDateTime.of(year, month, day, hour, minute);

        System.out.println("Please enter date of arrive:");
        System.out.print("Year: ");
        int year1 = scanner.nextInt();
        System.out.print("Month: ");
        int month1 = scanner.nextInt();
        System.out.print("Day: ");
        int day1 = scanner.nextInt();

        System.out.println("Please enter time of expected landing: ");
        System.out.print("Hour: ");
        int hour1 = scanner.nextInt();
        System.out.print("Minute: ");
        int minute1 = scanner.nextInt();

        LocalDateTime dateOfArrival = LocalDateTime.of(year1, month1, day1, hour1, minute1);

        Flight flight = new Flight(PlaneModel.valueOf(plane),
                dateOfDeparture,
                dateOfArrival,
                Airport.valueOf(departureAirport),
                Airport.valueOf(destination));

        System.out.println("Flight has been made. Details:");
        System.out.println(flight);

        System.out.println("Do you want to save this flight? Y/N");
        String yes = scanner.next();
        if (yes.equalsIgnoreCase("y")) {
            FlightOperatorService.getInstance().addFlight(flight);
        } else System.out.println("Created Flight has been discarded");
    }
}
