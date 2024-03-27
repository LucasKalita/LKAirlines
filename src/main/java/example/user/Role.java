package example.user;

public enum Role {
    ADMIN ("Admin"),
    USER ("User"),
    ASSOCIATE ("Associate"),
    NOUSER ("NoUser");
    final String name;

    Role(final String name) {
        this.name = name;
    }
}
