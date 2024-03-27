package example.user;

public class Associate extends User {
    public Associate(String email, String password) {
        super(email, password);
        setRole(Role.ASSOCIATE);
    }

    public <T> void cancelFlight(T flight) {
        //TODO implementFlight
    }

    public <T> void delayFlight(T flight) {
        //TODO implementFlight
    }

    public void getUnAnsweredMessages() {

    }
}
