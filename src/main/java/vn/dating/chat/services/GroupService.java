package vn.dating.chat.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.dating.chat.model.Group;
import vn.dating.chat.model.GroupMember;
import vn.dating.chat.model.User;
import vn.dating.chat.repositories.GroupMemberRepository;
import vn.dating.chat.repositories.GroupRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@Transactional
public class GroupService {

    @Autowired
    private  GroupRepository groupRepository;

    @Autowired
    private  GroupMemberRepository groupMemberRepository;


    public Group getGroupById(Long id) {
        return groupRepository.findById(id).orElse(null);
    }

    public Group getGroupByName(String name) {
        return groupRepository.findByName(name).orElse(null);
    }

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    public Group saveGroup(Group group) {
        return groupRepository.save(group);
    }

    public void deleteGroupById(Long id) {
        groupRepository.deleteById(id);
    }

    public List<GroupMember> addUsersToGroup(Group group, List<User> membersToAdd) {
        List<GroupMember> groupMembers = new ArrayList<>();
        for (User member : membersToAdd) {
            GroupMember groupMember = new GroupMember();
            groupMember.setGroup(group);
            groupMember.setUser(member);
            groupMembers.add(groupMember);

            groupMember = groupMemberRepository.save(groupMember);
            groupMembers.add(groupMember);
        }
        return groupMembers;
//        return groupMemberRepository.saveAll(groupMembers);
    }

    public Map<Group, List<User>> getGroupsForUser(long userId){

        List<GroupMember> groupMembers = groupMemberRepository.findByUserId(userId);
        Map<Group, List<User>> groupsAndMembers = new HashMap<>();
        for (GroupMember groupMember : groupMembers) {
            Group group = groupMember.getGroup();
            List<GroupMember> groupMemberByGrId =  groupMemberRepository.findByGroupId(group.getId());
            List<User> members = new ArrayList<>();
            groupMemberByGrId.forEach(g->{
                members.add(g.getUser());
            });
            groupsAndMembers.put(group, members);
        }

        return groupsAndMembers;

    }

    public List<String> getAllUserOfGroup(long groupId){

        log.info("groupId:" +groupId );
        List<GroupMember> groupMemberList = groupMemberRepository.findByUserId(groupId);
        log.info("Member group size: ",groupMemberList.size());
        List<String> listUsers = new ArrayList<>();
//        groupMemberList.forEach(m->{
//            listUsers.add(m.getUser().getEmail());
//        });

        return  listUsers;
    }

}

