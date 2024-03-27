package example.tickets;

import java.util.LinkedList;

public class TicketManager {
    private static final LinkedList<Ticket> ticketCollection = new LinkedList<>();
    public static void addTicketToCollection(Ticket ticket){
        ticketCollection.add(ticket);
    }
    public static void printTicketCollection(){
        for (Ticket ticket : ticketCollection){
            System.out.println("flight number: " + ticket.flightNumber());
            System.out.println("ticket number: " + ticket.ticketNumber());
        }
    }

}
