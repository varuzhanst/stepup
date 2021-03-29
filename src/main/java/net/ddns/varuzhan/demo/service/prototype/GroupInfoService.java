package net.ddns.varuzhan.demo.service.prototype;

import net.ddns.varuzhan.demo.model.GroupInfo;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public interface GroupInfoService {
    GroupInfo save (GroupInfo groupInfo);
    Set<GroupInfo> getAllGroups();
    Set<GroupInfo> getGroupInfoByGroupNumber(String groupNumber);
    GroupInfo getGroupInfoById(String id);
}
