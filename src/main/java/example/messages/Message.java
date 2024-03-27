package example.messages;



import example.user.User;

import java.time.LocalDateTime;

public record Message(User user, String description, LocalDateTime timestamp) {
}
