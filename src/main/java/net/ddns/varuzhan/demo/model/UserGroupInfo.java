package net.ddns.varuzhan.demo.model;

import javax.persistence.*;

@Entity
public class UserGroupInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private User user;
    @ManyToOne
    private GroupInfo groupInfo;

    public UserGroupInfo() {
    }

    public UserGroupInfo(User user, GroupInfo groupInfo) {
        this.user = user;
        this.groupInfo = groupInfo;
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
}
