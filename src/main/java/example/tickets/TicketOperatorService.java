package example.tickets;



import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class TicketOperatorService {
    private static final String ticketListDirectory = "Main/src/main/resources/ticketslist.json";
    private static TicketOperatorService instance;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Scanner scanner = new Scanner(System.in);
    private List<Ticket> tickets;

    private TicketOperatorService() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new ParameterNamesModule());
        this.tickets = new ArrayList<>();
        loadFromTicketListJson();
    }

    public static TicketOperatorService getInstance() {
        if (instance == null) instance = new TicketOperatorService();
        return instance;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    private void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }


    private void commitChangesTicket() {
        try {
            String jsonString = objectMapper.writeValueAsString(tickets);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(ticketListDirectory))) {
                writer.write(jsonString);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    public void loadFromTicketListJson() {
        try {
            String jsonPayload = Files.readString(Paths.get(ticketListDirectory));
            setTickets(objectMapper.readValue(jsonPayload, new TypeReference<>() {
            }));
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    private void ticketFoundPerformDeleteOperation(Ticket ticket) {
        System.out.println(ticket);
        System.out.println("Are you sure you want to remove this flight: Y/N");
        String y = scanner.next().toUpperCase();
        if (y.equals("Y")) deleteTicketById();
    }
    public void deleteTicketById() {
        System.out.println("Deleting ticket, type ticekt ID you want to delete:");
        String input = scanner.next().toUpperCase();
        TicketOperatorService.getInstance().getTickets().stream()
                .filter(x -> x.ticketNumber().equals(input))
                .findFirst()
                .ifPresentOrElse(this::ticketFoundPerformDeleteOperation, this::ticketNotFoundTryAgain);
    }
    private void ticketNotFoundTryAgain() {
        System.out.println("Flight not found, would you like to try again? Y/N");
        String y = scanner.next().toUpperCase();
        if (y.equals("Y")) deleteTicketById();
    }
    public void findTicketByID(){
        System.out.print("Please type ID of ticket:");
        String input = scanner.next().toUpperCase();
        System.out.println("Please type your email");
        String email= scanner.next();
        TicketOperatorService.getInstance().getTickets().stream()
                .filter(x -> x.ticketNumber().equals(input))
                .filter(x -> x.email().equalsIgnoreCase(email))
                .findFirst()
                .ifPresentOrElse(System.out::println, this::ticketIDNotFoundTryAgain);
    }
    private void ticketIDNotFoundTryAgain() {
        System.out.println("Flight not found, would you like to try again? Y/N");
        String y = scanner.next().toUpperCase();
        if (y.equals("Y")) findTicketByID();
    }
    public void addTicket(Ticket ticket) {
        this.tickets.add(ticket);
        this.commitChangesTicket();
    }
}
