package vn.dating.chat.mapper;

import org.modelmapper.ModelMapper;
import vn.dating.chat.dto.messages.ResultGroupMemberDto;
import vn.dating.chat.model.GroupMember;

import java.util.ArrayList;
import java.util.List;

public class GroupMemberMapper {
    public static ResultGroupMemberDto toResultGroupMemberDto(GroupMember groupMember){
        ModelMapper modelMapper = new ModelMapper();
        ResultGroupMemberDto resultGroupMemberDto = modelMapper.map(groupMember, ResultGroupMemberDto.class);
        return  resultGroupMemberDto;
    }
    public static List<ResultGroupMemberDto> toResultGroupMembersDto(List<GroupMember> groupMembers){
       List<ResultGroupMemberDto> resultGroupMemberDtos = new ArrayList<>();
       groupMembers.forEach(g->{
           resultGroupMemberDtos.add(toResultGroupMemberDto(g));
       });
       return  resultGroupMemberDtos;
    }
}
