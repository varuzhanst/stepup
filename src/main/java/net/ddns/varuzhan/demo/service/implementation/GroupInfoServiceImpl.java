package net.ddns.varuzhan.demo.service.implementation;

import net.ddns.varuzhan.demo.model.GroupInfo;
import net.ddns.varuzhan.demo.repository.GroupInfoRepository;
import net.ddns.varuzhan.demo.service.prototype.GroupInfoService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class GroupInfoServiceImpl implements GroupInfoService {
    private final GroupInfoRepository groupInfoRepository;

    public GroupInfoServiceImpl(GroupInfoRepository groupInfoRepository) {
        this.groupInfoRepository = groupInfoRepository;
    }

    @Override
    public GroupInfo save(GroupInfo groupInfo) {
        return groupInfoRepository.save(groupInfo);
    }

    @Override
    public Set<GroupInfo> getAllGroups() {
        return new HashSet<>(groupInfoRepository.findAll());
    }

    @Override
    public Set<GroupInfo> getGroupInfoByGroupNumber(String groupNumber) {
        return groupInfoRepository.getGroupInfoByGroupNumber(groupNumber);
    }

    @Override
    public GroupInfo getGroupInfoById(String id) {
        try{
            Integer groupId = Integer.parseInt(id);
            return groupInfoRepository.findById(groupId).get();
        }catch (Exception e){
            return null;
        }

    }


}
