package net.ddns.varuzhan.demo.dto;

import net.ddns.varuzhan.demo.model.GroupInfo;

import java.util.Objects;

public class GroupsManagementDto implements Comparable{
    private GroupInfo groupInfo;
    private String UsersCount;
    private String ManagersCount;

    public GroupsManagementDto(GroupInfo groupInfo, String usersCount, String managersCount) {
        this.groupInfo = groupInfo;
        UsersCount = usersCount;
        ManagersCount = managersCount;
    }

    public GroupInfo getGroupInfo() {
        return groupInfo;
    }

    public void setGroupInfo(GroupInfo groupInfo) {
        this.groupInfo = groupInfo;
    }

    public String getUsersCount() {
        return UsersCount;
    }

    public void setUsersCount(String usersCount) {
        UsersCount = usersCount;
    }

    public String getManagersCount() {
        return ManagersCount;
    }

    public void setManagersCount(String managersCount) {
        ManagersCount = managersCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupsManagementDto that = (GroupsManagementDto) o;
        return Objects.equals(groupInfo, that.groupInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupInfo);
    }


    @Override
    public int compareTo(Object o) {
        GroupsManagementDto dto = (GroupsManagementDto) o;
        return this.groupInfo.getGroupNumber().compareTo(dto.groupInfo.getGroupNumber());
    }
}
