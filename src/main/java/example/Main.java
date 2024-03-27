package example;



import example.flights.menu.MenuHelper;
import example.messages.Messages;
import example.messages.ThreadsController;
import example.user.Quest;
import example.user.User;
import example.user.UserController;

import java.io.IOException;
import java.util.List;


public class Main {
    private static User user;
    private static String[] emailAndPassword;
    private static String userInput;
    private static ThreadsController threadsController;

    public static void main(String[] args) throws IOException {


        user = new Quest();
        threadsController = new ThreadsController(user);
        while (true) {
            switch (user.getRole().name()) {
                case "NOUSER" -> {
                    MenuHelper.displayLoginMenu();
                    userInput = MenuHelper.getInputFromUserWithMessage("CHOICE");
                    switch (userInput.toUpperCase()) {
                        case "E" -> {
                            return;
                        }
                        case "1" -> user = MenuHelper.caseLoginUp();
                        case "2" -> MenuHelper.caseRegisterUser();
                        default -> MenuHelper.getNotImplementedChoice(userInput);
                    }
                }
                case "ADMIN" -> {
                    MenuHelper.displayAdminMenu();
                    userInput = MenuHelper.getInputFromUserWithMessage("CHOICE");
                    switch (userInput.toUpperCase()) {
                        case "E" -> {
                            return;
                        }
                        case "1" -> MenuHelper.caseRegisterAssociate();
                        case "2" -> MenuHelper.caseCreateFlight();
                        case "L" -> user = UserController.logout();
                        default -> MenuHelper.getNotImplementedChoice(userInput);
                    }
                }
                case "ASSOCIATE" -> {
                    MenuHelper.displayAssociateMenu(user.getEmail());
                    userInput = MenuHelper.getInputFromUserWithMessage("CHOICE");
                    switch (userInput.toUpperCase()) {
                        case "E" -> {
                            return;
                        }
                        case "1" -> MenuHelper.caseDisplayFlightsForAssociates();
                        case "2" -> MenuHelper.caseNewMessagesForAssociates(user);
                        case "3" -> MenuHelper.caseReclamationsForAssociates();
                        case "S" -> user = MenuHelper.caseChangePassword(user);
                        case "L" -> user = UserController.logout();
                    }
                }
                case "USER" -> {
                    MenuHelper.displayUserMenu(user);
                    userInput = MenuHelper.getInputFromUserWithMessage("CHOICE");
                    switch (userInput.toUpperCase()) {
                        case "E" -> {
                            return;
                        }
                        case "1" -> MenuHelper.caseDisplayFlightsForUsers();
                        case "2" -> {
                            List<Messages> messages = threadsController.getAllMessageMadeByUser(user);
                            MenuHelper.displayLastMessages(messages);
                            String choice = MenuHelper.getInputFromUserWithMessage("CHOICE");
                            int choiceInInt = -1;
                            try {
                                choiceInInt = Integer.parseInt(choice);
                            } catch (Exception e) {
                                //CHOICE IS NOT A NUMBER
                            }
                            if(choiceInInt == -1) {
                                if (choice.equals("N")) {
                                    String description = MenuHelper.getInputFromUserWithMessage("MESSAGE");
                                    threadsController.newMessage(user, description);
                                }
                            } else {
                                if(MenuHelper.numberIsInRange(choiceInInt, 0, messages.size())) {
                                    Messages messages1 = messages.get(choiceInInt);
                                    MenuHelper.displayAllMessages(messages1);
                                    userInput = MenuHelper.getInputFromUserWithMessage("[R] Respond | [ENTER] BACk \n CHOICE");
                                    if(userInput.equals("R")) {
                                        String answer = MenuHelper.getInputFromUserWithMessage("MESSAGE");
                                        threadsController.respond(user, messages1, answer);
                                    }
                                } else {
                                    System.out.println("OUT OF RANGE");
                                }
                            }
                        }
                        case "3" -> MenuHelper.caseReclamationsForUsers();
                        case "4" -> MenuHelper.caseAddFunds(user);
                        case "5" -> MenuHelper.caseBuyTickets(user);
                        case "6" -> MenuHelper.displayUserTickets(user);
                        case "S" -> user = MenuHelper.caseChangePassword(user);
                        case "L" -> user = UserController.logout();
                    }
                }
                default -> {
                    System.out.println("NO LOGIN DISPLAY");
                    return;
                }
            }
        }
    }
}