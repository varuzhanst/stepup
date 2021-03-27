package net.ddns.varuzhan.demo.model;

import javax.persistence.*;

@Entity
public class ManagersGroupsSubjects {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private User user;
    @ManyToOne
    private GroupInfo groupInfo;
    @ManyToOne
    private SubjectInfo subjectInfo;

    public ManagersGroupsSubjects(User user, GroupInfo groupInfo, SubjectInfo subjectInfo) {
        this.user = user;
        this.groupInfo = groupInfo;
        this.subjectInfo = subjectInfo;
    }

    public ManagersGroupsSubjects() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public GroupInfo getGroupInfo() {
        return groupInfo;
    }

    public void setGroupInfo(GroupInfo groupInfo) {
        this.groupInfo = groupInfo;
    }

    public SubjectInfo getSubjectInfo() {
        return subjectInfo;
    }

    public void setSubjectInfo(SubjectInfo subjectInfo) {
        this.subjectInfo = subjectInfo;
    }

}
