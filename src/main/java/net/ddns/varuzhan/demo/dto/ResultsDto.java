package net.ddns.varuzhan.demo.dto;

import net.ddns.varuzhan.demo.model.User;

public class ResultsDto implements Comparable{
    private User user;
    private String grade;

    public ResultsDto(User user, String grade) {
        this.user = user;
        this.grade = grade;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public int compareTo(Object o) {
        return this.user.compareTo(((ResultsDto)o).getUser());
    }
}
