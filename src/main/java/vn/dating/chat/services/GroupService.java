package vn.dating.chat.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.dating.chat.dto.messages.api.ResultGroupMembersAndMessagesOfGroupDto;
import vn.dating.chat.dto.messages.api.ResultGroupMembersOfGroupDto;
import vn.dating.chat.mapper.GroupMapper;
import vn.dating.chat.mapper.UserMapper;
import vn.dating.chat.model.*;
import vn.dating.chat.repositories.GroupMemberRepository;
import vn.dating.chat.repositories.GroupRepository;
import vn.dating.chat.utils.PagedResponse;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.*;

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
    private MessageService messageService;

    @Autowired
    private EntityManager entityManager;


    public Group getGroupById(Long id) {
        return groupRepository.findById(id).orElse(null);
    }

    public List<GroupMember> getGroupByIdAndUserId(long  groupId,long userId) {
        return groupMemberRepository.findByGroupIdAndUserId(groupId,userId);
    }

    public Group saveGroup(Group group) {
        return groupRepository.save(group);
    }

    public boolean addUsersToGroup(Group group, List<User> membersToAdd,GroupMemberType groupMemberType) {
        List<GroupMember> groupMembers = new ArrayList<>();
        for (User member : membersToAdd) {
            GroupMember groupMember = new GroupMember();
            groupMember.setGroup(group);
            groupMember.setUser(member);
            groupMember.setType(groupMemberType);
            groupMembers.add(groupMember);

            groupMember = groupMemberRepository.save(groupMember);
            if(groupMember !=null){
                groupMembers.add(groupMember);
            }
        }
        if(groupMembers.size() == membersToAdd.size()) return  true;
        return false;
    }

