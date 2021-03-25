package net.ddns.varuzhan.demo.service.prototype;

import net.ddns.varuzhan.demo.model.GroupInfo;
import org.springframework.stereotype.Component;

@Component
public interface GroupInfoService {
    GroupInfo save (GroupInfo groupInfo);
}
