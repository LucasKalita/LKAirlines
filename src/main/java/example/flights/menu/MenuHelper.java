package example.flights.menu;



import example.flights.operators.AdministratorFlightOperator;
import example.flights.operators.AssistanceFlightOperator;
import example.flights.operators.UserFlightOperator;
import example.messages.Message;
import example.messages.Messages;
import example.messages.ThreadsController;
import example.tickets.TicketOperatorService;
import example.user.Quest;
import example.user.Role;
import example.user.User;
import example.user.UserController;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class MenuHelper {
    private static final Scanner scanner = new Scanner(System.in);

    public static String getInputFromUserWithMessage(String message) {
        System.out.print(message + ": ");
        return scanner.nextLine();
    }

    public static void displayLoginMenu() {
        displayWelcomeMessageForAccount("Quest");
        System.out.println("1. LOGIN");
        System.out.println("2. REGISTER");
        displayExitAppOption();
    }

    public static void displayAdminMenu() {
        displayWelcomeMessageForAccount("Admin");
        System.out.println("1. CREATE ASSOCIATE ACCOUNT");
        System.out.println("2. CREATE FLIGHT");
        displayLogoutOption();
        displayExitAppOption();
    }

    public static String[] getEmailAndPassword() {
        String[] arrayToReturn = new String[2];
        arrayToReturn[0] = MenuHelper.getInputFromUserWithMessage("EMAIL");
        arrayToReturn[1] = MenuHelper.getInputFromUserWithMessage("PASSWORD");
        return arrayToReturn;
    }

    public static void getNotImplementedChoice(String choice) {
        System.out.println("CHOICE '" + choice + "'  NOT FOUND");
    }

    private static void displayWelcomeMessageForAccount(String account) {
        System.out.println("WELCOME " + account + "!");
    }

    public static void displayAssociateMenu(String email) {
        displayWelcomeMessageForAccount(email);
        System.out.println("1. DISPLAY FLIGHTS");
        System.out.println("2. NON ANSWERED MESSAGES FROM USER");
        System.out.println("3. RECLAMATIONS");
        displayChangePasswordOption();
        displayExitAppOption();
        displayLogoutOption();
    }

    public static void displayUnansweredMessages(List<Messages> messagesList) {
        System.out.printf("%6.6s|%15.15s|%15.15s|%15.15s\n", "CHOICE", "USER", "DESCRIPTION", "DATE");
        for (int i = 0; i < messagesList.size(); i++) {
            Messages messages = messagesList.get(i);
            Message message = messages.getMessages().get(messages.getMessages().size() - 1);
            LocalDateTime localDateTime = message.timestamp();
            String date = localDateTime.format(DateTimeFormatter.BASIC_ISO_DATE);
            System.out.printf("%5s|%15s|%15s|%15s\n", i, message.user(), message.description(), date);
        }
    }

    public static void displayLastMessages(List<Messages> messages) {
        StringBuilder stringBuilder = new StringBuilder();
        if (messages.size() > 0) {
            System.out.printf("%6.6s|%15.15s|%15.15s|%15.15s\n", "NUMBER", "USER", "DESCRIPTION", "DATE");
            for (int i = 0; i < messages.size(); i++) {
                int indexOfLastMessage = messages.get(i).getMessages().size() - 1;
                Message message = messages.get(i).getMessages().get(indexOfLastMessage);
                String date = displaySimpleDateTime(message.timestamp());
                System.out.printf("%5s|%15s|%15s|%15s\n", i, message.user(), message.description(), date);
            }
            stringBuilder.append("[NUMBER] - respond to user | [ENTER] BACK");
            System.out.println(stringBuilder);
        } else {
            System.out.println("NO MESSAGES [N] NEW MESSAGE | [ENTER] BACK");
        }
    }

    private static String displaySimpleDateTime(LocalDateTime dateTime) {
        int year = dateTime.getYear();
        int day = dateTime.getDayOfMonth();
        int month = dateTime.getMonthValue();
        int hour = dateTime.getHour();
        int minute = dateTime.getMinute();
        int second = dateTime.getSecond();
        return year + "." + month + "." + day + " " + hour + ":" + minute + ":" + second;
    }

    public static int getNumberFromUserWithMessage(String message) {
        while (true) {
            String userInput = getInputFromUserWithMessage(message);
            if(userInput.isEmpty()) {
                throw new IllegalArgumentException("ENTER DETECTED");
            }
            try {
                return Integer.parseInt(userInput);
            } catch (Exception e) {
                System.out.println("NOT A NUMBER, TRY AGAIN");
            }
        }
    }

    public static void displayUserMenu(User email) {
        displayWelcomeMessageForAccount(email.getEmail() + " FUNDS: " + email.getWallet());
        System.out.println("1. DISPLAY FLIGHTS");
        System.out.println("2. SEND MESSAGE FOR ACS");
        System.out.println("3. RECLAMATION");
        System.out.println("4. ADD FUNDS");
        System.out.println("5. BUY TICKETS");
        System.out.println("6. MY TICKETS");
        displayChangePasswordOption();
        displayExitAppOption();
        displayLogoutOption();
    }

    public static void displayUserTickets(User user) {
        TicketOperatorService.getInstance().getTickets().stream()
                .filter(ticket -> ticket.email().equals(user.getEmail()))
                .forEach(ticket -> {
                    System.out.println(ticket.ticketNumber());
                });
    }

    private static void displayLogoutOption() {
        System.out.println("L. LOGOUT");
    }

    private static void displayExitAppOption() {
        System.out.println("E. EXIT APP");
    }

    private static void displayChangePasswordOption() {
        System.out.println("S. CHANGE PASSWORD");
    }

    public static void displayMessagesFromUser(final List<Messages> messages) {
        StringBuilder stringBuilder = new StringBuilder();
        if (messages.size() > 0) {
            System.out.printf("%4.4s|%15.15s|%15.15s|%15.15s\n", "ID", "LAST TO RESPOND", "LAST MESSAGE", "DATE");
            for (int i = 0; i < messages.size(); i++) {
                List<Message> lastMessages = messages.get(i).getMessages();
                Message lastMessage = lastMessages.get(lastMessages.size() - 1);
                System.out.printf("%4.4s|%15.15s|%15.15s|%15.15s\n", i, lastMessage.user(), lastMessage.description(), lastMessage.timestamp());
            }
            stringBuilder.append("[NUMBER] - display details |");
        } else {
            System.out.println("NO MESSAGES");
        }
        stringBuilder.append("[N] - NEW MESSAGE FOR ACS | [ENTER] CANCEL");
        stringBuilder.append("\n");
        System.out.println(stringBuilder);
    }

    public static void displayAllMessages(Messages messages) {
        messages.getMessages().forEach(message -> {
            System.out.println("USER: " + message.user());
            System.out.println("MESSAGE: " + message.description());
            System.out.println("DATE: " + message.timestamp());
            System.out.println("____________________________________");
        });
    }

    public static User caseLoginUp() {
        User user = new Quest();
        System.out.println("LOGIN TO APP:");
        String[] emailAndPassword;
        emailAndPassword = MenuHelper.getEmailAndPassword();
        user = UserController.login(emailAndPassword[0], emailAndPassword[1]);
        if (user.getRole().equals(Role.NOUSER)) {
            System.out.println("EMAIL OR PASSWORD NOT MATCH; OR USER NOT IN DATABASE");
        } else {
            System.out.println("WELCOME, " + user.getEmail());
        }
        return user;
    }

    public static boolean numberIsInRange(final int choice, final int lowerRange, final int upperRange) {
        return (lowerRange <= choice && choice <= upperRange);
    }

    public static void caseRegisterUser() {
        String[] emailAndPassword;
        System.out.println("REGISTER:");
        emailAndPassword = MenuHelper.getEmailAndPassword();
        try {
            UserController.registerUser(emailAndPassword[0], emailAndPassword[1]);
            System.out.println("REGISTRATION successful".toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void caseRegisterAssociate() {
        String[] emailAndPassword;
        System.out.println("ENTER EMAIL AND PASSWORD FOR ASSOCIATE:");
        emailAndPassword = MenuHelper.getEmailAndPassword();
        try {
            UserController.registerAssociate(emailAndPassword[0], emailAndPassword[1]);
        } catch (IllegalArgumentException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void caseCreateFlight() {
        AdministratorFlightOperator administratorFlightOperator = new AdministratorFlightOperator();
        administratorFlightOperator.openAdmin();
    }

    public static void caseFlightFromHand() {
        System.out.println("FLIGHT FROM HAND");
    }

    public static void caseNewMessagesForAssociates(User user) throws IOException {
        ThreadsController threadsController = new ThreadsController(user);
        List<Messages> messages = threadsController.getAllUnansweredMessages();
        if (messages.size() > 0) {
            MenuHelper.displayLastMessages(messages);
            boolean isDone = true;
            Messages messages1 = new Messages();
            try {
                int choice = MenuHelper.getNumberFromUserWithMessage("ENTER THREAD TO ENTER");
                System.out.println("HANDLE DETAILS FOR MESSAGE :" + choice);
                messages1 = messages.get(choice);
                int indexOfLastMessage = messages1.getMessages().size() - 1;
                Message message = messages1.getMessages().get(indexOfLastMessage);
                System.out.println("USER: " + message.user());
                System.out.println("MESSAGE: " + message.description());
                System.out.println("DATE: " + message.timestamp());
            } catch (Exception e) {
                System.out.println("WRONG CHOICE");
            }
            String answer = MenuHelper.getInputFromUserWithMessage("MESSAGE");
            if(!answer.isEmpty()) {
                threadsController.respond(user, messages1, answer);
            }
        } else {
            System.out.println("NO NEW MESSAGES");
        }
    }

    private static void displayReclamations() {
        System.out.println("3. RECLAMATIONS");
    }

    public static User caseChangePassword(User user) {
        String newPassword = MenuHelper.getInputFromUserWithMessage("ENTER NEW PASSWORD");
        String oldPassword = MenuHelper.getInputFromUserWithMessage("ENTER OLD PASSWORD");
        if (UserController.changePassword(user.getEmail(), oldPassword, newPassword)) {
            System.out.println("PASSWORD CHANGED, LOGGING OUT");
             return UserController.logout();
        } else {
            System.out.println("OLD PASSWORD IS WRONG");
            return user;
        }
    }

    public static void caseAddFunds(final User user) {
        System.out.println("ADD FUNDS");
        int fundsToAdd = MenuHelper.getNumberFromUserWithMessage("AMOUNT TO ADD");
        UserController.addFunds(user, fundsToAdd);
    }

    public static void caseDisplayFlightsForUsers() {
        UserFlightOperator userFlightOperator = new UserFlightOperator();
        userFlightOperator.openUser();
    }

    public static void caseDisplayFlightsForAssociates() {
        AssistanceFlightOperator assistanceFlightOperator = new AssistanceFlightOperator();
        assistanceFlightOperator.openAssistance();
    }

    public static void caseBuyTickets(final User user) {
        UserController.buyTicket(user);
    }
}
