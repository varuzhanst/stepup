package net.ddns.varuzhan.demo.dto;

import net.ddns.varuzhan.demo.model.User;

public class SubjectsManagementDto implements Comparable {
    private User user;
    private Integer countOfSubjects;

    public SubjectsManagementDto(User user, Integer countOfSubjects) {
        this.user = user;
        this.countOfSubjects = countOfSubjects;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getCountOfSubjects() {
        return countOfSubjects;
    }

    public void setCountOfSubjects(Integer countOfSubjects) {
        this.countOfSubjects = countOfSubjects;
    }

    @Override
    public int compareTo(Object o) {
        SubjectsManagementDto subjectsManagementDto = (SubjectsManagementDto) o;
        return this.user.compareTo(subjectsManagementDto.user);
    }
}
