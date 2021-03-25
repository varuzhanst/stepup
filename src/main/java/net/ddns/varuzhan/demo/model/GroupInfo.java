package net.ddns.varuzhan.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class GroupInfo implements Comparable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String groupNumber;

    public GroupInfo() {
    }

    public GroupInfo(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupInfo groupInfo = (GroupInfo) o;
        return Objects.equals(groupNumber, groupInfo.groupNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupNumber);
    }


    @Override
    public int compareTo(Object o) {
        GroupInfo groupInfo = (GroupInfo) o;
        return this.groupNumber.compareTo(groupInfo.getGroupNumber());
    }


}
