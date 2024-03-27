package example.user;



import example.flights.FlightOperatorService;
import example.flights.menu.MenuHelper;
import example.flights.models.Flight;
import example.flights.operators.UserFlightOperator;
import example.json.UserJsonController;
import example.tickets.GenerateTicketNumber;
import example.tickets.Luggage;
import example.tickets.Ticket;
import example.tickets.TicketOperatorService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserController {
    private static final UserJsonController userJsonController = new UserJsonController();

    public static User login(String email, String password) {
        return LoginController.login(email, password);
    }

    public static User logout() {
        return LoginController.logout();
    }

    public static void registerUser(String email, String password) {
        RegisterController.register(email, password);
    }

    public static boolean changePassword(String email, String password, String newPassword) {
        return userJsonController.changePassword(login(email, password), newPassword);
    }

    public static void registerAssociate(String email, String password) throws IOException {
        RegisterController.registerAssociate(email, password);
    }

    public static void addFunds(User user, int funds) {
        userJsonController.addFunds(user, funds);
    }

    public static void buyTicket(User user){
        UserFlightOperator userFlightOperator = new UserFlightOperator();
        userFlightOperator.openUser();
        Flight flight = FlightOperatorService.getInstance().findFlightByID();
        if(flight == null) {
            return;
        }
        int numberOfTickets = MenuHelper.getNumberFromUserWithMessage("NUMBER OF TICKETS");
        int costOfTickets = 0;
        List<Ticket> ticketList = new ArrayList<>();
        for(int i = 0; i < numberOfTickets; i++) {
            System.out.println("DETAILS FOR " + (i+1) + " TICKET:");
            String name = MenuHelper.getInputFromUserWithMessage("NAME");
            String surname = MenuHelper.getInputFromUserWithMessage("SURNAME");
            int passportNumber = MenuHelper.getNumberFromUserWithMessage("PASSPORT");
            String luggage = MenuHelper.getInputFromUserWithMessage("LUGGAGE [BIG][SMALL][NO LUGGAGE]");
            Luggage luggage1;
            switch (luggage.toUpperCase()) {
                case "BIG" -> luggage1 = Luggage.B;
                case "SMALL" -> luggage1 = Luggage.S;
                default -> luggage1 = Luggage.N;
            }
            String premiumSeats = MenuHelper.getInputFromUserWithMessage("PREMIUM SEATS [Y] YES , [N] NO");
            double price = 10;
            boolean isPremiumSeat = false;
            if(premiumSeats.equals("Y")) {
                isPremiumSeat = true;
                price += 5;
            }
            String prioritySeats = MenuHelper.getInputFromUserWithMessage("PRIORITY SEATS [Y] YES , [N] NO");
            boolean isPrioritySeat = false;
            if(prioritySeats.equals("Y")) {
                isPrioritySeat = true;
                price += 5;
            }
            costOfTickets += price;
            String ticketNumber = GenerateTicketNumber.TicketNumber();
            ticketList.add(new Ticket(ticketNumber,
                                         name,
                                         surname,
                                         passportNumber,
                                         price,
                                         flight.getFlightNumber(),
                                         flight.getPlaneModel(),
                                         0,
                                         luggage1,
                                         isPremiumSeat,
                                         isPrioritySeat,
                                         user.getEmail()));
        }
        if(user.getWallet() < costOfTickets) {
            System.out.println("NO FUNDS TO BUY ALL TICKETS");

        }else {
            UserController.addFunds(user, costOfTickets * -1);
            ticketList.forEach(ticket -> {
                System.out.println(ticket.ticketNumber());
                TicketOperatorService.getInstance().addTicket(ticket);
            });
        }
    }
}
