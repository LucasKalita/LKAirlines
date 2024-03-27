package example.json;


import example.user.Quest;
import example.user.Role;
import example.user.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UserJsonController {
    private static final String FILENAME = "./Main/src/main/java/org/example/flights/files/users.json";
    private static final int SPACING = 3;

    public void registerAssociate(String email, String password) throws IOException {
        registerUser(email, password, "ASSOCIATE");
    }

    public void registerUser(String email, String password) throws IOException {
        registerUser(email, password, "USER");
    }


    public User loginUser(String email, String password) throws IOException {
        List<User> jsonModels = getAllUsers();
        final Optional<User> first = jsonModels.stream().filter(jsonModel -> jsonModel.getEmail().equals(email)
                        && jsonModel.getPassword().equals(password))
                .findFirst();
        return first.orElse(new Quest());
    }

    private void registerUser(String email, String password, String role) throws IOException {
        JSONArray jsonArray = checkFile();
        if(isEmailAlreadyInUse(jsonArray, email)) throw new IllegalArgumentException("EMAIL IS DATABASE");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", email);
        jsonObject.put("password", password);
        jsonObject.put("role", role);
        jsonObject.put("wallet", 0);
        jsonObject.put("tickets", new JSONArray());
        jsonArray.put(jsonObject);
        try (PrintWriter out = new PrintWriter(new FileWriter(FILENAME))) {
            out.write(jsonArray.toString(SPACING));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() throws IOException {
        List<User> listOfUsers = new ArrayList<>();
        JSONArray jsonArray = checkFile();
        for (int j = 0; j < jsonArray.length(); j++) {
            JSONObject jsonObject = jsonArray.getJSONObject(j);
            String email = jsonObject.getString("email");
            String password = jsonObject.getString("password");
            String role = jsonObject.getString("role");
            int wallet = jsonObject.getInt("wallet");
            Role role1 = Role.NOUSER;
            switch (role) {
                case "NOUSER" -> {
                }
                case "ASSOCIATE" ->
                    role1 = Role.ASSOCIATE;
                case "USER" ->
                    role1 = Role.USER;
                case "ADMIN" ->
                    role1 = Role.ADMIN;
            }
            User user = new User(email, password, role1, wallet, new ArrayList<>());
            listOfUsers.add(user);
        }
        return listOfUsers;
    }

    public void addFunds(User user, int funds) {
        try {
            List<User> jsonModels = getAllUsers();
            final User jsonModel2 = getAllUsers().stream()
                    .filter(jsonModel -> jsonModel.getEmail().equals(user.getEmail()) && jsonModel.getPassword().equals(user.getPassword()))
                    .findFirst().orElse(null);
            if (jsonModel2 != null) {
                jsonModels.remove(jsonModel2);
                jsonModel2.addFundsToWallet(funds);
                user.addFundsToWallet(funds);
                jsonModels.add(jsonModel2);
                writeUsers(jsonModels);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean changePassword(User user, String newPassword) {
        try {
            List<User> jsonModels = getAllUsers();
            final User jsonModel2 = getAllUsers().stream()
                    .filter(jsonModel -> jsonModel.getEmail().equals(user.getEmail()) && jsonModel.getPassword().equals(user.getPassword()))
                    .findFirst().orElse(null);
            if (jsonModel2 != null) {
                jsonModels.remove(jsonModel2);
                jsonModels.add(user.changePassword(newPassword));
                writeUsers(jsonModels);
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeUsers(List<User> jsonModels) {
        JSONArray jsonArray = new JSONArray();
        jsonModels.forEach(jsonModel -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", jsonModel.getEmail());
            jsonObject.put("password", jsonModel.getPassword());
            jsonObject.put("role", jsonModel.getRole());
            jsonObject.put("wallet", jsonModel.getWallet());
            jsonObject.put("tickets", jsonModel.getTicketIDs());
            jsonArray.put(jsonObject);
        });
        try (PrintWriter out = new PrintWriter(new FileWriter(FILENAME))) {
            out.write(jsonArray.toString(SPACING));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JSONArray checkFile() throws IOException {
        File f = new File(FILENAME);
        if (f.exists() && !f.isDirectory()) {
            JSONTokener jsonTokener = new JSONTokener(new FileReader(FILENAME));
            return new JSONArray(jsonTokener);
        } else {
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", "admin@acs");
            jsonObject.put("password", "admin");
            jsonObject.put("role", "ADMIN");
            jsonObject.put("wallet", 0);
            jsonObject.put("tickets", new JSONArray());
            jsonArray.put(jsonObject);
            try (PrintWriter out = new PrintWriter(new FileWriter(FILENAME))) {
                out.write(jsonArray.toString(SPACING));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new JSONArray();
        }
    }

    private boolean isEmailAlreadyInUse(JSONArray jsonArray, String emailToCheck) {
        for (int j = 0; j < jsonArray.length(); j++) {
            JSONObject jsonObject = jsonArray.getJSONObject(j);
            String email = jsonObject.getString("email");
            if (Objects.equals(email, emailToCheck)) {
                return true;
            }
        }
        return false;
    }

    public List<String> printEmailOfAssociates() throws IOException {
        List<String> listOfStrings = new ArrayList<>();
        JSONTokener jsonTokener = new JSONTokener(new FileReader(FILENAME));
        JSONArray jsonArray = new JSONArray(jsonTokener);
        for (int j = 0; j < jsonArray.length(); j++) {
            JSONObject jsonObject = jsonArray.getJSONObject(j);
            listOfStrings.add(jsonObject.getString("email"));
        }
        return listOfStrings;
    }
}
