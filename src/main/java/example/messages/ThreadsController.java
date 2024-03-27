package example.messages;
import example.json.MessagesJsonController;
import example.user.Role;
import example.user.User;

import java.io.IOException;
import java.util.List;

public class ThreadsController {
    private final MessagesJsonController messagesJsonController = new MessagesJsonController();
    private final Threads threads;

    public ThreadsController(User user) throws IOException {
        this.threads = messagesJsonController.getAllThreads();
        this.user = user;
    }

    private User user;

    public void newMessage(User user, String message) {
        this.user = user;
        if (user.getRole().equals(Role.USER)) {
            threads.newThread(user, message);
            messagesJsonController.writeThreads(threads);
        }
    }

    public void respond(User user, Messages messages, String message) {
        this.user = user;
        this.user.respondToMessage(messages, message);
        messagesJsonController.writeThreads(threads);
    }

    public List<Messages> getAllUnansweredMessages() {
        return threads.getAllMessageSentByUsers();
    }

    public List<Messages> getAllMessageMadeByUser(User user) {
        return threads.getThreads().stream().filter(messages ->
                messages.isUserInConversation(user)
        ).toList();
    }
}