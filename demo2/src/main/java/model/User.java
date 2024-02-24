package model;

public class User {
    private String name;
    private String login;
    private String password;

    // Default constructor
    public User() {
    }

    // Initializing constructor
    public User(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
    }

    // Getter and setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and setter for login
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    // Getter and setter for password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
