package vn.dating.chat.mapper;

import org.modelmapper.ModelMapper;
import vn.dating.chat.dto.messages.api.ResultGroupMembersAndMessagesOfGroupDto;
import vn.dating.chat.dto.messages.api.ResultGroupMembersOfGroupDto;
import vn.dating.chat.model.Group;
import vn.dating.chat.model.GroupMember;
import vn.dating.chat.model.Message;
import vn.dating.chat.model.User;

import java.util.List;

public class GroupMapper {

    public static ResultGroupMembersOfGroupDto toGetGroup(Group group){
        ModelMapper modelMapper = new ModelMapper();
        ResultGroupMembersOfGroupDto resultGroupMembersOfGroupDto = modelMapper.map(group, ResultGroupMembersOfGroupDto.class);
        return resultGroupMembersOfGroupDto;
    }
    public static ResultGroupMembersOfGroupDto toGetGroupOfUser(Group group, User current, List<User> members){
        ModelMapper modelMapper = new ModelMapper();
        ResultGroupMembersOfGroupDto resultGroupMembersOfGroupDto = modelMapper.map(group, ResultGroupMembersOfGroupDto.class);
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

    public static ResultGroupMembersAndMessagesOfGroupDto toGetMemberAndLastMessagesOfGroup(Group group, List<GroupMember> groupMembers, List<Message> messages){
        ModelMapper modelMapper = new ModelMapper();
        ResultGroupMembersAndMessagesOfGroupDto resultGroupMembersAndMessagesOfGroupDto = modelMapper.map(group, ResultGroupMembersAndMessagesOfGroupDto.class);
        resultGroupMembersAndMessagesOfGroupDto.setMembers(GroupMemberMapper.toResultGroupMembersDto(groupMembers));
        resultGroupMembersAndMessagesOfGroupDto.setMessages(MessageMapper.toMessages(messages));
        return resultGroupMembersAndMessagesOfGroupDto;
    }
}
