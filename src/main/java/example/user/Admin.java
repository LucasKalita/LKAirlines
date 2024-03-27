package example.user;


import example.json.UserJsonController;

public class Admin extends User {
    private final UserJsonController users = new UserJsonController();

    public Admin() {
        super("admin@acs", "admin");
        setRole(Role.ADMIN);
    }
}
