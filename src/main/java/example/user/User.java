package example.user;



import example.messages.Messages;
import example.messages.Threads;
import example.tickets.Ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {
    private String email;
    private String password;
    private Role role;
    private int wallet;
    private List<Ticket> ticketIDs;

    public List<Ticket> getTicketIDs() {
        return new ArrayList<>(ticketIDs);
    }

    public User(final String email, final String password) {
        this.email = email;
        this.password = password;
        this.role = Role.USER;
    }

    public User(final String email, final String password, final Role role, final int wallet, final List<Ticket> ticketIDs) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.wallet = wallet;
        this.ticketIDs = ticketIDs;
    }

    public User(final String email, final String password, final Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User changePassword(String newPassword) {
        return new User(this.getEmail(), newPassword, this.getRole(), this.getWallet(), this.getTicketIDs());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(final Role role) {
        this.role = role;
    }

    public int getWallet() {
        return wallet;
    }

    private void setWallet(final int wallet) {
        this.wallet = wallet;
    }

    public void addFundsToWallet(final int funds) {
        this.wallet += funds;
    }

    public boolean isUser() {
        return this.role.equals(Role.USER);
    }

    public boolean isAssociate() {
        return this.role.equals(Role.ASSOCIATE);
    }

    public void createMessage(Threads threads, String message) {
        threads.newThread(this, message);
    }

    public void respondToMessage(Messages message, String description) {
        message.respond(this, description);
    }

    @Override
    public String toString() {
        return this.email;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User user)) {
            return false;
        }
        return getWallet() == user.getWallet()
                && Objects.equals(getEmail(), user.getEmail())
                && Objects.equals(getPassword(), user.getPassword())
                && getRole() == user.getRole()
                && Objects.equals(getTicketIDs(), user.getTicketIDs());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail(), getPassword(), getRole(), getWallet(), getTicketIDs());
    }
}
