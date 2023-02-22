package vn.dating.chat.services;

import org.springframework.stereotype.Service;
import vn.dating.chat.model.GroupMember;
import vn.dating.chat.repositories.GroupMemberRepository;

import java.util.List;

@Service
public class GroupMemberService {

    private final GroupMemberRepository groupMemberRepository;

    public GroupMemberService(GroupMemberRepository groupMemberRepository) {
        this.groupMemberRepository = groupMemberRepository;
    }

    public List<GroupMember> getGroupMembersByGroupId(Long groupId) {
        return groupMemberRepository.findByGroupId(groupId);
    }

    public List<GroupMember> getGroupMembersByUserId(Long userId) {
        return groupMemberRepository.findByUserId(userId);
    }

    public void saveGroupMember(GroupMember groupMember) {
        groupMemberRepository.save(groupMember);
    }

    public void deleteGroupMemberById(Long id) {
        groupMemberRepository.deleteById(id);
    }

    public GroupMember getGroupMemberById(Long id) {
        return groupMemberRepository.findById(id).orElse(null);
    }



}

