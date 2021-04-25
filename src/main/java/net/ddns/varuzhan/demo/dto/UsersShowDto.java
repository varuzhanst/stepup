package net.ddns.varuzhan.demo.dto;

import net.ddns.varuzhan.demo.model.User;

public class UsersShowDto implements Comparable {
    private User user;
    private String group;

    public UsersShowDto(User user, String group) {
        this.user = user;
        this.group = group;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public int compareTo(Object o) {
        return this.user.compareTo(((UsersShowDto)o).getUser());
    }
}
