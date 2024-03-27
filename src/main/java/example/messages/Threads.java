package example.messages;



import example.user.User;

import java.util.ArrayList;
import java.util.List;

public class Threads {
    private List<Messages> threads = new ArrayList<>();

    public void newThread(User user, String description) {
        threads.add(new Messages(user, description));
    }

    public List<Messages> getAllMessageSentByUsers() {
        return threads.stream()
                .filter(Messages::isLastToRespondUser)
                .toList();
    }

    public List<Messages> getAllMessageSentByAssociates() {
        return threads.stream()
                .filter(Messages::isLastToRespondAssociate)
                .toList();
    }

    public void respond(User user, Messages messages) {
    }

    public List<Messages> getThreads() {
        return new ArrayList<>(threads);
    }

    public void add(Messages messages) {
        threads.add(messages);
    }

    @Override
    public String toString() {
        return "Threads{" +
                "threads=" + threads +
                '}';
    }
}
