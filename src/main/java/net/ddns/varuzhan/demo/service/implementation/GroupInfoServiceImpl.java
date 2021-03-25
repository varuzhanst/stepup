package net.ddns.varuzhan.demo.service.implementation;

import net.ddns.varuzhan.demo.model.GroupInfo;
import net.ddns.varuzhan.demo.repository.GroupInfoRepository;
import net.ddns.varuzhan.demo.service.prototype.GroupInfoService;
import org.springframework.stereotype.Service;

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
}
