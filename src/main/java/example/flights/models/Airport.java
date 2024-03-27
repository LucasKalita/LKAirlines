package example.flights.models;

public enum Airport {
    WARSAW("Warsaw"),
    BERLIN("Berlin"),
    PARIS("Paris"),
    MILAN("Milan"),
    ROME("Rome"),
    MADRID("Madrid"),
    BELGRADE("Belgrade"),
    TALLINN("Tallinn"),
    RIGA("Riga"),
    HELSINKI("Helsinki");
    private final String displayName;

    Airport(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
