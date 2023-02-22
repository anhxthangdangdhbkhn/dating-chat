package vn.dating.chat.mapper;

import org.modelmapper.ModelMapper;
import vn.dating.chat.dto.messages.api.ResultGroupDto;
import vn.dating.chat.model.Group;
import vn.dating.chat.model.User;

import java.util.List;

public class GroupMapper {

    public static ResultGroupDto toGetGroup(Group group){
        ModelMapper modelMapper = new ModelMapper();
        ResultGroupDto resultGroupDto = modelMapper.map(group, ResultGroupDto.class);
        return  resultGroupDto;
    }
    public static ResultGroupDto toGetGroupOfUser(Group group, List<User> members){
        ModelMapper modelMapper = new ModelMapper();
        ResultGroupDto resultGroupDto = modelMapper.map(group, ResultGroupDto.class);
        resultGroupDto.setMembers(UserMapper.toGetListUsers(members));
        return  resultGroupDto;
    }
}
