package example.flights.models;



import java.io.Serializable;
import java.time.LocalDateTime;


public class Flight implements Serializable {
    private final String flightNumber;
    private final PlaneModel planeModel;
    private final LocalDateTime departureDate;
    private final LocalDateTime expectedArrivalDate;
    private final Airport departureAirport;
    private final Airport arrivalAirport;
    private String status;

    @JsonCreator
    public Flight(@JsonProperty("flightNumber") final String flightNumber,
                  @JsonProperty("planeModel") final PlaneModel planeModel,
                  @JsonProperty("departureDate") final LocalDateTime departureDate,
                  @JsonProperty("expectedArrivalDate") final LocalDateTime expectedArrivalDate,
                  @JsonProperty("departureAirport") final Airport departureAirport,
                  @JsonProperty("arrivalAirport") final Airport arrivalAirport,
                  @JsonProperty("status") final String status) {
        this.flightNumber = flightNumber;
        this.planeModel = planeModel;
        this.departureDate = departureDate;
        this.expectedArrivalDate = expectedArrivalDate;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.status = status;
    }

    public Flight(PlaneModel planeModel,
                  LocalDateTime departureDate,
                  LocalDateTime expectedArrivalDate,
                  Airport departureAirport,
                  Airport arrivalAirport) {
        this.flightNumber = GenerateFlightNumber.flightNumber();
        this.planeModel = planeModel;
        this.departureDate = departureDate;
        this.expectedArrivalDate = expectedArrivalDate;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.status = "On Time";
    }

    @Override
    public String toString() {
        return "Flight number:" + flightNumber + " From: " + departureAirport + " to " + arrivalAirport
                + " Day of flight: " + departureDate.getDayOfMonth() + "-" + departureDate.getMonth() + "-" + departureDate.getYear()
                + " Time of flight: " + departureDate.getHour() + ":" + departureDate.getMinute()
                + " Expected landing date: " + expectedArrivalDate.getDayOfMonth() + "-" + expectedArrivalDate.getMonth() + "-" + expectedArrivalDate.getYear()
                + " Expected arrival time: " + expectedArrivalDate.getHour() + ":" + expectedArrivalDate.getMinute() + '\''
                + " Expected dealy: " + status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flight flight)) return false;

        if (!getFlightNumber().equals(flight.getFlightNumber())) return false;
        if (getPlaneModel() != flight.getPlaneModel()) return false;
        if (!getDepartureDate().equals(flight.getDepartureDate())) return false;
        if (!getExpectedArrivalDate().equals(flight.getExpectedArrivalDate())) return false;
        if (getDepartureAirport() != flight.getDepartureAirport()) return false;
        if (getArrivalAirport() != flight.getArrivalAirport()) return false;
        return getStatus().equals(flight.getStatus());
    }

    @Override
    public int hashCode() {
        int result = getFlightNumber().hashCode();
        result = 31 * result + getPlaneModel().hashCode();
        result = 31 * result + getDepartureDate().hashCode();
        result = 31 * result + getExpectedArrivalDate().hashCode();
        result = 31 * result + getDepartureAirport().hashCode();
        result = 31 * result + getArrivalAirport().hashCode();
        result = 31 * result + getStatus().hashCode();
        return result;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getStatus() {
        return status;
    }

    public PlaneModel getPlaneModel() {
        return planeModel;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public LocalDateTime getExpectedArrivalDate() {
        return expectedArrivalDate;
    }

    public Airport getDepartureAirport() {
        return departureAirport;
    }

    public Airport getArrivalAirport() {
        return arrivalAirport;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
