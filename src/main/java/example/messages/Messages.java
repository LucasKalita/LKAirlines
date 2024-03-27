package example.messages;



import example.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Messages {
    private final List<Message> messages = new ArrayList<>();

    public Messages(User user, String description) {
        messages.add(new Message(user, description, LocalDateTime.now()));
    }

    public Messages() {
    }

    public List<Message> getMessages() {
        return new ArrayList<>(messages);
    }

    public void respond(User user, String description) {
        messages.add(new Message(user, description, LocalDateTime.now()));
    }

    public void add(User user, String description, LocalDateTime dateTime) {
        messages.add(new Message(user, description, dateTime));
    }

    public boolean isLastToRespondUser() {
        return messages.get(messages.size() - 1).user().isUser();
    }

    public boolean isLastToRespondAssociate() {
        return messages.get(messages.size() - 1).user().isAssociate();
    }

    public boolean isUserInConversation(User user) {
        final long count = messages.stream().filter(message -> message.user().getEmail().equals(user.getEmail())).count();
        return count > 0;
    }

    @Override
    public String toString() {
        return "Messages{" +
                "messages=" + messages +
                '}';
    }
}
