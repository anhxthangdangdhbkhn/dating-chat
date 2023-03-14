package vn.dating.chat.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.dating.chat.dto.messages.api.CreateGroupDto;
import vn.dating.chat.dto.messages.api.ResultGroupDto;
import vn.dating.chat.mapper.GroupMapper;
import vn.dating.chat.mapper.UserMapper;
import vn.dating.chat.model.*;
import vn.dating.chat.repositories.GroupMemberRepository;
import vn.dating.chat.repositories.GroupRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.Instant;
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

    @Autowired
    private UserService userService;

    @Autowired
    private EntityManager entityManager;


    public Group getGroupById(Long id) {
        return groupRepository.findById(id).orElse(null);
    }

    public List<GroupMember> getGroupByIdAndUserId(long  groupId,long userId) {
        return groupMemberRepository.findByGroupIdAndUserId(groupId,userId);
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

    public List<GroupMember> addUsersToGroup(Group group, List<User> membersToAdd,GroupMemberType groupMemberType) {
        List<GroupMember> groupMembers = new ArrayList<>();
        for (User member : membersToAdd) {
            GroupMember groupMember = new GroupMember();
            groupMember.setGroup(group);
            groupMember.setUser(member);
            groupMember.setType(groupMemberType);
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

//    public boolean isUserMemberOfGroup(String userEmail, Long groupId) {
//        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(u) FROM user u JOIN u.groups g WHERE u.email = :userEmail AND g.id = :groupId", Long.class);
//        query.setParameter("userEmail", userEmail);
//        query.setParameter("groupId", groupId);
//
//        Long count = query.getSingleResult();
//        return count > 0;
//    }

    public boolean isUserMemberOfGroup(String userEmail, Long groupId) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(gm) FROM GroupMember as gm, User as u  WHERE u.email = :userEmail AND gm.id = :groupId", Long.class);
        query.setParameter("userEmail", userEmail);
        query.setParameter("groupId", groupId);

        Long count = query.getSingleResult();
        return count > 0;
    }
    public boolean isUserMemberOfGroup(String userEmail, Long groupId,GroupType groupType) {
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(gm) FROM GroupMember as gm, User as u  WHERE u.email = :userEmail AND gm.id = :groupId", Long.class);
        query.setParameter("userEmail", userEmail);
        query.setParameter("groupId", groupId);

        Long count = query.getSingleResult();
        return count > 0;
    }
//    public boolean isUserMemberOfGroup(String userEmail, Long groupId,GroupType groupType,GroupMemberType groupMemberType) {
//        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(gm) FROM public.group_member as gm, users as u  WHERE u.email = :userEmail AND gm.group_id = :groupId", Long.class);
//        query.setParameter("userEmail", userEmail);
//        query.setParameter("groupId", groupId);
//
//        Long count = query.getSingleResult();
//        return count > 0;
//    }

    public boolean existChatTwoUser(Long userId1, Long userId2) {

        String jpql = "SELECT gm.group.id FROM GroupMember gm " +
                "WHERE gm.user.id IN (:userId1, :userId2) " +
                "AND gm.group.type = :groupType " +
                "AND gm.type = :groupMemberType " +
                "GROUP BY gm.group.id " +
                "HAVING COUNT(gm.group.id) = 2";

        Query query = entityManager.createQuery(jpql);
        query.setParameter("userId1", userId1);
        query.setParameter("userId2", userId2);
        query.setParameter("groupType", GroupType.PRIVATE);
        query.setParameter("groupMemberType", GroupMemberType.PRIVATE);

        List<Long> result = query.getResultList();

        if (result.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
    public List<Long> existChatTwoUser(String userEmail1, String userEmail2) {

        String jpql = "SELECT gm.group.id FROM GroupMember gm " +
                "WHERE gm.user.email IN (:userEmail1, :userEmail2) " +
                "AND gm.group.type = :groupType " +
                "AND gm.type = :groupMemberType " +
                "GROUP BY gm.group.id " +
                "HAVING COUNT(gm.group.id) = 2";

        Query query = entityManager.createQuery(jpql);
        query.setParameter("userEmail1", userEmail1);
        query.setParameter("userEmail2", userEmail2);
        query.setParameter("groupType", GroupType.PRIVATE);
        query.setParameter("groupMemberType", GroupMemberType.PRIVATE);

        List<Long> result = query.getResultList();

        if (result.isEmpty()) {
            return null;
        } else {
            return result;
        }
    }

    public ResultGroupDto getChatInfoGroup(Long groupId, User current){
        Group group = getGroupById(groupId);
        ResultGroupDto resultGroupDto = GroupMapper.toGetGroup(group);

      //  List<GroupMember> groupMembers = groupMemberRepository.findByGroupId(groupId);

        List<User> members = userService.getUsersInGroup(groupId);

//        groupMembers.forEach(g->{
//            members.add(g.getUser());
//        });

        if(members.size()==2){
            if(members.get(1).getEmail().contains(current.getEmail())){
                resultGroupDto.setAvatar(members.get(0).getAvatar());
                resultGroupDto.setName(members.get(0).getUsername());
            }else {
                resultGroupDto.setAvatar(members.get(1).getAvatar());
                resultGroupDto.setName(members.get(1).getUsername());
            }

        }else resultGroupDto.setAvatar("https://via.placeholder.com/50x50");
        resultGroupDto.setMembers(UserMapper.toGetListUsers(members));

        return resultGroupDto;
    }

    public ResultGroupDto createGroup(Group group, List<User> userList,User current,GroupMemberType groupMemberType){


        List<GroupMember>   groupMembers = addUsersToGroup(group,userList,groupMemberType);

        ResultGroupDto resultGroupDto = GroupMapper.toGetGroup(group);

        List<User> groupUser = userService.getUsersInGroup(group.getId());
//        groupMembers.forEach(m->{
//            groupUser.add(m.getUser());
//        });

        if(userList.size()==2){
            if(userList.get(1).getEmail().contains(current.getEmail())){
                resultGroupDto.setAvatar(userList.get(0).getAvatar());
                resultGroupDto.setName(userList.get(0).getUsername());
            }else {
                resultGroupDto.setAvatar(userList.get(1).getAvatar());
                resultGroupDto.setName(userList.get(1).getUsername());
            }

        }else resultGroupDto.setAvatar("https://via.placeholder.com/50x50");

        resultGroupDto.setMembers(UserMapper.toGetListUsers(groupUser));

        return resultGroupDto;
    }
}

