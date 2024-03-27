package example.flights;


import example.flights.models.Airport;
import example.flights.models.Flight;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class FlightOperatorService {
    private static final String flightListDirectory = "Main/src/main/resources/flightList.json";
    private static FlightOperatorService instance;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Scanner scanner = new Scanner(System.in);
    private List<Flight> flights;

    private FlightOperatorService() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new ParameterNamesModule());
        this.flights = new ArrayList<>();
        loadFromFlightListJson();
    }

    public static FlightOperatorService getInstance() {
        if (instance == null) instance = new FlightOperatorService();
        return instance;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    private void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public void addFlight(Flight flight) {
        this.flights.add(flight);
        this.commitChanges();
    }

    private void deleteFlight(Flight flight) {
        this.flights.remove(flight);
        this.commitChanges();
    }

    private void commitChanges() {
        try {
            String jsonString = objectMapper.writeValueAsString(flights);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(flightListDirectory))) {
                writer.write(jsonString);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void loadFromFlightListJson() {
        try {
            String jsonPayload = Files.readString(Paths.get(flightListDirectory));
            setFlights(objectMapper.readValue(jsonPayload, new TypeReference<>() {
            }));
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void showFlightsByArrivalAirport() {
        System.out.print("Enter Arrival airport:");
        String input = scanner.next().toUpperCase();
        FlightOperatorService.getInstance().getFlights().stream()
                .filter(x -> x.getArrivalAirport().equals(Airport.valueOf(input)))
                .forEach(System.out::println);
    }

    public void showFlightsByDepartureAirport() {
        System.out.print("Enter Departure airport:");
        String input = scanner.next().toUpperCase();
        FlightOperatorService.getInstance().getFlights().stream()
                .filter(x -> x.getDepartureAirport().equals(Airport.valueOf(input)))
                .forEach(System.out::println);
    }

    public void showFlightsBeforeArrivalDate() {
        System.out.print("Year: ");
        int year1 = scanner.nextInt();
        System.out.print("Month: ");
        int month1 = scanner.nextInt();
        System.out.print("Day: ");
        int day1 = scanner.nextInt();

        LocalDateTime l1 = LocalDateTime.of(year1, month1, day1, 0, 0);

        FlightOperatorService.getInstance().getFlights().stream()
                .filter(x -> x.getDepartureDate().equals(l1) || x.getDepartureDate().isBefore(l1))
                .forEach(System.out::println);
    }

    public void showFlightsAfterArrivalDate() {
        System.out.print("Year: ");
        int year1 = scanner.nextInt();
        System.out.print("Month: ");
        int month1 = scanner.nextInt();
        System.out.print("Day: ");
        int day1 = scanner.nextInt();

        LocalDateTime l1 = LocalDateTime.of(year1, month1, day1, 0, 0);

        FlightOperatorService.getInstance().getFlights().stream()
                .filter(x -> x.getDepartureDate().equals(l1) || x.getDepartureDate().isAfter(l1))
                .forEach(System.out::println);
    }

    public void showFlightsByDepartureDate() {
        System.out.print("Year: ");
        int year1 = scanner.nextInt();
        System.out.print("Month: ");
        int month1 = scanner.nextInt();
        System.out.print("Day: ");
        int day1 = scanner.nextInt();

        LocalDateTime l1 = LocalDateTime.of(year1, month1, day1, 0, 0);

        FlightOperatorService.getInstance().getFlights().stream()
                .filter(x -> x.getDepartureDate().equals(l1) || x.getDepartureDate().isAfter(l1))
                .forEach(System.out::println);
    }

    public void deleteFlightById() {
        System.out.println("Deleting flight, type flight ID you want to delete:");
        String input = scanner.next().toUpperCase();
        FlightOperatorService.getInstance().getFlights().stream()
                .filter(x -> x.getFlightNumber().equals(input))
                .findFirst()
                .ifPresentOrElse(this::flightFoundPerformDeleteOperation, this::flightNotFoundTryAgain);
    }

    private void flightFoundPerformDeleteOperation(Flight flight) {
        System.out.println(flight);
        System.out.println("Are you sure you want to remove this flight: Y/N");
        String y = scanner.next().toUpperCase();
        if (y.equals("Y")) deleteFlight(flight);
    }

    private void flightNotFoundTryAgain() {
        System.out.println("Flight not found, would you like to try again? Y/N");
        String y = scanner.next().toUpperCase();
        if (y.equals("Y")) deleteFlightById();
    }

    public void changeFlightStatusByID() {
        System.out.print("Please type flight number which status you want to change:");
        String input = scanner.next().toUpperCase();
        this.flights.stream()
                .filter(x -> x.getFlightNumber().equals(input))
                .findFirst().ifPresentOrElse(this::changeFlightStatus, this::flightIDNotFound);
    }

    private void changeFlightStatus(Flight flight) {
        System.out.println("Please enter new status for this flight");
        String orginal = flight.getStatus();
        String input = scanner.next().toUpperCase();
        flight.setStatus(input);
        System.out.println("Status changed");
        System.out.println(flight);
        System.out.println("Would you like to save this status? Y/N");
        String y = scanner.next().toUpperCase();
        if (y.equals("N")) flight.setStatus(orginal);
        if (y.equals("Y")) this.commitChanges();
        else System.out.print("Unknown message, try again: ");
    }

    private void flightIDNotFound() {
        System.out.println("Flight with this ID not Found, would you like to try again? Y/N");
        String y = scanner.next().toUpperCase();
        if (y.equals("Y")) changeFlightStatusByID();
    }

    public  Flight findFlightByID(){
        System.out.println("ENTER FLIGHT NUMBER:");
        String input = scanner.next().toUpperCase();
        return FlightOperatorService.getInstance().getFlights().stream()
                .filter(x -> x.getFlightNumber().equals(input))
                .findFirst().orElse(null);
    }
}
