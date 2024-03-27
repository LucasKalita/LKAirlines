package example.json;



import example.messages.Threads;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MessagesJsonController {
    private static final String FILENAME = "./Main/src/main/java/org/example/flights/files/messages.json";
    private static final int SPACING = 3;

    public void writeThreads(Threads threads) {
        JSONArray jsonArray = new JSONArray();
        threads.getThreads()
                .forEach(messages -> {
                    JSONArray objectThread = new JSONArray();
                    messages.getMessages().forEach(message -> {
                        JSONObject objectMessage = new JSONObject();
                        objectMessage.put("user", message.user().getEmail());
                        objectMessage.put("description", message.description());
                        objectMessage.put("date", message.timestamp().format(DateTimeFormatter.ISO_DATE_TIME));
                        objectThread.put(objectMessage);
                    });
                    jsonArray.put(objectThread);
                });
        try (PrintWriter out = new PrintWriter(new FileWriter(FILENAME))) {
            out.write(jsonArray.toString(SPACING));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JSONArray checkFile() throws IOException {
        File f = new File(FILENAME);
        if (f.exists() && !f.isDirectory()) {
            JSONTokener jsonTokener = new JSONTokener(new FileReader(FILENAME));
            return new JSONArray(jsonTokener);
        } else {
            writeThreads(new Threads());
            return new JSONArray();
        }
    }

    public Threads getAllThreads() throws IOException {
        JSONArray jsonArray = checkFile();
        List<User> users = new UserJsonController().getAllUsers();
        Threads thread = new Threads();
        if(jsonArray.isEmpty()) {
            return thread;
        }
        for (int j = 0; j < jsonArray.length(); j++) {
            JSONArray jsonArray1 = jsonArray.getJSONArray(j);
            Messages messages = new Messages();
            for(int i = 0; i < jsonArray1.length(); i++) {
                JSONObject jsonObject = jsonArray1.getJSONObject(i);
                final String user = jsonObject.getString("user");
                final User user2 = users.stream()
                        .filter(user1 -> user1.getEmail().equals(user))
                        .findFirst()
                        .orElse(new Quest());
                final String description = jsonObject.getString("description");
                final LocalDateTime date = LocalDateTime.parse(jsonObject.getString("date"), DateTimeFormatter.ISO_DATE_TIME);
                messages.add(user2, description, date);
            }
            thread.add(messages);
        }
        return thread;
    }
}
