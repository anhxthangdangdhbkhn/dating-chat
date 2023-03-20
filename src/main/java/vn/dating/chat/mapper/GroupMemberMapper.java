package vn.dating.chat.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import vn.dating.chat.dto.messages.api.ResultGroupMemberDto;
import vn.dating.chat.model.GroupMember;
import vn.dating.chat.model.User;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GroupMemberMapper {
    public static ResultGroupMemberDto toResultGroupMemberDto(GroupMember groupMember){
        ModelMapper modelMapper = new ModelMapper();
        ResultGroupMemberDto resultGroupMemberDto = modelMapper.map(groupMember, ResultGroupMemberDto.class);
        return  resultGroupMemberDto;
    }
    public static List<ResultGroupMemberDto> toResultGroupMembersDto(List<GroupMember> groupMembers){
        List<User> users = new ArrayList<>();
        groupMembers.forEach(groupMember -> {
            users.add(groupMember.getUser());
        });
        ModelMapper modelMapper = new ModelMapper();
        Type listType = new TypeToken<List<ResultGroupMemberDto>>() {}.getType();
        return modelMapper.map(users,listType);
    }
}
