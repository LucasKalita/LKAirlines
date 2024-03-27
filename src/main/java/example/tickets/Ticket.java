package example.tickets;


import example.flights.models.PlaneModel;

public record Ticket (
     String ticketNumber,
     String name,
     String surname,
     int passportNumber,
     double price,
     String flightNumber,
     PlaneModel planeModel,
     int seatNumber,
     Luggage luggage,
     boolean premiumseats,
     boolean priorityseats,
     String email
){}



