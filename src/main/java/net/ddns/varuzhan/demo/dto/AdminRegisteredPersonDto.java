package net.ddns.varuzhan.demo.dto;

public class AdminRegisteredPersonDto {
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;

    public AdminRegisteredPersonDto() {
    }

    public AdminRegisteredPersonDto(String firstName,
                                    String lastName,
                                    String middleName,
                                    String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.email = email;
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
