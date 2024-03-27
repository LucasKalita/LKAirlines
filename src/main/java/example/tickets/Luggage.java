package example.tickets;

public enum Luggage {
    N("NOLUGGAGE"),
    S("SMALL"),
    B("BIG");

    public final String label;

    Luggage(String label) {
        this.label = label;
    }
}

