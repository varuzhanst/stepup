package net.ddns.varuzhan.demo.dto;

public class UserRegistrationDto {
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String password;
    private String token;
    private String role;
    public UserRegistrationDto() {
    }
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }







    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