//    public List<GroupMember> addUsersToGroup(Group group, List<User> membersToAdd,GroupMemberType groupMemberType) {
//        List<GroupMember> groupMembers = new ArrayList<>();
//        for (User member : membersToAdd) {
//            GroupMember groupMember = new GroupMember();
//            groupMember.setGroup(group);
//            groupMember.setUser(member);
//            groupMember.setType(groupMemberType);
//            groupMembers.add(groupMember);
//
//            groupMember = groupMemberRepository.save(groupMember);
//            if(groupMember !=null){
//                groupMembers.add(groupMember);
//            }
//        }
//        return groupMembers;
//    }


    public ResultGroupMembersAndMessagesOfGroupDto getGroupMembersAndLastMessagesOfGroup(Group group, String currentEmail){
        List<GroupMember> groupMembers = group.getMembers();
        List<Message> messages = messageService.getLastTenMessagesByGroupId(group.getId());
        ResultGroupMembersAndMessagesOfGroupDto resultGroupMembersAndMessagesOfGroupDto =  GroupMapper.toGetMemberAndLastMessagesOfGroup(group,groupMembers,messages);

        if(groupMembers.size()==2){
            if(groupMembers.get(1).getUser().getEmail().contains(currentEmail)){
                resultGroupMembersAndMessagesOfGroupDto.setAvatar(groupMembers.get(0).getUser().getAvatar());
                resultGroupMembersAndMessagesOfGroupDto.setName(groupMembers.get(0).getUser().getUsername());
            }else {
                resultGroupMembersAndMessagesOfGroupDto.setAvatar(groupMembers.get(1).getUser().getAvatar());
                resultGroupMembersAndMessagesOfGroupDto.setName(groupMembers.get(1).getUser().getUsername());
            }

        }else resultGroupMembersAndMessagesOfGroupDto.setAvatar("https://via.placeholder.com/50x50");
        return resultGroupMembersAndMessagesOfGroupDto;
    }
    public PagedResponse findGroupOfUser(User currentUser, int page, int size){

        Pageable pageable = PageRequest.of(page, size);
        Long userId  = currentUser.getId();
        Page<Group> groupPage =   groupRepository.findDistinctByMembersUserId(userId,pageable);


        if(groupPage.getNumberOfElements()==0){
            return new PagedResponse<>(Collections.emptyList(), groupPage.getNumber(), groupPage.getSize(),
                    groupPage.getTotalElements(), groupPage.getTotalPages(), groupPage.isLast());
        }

        List<Group> groupList = groupPage.stream().toList();
        List<ResultGroupMembersOfGroupDto> resultGroupMembersOfGroupDtos = new ArrayList<>();

        for(int index=0;index<groupList.size();index++){
            Group group = groupList.get(index);
            resultGroupMembersOfGroupDtos.add(getChatInfoGroup(group,currentUser));
        }
        return new PagedResponse<>(resultGroupMembersOfGroupDtos, groupPage.getNumber(), groupPage.getSize(), groupPage.getTotalElements(),
                groupPage.getTotalPages(), groupPage.isLast());
    }


    public PagedResponse findTopGroupsAndTopMessagesOfUser(User currentUser, int page, int size){

        Pageable pageable = PageRequest.of(page, size);
        Long userId  = currentUser.getId();
        String currentUserEmail = currentUser.getEmail();;
        Page<Group> groupPage =   groupRepository.findGroupsByUserIdOrderByLastMessage(userId,pageable);


        if(groupPage.getNumberOfElements()==0){
            return new PagedResponse<>(Collections.emptyList(), groupPage.getNumber(), groupPage.getSize(),
                    groupPage.getTotalElements(), groupPage.getTotalPages(), groupPage.isLast());
        }

        List<Group> groupList = groupPage.stream().toList();
        List<ResultGroupMembersAndMessagesOfGroupDto> resultGroupMembersAndMessagesOfGroupDtos = new ArrayList<>();

        for(int index=0;index<groupList.size();index++){
            Group group = groupList.get(index);
            resultGroupMembersAndMessagesOfGroupDtos.add(getGroupMembersAndLastMessagesOfGroup(group,currentUserEmail));
        }
        return new PagedResponse<>(resultGroupMembersAndMessagesOfGroupDtos, groupPage.getNumber(), groupPage.getSize(), groupPage.getTotalElements(),
                groupPage.getTotalPages(), groupPage.isLast());
    }

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

    public ResultGroupMembersOfGroupDto getChatInfoGroup(Group group, User current){

        ResultGroupMembersOfGroupDto resultGroupMembersOfGroupDto = GroupMapper.toGetGroup(group);

        List<User> members = userService.getUsersInGroup(group.getId());

        if(members.size()==2){
            if(members.get(1).getEmail().contains(current.getEmail())){
                resultGroupMembersOfGroupDto.setAvatar(members.get(0).getAvatar());
                resultGroupMembersOfGroupDto.setName(members.get(0).getUsername());
            }else {
                resultGroupMembersOfGroupDto.setAvatar(members.get(1).getAvatar());
                resultGroupMembersOfGroupDto.setName(members.get(1).getUsername());
            }

        }else resultGroupMembersOfGroupDto.setAvatar("https://via.placeholder.com/50x50");
        resultGroupMembersOfGroupDto.setMembers(UserMapper.toGetListUsers(members));

        return resultGroupMembersOfGroupDto;
    }

    public ResultGroupMembersOfGroupDto getChatInfoGroup(Long groupId, User current){
        Group group = getGroupById(groupId);
        ResultGroupMembersOfGroupDto resultGroupMembersOfGroupDto = GroupMapper.toGetGroup(group);

        List<User> members = userService.getUsersInGroup(groupId);

        if(members.size()==2){
            if(members.get(1).getEmail().contains(current.getEmail())){
                resultGroupMembersOfGroupDto.setAvatar(members.get(0).getAvatar());
                resultGroupMembersOfGroupDto.setName(members.get(0).getUsername());
            }else {
                resultGroupMembersOfGroupDto.setAvatar(members.get(1).getAvatar());
                resultGroupMembersOfGroupDto.setName(members.get(1).getUsername());
            }

        }else resultGroupMembersOfGroupDto.setAvatar("https://via.placeholder.com/50x50");
        resultGroupMembersOfGroupDto.setMembers(UserMapper.toGetListUsers(members));

        return resultGroupMembersOfGroupDto;
    }
    public ResultGroupMembersOfGroupDto createGroup(Group group, List<User> userList, User current, GroupMemberType groupMemberType){


        boolean checkAdd = addUsersToGroup(group,userList,groupMemberType);
//        if(checkAdd== false) return new ResultGroupDto();


        ResultGroupMembersOfGroupDto resultGroupMembersOfGroupDto = GroupMapper.toGetGroup(group);
        List<User> groupUser = userService.getUsersInGroup(group.getId());

        if(userList.size()==2){
            if(userList.get(1).getEmail().contains(current.getEmail())){
                resultGroupMembersOfGroupDto.setAvatar(userList.get(0).getAvatar());
                resultGroupMembersOfGroupDto.setName(userList.get(0).getUsername());
            }else {
                resultGroupMembersOfGroupDto.setAvatar(userList.get(1).getAvatar());
                resultGroupMembersOfGroupDto.setName(userList.get(1).getUsername());
            }

        }else resultGroupMembersOfGroupDto.setAvatar("https://via.placeholder.com/50x50");

        resultGroupMembersOfGroupDto.setMembers(UserMapper.toGetListUsers(groupUser));

        return resultGroupMembersOfGroupDto;
    }

}

